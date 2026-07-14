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
lexer grammar EolLexerRules;

tokens {
	FLOAT;
}

fragment DIGIT : '0'..'9';
fragment EXPONENT : ('e'|'E') ('+'|'-')? DIGIT+ ;
fragment FLOAT_TYPE_SUFFIX : ('f'|'F'|'d'|'D') ;

INT : (DIGIT)+ ('l'|'L'
	| (('.' DIGIT)         => '.' (DIGIT)+ {$type = FLOAT;} EXPONENT? FLOAT_TYPE_SUFFIX?)
	| (EXPONENT FLOAT_TYPE_SUFFIX? {$type = FLOAT;})
	| (FLOAT_TYPE_SUFFIX {$type = FLOAT;})
	)? ;

POINT : '.';

POINT_POINT : '..';

ARROW : '->';

NAVIGATION : '?.';

BOOLEAN
	:	'true' | 'false'
	;
	
STRING
    :   ('\'' ( EscapeSequence | ~('\''|'\\') )* '\'' | '"' ( EscapeSequence | ~('\\'|'"') )* '"')
    ;

StrangeNameLiteral
    :  '`' ( EscapeSequence | ~('\\'|'`'|'\n'|'\r') )* '`'
    {$type=NAME; setText($text.substring(1,$text.length() - 1));}
    ;
	
CollectionTypeName
	:	'Bag'|'Sequence'|'Set'|'OrderedSet'|'Collection'|'List'|'ConcurrentBag'|'ConcurrentSet'
	;
	
MapTypeName
	:	'Map'|'ConcurrentMap'|'Tuple'
	;
	
SpecialTypeName
	:	'Native'
	;

fragment
EscapeSequence
    :   '\\' (
    	'b' 
    	|'t'
    	|'n' 
    	|'f'
    	|'r' 
    	|'\"'
    	|'\''
    	|'\\'
    	)
    
    //|   UnicodeEscape
    //|   OctalEscape
    ;

NAME 
    :   (Letter|SpecialNameChar) (Letter|JavaIDDigit|SpecialNameChar)*
    ;
    
fragment
SpecialNameChar
	: '~' | '&' | '^'
	;

/**I found this char range in JavaCC's grammar, but Letter and Digit overlap.
   Still works, but...
 */
fragment
Letter
    //:  '\u0024' |
    :   '\u0041'..'\u005a' |
       '\u005f' |
       '\u0061'..'\u007a' |
       '\u00c0'..'\u00d6' |
       '\u00d8'..'\u00f6' |
       '\u00f8'..'\u00ff' |
       '\u0100'..'\u1fff' |
       '\u3040'..'\u318f' |
       '\u3300'..'\u337f' |
       '\u3400'..'\u3d2d' |
       '\u4e00'..'\u9fff' |
       '\uf900'..'\ufaff'
    ;

fragment
JavaIDDigit
    :  '\u0030'..'\u0039' |
       '\u0660'..'\u0669' |
       '\u06f0'..'\u06f9' |
       '\u0966'..'\u096f' |
       '\u09e6'..'\u09ef' |
       '\u0a66'..'\u0a6f' |
       '\u0ae6'..'\u0aef' |
       '\u0b66'..'\u0b6f' |
       '\u0be7'..'\u0bef' |
       '\u0c66'..'\u0c6f' |
       '\u0ce6'..'\u0cef' |
       '\u0d66'..'\u0d6f' |
       '\u0e50'..'\u0e59' |
       '\u0ed0'..'\u0ed9' |
       '\u1040'..'\u1049'
   ;

WS  :  (
	' '|
	'\r'|
	'\t' {input.setCharPositionInLine(input.getCharPositionInLine() + 3);}
	|'\u000C'
	|'\n'
	) {$channel=HIDDEN;}
    ;

COMMENT
    :   ('/*' ( options {greedy=false;} : . )* '*/') {$channel=HIDDEN;}
    ;

LINE_COMMENT
    : ('//' ~('\n'|'\r')*) {$channel=HIDDEN;}
    ;
    
Annotation
	: '@' ~('\n'|'\r')*
    ;
