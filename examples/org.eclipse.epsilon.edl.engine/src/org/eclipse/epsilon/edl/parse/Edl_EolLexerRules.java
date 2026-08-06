package org.eclipse.epsilon.edl.parse;

// $ANTLR 3.1b1 EolLexerRules.g 2021-01-10 13:33:48

import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
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
public class Edl_EolLexerRules extends Lexer {
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
    public static final int PROCESS=84;
    public static final int ELSE=36;
    public static final int T__137=137;
    public static final int T__136=136;
    public static final int FORMAL=28;
    public static final int IF=35;
    public static final int MultiplicativeExpression=62;
    public static final int TYPE=70;
    public static final int T__139=139;
    public static final int T__138=138;
    public static final int Tokens=154;
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
    public static final int EDLMODULE=83;
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
    public static final int T__151=151;
    public static final int T__150=150;
    public static final int T__153=153;
    public static final int T__152=152;
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
    // delegators
    public EdlLexer gEdl;

    public Edl_EolLexerRules() {;} 
    public Edl_EolLexerRules(CharStream input, EdlLexer gEdl) {
        this(input, new RecognizerSharedState(), gEdl);
    }
    public Edl_EolLexerRules(CharStream input, RecognizerSharedState state, EdlLexer gEdl) {
        super(input,state);

        this.gEdl = gEdl;
    }
    public String getGrammarFileName() { return "EolLexerRules.g"; }

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
        try {
            // EolLexerRules.g:44:16: ( '0' .. '9' )
            // EolLexerRules.g:44:18: '0' .. '9'
            {
            matchRange('0','9'); if (state.failed) return ;

            }

        }
        finally {
        }
    }
    // $ANTLR end DIGIT

    // $ANTLR start EXPONENT
    public final void mEXPONENT() throws RecognitionException {
        try {
            // EolLexerRules.g:45:19: ( ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+ )
            // EolLexerRules.g:45:21: ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+
            {
            if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // EolLexerRules.g:45:31: ( '+' | '-' )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0=='+'||LA1_0=='-') ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // EolLexerRules.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();
                    state.failed=false;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return ;}
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // EolLexerRules.g:45:42: ( DIGIT )+
            int cnt2=0;
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>='0' && LA2_0<='9')) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // EolLexerRules.g:45:42: DIGIT
            	    {
            	    mDIGIT(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt2 >= 1 ) break loop2;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(2, input);
                        throw eee;
                }
                cnt2++;
            } while (true);


            }

        }
        finally {
        }
    }
    // $ANTLR end EXPONENT

    // $ANTLR start FLOAT_TYPE_SUFFIX
    public final void mFLOAT_TYPE_SUFFIX() throws RecognitionException {
        try {
            // EolLexerRules.g:46:28: ( ( 'f' | 'F' | 'd' | 'D' ) )
            // EolLexerRules.g:46:30: ( 'f' | 'F' | 'd' | 'D' )
            {
            if ( input.LA(1)=='D'||input.LA(1)=='F'||input.LA(1)=='d'||input.LA(1)=='f' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end FLOAT_TYPE_SUFFIX

    // $ANTLR start INT
    public final void mINT() throws RecognitionException {
        try {
            int _type = INT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:48:5: ( ( DIGIT )+ ( 'l' | 'L' | ( ( '.' DIGIT )=> '.' ( DIGIT )+ ( EXPONENT )? ( FLOAT_TYPE_SUFFIX )? ) | ( EXPONENT ( FLOAT_TYPE_SUFFIX )? ) | ( FLOAT_TYPE_SUFFIX ) )? )
            // EolLexerRules.g:48:7: ( DIGIT )+ ( 'l' | 'L' | ( ( '.' DIGIT )=> '.' ( DIGIT )+ ( EXPONENT )? ( FLOAT_TYPE_SUFFIX )? ) | ( EXPONENT ( FLOAT_TYPE_SUFFIX )? ) | ( FLOAT_TYPE_SUFFIX ) )?
            {
            // EolLexerRules.g:48:7: ( DIGIT )+
            int cnt3=0;
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( ((LA3_0>='0' && LA3_0<='9')) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // EolLexerRules.g:48:8: DIGIT
            	    {
            	    mDIGIT(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    if ( cnt3 >= 1 ) break loop3;
            	    if (state.backtracking>0) {state.failed=true; return ;}
                        EarlyExitException eee =
                            new EarlyExitException(3, input);
                        throw eee;
                }
                cnt3++;
            } while (true);

            // EolLexerRules.g:48:16: ( 'l' | 'L' | ( ( '.' DIGIT )=> '.' ( DIGIT )+ ( EXPONENT )? ( FLOAT_TYPE_SUFFIX )? ) | ( EXPONENT ( FLOAT_TYPE_SUFFIX )? ) | ( FLOAT_TYPE_SUFFIX ) )?
            int alt8=6;
            int LA8_0 = input.LA(1);

            if ( (LA8_0=='l') ) {
                alt8=1;
            }
            else if ( (LA8_0=='L') ) {
                alt8=2;
            }
            else if ( (LA8_0=='.') && (synpred1_EolLexerRules())) {
                alt8=3;
            }
            else if ( (LA8_0=='E'||LA8_0=='e') ) {
                alt8=4;
            }
            else if ( (LA8_0=='D'||LA8_0=='F'||LA8_0=='d'||LA8_0=='f') ) {
                alt8=5;
            }
            switch (alt8) {
                case 1 :
                    // EolLexerRules.g:48:17: 'l'
                    {
                    match('l'); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EolLexerRules.g:48:21: 'L'
                    {
                    match('L'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // EolLexerRules.g:49:4: ( ( '.' DIGIT )=> '.' ( DIGIT )+ ( EXPONENT )? ( FLOAT_TYPE_SUFFIX )? )
                    {
                    // EolLexerRules.g:49:4: ( ( '.' DIGIT )=> '.' ( DIGIT )+ ( EXPONENT )? ( FLOAT_TYPE_SUFFIX )? )
                    // EolLexerRules.g:49:5: ( '.' DIGIT )=> '.' ( DIGIT )+ ( EXPONENT )? ( FLOAT_TYPE_SUFFIX )?
                    {
                    match('.'); if (state.failed) return ;
                    // EolLexerRules.g:49:32: ( DIGIT )+
                    int cnt4=0;
                    loop4:
                    do {
                        int alt4=2;
                        int LA4_0 = input.LA(1);

                        if ( ((LA4_0>='0' && LA4_0<='9')) ) {
                            alt4=1;
                        }


                        switch (alt4) {
                    	case 1 :
                    	    // EolLexerRules.g:49:33: DIGIT
                    	    {
                    	    mDIGIT(); if (state.failed) return ;

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt4 >= 1 ) break loop4;
                    	    if (state.backtracking>0) {state.failed=true; return ;}
                                EarlyExitException eee =
                                    new EarlyExitException(4, input);
                                throw eee;
                        }
                        cnt4++;
                    } while (true);

                    if ( state.backtracking==0 ) {
                      _type = FLOAT;
                    }
                    // EolLexerRules.g:49:58: ( EXPONENT )?
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='E'||LA5_0=='e') ) {
                        alt5=1;
                    }
                    switch (alt5) {
                        case 1 :
                            // EolLexerRules.g:49:58: EXPONENT
                            {
                            mEXPONENT(); if (state.failed) return ;

                            }
                            break;

                    }

                    // EolLexerRules.g:49:68: ( FLOAT_TYPE_SUFFIX )?
                    int alt6=2;
                    int LA6_0 = input.LA(1);

                    if ( (LA6_0=='D'||LA6_0=='F'||LA6_0=='d'||LA6_0=='f') ) {
                        alt6=1;
                    }
                    switch (alt6) {
                        case 1 :
                            // EolLexerRules.g:49:68: FLOAT_TYPE_SUFFIX
                            {
                            mFLOAT_TYPE_SUFFIX(); if (state.failed) return ;

                            }
                            break;

                    }


                    }


                    }
                    break;
                case 4 :
                    // EolLexerRules.g:50:4: ( EXPONENT ( FLOAT_TYPE_SUFFIX )? )
                    {
                    // EolLexerRules.g:50:4: ( EXPONENT ( FLOAT_TYPE_SUFFIX )? )
                    // EolLexerRules.g:50:5: EXPONENT ( FLOAT_TYPE_SUFFIX )?
                    {
                    mEXPONENT(); if (state.failed) return ;
                    // EolLexerRules.g:50:14: ( FLOAT_TYPE_SUFFIX )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='D'||LA7_0=='F'||LA7_0=='d'||LA7_0=='f') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // EolLexerRules.g:50:14: FLOAT_TYPE_SUFFIX
                            {
                            mFLOAT_TYPE_SUFFIX(); if (state.failed) return ;

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                      _type = FLOAT;
                    }

                    }


                    }
                    break;
                case 5 :
                    // EolLexerRules.g:51:4: ( FLOAT_TYPE_SUFFIX )
                    {
                    // EolLexerRules.g:51:4: ( FLOAT_TYPE_SUFFIX )
                    // EolLexerRules.g:51:5: FLOAT_TYPE_SUFFIX
                    {
                    mFLOAT_TYPE_SUFFIX(); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      _type = FLOAT;
                    }

                    }


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end INT

    // $ANTLR start POINT
    public final void mPOINT() throws RecognitionException {
        try {
            int _type = POINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:54:7: ( '.' )
            // EolLexerRules.g:54:9: '.'
            {
            match('.'); if (state.failed) return ;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end POINT

    // $ANTLR start POINT_POINT
    public final void mPOINT_POINT() throws RecognitionException {
        try {
            int _type = POINT_POINT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:56:13: ( '..' )
            // EolLexerRules.g:56:15: '..'
            {
            match(".."); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end POINT_POINT

    // $ANTLR start ARROW
    public final void mARROW() throws RecognitionException {
        try {
            int _type = ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:58:7: ( '->' )
            // EolLexerRules.g:58:9: '->'
            {
            match("->"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end ARROW

    // $ANTLR start NAVIGATION
    public final void mNAVIGATION() throws RecognitionException {
        try {
            int _type = NAVIGATION;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:60:12: ( '?.' )
            // EolLexerRules.g:60:14: '?.'
            {
            match("?."); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end NAVIGATION

    // $ANTLR start BOOLEAN
    public final void mBOOLEAN() throws RecognitionException {
        try {
            int _type = BOOLEAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:63:2: ( 'true' | 'false' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='t') ) {
                alt9=1;
            }
            else if ( (LA9_0=='f') ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // EolLexerRules.g:63:4: 'true'
                    {
                    match("true"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // EolLexerRules.g:63:13: 'false'
                    {
                    match("false"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end BOOLEAN

    // $ANTLR start STRING
    public final void mSTRING() throws RecognitionException {
        try {
            int _type = STRING;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:67:5: ( ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\'' | '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' ) )
            // EolLexerRules.g:67:9: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\'' | '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            {
            // EolLexerRules.g:67:9: ( '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\'' | '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"' )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0=='\'') ) {
                alt12=1;
            }
            else if ( (LA12_0=='\"') ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // EolLexerRules.g:67:10: '\\'' ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )* '\\''
                    {
                    match('\''); if (state.failed) return ;
                    // EolLexerRules.g:67:15: ( EscapeSequence | ~ ( '\\'' | '\\\\' ) )*
                    loop10:
                    do {
                        int alt10=3;
                        int LA10_0 = input.LA(1);

                        if ( (LA10_0=='\\') ) {
                            alt10=1;
                        }
                        else if ( ((LA10_0>='\u0000' && LA10_0<='&')||(LA10_0>='(' && LA10_0<='[')||(LA10_0>=']' && LA10_0<='\uFFFE')) ) {
                            alt10=2;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // EolLexerRules.g:67:17: EscapeSequence
                    	    {
                    	    mEscapeSequence(); if (state.failed) return ;

                    	    }
                    	    break;
                    	case 2 :
                    	    // EolLexerRules.g:67:34: ~ ( '\\'' | '\\\\' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='&')||(input.LA(1)>='(' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
                    	        input.consume();
                    	    state.failed=false;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop10;
                        }
                    } while (true);

                    match('\''); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EolLexerRules.g:67:57: '\"' ( EscapeSequence | ~ ( '\\\\' | '\"' ) )* '\"'
                    {
                    match('\"'); if (state.failed) return ;
                    // EolLexerRules.g:67:61: ( EscapeSequence | ~ ( '\\\\' | '\"' ) )*
                    loop11:
                    do {
                        int alt11=3;
                        int LA11_0 = input.LA(1);

                        if ( (LA11_0=='\\') ) {
                            alt11=1;
                        }
                        else if ( ((LA11_0>='\u0000' && LA11_0<='!')||(LA11_0>='#' && LA11_0<='[')||(LA11_0>=']' && LA11_0<='\uFFFE')) ) {
                            alt11=2;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // EolLexerRules.g:67:63: EscapeSequence
                    	    {
                    	    mEscapeSequence(); if (state.failed) return ;

                    	    }
                    	    break;
                    	case 2 :
                    	    // EolLexerRules.g:67:80: ~ ( '\\\\' | '\"' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
                    	        input.consume();
                    	    state.failed=false;
                    	    }
                    	    else {
                    	        if (state.backtracking>0) {state.failed=true; return ;}
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);

                    match('\"'); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              setText(getText().substring(1,getText().length() - 1));
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end STRING

    // $ANTLR start StrangeNameLiteral
    public final void mStrangeNameLiteral() throws RecognitionException {
        try {
            int _type = StrangeNameLiteral;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:72:5: ( '`' ( EscapeSequence | ~ ( '\\\\' | '`' | '\\n' | '\\r' ) )* '`' )
            // EolLexerRules.g:72:8: '`' ( EscapeSequence | ~ ( '\\\\' | '`' | '\\n' | '\\r' ) )* '`'
            {
            match('`'); if (state.failed) return ;
            // EolLexerRules.g:72:12: ( EscapeSequence | ~ ( '\\\\' | '`' | '\\n' | '\\r' ) )*
            loop13:
            do {
                int alt13=3;
                int LA13_0 = input.LA(1);

                if ( (LA13_0=='\\') ) {
                    alt13=1;
                }
                else if ( ((LA13_0>='\u0000' && LA13_0<='\t')||(LA13_0>='\u000B' && LA13_0<='\f')||(LA13_0>='\u000E' && LA13_0<='[')||(LA13_0>=']' && LA13_0<='_')||(LA13_0>='a' && LA13_0<='\uFFFE')) ) {
                    alt13=2;
                }


                switch (alt13) {
            	case 1 :
            	    // EolLexerRules.g:72:14: EscapeSequence
            	    {
            	    mEscapeSequence(); if (state.failed) return ;

            	    }
            	    break;
            	case 2 :
            	    // EolLexerRules.g:72:31: ~ ( '\\\\' | '`' | '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);

            match('`'); if (state.failed) return ;
            if ( state.backtracking==0 ) {
              _type=NAME; setText(getText().substring(1,getText().length() - 1));
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end StrangeNameLiteral

    // $ANTLR start CollectionTypeName
    public final void mCollectionTypeName() throws RecognitionException {
        try {
            int _type = CollectionTypeName;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:77:2: ( 'Bag' | 'Sequence' | 'Set' | 'OrderedSet' | 'Collection' | 'List' | 'ConcurrentBag' | 'ConcurrentSet' )
            int alt14=8;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // EolLexerRules.g:77:4: 'Bag'
                    {
                    match("Bag"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // EolLexerRules.g:77:10: 'Sequence'
                    {
                    match("Sequence"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // EolLexerRules.g:77:21: 'Set'
                    {
                    match("Set"); if (state.failed) return ;


                    }
                    break;
                case 4 :
                    // EolLexerRules.g:77:27: 'OrderedSet'
                    {
                    match("OrderedSet"); if (state.failed) return ;


                    }
                    break;
                case 5 :
                    // EolLexerRules.g:77:40: 'Collection'
                    {
                    match("Collection"); if (state.failed) return ;


                    }
                    break;
                case 6 :
                    // EolLexerRules.g:77:53: 'List'
                    {
                    match("List"); if (state.failed) return ;


                    }
                    break;
                case 7 :
                    // EolLexerRules.g:77:60: 'ConcurrentBag'
                    {
                    match("ConcurrentBag"); if (state.failed) return ;


                    }
                    break;
                case 8 :
                    // EolLexerRules.g:77:76: 'ConcurrentSet'
                    {
                    match("ConcurrentSet"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end CollectionTypeName

    // $ANTLR start MapTypeName
    public final void mMapTypeName() throws RecognitionException {
        try {
            int _type = MapTypeName;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:81:2: ( 'Map' | 'ConcurrentMap' | 'Tuple' )
            int alt15=3;
            switch ( input.LA(1) ) {
            case 'M':
                {
                alt15=1;
                }
                break;
            case 'C':
                {
                alt15=2;
                }
                break;
            case 'T':
                {
                alt15=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // EolLexerRules.g:81:4: 'Map'
                    {
                    match("Map"); if (state.failed) return ;


                    }
                    break;
                case 2 :
                    // EolLexerRules.g:81:10: 'ConcurrentMap'
                    {
                    match("ConcurrentMap"); if (state.failed) return ;


                    }
                    break;
                case 3 :
                    // EolLexerRules.g:81:26: 'Tuple'
                    {
                    match("Tuple"); if (state.failed) return ;


                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end MapTypeName

    // $ANTLR start SpecialTypeName
    public final void mSpecialTypeName() throws RecognitionException {
        try {
            int _type = SpecialTypeName;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:85:2: ( 'Native' )
            // EolLexerRules.g:85:4: 'Native'
            {
            match("Native"); if (state.failed) return ;


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end SpecialTypeName

    // $ANTLR start EscapeSequence
    public final void mEscapeSequence() throws RecognitionException {
        try {
            // EolLexerRules.g:90:5: ( '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' ) )
            // EolLexerRules.g:90:9: '\\\\' ( 'b' | 't' | 'n' | 'f' | 'r' | '\\\"' | '\\'' | '\\\\' )
            {
            match('\\'); if (state.failed) return ;
            if ( input.LA(1)=='\"'||input.LA(1)=='\''||input.LA(1)=='\\'||input.LA(1)=='b'||input.LA(1)=='f'||input.LA(1)=='n'||input.LA(1)=='r'||input.LA(1)=='t' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end EscapeSequence

    // $ANTLR start NAME
    public final void mNAME() throws RecognitionException {
        try {
            int _type = NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:106:5: ( ( Letter | SpecialNameChar ) ( Letter | JavaIDDigit | SpecialNameChar )* )
            // EolLexerRules.g:106:9: ( Letter | SpecialNameChar ) ( Letter | JavaIDDigit | SpecialNameChar )*
            {
            if ( input.LA(1)=='&'||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='^' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='~'||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

            // EolLexerRules.g:106:34: ( Letter | JavaIDDigit | SpecialNameChar )*
            loop16:
            do {
                int alt16=2;
                int LA16_0 = input.LA(1);

                if ( (LA16_0=='&'||(LA16_0>='0' && LA16_0<='9')||(LA16_0>='A' && LA16_0<='Z')||(LA16_0>='^' && LA16_0<='_')||(LA16_0>='a' && LA16_0<='z')||LA16_0=='~'||(LA16_0>='\u00C0' && LA16_0<='\u00D6')||(LA16_0>='\u00D8' && LA16_0<='\u00F6')||(LA16_0>='\u00F8' && LA16_0<='\u1FFF')||(LA16_0>='\u3040' && LA16_0<='\u318F')||(LA16_0>='\u3300' && LA16_0<='\u337F')||(LA16_0>='\u3400' && LA16_0<='\u3D2D')||(LA16_0>='\u4E00' && LA16_0<='\u9FFF')||(LA16_0>='\uF900' && LA16_0<='\uFAFF')) ) {
                    alt16=1;
                }


                switch (alt16) {
            	case 1 :
            	    // EolLexerRules.g:
            	    {
            	    if ( input.LA(1)=='&'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='A' && input.LA(1)<='Z')||(input.LA(1)>='^' && input.LA(1)<='_')||(input.LA(1)>='a' && input.LA(1)<='z')||input.LA(1)=='~'||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop16;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end NAME

    // $ANTLR start SpecialNameChar
    public final void mSpecialNameChar() throws RecognitionException {
        try {
            // EolLexerRules.g:111:2: ( '~' | '&' | '^' )
            // EolLexerRules.g:
            {
            if ( input.LA(1)=='&'||input.LA(1)=='^'||input.LA(1)=='~' ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end SpecialNameChar

    // $ANTLR start Letter
    public final void mLetter() throws RecognitionException {
        try {
            // EolLexerRules.g:120:5: ( '\\u0041' .. '\\u005a' | '\\u005f' | '\\u0061' .. '\\u007a' | '\\u00c0' .. '\\u00d6' | '\\u00d8' .. '\\u00f6' | '\\u00f8' .. '\\u00ff' | '\\u0100' .. '\\u1fff' | '\\u3040' .. '\\u318f' | '\\u3300' .. '\\u337f' | '\\u3400' .. '\\u3d2d' | '\\u4e00' .. '\\u9fff' | '\\uf900' .. '\\ufaff' )
            // EolLexerRules.g:
            {
            if ( (input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z')||(input.LA(1)>='\u00C0' && input.LA(1)<='\u00D6')||(input.LA(1)>='\u00D8' && input.LA(1)<='\u00F6')||(input.LA(1)>='\u00F8' && input.LA(1)<='\u1FFF')||(input.LA(1)>='\u3040' && input.LA(1)<='\u318F')||(input.LA(1)>='\u3300' && input.LA(1)<='\u337F')||(input.LA(1)>='\u3400' && input.LA(1)<='\u3D2D')||(input.LA(1)>='\u4E00' && input.LA(1)<='\u9FFF')||(input.LA(1)>='\uF900' && input.LA(1)<='\uFAFF') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end Letter

    // $ANTLR start JavaIDDigit
    public final void mJavaIDDigit() throws RecognitionException {
        try {
            // EolLexerRules.g:136:5: ( '\\u0030' .. '\\u0039' | '\\u0660' .. '\\u0669' | '\\u06f0' .. '\\u06f9' | '\\u0966' .. '\\u096f' | '\\u09e6' .. '\\u09ef' | '\\u0a66' .. '\\u0a6f' | '\\u0ae6' .. '\\u0aef' | '\\u0b66' .. '\\u0b6f' | '\\u0be7' .. '\\u0bef' | '\\u0c66' .. '\\u0c6f' | '\\u0ce6' .. '\\u0cef' | '\\u0d66' .. '\\u0d6f' | '\\u0e50' .. '\\u0e59' | '\\u0ed0' .. '\\u0ed9' | '\\u1040' .. '\\u1049' )
            // EolLexerRules.g:
            {
            if ( (input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='\u0660' && input.LA(1)<='\u0669')||(input.LA(1)>='\u06F0' && input.LA(1)<='\u06F9')||(input.LA(1)>='\u0966' && input.LA(1)<='\u096F')||(input.LA(1)>='\u09E6' && input.LA(1)<='\u09EF')||(input.LA(1)>='\u0A66' && input.LA(1)<='\u0A6F')||(input.LA(1)>='\u0AE6' && input.LA(1)<='\u0AEF')||(input.LA(1)>='\u0B66' && input.LA(1)<='\u0B6F')||(input.LA(1)>='\u0BE7' && input.LA(1)<='\u0BEF')||(input.LA(1)>='\u0C66' && input.LA(1)<='\u0C6F')||(input.LA(1)>='\u0CE6' && input.LA(1)<='\u0CEF')||(input.LA(1)>='\u0D66' && input.LA(1)<='\u0D6F')||(input.LA(1)>='\u0E50' && input.LA(1)<='\u0E59')||(input.LA(1)>='\u0ED0' && input.LA(1)<='\u0ED9')||(input.LA(1)>='\u1040' && input.LA(1)<='\u1049') ) {
                input.consume();
            state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return ;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end JavaIDDigit

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:153:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // EolLexerRules.g:153:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            // EolLexerRules.g:153:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            int alt17=5;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt17=1;
                }
                break;
            case '\r':
                {
                alt17=2;
                }
                break;
            case '\t':
                {
                alt17=3;
                }
                break;
            case '\f':
                {
                alt17=4;
                }
                break;
            case '\n':
                {
                alt17=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return ;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // EolLexerRules.g:154:2: ' '
                    {
                    match(' '); if (state.failed) return ;

                    }
                    break;
                case 2 :
                    // EolLexerRules.g:155:2: '\\r'
                    {
                    match('\r'); if (state.failed) return ;

                    }
                    break;
                case 3 :
                    // EolLexerRules.g:156:2: '\\t'
                    {
                    match('\t'); if (state.failed) return ;
                    if ( state.backtracking==0 ) {
                      input.setCharPositionInLine(input.getCharPositionInLine() + 3);
                    }

                    }
                    break;
                case 4 :
                    // EolLexerRules.g:157:3: '\\u000C'
                    {
                    match('\f'); if (state.failed) return ;

                    }
                    break;
                case 5 :
                    // EolLexerRules.g:158:3: '\\n'
                    {
                    match('\n'); if (state.failed) return ;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end WS

    // $ANTLR start COMMENT
    public final void mCOMMENT() throws RecognitionException {
        try {
            int _type = COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:163:5: ( ( '/*' ( options {greedy=false; } : . )* '*/' ) )
            // EolLexerRules.g:163:9: ( '/*' ( options {greedy=false; } : . )* '*/' )
            {
            // EolLexerRules.g:163:9: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // EolLexerRules.g:163:10: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); if (state.failed) return ;

            // EolLexerRules.g:163:15: ( options {greedy=false; } : . )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0=='*') ) {
                    int LA18_1 = input.LA(2);

                    if ( (LA18_1=='/') ) {
                        alt18=2;
                    }
                    else if ( ((LA18_1>='\u0000' && LA18_1<='.')||(LA18_1>='0' && LA18_1<='\uFFFE')) ) {
                        alt18=1;
                    }


                }
                else if ( ((LA18_0>='\u0000' && LA18_0<=')')||(LA18_0>='+' && LA18_0<='\uFFFE')) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // EolLexerRules.g:163:43: .
            	    {
            	    matchAny(); if (state.failed) return ;

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);

            match("*/"); if (state.failed) return ;


            }

            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end COMMENT

    // $ANTLR start LINE_COMMENT
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:167:5: ( ( '//' (~ ( '\\n' | '\\r' ) )* ) )
            // EolLexerRules.g:167:7: ( '//' (~ ( '\\n' | '\\r' ) )* )
            {
            // EolLexerRules.g:167:7: ( '//' (~ ( '\\n' | '\\r' ) )* )
            // EolLexerRules.g:167:8: '//' (~ ( '\\n' | '\\r' ) )*
            {
            match("//"); if (state.failed) return ;

            // EolLexerRules.g:167:13: (~ ( '\\n' | '\\r' ) )*
            loop19:
            do {
                int alt19=2;
                int LA19_0 = input.LA(1);

                if ( ((LA19_0>='\u0000' && LA19_0<='\t')||(LA19_0>='\u000B' && LA19_0<='\f')||(LA19_0>='\u000E' && LA19_0<='\uFFFE')) ) {
                    alt19=1;
                }


                switch (alt19) {
            	case 1 :
            	    // EolLexerRules.g:167:13: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop19;
                }
            } while (true);


            }

            if ( state.backtracking==0 ) {
              _channel=HIDDEN;
            }

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end LINE_COMMENT

    // $ANTLR start Annotation
    public final void mAnnotation() throws RecognitionException {
        try {
            int _type = Annotation;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // EolLexerRules.g:171:2: ( '@' (~ ( '\\n' | '\\r' ) )* )
            // EolLexerRules.g:171:4: '@' (~ ( '\\n' | '\\r' ) )*
            {
            match('@'); if (state.failed) return ;
            // EolLexerRules.g:171:8: (~ ( '\\n' | '\\r' ) )*
            loop20:
            do {
                int alt20=2;
                int LA20_0 = input.LA(1);

                if ( ((LA20_0>='\u0000' && LA20_0<='\t')||(LA20_0>='\u000B' && LA20_0<='\f')||(LA20_0>='\u000E' && LA20_0<='\uFFFE')) ) {
                    alt20=1;
                }


                switch (alt20) {
            	case 1 :
            	    // EolLexerRules.g:171:8: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();
            	    state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return ;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop20;
                }
            } while (true);


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end Annotation

    public void mTokens() throws RecognitionException {
        // EolLexerRules.g:1:8: ( INT | POINT | POINT_POINT | ARROW | NAVIGATION | BOOLEAN | STRING | StrangeNameLiteral | CollectionTypeName | MapTypeName | SpecialTypeName | NAME | WS | COMMENT | LINE_COMMENT | Annotation )
        int alt21=16;
        alt21 = dfa21.predict(input);
        switch (alt21) {
            case 1 :
                // EolLexerRules.g:1:10: INT
                {
                mINT(); if (state.failed) return ;

                }
                break;
            case 2 :
                // EolLexerRules.g:1:14: POINT
                {
                mPOINT(); if (state.failed) return ;

                }
                break;
            case 3 :
                // EolLexerRules.g:1:20: POINT_POINT
                {
                mPOINT_POINT(); if (state.failed) return ;

                }
                break;
            case 4 :
                // EolLexerRules.g:1:32: ARROW
                {
                mARROW(); if (state.failed) return ;

                }
                break;
            case 5 :
                // EolLexerRules.g:1:38: NAVIGATION
                {
                mNAVIGATION(); if (state.failed) return ;

                }
                break;
            case 6 :
                // EolLexerRules.g:1:49: BOOLEAN
                {
                mBOOLEAN(); if (state.failed) return ;

                }
                break;
            case 7 :
                // EolLexerRules.g:1:57: STRING
                {
                mSTRING(); if (state.failed) return ;

                }
                break;
            case 8 :
                // EolLexerRules.g:1:64: StrangeNameLiteral
                {
                mStrangeNameLiteral(); if (state.failed) return ;

                }
                break;
            case 9 :
                // EolLexerRules.g:1:83: CollectionTypeName
                {
                mCollectionTypeName(); if (state.failed) return ;

                }
                break;
            case 10 :
                // EolLexerRules.g:1:102: MapTypeName
                {
                mMapTypeName(); if (state.failed) return ;

                }
                break;
            case 11 :
                // EolLexerRules.g:1:114: SpecialTypeName
                {
                mSpecialTypeName(); if (state.failed) return ;

                }
                break;
            case 12 :
                // EolLexerRules.g:1:130: NAME
                {
                mNAME(); if (state.failed) return ;

                }
                break;
            case 13 :
                // EolLexerRules.g:1:135: WS
                {
                mWS(); if (state.failed) return ;

                }
                break;
            case 14 :
                // EolLexerRules.g:1:138: COMMENT
                {
                mCOMMENT(); if (state.failed) return ;

                }
                break;
            case 15 :
                // EolLexerRules.g:1:146: LINE_COMMENT
                {
                mLINE_COMMENT(); if (state.failed) return ;

                }
                break;
            case 16 :
                // EolLexerRules.g:1:159: Annotation
                {
                mAnnotation(); if (state.failed) return ;

                }
                break;

        }

    }

    // $ANTLR start synpred1_EolLexerRules
    public final void synpred1_EolLexerRules_fragment() throws RecognitionException {   
        // EolLexerRules.g:49:5: ( '.' DIGIT )
        // EolLexerRules.g:49:6: '.' DIGIT
        {
        match('.'); if (state.failed) return ;
        mDIGIT(); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred1_EolLexerRules

    public final boolean synpred1_EolLexerRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred1_EolLexerRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA14 dfa14 = new DFA14(this);
    protected DFA21 dfa21 = new DFA21(this);
    static final String DFA14_eotS =
        "\25\uffff";
    static final String DFA14_eofS =
        "\25\uffff";
    static final String DFA14_minS =
        "\1\102\1\uffff\1\145\1\uffff\1\157\1\uffff\1\161\1\154\3\uffff\1"+
        "\143\1\165\2\162\1\145\1\156\1\164\1\102\2\uffff";
    static final String DFA14_maxS =
        "\1\123\1\uffff\1\145\1\uffff\1\157\1\uffff\1\164\1\156\3\uffff\1"+
        "\143\1\165\2\162\1\145\1\156\1\164\1\123\2\uffff";
    static final String DFA14_acceptS =
        "\1\uffff\1\1\1\uffff\1\4\1\uffff\1\6\2\uffff\1\2\1\3\1\5\10\uffff"+
        "\1\7\1\10";
    static final String DFA14_specialS =
        "\25\uffff}>";
    static final String[] DFA14_transitionS = {
            "\1\1\1\4\10\uffff\1\5\2\uffff\1\3\3\uffff\1\2",
            "",
            "\1\6",
            "",
            "\1\7",
            "",
            "\1\10\2\uffff\1\11",
            "\1\12\1\uffff\1\13",
            "",
            "",
            "",
            "\1\14",
            "\1\15",
            "\1\16",
            "\1\17",
            "\1\20",
            "\1\21",
            "\1\22",
            "\1\23\20\uffff\1\24",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "76:1: CollectionTypeName : ( 'Bag' | 'Sequence' | 'Set' | 'OrderedSet' | 'Collection' | 'List' | 'ConcurrentBag' | 'ConcurrentSet' );";
        }
    }
    static final String DFA21_eotS =
        "\2\uffff\1\26\2\uffff\2\21\2\uffff\10\21\6\uffff\12\21\2\uffff\2"+
        "\21\1\61\1\21\1\61\4\21\1\67\2\21\1\72\1\21\1\uffff\4\21\1\61\1"+
        "\uffff\2\21\1\uffff\1\72\4\21\1\67\5\21\1\113\4\21\1\uffff\1\61"+
        "\6\21\2\61\7\21\2\61\1\67";
    static final String DFA21_eofS =
        "\137\uffff";
    static final String DFA21_minS =
        "\1\11\1\uffff\1\56\2\uffff\1\162\1\141\2\uffff\1\141\1\145\1\162"+
        "\1\157\1\151\1\141\1\165\1\141\2\uffff\1\52\3\uffff\1\165\1\154"+
        "\1\147\1\161\1\144\1\154\1\163\2\160\1\164\2\uffff\1\145\1\163\1"+
        "\46\1\165\1\46\1\145\1\154\1\143\1\164\1\46\1\154\1\151\1\46\1\145"+
        "\1\uffff\1\145\1\162\1\145\1\165\1\46\1\uffff\1\145\1\166\1\uffff"+
        "\1\46\1\156\1\145\1\143\1\162\1\46\1\145\1\143\1\144\1\164\1\162"+
        "\1\46\1\145\1\123\1\151\1\145\1\uffff\1\46\1\145\1\157\1\156\1\164"+
        "\1\156\1\164\2\46\1\102\1\141\1\145\1\141\1\147\1\164\1\160\3\46";
    static final String DFA21_maxS =
        "\1\ufaff\1\uffff\1\56\2\uffff\1\162\1\141\2\uffff\1\141\1\145\1"+
        "\162\1\157\1\151\1\141\1\165\1\141\2\uffff\1\57\3\uffff\1\165\1"+
        "\154\1\147\1\164\1\144\1\156\1\163\2\160\1\164\2\uffff\1\145\1\163"+
        "\1\ufaff\1\165\1\ufaff\1\145\1\154\1\143\1\164\1\ufaff\1\154\1\151"+
        "\1\ufaff\1\145\1\uffff\1\145\1\162\1\145\1\165\1\ufaff\1\uffff\1"+
        "\145\1\166\1\uffff\1\ufaff\1\156\1\145\1\143\1\162\1\ufaff\1\145"+
        "\1\143\1\144\1\164\1\162\1\ufaff\1\145\1\123\1\151\1\145\1\uffff"+
        "\1\ufaff\1\145\1\157\1\156\1\164\1\156\1\164\2\ufaff\1\123\1\141"+
        "\1\145\1\141\1\147\1\164\1\160\3\ufaff";
    static final String DFA21_acceptS =
        "\1\uffff\1\1\1\uffff\1\4\1\5\2\uffff\1\7\1\10\10\uffff\1\14\1\15"+
        "\1\uffff\1\20\1\3\1\2\12\uffff\1\16\1\17\16\uffff\1\11\5\uffff\1"+
        "\12\2\uffff\1\6\20\uffff\1\13\23\uffff";
    static final String DFA21_specialS =
        "\137\uffff}>";
    static final String[] DFA21_transitionS = {
            "\2\22\1\uffff\2\22\22\uffff\1\22\1\uffff\1\7\3\uffff\1\21\1"+
            "\7\5\uffff\1\3\1\2\1\23\12\1\5\uffff\1\4\1\24\1\21\1\11\1\14"+
            "\10\21\1\15\1\16\1\20\1\13\3\21\1\12\1\17\6\21\3\uffff\2\21"+
            "\1\10\5\21\1\6\15\21\1\5\6\21\3\uffff\1\21\101\uffff\27\21\1"+
            "\uffff\37\21\1\uffff\u1f08\21\u1040\uffff\u0150\21\u0170\uffff"+
            "\u0080\21\u0080\uffff\u092e\21\u10d2\uffff\u5200\21\u5900\uffff"+
            "\u0200\21",
            "",
            "\1\25",
            "",
            "",
            "\1\27",
            "\1\30",
            "",
            "",
            "\1\31",
            "\1\32",
            "\1\33",
            "\1\34",
            "\1\35",
            "\1\36",
            "\1\37",
            "\1\40",
            "",
            "",
            "\1\41\4\uffff\1\42",
            "",
            "",
            "",
            "\1\43",
            "\1\44",
            "\1\45",
            "\1\46\2\uffff\1\47",
            "\1\50",
            "\1\51\1\uffff\1\52",
            "\1\53",
            "\1\54",
            "\1\55",
            "\1\56",
            "",
            "",
            "\1\57",
            "\1\60",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\62",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\63",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\70",
            "\1\71",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\73",
            "",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "",
            "\1\100",
            "\1\101",
            "",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\106",
            "\1\107",
            "\1\110",
            "\1\111",
            "\1\112",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\117",
            "",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\120",
            "\1\121",
            "\1\122",
            "\1\123",
            "\1\124",
            "\1\125",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\126\12\uffff\1\130\5\uffff\1\127",
            "\1\131",
            "\1\132",
            "\1\133",
            "\1\134",
            "\1\135",
            "\1\136",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21",
            "\1\21\11\uffff\12\21\7\uffff\32\21\3\uffff\2\21\1\uffff\32"+
            "\21\3\uffff\1\21\101\uffff\27\21\1\uffff\37\21\1\uffff\u1f08"+
            "\21\u1040\uffff\u0150\21\u0170\uffff\u0080\21\u0080\uffff\u092e"+
            "\21\u10d2\uffff\u5200\21\u5900\uffff\u0200\21"
    };

    static final short[] DFA21_eot = DFA.unpackEncodedString(DFA21_eotS);
    static final short[] DFA21_eof = DFA.unpackEncodedString(DFA21_eofS);
    static final char[] DFA21_min = DFA.unpackEncodedStringToUnsignedChars(DFA21_minS);
    static final char[] DFA21_max = DFA.unpackEncodedStringToUnsignedChars(DFA21_maxS);
    static final short[] DFA21_accept = DFA.unpackEncodedString(DFA21_acceptS);
    static final short[] DFA21_special = DFA.unpackEncodedString(DFA21_specialS);
    static final short[][] DFA21_transition;

    static {
        int numStates = DFA21_transitionS.length;
        DFA21_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA21_transition[i] = DFA.unpackEncodedString(DFA21_transitionS[i]);
        }
    }

    class DFA21 extends DFA {

        public DFA21(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 21;
            this.eot = DFA21_eot;
            this.eof = DFA21_eof;
            this.min = DFA21_min;
            this.max = DFA21_max;
            this.accept = DFA21_accept;
            this.special = DFA21_special;
            this.transition = DFA21_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( INT | POINT | POINT_POINT | ARROW | NAVIGATION | BOOLEAN | STRING | StrangeNameLiteral | CollectionTypeName | MapTypeName | SpecialTypeName | NAME | WS | COMMENT | LINE_COMMENT | Annotation );";
        }
    }
 

}
