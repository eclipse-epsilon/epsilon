// $ANTLR 3.1b1 Hutn.g 2024-12-09 11:02:50

package org.eclipse.epsilon.hutn.parse;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

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
public class HutnParser extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "TRUE", "FALSE", "NULL", "ADJECTIVE", "CLS_LVL_ATTRIBUTE", "ASSOC_INSTANCE", "REFERENCE", "NAME", "TEXTUAL_VALUE", "LBRACKET", "RBRACKET", "ASSIGNMENT", "ADJECTIVE_PREFIX", "COMMA", "NUMERIC_VALUE", "ID_START_LETTER", "ID_LETTER", "ESC", "TEXT_LETTER", "DIGIT", "WS", "ML_COMMENT", "SL_COMMENT", "';'", "'.'"
    };
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


        public HutnParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public HutnParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return HutnParser.tokenNames; }
    public String getGrammarFileName() { return "Hutn.g"; }


    public static class document_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start document
    // Hutn.g:99:1: document : ( pkg )* EOF -> ( pkg )* ;
    public final HutnParser.document_return document() throws RecognitionException {
        HutnParser.document_return retval = new HutnParser.document_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token EOF2=null;
        HutnParser.pkg_return pkg1 = null;


        CommonTree EOF2_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_pkg=new RewriteRuleSubtreeStream(adaptor,"rule pkg");
        try {
            // Hutn.g:99:9: ( ( pkg )* EOF -> ( pkg )* )
            // Hutn.g:99:11: ( pkg )* EOF
            {
            // Hutn.g:99:11: ( pkg )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==NAME) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Hutn.g:0:0: pkg
            	    {
            	    pushFollow(FOLLOW_pkg_in_document124);
            	    pkg1=pkg();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_pkg.add(pkg1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            EOF2=(Token)match(input,EOF,FOLLOW_EOF_in_document127); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF2);



            // AST REWRITE
            // elements: pkg
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 99:20: -> ( pkg )*
            {
                // Hutn.g:99:23: ( pkg )*
                while ( stream_pkg.hasNext() ) {
                    adaptor.addChild(root_0, stream_pkg.nextTree());

                }
                stream_pkg.reset();

            }

            retval.tree = root_0;retval.tree = root_0;}
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
    // $ANTLR end document

    public static class pkg_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pkg
    // Hutn.g:101:1: pkg : NAME ( TEXTUAL_VALUE )? LBRACKET ( pkg_contents )* RBRACKET ;
    public final HutnParser.pkg_return pkg() throws RecognitionException {
        HutnParser.pkg_return retval = new HutnParser.pkg_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME3=null;
        Token TEXTUAL_VALUE4=null;
        Token LBRACKET5=null;
        Token RBRACKET7=null;
        HutnParser.pkg_contents_return pkg_contents6 = null;


        CommonTree NAME3_tree=null;
        CommonTree TEXTUAL_VALUE4_tree=null;
        CommonTree LBRACKET5_tree=null;
        CommonTree RBRACKET7_tree=null;

        try {
            // Hutn.g:101:4: ( NAME ( TEXTUAL_VALUE )? LBRACKET ( pkg_contents )* RBRACKET )
            // Hutn.g:101:6: NAME ( TEXTUAL_VALUE )? LBRACKET ( pkg_contents )* RBRACKET
            {
            root_0 = (CommonTree)adaptor.nil();

            NAME3=(Token)match(input,NAME,FOLLOW_NAME_in_pkg139); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME3_tree = (CommonTree)adaptor.create(NAME3);
            root_0 = (CommonTree)adaptor.becomeRoot(NAME3_tree, root_0);
            }
            // Hutn.g:101:12: ( TEXTUAL_VALUE )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==TEXTUAL_VALUE) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // Hutn.g:0:0: TEXTUAL_VALUE
                    {
                    TEXTUAL_VALUE4=(Token)match(input,TEXTUAL_VALUE,FOLLOW_TEXTUAL_VALUE_in_pkg142); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TEXTUAL_VALUE4_tree = (CommonTree)adaptor.create(TEXTUAL_VALUE4);
                    adaptor.addChild(root_0, TEXTUAL_VALUE4_tree);
                    }

                    }
                    break;

            }

            LBRACKET5=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_pkg145); if (state.failed) return retval;
            // Hutn.g:101:37: ( pkg_contents )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==NAME||LA3_0==ADJECTIVE_PREFIX) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // Hutn.g:0:0: pkg_contents
            	    {
            	    pushFollow(FOLLOW_pkg_contents_in_pkg148);
            	    pkg_contents6=pkg_contents();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, pkg_contents6.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            RBRACKET7=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_pkg151); if (state.failed) return retval;

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
    // $ANTLR end pkg

    public static class pkg_contents_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pkg_contents
    // Hutn.g:103:1: pkg_contents : ( cls | cls_level_attribute | assoc_instance );
    public final HutnParser.pkg_contents_return pkg_contents() throws RecognitionException {
        HutnParser.pkg_contents_return retval = new HutnParser.pkg_contents_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        HutnParser.cls_return cls8 = null;

        HutnParser.cls_level_attribute_return cls_level_attribute9 = null;

        HutnParser.assoc_instance_return assoc_instance10 = null;



        try {
            // Hutn.g:103:13: ( cls | cls_level_attribute | assoc_instance )
            int alt4=3;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==ADJECTIVE_PREFIX) ) {
                alt4=1;
            }
            else if ( (LA4_0==NAME) ) {
                switch ( input.LA(2) ) {
                case 28:
                    {
                    alt4=2;
                    }
                    break;
                case TEXTUAL_VALUE:
                    {
                    int LA4_4 = input.LA(3);

                    if ( (LA4_4==LBRACKET||LA4_4==27) ) {
                        alt4=1;
                    }
                    else if ( (LA4_4==NAME) ) {
                        alt4=3;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 4, input);

                        throw nvae;
                    }
                    }
                    break;
                case LBRACKET:
                    {
                    int LA4_5 = input.LA(3);

                    if ( (LA4_5==NAME) ) {
                        int LA4_7 = input.LA(4);

                        if ( (LA4_7==NAME||(LA4_7>=RBRACKET && LA4_7<=ADJECTIVE_PREFIX)) ) {
                            alt4=1;
                        }
                        else if ( (LA4_7==TEXTUAL_VALUE) ) {
                            alt4=3;
                        }
                        else {
                            if (state.backtracking>0) {state.failed=true; return retval;}
                            NoViableAltException nvae =
                                new NoViableAltException("", 4, 7, input);

                            throw nvae;
                        }
                    }
                    else if ( (LA4_5==RBRACKET||LA4_5==ADJECTIVE_PREFIX) ) {
                        alt4=1;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 5, input);

                        throw nvae;
                    }
                    }
                    break;
                case NAME:
                case ADJECTIVE_PREFIX:
                case 27:
                    {
                    alt4=1;
                    }
                    break;
                default:
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 4, 2, input);

                    throw nvae;
                }

            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // Hutn.g:103:15: cls
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_cls_in_pkg_contents159);
                    cls8=cls();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cls8.getTree());

                    }
                    break;
                case 2 :
                    // Hutn.g:103:21: cls_level_attribute
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_cls_level_attribute_in_pkg_contents163);
                    cls_level_attribute9=cls_level_attribute();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cls_level_attribute9.getTree());

                    }
                    break;
                case 3 :
                    // Hutn.g:103:43: assoc_instance
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_assoc_instance_in_pkg_contents167);
                    assoc_instance10=assoc_instance();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assoc_instance10.getTree());

                    }
                    break;

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
    // $ANTLR end pkg_contents

    public static class assoc_instance_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start assoc_instance
    // Hutn.g:105:1: assoc_instance : ( infix_assoc | assoc_block );
    public final HutnParser.assoc_instance_return assoc_instance() throws RecognitionException {
        HutnParser.assoc_instance_return retval = new HutnParser.assoc_instance_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        HutnParser.infix_assoc_return infix_assoc11 = null;

        HutnParser.assoc_block_return assoc_block12 = null;



        try {
            // Hutn.g:105:15: ( infix_assoc | assoc_block )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==NAME) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==TEXTUAL_VALUE) ) {
                    alt5=1;
                }
                else if ( (LA5_1==LBRACKET) ) {
                    alt5=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 5, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // Hutn.g:105:17: infix_assoc
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_infix_assoc_in_assoc_instance174);
                    infix_assoc11=infix_assoc();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, infix_assoc11.getTree());

                    }
                    break;
                case 2 :
                    // Hutn.g:105:31: assoc_block
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_assoc_block_in_assoc_instance178);
                    assoc_block12=assoc_block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assoc_block12.getTree());

                    }
                    break;

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
    // $ANTLR end assoc_instance

    public static class infix_assoc_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start infix_assoc
    // Hutn.g:107:1: infix_assoc : ref NAME ref ';' -> ^( ASSOC_INSTANCE[$NAME.text] ref ref ) ;
    public final HutnParser.infix_assoc_return infix_assoc() throws RecognitionException {
        HutnParser.infix_assoc_return retval = new HutnParser.infix_assoc_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME14=null;
        Token char_literal16=null;
        HutnParser.ref_return ref13 = null;

        HutnParser.ref_return ref15 = null;


        CommonTree NAME14_tree=null;
        CommonTree char_literal16_tree=null;
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_ref=new RewriteRuleSubtreeStream(adaptor,"rule ref");
        try {
            // Hutn.g:108:7: ( ref NAME ref ';' -> ^( ASSOC_INSTANCE[$NAME.text] ref ref ) )
            // Hutn.g:108:11: ref NAME ref ';'
            {
            pushFollow(FOLLOW_ref_in_infix_assoc194);
            ref13=ref();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ref.add(ref13.getTree());
            NAME14=(Token)match(input,NAME,FOLLOW_NAME_in_infix_assoc196); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME14);

            pushFollow(FOLLOW_ref_in_infix_assoc198);
            ref15=ref();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_ref.add(ref15.getTree());
            char_literal16=(Token)match(input,27,FOLLOW_27_in_infix_assoc200); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_27.add(char_literal16);



            // AST REWRITE
            // elements: ref, ref
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 108:28: -> ^( ASSOC_INSTANCE[$NAME.text] ref ref )
            {
                // Hutn.g:108:31: ^( ASSOC_INSTANCE[$NAME.text] ref ref )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ASSOC_INSTANCE, (NAME14!=null?NAME14.getText():null)), root_1);

                adaptor.addChild(root_1, stream_ref.nextTree());
                adaptor.addChild(root_1, stream_ref.nextTree());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
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
    // $ANTLR end infix_assoc

    public static class assoc_block_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start assoc_block
    // Hutn.g:111:1: assoc_block : NAME LBRACKET ( assoc_contents )+ RBRACKET -> ^( ASSOC_INSTANCE[$NAME.text] ( assoc_contents )* ) ;
    public final HutnParser.assoc_block_return assoc_block() throws RecognitionException {
        HutnParser.assoc_block_return retval = new HutnParser.assoc_block_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME17=null;
        Token LBRACKET18=null;
        Token RBRACKET20=null;
        HutnParser.assoc_contents_return assoc_contents19 = null;


        CommonTree NAME17_tree=null;
        CommonTree LBRACKET18_tree=null;
        CommonTree RBRACKET20_tree=null;
        RewriteRuleTokenStream stream_LBRACKET=new RewriteRuleTokenStream(adaptor,"token LBRACKET");
        RewriteRuleTokenStream stream_RBRACKET=new RewriteRuleTokenStream(adaptor,"token RBRACKET");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_assoc_contents=new RewriteRuleSubtreeStream(adaptor,"rule assoc_contents");
        try {
            // Hutn.g:112:7: ( NAME LBRACKET ( assoc_contents )+ RBRACKET -> ^( ASSOC_INSTANCE[$NAME.text] ( assoc_contents )* ) )
            // Hutn.g:112:11: NAME LBRACKET ( assoc_contents )+ RBRACKET
            {
            NAME17=(Token)match(input,NAME,FOLLOW_NAME_in_assoc_block234); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME17);

            LBRACKET18=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_assoc_block236); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_LBRACKET.add(LBRACKET18);

            // Hutn.g:112:25: ( assoc_contents )+
            int cnt6=0;
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==NAME) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // Hutn.g:0:0: assoc_contents
            	    {
            	    pushFollow(FOLLOW_assoc_contents_in_assoc_block238);
            	    assoc_contents19=assoc_contents();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_assoc_contents.add(assoc_contents19.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt6 >= 1 ) break loop6;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(6, input);
                        throw eee;
                }
                cnt6++;
            } while (true);

            RBRACKET20=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_assoc_block241); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_RBRACKET.add(RBRACKET20);



            // AST REWRITE
            // elements: assoc_contents
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 112:50: -> ^( ASSOC_INSTANCE[$NAME.text] ( assoc_contents )* )
            {
                // Hutn.g:112:53: ^( ASSOC_INSTANCE[$NAME.text] ( assoc_contents )* )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ASSOC_INSTANCE, (NAME17!=null?NAME17.getText():null)), root_1);

                // Hutn.g:112:82: ( assoc_contents )*
                while ( stream_assoc_contents.hasNext() ) {
                    adaptor.addChild(root_1, stream_assoc_contents.nextTree());

                }
                stream_assoc_contents.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
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
    // $ANTLR end assoc_block

    public static class assoc_contents_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start assoc_contents
    // Hutn.g:115:1: assoc_contents : ref ref ;
    public final HutnParser.assoc_contents_return assoc_contents() throws RecognitionException {
        HutnParser.assoc_contents_return retval = new HutnParser.assoc_contents_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        HutnParser.ref_return ref21 = null;

        HutnParser.ref_return ref22 = null;



        try {
            // Hutn.g:116:7: ( ref ref )
            // Hutn.g:116:11: ref ref
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_ref_in_assoc_contents274);
            ref21=ref();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ref21.getTree());
            pushFollow(FOLLOW_ref_in_assoc_contents276);
            ref22=ref();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ref22.getTree());

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
    // $ANTLR end assoc_contents

    public static class cls_level_attribute_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cls_level_attribute
    // Hutn.g:119:1: cls_level_attribute : NAME '.' NAME ASSIGNMENT attr ';' -> ^( CLS_LVL_ATTRIBUTE ^( NAME ^( NAME attr ) ) ) ;
    public final HutnParser.cls_level_attribute_return cls_level_attribute() throws RecognitionException {
        HutnParser.cls_level_attribute_return retval = new HutnParser.cls_level_attribute_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME23=null;
        Token char_literal24=null;
        Token NAME25=null;
        Token ASSIGNMENT26=null;
        Token char_literal28=null;
        HutnParser.attr_return attr27 = null;


        CommonTree NAME23_tree=null;
        CommonTree char_literal24_tree=null;
        CommonTree NAME25_tree=null;
        CommonTree ASSIGNMENT26_tree=null;
        CommonTree char_literal28_tree=null;
        RewriteRuleTokenStream stream_27=new RewriteRuleTokenStream(adaptor,"token 27");
        RewriteRuleTokenStream stream_28=new RewriteRuleTokenStream(adaptor,"token 28");
        RewriteRuleTokenStream stream_ASSIGNMENT=new RewriteRuleTokenStream(adaptor,"token ASSIGNMENT");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_attr=new RewriteRuleSubtreeStream(adaptor,"rule attr");
        try {
            // Hutn.g:120:7: ( NAME '.' NAME ASSIGNMENT attr ';' -> ^( CLS_LVL_ATTRIBUTE ^( NAME ^( NAME attr ) ) ) )
            // Hutn.g:120:11: NAME '.' NAME ASSIGNMENT attr ';'
            {
            NAME23=(Token)match(input,NAME,FOLLOW_NAME_in_cls_level_attribute299); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME23);

            char_literal24=(Token)match(input,28,FOLLOW_28_in_cls_level_attribute301); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_28.add(char_literal24);

            NAME25=(Token)match(input,NAME,FOLLOW_NAME_in_cls_level_attribute303); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME25);

            ASSIGNMENT26=(Token)match(input,ASSIGNMENT,FOLLOW_ASSIGNMENT_in_cls_level_attribute305); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_ASSIGNMENT.add(ASSIGNMENT26);

            pushFollow(FOLLOW_attr_in_cls_level_attribute307);
            attr27=attr();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_attr.add(attr27.getTree());
            char_literal28=(Token)match(input,27,FOLLOW_27_in_cls_level_attribute309); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_27.add(char_literal28);



            // AST REWRITE
            // elements: NAME, NAME, attr
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 120:45: -> ^( CLS_LVL_ATTRIBUTE ^( NAME ^( NAME attr ) ) )
            {
                // Hutn.g:120:48: ^( CLS_LVL_ATTRIBUTE ^( NAME ^( NAME attr ) ) )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(CLS_LVL_ATTRIBUTE, "CLS_LVL_ATTRIBUTE"), root_1);

                // Hutn.g:120:68: ^( NAME ^( NAME attr ) )
                {
                CommonTree root_2 = (CommonTree)adaptor.nil();
                root_2 = (CommonTree)adaptor.becomeRoot(stream_NAME.nextNode(), root_2);

                // Hutn.g:120:75: ^( NAME attr )
                {
                CommonTree root_3 = (CommonTree)adaptor.nil();
                root_3 = (CommonTree)adaptor.becomeRoot(stream_NAME.nextNode(), root_3);

                adaptor.addChild(root_3, stream_attr.nextTree());

                adaptor.addChild(root_2, root_3);
                }

                adaptor.addChild(root_1, root_2);
                }

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
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
    // $ANTLR end cls_level_attribute

    public static class cls_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cls
    // Hutn.g:123:1: cls : ( adjective )* NAME ( TEXTUAL_VALUE )? ( cls_body | ';' ) ;
    public final HutnParser.cls_return cls() throws RecognitionException {
        HutnParser.cls_return retval = new HutnParser.cls_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME30=null;
        Token TEXTUAL_VALUE31=null;
        Token char_literal33=null;
        HutnParser.adjective_return adjective29 = null;

        HutnParser.cls_body_return cls_body32 = null;


        CommonTree NAME30_tree=null;
        CommonTree TEXTUAL_VALUE31_tree=null;
        CommonTree char_literal33_tree=null;

        try {
            // Hutn.g:123:4: ( ( adjective )* NAME ( TEXTUAL_VALUE )? ( cls_body | ';' ) )
            // Hutn.g:123:6: ( adjective )* NAME ( TEXTUAL_VALUE )? ( cls_body | ';' )
            {
            root_0 = (CommonTree)adaptor.nil();

            // Hutn.g:123:6: ( adjective )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==NAME) ) {
                    int LA7_1 = input.LA(2);

                    if ( (LA7_1==NAME||LA7_1==ADJECTIVE_PREFIX) ) {
                        alt7=1;
                    }


                }
                else if ( (LA7_0==ADJECTIVE_PREFIX) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // Hutn.g:0:0: adjective
            	    {
            	    pushFollow(FOLLOW_adjective_in_cls339);
            	    adjective29=adjective();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, adjective29.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            NAME30=(Token)match(input,NAME,FOLLOW_NAME_in_cls342); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME30_tree = (CommonTree)adaptor.create(NAME30);
            root_0 = (CommonTree)adaptor.becomeRoot(NAME30_tree, root_0);
            }
            // Hutn.g:123:23: ( TEXTUAL_VALUE )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==TEXTUAL_VALUE) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // Hutn.g:0:0: TEXTUAL_VALUE
                    {
                    TEXTUAL_VALUE31=(Token)match(input,TEXTUAL_VALUE,FOLLOW_TEXTUAL_VALUE_in_cls345); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TEXTUAL_VALUE31_tree = (CommonTree)adaptor.create(TEXTUAL_VALUE31);
                    adaptor.addChild(root_0, TEXTUAL_VALUE31_tree);
                    }

                    }
                    break;

            }

            // Hutn.g:123:38: ( cls_body | ';' )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==LBRACKET) ) {
                alt9=1;
            }
            else if ( (LA9_0==27) ) {
                alt9=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // Hutn.g:123:39: cls_body
                    {
                    pushFollow(FOLLOW_cls_body_in_cls349);
                    cls_body32=cls_body();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cls_body32.getTree());

                    }
                    break;
                case 2 :
                    // Hutn.g:123:50: ';'
                    {
                    char_literal33=(Token)match(input,27,FOLLOW_27_in_cls353); if (state.failed) return retval;

                    }
                    break;

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
    // $ANTLR end cls

    public static class cls_body_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cls_body
    // Hutn.g:125:1: cls_body : LBRACKET ( cls_contents )* RBRACKET ;
    public final HutnParser.cls_body_return cls_body() throws RecognitionException {
        HutnParser.cls_body_return retval = new HutnParser.cls_body_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token LBRACKET34=null;
        Token RBRACKET36=null;
        HutnParser.cls_contents_return cls_contents35 = null;


        CommonTree LBRACKET34_tree=null;
        CommonTree RBRACKET36_tree=null;

        try {
            // Hutn.g:125:9: ( LBRACKET ( cls_contents )* RBRACKET )
            // Hutn.g:125:11: LBRACKET ( cls_contents )* RBRACKET
            {
            root_0 = (CommonTree)adaptor.nil();

            LBRACKET34=(Token)match(input,LBRACKET,FOLLOW_LBRACKET_in_cls_body362); if (state.failed) return retval;
            // Hutn.g:125:21: ( cls_contents )*
            loop10:
            do {
                int alt10=2;
                int LA10_0 = input.LA(1);

                if ( (LA10_0==NAME||LA10_0==ADJECTIVE_PREFIX) ) {
                    alt10=1;
                }


                switch (alt10) {
            	case 1 :
            	    // Hutn.g:0:0: cls_contents
            	    {
            	    pushFollow(FOLLOW_cls_contents_in_cls_body365);
            	    cls_contents35=cls_contents();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, cls_contents35.getTree());

            	    }
            	    break;

            	default :
            	    break loop10;
                }
            } while (true);

            RBRACKET36=(Token)match(input,RBRACKET,FOLLOW_RBRACKET_in_cls_body368); if (state.failed) return retval;

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
    // $ANTLR end cls_body

    public static class adjective_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start adjective
    // Hutn.g:127:1: adjective : ( ADJECTIVE_PREFIX )? NAME -> ^( ADJECTIVE[name] ) ;
    public final HutnParser.adjective_return adjective() throws RecognitionException {
        HutnParser.adjective_return retval = new HutnParser.adjective_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token ADJECTIVE_PREFIX37=null;
        Token NAME38=null;

        CommonTree ADJECTIVE_PREFIX37_tree=null;
        CommonTree NAME38_tree=null;
        RewriteRuleTokenStream stream_ADJECTIVE_PREFIX=new RewriteRuleTokenStream(adaptor,"token ADJECTIVE_PREFIX");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");


          String name = "";

        try {
            // Hutn.g:131:7: ( ( ADJECTIVE_PREFIX )? NAME -> ^( ADJECTIVE[name] ) )
            // Hutn.g:131:10: ( ADJECTIVE_PREFIX )? NAME
            {
            // Hutn.g:131:10: ( ADJECTIVE_PREFIX )?
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==ADJECTIVE_PREFIX) ) {
                alt11=1;
            }
            switch (alt11) {
                case 1 :
                    // Hutn.g:131:11: ADJECTIVE_PREFIX
                    {
                    ADJECTIVE_PREFIX37=(Token)match(input,ADJECTIVE_PREFIX,FOLLOW_ADJECTIVE_PREFIX_in_adjective391); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_ADJECTIVE_PREFIX.add(ADJECTIVE_PREFIX37);

                    if ( state.backtracking==0 ) {
                      name = (ADJECTIVE_PREFIX37!=null?ADJECTIVE_PREFIX37.getText():null);
                    }

                    }
                    break;

            }

            NAME38=(Token)match(input,NAME,FOLLOW_NAME_in_adjective397); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME38);

            if ( state.backtracking==0 ) {
              name += (NAME38!=null?NAME38.getText():null);
            }


            // AST REWRITE
            // elements: 
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 131:90: -> ^( ADJECTIVE[name] )
            {
                // Hutn.g:131:93: ^( ADJECTIVE[name] )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(ADJECTIVE, name), root_1);

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
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
    // $ANTLR end adjective

    public static class cls_contents_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cls_contents
    // Hutn.g:134:1: cls_contents : ( feature | adjective );
    public final HutnParser.cls_contents_return cls_contents() throws RecognitionException {
        HutnParser.cls_contents_return retval = new HutnParser.cls_contents_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        HutnParser.feature_return feature39 = null;

        HutnParser.adjective_return adjective40 = null;



        try {
            // Hutn.g:134:13: ( feature | adjective )
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==NAME) ) {
                int LA12_1 = input.LA(2);

                if ( (LA12_1==ASSIGNMENT) ) {
                    alt12=1;
                }
                else if ( (LA12_1==EOF||LA12_1==NAME||LA12_1==RBRACKET||LA12_1==ADJECTIVE_PREFIX) ) {
                    alt12=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 12, 1, input);

                    throw nvae;
                }
            }
            else if ( (LA12_0==ADJECTIVE_PREFIX) ) {
                alt12=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 12, 0, input);

                throw nvae;
            }
            switch (alt12) {
                case 1 :
                    // Hutn.g:134:15: feature
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_feature_in_cls_contents420);
                    feature39=feature();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, feature39.getTree());

                    }
                    break;
                case 2 :
                    // Hutn.g:134:25: adjective
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_adjective_in_cls_contents424);
                    adjective40=adjective();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, adjective40.getTree());

                    }
                    break;

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
    // $ANTLR end cls_contents

    public static class feature_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start feature
    // Hutn.g:136:1: feature : NAME ASSIGNMENT feature_contents ;
    public final HutnParser.feature_return feature() throws RecognitionException {
        HutnParser.feature_return retval = new HutnParser.feature_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME41=null;
        Token ASSIGNMENT42=null;
        HutnParser.feature_contents_return feature_contents43 = null;


        CommonTree NAME41_tree=null;
        CommonTree ASSIGNMENT42_tree=null;

        try {
            // Hutn.g:136:8: ( NAME ASSIGNMENT feature_contents )
            // Hutn.g:136:10: NAME ASSIGNMENT feature_contents
            {
            root_0 = (CommonTree)adaptor.nil();

            NAME41=(Token)match(input,NAME,FOLLOW_NAME_in_feature431); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME41_tree = (CommonTree)adaptor.create(NAME41);
            root_0 = (CommonTree)adaptor.becomeRoot(NAME41_tree, root_0);
            }
            ASSIGNMENT42=(Token)match(input,ASSIGNMENT,FOLLOW_ASSIGNMENT_in_feature434); if (state.failed) return retval;
            pushFollow(FOLLOW_feature_contents_in_feature437);
            feature_contents43=feature_contents();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, feature_contents43.getTree());

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

    public static class feature_contents_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start feature_contents
    // Hutn.g:138:1: feature_contents : ( attr | refs | containments );
    public final HutnParser.feature_contents_return feature_contents() throws RecognitionException {
        HutnParser.feature_contents_return retval = new HutnParser.feature_contents_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        HutnParser.attr_return attr44 = null;

        HutnParser.refs_return refs45 = null;

        HutnParser.containments_return containments46 = null;



        try {
            // Hutn.g:138:17: ( attr | refs | containments )
            int alt13=3;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // Hutn.g:138:19: attr
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_attr_in_feature_contents444);
                    attr44=attr();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, attr44.getTree());

                    }
                    break;
                case 2 :
                    // Hutn.g:138:26: refs
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_refs_in_feature_contents448);
                    refs45=refs();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, refs45.getTree());

                    }
                    break;
                case 3 :
                    // Hutn.g:138:33: containments
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_containments_in_feature_contents452);
                    containments46=containments();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, containments46.getTree());

                    }
                    break;

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
    // $ANTLR end feature_contents

    public static class attr_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start attr
    // Hutn.g:140:1: attr : ( attr_value ( COMMA attr_value )* | NULL );
    public final HutnParser.attr_return attr() throws RecognitionException {
        HutnParser.attr_return retval = new HutnParser.attr_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token COMMA48=null;
        Token NULL50=null;
        HutnParser.attr_value_return attr_value47 = null;

        HutnParser.attr_value_return attr_value49 = null;


        CommonTree COMMA48_tree=null;
        CommonTree NULL50_tree=null;

        try {
            // Hutn.g:140:5: ( attr_value ( COMMA attr_value )* | NULL )
            int alt15=2;
            int LA15_0 = input.LA(1);

            if ( ((LA15_0>=TRUE && LA15_0<=FALSE)||(LA15_0>=NAME && LA15_0<=TEXTUAL_VALUE)||LA15_0==NUMERIC_VALUE) ) {
                alt15=1;
            }
            else if ( (LA15_0==NULL) ) {
                alt15=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }
            switch (alt15) {
                case 1 :
                    // Hutn.g:140:7: attr_value ( COMMA attr_value )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_attr_value_in_attr459);
                    attr_value47=attr_value();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, attr_value47.getTree());
                    // Hutn.g:140:18: ( COMMA attr_value )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( (LA14_0==COMMA) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // Hutn.g:140:19: COMMA attr_value
                    	    {
                    	    COMMA48=(Token)match(input,COMMA,FOLLOW_COMMA_in_attr462); if (state.failed) return retval;
                    	    pushFollow(FOLLOW_attr_value_in_attr465);
                    	    attr_value49=attr_value();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, attr_value49.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // Hutn.g:140:41: NULL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    NULL50=(Token)match(input,NULL,FOLLOW_NULL_in_attr471); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NULL50_tree = (CommonTree)adaptor.create(NULL50);
                    adaptor.addChild(root_0, NULL50_tree);
                    }

                    }
                    break;

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
    // $ANTLR end attr

    public static class attr_value_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start attr_value
    // Hutn.g:142:1: attr_value : ( TEXTUAL_VALUE | NUMERIC_VALUE | bool | enum_value );
    public final HutnParser.attr_value_return attr_value() throws RecognitionException {
        HutnParser.attr_value_return retval = new HutnParser.attr_value_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token TEXTUAL_VALUE51=null;
        Token NUMERIC_VALUE52=null;
        HutnParser.bool_return bool53 = null;

        HutnParser.enum_value_return enum_value54 = null;


        CommonTree TEXTUAL_VALUE51_tree=null;
        CommonTree NUMERIC_VALUE52_tree=null;

        try {
            // Hutn.g:142:11: ( TEXTUAL_VALUE | NUMERIC_VALUE | bool | enum_value )
            int alt16=4;
            switch ( input.LA(1) ) {
            case TEXTUAL_VALUE:
                {
                alt16=1;
                }
                break;
            case NUMERIC_VALUE:
                {
                alt16=2;
                }
                break;
            case TRUE:
            case FALSE:
                {
                alt16=3;
                }
                break;
            case NAME:
                {
                alt16=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 16, 0, input);

                throw nvae;
            }

            switch (alt16) {
                case 1 :
                    // Hutn.g:142:13: TEXTUAL_VALUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    TEXTUAL_VALUE51=(Token)match(input,TEXTUAL_VALUE,FOLLOW_TEXTUAL_VALUE_in_attr_value478); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TEXTUAL_VALUE51_tree = (CommonTree)adaptor.create(TEXTUAL_VALUE51);
                    adaptor.addChild(root_0, TEXTUAL_VALUE51_tree);
                    }

                    }
                    break;
                case 2 :
                    // Hutn.g:142:29: NUMERIC_VALUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    NUMERIC_VALUE52=(Token)match(input,NUMERIC_VALUE,FOLLOW_NUMERIC_VALUE_in_attr_value482); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NUMERIC_VALUE52_tree = (CommonTree)adaptor.create(NUMERIC_VALUE52);
                    adaptor.addChild(root_0, NUMERIC_VALUE52_tree);
                    }

                    }
                    break;
                case 3 :
                    // Hutn.g:142:45: bool
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_bool_in_attr_value486);
                    bool53=bool();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, bool53.getTree());

                    }
                    break;
                case 4 :
                    // Hutn.g:142:52: enum_value
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_enum_value_in_attr_value490);
                    enum_value54=enum_value();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, enum_value54.getTree());

                    }
                    break;

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
    // $ANTLR end attr_value

    public static class bool_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start bool
    // Hutn.g:144:1: bool : ( TRUE | FALSE );
    public final HutnParser.bool_return bool() throws RecognitionException {
        HutnParser.bool_return retval = new HutnParser.bool_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token set55=null;

        CommonTree set55_tree=null;

        try {
            // Hutn.g:144:5: ( TRUE | FALSE )
            // Hutn.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set55=(Token)input.LT(1);
            if ( (input.LA(1)>=TRUE && input.LA(1)<=FALSE) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set55));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
    // $ANTLR end bool

    public static class refs_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start refs
    // Hutn.g:146:1: refs : ref ( COMMA ref )* ;
    public final HutnParser.refs_return refs() throws RecognitionException {
        HutnParser.refs_return retval = new HutnParser.refs_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token COMMA57=null;
        HutnParser.ref_return ref56 = null;

        HutnParser.ref_return ref58 = null;


        CommonTree COMMA57_tree=null;

        try {
            // Hutn.g:146:5: ( ref ( COMMA ref )* )
            // Hutn.g:146:7: ref ( COMMA ref )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_ref_in_refs508);
            ref56=ref();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ref56.getTree());
            // Hutn.g:146:11: ( COMMA ref )*
            loop17:
            do {
                int alt17=2;
                int LA17_0 = input.LA(1);

                if ( (LA17_0==COMMA) ) {
                    alt17=1;
                }


                switch (alt17) {
            	case 1 :
            	    // Hutn.g:146:12: COMMA ref
            	    {
            	    COMMA57=(Token)match(input,COMMA,FOLLOW_COMMA_in_refs511); if (state.failed) return retval;
            	    pushFollow(FOLLOW_ref_in_refs514);
            	    ref58=ref();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, ref58.getTree());

            	    }
            	    break;

            	default :
            	    break loop17;
                }
            } while (true);


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
    // $ANTLR end refs

    public static class ref_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ref
    // Hutn.g:148:1: ref : NAME TEXTUAL_VALUE -> ^( REFERENCE[$NAME.text] TEXTUAL_VALUE ) ;
    public final HutnParser.ref_return ref() throws RecognitionException {
        HutnParser.ref_return retval = new HutnParser.ref_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME59=null;
        Token TEXTUAL_VALUE60=null;

        CommonTree NAME59_tree=null;
        CommonTree TEXTUAL_VALUE60_tree=null;
        RewriteRuleTokenStream stream_TEXTUAL_VALUE=new RewriteRuleTokenStream(adaptor,"token TEXTUAL_VALUE");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");

        try {
            // Hutn.g:149:7: ( NAME TEXTUAL_VALUE -> ^( REFERENCE[$NAME.text] TEXTUAL_VALUE ) )
            // Hutn.g:149:11: NAME TEXTUAL_VALUE
            {
            NAME59=(Token)match(input,NAME,FOLLOW_NAME_in_ref532); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME59);

            TEXTUAL_VALUE60=(Token)match(input,TEXTUAL_VALUE,FOLLOW_TEXTUAL_VALUE_in_ref534); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_TEXTUAL_VALUE.add(TEXTUAL_VALUE60);



            // AST REWRITE
            // elements: TEXTUAL_VALUE
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (CommonTree)adaptor.nil();
            // 149:30: -> ^( REFERENCE[$NAME.text] TEXTUAL_VALUE )
            {
                // Hutn.g:149:33: ^( REFERENCE[$NAME.text] TEXTUAL_VALUE )
                {
                CommonTree root_1 = (CommonTree)adaptor.nil();
                root_1 = (CommonTree)adaptor.becomeRoot((CommonTree)adaptor.create(REFERENCE, (NAME59!=null?NAME59.getText():null)), root_1);

                adaptor.addChild(root_1, stream_TEXTUAL_VALUE.nextNode());

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
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
    // $ANTLR end ref

    public static class containments_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start containments
    // Hutn.g:152:1: containments : cls ( COMMA cls )* ;
    public final HutnParser.containments_return containments() throws RecognitionException {
        HutnParser.containments_return retval = new HutnParser.containments_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token COMMA62=null;
        HutnParser.cls_return cls61 = null;

        HutnParser.cls_return cls63 = null;


        CommonTree COMMA62_tree=null;

        try {
            // Hutn.g:152:13: ( cls ( COMMA cls )* )
            // Hutn.g:152:15: cls ( COMMA cls )*
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_cls_in_containments557);
            cls61=cls();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, cls61.getTree());
            // Hutn.g:152:19: ( COMMA cls )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==COMMA) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // Hutn.g:152:20: COMMA cls
            	    {
            	    COMMA62=(Token)match(input,COMMA,FOLLOW_COMMA_in_containments560); if (state.failed) return retval;
            	    pushFollow(FOLLOW_cls_in_containments563);
            	    cls63=cls();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, cls63.getTree());

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


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
    // $ANTLR end containments

    public static class enum_value_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start enum_value
    // Hutn.g:154:1: enum_value : NAME ;
    public final HutnParser.enum_value_return enum_value() throws RecognitionException {
        HutnParser.enum_value_return retval = new HutnParser.enum_value_return();
        retval.start = input.LT(1);

        CommonTree root_0 = null;

        Token NAME64=null;

        CommonTree NAME64_tree=null;

        try {
            // Hutn.g:154:11: ( NAME )
            // Hutn.g:154:13: NAME
            {
            root_0 = (CommonTree)adaptor.nil();

            NAME64=(Token)match(input,NAME,FOLLOW_NAME_in_enum_value572); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME64_tree = (CommonTree)adaptor.create(NAME64);
            root_0 = (CommonTree)adaptor.becomeRoot(NAME64_tree, root_0);
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
    // $ANTLR end enum_value

    // Delegated rules


    protected DFA13 dfa13 = new DFA13(this);
    static final String DFA13_eotS =
        "\11\uffff";
    static final String DFA13_eofS =
        "\2\uffff\1\1\1\uffff\1\7\4\uffff";
    static final String DFA13_minS =
        "\1\4\1\uffff\1\13\1\uffff\3\13\1\uffff\1\13";
    static final String DFA13_maxS =
        "\1\22\1\uffff\1\33\1\uffff\2\33\1\13\1\uffff\1\20";
    static final String DFA13_acceptS =
        "\1\uffff\1\1\1\uffff\1\3\3\uffff\1\2\1\uffff";
    static final String DFA13_specialS =
        "\11\uffff}>";
    static final String[] DFA13_transitionS = {
            "\3\1\4\uffff\1\2\1\1\3\uffff\1\3\1\uffff\1\1",
            "",
            "\1\5\1\4\1\3\1\1\1\uffff\1\6\1\1\11\uffff\1\3",
            "",
            "\1\7\1\uffff\1\3\1\7\1\uffff\2\7\11\uffff\1\3",
            "\1\5\2\3\2\1\1\6\12\uffff\1\3",
            "\1\10",
            "",
            "\1\5\2\uffff\1\1\1\uffff\1\6"
    };

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "138:1: feature_contents : ( attr | refs | containments );";
        }
    }
 

    public static final BitSet FOLLOW_pkg_in_document124 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_EOF_in_document127 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_pkg139 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_TEXTUAL_VALUE_in_pkg142 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACKET_in_pkg145 = new BitSet(new long[]{0x0000000000014800L});
    public static final BitSet FOLLOW_pkg_contents_in_pkg148 = new BitSet(new long[]{0x0000000000014800L});
    public static final BitSet FOLLOW_RBRACKET_in_pkg151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cls_in_pkg_contents159 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cls_level_attribute_in_pkg_contents163 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assoc_instance_in_pkg_contents167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_infix_assoc_in_assoc_instance174 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assoc_block_in_assoc_instance178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ref_in_infix_assoc194 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_NAME_in_infix_assoc196 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_ref_in_infix_assoc198 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_infix_assoc200 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_assoc_block234 = new BitSet(new long[]{0x0000000000002000L});
    public static final BitSet FOLLOW_LBRACKET_in_assoc_block236 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_assoc_contents_in_assoc_block238 = new BitSet(new long[]{0x0000000000004800L});
    public static final BitSet FOLLOW_RBRACKET_in_assoc_block241 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ref_in_assoc_contents274 = new BitSet(new long[]{0x0000000000004800L});
    public static final BitSet FOLLOW_ref_in_assoc_contents276 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_cls_level_attribute299 = new BitSet(new long[]{0x0000000010000000L});
    public static final BitSet FOLLOW_28_in_cls_level_attribute301 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_NAME_in_cls_level_attribute303 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ASSIGNMENT_in_cls_level_attribute305 = new BitSet(new long[]{0x0000000000041870L});
    public static final BitSet FOLLOW_attr_in_cls_level_attribute307 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_27_in_cls_level_attribute309 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_adjective_in_cls339 = new BitSet(new long[]{0x0000000000010800L});
    public static final BitSet FOLLOW_NAME_in_cls342 = new BitSet(new long[]{0x0000000008003000L});
    public static final BitSet FOLLOW_TEXTUAL_VALUE_in_cls345 = new BitSet(new long[]{0x0000000008002000L});
    public static final BitSet FOLLOW_cls_body_in_cls349 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_27_in_cls353 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LBRACKET_in_cls_body362 = new BitSet(new long[]{0x0000000000014800L});
    public static final BitSet FOLLOW_cls_contents_in_cls_body365 = new BitSet(new long[]{0x0000000000014800L});
    public static final BitSet FOLLOW_RBRACKET_in_cls_body368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ADJECTIVE_PREFIX_in_adjective391 = new BitSet(new long[]{0x0000000000000800L});
    public static final BitSet FOLLOW_NAME_in_adjective397 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_feature_in_cls_contents420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_adjective_in_cls_contents424 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_feature431 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_ASSIGNMENT_in_feature434 = new BitSet(new long[]{0x0000000000055870L});
    public static final BitSet FOLLOW_feature_contents_in_feature437 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attr_in_feature_contents444 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_refs_in_feature_contents448 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_containments_in_feature_contents452 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_attr_value_in_attr459 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_attr462 = new BitSet(new long[]{0x0000000000041830L});
    public static final BitSet FOLLOW_attr_value_in_attr465 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_NULL_in_attr471 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TEXTUAL_VALUE_in_attr_value478 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NUMERIC_VALUE_in_attr_value482 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_bool_in_attr_value486 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enum_value_in_attr_value490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_bool0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ref_in_refs508 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_refs511 = new BitSet(new long[]{0x0000000000004800L});
    public static final BitSet FOLLOW_ref_in_refs514 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_NAME_in_ref532 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_TEXTUAL_VALUE_in_ref534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_cls_in_containments557 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_COMMA_in_containments560 = new BitSet(new long[]{0x0000000000055870L});
    public static final BitSet FOLLOW_cls_in_containments563 = new BitSet(new long[]{0x0000000000020002L});
    public static final BitSet FOLLOW_NAME_in_enum_value572 = new BitSet(new long[]{0x0000000000000002L});

}