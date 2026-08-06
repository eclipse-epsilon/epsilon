// $ANTLR 3.1b1 C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g 2020-04-08 19:05:29

package org.eclipse.epsilon.hutn.parse.spec;


import org.antlr.runtime.*;
import org.antlr.runtime.tree.*;

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
public class HutnSpecParser extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "SPEC", "TEXTUAL_VALUE", "LBRACKET", "RBRACKET", "NAME", "ASSIGNMENT", "ID_START_LETTER", "ID_LETTER", "ESC", "TEXT_LETTER", "WS", "ML_COMMENT", "SL_COMMENT"
    };
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


        public HutnSpecParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public HutnSpecParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    @Override
	public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    @Override
	public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    @Override
	public String[] getTokenNames() { return HutnSpecParser.tokenNames; }
    @Override
	public String getGrammarFileName() { return "C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g"; }


    public static class preamble_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start preamble
    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:87:1: preamble : SPEC ( TEXTUAL_VALUE )? LBRACKET ( metamodel )* RBRACKET ;
    public final HutnSpecParser.preamble_return preamble() throws RecognitionException {
        HutnSpecParser.preamble_return retval = new HutnSpecParser.preamble_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token SPEC1=null;
        Token TEXTUAL_VALUE2=null;
        Token LBRACKET3=null;
        Token RBRACKET5=null;
        HutnSpecParser.metamodel_return metamodel4 = null;


        CommonTree SPEC1_tree=null;
        CommonTree TEXTUAL_VALUE2_tree=null;
        CommonTree LBRACKET3_tree=null;
        CommonTree RBRACKET5_tree=null;

        try {
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:87:9: ( SPEC ( TEXTUAL_VALUE )? LBRACKET ( metamodel )* RBRACKET )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:87:11: SPEC ( TEXTUAL_VALUE )? LBRACKET ( metamodel )* RBRACKET
            {
            root_0 = (CommonTree)adaptor.nil();

            SPEC1=(Token)match(input,SPEC,FOLLOW_SPEC_in_preamble49); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SPEC1_tree = (CommonTree)adaptor.create(SPEC1);
            root_0 = (CommonTree)adaptor.becomeRoot(SPEC1_tree, root_0);
            }
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:87:17: ( TEXTUAL_VALUE )?
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==TEXTUAL_VALUE) ) {
                alt1=1;
            }
            switch (alt1) {
                case 1 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:0:0: TEXTUAL_VALUE
                    {
                    TEXTUAL_VALUE2=(Token)match(input,TEXTUAL_VALUE,FOLLOW_TEXTUAL_VALUE_in_preamble52); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TEXTUAL_VALUE2_tree = (CommonTree)adaptor.create(TEXTUAL_VALUE2);
                    adaptor.addChild(root_0, TEXTUAL_VALUE2_tree);
                    }

                    }
                    break;

            }

            LBRACKET3=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_preamble55); if (state.failed) return retval;
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:87:42: ( metamodel )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==NAME) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:0:0: metamodel
            	    {
            	    pushFollow(FOLLOW_metamodel_in_preamble58);
            	    metamodel4=metamodel();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, metamodel4.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            RBRACKET5=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_preamble61); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end preamble

    public static class metamodel_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start metamodel
    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:89:1: metamodel : NAME ( TEXTUAL_VALUE )? LBRACKET ( feature )* RBRACKET ;
    public final HutnSpecParser.metamodel_return metamodel() throws RecognitionException {
        HutnSpecParser.metamodel_return retval = new HutnSpecParser.metamodel_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME6=null;
        Token TEXTUAL_VALUE7=null;
        Token LBRACKET8=null;
        Token RBRACKET10=null;
        HutnSpecParser.feature_return feature9 = null;


        CommonTree NAME6_tree=null;
        CommonTree TEXTUAL_VALUE7_tree=null;
        CommonTree LBRACKET8_tree=null;
        CommonTree RBRACKET10_tree=null;

        try {
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:89:10: ( NAME ( TEXTUAL_VALUE )? LBRACKET ( feature )* RBRACKET )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:89:12: NAME ( TEXTUAL_VALUE )? LBRACKET ( feature )* RBRACKET
            {
            root_0 = (CommonTree)adaptor.nil();

            NAME6=(Token)match(input,NAME,FOLLOW_NAME_in_metamodel69); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME6_tree = (CommonTree)adaptor.create(NAME6);
            root_0 = (CommonTree)adaptor.becomeRoot(NAME6_tree, root_0);
            }
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:89:18: ( TEXTUAL_VALUE )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==TEXTUAL_VALUE) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:0:0: TEXTUAL_VALUE
                    {
                    TEXTUAL_VALUE7=(Token)match(input,TEXTUAL_VALUE,FOLLOW_TEXTUAL_VALUE_in_metamodel72); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TEXTUAL_VALUE7_tree = (CommonTree)adaptor.create(TEXTUAL_VALUE7);
                    adaptor.addChild(root_0, TEXTUAL_VALUE7_tree);
                    }

                    }
                    break;

            }

            LBRACKET8=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_metamodel75); if (state.failed) return retval;
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:89:43: ( feature )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==NAME) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:0:0: feature
            	    {
            	    pushFollow(FOLLOW_feature_in_metamodel78);
            	    feature9=feature();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, feature9.getTree());

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            RBRACKET10=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_metamodel81); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end metamodel

    public static class feature_return extends ParserRuleReturnScope {
        CommonTree tree;
        @Override
		public Object getTree() { return tree; }
    };

    // $ANTLR start feature
    // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:91:1: feature : NAME ASSIGNMENT TEXTUAL_VALUE ;
    public final HutnSpecParser.feature_return feature() throws RecognitionException {
        HutnSpecParser.feature_return retval = new HutnSpecParser.feature_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME11=null;
        Token ASSIGNMENT12=null;
        Token TEXTUAL_VALUE13=null;

        CommonTree NAME11_tree=null;
        CommonTree ASSIGNMENT12_tree=null;
        CommonTree TEXTUAL_VALUE13_tree=null;

        try {
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:91:8: ( NAME ASSIGNMENT TEXTUAL_VALUE )
            // C:\\Users\\Sina-\\Projects\\Epsilon\\org.eclipse.epsilon\\plugins\\org.eclipse.epsilon.hutn.engine\\src\\org\\eclipse\\epsilon\\hutn\\parse\\spec\\HutnSpec.g:91:10: NAME ASSIGNMENT TEXTUAL_VALUE
            {
            root_0 = (CommonTree)adaptor.nil();

            NAME11=(Token)match(input,NAME,FOLLOW_NAME_in_feature89); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME11_tree = (CommonTree)adaptor.create(NAME11);
            root_0 = (CommonTree)adaptor.becomeRoot(NAME11_tree, root_0);
            }
            ASSIGNMENT12=(Token)match(input,ASSIGNMENT,FOLLOW_ASSIGNMENT_in_feature92); if (state.failed) return retval;
            TEXTUAL_VALUE13=(Token)match(input,TEXTUAL_VALUE,FOLLOW_TEXTUAL_VALUE_in_feature95); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            TEXTUAL_VALUE13_tree = (CommonTree)adaptor.create(TEXTUAL_VALUE13);
            adaptor.addChild(root_0, TEXTUAL_VALUE13_tree);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end feature

    // Delegated rules


 

    public static final BitSet FOLLOW_SPEC_in_preamble49 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_TEXTUAL_VALUE_in_preamble52 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LBRACKET_in_preamble55 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_metamodel_in_preamble58 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_RBRACKET_in_preamble61 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_metamodel69 = new BitSet(new long[]{0x0000000000000060L});
    public static final BitSet FOLLOW_TEXTUAL_VALUE_in_metamodel72 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_LBRACKET_in_metamodel75 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_feature_in_metamodel78 = new BitSet(new long[]{0x0000000000000180L});
    public static final BitSet FOLLOW_RBRACKET_in_metamodel81 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_feature89 = new BitSet(new long[]{0x0000000000000200L});
    public static final BitSet FOLLOW_ASSIGNMENT_in_feature92 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_TEXTUAL_VALUE_in_feature95 = new BitSet(new long[]{0x0000000000000002L});

}