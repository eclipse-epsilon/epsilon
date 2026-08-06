/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * Copyright (c) 2012-2014 University of Twente.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Maarten Bezemer - lots of improvements
 ******************************************************************************/
package org.eclipse.epsilon.evl.emf.validation;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.EmfPrettyPrinter;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.dt.launching.EclipseContextManager;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.types.EolAnyType;
import org.eclipse.epsilon.evl.IEvlModule;
import org.eclipse.epsilon.evl.concurrent.EvlModuleParallelAnnotation;
import org.eclipse.epsilon.evl.execute.FixInstance;
import org.eclipse.epsilon.evl.execute.UnsatisfiedConstraint;
import org.eclipse.epsilon.evl.execute.context.IEvlContext;

public class EvlValidator implements EValidator {

	protected Set<String> diagnosticVariables;
	protected IEvlModule module;
	protected URI source;
	protected EmfPrettyPrinter printer = new EmfPrettyPrinter();
	protected Resource currentResource;
	protected ValidationResults results = new ValidationResults();
	protected Collection<EObject> history = new ArrayList<>();
	protected String modelName;
	protected String ePackageUri;
	protected String bundleId;
	protected boolean showErrorDialog = true;
	protected boolean logErrors = true;
	protected Collection<ValidationProblemListener> problemListeners = new ArrayList<>();
	protected DiagnosticChain diagnostics;

	/** Collection of all packages that are available to this validator */
	protected Collection<EPackage> ePackages = new ArrayList<>();
	
	public static final String DEFAULT_MODEL_NAME = "_Model";

	/**
	 * Key for the validation context, that (when provided) holds a
	 * IProgressMonitor object
	 */
	public static final String VALIDATION_MONITOR = "Epsilon EVL Validation Monitor";

	/**
	 * Note when using this constructor, make sure to call
	 * {@link #initialise(URI, String, String, String)} afterwards
	 */
	public EvlValidator() {
	}

	public EvlValidator(URI source, String modelName, String ePackageUri, String bundleId) {
		initialise(source, modelName, ePackageUri, bundleId);
	}

	public void initialise(URI source, String modelName, String ePackageUri, String bundleId) {
		// Emulate 4-arg constructor by only allowing initialisation once
		if (this.source == null) {
			this.source = source;
			this.modelName = modelName;
			this.ePackageUri = ePackageUri;
			this.bundleId = bundleId;
			ePackages.add(EPackage.Registry.INSTANCE.getEPackage(ePackageUri));
		}
		else {
			Activator.getDefault().getLog().log(new Status(
				IStatus.WARNING, Activator.PLUGIN_ID,
				"Ignored duplicate initialisation of validator (" + source.toString() + ").")
			);
		}
	}

	/**
	 * <p>Indicates that the value of the entry of the {@link Diagnostician}
	 * context {@link java.util.Map} with the specified <code>name</code> should
	 * be published as a global variable in the EVL script. If the context map
	 * does not contain an entry with the specified <code>name</code>, the variable
	 * will be set to <code>null</code>.
	 *
	 * <p>Note: this map is received through the <code>validate</code> methods in this
	 * class.</p>
	 *
	 * @see #validate(EObject, DiagnosticChain, Map)
	 * @see #validate(EClass, EObject, DiagnosticChain, Map)
	 * @see #validate(EDataType, Object, DiagnosticChain, Map)
	 */
	public void addDiagnosticianVariable(String name) {
		if (diagnosticVariables == null) {
			diagnosticVariables = new HashSet<>();
		}
		diagnosticVariables.add(name);
	}

	/**
	 * Make an additional package available to be used by this validator.
	 * 
	 * @param packageUri
	 *            Namespace URI of the package
	 */
	public void addAdditionalPackage(String packageUri) {
		EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageUri);
		if (ePackage != null) {
			ePackages.add(ePackage);
		}
	}
	
	@Override
	public boolean validate(EObject object, DiagnosticChain diagnostics,
			Map<Object, Object> context) {
		return true;
	}

	@Override
	public boolean validate(EClass eClass, EObject eObject,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		
		if (eObject.eResource() == null) return false;
		
		if (diagnostics != null) {
			// A complete validation is performed, so clear old fixes
			EvlMarkerResolutionGenerator.INSTANCE.removeFixesFor(eObject, source);
		}
		
		if (diagnostics != this.diagnostics) {
			this.diagnostics = diagnostics;
			validate(eObject.eResource(), context);

			// Add problem markers for violations in objects in externally referenced models
			for (Map.Entry<Object, Collection<UnsatisfiedConstraint>> entry : results.entrySet()) {
				if (!(entry.getKey() instanceof EObject)) {
					continue;
				}
				final EObject key = (EObject) entry.getKey();
				if (key.eResource() == eObject.eResource()) {
					continue;
				}

				addMarkers("[" + key.eResource().getURI() + "] ", key, diagnostics);
			}
		}

		addMarkers("", eObject, diagnostics);
		return results.isEmpty();
	}

	@Override
	public boolean validate(EDataType dataType, Object value,
			DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	protected Diagnostic createDiagnostic(String msgPrefix, UnsatisfiedConstraint unsatisfied) {
		int severity;

		if (unsatisfied.getConstraint().isCritique()) {
			if (unsatisfied.getConstraint().isInfo()) {
				severity = Diagnostic.INFO;
			}
			else {
				severity = Diagnostic.WARNING;
			}
		}
		else {
			severity = Diagnostic.ERROR;
		}

		return new BasicDiagnostic(severity, bundleId, 0,
			msgPrefix + unsatisfied.getMessage(), new Object[]{unsatisfied.getInstance()}
		);
	}
	
	/**
	 * 
	 * @return A new {@link IEvlModule}
	 * @since 1.6
	 */
	protected IEvlModule newModule() {
		return new EvlModuleParallelAnnotation();
	}
	
	protected void validate(Resource resource, Map<Object, Object> context) {
		results.clear();
		
		if (resource.getResourceSet() != null) EcoreUtil.resolveAll(resource.getResourceSet());
		else EcoreUtil.resolveAll(resource);
		
		IEvlContext evlContext = (module = newModule()).getContext();

		try {
			module.parse(source);
		}
		catch (Exception e) {
			if (logException(e)) {
				LogUtil.log("An error was encountered while parsing " + source + " : " + e.getMessage(), e, isShowErrorDialog());
			}
			for (ValidationProblemListener listener : problemListeners) {
				listener.onParseException(module, e);
			}
			return;
		}

		if (!module.getParseProblems().isEmpty()) {
			if (isLogErrors()) {
				LogUtil.log(source + " has one or more syntax errors : " + module.getParseProblems().get(0).toString(), null, isShowErrorDialog());
			}
			for (ValidationProblemListener listener : problemListeners) {
				listener.onParseProblems(module, module.getParseProblems());
			}
			return;
		}

		InMemoryEmfModel model = new InMemoryEmfModel(modelName, resource, ePackages);
		model.setConcurrent(true);
		evlContext.getModelRepository().addModel(model);
		
		Object monitor = null;
		if (context != null) {
			monitor = context.get(VALIDATION_MONITOR);
		}
		
		if (monitor instanceof IProgressMonitor) {
			EclipseContextManager.setup(evlContext, (IProgressMonitor) monitor);
		}
		else {
			EclipseContextManager.setup(evlContext);
		}

		if (diagnosticVariables != null) {
			for (String diagnosticVariable : diagnosticVariables) {
				final Variable variable = new Variable(
					diagnosticVariable,
					context.get(diagnosticVariable),
					EolAnyType.Instance
				);
				evlContext.getFrameStack().put(variable);
			}
		}

		try {
			for (UnsatisfiedConstraint unsatisfied : module.execute()) {
				Object key = unsatisfied.getInstance();
				Collection<UnsatisfiedConstraint> value = results.get(key);
				if (value == null) {
					results.put(key, value = new ArrayList<>());
				}
				value.add(unsatisfied);
			}
		}
		catch (EolRuntimeException e) {
			if (logException(e)) {
				LogUtil.log("A runtime error was raised during the evaluation of " + source + " : " + e.getMessage(), e, isShowErrorDialog());
			}
			for (ValidationProblemListener listener : problemListeners) {
				listener.onRuntimeException(module, e);
			}
		}
		finally {
			evlContext.dispose();
			evlContext.getModelRepository().dispose();
		}
	}

	protected void addMarkers(String msgPrefix, EObject eObject, DiagnosticChain diagnostics) {
		if (diagnostics == null) {
			// user is not interested in markers...
			return;
		}
		Collection<UnsatisfiedConstraint> unsatisfiedConstraints = results.get(eObject);
		
		if (unsatisfiedConstraints != null && unsatisfiedConstraints.size() > 0) {
			for (UnsatisfiedConstraint unsatisfied : unsatisfiedConstraints) {
				diagnostics.add(createDiagnostic(msgPrefix, unsatisfied));
				for (Object fix : unsatisfied.getFixes()) {
					EvlMarkerResolutionGenerator.INSTANCE.addResolution(unsatisfied.getMessage(),(FixInstance) fix, modelName, ePackageUri);
				}
			}
		}
	}

	/** @return true if an error dialog needs to be shown when an error occurs */
	public boolean isShowErrorDialog() {
		return showErrorDialog;
	}

	/**
	 * Sets whether an error dialog needs to be shown
	 * 
	 * @see #isShowErrorDialog()
	 */
	public void setShowErrorDialog(boolean showErrorDialog) {
		this.showErrorDialog = showErrorDialog;
	}

	/**
	 * Method to determine whether the given exception needs to be logged. Can
	 * be overridden to filter out certain types of exceptions (e.g. when the
	 * user cancelled the validation)
	 * 
	 * @return true if error needs to be logged
	 */
	public boolean logException(Exception exception) {
		return isLogErrors();
	}

	/**
	 * This method is used by the default implementation of
	 * {@link #logException(Exception) logException()}
	 * 
	 * @return true if errors need to be logged
	 * @see #logException(Exception)
	 */
	public boolean isLogErrors() {
		return logErrors;
	}

	/**
	 * Sets whether errors need to be logged or not
	 * 
	 * @see #isLogErrors()
	 */
	public void setLogErrors(boolean logErrors) {
		this.logErrors = logErrors;
	}

	public void addValidationProblemListener(ValidationProblemListener listener) {
		problemListeners.add(listener);
	}
	
	public boolean removeValidationProblemListener(ValidationProblemListener listener) {
		return problemListeners.remove(listener);
	}
	
	public static interface ValidationProblemListener {
		
		public void onParseProblems(IEvlModule module, List<ParseProblem> parseProblems);
		
		public void onRuntimeException(IEvlModule module, EolRuntimeException ex);
	
		public void onParseException(IEvlModule module, Exception ex);
		
	}
	
}
