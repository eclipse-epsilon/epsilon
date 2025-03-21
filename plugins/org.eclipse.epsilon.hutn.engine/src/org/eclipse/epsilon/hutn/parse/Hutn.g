/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
grammar Hutn;

options {backtrack=true; output=AST; ASTLabelType=CommonTree; superClass='org.eclipse.epsilon.common.parse.EpsilonParser';}

tokens {
     TRUE  = 'true';
     FALSE = 'false';
     NULL  = 'null';
     ADJECTIVE;
     CLS_LVL_ATTRIBUTE;
     ASSOC_INSTANCE;
     REFERENCE;
}

@header {
package org.eclipse.epsilon.hutn.parse;
}

@lexer::header {
/*******************************************************************************
 * Copyright (c) 2008 The University of York.
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
package org.eclipse.epsilon.hutn.parse;

import org.eclipse.epsilon.common.parse.Lexer;	
}

document: pkg* EOF -> pkg*;

pkg: NAME^ TEXTUAL_VALUE? LBRACKET! pkg_contents* RBRACKET!;

pkg_contents: cls | cls_level_attribute | assoc_instance;

assoc_instance: infix_assoc | assoc_block;

infix_assoc
      :   ref NAME ref ';' -> ^(ASSOC_INSTANCE[$NAME.text] ref ref)
      ;

assoc_block
      :   NAME LBRACKET assoc_contents+ RBRACKET -> ^(ASSOC_INSTANCE[$NAME.text] assoc_contents*)
      ;

assoc_contents
      :   ref ref
      ;

cls_level_attribute
      :   NAME '.' NAME ASSIGNMENT attr ';' -> ^(CLS_LVL_ATTRIBUTE ^(NAME ^(NAME attr)))
      ;

cls: adjective* NAME^ TEXTUAL_VALUE? (cls_body | ';'!);

cls_body: LBRACKET! cls_contents* RBRACKET!;

adjective
@init {
  String name = "";
} 
      :  (ADJECTIVE_PREFIX {name = $ADJECTIVE_PREFIX.text;})? NAME {name += $NAME.text;} -> ^(ADJECTIVE[name])
      ;

cls_contents: feature | adjective;

feature: NAME^ ASSIGNMENT! feature_contents;

feature_contents: attr | refs | containments;

attr: attr_value (COMMA! attr_value)* | NULL;

attr_value: TEXTUAL_VALUE | NUMERIC_VALUE | bool | enum_value;

bool: TRUE | FALSE;

refs: ref (COMMA! ref)*;

ref
      :   NAME TEXTUAL_VALUE -> ^(REFERENCE[$NAME.text] TEXTUAL_VALUE)
      ;

containments: cls (COMMA! cls)*;

enum_value: NAME^;

COMMA        : ',';
LBRACKET     : '{';
RBRACKET     : '}';
ASSIGNMENT  : ':' | '=';
NAME         : ID_START_LETTER ID_LETTER*;

/** Code for handling escape sequences based on: http://stackoverflow.com/questions/504402/how-to-handle-escape-sequences-in-string-literals-in-antlr-3 */
TEXTUAL_VALUE         
@init{StringBuilder lBuf = new StringBuilder();}
    :   
           '"'
           ( 
             escaped=ESC        {lBuf.append(getText());} 
             | 
             normal=TEXT_LETTER {lBuf.append(normal.getText());}
           )*
           '"'     
           {setText('"' + lBuf.toString() + '"');}
    ;


NUMERIC_VALUE: ('-'|'+')? DIGIT+ ('.' DIGIT+)? (('e'|'E') ('+'|'-')? DIGIT+)?;
ADJECTIVE_PREFIX: '~' | '#';

WS  :  (
	' '|
	'\r'|
	'\t' {input.setCharPositionInLine(input.getCharPositionInLine() + 3);}
	|'\u000C'
	|'\n'
	) {$channel=HIDDEN;}
    ;

ML_COMMENT
    :   '/*' ( options {greedy=false;} : . )* '*/' {$channel=HIDDEN;}
    ;

SL_COMMENT
    : '//' ~('\n'|'\r')* '\r'? '\n' {$channel=HIDDEN;}
    ;

fragment
ID_START_LETTER
	:    '_'
    |    'a'..'z'
    |    'A'..'Z'
    |    '@'
    ;

fragment
ID_LETTER
    :    ID_START_LETTER
    |    '0'..'9'
    |    '-'
    |    '#'
    ;

fragment
ESC
    :   '\\'
        (
           '"'    {setText("\"");} |
           '\\'   {setText("\\");}
        )
    ;
  
fragment
TEXT_LETTER
    :    ~('\"' | '\\')   /* Anything but a speech mark or a slash (which delimit strings and escape characters respectively) */
    ;
    
fragment
DIGIT : '0'..'9';