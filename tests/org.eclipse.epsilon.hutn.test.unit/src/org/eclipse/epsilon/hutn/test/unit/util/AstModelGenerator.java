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
 * $Id: AstModelGenerator.java,v 1.3 2008/08/08 17:19:01 louis Exp $
 */
package org.eclipse.epsilon.hutn.test.unit.util;

import java.io.IOException;
import java.io.StringReader;

import org.antlr.runtime.ANTLRReaderStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;
import org.eclipse.epsilon.common.parse.AST;
import org.eclipse.epsilon.emc.emf.InMemoryEmfModel;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.hutn.parse.HutnLexer;
import org.eclipse.epsilon.hutn.parse.HutnParser;
import org.eclipse.epsilon.hutn.parse.postprocessor.HutnPostProcessor;


public class AstModelGenerator {

	public static IModel generate(String spec) {
		try {
			final HutnLexer  lexer  = new HutnLexer(new ANTLRReaderStream(new StringReader(spec)));
			final HutnParser parser = new HutnParser(new CommonTokenStream(lexer));
			
			final AST ast = new AST((CommonTree)parser.document().getTree());

			return new InMemoryEmfModel(new HutnPostProcessor().process(ast).eResource());
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RecognitionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		final String hutn = "@Spec {"                                  +
        "	MetaModel \"FamiliesMetaModel\" {"     +
        "		nsUri = \"families\""              +
        "	}"                                     +
        "}"                                        +
        "Families {"                               +
        "	Dog \"Fido\" {"                        +
        "		friend: Dog \"Rover\""                   +
        "		breed: labrador"                   +
        "	}"                                     +
        "}";
		
		generate(hutn).store("Ast.model");
	}
}
