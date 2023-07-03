package org.eclipse.epsilon.ecl.parse;

// $ANTLR 3.1b1 ErlParserRules.g 2023-07-03 20:54:43

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
public class Ecl_ErlParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
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
    public static final int T__166=166;
    public static final int T__165=165;
    public static final int T__168=168;
    public static final int T__167=167;
    public static final int T__162=162;
    public static final int T__161=161;
    public static final int T__164=164;
    public static final int MODELDECLARATION=73;
    public static final int T__163=163;
    public static final int EXPRESSIONINBRACKETS=64;
    public static final int T__160=160;
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
    public static final int RIGHTDOMAIN=91;
    public static final int T__159=159;
    public static final int T__158=158;
    public static final int T__155=155;
    public static final int SPECIAL_ASSIGNMENT=31;
    public static final int T__154=154;
    public static final int T__157=157;
    public static final int T__156=156;
    public static final int T__151=151;
    public static final int T__150=150;
    public static final int T__153=153;
    public static final int T__152=152;
    public static final int LEFTDOMAIN=90;
    public static final int Annotation=27;
    public static final int CONTINUE=45;
    public static final int ENUMERATION_VALUE=71;
    public static final int OPERATOR=63;
    public static final int EXPONENT=6;
    public static final int STRING=15;
    public static final int T__148=148;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int T__100=100;
    public static final int NAMESPACE=74;
    public static final int COLLECTION=47;
    public static final int NEW=54;
    public static final int EXTENDS=85;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int PRE=83;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int POST=84;
    public static final int ALIAS=75;
    public static final int DRIVER=76;
    public static final int DO=89;
    public static final int KEYVAL=81;
    public static final int POINT_POINT=10;
    public static final int GUARD=86;
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
    public static final int T__170=170;
    public static final int PARAMETERS=51;
    public static final int SpecialNameChar=21;
    public static final int BOOLEAN=13;
    public static final int NAME=23;
    public static final int COMPARE=88;
    public static final int SWITCH=39;
    public static final int T__169=169;
    public static final int FeatureCall=65;
    public static final int T__122=122;
    public static final int T__121=121;
    public static final int T__124=124;
    public static final int FLOAT=4;
    public static final int ECLMODULE=92;
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
    public static final int T__108=108;
    public static final int T__107=107;
    public static final int WHILE=38;
    public static final int T__109=109;
    public static final int NAVIGATION=12;
    public static final int T__104=104;
    public static final int POINT=9;
    public static final int T__103=103;
    public static final int T__106=106;
    public static final int DEFAULT=41;
    public static final int MATCH=87;
    public static final int T__105=105;

    // delegates
    // delegators
    public EclParser gEcl;


        public Ecl_ErlParserRules(TokenStream input, EclParser gEcl) {
            this(input, new RecognizerSharedState(), gEcl);
        }
        public Ecl_ErlParserRules(TokenStream input, RecognizerSharedState state, EclParser gEcl) {
            super(input, state);
            this.gEcl = gEcl;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EclParser.tokenNames; }
    public String getGrammarFileName() { return "ErlParserRules.g"; }


    public static class erlModuleContent_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start erlModuleContent
    // ErlParserRules.g:48:1: erlModuleContent : ( pre | annotationBlock | operationDeclaration | post );
    public final Ecl_ErlParserRules.erlModuleContent_return erlModuleContent() throws RecognitionException {
        Ecl_ErlParserRules.erlModuleContent_return retval = new Ecl_ErlParserRules.erlModuleContent_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Ecl_ErlParserRules.pre_return pre1 = null;

        Ecl_EolParserRules.annotationBlock_return annotationBlock2 = null;

        Ecl_EolParserRules.operationDeclaration_return operationDeclaration3 = null;

        Ecl_ErlParserRules.post_return post4 = null;



        try {
            // ErlParserRules.g:49:2: ( pre | annotationBlock | operationDeclaration | post )
            int alt1=4;
            switch ( input.LA(1) ) {
            case 161:
                {
                alt1=1;
                }
                break;
            case Annotation:
            case 107:
                {
                alt1=2;
                }
                break;
            case 101:
            case 102:
                {
                alt1=3;
                }
                break;
            case 162:
                {
                alt1=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // ErlParserRules.g:49:4: pre
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_pre_in_erlModuleContent48);
                    pre1=pre();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pre1.getTree());

                    }
                    break;
                case 2 :
                    // ErlParserRules.g:49:10: annotationBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_annotationBlock_in_erlModuleContent52);
                    annotationBlock2=gEcl.annotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, annotationBlock2.getTree());

                    }
                    break;
                case 3 :
                    // ErlParserRules.g:49:28: operationDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_operationDeclaration_in_erlModuleContent56);
                    operationDeclaration3=gEcl.operationDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operationDeclaration3.getTree());

                    }
                    break;
                case 4 :
                    // ErlParserRules.g:49:51: post
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_post_in_erlModuleContent60);
                    post4=post();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, post4.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end erlModuleContent

    public static class pre_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pre
    // ErlParserRules.g:52:1: pre : p= 'pre' ( NAME )? statementBlock ;
    public final Ecl_ErlParserRules.pre_return pre() throws RecognitionException {
        Ecl_ErlParserRules.pre_return retval = new Ecl_ErlParserRules.pre_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token p=null;
        Token NAME5=null;
        Ecl_EolParserRules.statementBlock_return statementBlock6 = null;


        org.eclipse.epsilon.common.parse.AST p_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME5_tree=null;

        try {
            // ErlParserRules.g:53:2: (p= 'pre' ( NAME )? statementBlock )
            // ErlParserRules.g:53:4: p= 'pre' ( NAME )? statementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            p=(Token)match(input,161,FOLLOW_161_in_pre73); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            p_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(p);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(p_tree, root_0);
            }
            // ErlParserRules.g:53:13: ( NAME )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==NAME) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // ErlParserRules.g:0:0: NAME
                    {
                    NAME5=(Token)match(input,NAME,FOLLOW_NAME_in_pre76); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME5_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME5);
                    adaptor.addChild(root_0, NAME5_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_statementBlock_in_pre79);
            statementBlock6=gEcl.statementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock6.getTree());
            if ( state.backtracking==0 ) {
              p.setType(PRE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end pre

    public static class post_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start post
    // ErlParserRules.g:57:1: post : p= 'post' ( NAME )? statementBlock ;
    public final Ecl_ErlParserRules.post_return post() throws RecognitionException {
        Ecl_ErlParserRules.post_return retval = new Ecl_ErlParserRules.post_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token p=null;
        Token NAME7=null;
        Ecl_EolParserRules.statementBlock_return statementBlock8 = null;


        org.eclipse.epsilon.common.parse.AST p_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME7_tree=null;

        try {
            // ErlParserRules.g:58:2: (p= 'post' ( NAME )? statementBlock )
            // ErlParserRules.g:58:4: p= 'post' ( NAME )? statementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            p=(Token)match(input,162,FOLLOW_162_in_post95); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            p_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(p);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(p_tree, root_0);
            }
            // ErlParserRules.g:58:14: ( NAME )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==NAME) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // ErlParserRules.g:0:0: NAME
                    {
                    NAME7=(Token)match(input,NAME,FOLLOW_NAME_in_post98); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME7_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME7);
                    adaptor.addChild(root_0, NAME7_tree);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_statementBlock_in_post101);
            statementBlock8=gEcl.statementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock8.getTree());
            if ( state.backtracking==0 ) {
              p.setType(POST);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end post

    public static class guard_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start guard
    // ErlParserRules.g:62:1: guard : g= 'guard' expressionOrStatementBlock ;
    public final Ecl_ErlParserRules.guard_return guard() throws RecognitionException {
        Ecl_ErlParserRules.guard_return retval = new Ecl_ErlParserRules.guard_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token g=null;
        Ecl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock9 = null;


        org.eclipse.epsilon.common.parse.AST g_tree=null;

        try {
            // ErlParserRules.g:63:2: (g= 'guard' expressionOrStatementBlock )
            // ErlParserRules.g:63:4: g= 'guard' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            g=(Token)match(input,163,FOLLOW_163_in_guard117); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            g_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(g);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(g_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_guard120);
            expressionOrStatementBlock9=gEcl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock9.getTree());
            if ( state.backtracking==0 ) {
              g.setType(GUARD);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end guard

    public static class extendz_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start extendz
    // ErlParserRules.g:67:1: extendz : e= 'extends' NAME ( ',' NAME )* ;
    public final Ecl_ErlParserRules.extendz_return extendz() throws RecognitionException {
        Ecl_ErlParserRules.extendz_return retval = new Ecl_ErlParserRules.extendz_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token e=null;
        Token NAME10=null;
        Token char_literal11=null;
        Token NAME12=null;

        org.eclipse.epsilon.common.parse.AST e_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME10_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal11_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME12_tree=null;

        try {
            // ErlParserRules.g:68:2: (e= 'extends' NAME ( ',' NAME )* )
            // ErlParserRules.g:68:4: e= 'extends' NAME ( ',' NAME )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            e=(Token)match(input,164,FOLLOW_164_in_extendz136); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            e_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(e);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(e_tree, root_0);
            }
            NAME10=(Token)match(input,NAME,FOLLOW_NAME_in_extendz139); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME10_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME10);
            adaptor.addChild(root_0, NAME10_tree);
            }
            // ErlParserRules.g:68:22: ( ',' NAME )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==96) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // ErlParserRules.g:68:23: ',' NAME
            	    {
            	    char_literal11=(Token)match(input,96,FOLLOW_96_in_extendz142); if (state.failed) return retval;
            	    NAME12=(Token)match(input,NAME,FOLLOW_NAME_in_extendz145); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NAME12_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME12);
            	    adaptor.addChild(root_0, NAME12_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
                }
            } while (true);

            if ( state.backtracking==0 ) {
              e.setType(EXTENDS);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
        }
        return retval;
    }
    // $ANTLR end extendz

    // Delegated rules


 

    public static final BitSet FOLLOW_pre_in_erlModuleContent48 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationBlock_in_erlModuleContent52 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationDeclaration_in_erlModuleContent56 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_post_in_erlModuleContent60 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_161_in_pre73 = new BitSet(new long[]{0x0000000000800000L,0x0000000400000000L});
    public static final BitSet FOLLOW_NAME_in_pre76 = new BitSet(new long[]{0x0000000000800000L,0x0000000400000000L});
    public static final BitSet FOLLOW_statementBlock_in_pre79 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_162_in_post95 = new BitSet(new long[]{0x0000000000800000L,0x0000000400000000L});
    public static final BitSet FOLLOW_NAME_in_post98 = new BitSet(new long[]{0x0000000000800000L,0x0000000400000000L});
    public static final BitSet FOLLOW_statementBlock_in_post101 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_163_in_guard117 = new BitSet(new long[]{0x0000000000800000L,0x0000020400000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_guard120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_164_in_extendz136 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_extendz139 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_extendz142 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_extendz145 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});

}
