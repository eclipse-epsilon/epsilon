/*******************************************************************************
 * Copyright (c) 2008-2012 The University of York, Antonio García-Domínguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio García-Domínguez - recreate the menu on every call to getMenu
 *                                (required in Eclipse 4.x), clean up
 ******************************************************************************/
package org.eclipse.epsilon.ewl.emf;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.epsilon.common.dt.console.EpsilonConsole;
import org.eclipse.epsilon.common.dt.util.EclipseUtil;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.emc.emf.EmfPrettyPrinter;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.dt.ExtensionPointToolNativeTypeDelegate;
import org.eclipse.epsilon.eol.dt.userinput.JFaceUserInput;
import org.eclipse.epsilon.eol.exceptions.EolRuntimeException;
import org.eclipse.epsilon.eol.execute.context.Variable;
import org.eclipse.epsilon.eol.tools.EolSystem;
import org.eclipse.epsilon.ewl.EwlModule;
import org.eclipse.epsilon.ewl.execute.WizardInstance;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuCreator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.MenuListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IActionDelegate;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractContributeWizardsAction implements IObjectActionDelegate, IMenuCreator, MenuListener {
	protected ISelection selection;
	protected IWorkbenchPart targetPart;

	private List<InMemoryEmfModel> models;
	
	/**
	 * Constructor for ContributeWizardsAction.
	 */
	public AbstractContributeWizardsAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	@Override
	public void run(IAction action) {
		// do nothing
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
		action.setMenuCreator(this);
	}

	@Override
	public void dispose() {
		if (models != null) {
			for (InMemoryEmfModel model : models) {
				model.dispose();
			}
		}
	}

	@Override
	public Menu getMenu(Menu parent) {
		Menu wizardsMenu = new Menu(parent);
		wizardsMenu.addMenuListener(this);
		return wizardsMenu;
	}

	@Override
	public Menu getMenu(Control parent) {
		Menu wizardsMenu = new Menu(parent);
		wizardsMenu.addMenuListener(this);
		return wizardsMenu;
	}

	@Override
	public void menuHidden(MenuEvent e) {
		// not interested in this event
	}

	@Override
	public void menuShown(MenuEvent e) {
		populateWizardsMenu((Menu)e.widget);
	}

	protected abstract EObject getEObject(Object selected);

	protected abstract EditingDomain getEditingDomain();

	protected abstract WorkbenchPartRefresher getWorkbenchPartRefresher();

	protected void execute(Command command) {
		EditingDomain editingDomain = getEditingDomain();		
		if (editingDomain != null) {
			editingDomain.getCommandStack().execute(command);
		}
		else {
			command.execute();
		}
	}

	private void populateWizardsMenu(Menu wizardsMenu) {
		// Clean the menu
		for (MenuItem item : wizardsMenu.getItems()) {
			item.dispose();
		}
		
		List<EObject> eObjects = new ArrayList<>();
		List<WizardInstance> applicableWizards = new ArrayList<>();

		if ((selection instanceof IStructuredSelection)) {
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Iterator<?> it = structuredSelection.iterator();
			
			while (it.hasNext()) {
				Object next = it.next();
				
				EObject eObject = getEObject(next);
				if (eObject != null) {
					eObjects.add(eObject);
				}
			}

			final Set<String> eObjectURIs = getEObjectURIsFor(eObjects);
			final List<IConfigurationElement> availableConfigElems = getConfigurationElementsFor(eObjectURIs);
			final List<EPackage> ePackages = getExtraPackages(availableConfigElems);
			if (!eObjects.isEmpty()) {
				ePackages.add(EmfUtil.getTopEPackage(eObjects.get(0)));
			}

			final Resource mainResource = eObjects.get(0).eResource();
			models = new ArrayList<>();
			models.add(new InMemoryEmfModel("Model", mainResource, ePackages));
			if (getEditingDomain() instanceof AdapterFactoryEditingDomain) {
				// AFED is the common superclass of many useful editing domains, such as the GMF DiagramEditingDomain,
				// and it gives us access to the other models besides the domain model (such as the .diagram notation
				// model).
				final AdapterFactoryEditingDomain adapterEDomain = (AdapterFactoryEditingDomain)getEditingDomain();

				// Try to give the extra models better names (without collisions)
				final Map<String, Integer> usedCounts = new HashMap<>();
				for (Resource res : adapterEDomain.getResourceSet().getResources()) {
					if (res != mainResource) {
						final String modelName = extractModelNameFromExtension(res, usedCounts);
						models.add(new InMemoryEmfModel(modelName, res));
					}
				}
			}

			for (URI uri : getEwlURIsFor(eObjectURIs, availableConfigElems)) {
				EwlModule module = new EwlModule();
				
				try {
					module.parse(uri);
					module.getContext().setErrorStream(EpsilonConsole.getInstance().getErrorStream());
					module.getContext().setOutputStream(EpsilonConsole.getInstance().getDebugStream());
					module.getContext().getNativeTypeDelegates().add(new ExtensionPointToolNativeTypeDelegate());
					module.getContext().setUserInput(new JFaceUserInput(module.getContext().getPrettyPrinterManager()));
					module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("editor", targetPart));
					module.getContext().getPrettyPrinterManager().addPrettyPrinter(new EmfPrettyPrinter());
					EolSystem system = new EolSystem();
					system.setContext(module.getContext());
					module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("System", system));
				} catch (Exception e) {
					LogUtil.log(e);
					continue;
				}

				for (InMemoryEmfModel model : models) {
					module.getContext().getModelRepository().addModel(model);
				}
				
				Object self = null;
				if (eObjects.size() > 1) {
					self = eObjects;
				}
				else if (eObjects.size() == 1){
					self = eObjects.get(0);
				}
				
				try {
					applicableWizards.addAll(module.getWizardsFor(self));
				} catch (EolRuntimeException e) {
					EpsilonConsole.getInstance().getErrorStream().println(e.toString());
				}
			}
		}
		
		for (final WizardInstance wizard : applicableWizards) {
			final MenuItem wizardItem = new MenuItem(wizardsMenu, SWT.NONE);
			try {
				wizardItem.setText(wizard.getTitle());
			} catch (EolRuntimeException e) {
				wizardItem.setText("<error> " + wizard.getWizard().getName());
				LogUtil.log(e);
			}
			wizardItem.addSelectionListener(new SelectionListener() {
				@Override
				public void widgetDefaultSelected(SelectionEvent e) {
					// do nothing
				}

				@Override
				public void widgetSelected(SelectionEvent e) {
						WorkbenchPartRefresher refresher = getWorkbenchPartRefresher();
						refresher.setPart(targetPart);
						
						ExecuteWizardInstanceCommand command = 
							new ExecuteWizardInstanceCommand(wizard, models, refresher);
						
						execute(command);
				}
			});
		}
	}

	/**
	 * Extracts the name of the extra model from the extension of the resource URI, while
	 * avoiding name collisions using a map that counts how many times each name has been
	 * used.
	 */
	private String extractModelNameFromExtension(Resource res, final Map<String, Integer> usedCounts) {
		String modelName = "Extra";

		if (res.getURI() != null && res.getURI().path() != null) {
			// Try to use the resource extension for the model name
			final String resourcePath = res.getURI().path().trim();
			final int dotPosition = resourcePath.lastIndexOf('.');
			if (dotPosition != -1) {
				final String extension = resourcePath.substring(dotPosition + 1);
				if (extension.length() > 0) {
					modelName = extension.substring(0, 1).toUpperCase() + extension.substring(1);
				}
			}
		}

		// Avoid collisions by appending '2', '3' and so on after the first use
		Integer usedCount = usedCounts.get(modelName);
		if (usedCount != null) {
			usedCount = usedCount + 1;
			usedCounts.put(modelName, usedCount);
			modelName = modelName + usedCount;
		}
		else {
			usedCounts.put(modelName, 1);
		}
		return modelName;
	}

	private IConfigurationElement[] getAllConfigurationElements() {
		return Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.epsilon.ewl.emf.wizards");
	}

	private List<IConfigurationElement> getConfigurationElementsFor(final Set<String> eObjectURIs) {
		final List<IConfigurationElement> availableConfigElems = new ArrayList<>();
		for (IConfigurationElement configurationElement : getAllConfigurationElements()) {
			String namespaceURI = configurationElement.getAttribute("namespaceURI");
			if (namespaceURI.equalsIgnoreCase("*") || eObjectURIs.contains(namespaceURI)) {
				availableConfigElems.add(configurationElement);
			}
		}
		return availableConfigElems;
	}

	private Set<String> getEObjectURIsFor(Collection<EObject> eObjects) {
		final Set<String> eObjectURIs = new TreeSet<>(); 
		for (EObject eObject : eObjects) {
			String eObjectNsURI = EmfUtil.getTopEPackage(eObject).getNsURI();
			eObjectURIs.add(eObjectNsURI);
		}
		return eObjectURIs;
	}

	private List<URI> getEwlURIsFor(Set<String> eObjectURIs, Collection<IConfigurationElement> availableConfigElems) {
		final List<URI> wizardURIs = new ArrayList<>();
		for (IConfigurationElement configurationElement : availableConfigElems) {
			String pluginId = configurationElement.getDeclaringExtension().getNamespaceIdentifier();
			try {
				String path = configurationElement.getAttribute("file");
				wizardURIs.add(FileLocator.find(Platform.getBundle(pluginId), new Path(path), null).toURI());
			} catch (Exception e) {
				LogUtil.log(e);
			}
		}
		for (WizardsExtensionPreference preference : WizardsExtensionPreference.getPreferences()) {
			String namespaceURI = preference.getNamespaceURI();
			if (namespaceURI.equalsIgnoreCase("*") || eObjectURIs.contains(namespaceURI)) {
				wizardURIs.add(new File(EclipseUtil.getWorkspaceFileAbsolutePath(preference.getWizards())).toURI());
			}
		}
		
		return wizardURIs;
	}

	private List<EPackage> getExtraPackages(Collection<IConfigurationElement> availableConfigElems) {
		final List<EPackage> ePackages = new ArrayList<>();
		for (IConfigurationElement elem : availableConfigElems) {
			final String extraPackages = elem.getAttribute("extraPackages");
			if (extraPackages == null) continue;
	
			for (String packageURI : extraPackages.split(",")) {
				final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageURI);
				if (ePackage != null) {
					ePackages.add(ePackage);
				}
			}
		}
		for (WizardsExtensionPreference preference : WizardsExtensionPreference.getPreferences()) {
			for (String packageURI : preference.getExtraPackages()) {
				final EPackage ePackage = EPackage.Registry.INSTANCE.getEPackage(packageURI);
				if (ePackage != null) {
					ePackages.add(ePackage);
				}
			}
		}
		return ePackages;
	}
}
