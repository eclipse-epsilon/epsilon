package org.eclipse.epsilon.examples.egl.strings;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.Map;

import org.eclipse.epsilon.egl.EglTemplate;
import org.eclipse.epsilon.egl.EglTemplateFactory;
import org.eclipse.epsilon.egl.exceptions.EglRuntimeException;

public class StringGeneratingTemplateFactory extends EglTemplateFactory {

	protected Map<String, String> results = new LinkedHashMap<>();
	protected Map<String, String> templates = new LinkedHashMap<>();
	
	public StringGeneratingTemplateFactory(Map<String, String> templates) {
		this.templates = templates;
	}

	@Override
	public EglTemplate load(URI resource) throws EglRuntimeException {
		try {
			for (String key : templates.keySet()) {
				if (resource.toString().endsWith(key)) {
					String templateCode = templates.get(key);
					return new StringGeneratingTemplate(new StringGeneratingTemplateSpecification(templateCode), context, resource, results, templateCode);
				}
			}
		} catch (Exception e) {
			throw new EglRuntimeException(e);
		}
		throw new EglRuntimeException(new RuntimeException("URI " + resource + " not found"));
	}
	
	public Map<String, String> getResults() {
		return results;
	}

	
}