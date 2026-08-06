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
parser grammar EvlParserRules;
options {backtrack=true; output=AST;}

tokens {
	CONSTRAINT;
	CRITIQUE;
	CONTEXT;
	FIX;
	CHECK;
	DO;
	TITLE;
	MESSAGE;
}

evlModuleContent
	:	context | operationDeclaration | constraint | critique | erlModuleContent
	;

context
	@after {
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	}
	:
	c='context'^ typeName ob='{'! guard? contextContent* cb='}'!
	{$c.setType(CONTEXT);}
	;

contextContent
	:	constraint|critique|annotationBlock
	;

constraint
	@after {
		$tree.getExtraTokens().add($ct);
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	}
	:
	ct='constraint'^ NAME ob='{'! guard? check message? (fix)* cb='}'!
	{$ct.setType(CONSTRAINT);}
	;

critique
	@after {
		$tree.getExtraTokens().add($cr);
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	}
	:
	cr='critique'^ NAME ob='{'! guard? check message? (fix)* cb='}'!
	{$cr.setType(CRITIQUE);}
	;

check
	: c='check'^ expressionOrStatementBlock
	{$c.setType(CHECK);}
	;

message
	: m='message'^ expressionOrStatementBlock
	{$m.setType(MESSAGE);}
	;

fix
	@after {
		$tree.getExtraTokens().add($ob);
		$tree.getExtraTokens().add($cb);
	}
	:	f='fix'^ ob='{'! guard? title fixBody cb='}'!
	{$f.setType(FIX);}
	;

title
	: t='title'^ expressionOrStatementBlock
	{$t.setType(TITLE);}
	;

fixBody
	: d='do'^ statementBlock
	{$d.setType(DO);}
	;
