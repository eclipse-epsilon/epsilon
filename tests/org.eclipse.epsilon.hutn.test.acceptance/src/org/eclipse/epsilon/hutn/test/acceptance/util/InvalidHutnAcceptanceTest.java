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
 * $Id: InvalidHutnAcceptanceTest.java,v 1.2 2008/08/07 12:44:24 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.acceptance.util;

import java.util.List;

import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.hutn.HutnModule;
import org.eclipse.epsilon.hutn.IHutnModule;

public abstract class InvalidHutnAcceptanceTest extends HutnTestWithFamiliesMetaModel {

	protected static List<ParseProblem> parse(String hutn) throws Exception {
		final IHutnModule module = new HutnModule();
		
		module.parse(hutn);
		
		return module.getParseProblems();
	}
}
