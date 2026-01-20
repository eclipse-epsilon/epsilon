package org.eclipse.epsilon.examples.egl.strings;

import java.io.File;
import java.net.URI;
import java.util.Map;

import org.eclipse.epsilon.egl.EglFileGeneratingTemplate;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;
import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;

public class StringGeneratingTemplate extends EglFileGeneratingTemplate {
	
	protected Map<String, String> results = null;

	public StringGeneratingTemplate(EglTemplateSpecification spec, IEglContext context, URI outputRoot, Map<String, String> results, String templateCode)
			throws Exception {
		super(new StringGeneratingTemplateSpecification(templateCode), context, outputRoot);
		this.results = results;
	}
	
	@Override
	public File generate(String path, boolean overwrite, boolean merge) throws EglRuntimeException {
		results.put(path, process());
		return null;
	}
	
}