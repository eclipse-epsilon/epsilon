/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
******************************************************************************/
package org.eclipse.epsilon.eol.engine.test.acceptance.eunit;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.dom.Operation;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.eunit.EUnitModule;
import org.eclipse.epsilon.eunit.EUnitTest;
import org.eclipse.epsilon.eunit.EUnitTestResultType;
import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

public class EUnitRunner extends Runner {
	
	protected Class<?> clazz;
	protected EUnitModule module;
	protected HashMap<String, Description> descriptions = new HashMap<>();
	protected Description testSuiteDescription;
	protected File eolFile;
	
	public EUnitRunner(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * 
	 * @return A new EUnitModule
	 * @since 1.6
	 */
	protected EUnitModule newModule() {
		return new EUnitModule();
	}
	
	@Override
	public Description getDescription() {
		module = newModule();
		testSuiteDescription = Description.createSuiteDescription(clazz);
		module.getContext().getOperationContributorRegistry().add(new AssertWarningOperationContributor(module.getContext()));
		
		try {
			eolFile = FileUtil.getFileStandalone(clazz.getSimpleName() + ".eol", clazz);
			module.parse(eolFile);
		}
		catch (Exception e) {
			return testSuiteDescription;
		}
		
		if (module.getParseProblems().size() > 0) {
			return testSuiteDescription;
		}
		
		for (Operation test : module.getTests()) {
			Description testDescription = Description.createTestDescription(clazz, test.getName());
			testSuiteDescription.addChild(testDescription);
			descriptions.put(test.getName(), testDescription);
		}
		
		return testSuiteDescription;
	}

	@Override
	public void run(RunNotifier notifier) {
		
		notifier.fireTestStarted(testSuiteDescription);
		
		if (eolFile == null) {
			notifier.fireTestFailure(new Failure(testSuiteDescription, new Exception("EUnit file " + clazz.getSimpleName() + ".eol does not exist")));
			return;
		}
		
		if (module.getParseProblems().size() > 0) {
			StringBuilder sBuilder = new StringBuilder("Syntax errors in ");
			sBuilder.append(eolFile);
			sBuilder.append(": \n");
			for (ParseProblem problem : module.getParseProblems()) {
				sBuilder.append("- ");
				sBuilder.append(problem.toString());
				sBuilder.append('\n');
			}
			notifier.fireTestFailure(new Failure(testSuiteDescription, new Exception(sBuilder.toString())));
			return;
		}

		EUnitTest suiteRoot;
		try {
			module.getContext().getModelRepository().addModel(getEcoreModel());
			addExtraModels();
			module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("modelManager", new ModelManager(module.getContext())));
			
			module.execute();
			suiteRoot = module.getSuiteRoot();

			for (EUnitTest result : suiteRoot.getChildren()) {
				String name = result.getOperation().getName();
				Description description = descriptions.get(name);
				notifier.fireTestStarted(description);
				if (result.getResult() == EUnitTestResultType.SUCCESS) {
					notifier.fireTestFinished(description);
				}
				else if (result.getResult() == EUnitTestResultType.FAILURE) {
					notifier.fireTestFailure(new Failure(description, result.getException()));
				}
				else if (result.getResult() == EUnitTestResultType.ERROR) {
					notifier.fireTestFailure(new Failure(description, result.getException()));
				}
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
			notifier.fireTestFailure(new Failure(testSuiteDescription, ex));
		}
		finally {
			module.getContext().dispose();
			module.getContext().getModelRepository().dispose();
		}
		
		notifier.fireTestFinished(testSuiteDescription);
		
	}

	public InMemoryEmfModel getEcoreModel() {
		ResourceSet rs = new ResourceSetImpl();
		rs.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		Resource r = rs.createResource(URI.createFileURI("foo.xmi"));
		InMemoryEmfModel ecore = new InMemoryEmfModel("Ecore", r, EcorePackage.eINSTANCE);
		EObject copy = EcoreUtil.copy(EcorePackage.eINSTANCE.eResource().getContents().get(0));
		r.getContents().add(copy);
		rs.getResources().add(r);
		return ecore;
	}

	private void addExtraModels() throws InstantiationException,
			IllegalAccessException, InvocationTargetException, IllegalArgumentException, NoSuchMethodException, SecurityException {
		final List<Method> extraModelMethods = new ArrayList<>();
		for (Method m : clazz.getMethods()) {
			if (m.isAnnotationPresent(ExtraModel.class)) {
				extraModelMethods.add(m);
			}
		}
		if (!extraModelMethods.isEmpty()) {
			final Object oClazz = clazz.getConstructor().newInstance();
			for (Method m : extraModelMethods) {
				final IModel model = (IModel)m.invoke(oClazz);
				module.getContext().getModelRepository().addModel(model);
			}
		}
	}
	
}
 
