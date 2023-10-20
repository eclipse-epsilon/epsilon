/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.flexmi.dt;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.epsilon.common.dt.util.LogUtil;
import org.eclipse.epsilon.common.dt.util.ThemeChangeListener;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.flexmi.EObjectLocation;
import org.eclipse.epsilon.flexmi.FlexmiParseException;
import org.eclipse.epsilon.flexmi.FlexmiResource;
import org.eclipse.epsilon.flexmi.FlexmiResourceFactory;
import org.eclipse.epsilon.flexmi.dt.xml.XMLConfiguration;
import org.eclipse.epsilon.flexmi.dt.xml.XMLDocumentProvider;
import org.eclipse.epsilon.flexmi.dt.yaml.YamlConfiguration;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.jface.text.source.ICharacterPairMatcher;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.FileDocumentProvider;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.MarkerUtilities;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

public class FlexmiEditor extends TextEditor {

	private FlexmiHighlightingManager highlightingManager;
	protected Job parseResourceJob = null;
	protected FlexmiContentOutlinePage outlinePage = null;
	protected FlexmiResource resource = null;
	protected IFile file;
	protected FlexmiFlavour flexmiFlavour = FlexmiFlavour.XML;
	
	public FlexmiEditor() {
		super();
		setEditorContextMenuId("#TextEditorContext");
	    setRulerContextMenuId("editor.rulerMenu");
		highlightingManager = new FlexmiHighlightingManager();
		highlightingManager.initialiseDefaultColors();
		setSourceViewerConfiguration(createSourceViewerConfiguration(highlightingManager));
		setDocumentProvider(createDocumentProvider());
	}

	public IFile getFile() {
		return file;
	}
	
	@Override
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		super.init(site, input);
		
		outlinePage = new FlexmiContentOutlinePage(this);
		
		final int delay = 1000;
		
		parseResourceJob = new Job("Parsing module") {
			
			protected int status = -1;
			
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				
				if (!isClosed()) {
					int textHashCode = getText().hashCode();
					if (status != textHashCode) {
						
						parseResource();
						status = textHashCode;
					}
					
					this.schedule(delay);
				}
				
				return Status.OK_STATUS;
			}
		};
		
		parseResourceJob.setSystem(true);
		parseResourceJob.schedule(delay);
		
		PlatformUI.getWorkbench().getThemeManager().addPropertyChangeListener(new ThemeChangeListener() {
			@Override
			public void themeChange() {
				highlightingManager.initialiseDefaultColors();
				refreshText();
			}
		});
		highlightingManager.getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent event) {
				if (highlightingManager.isColorPreference(event.getProperty())) {
					refreshText();
				}
			}
		});
	}
	
	public void refreshText() {
		ISourceViewer viewer= getSourceViewer();
		setSourceViewerConfiguration(createSourceViewerConfiguration(highlightingManager));
		viewer.configure(getSourceViewerConfiguration());
	}
	
	@Override
	protected boolean isTabsToSpacesConversionEnabled() {
		if (flexmiFlavour == FlexmiFlavour.YAML) return true;
		else return super.isTabsToSpacesConversionEnabled();
	}
	
	@Override
	protected boolean isSpacesAsTabsDeletionEnabled() {
		if (flexmiFlavour == FlexmiFlavour.YAML) return true;
		else return super.isSpacesAsTabsDeletionEnabled();
	}
	
	protected SourceViewerConfiguration createSourceViewerConfiguration(FlexmiHighlightingManager highlightingManager) {
		if (flexmiFlavour == FlexmiFlavour.XML) return new XMLConfiguration(highlightingManager);
		else return new YamlConfiguration(highlightingManager);
	}
	
	public final static String EDITOR_MATCHING_BRACKETS = "matchingBrackets";
	public final static String EDITOR_MATCHING_BRACKETS_COLOR= "matchingBracketsColor";
	
	@Override
	protected void configureSourceViewerDecorationSupport(
			SourceViewerDecorationSupport support) {

		super.configureSourceViewerDecorationSupport(support);
		char[] matchChars = {'[', ']', '{', '}'}; 		
		ICharacterPairMatcher matcher = new DefaultCharacterPairMatcher(matchChars ,
				IDocumentExtension3.DEFAULT_PARTITIONING);
		support.setCharacterPairMatcher(matcher);
		support.setMatchingCharacterPainterPreferenceKeys(EDITOR_MATCHING_BRACKETS,EDITOR_MATCHING_BRACKETS_COLOR);
		
		IPreferenceStore store = getPreferenceStore();
		store.setDefault(EDITOR_MATCHING_BRACKETS, true);
		store.setDefault(EDITOR_MATCHING_BRACKETS_COLOR, "128,128,128");
	}
	
	private IDocumentProvider createDocumentProvider() {
		if (flexmiFlavour == FlexmiFlavour.XML) return new XMLDocumentProvider();
		else return new FileDocumentProvider();
	}
	
	@Override
	protected void doSetSelection(ISelection selection) {
		super.doSetSelection(selection);
	}
	
	/*
	@Override
	protected boolean isTabsToSpacesConversionEnabled() {
		return super.isTabsToSpacesConversionEnabled() || flexmiFlavour == FlexmiFlavour.YAML;
	}*/
	
	public void parseResource() {
		
		// Return early if the file is opened in an unexpected editor (e.g. in a Subclipse RemoteFileEditor)
		if (!(getEditorInput() instanceof FileEditorInput)) return;
		
		FileEditorInput fileInputEditor = (FileEditorInput) getEditorInput();
		file = fileInputEditor.getFile();
		
		final IDocument doc = this.getDocumentProvider().getDocument(
				this.getEditorInput());
		
		// Replace tabs with spaces to match
		// column numbers produced by the parser
		String code = doc.get();
		
		// Detect the flavour of the Flexmi document and refresh the configuration accordingly
		FlexmiFlavour detectedFlavour = FlexmiResource.isXml(new BufferedInputStream(new ByteArrayInputStream(code.getBytes()))) ? FlexmiFlavour.XML : FlexmiFlavour.YAML;
		if ((detectedFlavour != flexmiFlavour)) {
			flexmiFlavour = detectedFlavour;
			handlePreferenceStoreChanged(new PropertyChangeEvent(doc, AbstractDecoratedTextEditorPreferenceConstants.EDITOR_SPACES_FOR_TABS, null, null));
			Display.getDefault().asyncExec(() -> refreshText());
		}
		
		FlexmiParseException parseException = null;
		
		ResourceSet resourceSet = new ResourceSetImpl();
		
		try {
			resourceSet.getPackageRegistry().put(EcorePackage.eINSTANCE.getNsURI(), EcorePackage.eINSTANCE);
			resource = (FlexmiResource) resourceSet.createResource(URI.createFileURI(file.getLocation().toOSString()));
			resource.load(new ByteArrayInputStream(code.getBytes()), null);
		}
		catch (Exception ex) {
			if (ex instanceof FlexmiParseException) {
				parseException = (FlexmiParseException) ex;
			}
			else {
				LogUtil.log(ex);
			}
		}
		
		final String markerType = "org.eclipse.epsilon.flexmi.dt.problemmarker";
		
		// Update problem markers
		try {
			
			List<FlexmiResource> flexmiResources = new ArrayList<FlexmiResource>();
			for (Resource resourceSetResource : resource.getResourceSet().getResources()) {
				if (resourceSetResource instanceof FlexmiResource) flexmiResources.add((FlexmiResource) resourceSetResource);
			}
			
			for (FlexmiResource flexmiResource : flexmiResources) {
				// Delete existing markers from all imported Flexmi resources
				try {
					for (IFile flexmiFile : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new java.net.URI(flexmiResource.getURI().toString()))) {
						flexmiFile.deleteMarkers(markerType, true, IResource.DEPTH_INFINITE);
					}
				} catch (URISyntaxException e) {}		
			
				// Delete existing markers from all included Flexmi resources
				for (URI uri : flexmiResource.getParsedFragmentURIs()) {
					try {
						for (IFile parsedFragmentFile : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new java.net.URI(uri.toString()))) {
							parsedFragmentFile.deleteMarkers(markerType, true, IResource.DEPTH_INFINITE);
						}
					}
					catch (Exception ex) {}
				}
			}
			
			if (parseException != null) {
				createMarker(parseException.getMessage(), parseException.getLineNumber(), true, file, markerType);
			}
			else {
				
				for (FlexmiResource flexmiResource : flexmiResources) {
					
					// Create warnings for e.g. unresolvable references, incompatible attribute types
					for (Resource.Diagnostic warning : flexmiResource.getWarnings()) {
						try {
							for (IFile markerFile : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new java.net.URI(warning.getLocation()))) {
								createMarker(warning.getMessage(), warning.getLine(), false, markerFile, markerType);
							}
						} catch (URISyntaxException e) {}
					}
					
					// Create markers for metamodel conformance issues
					for (Diagnostic diagnostic : EmfUtil.validate(flexmiResource).getChildren()) {
						
						if (diagnostic.getSeverity() == Diagnostic.INFO) continue;
						
						for (Object data : diagnostic.getData()) {
							if (data instanceof EObject) {
								EObjectLocation location = flexmiResource.getEObjectTraceManager().getLine((EObject) data);
								if (location != null) {
									try {
										for (IFile markerFile : ResourcesPlugin.getWorkspace().getRoot().findFilesForLocationURI(new java.net.URI(location.getUri().toString()))) {
											createMarker(diagnostic.getMessage(), location.getLine(), false /*diagnostic.getSeverity() == Diagnostic.ERROR*/, markerFile, markerType);
										}
									} catch (URISyntaxException e) {}
								}
							}
						}
					}
				}
				outlinePage.setResourceSet(resourceSet);
			}
			
		} catch (CoreException e1) {
			e1.printStackTrace();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			return outlinePage;
		}
		return super.getAdapter(required);
	}
	
	protected void createMarker(String message, int lineNumber, boolean error, IFile file, String markerType) throws CoreException {
		Map<String, Object> attr = new HashMap<>();
		attr.put(IMarker.LINE_NUMBER, lineNumber);
		attr.put(IMarker.MESSAGE, message);				
		int markerSeverity = IMarker.SEVERITY_WARNING;
		if (error) markerSeverity = IMarker.SEVERITY_ERROR;
		attr.put(IMarker.SEVERITY, markerSeverity);
		MarkerUtilities.createMarker(file, attr, markerType);
	}
	
	public boolean isClosed() {
		return this.getDocumentProvider() == null;
	}
	
	public String getText() {
		return this.getDocumentProvider().getDocument(
				this.getEditorInput()).get();
	}
	
	public FlexmiResource getResource() {
		return resource;
	}
	
	@Override
	public void dispose() {
		super.dispose();
	}
	
	
	
}
