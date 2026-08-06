/*******************************************************************************
 * Copyright (c) 2008-2024 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Antonio Garcia-Dominguez - add import caching via import manager
 ******************************************************************************/
package org.eclipse.epsilon.egl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.epsilon.common.util.UriUtil;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.EglContext;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.execute.control.ITemplateExecutionListener;
import org.eclipse.epsilon.egl.formatter.CompositeFormatter;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.egl.formatter.NullFormatter;
import org.eclipse.epsilon.egl.incremental.IncrementalitySettings;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecificationFactory;
import org.eclipse.epsilon.egl.util.FileUtil;
import org.eclipse.epsilon.eol.IImportManager;
import org.eclipse.epsilon.eol.ImportManager;
import org.eclipse.epsilon.eol.execute.context.IEolContext;
import org.eclipse.epsilon.eol.execute.context.concurrent.IEolContextParallel;

public class EglTemplateFactory {

	protected IEglContext context;
	protected URI root;
	private URI templateRoot;
	private String templateRootPath;
	private Formatter defaultFormatter = new NullFormatter();
	private IncrementalitySettings defaultIncrementalitySettings = new IncrementalitySettings();
	private final Collection<ITemplateExecutionListener> listeners = new LinkedList<>();
	private IImportManager importManager = new ImportManager();

	public EglTemplateFactory() {
		this(null);
	}
	
	public EglTemplateFactory(IEglContext context) {
		this.context = context != null ? context : new EglContext(this);
	}
	
	public Collection<ITemplateExecutionListener> getTemplateExecutionListeners() {
		return this.listeners;
	}
	
	public IncrementalitySettings getDefaultIncrementalitySettings() {
		return this.defaultIncrementalitySettings;
	}
	
	public void setDefaultFormatter(Formatter defaultFormatter) {
		this.defaultFormatter = defaultFormatter;
	}
	
	public void setDefaultFormatters(Formatter... defaultFormatters) {
		setDefaultFormatters(Arrays.asList(defaultFormatters));
	}
	
	public void setDefaultFormatters(Collection<Formatter> defaultFormatters) {
		setDefaultFormatter(new CompositeFormatter(defaultFormatters));
	}
	
	public IEglContext getContext() {
		return context;
	}
	
	public void setContext(IEglContext context) {
		this.context = context;
	}

	public IImportManager getImportManager() {
		return importManager;
	}

	public void setImportManager(IImportManager importManager) {
		this.importManager = importManager;
	}

	/**
	 * Sets the root of this template factory, unless it has already been set.
	 * @param root The new root.
	 * @return <code>true</code> if the root was set as a result of this call,
	 * <code>false</code> if the root had already been initialized.
	 */
	public boolean initialiseRoot(URI root) {
		if (this.root == null) synchronized (this) {
			if (this.root == null) {
				setRoot(root);
				return true;
			}
		}
		return false;
	}
	
	public void setRoot(URI root) {
		this.root = root;
	}
	
	public String getTemplateRoot() {
		return templateRootPath;
	}
	
	public void setTemplateRoot(String path) throws EglRuntimeException {
		templateRoot = resolveRoot(templateRootPath = path);
	}
	
	protected URI resolveRoot(String path) throws EglRuntimeException {
		try {
			return UriUtil.resolve(UriUtil.encode(path, true), root);

		} catch (URISyntaxException e) {
			throw new EglRuntimeException("Could not resolve path: "+path, e, getContext().getModule());
		}
	}
	
	public URI resolveTemplate(String path) throws EglRuntimeException {
		try {
			return UriUtil.resolve(UriUtil.encode(path, false), templateRoot, root);
			
		} catch (URISyntaxException e) {
			throw new EglRuntimeException("Could not resolve path: "+path, e, getContext().getModule());
		}
	}
	
	protected String name(String path) {
		String name = path;
		 if (templateRootPath != null)
			 name = new File(templateRootPath).getPath() + FileUtil.FILE_SEP + name;
		 return name;
	}
	
	/**
	 * Loads an EglTemplate for the EGL code stored in the indicated file.
	 * 
	 * Subclasses should override {@link #createTemplate(String, URI)}, rather
	 * than this method, unless they wish to alter the way in which a file is
	 * transformed into an EglTemplateSpecification
	 */
	public EglTemplate load(File file) throws EglRuntimeException {
		final String name = name(file.getAbsolutePath());
		try {
			return load(createTemplateSpecificationFactory().fromResource(name, UriUtil.fileToUri(file)));
		} catch (URISyntaxException e) {
			return handleFailedLoad(name, e);
		}
	}
	
	/**
	 * Loads an EglTemplate for the given EGL code as though it were
	 * contained in the given File. Used for parsing "dirty" code (which 
	 * has not yet been saved to disk).
	 * 
	 * Subclasses should override {@link #createTemplate(String, URI)}, rather
	 * than this method, unless they wish to alter the way in which a dirty 
	 * resource is transformed into an EglTemplateSpecification
	 */
	protected EglTemplate load(String code, File file) throws EglRuntimeException {
		final String name = name(file.getAbsolutePath());
		try {
			return load(createTemplateSpecificationFactory().fromDirtyResource(name, code, UriUtil.fileToUri(file)));
		} catch (URISyntaxException e) {
			return handleFailedLoad(name, e);
		}			
	}

	/**
	 * Loads an EglTemplate for the EGL code stored in the file at path.
	 * 
	 * Subclasses should override {@link #createTemplate(String, URI)}, rather
	 * than this method, unless they wish to alter the way in which a path is
	 * transformed into an EglTemplateSpecification
	 */
	public EglTemplate load(String path) throws EglRuntimeException {
		return load(createTemplateSpecificationFactory().fromResource(name(path), resolveTemplate(path)));
	}
	
	/**
	 * Loads an EglTemplate for the EGL code stored in the given resource.
	 * 
	 * Subclasses should override {@link #createTemplate(String, URI)}, rather
	 * than this method, unless they wish to alter the way in which a resource is
	 * transformed into an EglTemplateSpecification
	 */
	public EglTemplate load(URI resource) throws EglRuntimeException {
		final String name = resource.toString(); // FIXME better name for URIs
		return load(createTemplateSpecificationFactory().fromResource(name, resource));
	}
	
	/**
	 * Loads an EglTemplate for the given EGL code as though it were
	 * contained in the given URI. Used for parsing "dirty" code (which 
	 * has not yet been saved to disk).
	 * 
	 * Subclasses should override {@link #createTemplate(String, URI)}, rather
	 * than this method, unless they wish to alter the way in which a dirty 
	 * resource is transformed into an EglTemplateSpecification
	 */
	protected EglTemplate load(String code, URI resource) throws EglRuntimeException {
		final String name = resource.toString(); // FIXME better name for URIs
		return load(createTemplateSpecificationFactory().fromDirtyResource(name, code, resource));
	}

	/**
	 * Loads an EglTemplate from the given EglTemplateSpecification.
	 * 
	 * Subclasses should override {@link #createTemplate(String, URI)}, rather
	 * than this method, unless they wish to alter the way in which IOExceptions
	 * are handled, in which case they should override {@link #handleFailedLoad(String, Exception)}.
	 */
	protected final EglTemplate load(EglTemplateSpecification spec) throws EglRuntimeException {
		try {
			initialiseRoot(spec.getURI());
			return createTemplate(spec);
			
		} catch (Exception e) {
			return handleFailedLoad(spec.getName(), e);
		}
	}
	
	protected EglTemplate handleFailedLoad(final String name, Exception e) throws EglRuntimeException {
		final String reason = e instanceof FileNotFoundException ? "Template not found" : "Could not process";
		throw new EglRuntimeException(reason + " '" + name + "'", e, getContext().getModule());
	}
	
	/**
	 * Prepares an EGL template that will execute the given
	 * EGL source code. Subclasses should override 
	 * {@link #createTemplate(String)}, rather than this method,
	 * as this method may, in the future, acquire additional
	 * responsibilities, such as exception handling.
	 */
	public final EglTemplate prepare(String code) throws Exception {
		return createTemplate(createTemplateSpecificationFactory().fromCode(code));
	}

	/**
	 * Creates a template from the given specification.
	 * Subclasses may override to create different types of template.
	 */
	protected EglTemplate createTemplate(EglTemplateSpecification spec) throws Exception {
		return new EglTemplate(spec, getContextForNewTemplate());
	}
	
	public void copyState(IEolContext delegate) {
		context.setDelegate(delegate);
	}
	
	/**
	 * This method should be called when creating a new template from {@link #createTemplate(EglTemplateSpecification)}.
	 * The rationale is that in some cases this factory's context is not safe to be used directly, so
	 * a proxy or modifications may be needed instead.
	 * 
	 * @return An appropriate context to be used for a new EglTemplate instance.
	 * @since 1.6
	 */
	protected IEglContext getContextForNewTemplate() {
		if (context.getDelegate() instanceof IEolContextParallel) {
			return new EglContext(context);
		}
		else {
			// Refresh the output stream of the context etc.
			// in case they have changed in the delegate
			getContext().setDelegate(getContext().getDelegate());
			return getContext();
		}
	}
	
	private EglTemplateSpecificationFactory createTemplateSpecificationFactory() {
		return new EglTemplateSpecificationFactory(
			defaultFormatter, defaultIncrementalitySettings, importManager,
			listeners.toArray(new ITemplateExecutionListener[listeners.size()])
		);
	}
		
	@Override
	public String toString() {
		final String root = templateRoot == null ? "" : templateRoot.toString();
		return getClass().getSimpleName()+": root='" + root + "'";
	}
}
