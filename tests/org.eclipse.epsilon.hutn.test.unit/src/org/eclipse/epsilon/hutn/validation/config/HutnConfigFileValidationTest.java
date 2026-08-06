/**
 * *******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * ******************************************************************************
 *
 * $Id: HutnConfigFileValidationTest.java,v 1.3 2008/10/09 12:11:09 louis Exp $
 */
package org.eclipse.epsilon.hutn.validation.config;

import java.util.List;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.hutn.model.config.hutnConfig.Configuration;
import org.eclipse.epsilon.hutn.model.config.hutnConfig.DefaultValueRule;
import org.eclipse.epsilon.hutn.model.config.hutnConfig.HutnConfigFactory;
import org.eclipse.epsilon.hutn.model.config.hutnConfig.HutnConfigPackage;
import org.eclipse.epsilon.hutn.model.config.hutnConfig.Rule;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;

public abstract class HutnConfigFileValidationTest extends HutnTestWithFamiliesMetaModel {

	public static List<ParseProblem> configFileValidationTest(Configuration configuration) throws Exception {
		EmfModel model = null;
		
		try {
			model = new InMemoryEmfModel("Config", EmfUtil.createResource(configuration));
			model.setMetamodelUri(HutnConfigPackage.eNS_URI);
		
			final HutnConfigFileValidator validator = new HutnConfigFileValidator();
			return validator.getProblemsForConfigurationModel(model, "families");
			
		} finally {
			if (model != null) model.dispose();
		}
	}
	
	public static Configuration createConfiguration(Rule... rules) {
		final Configuration config = HutnConfigFactory.eINSTANCE.createConfiguration();
		EmfUtil.createResource(config);
		
		for (Rule rule : rules) {
			config.getRules().add(rule);
		}
		
		return config;
	}
	
	private static Rule setAttribute(Rule rule, String classifier, String attribute) {
		rule.setClassifier(classifier);
		rule.setAttribute(attribute);
		return rule;
	}
	
	public static Rule createIdentifierRule(String classifier, String attribute) {
		return setAttribute(HutnConfigFactory.eINSTANCE.createIdentifierRule(), classifier, attribute);
	}
	
	public static Rule createDefaultValueRule(String classifier, String attribute, String defaultValue) {
		final DefaultValueRule rule = HutnConfigFactory.eINSTANCE.createDefaultValueRule();
		
		rule.setDefaultValue(defaultValue);
		
		return setAttribute(rule, classifier, attribute);
	}
}
