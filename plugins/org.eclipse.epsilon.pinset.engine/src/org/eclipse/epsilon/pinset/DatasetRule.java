/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *********************************************************************/
package org.eclipse.epsilon.pinset;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.dom.AnnotatableModuleElement;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.dom.IExecutableModuleElement;
import org.eclipse.epsilon.eol.dom.Parameter;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.FrameType;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.execute.introspection.IPropertyGetter;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.EolModelElementType;
import org.eclipse.epsilon.eol.types.EolType;
import org.eclipse.epsilon.pinset.columnGenerators.Column;
import org.eclipse.epsilon.pinset.columnGenerators.ColumnGenerator;
import org.eclipse.epsilon.pinset.columnGenerators.Grid;
import org.eclipse.epsilon.pinset.columnGenerators.NestedFrom;
import org.eclipse.epsilon.pinset.columnGenerators.Properties;
import org.eclipse.epsilon.pinset.columnGenerators.Reference;
import org.eclipse.epsilon.pinset.parse.PinsetParser;

/**
 * DatasetRule.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class DatasetRule extends AnnotatableModuleElement {

	protected String name;
	protected Parameter parameter;
	protected ExecutableBlock<Boolean> guardBlock;
	protected IExecutableModuleElement fromBlock = null;
	protected List<ColumnGenerator> generators = new ArrayList<>();
	protected Dataset dataset;

	@SuppressWarnings("unchecked")
	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);
		name = cst.getFirstChild().getText();
		parameter = (Parameter) module.createAst(cst.getSecondChild(), this);
		guardBlock = (ExecutableBlock<Boolean>) module.createAst(
				AstUtil.getChild(cst, PinsetParser.GUARD), this);
		AST fromAST = AstUtil.getChild(cst, PinsetParser.FROM);
		if (fromAST != null) {
			fromBlock = (IExecutableModuleElement) module.createAst(fromAST.getFirstChild(), this);
		}
		boolean isSilent = ((PinsetModule) module).isSilent() ||
				this.hasAnnotation(PinsetModule.SILENT_ANNOTATION);
		// loop over children looking for column generators
		for (AST child : cst.getChildren()) {
			if (isColumnGenerator(child)) {
				ColumnGenerator gen = (ColumnGenerator) module.createAst(child, this);
				setSilent(gen, isSilent);
				generators.add(gen);
			}
		}
	}

	public static void setSilent(ColumnGenerator colGen, boolean isSilent) {
		if (colGen instanceof Column) {
			((Column) colGen).setSilent(isSilent);
		}
		else if (colGen instanceof Grid) {
			((Grid) colGen).setSilent(isSilent);
		}
		else if (colGen instanceof NestedFrom) {
			((NestedFrom) colGen).setSilent(isSilent);
		}
	}

	public static boolean isColumnGenerator(AST child) {
		switch (child.getType()) {
		case PinsetParser.PROPERTIES:
		case PinsetParser.REFERENCE:
		case PinsetParser.COLUMN:
		case PinsetParser.GRID:
		case PinsetParser.NESTEDFROM:
			return true;
		default:
			return false;
		}
	}

	public boolean isIncluded(Object object, IEolContext context,
			String varName) throws EolRuntimeException {
		if (guardBlock != null) {
			return guardBlock.execute(context,
					Variable.createReadOnlyVariable(varName, object));
		}
		else {
			return true;
		}
	}

	public void execute(IEolContext context) throws EolRuntimeException {
		Collection<?> oElements = null;
		IModel model = null;
		IPropertyGetter getter = null;
		EolType parameterType = parameter.getType(context);

		if (!(parameterType instanceof EolModelElementType)) {
			if (fromBlock == null) {
				throw new EolRuntimeException(
						"Datasets generated over non-model types must specify a 'from' expression");
			}
		}
		else {
			model = ((EolModelElementType) parameterType).getModel();
			getter = model.getPropertyGetter();
		}
		if (fromBlock == null) {
			oElements = ((EolModelElementType) parameterType).getAllOfKind();
		}
		else {
			context.getFrameStack().enterLocal(FrameType.UNPROTECTED, fromBlock);
			Object result = ReturnValueParser.obtainValue(
					context.getExecutorFactory().execute(fromBlock, context));
			context.getFrameStack().leaveLocal(fromBlock);
			if (!(result instanceof Collection<?>)) {
				throw new EolRuntimeException(
						"The 'from' expression must return a collection of elements");
			}
			oElements = (Collection<?>) result;
		}
		initialiseGenerators(context, getter);
		dataset = new Dataset();
		dataset.setColumnNames(getColumnNames());
		for (Object oElem : oElements) {
			if (!isIncluded(oElem, context, parameter.getName())) {
				continue;
			}
			dataset.addColumnValues(getRowValues(context, oElem));
		}
		postProcess(context, dataset);
	}

	private void postProcess(IEolContext context, Dataset dataset)
			throws EolRuntimeException {
		for (ColumnGenerator colGen : generators) {
			if (colGen instanceof Column || colGen instanceof Grid) {
				AnnotatableModuleElement colGenElement =
						((AnnotatableModuleElement) colGen);
				if (colGenElement.hasAnnotation(PinsetModule.NORMALIZE_ANNOTATION)) {
					Object value = colGenElement
							.getAnnotationsValues(PinsetModule.NORMALIZE_ANNOTATION,
									context)
							.get(0);
					if (value instanceof String) {
						try {
							value = Double.parseDouble((String) value);
						}
						catch (NumberFormatException nfe) {
							/* treated just below */}
					}
					if (value != null && !(value instanceof Number)) {
						throw new EolRuntimeException("Normalization value must be a number");
					}
					for (String colName : colGen.getNames()) {
						PostProcessing.normalize(dataset.getValuesByColumn(colName),
								(Number) value);
					}
				}
				if (colGenElement.hasAnnotation(PinsetModule.FILL_NULLS_ANNOTATION)) {
					String value = (String) colGenElement
							.getAnnotationsValues(PinsetModule.FILL_NULLS_ANNOTATION,
									context)
							.get(0);
					PostProcessing.FillType fType = PostProcessing.FillType.VALUE;
					if (value != null && value.equals(PinsetModule.FILL_NULLS_MODE)) {
						fType = PostProcessing.FillType.MODE;
					}
					else if (value != null && value.equals(PinsetModule.FILL_NULLS_MEAN)) {
						fType = PostProcessing.FillType.MEAN;
					}
					for (String colName : colGen.getNames()) {
						PostProcessing.fillNullValues(dataset.getValuesByColumn(colName),
								fType, value);
					}
				}
			}
		}
	}

	private List<Object> getRowValues(IEolContext context, Object oElem)
			throws EolRuntimeException {
		List<Object> rowValues = new ArrayList<>();
		context.getFrameStack().enterLocal(FrameType.UNPROTECTED, this);
		context.getFrameStack().put(
				Variable.createReadOnlyVariable(parameter.getName(), oElem));
		for (ColumnGenerator generator : generators) {
			List<Object> values = generator.getValues(oElem);
			rowValues.addAll(values);
			// if we calculate a column, we add it to the stack so it can be used
			//    in later column calculations. We know that values only has 1 elem
			if (generator instanceof Column) {
				context.getFrameStack().put(
						Variable.createReadOnlyVariable(((Column) generator).getName(),
								values.get(0)));
			}
		}
		context.getFrameStack().leaveLocal(this);
		return rowValues;
	}

	private List<String> getColumnNames() throws EolRuntimeException {
		List<String> columnNames = new ArrayList<>();
		for (ColumnGenerator generator : generators) {
			columnNames.addAll(generator.getNames());
		}
		return columnNames;
	}

	private void initialiseGenerators(IEolContext context,
			IPropertyGetter getter) {
		for (ColumnGenerator generator : generators) {
			initialise(generator, context, getter);
		}
	}

	public static void initialise(ColumnGenerator generator, IEolContext context,
			IPropertyGetter getter) {
		if (generator instanceof Properties) {
			((Properties) generator).setContext(context);
			((Properties) generator).setGetter(getter);
		}
		else if (generator instanceof Reference) {
			((Reference) generator).setContext(context);
			((Reference) generator).setGetter(getter);
		}
		else if (generator instanceof Column) {
			((Column) generator).setContext(context);
		}
		else if (generator instanceof Grid) {
			((Grid) generator).setContext(context);
		}
		else if (generator instanceof NestedFrom) {
			((NestedFrom) generator).initialise(context, getter);
		}
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dataset getDataset() {
		return dataset;
	}

	public void dispose() {
		dataset = null;
	}
}
