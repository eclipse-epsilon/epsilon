// $ANTLR 3.1b1 Eol__.g 2025-03-21 07:18:57

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
package org.eclipse.epsilon.eol.parse;

import org.eclipse.epsilon.common.parse.Lexer;	


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class EolLexer extends Lexer {
    public static final int T__144=144;
    public static final int T__143=143;
    public static final int T__146=146;
    public static final int MODELDECLARATIONPARAMETER=78;
    public static final int T__145=145;
    public static final int BREAKALL=44;
    public static final int T__140=140;
    public static final int T__142=142;
    public static final int VAR=53;
    public static final int MODELDECLARATIONPARAMETERS=77;
    public static final int T__141=141;
    public static final int THROW=58;
    public static final int SpecialTypeName=19;
    public static final int PARAMLIST=29;
    public static final int EXPRLIST=59;
    public static final int EXPRRANGE=60;
    public static final int BREAK=43;
    public static final int ELSE=36;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int FORMAL=28;
    public static final int IF=35;
    public static final int MultiplicativeExpression=62;
    public static final int TYPE=70;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int Tokens=151;
    public static final int T__133=133;
    public static final int T__132=132;
    public static final int T__135=135;
    public static final int T__134=134;
    public static final int T__131=131;
    public static final int NewExpression=52;
    public static final int T__130=130;
    public static final int CASE=40;
    public static final int Letter=20;
    public static final int LINE_COMMENT=26;
    public static final int T__129=129;
    public static final int T__126=126;
    public static final int JavaIDDigit=22;
    public static final int T__125=125;
    public static final int LAMBDAEXPR=69;
    public static final int MAP=80;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int MODELDECLARATION=73;
    public static final int EXPRESSIONINBRACKETS=64;
    public static final int TERNARY=37;
    public static final int TRANSACTION=46;
    public static final int FLOAT_TYPE_SUFFIX=7;
    public static final int ITEMSELECTOR=79;
    public static final int COMMENT=25;
    public static final int ModelElementType=50;
    public static final int IMPORT=72;
    public static final int DELETE=57;
    public static final int ARROW=11;
    public static final int MapTypeName=18;
    public static final int SPECIAL_ASSIGNMENT=31;
    public static final int T__150=150;
    public static final int Annotation=27;
    public static final int CONTINUE=45;
    public static final int ENUMERATION_VALUE=71;
    public static final int OPERATOR=63;
    public static final int EXPONENT=6;
    public static final int STRING=15;
    public static final int T__148=148;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int T__91=91;
    public static final int T__100=100;
    public static final int NAMESPACE=74;
    public static final int T__92=92;
    public static final int COLLECTION=47;
    public static final int NEW=54;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int T__90=90;
    public static final int ALIAS=75;
    public static final int DRIVER=76;
    public static final int KEYVAL=81;
    public static final int POINT_POINT=10;
    public static final int T__99=99;
    public static final int T__95=95;
    public static final int HELPERMETHOD=32;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int StatementBlock=33;
    public static final int T__98=98;
    public static final int ABORT=48;
    public static final int StrangeNameLiteral=16;
    public static final int FOR=34;
    public static final int BLOCK=67;
    public static final int PARAMETERS=51;
    public static final int SpecialNameChar=21;
    public static final int BOOLEAN=13;
    public static final int NAME=23;
    public static final int SWITCH=39;
    public static final int FeatureCall=65;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int T__124=124;
    public static final int FLOAT=4;
    public static final int T__123=123;
    public static final int T__120=120;
    public static final int NativeType=61;
    public static final int INT=8;
    public static final int ANNOTATIONBLOCK=55;
    public static final int RETURN=42;
    public static final int KEYVALLIST=82;
    public static final int FEATURECALL=68;
    public static final int CollectionType=49;
    public static final int T__119=119;
    public static final int ASSIGNMENT=30;
    public static final int T__118=118;
    public static final int T__115=115;
    public static final int WS=24;
    public static final int EOF=-1;
    public static final int T__114=114;
    public static final int T__117=117;
    public static final int T__116=116;
    public static final int T__111=111;
    public static final int T__110=110;
    public static final int T__113=113;
    public static final int T__83=83;
    public static final int T__112=112;
    public static final int EscapeSequence=14;
    public static final int EOLMODULE=66;
    public static final int CollectionTypeName=17;
    public static final int DIGIT=5;
    public static final int EXECUTABLEANNOTATION=56;
    public static final int T__88=88;
    public static final int T__108=108;
    public static final int T__89=89;
    public static final int T__107=107;
    public static final int WHILE=38;
    public static final int T__109=109;
    public static final int NAVIGATION=12;
    public static final int T__84=84;
    public static final int T__104=104;
    public static final int POINT=9;
    public static final int T__85=85;
    public static final int T__103=103;
    public static final int T__86=86;
    public static final int T__106=106;
    public static final int DEFAULT=41;
    public static final int T__87=87;
    public static final int T__105=105;

    // delegates
    public Eol_EolLexerRules gEolLexerRules;
    // delegators

    public EolLexer() {;} 
    public EolLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public EolLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);
        gEolLexerRules = new Eol_EolLexerRules(input, state, this);
    }
    public String getGrammarFileName() { return "Eol__.g"; }

    // $ANTLR start T__83
    public final void mT__83() throws RecognitionException {
        try {
            int _type = T__83;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:46:7: ( 'model' )
            // Eol__.g:46:9: 'model'
            {
            match("model"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__83

    // $ANTLR start T__84
    public final void mT__84() throws RecognitionException {
        try {
            int _type = T__84;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:47:7: ( ';' )
            // Eol__.g:47:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__84

    // $ANTLR start T__85
    public final void mT__85() throws RecognitionException {
        try {
            int _type = T__85;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:48:7: ( 'alias' )
            // Eol__.g:48:9: 'alias'
            {
            match("alias"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__85

    // $ANTLR start T__86
    public final void mT__86() throws RecognitionException {
        try {
            int _type = T__86;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:49:7: ( ',' )
            // Eol__.g:49:9: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__86

    // $ANTLR start T__87
    public final void mT__87() throws RecognitionException {
        try {
            int _type = T__87;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:50:7: ( 'driver' )
            // Eol__.g:50:9: 'driver'
            {
            match("driver"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__87

    // $ANTLR start T__88
    public final void mT__88() throws RecognitionException {
        try {
            int _type = T__88;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:51:7: ( '{' )
            // Eol__.g:51:9: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__88

    // $ANTLR start T__89
    public final void mT__89() throws RecognitionException {
        try {
            int _type = T__89;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:52:7: ( '}' )
            // Eol__.g:52:9: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__89

    // $ANTLR start T__90
    public final void mT__90() throws RecognitionException {
        try {
            int _type = T__90;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:53:7: ( '=' )
            // Eol__.g:53:9: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__90

    // $ANTLR start T__91
    public final void mT__91() throws RecognitionException {
        try {
            int _type = T__91;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:54:7: ( 'operation' )
            // Eol__.g:54:9: 'operation'
            {
            match("operation"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__91

    // $ANTLR start T__92
    public final void mT__92() throws RecognitionException {
        try {
            int _type = T__92;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:55:7: ( 'function' )
            // Eol__.g:55:9: 'function'
            {
            match("function"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__92

    // $ANTLR start T__93
    public final void mT__93() throws RecognitionException {
        try {
            int _type = T__93;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:56:7: ( '(' )
            // Eol__.g:56:9: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__93

    // $ANTLR start T__94
    public final void mT__94() throws RecognitionException {
        try {
            int _type = T__94;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:57:7: ( ')' )
            // Eol__.g:57:9: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__94

    // $ANTLR start T__95
    public final void mT__95() throws RecognitionException {
        try {
            int _type = T__95;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:58:7: ( ':' )
            // Eol__.g:58:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__95

    // $ANTLR start T__96
    public final void mT__96() throws RecognitionException {
        try {
            int _type = T__96;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:59:7: ( 'import' )
            // Eol__.g:59:9: 'import'
            {
            match("import"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__96

    // $ANTLR start T__97
    public final void mT__97() throws RecognitionException {
        try {
            int _type = T__97;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:60:7: ( '$' )
            // Eol__.g:60:9: '$'
            {
            match('$'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__97

    // $ANTLR start T__98
    public final void mT__98() throws RecognitionException {
        try {
            int _type = T__98;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:61:7: ( '!' )
            // Eol__.g:61:9: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__98

    // $ANTLR start T__99
    public final void mT__99() throws RecognitionException {
        try {
            int _type = T__99;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:62:7: ( '::' )
            // Eol__.g:62:9: '::'
            {
            match("::"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__99

    // $ANTLR start T__100
    public final void mT__100() throws RecognitionException {
        try {
            int _type = T__100;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:63:8: ( '<' )
            // Eol__.g:63:10: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__100

    // $ANTLR start T__101
    public final void mT__101() throws RecognitionException {
        try {
            int _type = T__101;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:64:8: ( '>' )
            // Eol__.g:64:10: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__101

    // $ANTLR start T__102
    public final void mT__102() throws RecognitionException {
        try {
            int _type = T__102;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:65:8: ( 'if' )
            // Eol__.g:65:10: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__102

    // $ANTLR start T__103
    public final void mT__103() throws RecognitionException {
        try {
            int _type = T__103;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:66:8: ( 'else' )
            // Eol__.g:66:10: 'else'
            {
            match("else"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__103

    // $ANTLR start T__104
    public final void mT__104() throws RecognitionException {
        try {
            int _type = T__104;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:67:8: ( 'switch' )
            // Eol__.g:67:10: 'switch'
            {
            match("switch"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__104

    // $ANTLR start T__105
    public final void mT__105() throws RecognitionException {
        try {
            int _type = T__105;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:68:8: ( 'case' )
            // Eol__.g:68:10: 'case'
            {
            match("case"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__105

    // $ANTLR start T__106
    public final void mT__106() throws RecognitionException {
        try {
            int _type = T__106;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:69:8: ( 'default' )
            // Eol__.g:69:10: 'default'
            {
            match("default"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__106

    // $ANTLR start T__107
    public final void mT__107() throws RecognitionException {
        try {
            int _type = T__107;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:70:8: ( 'for' )
            // Eol__.g:70:10: 'for'
            {
            match("for"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__107

    // $ANTLR start T__108
    public final void mT__108() throws RecognitionException {
        try {
            int _type = T__108;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:71:8: ( 'in' )
            // Eol__.g:71:10: 'in'
            {
            match("in"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__108

    // $ANTLR start T__109
    public final void mT__109() throws RecognitionException {
        try {
            int _type = T__109;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:72:8: ( 'while' )
            // Eol__.g:72:10: 'while'
            {
            match("while"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__109

    // $ANTLR start T__110
    public final void mT__110() throws RecognitionException {
        try {
            int _type = T__110;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:73:8: ( 'return' )
            // Eol__.g:73:10: 'return'
            {
            match("return"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__110

    // $ANTLR start T__111
    public final void mT__111() throws RecognitionException {
        try {
            int _type = T__111;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:74:8: ( 'throw' )
            // Eol__.g:74:10: 'throw'
            {
            match("throw"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__111

    // $ANTLR start T__112
    public final void mT__112() throws RecognitionException {
        try {
            int _type = T__112;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:75:8: ( 'delete' )
            // Eol__.g:75:10: 'delete'
            {
            match("delete"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__112

    // $ANTLR start T__113
    public final void mT__113() throws RecognitionException {
        try {
            int _type = T__113;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:76:8: ( 'break' )
            // Eol__.g:76:10: 'break'
            {
            match("break"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__113

    // $ANTLR start T__114
    public final void mT__114() throws RecognitionException {
        try {
            int _type = T__114;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:77:8: ( 'breakAll' )
            // Eol__.g:77:10: 'breakAll'
            {
            match("breakAll"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__114

    // $ANTLR start T__115
    public final void mT__115() throws RecognitionException {
        try {
            int _type = T__115;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:78:8: ( 'continue' )
            // Eol__.g:78:10: 'continue'
            {
            match("continue"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__115

    // $ANTLR start T__116
    public final void mT__116() throws RecognitionException {
        try {
            int _type = T__116;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:79:8: ( 'abort' )
            // Eol__.g:79:10: 'abort'
            {
            match("abort"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__116

    // $ANTLR start T__117
    public final void mT__117() throws RecognitionException {
        try {
            int _type = T__117;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:80:8: ( 'transaction' )
            // Eol__.g:80:10: 'transaction'
            {
            match("transaction"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__117

    // $ANTLR start T__118
    public final void mT__118() throws RecognitionException {
        try {
            int _type = T__118;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:81:8: ( ':=' )
            // Eol__.g:81:10: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__118

    // $ANTLR start T__119
    public final void mT__119() throws RecognitionException {
        try {
            int _type = T__119;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:82:8: ( '+=' )
            // Eol__.g:82:10: '+='
            {
            match("+="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__119

    // $ANTLR start T__120
    public final void mT__120() throws RecognitionException {
        try {
            int _type = T__120;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:83:8: ( '-=' )
            // Eol__.g:83:10: '-='
            {
            match("-="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__120

    // $ANTLR start T__121
    public final void mT__121() throws RecognitionException {
        try {
            int _type = T__121;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:84:8: ( '*=' )
            // Eol__.g:84:10: '*='
            {
            match("*="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__121

    // $ANTLR start T__122
    public final void mT__122() throws RecognitionException {
        try {
            int _type = T__122;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:85:8: ( '/=' )
            // Eol__.g:85:10: '/='
            {
            match("/="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__122

    // $ANTLR start T__123
    public final void mT__123() throws RecognitionException {
        try {
            int _type = T__123;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:86:8: ( '?=' )
            // Eol__.g:86:10: '?='
            {
            match("?="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__123

    // $ANTLR start T__124
    public final void mT__124() throws RecognitionException {
        try {
            int _type = T__124;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:87:8: ( '::=' )
            // Eol__.g:87:10: '::='
            {
            match("::="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__124

    // $ANTLR start T__125
    public final void mT__125() throws RecognitionException {
        try {
            int _type = T__125;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:88:8: ( 'or' )
            // Eol__.g:88:10: 'or'
            {
            match("or"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__125

    // $ANTLR start T__126
    public final void mT__126() throws RecognitionException {
        try {
            int _type = T__126;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:89:8: ( 'and' )
            // Eol__.g:89:10: 'and'
            {
            match("and"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__126

    // $ANTLR start T__127
    public final void mT__127() throws RecognitionException {
        try {
            int _type = T__127;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:90:8: ( 'xor' )
            // Eol__.g:90:10: 'xor'
            {
            match("xor"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__127

    // $ANTLR start T__128
    public final void mT__128() throws RecognitionException {
        try {
            int _type = T__128;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:91:8: ( 'implies' )
            // Eol__.g:91:10: 'implies'
            {
            match("implies"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__128

    // $ANTLR start T__129
    public final void mT__129() throws RecognitionException {
        try {
            int _type = T__129;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:92:8: ( '?' )
            // Eol__.g:92:10: '?'
            {
            match('?'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__129

    // $ANTLR start T__130
    public final void mT__130() throws RecognitionException {
        try {
            int _type = T__130;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:93:8: ( '==' )
            // Eol__.g:93:10: '=='
            {
            match("=="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__130

    // $ANTLR start T__131
    public final void mT__131() throws RecognitionException {
        try {
            int _type = T__131;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:94:8: ( '!=' )
            // Eol__.g:94:10: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__131

    // $ANTLR start T__132
    public final void mT__132() throws RecognitionException {
        try {
            int _type = T__132;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:95:8: ( '?:' )
            // Eol__.g:95:10: '?:'
            {
            match("?:"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__132

    // $ANTLR start T__133
    public final void mT__133() throws RecognitionException {
        try {
            int _type = T__133;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:96:8: ( '>=' )
            // Eol__.g:96:10: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__133

    // $ANTLR start T__134
    public final void mT__134() throws RecognitionException {
        try {
            int _type = T__134;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:97:8: ( '<=' )
            // Eol__.g:97:10: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__134

    // $ANTLR start T__135
    public final void mT__135() throws RecognitionException {
        try {
            int _type = T__135;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:98:8: ( '<>' )
            // Eol__.g:98:10: '<>'
            {
            match("<>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__135

    // $ANTLR start T__136
    public final void mT__136() throws RecognitionException {
        try {
            int _type = T__136;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:99:8: ( '+' )
            // Eol__.g:99:10: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__136

    // $ANTLR start T__137
    public final void mT__137() throws RecognitionException {
        try {
            int _type = T__137;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:100:8: ( '-' )
            // Eol__.g:100:10: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__137

    // $ANTLR start T__138
    public final void mT__138() throws RecognitionException {
        try {
            int _type = T__138;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:101:8: ( '*' )
            // Eol__.g:101:10: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__138

    // $ANTLR start T__139
    public final void mT__139() throws RecognitionException {
        try {
            int _type = T__139;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:102:8: ( '/' )
            // Eol__.g:102:10: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__139

    // $ANTLR start T__140
    public final void mT__140() throws RecognitionException {
        try {
            int _type = T__140;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:103:8: ( 'not' )
            // Eol__.g:103:10: 'not'
            {
            match("not"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__140

    // $ANTLR start T__141
    public final void mT__141() throws RecognitionException {
        try {
            int _type = T__141;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:104:8: ( '++' )
            // Eol__.g:104:10: '++'
            {
            match("++"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__141

    // $ANTLR start T__142
    public final void mT__142() throws RecognitionException {
        try {
            int _type = T__142;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:105:8: ( '--' )
            // Eol__.g:105:10: '--'
            {
            match("--"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__142

    // $ANTLR start T__143
    public final void mT__143() throws RecognitionException {
        try {
            int _type = T__143;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:106:8: ( '[' )
            // Eol__.g:106:10: '['
            {
            match('['); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__143

    // $ANTLR start T__144
    public final void mT__144() throws RecognitionException {
        try {
            int _type = T__144;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:107:8: ( ']' )
            // Eol__.g:107:10: ']'
            {
            match(']'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__144

    // $ANTLR start T__145
    public final void mT__145() throws RecognitionException {
        try {
            int _type = T__145;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:108:8: ( '|' )
            // Eol__.g:108:10: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__145

    // $ANTLR start T__146
    public final void mT__146() throws RecognitionException {
        try {
            int _type = T__146;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:109:8: ( '=>' )
            // Eol__.g:109:10: '=>'
            {
            match("=>"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__146

    // $ANTLR start T__147
    public final void mT__147() throws RecognitionException {
        try {
            int _type = T__147;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:110:8: ( 'new' )
            // Eol__.g:110:10: 'new'
            {
            match("new"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__147

    // $ANTLR start T__148
    public final void mT__148() throws RecognitionException {
        try {
            int _type = T__148;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:111:8: ( 'var' )
            // Eol__.g:111:10: 'var'
            {
            match("var"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__148

    // $ANTLR start T__149
    public final void mT__149() throws RecognitionException {
        try {
            int _type = T__149;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:112:8: ( 'ext' )
            // Eol__.g:112:10: 'ext'
            {
            match("ext"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__149

    // $ANTLR start T__150
    public final void mT__150() throws RecognitionException {
        try {
            int _type = T__150;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Eol__.g:113:8: ( '#' )
            // Eol__.g:113:10: '#'
            {
            match('#'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__150

    public void mTokens() throws RecognitionException {
        // Eol__.g:1:8: ( T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | T__131 | T__132 | T__133 | T__134 | T__135 | T__136 | T__137 | T__138 | T__139 | T__140 | T__141 | T__142 | T__143 | T__144 | T__145 | T__146 | T__147 | T__148 | T__149 | T__150 | EolLexerRules. Tokens )
        int alt1=69;
        alt1 = dfa1.predict(input);
        switch (alt1) {
            case 1 :
                // Eol__.g:1:10: T__83
                {
                mT__83(); 

                }
                break;
            case 2 :
                // Eol__.g:1:16: T__84
                {
                mT__84(); 

                }
                break;
            case 3 :
                // Eol__.g:1:22: T__85
                {
                mT__85(); 

                }
                break;
            case 4 :
                // Eol__.g:1:28: T__86
                {
                mT__86(); 

                }
                break;
            case 5 :
                // Eol__.g:1:34: T__87
                {
                mT__87(); 

                }
                break;
            case 6 :
                // Eol__.g:1:40: T__88
                {
                mT__88(); 

                }
                break;
            case 7 :
                // Eol__.g:1:46: T__89
                {
                mT__89(); 

                }
                break;
            case 8 :
                // Eol__.g:1:52: T__90
                {
                mT__90(); 

                }
                break;
            case 9 :
                // Eol__.g:1:58: T__91
                {
                mT__91(); 

                }
                break;
            case 10 :
                // Eol__.g:1:64: T__92
                {
                mT__92(); 

                }
                break;
            case 11 :
                // Eol__.g:1:70: T__93
                {
                mT__93(); 

                }
                break;
            case 12 :
                // Eol__.g:1:76: T__94
                {
                mT__94(); 

                }
                break;
            case 13 :
                // Eol__.g:1:82: T__95
                {
                mT__95(); 

                }
                break;
            case 14 :
                // Eol__.g:1:88: T__96
                {
                mT__96(); 

                }
                break;
            case 15 :
                // Eol__.g:1:94: T__97
                {
                mT__97(); 

                }
                break;
            case 16 :
                // Eol__.g:1:100: T__98
                {
                mT__98(); 

                }
                break;
            case 17 :
                // Eol__.g:1:106: T__99
                {
                mT__99(); 

                }
                break;
            case 18 :
                // Eol__.g:1:112: T__100
                {
                mT__100(); 

                }
                break;
            case 19 :
                // Eol__.g:1:119: T__101
                {
                mT__101(); 

                }
                break;
            case 20 :
                // Eol__.g:1:126: T__102
                {
                mT__102(); 

                }
                break;
            case 21 :
                // Eol__.g:1:133: T__103
                {
                mT__103(); 

                }
                break;
            case 22 :
                // Eol__.g:1:140: T__104
                {
                mT__104(); 

                }
                break;
            case 23 :
                // Eol__.g:1:147: T__105
                {
                mT__105(); 

                }
                break;
            case 24 :
                // Eol__.g:1:154: T__106
                {
                mT__106(); 

                }
                break;
            case 25 :
                // Eol__.g:1:161: T__107
                {
                mT__107(); 

                }
                break;
            case 26 :
                // Eol__.g:1:168: T__108
                {
                mT__108(); 

                }
                break;
            case 27 :
                // Eol__.g:1:175: T__109
                {
                mT__109(); 

                }
                break;
            case 28 :
                // Eol__.g:1:182: T__110
                {
                mT__110(); 

                }
                break;
            case 29 :
                // Eol__.g:1:189: T__111
                {
                mT__111(); 

                }
                break;
            case 30 :
                // Eol__.g:1:196: T__112
                {
                mT__112(); 

                }
                break;
            case 31 :
                // Eol__.g:1:203: T__113
                {
                mT__113(); 

                }
                break;
            case 32 :
                // Eol__.g:1:210: T__114
                {
                mT__114(); 

                }
                break;
            case 33 :
                // Eol__.g:1:217: T__115
                {
                mT__115(); 

                }
                break;
            case 34 :
                // Eol__.g:1:224: T__116
                {
                mT__116(); 

                }
                break;
            case 35 :
                // Eol__.g:1:231: T__117
                {
                mT__117(); 

                }
                break;
            case 36 :
                // Eol__.g:1:238: T__118
                {
                mT__118(); 

                }
                break;
            case 37 :
                // Eol__.g:1:245: T__119
                {
                mT__119(); 

                }
                break;
            case 38 :
                // Eol__.g:1:252: T__120
                {
                mT__120(); 

                }
                break;
            case 39 :
                // Eol__.g:1:259: T__121
                {
                mT__121(); 

                }
                break;
            case 40 :
                // Eol__.g:1:266: T__122
                {
                mT__122(); 

                }
                break;
            case 41 :
                // Eol__.g:1:273: T__123
                {
                mT__123(); 

                }
                break;
            case 42 :
                // Eol__.g:1:280: T__124
                {
                mT__124(); 

                }
                break;
            case 43 :
                // Eol__.g:1:287: T__125
                {
                mT__125(); 

                }
                break;
            case 44 :
                // Eol__.g:1:294: T__126
                {
                mT__126(); 

                }
                break;
            case 45 :
                // Eol__.g:1:301: T__127
                {
                mT__127(); 

                }
                break;
            case 46 :
                // Eol__.g:1:308: T__128
                {
                mT__128(); 

                }
                break;
            case 47 :
                // Eol__.g:1:315: T__129
                {
                mT__129(); 

                }
                break;
            case 48 :
                // Eol__.g:1:322: T__130
                {
                mT__130(); 

                }
                break;
            case 49 :
                // Eol__.g:1:329: T__131
                {
                mT__131(); 

                }
                break;
            case 50 :
                // Eol__.g:1:336: T__132
                {
                mT__132(); 

                }
                break;
            case 51 :
                // Eol__.g:1:343: T__133
                {
                mT__133(); 

                }
                break;
            case 52 :
                // Eol__.g:1:350: T__134
                {
                mT__134(); 

                }
                break;
            case 53 :
                // Eol__.g:1:357: T__135
                {
                mT__135(); 

                }
                break;
            case 54 :
                // Eol__.g:1:364: T__136
                {
                mT__136(); 

                }
                break;
            case 55 :
                // Eol__.g:1:371: T__137
                {
                mT__137(); 

                }
                break;
            case 56 :
                // Eol__.g:1:378: T__138
                {
                mT__138(); 

                }
                break;
            case 57 :
                // Eol__.g:1:385: T__139
                {
                mT__139(); 

                }
                break;
            case 58 :
                // Eol__.g:1:392: T__140
                {
                mT__140(); 

                }
                break;
            case 59 :
                // Eol__.g:1:399: T__141
                {
                mT__141(); 

                }
                break;
            case 60 :
                // Eol__.g:1:406: T__142
                {
                mT__142(); 

                }
                break;
            case 61 :
                // Eol__.g:1:413: T__143
                {
                mT__143(); 

                }
                break;
            case 62 :
                // Eol__.g:1:420: T__144
                {
                mT__144(); 

                }
                break;
            case 63 :
                // Eol__.g:1:427: T__145
                {
                mT__145(); 

                }
                break;
            case 64 :
                // Eol__.g:1:434: T__146
                {
                mT__146(); 

                }
                break;
            case 65 :
                // Eol__.g:1:441: T__147
                {
                mT__147(); 

                }
                break;
            case 66 :
                // Eol__.g:1:448: T__148
                {
                mT__148(); 

                }
                break;
            case 67 :
                // Eol__.g:1:455: T__149
                {
                mT__149(); 

                }
                break;
            case 68 :
                // Eol__.g:1:462: T__150
                {
                mT__150(); 

                }
                break;
            case 69 :
                // Eol__.g:1:469: EolLexerRules. Tokens
                {
                gEolLexerRules.mTokens(); 

                }
                break;

        }

    }


    protected DFA1 dfa1 = new DFA1(this);
    static final String DFA1_eotS =
        "\1\uffff\1\46\1\uffff\1\46\1\uffff\1\46\2\uffff\1\57\2\46\2\uffff"+
        "\1\66\1\46\1\uffff\1\73\1\76\1\100\7\46\1\115\1\120\1\122\1\124"+
        "\1\127\2\46\3\uffff\1\46\2\uffff\6\46\3\uffff\1\46\1\144\2\46\1"+
        "\150\2\uffff\1\46\1\152\1\153\7\uffff\12\46\15\uffff\7\46\1\175"+
        "\4\46\1\uffff\1\46\1\u0083\2\uffff\1\46\2\uffff\1\46\1\u0087\10"+
        "\46\1\u0090\1\u0091\1\u0092\1\u0093\3\46\1\uffff\5\46\1\uffff\2"+
        "\46\1\u009e\1\uffff\1\46\1\u00a0\6\46\4\uffff\1\u00a7\1\u00a8\1"+
        "\u00a9\7\46\1\uffff\1\46\1\uffff\1\46\1\u00b3\1\46\1\u00b5\1\46"+
        "\1\u00b8\3\uffff\1\u00b9\1\46\1\u00bb\2\46\1\u00be\1\46\1\u00c0"+
        "\1\46\1\uffff\1\u00c2\1\uffff\2\46\2\uffff\1\u00c5\1\uffff\2\46"+
        "\1\uffff\1\u00c8\1\uffff\1\46\1\uffff\2\46\1\uffff\1\46\1\u00cd"+
        "\1\uffff\1\u00ce\1\46\1\u00d0\1\u00d1\2\uffff\1\46\2\uffff\1\46"+
        "\1\u00d4\1\uffff";
    static final String DFA1_eofS =
        "\u00d5\uffff";
    static final String DFA1_minS =
        "\1\11\1\157\1\uffff\1\142\1\uffff\1\145\2\uffff\1\75\1\160\1\157"+
        "\2\uffff\1\72\1\146\1\uffff\3\75\1\154\1\167\1\141\1\150\1\145\1"+
        "\150\1\162\1\53\1\55\1\75\1\52\1\56\1\157\1\145\3\uffff\1\141\2"+
        "\uffff\1\144\1\151\1\157\1\144\1\151\1\146\3\uffff\1\145\1\46\1"+
        "\156\1\162\1\75\2\uffff\1\160\2\46\7\uffff\1\163\1\164\1\151\1\163"+
        "\1\156\1\151\1\164\1\162\1\141\1\145\15\uffff\1\162\1\164\1\167"+
        "\1\162\1\145\1\141\1\162\1\46\1\166\1\141\1\145\1\162\1\uffff\1"+
        "\143\1\46\2\uffff\1\154\2\uffff\1\145\1\46\1\164\1\145\1\164\1\154"+
        "\1\165\1\157\1\156\1\141\4\46\1\154\1\163\1\164\1\uffff\1\145\1"+
        "\165\1\164\1\141\1\164\1\uffff\1\162\1\151\1\46\1\uffff\1\143\1"+
        "\46\1\151\1\145\1\162\1\167\1\163\1\153\4\uffff\3\46\1\162\1\154"+
        "\1\145\1\164\1\151\1\164\1\145\1\uffff\1\150\1\uffff\1\156\1\46"+
        "\1\156\1\46\1\141\1\46\3\uffff\1\46\1\164\1\46\1\151\1\157\1\46"+
        "\1\163\1\46\1\165\1\uffff\1\46\1\uffff\1\143\1\154\2\uffff\1\46"+
        "\1\uffff\1\157\1\156\1\uffff\1\46\1\uffff\1\145\1\uffff\1\164\1"+
        "\154\1\uffff\1\156\1\46\1\uffff\1\46\1\151\2\46\2\uffff\1\157\2"+
        "\uffff\1\156\1\46\1\uffff";
    static final String DFA1_maxS =
        "\1\ufaff\1\157\1\uffff\1\156\1\uffff\1\162\2\uffff\1\76\1\162\1"+
        "\165\2\uffff\1\75\1\156\1\uffff\1\75\1\76\1\75\1\170\1\167\1\157"+
        "\1\150\1\145\2\162\1\75\1\76\3\75\2\157\3\uffff\1\141\2\uffff\1"+
        "\144\1\151\1\157\1\144\1\151\1\154\3\uffff\1\145\1\ufaff\1\156\1"+
        "\162\1\75\2\uffff\1\160\2\ufaff\7\uffff\1\163\1\164\1\151\1\163"+
        "\1\156\1\151\1\164\1\162\1\141\1\145\15\uffff\1\162\1\164\1\167"+
        "\1\162\1\145\1\141\1\162\1\ufaff\1\166\1\141\1\145\1\162\1\uffff"+
        "\1\143\1\ufaff\2\uffff\1\157\2\uffff\1\145\1\ufaff\1\164\1\145\1"+
        "\164\1\154\1\165\1\157\1\156\1\141\4\ufaff\1\154\1\163\1\164\1\uffff"+
        "\1\145\1\165\1\164\1\141\1\164\1\uffff\1\162\1\151\1\ufaff\1\uffff"+
        "\1\143\1\ufaff\1\151\1\145\1\162\1\167\1\163\1\153\4\uffff\3\ufaff"+
        "\1\162\1\154\1\145\1\164\1\151\1\164\1\145\1\uffff\1\150\1\uffff"+
        "\1\156\1\ufaff\1\156\1\ufaff\1\141\1\ufaff\3\uffff\1\ufaff\1\164"+
        "\1\ufaff\1\151\1\157\1\ufaff\1\163\1\ufaff\1\165\1\uffff\1\ufaff"+
        "\1\uffff\1\143\1\154\2\uffff\1\ufaff\1\uffff\1\157\1\156\1\uffff"+
        "\1\ufaff\1\uffff\1\145\1\uffff\1\164\1\154\1\uffff\1\156\1\ufaff"+
        "\1\uffff\1\ufaff\1\151\2\ufaff\2\uffff\1\157\2\uffff\1\156\1\ufaff"+
        "\1\uffff";
    static final String DFA1_acceptS =
        "\2\uffff\1\2\1\uffff\1\4\1\uffff\1\6\1\7\3\uffff\1\13\1\14\2\uffff"+
        "\1\17\21\uffff\1\75\1\76\1\77\1\uffff\1\104\1\105\6\uffff\1\60\1"+
        "\100\1\10\5\uffff\1\44\1\15\3\uffff\1\61\1\20\1\64\1\65\1\22\1\63"+
        "\1\23\12\uffff\1\45\1\73\1\66\1\46\1\74\1\67\1\47\1\70\1\50\1\71"+
        "\1\51\1\62\1\57\14\uffff\1\53\2\uffff\1\52\1\21\1\uffff\1\24\1\32"+
        "\21\uffff\1\54\5\uffff\1\31\3\uffff\1\103\10\uffff\1\55\1\72\1\101"+
        "\1\102\12\uffff\1\25\1\uffff\1\27\6\uffff\1\1\1\3\1\42\11\uffff"+
        "\1\33\1\uffff\1\35\2\uffff\1\37\1\5\1\uffff\1\36\2\uffff\1\16\1"+
        "\uffff\1\26\1\uffff\1\34\2\uffff\1\30\2\uffff\1\56\4\uffff\1\12"+
        "\1\41\1\uffff\1\40\1\11\2\uffff\1\43";
    static final String DFA1_specialS =
        "\u00d5\uffff}>";
    static final String[] DFA1_transitionS = {
            "\2\46\1\uffff\2\46\22\uffff\1\46\1\20\1\46\1\45\1\17\1\uffff"+
            "\2\46\1\13\1\14\1\34\1\32\1\4\1\33\1\46\1\35\12\46\1\15\1\2"+
            "\1\21\1\10\1\22\1\36\33\46\1\41\1\uffff\1\42\3\46\1\3\1\31\1"+
            "\25\1\5\1\23\1\12\2\46\1\16\3\46\1\1\1\40\1\11\2\46\1\27\1\24"+
            "\1\30\1\46\1\44\1\26\1\37\2\46\1\6\1\43\1\7\1\46\101\uffff\27"+
            "\46\1\uffff\37\46\1\uffff\u1f08\46\u1040\uffff\u0150\46\u0170"+
            "\uffff\u0080\46\u0080\uffff\u092e\46\u10d2\uffff\u5200\46\u5900"+
            "\uffff\u0200\46",
            "\1\47",
            "",
            "\1\51\11\uffff\1\50\1\uffff\1\52",
            "",
            "\1\54\14\uffff\1\53",
            "",
            "",
            "\1\55\1\56",
            "\1\60\1\uffff\1\61",
            "\1\63\5\uffff\1\62",
            "",
            "",
            "\1\64\2\uffff\1\65",
            "\1\70\6\uffff\1\67\1\71",
            "",
            "\1\72",
            "\1\74\1\75",
            "\1\77",
            "\1\101\13\uffff\1\102",
            "\1\103",
            "\1\104\15\uffff\1\105",
            "\1\106",
            "\1\107",
            "\1\110\11\uffff\1\111",
            "\1\112",
            "\1\114\21\uffff\1\113",
            "\1\117\17\uffff\1\116\1\46",
            "\1\121",
            "\1\46\4\uffff\1\46\15\uffff\1\123",
            "\1\46\13\uffff\1\126\2\uffff\1\125",
            "\1\130",
            "\1\132\11\uffff\1\131",
            "",
            "",
            "",
            "\1\133",
            "",
            "",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\137",
            "\1\140",
            "\1\141\5\uffff\1\142",
            "",
            "",
            "",
            "\1\143",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\145",
            "\1\146",
            "\1\147",
            "",
            "",
            "\1\151",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\154",
            "\1\155",
            "\1\156",
            "\1\157",
            "\1\160",
            "\1\161",
            "\1\162",
            "\1\163",
            "\1\164",
            "\1\165",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\166",
            "\1\167",
            "\1\170",
            "\1\171",
            "\1\172",
            "\1\173",
            "\1\174",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\176",
            "\1\177",
            "\1\u0080",
            "\1\u0081",
            "",
            "\1\u0082",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "",
            "\1\u0085\2\uffff\1\u0084",
            "",
            "",
            "\1\u0086",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u0088",
            "\1\u0089",
            "\1\u008a",
            "\1\u008b",
            "\1\u008c",
            "\1\u008d",
            "\1\u008e",
            "\1\u008f",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u0094",
            "\1\u0095",
            "\1\u0096",
            "",
            "\1\u0097",
            "\1\u0098",
            "\1\u0099",
            "\1\u009a",
            "\1\u009b",
            "",
            "\1\u009c",
            "\1\u009d",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "\1\u009f",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00a1",
            "\1\u00a2",
            "\1\u00a3",
            "\1\u00a4",
            "\1\u00a5",
            "\1\u00a6",
            "",
            "",
            "",
            "",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00aa",
            "\1\u00ab",
            "\1\u00ac",
            "\1\u00ad",
            "\1\u00ae",
            "\1\u00af",
            "\1\u00b0",
            "",
            "\1\u00b1",
            "",
            "\1\u00b2",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00b4",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00b6",
            "\1\46\11\uffff\12\46\7\uffff\1\u00b7\31\46\3\uffff\2\46\1\uffff"+
            "\32\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "",
            "",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00ba",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00bc",
            "\1\u00bd",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00bf",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00c1",
            "",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "\1\u00c3",
            "\1\u00c4",
            "",
            "",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "\1\u00c6",
            "\1\u00c7",
            "",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "\1\u00c9",
            "",
            "\1\u00ca",
            "\1\u00cb",
            "",
            "\1\u00cc",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\u00cf",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            "",
            "",
            "\1\u00d2",
            "",
            "",
            "\1\u00d3",
            "\1\46\11\uffff\12\46\7\uffff\32\46\3\uffff\2\46\1\uffff\32"+
            "\46\3\uffff\1\46\101\uffff\27\46\1\uffff\37\46\1\uffff\u1f08"+
            "\46\u1040\uffff\u0150\46\u0170\uffff\u0080\46\u0080\uffff\u092e"+
            "\46\u10d2\uffff\u5200\46\u5900\uffff\u0200\46",
            ""
    };

    static final short[] DFA1_eot = DFA.unpackEncodedString(DFA1_eotS);
    static final short[] DFA1_eof = DFA.unpackEncodedString(DFA1_eofS);
    static final char[] DFA1_min = DFA.unpackEncodedStringToUnsignedChars(DFA1_minS);
    static final char[] DFA1_max = DFA.unpackEncodedStringToUnsignedChars(DFA1_maxS);
    static final short[] DFA1_accept = DFA.unpackEncodedString(DFA1_acceptS);
    static final short[] DFA1_special = DFA.unpackEncodedString(DFA1_specialS);
    static final short[][] DFA1_transition;

    static {
        int numStates = DFA1_transitionS.length;
        DFA1_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA1_transition[i] = DFA.unpackEncodedString(DFA1_transitionS[i]);
        }
    }

    class DFA1 extends DFA {

        public DFA1(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 1;
            this.eot = DFA1_eot;
            this.eof = DFA1_eof;
            this.min = DFA1_min;
            this.max = DFA1_max;
            this.accept = DFA1_accept;
            this.special = DFA1_special;
            this.transition = DFA1_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( T__83 | T__84 | T__85 | T__86 | T__87 | T__88 | T__89 | T__90 | T__91 | T__92 | T__93 | T__94 | T__95 | T__96 | T__97 | T__98 | T__99 | T__100 | T__101 | T__102 | T__103 | T__104 | T__105 | T__106 | T__107 | T__108 | T__109 | T__110 | T__111 | T__112 | T__113 | T__114 | T__115 | T__116 | T__117 | T__118 | T__119 | T__120 | T__121 | T__122 | T__123 | T__124 | T__125 | T__126 | T__127 | T__128 | T__129 | T__130 | T__131 | T__132 | T__133 | T__134 | T__135 | T__136 | T__137 | T__138 | T__139 | T__140 | T__141 | T__142 | T__143 | T__144 | T__145 | T__146 | T__147 | T__148 | T__149 | T__150 | EolLexerRules. Tokens );";
        }
    }
 

}