package org.eclipse.epsilon.ecl.parse;

// $ANTLR 3.1b1 EclParserRules.g 2023-07-03 20:54:43

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
public class Ecl_EclParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
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


        public Ecl_EclParserRules(TokenStream input, EclParser gEcl) {
            this(input, new RecognizerSharedState(), gEcl);
        }
        public Ecl_EclParserRules(TokenStream input, RecognizerSharedState state, EclParser gEcl) {
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
    public String getGrammarFileName() { return "EclParserRules.g"; }


    public static class eclModuleContent_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start eclModuleContent
    // EclParserRules.g:49:1: eclModuleContent : ( matchRule | erlModuleContent );
    public final Ecl_EclParserRules.eclModuleContent_return eclModuleContent() throws RecognitionException {
        Ecl_EclParserRules.eclModuleContent_return retval = new Ecl_EclParserRules.eclModuleContent_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Ecl_EclParserRules.matchRule_return matchRule1 = null;

        Ecl_ErlParserRules.erlModuleContent_return erlModuleContent2 = null;



        try {
            // EclParserRules.g:50:2: ( matchRule | erlModuleContent )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==165) ) {
                alt1=1;
            }
            else if ( (LA1_0==Annotation||(LA1_0>=101 && LA1_0<=102)||LA1_0==107||(LA1_0>=161 && LA1_0<=162)) ) {
                alt1=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // EclParserRules.g:50:4: matchRule
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_matchRule_in_eclModuleContent52);
                    matchRule1=matchRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, matchRule1.getTree());

                    }
                    break;
                case 2 :
                    // EclParserRules.g:50:16: erlModuleContent
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_erlModuleContent_in_eclModuleContent56);
                    erlModuleContent2=gEcl.erlModuleContent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, erlModuleContent2.getTree());

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
    // $ANTLR end eclModuleContent

    public static class matchRule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start matchRule
    // EclParserRules.g:53:1: matchRule : r= 'rule' rule= NAME 'match' formalParameter ( leftDomain )? 'with' formalParameter ( rightDomain )? ( extendz )? ob= '{' ( guard )? ( compareBlock )? ( doBlock )? cb= '}' ;
    public final Ecl_EclParserRules.matchRule_return matchRule() throws RecognitionException {
        Ecl_EclParserRules.matchRule_return retval = new Ecl_EclParserRules.matchRule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token r=null;
        Token rule=null;
        Token ob=null;
        Token cb=null;
        Token string_literal3=null;
        Token string_literal6=null;
        Ecl_EolParserRules.formalParameter_return formalParameter4 = null;

        Ecl_EclParserRules.leftDomain_return leftDomain5 = null;

        Ecl_EolParserRules.formalParameter_return formalParameter7 = null;

        Ecl_EclParserRules.rightDomain_return rightDomain8 = null;

        Ecl_ErlParserRules.extendz_return extendz9 = null;

        Ecl_ErlParserRules.guard_return guard10 = null;

        Ecl_EclParserRules.compareBlock_return compareBlock11 = null;

        Ecl_EclParserRules.doBlock_return doBlock12 = null;


        org.eclipse.epsilon.common.parse.AST r_tree=null;
        org.eclipse.epsilon.common.parse.AST rule_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal3_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal6_tree=null;

        try {
            // EclParserRules.g:58:2: (r= 'rule' rule= NAME 'match' formalParameter ( leftDomain )? 'with' formalParameter ( rightDomain )? ( extendz )? ob= '{' ( guard )? ( compareBlock )? ( doBlock )? cb= '}' )
            // EclParserRules.g:58:4: r= 'rule' rule= NAME 'match' formalParameter ( leftDomain )? 'with' formalParameter ( rightDomain )? ( extendz )? ob= '{' ( guard )? ( compareBlock )? ( doBlock )? cb= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            r=(Token)match(input,165,FOLLOW_165_in_matchRule76); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            r_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(r);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(r_tree, root_0);
            }
            rule=(Token)match(input,NAME,FOLLOW_NAME_in_matchRule81); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rule_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(rule);
            adaptor.addChild(root_0, rule_tree);
            }
            string_literal3=(Token)match(input,166,FOLLOW_166_in_matchRule83); if (state.failed) return retval;
            pushFollow(FOLLOW_formalParameter_in_matchRule86);
            formalParameter4=gEcl.formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameter4.getTree());
            // EclParserRules.g:58:49: ( leftDomain )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==118) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // EclParserRules.g:0:0: leftDomain
                    {
                    pushFollow(FOLLOW_leftDomain_in_matchRule88);
                    leftDomain5=leftDomain();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, leftDomain5.getTree());

                    }
                    break;

            }

            string_literal6=(Token)match(input,167,FOLLOW_167_in_matchRule91); if (state.failed) return retval;
            pushFollow(FOLLOW_formalParameter_in_matchRule94);
            formalParameter7=gEcl.formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameter7.getTree());
            // EclParserRules.g:58:85: ( rightDomain )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==118||LA3_0==170) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // EclParserRules.g:0:0: rightDomain
                    {
                    pushFollow(FOLLOW_rightDomain_in_matchRule96);
                    rightDomain8=rightDomain();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, rightDomain8.getTree());

                    }
                    break;

            }

            // EclParserRules.g:59:2: ( extendz )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==164) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // EclParserRules.g:0:0: extendz
                    {
                    pushFollow(FOLLOW_extendz_in_matchRule100);
                    extendz9=gEcl.extendz();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, extendz9.getTree());

                    }
                    break;

            }

            ob=(Token)match(input,98,FOLLOW_98_in_matchRule105); if (state.failed) return retval;
            // EclParserRules.g:59:19: ( guard )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==163) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // EclParserRules.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_matchRule108);
                    guard10=gEcl.guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard10.getTree());

                    }
                    break;

            }

            // EclParserRules.g:59:26: ( compareBlock )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==168) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // EclParserRules.g:0:0: compareBlock
                    {
                    pushFollow(FOLLOW_compareBlock_in_matchRule111);
                    compareBlock11=compareBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, compareBlock11.getTree());

                    }
                    break;

            }

            // EclParserRules.g:59:40: ( doBlock )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==169) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // EclParserRules.g:0:0: doBlock
                    {
                    pushFollow(FOLLOW_doBlock_in_matchRule114);
                    doBlock12=doBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, doBlock12.getTree());

                    }
                    break;

            }

            cb=(Token)match(input,99,FOLLOW_99_in_matchRule119); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              r.setType(MATCH);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(ob);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cb);
              	
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
    // $ANTLR end matchRule

    public static class compareBlock_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start compareBlock
    // EclParserRules.g:63:1: compareBlock : c= 'compare' expressionOrStatementBlock ;
    public final Ecl_EclParserRules.compareBlock_return compareBlock() throws RecognitionException {
        Ecl_EclParserRules.compareBlock_return retval = new Ecl_EclParserRules.compareBlock_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Ecl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock13 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EclParserRules.g:64:2: (c= 'compare' expressionOrStatementBlock )
            // EclParserRules.g:64:4: c= 'compare' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,168,FOLLOW_168_in_compareBlock136); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_compareBlock139);
            expressionOrStatementBlock13=gEcl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock13.getTree());
            if ( state.backtracking==0 ) {
              c.setType(COMPARE);
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
    // $ANTLR end compareBlock

    public static class doBlock_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start doBlock
    // EclParserRules.g:68:1: doBlock : d= 'do' statementBlock ;
    public final Ecl_EclParserRules.doBlock_return doBlock() throws RecognitionException {
        Ecl_EclParserRules.doBlock_return retval = new Ecl_EclParserRules.doBlock_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token d=null;
        Ecl_EolParserRules.statementBlock_return statementBlock14 = null;


        org.eclipse.epsilon.common.parse.AST d_tree=null;

        try {
            // EclParserRules.g:69:2: (d= 'do' statementBlock )
            // EclParserRules.g:69:4: d= 'do' statementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            d=(Token)match(input,169,FOLLOW_169_in_doBlock155); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            d_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(d);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(d_tree, root_0);
            }
            pushFollow(FOLLOW_statementBlock_in_doBlock158);
            statementBlock14=gEcl.statementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock14.getTree());
            if ( state.backtracking==0 ) {
              d.setType(DO);
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
    // $ANTLR end doBlock

    public static class leftDomain_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start leftDomain
    // EclParserRules.g:73:1: leftDomain : (c= 'in' ) expressionOrStatementBlock ;
    public final Ecl_EclParserRules.leftDomain_return leftDomain() throws RecognitionException {
        Ecl_EclParserRules.leftDomain_return retval = new Ecl_EclParserRules.leftDomain_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Ecl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock15 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EclParserRules.g:74:2: ( (c= 'in' ) expressionOrStatementBlock )
            // EclParserRules.g:74:4: (c= 'in' ) expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EclParserRules.g:74:4: (c= 'in' )
            // EclParserRules.g:74:5: c= 'in'
            {
            c=(Token)match(input,118,FOLLOW_118_in_leftDomain177); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }

            }

            pushFollow(FOLLOW_expressionOrStatementBlock_in_leftDomain181);
            expressionOrStatementBlock15=gEcl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock15.getTree());
            if ( state.backtracking==0 ) {
              c.setType(LEFTDOMAIN);
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
    // $ANTLR end leftDomain

    public static class rightDomain_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start rightDomain
    // EclParserRules.g:78:1: rightDomain : (c= 'in' | c= 'from' ) expressionOrStatementBlock ;
    public final Ecl_EclParserRules.rightDomain_return rightDomain() throws RecognitionException {
        Ecl_EclParserRules.rightDomain_return retval = new Ecl_EclParserRules.rightDomain_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Ecl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock16 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EclParserRules.g:79:2: ( (c= 'in' | c= 'from' ) expressionOrStatementBlock )
            // EclParserRules.g:79:4: (c= 'in' | c= 'from' ) expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EclParserRules.g:79:4: (c= 'in' | c= 'from' )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==118) ) {
                alt8=1;
            }
            else if ( (LA8_0==170) ) {
                alt8=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // EclParserRules.g:79:5: c= 'in'
                    {
                    c=(Token)match(input,118,FOLLOW_118_in_rightDomain200); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // EclParserRules.g:79:13: c= 'from'
                    {
                    c=(Token)match(input,170,FOLLOW_170_in_rightDomain205); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_expressionOrStatementBlock_in_rightDomain209);
            expressionOrStatementBlock16=gEcl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock16.getTree());
            if ( state.backtracking==0 ) {
              c.setType(RIGHTDOMAIN);
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
    // $ANTLR end rightDomain

    // Delegated rules


 

    public static final BitSet FOLLOW_matchRule_in_eclModuleContent52 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_erlModuleContent_in_eclModuleContent56 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_165_in_matchRule76 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_matchRule81 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_166_in_matchRule83 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_formalParameter_in_matchRule86 = new BitSet(new long[]{0x0000000000000000L,0x0040000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_leftDomain_in_matchRule88 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_167_in_matchRule91 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_formalParameter_in_matchRule94 = new BitSet(new long[]{0x0000000000000000L,0x0040000400000000L,0x0000041000000000L});
    public static final BitSet FOLLOW_rightDomain_in_matchRule96 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_extendz_in_matchRule100 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_matchRule105 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L,0x0000030800000000L});
    public static final BitSet FOLLOW_guard_in_matchRule108 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L,0x0000030000000000L});
    public static final BitSet FOLLOW_compareBlock_in_matchRule111 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_doBlock_in_matchRule114 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_matchRule119 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_168_in_compareBlock136 = new BitSet(new long[]{0x0000000000000000L,0x0000020400000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_compareBlock139 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_169_in_doBlock155 = new BitSet(new long[]{0x0000000000000000L,0x0000020400000000L});
    public static final BitSet FOLLOW_statementBlock_in_doBlock158 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_leftDomain177 = new BitSet(new long[]{0x0000000000000000L,0x0000020400000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_leftDomain181 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_rightDomain200 = new BitSet(new long[]{0x0000000000000000L,0x0000020400000000L});
    public static final BitSet FOLLOW_170_in_rightDomain205 = new BitSet(new long[]{0x0000000000000000L,0x0000020400000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_rightDomain209 = new BitSet(new long[]{0x0000000000000002L});

}
