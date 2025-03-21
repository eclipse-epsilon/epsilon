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
 * $Id: IntermediateModelGenerator.java,v 1.2 2008/08/07 12:44:20 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.unit.util;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.common.parse.problem.ParseProblem;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.hutn.parse.HutnLexer;
import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.eclipse.epsilon.hutn.parse.postprocessor.HutnPostProcessor;
import org.eclipse.epsilon.hutn.test.model.HutnTestWithFamiliesMetaModel;
import org.eclipse.epsilon.hutn.translate.HutnTranslator;

public class IntermediateModelGenerator {

	public static IModel generate(String spec) throws Exception {
		try {
			final HutnLexer  lexer  = new HutnLexer(new ANTLRReaderStream(new StringReader(spec)));
			final HutnParser parser = new HutnParser(new CommonTokenStream(lexer));
			
			parser.getParseProblems().addAll(lexer.getParseProblems());
			final AST ast = new AST((CommonTree)parser.document().getTree());

			if (parser.getParseProblems().isEmpty()) {
				return new InMemoryEmfModel(new HutnTranslator().createIntermediateModel(new HutnPostProcessor().process(ast), null).eResource());
			
			} else {
				for (ParseProblem p : parser.getParseProblems()) {
					System.err.println(p);
				}
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception {
		generate("@Spec {"                                                                             +
                "	MetaModel \"FamiliesMetaModel\" {"                                                +
                "		nsUri = \"families\""                                                         +
                "	}"                                                                                +
                "}"                                                                                   +
                "Families {"                                                                          +
                "	Person \"John\" {"                                                                +
                "		accounts: Account \"" + HutnTestWithFamiliesMetaModel.BANK_ACCOUNTS_MODEL_URI + "#//@accounts.0\","             +
                "                 Account \"" + HutnTestWithFamiliesMetaModel.BANK_ACCOUNTS_MODEL_URI + "#_swAAYJX5Ed2TbbKclPHPaA\""  +
                "	}"                                                                                +
                "}").store("Intermediate.model");
	}
}
