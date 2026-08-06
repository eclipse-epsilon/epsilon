/*******************************************************************************
 * Copyright (c) 2008-2015 The University of York, Antonio Garcia-Dominguez
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 *     Antonio Garcia-Dominguez - maintenance, add viewer customization extension point
 ******************************************************************************/
package org.eclipse.epsilon.dt.exeed;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.util.BasicDiagnostic;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.presentation.EcoreEditor;
import org.eclipse.emf.ecore.presentation.EcoreEditorPlugin;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.BinaryResourceImpl;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.GenericXMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceSetItemProvider;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.emf.edit.ui.dnd.ViewerDragAdapter;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryContentProvider;
import org.eclipse.emf.edit.ui.provider.UnwrappingSelectionProvider;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.dt.exeed.extensions.IExeedCustomizer;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.emf.dt.EmfRegistryManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.osgi.framework.FrameworkUtil;

public class ExeedEditor extends EcoreEditor {
	private static final String VIEWERCUSTOMIZER_CUSTOMIZERCLASS_ATTR = "customizerClass";
	private static final String VIEWERCUSTOMIZER_RESOURCECLASS_ATTR = "resourceClass";
	private static final String VIEWERCUSTOMIZER_EXTPOINT = "org.eclipse.epsilon.dt.exeed.customizer";
	
	protected List<DisposeListener> disposeListeners = new ArrayList<>();
	
	private static final class RegisteredEPackageResourceFactory implements Resource.Factory {
		@Override
		public Resource createResource(URI uri) {
			IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(uri.toString().replace("platform:/resource/", "")));
			String nsUri = "";
			try (InputStream is = file.getContents()) {
				try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
					nsUri = br.readLine();
				}
			}
			catch (Exception e) {
				LogUtil.log(e);
			}
			
			return EPackage.Registry.INSTANCE.getEPackage(nsUri).eResource();
		}
	}

	private final class SwitchableResourceSetItemProvider extends ResourceItemProviderAdapterFactory {
		@Override
		public Adapter createResourceSetAdapter() {
			return new ResourceSetItemProvider(this) {
				@Override
				public Collection<Resource> getChildren(Object object)
				  {
				    ResourceSet resourceSet = (ResourceSet)object;
				    if (showAllResources) {
				    	return resourceSet.getResources();
				    }
				    else {
						ArrayList<Resource> list = new ArrayList<>();
						list.add(resourceSet.getResources().get(0));
						return list;
				    }
				  }
			};
		}
	}

	private class IDXMIResourceFactoryImpl extends XMIResourceFactoryImpl {
		@Override
		public Resource createResource(URI arg0) {
			IDXMIResource resource = new IDXMIResource(arg0);
			resource.setTrackingModification(true);
			Adapter adapter = new Adapter() {
	
				@Override
				public Notifier getTarget() {
					return null;
				}
	
				@Override
				public boolean isAdapterForType(Object type) {
					return false;
				}
	
				@Override
				public void notifyChanged(Notification notification) {
					if (ExeedEditor.this.getViewer() != null) {
						Display.getDefault().syncExec(() -> ExeedEditor.this.getViewer().refresh());
					}
				}
	
				@Override
				public void setTarget(Notifier newTarget) {
					
				}
				
			};
			
			resource.eAdapters().add(adapter);
			return resource;
		}
	}

	private static class IDXMIResource extends XMIResourceImpl {
		public IDXMIResource(URI uri) {
			super(uri);
		}
		
		@Override
		protected boolean useUUIDs() {
			return true;
		}
	}

	private ExeedItemProviderAdapterFactory exeedItemProviderAdapterFactory = null;
	private ExeedImageTextProvider imageTextProvider = null;

	private boolean showAllResources
		= getPlugin().getPreferenceStore().getBoolean(ExeedPreferencePage.SHOW_ALL_RESOURCES);
	private boolean showReferenceNamesInCreateActions
		= !getPlugin().getPreferenceStore().getBoolean(ExeedPreferencePage.HIDE_REFERENCE_NAMES);;
	private boolean sortProperties
		= !getPlugin().getPreferenceStore().getBoolean(ExeedPreferencePage.KEEP_PROPERTY_DECLARATION_ORDER);

	// This extra field is necessary, as propertySheetPage does not exist anymore since Kepler (4.2)
	private ExeedPropertySheetPage exeedPropertySheetPage;
	private Map<Class<?>, IExeedCustomizer> resourceClassToCustomizerMap;

	@Override
	public Diagnostic analyzeResourceProblems(Resource resource, Exception exception) {
		if (exception != null)
	    {
	      return
	        new BasicDiagnostic
	          (Diagnostic.ERROR,
	           "org.eclipse.epsilon.dt.exeed",
	           0,
	           EcoreEditorPlugin.INSTANCE.getString("_UI_CreateModelError_message", new Object[]{ resource != null ? resource.getURI() : "(unknown)"}),
	           new Object[] { exception });
	    }
		return Diagnostic.OK_INSTANCE;
	}

	protected ExeedPlugin getPlugin() {
		return ExeedPlugin.getDefault();
	}

	public ExeedItemProviderAdapterFactory getItemProviderAdapterFactory() {
		return exeedItemProviderAdapterFactory;
	}

	public void refresh() {
		currentViewer.refresh();
	}

	public ExeedEditor() {
		super();

		loadViewerCustomizers();

		// Clear the adapter factory produced by the superclass and refill it
		// with our own selection of adapter factories
		exeedItemProviderAdapterFactory = new ExeedItemProviderAdapterFactory(getPlugin(), resourceClassToCustomizerMap);
	    adapterFactory = new ComposedAdapterFactory(/*ComposedAdapterFactory.Descriptor.Registry.INSTANCE*/);
	    adapterFactory.addAdapterFactory(new SwitchableResourceSetItemProvider());
		adapterFactory.addAdapterFactory(exeedItemProviderAdapterFactory);

		// Recreate the editing domain with the new adapter factory and our own extension to factory map
		editingDomain = new AdapterFactoryEditingDomain(
			adapterFactory, editingDomain.getCommandStack(), new HashMap<Resource, Boolean>());

		final Map<String, Object> extensionToFactoryMap =
				editingDomain.getResourceSet().getResourceFactoryRegistry().getExtensionToFactoryMap();
		extensionToFactoryMap.put("xml", new GenericXMLResourceFactoryImpl());

		// We shouldn't override the existing "*" default, which is the ECore
		// RegistryReader$ResourceFactoryDescriptor.
		// Otherwise, some models will not be loaded correctly (e.g. Papyrus UML
		// models).
		//
		//extensionToFactoryMap.put("*", new IDXMIResourceFactoryImpl());
		//
		// I will leave "model" as the 'generic' extension for models, though,
		// which is the usual convention followed throughout Epsilon's docs.

		extensionToFactoryMap.put("model", new IDXMIResourceFactoryImpl());
		extensionToFactoryMap.put("bim", new ResourceFactoryImpl() {
			@Override
			public Resource createResource(URI uri) {
				return new BinaryResourceImpl(uri);
			}
		});
		extensionToFactoryMap.put("registered", new RegisteredEPackageResourceFactory());
	}

	protected void loadViewerCustomizers() {
		final String bundleID = FrameworkUtil.getBundle(ExeedEditor.class).getSymbolicName();
		this.resourceClassToCustomizerMap = new HashMap<>();
		for (IConfigurationElement elem : Platform.getExtensionRegistry().getConfigurationElementsFor(VIEWERCUSTOMIZER_EXTPOINT)) {
			try {
				final Class<?> resourceClass = elem.createExecutableExtension(VIEWERCUSTOMIZER_RESOURCECLASS_ATTR).getClass();
				final IExeedCustomizer customizer = (IExeedCustomizer)elem.createExecutableExtension(VIEWERCUSTOMIZER_CUSTOMIZERCLASS_ATTR);
				resourceClassToCustomizerMap.put(resourceClass, customizer);
			} catch (CoreException ex) {
				ExeedPlugin.getDefault().getLog().log(
					new Status(IStatus.ERROR, bundleID, ex.getMessage(), ex)
				);
			}
		}
	}

	@Override
	public ExeedPropertySheetPage getPropertySheetPage() {
		if (exeedPropertySheetPage == null) {
			exeedPropertySheetPage = new ExeedPropertySheetPage(editingDomain, ExeedEditor.this, getPlugin());
			exeedPropertySheetPage.setPropertySourceProvider(new AdapterFactoryContentProvider(adapterFactory));
		}
		return exeedPropertySheetPage;
	}

	@Override
	protected void createContextMenuForGen(StructuredViewer viewer) {
	    MenuManager contextMenu = new MenuManager("#PopUp");
	    contextMenu.add(new Separator("additions"));
	    contextMenu.setRemoveAllWhenShown(true);
	    contextMenu.addMenuListener(this);
	    Menu menu= contextMenu.createContextMenu(viewer.getControl());
	    viewer.getControl().setMenu(menu);
	    getSite().registerContextMenu(contextMenu, new UnwrappingSelectionProvider(viewer));

	    int dndOperations = DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK;
	    final Transfer[] transfers = new Transfer[] { LocalTransfer.getInstance() };
	    viewer.addDragSupport(dndOperations, transfers, new ViewerDragAdapter(viewer));
	    ExeedEditingDomainViewerDropAdapter dropAdapter = new ExeedEditingDomainViewerDropAdapter(editingDomain, viewer, getPlugin());
		viewer.addDropSupport(dndOperations, transfers, dropAdapter);
		disposeListeners.add(dropAdapter);
	}
	
	@Override
	public void dispose() {
		for (DisposeListener listener : disposeListeners) {
			listener.widgetDisposed(null);
		}
	}
	
	protected void registerCustomMetamodels() {
		
	}
	
	@Override
	public void createPages() {
		EmfRegistryManager.getInstance();
		registerCustomMetamodels();

		/*
		 * Taken from EcoreEditor#createModel(): we don't use reflective mode,
		 * and we don't want the last bit that goes through getAllContents() (as
		 * it'd break customizers that hook Exeed into lazy-loading resources).
		 */
		final ResourceSet resourceSet = editingDomain.getResourceSet();
		resourceSet.getURIConverter().getURIMap().putAll(EcorePlugin.computePlatformURIMap(true));
	    resourceSet.getLoadOptions().put(XMLResource.OPTION_DEFER_IDREF_RESOLUTION, Boolean.TRUE);

		createModelGen();

		final Resource mainResource = this.getEditingDomain().getResourceSet().getResources().get(0);
		final IExeedCustomizer customizer = resourceClassToCustomizerMap.get(mainResource.getClass());
		if (customizer != null && customizer.isEnabledFor(mainResource)) {
			customizer.createPages(this, getContainer(), adapterFactory);
		} else {
			super.createPages();
		}

		TreeViewer viewer = (TreeViewer) getViewer();
		viewer.addSelectionChangedListener(event -> event.getSource());

		InMemoryEmfModel model;
		if (customizer != null && customizer.isEnabledFor(mainResource)) {
			model = customizer.createInMemoryEmfModel(mainResource);
		} else {
			model = new InMemoryEmfModel(mainResource);
		}
		model.setCachingEnabled(false);

		imageTextProvider = new ExeedImageTextProvider(model, getPlugin(), this);
		exeedItemProviderAdapterFactory.setImageTextProvider(imageTextProvider);
		((ExeedActionBarContributor) this.getActionBarContributor()).setProvider(imageTextProvider);
		((ExeedActionBarContributor) this.getActionBarContributor()).setCustomizerMap(resourceClassToCustomizerMap);

		// Allow extenders to customize the tree viewer depending on the Resource implementation
	}

	public ExeedImageTextProvider getImageTextProvider() {
		return imageTextProvider;
	}

	public boolean isShowAllResources() {
		return showAllResources;
	}

	public void setShowAllResources(boolean showAllResources) {
		this.showAllResources = showAllResources;
	}

	public boolean isSortProperties() {
		return sortProperties;
	}

	public void setSortProperties(boolean sortProperties) {
		this.sortProperties = sortProperties;
	}

	public boolean isShowReferenceNamesInCreateActions() {
		return showReferenceNamesInCreateActions;
	}

	public void setShowReferenceNamesInCreateActions(boolean showReferenceNamesInCreateActions) {
		this.showReferenceNamesInCreateActions = showReferenceNamesInCreateActions;
	}

	@Override
	public void createContextMenuFor(StructuredViewer viewer) {
		// increase visibility for IViewerCustomizer
		super.createContextMenuFor(viewer);
	}

	@Override
	public void setPageText(int pageIndex, String text) {
		// increase visibility for IViewerCustomizer
		super.setPageText(pageIndex, text);
	}

}
