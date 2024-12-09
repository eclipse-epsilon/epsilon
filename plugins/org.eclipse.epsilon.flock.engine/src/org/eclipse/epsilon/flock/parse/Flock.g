/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * -----------------------------------------------------------------------------
 * ANTLR 3 License
 * [The "BSD licence"]
 * Copyright (c) 2005-2008 Terence Parr
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
grammar Flock;

options {backtrack=true; output=AST; ASTLabelType='org.eclipse.epsilon.common.parse.AST'; superClass='org.eclipse.epsilon.common.parse.EpsilonParser';}

import EolLexerRules, EolParserRules, ErlParserRules;

tokens {
  FLOCKMODULE; RETYPE; RETYPEPACKAGE; DELETE; DELETEPACKAGE; MIGRATE; GUARD; IGNORING;
}

@header {
package org.eclipse.epsilon.flock.parse;

import java.net.URI;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.AST;
}

@lexer::header {
/*******************************************************************************
 * Copyright (c) 2009 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Louis Rose - initial API and implementation
 * -----------------------------------------------------------------------------
 * ANTLR 3 License
 * [The "BSD licence"]
 * Copyright (c) 2005-2008 Terence Parr
 * All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package org.eclipse.epsilon.flock.parse;

import org.eclipse.epsilon.common.parse.Lexer;
}

@parser::members {
	@Override
	public void prepareForGUnit() {
		super.prepareForGUnit();
		this.setDeepTreeAdaptor(new EpsilonTreeAdaptor((URI) null) {
			@Override
		    public Object errorNode(TokenStream arg0, Token token, Token arg2,
		    		RecognitionException arg3) {
		    	AST ast = new AST(arg2, uri);
		    	return ast;
		    }
		});
		gEolParserRules.prepareForGUnit();
	}
}


flockModule
	:	importStatement* modelDeclaration* (flockModuleContent)* EOF
	-> ^(FLOCKMODULE importStatement* modelDeclaration* flockModuleContent*)
	;

flockModuleContent
	:	retyping | deletion | migrateRule | erlModuleContent
	;

retyping
	:	retyping_package | retyping_classifier;

retyping_package
	: rt='retype'^ 'package'! originalPackage=NAME 'to'! migratedPackage=NAME guard?
    {$rt.setType(RETYPEPACKAGE); $rt.setText("RETYPEPACKAGE");}
    ;
 
retyping_classifier
	: rt='retype'^ originalType=packagedType 'to'! migratedType=packagedType guard?
    {$rt.setType(RETYPE); $rt.setText("RETYPE");}
    ;

deletion
	:	deletion_package | deletion_classifier;

deletion_package
  : del='delete'^ 'package'! pkg=NAME guard?
  	{$del.setType(DELETEPACKAGE); $del.setText("DELETEPACKAGE");}
    ;

deletion_classifier
  : del='delete'^ type=packagedType guard?
  	{$del.setType(DELETE); $del.setText("DELETE");}
    ;


migrateRule
  : fullRule | ignoringRule;
  
fullRule
	@after {
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	} 
  : mig='migrate'^ originalType=packagedType ignoring? guard? ob='{'! body=block cb='}'!
    {$mig.setType(MIGRATE); $mig.setText("MIGRATE");}
    ;
    
ignoringRule
  : mig='migrate'^ originalType=packagedType ignoring guard?
    {$mig.setType(MIGRATE); $mig.setText("MIGRATE");}
    ;

ignoring
  : ign='ignoring'^ propertyList
  	{$ign.setType(IGNORING); $ign.setText("IGNORING");}
  ;
  
propertyList
  : NAME (',' NAME)*
  ->
  NAME*;

guard
  : w='when'^ expressionOrStatementBlock
    {$w.setType(GUARD);  $w.setText("GUARD");}
    ;