/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.util;

import java.util.List;

import org.eclipse.epsilon.emc.emf.tools.EmfTool;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eol.types.IToolNativeTypeDelegate;
import org.eclipse.epsilon.etl.EtlModule;
import org.eclipse.epsilon.etl.IEtlModule;
import org.eclipse.epsilon.evl.EvlModule;
import org.eclipse.epsilon.evl.IEvlFixer;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.execute.context.EvlContext;

public abstract class EpsilonUtil {

	private EpsilonUtil() {}
	
	public static IEtlModule initialiseEtlModule(IModel source, IModel target, IModel... extraModels) throws EolModelLoadingException {
		final IEtlModule transformer = new EtlModule();
		
		source.setStoredOnDisposal(false);
		target.setStoredOnDisposal(false);
		target.setReadOnLoad(false);
		
		transformer.getContext().getModelRepository().addModels(source, target);
		
		addExtraModels(transformer.getContext(), extraModels);
		
		addNativeTypeDelegate(transformer.getContext());
		
		return transformer;
	}
	
	public static IEvlModule initialseEvlModule(IEvlFixer fixer, IModel model, IModel... extraModels) throws EolModelLoadingException  {
		final IEvlModule validator = new EvlModule();
		// Guarantee ordering of unsatisfied constraints for traceability tests
		validator.setContext(new EvlContext() {{
			unsatisfiedConstraints = new java.util.LinkedHashSet<>();
		}});
		validator.setUnsatisfiedConstraintFixer(fixer);
		
		model.setReadOnLoad(true);
		model.setStoredOnDisposal(true); // Fixes may be applied, so we should store the model
		
		validator.getContext().getModelRepository().addModel(model);
		
		addExtraModels(validator.getContext(), extraModels);

		addNativeTypeDelegate(validator.getContext());
		
		return validator;
	}

	private static void addExtraModels(IEolContext context, IModel... models) throws EolModelLoadingException {
		for (IModel model : models) {
			model.setStoredOnDisposal(false);
			model.load();
		}
		context.getModelRepository().addModels(models);
	}
	
	private static void addNativeTypeDelegate(IEolContext context) {
		context.getNativeTypeDelegates().add(new IToolNativeTypeDelegate(){

			public Object createInstance(String clazz, IEolContext context) throws EolRuntimeException {
				return new EmfTool();
			}

			@Override
			public boolean knowsAbout(String clazz) {
				return "org.eclipse.epsilon.emc.emf.tools.EmfTool".equals(clazz);
			}

			@Override
			public Object createInstance(String clazz, List<Object> parameters,
					IEolContext context) throws EolRuntimeException {
				return createInstance(clazz, context);
			}
		});
	}
}
