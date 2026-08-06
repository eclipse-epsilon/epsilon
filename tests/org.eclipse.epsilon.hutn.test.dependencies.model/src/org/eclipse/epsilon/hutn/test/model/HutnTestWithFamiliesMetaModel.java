/**
 * *******************************************************************************
 * Copyright (c) 2008-2020 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 *     Sina Madani - stateless refactoring (works in maven / JAR)
 * ******************************************************************************
 *
 */
package org.eclipse.epsilon.hutn.test.model;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.epsilon.common.util.FileUtil;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.hutn.test.model.bankAccounts.BankAccountsPackage;
import org.eclipse.epsilon.hutn.test.model.families.FamiliesPackage;
import org.eclipse.epsilon.hutn.test.models.BankAccounts;
import org.eclipse.epsilon.hutn.test.models.Families;
import org.junit.AfterClass;
import org.junit.BeforeClass;

public class HutnTestWithFamiliesMetaModel {
	
	protected static final String CONFIG_FILE;
	
	public static final String BANK_ACCOUNTS_MODEL_URI;
	
	static {
		final String configModel = "FamiliesConfig.model";
		String bankURI = null, configTemp = null;
		try {
			bankURI = Families.getBankAccountsModelUri().toString();
			configTemp = FileUtil.getFileStandalone(configModel, Families.class).getAbsolutePath();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		if (configTemp == null) {
			configTemp = "../org.eclipse.epsilon.hutn.test.dependencies.model/" +
				"models/org/eclipse/epsilon/hutn/test/models/"+configModel;
		}
		
		CONFIG_FILE = configTemp.replace('\\', '/');
		BANK_ACCOUNTS_MODEL_URI = bankURI;
	}
	
	@BeforeClass
	public static void registerMetaModels() throws Exception {
		EmfUtil.register(Families.getMetaModelUri(), EPackage.Registry.INSTANCE);
		EmfUtil.register(BankAccounts.getMetaModelUri(), EPackage.Registry.INSTANCE);
	}
	
	@AfterClass
	public static void unregisterMetaModels() throws Exception {
		EPackage.Registry.INSTANCE.remove(FamiliesPackage.eNS_URI);
		EPackage.Registry.INSTANCE.remove(BankAccountsPackage.eNS_URI);
	}

	protected static String families(String body) {
		return "@Spec {"                               + '\n' +
	           "	MetaModel \"FamiliesMetaModel\" {" + '\n' +
	           "		nsUri = \"families\""          + '\n' +
	           "	}"                                 + '\n' +
	           "}"                                     + '\n' +
	           ""                                      + '\n' +
	           "families {"                            + '\n' +
	           body                                    + '\n' +
	           "}";
	}
}
