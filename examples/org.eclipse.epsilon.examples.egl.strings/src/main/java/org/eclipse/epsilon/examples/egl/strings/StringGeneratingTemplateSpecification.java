package org.eclipse.epsilon.examples.egl.strings;

import java.io.File;
import java.net.URI;
import java.util.Collections;

import org.eclipse.epsilon.egl.formatter.NullFormatter;
import org.eclipse.epsilon.egl.incremental.IncrementalitySettings;
import org.eclipse.epsilon.egl.internal.IEglModule;
import org.eclipse.epsilon.egl.spec.EglTemplateSpecification;
import org.eclipse.epsilon.egl.traceability.Template;

public class StringGeneratingTemplateSpecification extends EglTemplateSpecification {

	private final String code;
	
	protected StringGeneratingTemplateSpecification(String code) {
		super("Anonymous", new NullFormatter(), new IncrementalitySettings(), Collections.emptyList());	
		this.code = code;
	}
	
	@Override
	public Template createTemplate() {
		return new Template();
	}
	
	@Override
	public void parseInto(IEglModule module) throws Exception {
		module.parse(code, new File("/template.egl"));
	}

	@Override
	public URI getURI() {
		return null;
	}
}