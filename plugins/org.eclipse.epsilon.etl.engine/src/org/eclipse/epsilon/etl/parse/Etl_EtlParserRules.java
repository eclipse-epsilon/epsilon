package org.eclipse.epsilon.etl.parse;

// $ANTLR 3.1b1 EtlParserRules.g 2025-05-14 20:12:02

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
public class Etl_EtlParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
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
    public static final int TRANSFORM_TO_LIST=89;
    public static final int CASE=40;
    public static final int Letter=20;
    public static final int LINE_COMMENT=26;
    public static final int T__129=129;
    public static final int TRANSFORM_TO=88;
    public static final int T__126=126;
    public static final int JavaIDDigit=22;
    public static final int T__125=125;
    public static final int LAMBDAEXPR=69;
    public static final int MAP=80;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int T__165=165;
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
    public static final int EXTENDS=85;
    public static final int T__93=93;
    public static final int T__102=102;
    public static final int PRE=83;
    public static final int T__94=94;
    public static final int T__101=101;
    public static final int POST=84;
    public static final int ALIAS=75;
    public static final int DRIVER=76;
    public static final int KEYVAL=81;
    public static final int POINT_POINT=10;
    public static final int GUARD=86;
    public static final int T__99=99;
    public static final int TRANSFORM=87;
    public static final int T__95=95;
    public static final int HELPERMETHOD=32;
    public static final int T__96=96;
    public static final int T__97=97;
    public static final int StatementBlock=33;
    public static final int T__98=98;
    public static final int ABORT=48;
    public static final int StrangeNameLiteral=16;
    public static final int ETLMODULE=90;
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
    public static final int T__105=105;

    // delegates
    // delegators
    public EtlParser gEtl;


        public Etl_EtlParserRules(TokenStream input, EtlParser gEtl) {
            this(input, new RecognizerSharedState(), gEtl);
        }
        public Etl_EtlParserRules(TokenStream input, RecognizerSharedState state, EtlParser gEtl) {
            super(input, state);
            this.gEtl = gEtl;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EtlParser.tokenNames; }
    public String getGrammarFileName() { return "EtlParserRules.g"; }


    public static class etlModuleContent_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start etlModuleContent
    // EtlParserRules.g:45:1: etlModuleContent : ( transformationRule | erlModuleContent );
    public final Etl_EtlParserRules.etlModuleContent_return etlModuleContent() throws RecognitionException {
        Etl_EtlParserRules.etlModuleContent_return retval = new Etl_EtlParserRules.etlModuleContent_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EtlParserRules.transformationRule_return transformationRule1 = null;

        Etl_ErlParserRules.erlModuleContent_return erlModuleContent2 = null;



        try {
            // EtlParserRules.g:46:2: ( transformationRule | erlModuleContent )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==163) ) {
                alt1=1;
            }
            else if ( (LA1_0==Annotation||(LA1_0>=99 && LA1_0<=100)||LA1_0==105||(LA1_0>=159 && LA1_0<=160)) ) {
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
                    // EtlParserRules.g:46:4: transformationRule
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_transformationRule_in_etlModuleContent42);
                    transformationRule1=transformationRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, transformationRule1.getTree());

                    }
                    break;
                case 2 :
                    // EtlParserRules.g:46:25: erlModuleContent
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_erlModuleContent_in_etlModuleContent46);
                    erlModuleContent2=gEtl.erlModuleContent();

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
    // $ANTLR end etlModuleContent

    public static class transformationRule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start transformationRule
    // EtlParserRules.g:49:1: transformationRule : r= 'rule' rule= NAME 'transform' formalParameter 'to' transformToList ( extendz )? ob= '{' ( guard )? block cb= '}' ;
    public final Etl_EtlParserRules.transformationRule_return transformationRule() throws RecognitionException {
        Etl_EtlParserRules.transformationRule_return retval = new Etl_EtlParserRules.transformationRule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token r=null;
        Token rule=null;
        Token ob=null;
        Token cb=null;
        Token string_literal3=null;
        Token string_literal5=null;
        Etl_EolParserRules.formalParameter_return formalParameter4 = null;

        Etl_EtlParserRules.transformToList_return transformToList6 = null;

        Etl_ErlParserRules.extendz_return extendz7 = null;

        Etl_ErlParserRules.guard_return guard8 = null;

        Etl_EolParserRules.block_return block9 = null;


        org.eclipse.epsilon.common.parse.AST r_tree=null;
        org.eclipse.epsilon.common.parse.AST rule_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal3_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal5_tree=null;

        try {
            // EtlParserRules.g:54:2: (r= 'rule' rule= NAME 'transform' formalParameter 'to' transformToList ( extendz )? ob= '{' ( guard )? block cb= '}' )
            // EtlParserRules.g:54:4: r= 'rule' rule= NAME 'transform' formalParameter 'to' transformToList ( extendz )? ob= '{' ( guard )? block cb= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            r=(Token)match(input,163,FOLLOW_163_in_transformationRule65); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            r_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(r);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(r_tree, root_0);
            }
            rule=(Token)match(input,NAME,FOLLOW_NAME_in_transformationRule70); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rule_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(rule);
            adaptor.addChild(root_0, rule_tree);
            }
            string_literal3=(Token)match(input,164,FOLLOW_164_in_transformationRule76); if (state.failed) return retval;
            pushFollow(FOLLOW_formalParameter_in_transformationRule79);
            formalParameter4=gEtl.formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameter4.getTree());
            string_literal5=(Token)match(input,165,FOLLOW_165_in_transformationRule81); if (state.failed) return retval;
            pushFollow(FOLLOW_transformToList_in_transformationRule84);
            transformToList6=transformToList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, transformToList6.getTree());
            // EtlParserRules.g:56:4: ( extendz )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==162) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // EtlParserRules.g:0:0: extendz
                    {
                    pushFollow(FOLLOW_extendz_in_transformationRule89);
                    extendz7=gEtl.extendz();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, extendz7.getTree());

                    }
                    break;

            }

            ob=(Token)match(input,96,FOLLOW_96_in_transformationRule94); if (state.failed) return retval;
            // EtlParserRules.g:56:21: ( guard )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==161) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // EtlParserRules.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_transformationRule97);
                    guard8=gEtl.guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard8.getTree());

                    }
                    break;

            }

            pushFollow(FOLLOW_block_in_transformationRule100);
            block9=gEtl.block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, block9.getTree());
            cb=(Token)match(input,97,FOLLOW_97_in_transformationRule104); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              r.setType(TRANSFORM);
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
    // $ANTLR end transformationRule

    public static class transformTo_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start transformTo
    // EtlParserRules.g:60:1: transformTo : fp= formalParameter ( '=' initializer= logicalExpression )? -> ^( TRANSFORM_TO formalParameter ( logicalExpression )? ) ;
    public final Etl_EtlParserRules.transformTo_return transformTo() throws RecognitionException {
        Etl_EtlParserRules.transformTo_return retval = new Etl_EtlParserRules.transformTo_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token char_literal10=null;
        Etl_EolParserRules.formalParameter_return fp = null;

        Etl_EolParserRules.logicalExpression_return initializer = null;


        org.eclipse.epsilon.common.parse.AST char_literal10_tree=null;
        RewriteRuleTokenStream stream_98=new RewriteRuleTokenStream(adaptor,"token 98");
        RewriteRuleSubtreeStream stream_formalParameter=new RewriteRuleSubtreeStream(adaptor,"rule formalParameter");
        RewriteRuleSubtreeStream stream_logicalExpression=new RewriteRuleSubtreeStream(adaptor,"rule logicalExpression");
        try {
            // EtlParserRules.g:61:2: (fp= formalParameter ( '=' initializer= logicalExpression )? -> ^( TRANSFORM_TO formalParameter ( logicalExpression )? ) )
            // EtlParserRules.g:61:4: fp= formalParameter ( '=' initializer= logicalExpression )?
            {
            pushFollow(FOLLOW_formalParameter_in_transformTo122);
            fp=gEtl.formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_formalParameter.add(fp.getTree());
            // EtlParserRules.g:61:23: ( '=' initializer= logicalExpression )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==98) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // EtlParserRules.g:61:24: '=' initializer= logicalExpression
                    {
                    char_literal10=(Token)match(input,98,FOLLOW_98_in_transformTo125); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_98.add(char_literal10);

                    pushFollow(FOLLOW_logicalExpression_in_transformTo129);
                    initializer=gEtl.logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_logicalExpression.add(initializer.getTree());

                    }
                    break;

            }



            // AST REWRITE
            // elements: formalParameter, logicalExpression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 62:2: -> ^( TRANSFORM_TO formalParameter ( logicalExpression )? )
            {
                // EtlParserRules.g:62:5: ^( TRANSFORM_TO formalParameter ( logicalExpression )? )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(TRANSFORM_TO, "TRANSFORM_TO"), root_1);

                adaptor.addChild(root_1, stream_formalParameter.nextTree());
                // EtlParserRules.g:62:36: ( logicalExpression )?
                if ( stream_logicalExpression.hasNext() ) {
                    adaptor.addChild(root_1, stream_logicalExpression.nextTree());

                }
                stream_logicalExpression.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
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
    // $ANTLR end transformTo

    public static class transformToList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start transformToList
    // EtlParserRules.g:65:1: transformToList : transformTo ( ',' transformTo )* -> ^( TRANSFORM_TO_LIST ( transformTo )* ) ;
    public final Etl_EtlParserRules.transformToList_return transformToList() throws RecognitionException {
        Etl_EtlParserRules.transformToList_return retval = new Etl_EtlParserRules.transformToList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token char_literal12=null;
        Etl_EtlParserRules.transformTo_return transformTo11 = null;

        Etl_EtlParserRules.transformTo_return transformTo13 = null;


        org.eclipse.epsilon.common.parse.AST char_literal12_tree=null;
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleSubtreeStream stream_transformTo=new RewriteRuleSubtreeStream(adaptor,"rule transformTo");
        try {
            // EtlParserRules.g:69:2: ( transformTo ( ',' transformTo )* -> ^( TRANSFORM_TO_LIST ( transformTo )* ) )
            // EtlParserRules.g:69:4: transformTo ( ',' transformTo )*
            {
            pushFollow(FOLLOW_transformTo_in_transformToList160);
            transformTo11=transformTo();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_transformTo.add(transformTo11.getTree());
            // EtlParserRules.g:69:16: ( ',' transformTo )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==94) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // EtlParserRules.g:69:17: ',' transformTo
            	    {
            	    char_literal12=(Token)match(input,94,FOLLOW_94_in_transformToList163); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_94.add(char_literal12);

            	    pushFollow(FOLLOW_transformTo_in_transformToList165);
            	    transformTo13=transformTo();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_transformTo.add(transformTo13.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);



            // AST REWRITE
            // elements: transformTo
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 70:2: -> ^( TRANSFORM_TO_LIST ( transformTo )* )
            {
                // EtlParserRules.g:70:5: ^( TRANSFORM_TO_LIST ( transformTo )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(TRANSFORM_TO_LIST, "TRANSFORM_TO_LIST"), root_1);

                // EtlParserRules.g:70:25: ( transformTo )*
                while ( stream_transformTo.hasNext() ) {
                    adaptor.addChild(root_1, stream_transformTo.nextTree());

                }
                stream_transformTo.reset();

                adaptor.addChild(root_0, root_1);
                }

            }

            retval.tree = root_0;retval.tree = root_0;}
            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).setImaginary(true);
               	
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
    // $ANTLR end transformToList

    // Delegated rules


 

    public static final BitSet FOLLOW_transformationRule_in_etlModuleContent42 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_erlModuleContent_in_etlModuleContent46 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_163_in_transformationRule65 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_transformationRule70 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000001000000000L});
    public static final BitSet FOLLOW_164_in_transformationRule76 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_formalParameter_in_transformationRule79 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_165_in_transformationRule81 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_transformToList_in_transformationRule84 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_extendz_in_transformationRule89 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_transformationRule94 = new BitSet(new long[]{0x00000000008EA110L,0x3FE9402200000000L,0x0000000278120000L});
    public static final BitSet FOLLOW_guard_in_transformationRule97 = new BitSet(new long[]{0x00000000008EA110L,0x3FE9402200000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_block_in_transformationRule100 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_transformationRule104 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameter_in_transformTo122 = new BitSet(new long[]{0x0000000000000002L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_transformTo125 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_transformTo129 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_transformTo_in_transformToList160 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_transformToList163 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_transformTo_in_transformToList165 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});

}
