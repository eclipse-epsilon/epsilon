/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.hutn.validation.config;

import java.util.List;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.EmfMetaModel;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.hutn.exceptions.HutnMetaModelRegistrationException;
import org.eclipse.epsilon.hutn.exceptions.HutnValidationException;
import org.eclipse.epsilon.hutn.model.config.hutnConfig.HutnConfigPackage;
import org.eclipse.epsilon.hutn.util.EmcUtil;
import org.eclipse.epsilon.hutn.validation.AbstractValidator;

public class HutnConfigFileValidator extends AbstractValidator {

	// For unit tests
	HutnConfigFileValidator() throws HutnValidationException {
		this(1,1);
	}
	
	public HutnConfigFileValidator(int line, int column) throws HutnValidationException {
		super(new HutnConfigFileFixer(line, column), "ValidateConfigurationModel.evl");
	}
	
	private static void registerMetaModels() throws HutnMetaModelRegistrationException {
		EmcUtil.register(HutnConfigPackage.eNS_URI, HutnConfigPackage.eINSTANCE);
	}
	
	public List<ParseProblem> getProblemsForConfigurationModel(IModel configModel, String metaModelNsUri) throws HutnValidationException {
		try {
			registerMetaModels();
			
			configModel.load();
			
			return validate(configModel, new EmfMetaModel("MetaModel", metaModelNsUri));
		
		} catch (EolModelLoadingException e) {
			throw new HutnValidationException(e);
			
		} catch (HutnMetaModelRegistrationException e) {
			throw new HutnValidationException(e);
		
		} finally {
			configModel.dispose();
		}
	}
}
