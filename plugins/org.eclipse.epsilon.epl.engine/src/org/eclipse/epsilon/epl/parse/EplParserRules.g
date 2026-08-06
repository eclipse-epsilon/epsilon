/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
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
parser grammar EplParserRules;
options {backtrack=true; output=AST;}

tokens {
	PATTERN;
	CARDINALITY;
	DOMAIN;
	ROLE;
	MATCH;
	NOMATCH;
	ONMATCH;
	DO;
	ALIAS;
	NO;
	OPTIONAL;
	ACTIVE;
}

@members {

public void setTokenType(ParserRuleReturnScope tree, int type) {
	((CommonTree) tree.getTree()).getToken().setType(type);
}

}

eplModuleContent
	:	pattern | erlModuleContent
	;

pattern
	@after {
		$tree.getExtraTokens().add($pt);
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	}
	:
	pt='pattern'^ NAME role (','! role)* (ob='{'! (match | do_ | nomatch | onmatch)* cb='}'!)?
	{$pt.setType(PATTERN);}
	;

role
	: no? NAME (','! NAME)* n=':'^ t=typeName {setTokenType(t, TYPE);} cardinality? (domain | guard | optional | active)*
	{$n.setType(ROLE);}
	;

no : n='no' {$n.setType(NO);};

cardinality
	@after {
		$tree.getExtraTokens().add($cb);
	}
	: c='['^ bound ('..'! bound)? cb=']'!
	{$c.setType(CARDINALITY);}
	;

bound
	: INT | '*'
	;

domain :
	(c='in'^|c='from'^) expressionOrStatementBlock
	{$c.setType(DOMAIN);}
	;

match :
	c='match'^ expressionOrStatementBlock
	{$c.setType(MATCH);}
	;

optional :
	c='optional'^ expressionOrStatementBlock
	{$c.setType(OPTIONAL);}
	;

active :
	c='active'^ expressionOrStatementBlock
	{$c.setType(ACTIVE);}
	;

do_ :
	c='do'^ expressionOrStatementBlock
	{$c.setType(DO);}
	;

onmatch :
	c='onmatch'^ expressionOrStatementBlock
	{$c.setType(ONMATCH);}
	;

nomatch :
	c='nomatch'^ expressionOrStatementBlock
	{$c.setType(NOMATCH);}
	;
