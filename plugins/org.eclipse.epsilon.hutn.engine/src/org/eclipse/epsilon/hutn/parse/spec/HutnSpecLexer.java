// $ANTLR 3.1b1 C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g 2020-04-08 19:05:29

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
package org.eclipse.epsilon.hutn.parse.spec;	


import org.antlr.runtime.*;

public class HutnSpecLexer extends Lexer {
    public static final int ID_START_LETTER=10;
    public static final int TEXTUAL_VALUE=5;
    public static final int LBRACKET=6;
    public static final int RBRACKET=7;
    public static final int TEXT_LETTER=13;
    public static final int ID_LETTER=11;
    public static final int NAME=8;
    public static final int ESC=12;
    public static final int ML_COMMENT=15;
    public static final int ASSIGNMENT=9;
    public static final int SL_COMMENT=16;
    public static final int WS=14;
    public static final int EOF=-1;
    public static final int SPEC=4;

    // delegates
    // delegators

    public HutnSpecLexer() {;} 
    public HutnSpecLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public HutnSpecLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    @Override
	public String getGrammarFileName() { return "C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g"; }

    // $ANTLR start SPEC
    public final void mSPEC() throws RecognitionException {
        try {
            int _type = SPEC;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:93:14: ( '@Spec' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:93:16: '@Spec'
            {
            match("@Spec"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end SPEC

    // $ANTLR start LBRACKET
    public final void mLBRACKET() throws RecognitionException {
        try {
            int _type = LBRACKET;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:94:14: ( '{' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:94:16: '{'
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:95:14: ( '}' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:95:16: '}'
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:96:14: ( ':' | '=' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:97:14: ( ID_START_LETTER ( ID_LETTER )* )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:97:16: ID_START_LETTER ( ID_LETTER )*
            {
            mID_START_LETTER(); 
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:97:32: ( ID_LETTER )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='#'||LA1_0=='-'||(LA1_0>='0' && LA1_0<='9')||(LA1_0>='@' && LA1_0<='Z')||LA1_0=='_'||(LA1_0>='a' && LA1_0<='z')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:97:32: ID_LETTER
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:102:5: ( '\"' (escaped= ESC | normal= TEXT_LETTER )* '\"' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:103:12: '\"' (escaped= ESC | normal= TEXT_LETTER )* '\"'
            {
            match('\"'); 
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:104:12: (escaped= ESC | normal= TEXT_LETTER )*
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
            	    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:105:14: escaped= ESC
            	    {
            	    int escapedStart157 = getCharIndex();
            	    mESC(); 
            	    escaped = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, escapedStart157, getCharIndex()-1);
            	    lBuf.append(getText());

            	    }
            	    break;
            	case 2 :
            	    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:107:14: normal= TEXT_LETTER
            	    {
            	    int normalStart200 = getCharIndex();
            	    mTEXT_LETTER(); 
            	    normal = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, normalStart200, getCharIndex()-1);
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

    // $ANTLR start WS
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:113:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:113:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:113:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            int alt3=5;
            switch ( input.LA(1) ) {
            case ' ':
                {
                alt3=1;
                }
                break;
            case '\r':
                {
                alt3=2;
                }
                break;
            case '\t':
                {
                alt3=3;
                }
                break;
            case '\f':
                {
                alt3=4;
                }
                break;
            case '\n':
                {
                alt3=5;
                }
                break;
            default:
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:114:2: ' '
                    {
                    match(' '); 

                    }
                    break;
                case 2 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:115:2: '\\r'
                    {
                    match('\r'); 

                    }
                    break;
                case 3 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:116:2: '\\t'
                    {
                    match('\t'); 
                    input.setCharPositionInLine(input.getCharPositionInLine() + 3);

                    }
                    break;
                case 4 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:117:3: '\\u000C'
                    {
                    match('\f'); 

                    }
                    break;
                case 5 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:118:3: '\\n'
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:123:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:123:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:123:14: ( options {greedy=false; } : . )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='*') ) {
                    int LA4_1 = input.LA(2);

                    if ( (LA4_1=='/') ) {
                        alt4=2;
                    }
                    else if ( ((LA4_1>='\u0000' && LA4_1<='.')||(LA4_1>='0' && LA4_1<='\uFFFE')) ) {
                        alt4=1;
                    }


                }
                else if ( ((LA4_0>='\u0000' && LA4_0<=')')||(LA4_0>='+' && LA4_0<='\uFFFE')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:123:42: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop4;
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:127:5: ( '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:127:7: '//' (~ ( '\\n' | '\\r' ) )* ( '\\r' )? '\\n'
            {
            match("//"); 

            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:127:12: (~ ( '\\n' | '\\r' ) )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( ((LA5_0>='\u0000' && LA5_0<='\t')||(LA5_0>='\u000B' && LA5_0<='\f')||(LA5_0>='\u000E' && LA5_0<='\uFFFE')) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:127:12: ~ ( '\\n' | '\\r' )
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
            	    break loop5;
                }
            } while (true);

            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:127:26: ( '\\r' )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0=='\r') ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:127:26: '\\r'
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:132:2: ( '_' | 'a' .. 'z' | 'A' .. 'Z' | '@' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:140:5: ( ID_START_LETTER | '0' .. '9' | '-' | '#' )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:148:5: ( '\\\\' ( '\"' | '\\\\' ) )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:148:9: '\\\\' ( '\"' | '\\\\' )
            {
            match('\\'); 
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:149:9: ( '\"' | '\\\\' )
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0=='\"') ) {
                alt7=1;
            }
            else if ( (LA7_0=='\\') ) {
                alt7=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }
            switch (alt7) {
                case 1 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:150:12: '\"'
                    {
                    match('\"'); 
                    setText("\"");

                    }
                    break;
                case 2 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:151:12: '\\\\'
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
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:157:5: (~ ( '\\\"' | '\\\\' ) )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:157:10: ~ ( '\\\"' | '\\\\' )
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

    @Override
	public void mTokens() throws RecognitionException {
        // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:8: ( SPEC | LBRACKET | RBRACKET | ASSIGNMENT | NAME | TEXTUAL_VALUE | WS | ML_COMMENT | SL_COMMENT )
        int alt8=9;
        alt8 = dfa8.predict(input);
        switch (alt8) {
            case 1 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:10: SPEC
                {
                mSPEC(); 

                }
                break;
            case 2 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:15: LBRACKET
                {
                mLBRACKET(); 

                }
                break;
            case 3 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:24: RBRACKET
                {
                mRBRACKET(); 

                }
                break;
            case 4 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:33: ASSIGNMENT
                {
                mASSIGNMENT(); 

                }
                break;
            case 5 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:44: NAME
                {
                mNAME(); 

                }
                break;
            case 6 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:49: TEXTUAL_VALUE
                {
                mTEXTUAL_VALUE(); 

                }
                break;
            case 7 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:63: WS
                {
                mWS(); 

                }
                break;
            case 8 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:66: ML_COMMENT
                {
                mML_COMMENT(); 

                }
                break;
            case 9 :
                // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:1:77: SL_COMMENT
                {
                mSL_COMMENT(); 

                }
                break;

        }

    }


    protected DFA8 dfa8 = new DFA8(this);
    static final String DFA8_eotS =
        "\1\uffff\1\5\7\uffff\1\5\2\uffff\2\5\1\17\1\uffff";
    static final String DFA8_eofS =
        "\20\uffff";
    static final String DFA8_minS =
        "\1\11\1\123\6\uffff\1\52\1\160\2\uffff\1\145\1\143\1\43\1\uffff";
    static final String DFA8_maxS =
        "\1\175\1\123\6\uffff\1\57\1\160\2\uffff\1\145\1\143\1\172\1\uffff";
    static final String DFA8_acceptS =
        "\2\uffff\1\2\1\3\1\4\1\5\1\6\1\7\2\uffff\1\10\1\11\3\uffff\1\1";
    static final String DFA8_specialS =
        "\20\uffff}>";
    static final String[] DFA8_transitionS = {
            "\2\7\1\uffff\2\7\22\uffff\1\7\1\uffff\1\6\14\uffff\1\10\12\uffff"+
            "\1\4\2\uffff\1\4\2\uffff\1\1\32\5\4\uffff\1\5\1\uffff\32\5\1"+
            "\2\1\uffff\1\3",
            "\1\11",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\12\4\uffff\1\13",
            "\1\14",
            "",
            "",
            "\1\15",
            "\1\16",
            "\1\5\11\uffff\1\5\2\uffff\12\5\6\uffff\33\5\4\uffff\1\5\1\uffff"+
            "\32\5",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        @Override
		public String getDescription() {
            return "1:1: Tokens : ( SPEC | LBRACKET | RBRACKET | ASSIGNMENT | NAME | TEXTUAL_VALUE | WS | ML_COMMENT | SL_COMMENT );";
        }
    }
 

}