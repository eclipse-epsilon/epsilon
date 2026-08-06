/*********************************************************************
* Copyright (c) 2020 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.dt.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.Callable;

import org.eclipse.acceleo.ui.interpreter.language.AbstractLanguageInterpreter;
import org.eclipse.acceleo.ui.interpreter.language.CompilationResult;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationContext;
import org.eclipse.acceleo.ui.interpreter.language.EvaluationResult;
import org.eclipse.acceleo.ui.interpreter.language.InterpreterContext;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.editor.AbstractModuleEditorSourceViewerConfiguration;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.EolModule;
import org.eclipse.epsilon.eol.dt.editor.EolEditor;
import org.eclipse.epsilon.eol.execute.context.FrameStack;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

/**
 * 
 * @since 2.3
 */
public class EolInterpreter extends AbstractLanguageInterpreter {
	
	public void configureSourceViewer(SourceViewer viewer) {
		SourceViewerConfiguration configuration = new AbstractModuleEditorSourceViewerConfiguration(new EolEditor());
		configuration.getPresentationReconciler(viewer).install(viewer);
	};
	
	@Override
	public Callable<CompilationResult> getCompilationTask(InterpreterContext context) {
		return () -> {
			EolModule module = new EolModule();
			try {
				module.parse(context.getExpression());
				if (!module.getParseProblems().isEmpty()) {
					return new CompilationResult(new Status(IStatus.ERROR, this.getClass(), 
							module.getParseProblems().get(0).toString()));
				}
				return new CompilationResult(IStatus.OK);
			}
			catch (Exception ex) {
				return new CompilationResult(new Status(IStatus.ERROR, this.getClass(), 
							module.getParseProblems().get(0).toString()));
			}
		};
	}
	
	@Override
	public Callable<EvaluationResult> getEvaluationTask(EvaluationContext context) {
		return () -> {
			EolModule module = new EolModule();
			module.parse(context.getExpression());
			FrameStack frameStack = module.getContext().getFrameStack();
			
			LinkedHashSet<Resource> resources = new LinkedHashSet<>();
			
			for (Notifier notifier : context.getTargetNotifiers()) {
				Resource resource = getResource(notifier);
				if (resource != null) resources.add(resource);
			}
			
			List<InMemoryEmfModel> models = new ArrayList<InMemoryEmfModel>();
			for (Resource resource : resources) {
				InMemoryEmfModel model = new InMemoryEmfModel(resource) {
					@Override
					protected void init(String name, Resource modelImpl, Collection<EPackage> ePackages,
							boolean isContainerListenerEnabled) {
						// Models in the interpreter should have access to the global EPackage.Registry
						// See https://github.com/eclipse-epsilon/epsilon/discussions/213
						this.registry = EPackage.Registry.INSTANCE;
						super.init(name, modelImpl, ePackages, isContainerListenerEnabled);
					}
				};
				
				model.setName(models.isEmpty() ? "M" : "M" + models.size());
				module.getContext().getModelRepository().addModel(model);
			}
			
			List<Notifier> notifiers = context.getTargetNotifiers();
			
			if (notifiers.isEmpty()) {
				frameStack.put("self", null);
			}
			else {
				if (notifiers.size() == 1) {
					frameStack.put("self", notifiers.get(0));
				}
				else {
					frameStack.put("self", notifiers);
				}
			}
			
			for (org.eclipse.acceleo.ui.interpreter.view.Variable variable : context.getVariables()) {
				frameStack.put(variable.getName(), variable.getValue());
			}
			
			IEolContext eolContext = module.getContext();

			eolContext.setOutputStream(EpsilonConsole.getInstance().getDebugStream());
			eolContext.setErrorStream(EpsilonConsole.getInstance().getErrorStream());
			eolContext.setWarningStream(EpsilonConsole.getInstance().getWarningStream());

			Object result = module.execute();
			return new EvaluationResult(result);
		};
	}
	
	protected Resource getResource(Notifier notifier) {
		Resource resource = null;
		
		if (notifier instanceof ResourceSet) {
			ResourceSet resourceSet = (ResourceSet) notifier;
			if (!resourceSet.getResources().isEmpty()) {
				resource = resourceSet.getResources().get(0);
			}
		}
		else if (notifier instanceof Resource) {
			resource = (Resource) notifier;
		}
		else if (notifier instanceof EObject) {
			resource = ((EObject) notifier).eResource();
		}
		return resource;
	}

}
