// $ANTLR 3.1b1 Hutn.g 2024-12-09 11:02:50

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class HutnLexer extends Lexer {
    public static final int CLS_LVL_ATTRIBUTE=8;
    public static final int TEXTUAL_VALUE=12;
    public static final int ASSOC_INSTANCE=9;
    public static final int ESC=21;
    public static final int REFERENCE=10;
    public static final int ASSIGNMENT=15;
    public static final int ADJECTIVE=7;
    public static final int WS=24;
    public static final int EOF=-1;
    public static final int COMMA=17;
    public static final int ID_START_LETTER=19;
    public static final int NULL=6;
    public static final int TRUE=4;
    public static final int LBRACKET=13;
    public static final int NUMERIC_VALUE=18;
    public static final int RBRACKET=14;
    public static final int TEXT_LETTER=22;
    public static final int ID_LETTER=20;
    public static final int DIGIT=23;
    public static final int NAME=11;
    public static final int T__27=27;
    public static final int T__28=28;
    public static final int ADJECTIVE_PREFIX=16;
    public static final int ML_COMMENT=25;
    public static final int SL_COMMENT=26;
    public static final int FALSE=5;

    // delegates
    // delegators

    public HutnLexer() {;} 
    public HutnLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public HutnLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "Hutn.g"; }

    // $ANTLR start TRUE
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:46:6: ( 'true' )
            // Hutn.g:46:8: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end TRUE

    // $ANTLR start FALSE
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:47:7: ( 'false' )
            // Hutn.g:47:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end FALSE

    // $ANTLR start NULL
    public final void mNULL() throws RecognitionException {
        try {
            int _type = NULL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:48:6: ( 'null' )
            // Hutn.g:48:8: 'null'
            {
            match("null"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end NULL

    // $ANTLR start T__27
    public final void mT__27() throws RecognitionException {
        try {
            int _type = T__27;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:49:7: ( ';' )
            // Hutn.g:49:9: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__27

    // $ANTLR start T__28
    public final void mT__28() throws RecognitionException {
        try {
            int _type = T__28;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:50:7: ( '.' )
            // Hutn.g:50:9: '.'
            {
            match('.'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end T__28

    // $ANTLR start COMMA
    public final void mCOMMA() throws RecognitionException {
        try {
            int _type = COMMA;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:156:14: ( ',' )
            // Hutn.g:156:16: ','
            {
            match(','); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end COMMA

    // $ANTLR start LBRACKET
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:157:14: ( '{' )
            // Hutn.g:157:16: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end LBRACKET

    // $ANTLR start RBRACKET
    public final void mRBRACKET() throws RecognitionException {
        try {
            int _type = RBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:158:14: ( '}' )
            // Hutn.g:158:16: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end RBRACKET

    // $ANTLR start ASSIGNMENT
    public final void mASSIGNMENT() throws RecognitionException {
        try {
            int _type = ASSIGNMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:159:13: ( ':' | '=' )
            // Hutn.g:
            {
            if ( input.LA(1)==':'||input.LA(1)=='=' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end ASSIGNMENT

    // $ANTLR start NAME
    public final void mNAME() throws RecognitionException {
        try {
            int _type = NAME;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:160:14: ( ID_START_LETTER ( ID_LETTER )* )
            // Hutn.g:160:16: ID_START_LETTER ( ID_LETTER )*
            {
            mID_START_LETTER(); 
            // Hutn.g:160:32: ( ID_LETTER )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='#'||LA1_0=='-'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='@' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Hutn.g:160:32: ID_LETTER
            	    {
            	    mID_LETTER(); 

            	    }
            	    break;

            	default :
            	    break loop1;
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

    // $ANTLR start TEXTUAL_VALUE
    public final void mTEXTUAL_VALUE() throws RecognitionException {
        try {
            int _type = TEXTUAL_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            Token escaped=null;
            Token normal=null;

            StringBuilder lBuf = new StringBuilder();
            // Hutn.g:165:5: ( '\"' (escaped= ESC | normal= TEXT_LETTER )* '\"' )
            // Hutn.g:166:12: '\"' (escaped= ESC | normal= TEXT_LETTER )* '\"'
            {
            match('\"'); 
            // Hutn.g:167:12: (escaped= ESC | normal= TEXT_LETTER )*
            loop2:
            do {
                int alt2=3;
                int LA2_0 = input.LA(1);

                if ( (LA2_0=='\\') ) {
                    alt2=1;
                }
                else if ( ((LA2_0>='\u0000' && LA2_0<='!')||(LA2_0>='#' && LA2_0<='[')||(LA2_0>=']' && LA2_0<='\uFFFE')) ) {
                    alt2=2;
                }


                switch (alt2) {
            	case 1 :
            	    // Hutn.g:168:14: escaped= ESC
            	    {
            	    int escapedStart196 = getCharIndex();
            	    mESC(); 
            	    escaped = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, escapedStart196, getCharIndex()-1);
            	    lBuf.append(getText());

            	    }
            	    break;
            	case 2 :
            	    // Hutn.g:170:14: normal= TEXT_LETTER
            	    {
            	    int normalStart239 = getCharIndex();
            	    mTEXT_LETTER(); 
            	    normal = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, normalStart239, getCharIndex()-1);
            	    lBuf.append(normal.getText());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            match('\"'); 
            setText('"' + lBuf.toString() + '"');

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end TEXTUAL_VALUE

    // $ANTLR start NUMERIC_VALUE
    public final void mNUMERIC_VALUE() throws RecognitionException {
        try {
            int _type = NUMERIC_VALUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:177:14: ( ( '-' | '+' )? ( DIGIT )+ ( '.' ( DIGIT )+ )? ( ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+ )? )
            // Hutn.g:177:16: ( '-' | '+' )? ( DIGIT )+ ( '.' ( DIGIT )+ )? ( ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+ )?
            {
            // Hutn.g:177:16: ( '-' | '+' )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0=='+'||LA3_0=='-') ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // Hutn.g:
                    {
                    if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}


                    }
                    break;

            }

            // Hutn.g:177:27: ( DIGIT )+
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
            	    // Hutn.g:177:27: DIGIT
            	    {
            	    mDIGIT(); 

            	    }
            	    break;

            	default :
            	    if ( cnt4 >= 1 ) break loop4;
                        EarlyExitException eee =
                            new EarlyExitException(4, input);
                        throw eee;
                }
                cnt4++;
            } while (true);

            // Hutn.g:177:34: ( '.' ( DIGIT )+ )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='.') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // Hutn.g:177:35: '.' ( DIGIT )+
                    {
                    match('.'); 
                    // Hutn.g:177:39: ( DIGIT )+
                    int cnt5=0;
                    loop5:
                    do {
                        int alt5=2;
                        int LA5_0 = input.LA(1);

                        if ( ((LA5_0>='0' && LA5_0<='9')) ) {
                            alt5=1;
                        }


                        switch (alt5) {
                    	case 1 :
                    	    // Hutn.g:177:39: DIGIT
                    	    {
                    	    mDIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt5 >= 1 ) break loop5;
                                EarlyExitException eee =
                                    new EarlyExitException(5, input);
                                throw eee;
                        }
                        cnt5++;
                    } while (true);


                    }
                    break;

            }

            // Hutn.g:177:48: ( ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+ )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='E'||LA9_0=='e') ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Hutn.g:177:49: ( 'e' | 'E' ) ( '+' | '-' )? ( DIGIT )+
                    {
                    if ( input.LA(1)=='E'||input.LA(1)=='e' ) {
                        input.consume();

                    }
                    else {
                        MismatchedSetException mse = new MismatchedSetException(null,input);
                        recover(mse);
                        throw mse;}

                    // Hutn.g:177:59: ( '+' | '-' )?
                    int alt7=2;
                    int LA7_0 = input.LA(1);

                    if ( (LA7_0=='+'||LA7_0=='-') ) {
                        alt7=1;
                    }
                    switch (alt7) {
                        case 1 :
                            // Hutn.g:
                            {
                            if ( input.LA(1)=='+'||input.LA(1)=='-' ) {
                                input.consume();

                            }
                            else {
                                MismatchedSetException mse = new MismatchedSetException(null,input);
                                recover(mse);
                                throw mse;}


                            }
                            break;

                    }

                    // Hutn.g:177:70: ( DIGIT )+
                    int cnt8=0;
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // Hutn.g:177:70: DIGIT
                    	    {
                    	    mDIGIT(); 

                    	    }
                    	    break;

                    	default :
                    	    if ( cnt8 >= 1 ) break loop8;
                                EarlyExitException eee =
                                    new EarlyExitException(8, input);
                                throw eee;
                        }
                        cnt8++;
                    } while (true);


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
    // $ANTLR end NUMERIC_VALUE

    // $ANTLR start ADJECTIVE_PREFIX
    public final void mADJECTIVE_PREFIX() throws RecognitionException {
        try {
            int _type = ADJECTIVE_PREFIX;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:178:17: ( '~' | '#' )
            // Hutn.g:
            {
            if ( input.LA(1)=='#'||input.LA(1)=='~' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end ADJECTIVE_PREFIX

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:180:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // Hutn.g:180:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            // Hutn.g:180:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            int alt10=5;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt10=1;
                }
                break;
            case '\r':
                {
                alt10=2;
                }
                break;
            case '\t':
                {
                alt10=3;
                }
                break;
            case '\f':
                {
                alt10=4;
                }
                break;
            case '\n':
                {
                alt10=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }

            switch (alt10) {
                case 1 :
                    // Hutn.g:181:2: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 2 :
                    // Hutn.g:182:2: '\\r'
                    {
                    match('\r'); 

                    }
                    break;
                case 3 :
                    // Hutn.g:183:2: '\\t'
                    {
                    match('\t'); 
                    input.setCharPositionInLine(input.getCharPositionInLine() + 3);

                    }
                    break;
                case 4 :
                    // Hutn.g:184:3: '\\u000C'
                    {
                    match('\f'); 

                    }
                    break;
                case 5 :
                    // Hutn.g:185:3: '\\n'
                    {
                    match('\n'); 

                    }
                    break;

            }

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end WS

    // $ANTLR start ML_COMMENT
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:190:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // Hutn.g:190:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // Hutn.g:190:14: ( options {greedy=false; } : . )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='*') ) {
                    int LA11_1 = input.LA(2);

                    if ( (LA11_1=='/') ) {
                        alt11=2;
                    }
                    else if ( ((LA11_1>='\u0000' && LA11_1<='.')||(LA11_1>='0' && LA11_1<='\uFFFE')) ) {
                        alt11=1;
                    }


                }
                else if ( ((LA11_0>='\u0000' && LA11_0<=')')||(LA11_0>='+' && LA11_0<='\uFFFE')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // Hutn.g:190:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end ML_COMMENT

    // $ANTLR start SL_COMMENT
    public final void mSL_COMMENT() throws RecognitionException {
        try {
            int _type = SL_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // Hutn.g:194:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // Hutn.g:194:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // Hutn.g:194:12: (~ ( '\\n' | '\\r' ) )*
            loop12:
            do {
                int alt12=2;
                int LA12_0 = input.LA(1);

                if ( ((LA12_0>='\u0000' && LA12_0<='\t')||(LA12_0>='\u000B' && LA12_0<='\f')||(LA12_0>='\u000E' && LA12_0<='\uFFFE')) ) {
                    alt12=1;
                }


                switch (alt12) {
            	case 1 :
            	    // Hutn.g:194:12: ~ ( '\\n' | '\\r' )
            	    {
            	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFE') ) {
            	        input.consume();

            	    }
            	    else {
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        recover(mse);
            	        throw mse;}


            	    }
            	    break;

            	default :
            	    break loop12;
                }
            } while (true);

            // Hutn.g:194:26: ( '\\r' )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0=='\r') ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // Hutn.g:194:26: '\\r'
                    {
                    match('\r'); 

                    }
                    break;

            }

            match('\n'); 
            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end SL_COMMENT

    // $ANTLR start ID_START_LETTER
    public final void mID_START_LETTER() throws RecognitionException {
        try {
            // Hutn.g:199:2: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '@' )
            // Hutn.g:
            {
            if ( (input.LA(1)>='@' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end ID_START_LETTER

    // $ANTLR start ID_LETTER
    public final void mID_LETTER() throws RecognitionException {
        try {
            // Hutn.g:207:5: ( ID_START_LETTER | '0' .. '9' | '-' | '#' )
            // Hutn.g:
            {
            if ( input.LA(1)=='#'||input.LA(1)=='-'||(input.LA(1)>='0' && input.LA(1)<='9')||(input.LA(1)>='@' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end ID_LETTER

    // $ANTLR start ESC
    public final void mESC() throws RecognitionException {
        try {
            // Hutn.g:215:5: ( '\\\\' ( '\"' | '\\\\' ) )
            // Hutn.g:215:9: '\\\\' ( '\"' | '\\\\' )
            {
            match('\\'); 
            // Hutn.g:216:9: ( '\"' | '\\\\' )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0=='\"') ) {
                alt14=1;
            }
            else if ( (LA14_0=='\\') ) {
                alt14=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // Hutn.g:217:12: '\"'
                    {
                    match('\"'); 
                    setText("\"");

                    }
                    break;
                case 2 :
                    // Hutn.g:218:12: '\\\\'
                    {
                    match('\\'); 
                    setText("\\");

                    }
                    break;

            }


            }

        }
        finally {
        }
    }
    // $ANTLR end ESC

    // $ANTLR start TEXT_LETTER
    public final void mTEXT_LETTER() throws RecognitionException {
        try {
            // Hutn.g:224:5: (~ ( '\\\"' | '\\\\' ) )
            // Hutn.g:224:10: ~ ( '\\\"' | '\\\\' )
            {
            if ( (input.LA(1)>='\u0000' && input.LA(1)<='!')||(input.LA(1)>='#' && input.LA(1)<='[')||(input.LA(1)>=']' && input.LA(1)<='\uFFFE') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end TEXT_LETTER

    // $ANTLR start DIGIT
    public final void mDIGIT() throws RecognitionException {
        try {
            // Hutn.g:228:7: ( '0' .. '9' )
            // Hutn.g:228:9: '0' .. '9'
            {
            matchRange('0','9'); 

            }

        }
        finally {
        }
    }
    // $ANTLR end DIGIT

    public void mTokens() throws RecognitionException {
        // Hutn.g:1:8: ( TRUE | FALSE | NULL | T__27 | T__28 | COMMA | LBRACKET | RBRACKET | ASSIGNMENT | NAME | TEXTUAL_VALUE | NUMERIC_VALUE | ADJECTIVE_PREFIX | WS | ML_COMMENT | SL_COMMENT )
        int alt15=16;
        alt15 = dfa15.predict(input);
        switch (alt15) {
            case 1 :
                // Hutn.g:1:10: TRUE
                {
                mTRUE(); 

                }
                break;
            case 2 :
                // Hutn.g:1:15: FALSE
                {
                mFALSE(); 

                }
                break;
            case 3 :
                // Hutn.g:1:21: NULL
                {
                mNULL(); 

                }
                break;
            case 4 :
                // Hutn.g:1:26: T__27
                {
                mT__27(); 

                }
                break;
            case 5 :
                // Hutn.g:1:32: T__28
                {
                mT__28(); 

                }
                break;
            case 6 :
                // Hutn.g:1:38: COMMA
                {
                mCOMMA(); 

                }
                break;
            case 7 :
                // Hutn.g:1:44: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 8 :
                // Hutn.g:1:53: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 9 :
                // Hutn.g:1:62: ASSIGNMENT
                {
                mASSIGNMENT(); 

                }
                break;
            case 10 :
                // Hutn.g:1:73: NAME
                {
                mNAME(); 

                }
                break;
            case 11 :
                // Hutn.g:1:78: TEXTUAL_VALUE
                {
                mTEXTUAL_VALUE(); 

                }
                break;
            case 12 :
                // Hutn.g:1:92: NUMERIC_VALUE
                {
                mNUMERIC_VALUE(); 

                }
                break;
            case 13 :
                // Hutn.g:1:106: ADJECTIVE_PREFIX
                {
                mADJECTIVE_PREFIX(); 

                }
                break;
            case 14 :
                // Hutn.g:1:123: WS
                {
                mWS(); 

                }
                break;
            case 15 :
                // Hutn.g:1:126: ML_COMMENT
                {
                mML_COMMENT(); 

                }
                break;
            case 16 :
                // Hutn.g:1:137: SL_COMMENT
                {
                mSL_COMMENT(); 

                }
                break;

        }

    }


    protected DFA15 dfa15 = new DFA15(this);
    static final String DFA15_eotS =
        "\1\uffff\3\12\14\uffff\3\12\2\uffff\3\12\1\33\1\12\1\35\1\uffff"+
        "\1\36\2\uffff";
    static final String DFA15_eofS =
        "\37\uffff";
    static final String DFA15_minS =
        "\1\11\1\162\1\141\1\165\13\uffff\1\52\1\165\2\154\2\uffff\1\145"+
        "\1\163\1\154\1\43\1\145\1\43\1\uffff\1\43\2\uffff";
    static final String DFA15_maxS =
        "\1\176\1\162\1\141\1\165\13\uffff\1\57\1\165\2\154\2\uffff\1\145"+
        "\1\163\1\154\1\172\1\145\1\172\1\uffff\1\172\2\uffff";
    static final String DFA15_acceptS =
        "\4\uffff\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\1\14\1\15\1\16\4\uffff"+
        "\1\17\1\20\6\uffff\1\1\1\uffff\1\3\1\2";
    static final String DFA15_specialS =
        "\37\uffff}>";
    static final String[] DFA15_transitionS = {
            "\2\16\1\uffff\2\16\22\uffff\1\16\1\uffff\1\13\1\15\7\uffff\1"+
            "\14\1\6\1\14\1\5\1\17\12\14\1\11\1\4\1\uffff\1\11\2\uffff\33"+
            "\12\4\uffff\1\12\1\uffff\5\12\1\2\7\12\1\3\5\12\1\1\6\12\1\7"+
            "\1\uffff\1\10\1\15",
            "\1\20",
            "\1\21",
            "\1\22",
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
            "\1\23\4\uffff\1\24",
            "\1\25",
            "\1\26",
            "\1\27",
            "",
            "",
            "\1\30",
            "\1\31",
            "\1\32",
            "\1\12\11\uffff\1\12\2\uffff\12\12\6\uffff\33\12\4\uffff\1\12"+
            "\1\uffff\32\12",
            "\1\34",
            "\1\12\11\uffff\1\12\2\uffff\12\12\6\uffff\33\12\4\uffff\1\12"+
            "\1\uffff\32\12",
            "",
            "\1\12\11\uffff\1\12\2\uffff\12\12\6\uffff\33\12\4\uffff\1\12"+
            "\1\uffff\32\12",
            "",
            ""
    };

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( TRUE | FALSE | NULL | T__27 | T__28 | COMMA | LBRACKET | RBRACKET | ASSIGNMENT | NAME | TEXTUAL_VALUE | NUMERIC_VALUE | ADJECTIVE_PREFIX | WS | ML_COMMENT | SL_COMMENT );";
        }
    }
 

}