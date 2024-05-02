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

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.antlr.runtime.ANTLRInputStream;
import org.antlr.runtime.Lexer;
import org.antlr.runtime.TokenStream;
import org.eclipse.epsilon.common.module.IModule;
import org.eclipse.epsilon.common.module.ModuleElement;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.EpsilonParser;
import org.eclipse.epsilon.common.util.AstUtil;
import org.eclipse.epsilon.eol.debug.EolDebugger;
import org.eclipse.epsilon.eol.dom.ExecutableBlock;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.erl.ErlModule;
import org.eclipse.epsilon.pinset.columnGenerators.Column;
import org.eclipse.epsilon.pinset.columnGenerators.Grid;
import org.eclipse.epsilon.pinset.columnGenerators.NestedFrom;
import org.eclipse.epsilon.pinset.columnGenerators.Properties;
import org.eclipse.epsilon.pinset.columnGenerators.Reference;
import org.eclipse.epsilon.pinset.debug.PinsetDebugger;
import org.eclipse.epsilon.pinset.parse.PinsetLexer;
import org.eclipse.epsilon.pinset.parse.PinsetParser;

/**
 * PinsetModule.
 *
 * @author Alfonso de la Vega
 * @since 2.1
 */
public class PinsetModule extends ErlModule {

	public static final String SILENT_ANNOTATION = "silent";
	public static final String NORMALIZE_ANNOTATION = "normalize";
	public static final String FILL_NULLS_ANNOTATION = "fillNulls";
	public static final String FILL_NULLS_MEAN = "mean";
	public static final String FILL_NULLS_MODE = "mode";

	protected List<DatasetRule> datasetRules = new ArrayList<>();
	protected String outputFolder = "";
	protected String extension = ".csv";
	protected String prefix = "";
	protected boolean silent = false;
	protected boolean persistDatasets = true;

	@Override
	public ModuleElement adapt(AST cst, ModuleElement parentAst) {
		switch (cst.getType()) {
			case PinsetParser.DATASET:
				return new DatasetRule();
			case PinsetParser.GUARD:
				return new ExecutableBlock<>(Boolean.class);
			case PinsetParser.COLUMN:
				return new Column();
			case PinsetParser.PROPERTIES:
				return new Properties();
			case PinsetParser.REFERENCE:
				return new Reference();
			case PinsetParser.GRID:
				return new Grid();
			case PinsetParser.NESTEDFROM:
				return new NestedFrom();
		}
		return super.adapt(cst, parentAst);
	}

	@Override
	public void build(AST cst, IModule module) {
		super.build(cst, module);

		for (AST processRuleAst : AstUtil.getChildren(cst, PinsetParser.DATASET)) {
			datasetRules.add((DatasetRule) module.createAst(processRuleAst, this));
		}
	}

	@Override
	public Lexer createLexer(ANTLRInputStream inputStream) {
		return new PinsetLexer(inputStream);
	}

	@Override
	public EpsilonParser createParser(TokenStream tokenStream) {
		return new PinsetParser(tokenStream);
	}

	@Override
	public String getMainRule() {
		return "pinsetModule";
	}

	@Override
	public HashMap<String, Class<? extends IModule>> getImportConfiguration() {
		HashMap<String, Class<? extends IModule>> importConfiguration = super.getImportConfiguration();
		importConfiguration.put("pinset", PinsetModule.class);
		return importConfiguration;
	}

	@Override
	protected Object processRules() throws EolRuntimeException {
		for (DatasetRule datasetRule : datasetRules) {
			datasetRule.execute(context);
			if (persistDatasets) {
				try {
					new CSVFile(getFilePath(datasetRule), datasetRule.getDataset()).save();
				}
				catch (FileNotFoundException e) {
					throw new EolRuntimeException(e);
				}
				datasetRule.dispose();
			}
		}
		return null;
	}

	public void preExecution() throws EolRuntimeException {
		execute(getPre(), getContext());
	}

	public List<DatasetRule> getDatasetRules() {
		return datasetRules;
	}

	public DatasetRule getDatasetRule(String ruleName) {
		for (DatasetRule rule : datasetRules) {
			if (rule.getName().equalsIgnoreCase(ruleName)) {
				return rule;
			}
		}
		return null;
	}

	public void setOutputFolder(String attribute) {
		outputFolder = attribute;
	}

	public String getOutputFolder() {
		if (outputFolder.equals("") && getSourceFile() != null) {
			outputFolder = getSourceFile().getParent();
		}
		return outputFolder;
	}

	public String getFilePath(DatasetRule rule) {
		return String.format("%s/%s",
				getOutputFolder(), getFileName(rule));
	}

	public String getFileName(DatasetRule rule) {
		return String.format("%s%s%s",
				getPrefix(), rule.getName(), getExtension());
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public boolean isSilent() {
		return silent;
	}

	public void setSilent(boolean silent) {
		this.silent = silent;
	}

	/**
	 * Set whether the datasets must be persisted into output files or not
	 */
	public void persistDatasets(boolean persistDatasets) {
		this.persistDatasets = persistDatasets;
	}

	@Override
	public EolDebugger createDebugger() {
		return new PinsetDebugger();
	}
	
}
