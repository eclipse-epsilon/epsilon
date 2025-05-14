package org.eclipse.epsilon.etl.parse;

// $ANTLR 3.1b1 EolParserRules.g 2025-05-14 20:12:02

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
 * https://www.eclipse.org/legal/epl-v20.html
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
public class Etl_EolParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
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


        public Etl_EolParserRules(TokenStream input, EtlParser gEtl) {
            this(input, new RecognizerSharedState(), gEtl);
        }
        public Etl_EolParserRules(TokenStream input, RecognizerSharedState state, EtlParser gEtl) {
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
    public String getGrammarFileName() { return "EolParserRules.g"; }



    public void setTokenType(ParserRuleReturnScope tree, int type) {
    	((CommonTree) tree.getTree()).getToken().setType(type);
    }



    public static class operationDeclarationOrAnnotationBlock_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start operationDeclarationOrAnnotationBlock
    // EolParserRules.g:107:1: operationDeclarationOrAnnotationBlock : ( operationDeclaration | annotationBlock );
    public final Etl_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock() throws RecognitionException {
        Etl_EolParserRules.operationDeclarationOrAnnotationBlock_return retval = new Etl_EolParserRules.operationDeclarationOrAnnotationBlock_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.operationDeclaration_return operationDeclaration1 = null;

        Etl_EolParserRules.annotationBlock_return annotationBlock2 = null;



        try {
            // EolParserRules.g:108:2: ( operationDeclaration | annotationBlock )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( ((LA1_0>=99 && LA1_0<=100)) ) {
                alt1=1;
            }
            else if ( (LA1_0==Annotation||LA1_0==105) ) {
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
                    // EolParserRules.g:108:4: operationDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_operationDeclaration_in_operationDeclarationOrAnnotationBlock263);
                    operationDeclaration1=operationDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operationDeclaration1.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:108:27: annotationBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_annotationBlock_in_operationDeclarationOrAnnotationBlock267);
                    annotationBlock2=annotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, annotationBlock2.getTree());

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
    // $ANTLR end operationDeclarationOrAnnotationBlock

    public static class modelDeclaration_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start modelDeclaration
    // EolParserRules.g:111:1: modelDeclaration : m= 'model' NAME ( modelAlias )? ( modelDriver )? ( modelDeclarationParameters )? sem= ';' ;
    public final Etl_EolParserRules.modelDeclaration_return modelDeclaration() throws RecognitionException {
        Etl_EolParserRules.modelDeclaration_return retval = new Etl_EolParserRules.modelDeclaration_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token m=null;
        Token sem=null;
        Token NAME3=null;
        Etl_EolParserRules.modelAlias_return modelAlias4 = null;

        Etl_EolParserRules.modelDriver_return modelDriver5 = null;

        Etl_EolParserRules.modelDeclarationParameters_return modelDeclarationParameters6 = null;


        org.eclipse.epsilon.common.parse.AST m_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME3_tree=null;

        try {
            // EolParserRules.g:116:2: (m= 'model' NAME ( modelAlias )? ( modelDriver )? ( modelDeclarationParameters )? sem= ';' )
            // EolParserRules.g:116:4: m= 'model' NAME ( modelAlias )? ( modelDriver )? ( modelDeclarationParameters )? sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            m=(Token)match(input,91,FOLLOW_91_in_modelDeclaration286); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            m_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(m);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(m_tree, root_0);
            }
            NAME3=(Token)match(input,NAME,FOLLOW_NAME_in_modelDeclaration289); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME3_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME3);
            adaptor.addChild(root_0, NAME3_tree);
            }
            // EolParserRules.g:116:20: ( modelAlias )?
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==93) ) {
                alt2=1;
            }
            switch (alt2) {
                case 1 :
                    // EolParserRules.g:0:0: modelAlias
                    {
                    pushFollow(FOLLOW_modelAlias_in_modelDeclaration291);
                    modelAlias4=modelAlias();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, modelAlias4.getTree());

                    }
                    break;

            }

            // EolParserRules.g:116:32: ( modelDriver )?
            int alt3=2;
            int LA3_0 = input.LA(1);

            if ( (LA3_0==95) ) {
                alt3=1;
            }
            switch (alt3) {
                case 1 :
                    // EolParserRules.g:0:0: modelDriver
                    {
                    pushFollow(FOLLOW_modelDriver_in_modelDeclaration294);
                    modelDriver5=modelDriver();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, modelDriver5.getTree());

                    }
                    break;

            }

            // EolParserRules.g:116:45: ( modelDeclarationParameters )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==96) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // EolParserRules.g:0:0: modelDeclarationParameters
                    {
                    pushFollow(FOLLOW_modelDeclarationParameters_in_modelDeclaration297);
                    modelDeclarationParameters6=modelDeclarationParameters();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, modelDeclarationParameters6.getTree());

                    }
                    break;

            }

            sem=(Token)match(input,92,FOLLOW_92_in_modelDeclaration302); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(MODELDECLARATION);
              	
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
    // $ANTLR end modelDeclaration

    public static class modelAlias_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start modelAlias
    // EolParserRules.g:119:1: modelAlias : a= 'alias' NAME ( ',' NAME )* ;
    public final Etl_EolParserRules.modelAlias_return modelAlias() throws RecognitionException {
        Etl_EolParserRules.modelAlias_return retval = new Etl_EolParserRules.modelAlias_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token a=null;
        Token NAME7=null;
        Token char_literal8=null;
        Token NAME9=null;

        org.eclipse.epsilon.common.parse.AST a_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME7_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal8_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME9_tree=null;

        try {
            // EolParserRules.g:120:2: (a= 'alias' NAME ( ',' NAME )* )
            // EolParserRules.g:120:5: a= 'alias' NAME ( ',' NAME )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            a=(Token)match(input,93,FOLLOW_93_in_modelAlias317); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            a_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(a);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(a_tree, root_0);
            }
            NAME7=(Token)match(input,NAME,FOLLOW_NAME_in_modelAlias320); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME7_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME7);
            adaptor.addChild(root_0, NAME7_tree);
            }
            // EolParserRules.g:120:21: ( ',' NAME )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==94) ) {
                    alt5=1;
                }


                switch (alt5) {
            	case 1 :
            	    // EolParserRules.g:120:22: ',' NAME
            	    {
            	    char_literal8=(Token)match(input,94,FOLLOW_94_in_modelAlias323); if (state.failed) return retval;
            	    NAME9=(Token)match(input,NAME,FOLLOW_NAME_in_modelAlias326); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NAME9_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME9);
            	    adaptor.addChild(root_0, NAME9_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop5;
                }
            } while (true);

            if ( state.backtracking==0 ) {
              a.setType(ALIAS);
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
    // $ANTLR end modelAlias

    public static class modelDriver_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start modelDriver
    // EolParserRules.g:124:1: modelDriver : d= 'driver' NAME ;
    public final Etl_EolParserRules.modelDriver_return modelDriver() throws RecognitionException {
        Etl_EolParserRules.modelDriver_return retval = new Etl_EolParserRules.modelDriver_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token d=null;
        Token NAME10=null;

        org.eclipse.epsilon.common.parse.AST d_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME10_tree=null;

        try {
            // EolParserRules.g:125:2: (d= 'driver' NAME )
            // EolParserRules.g:125:5: d= 'driver' NAME
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            d=(Token)match(input,95,FOLLOW_95_in_modelDriver345); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            d_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(d);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(d_tree, root_0);
            }
            NAME10=(Token)match(input,NAME,FOLLOW_NAME_in_modelDriver348); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME10_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME10);
            adaptor.addChild(root_0, NAME10_tree);
            }
            if ( state.backtracking==0 ) {
              d.setType(DRIVER);
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
    // $ANTLR end modelDriver

    public static class modelDeclarationParameters_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start modelDeclarationParameters
    // EolParserRules.g:129:1: modelDeclarationParameters : s= '{' ( modelDeclarationParameter )? ( ',' modelDeclarationParameter )* cb= '}' ;
    public final Etl_EolParserRules.modelDeclarationParameters_return modelDeclarationParameters() throws RecognitionException {
        Etl_EolParserRules.modelDeclarationParameters_return retval = new Etl_EolParserRules.modelDeclarationParameters_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token s=null;
        Token cb=null;
        Token char_literal12=null;
        Etl_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter11 = null;

        Etl_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter13 = null;


        org.eclipse.epsilon.common.parse.AST s_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal12_tree=null;

        try {
            // EolParserRules.g:134:2: (s= '{' ( modelDeclarationParameter )? ( ',' modelDeclarationParameter )* cb= '}' )
            // EolParserRules.g:134:4: s= '{' ( modelDeclarationParameter )? ( ',' modelDeclarationParameter )* cb= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            s=(Token)match(input,96,FOLLOW_96_in_modelDeclarationParameters370); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            s_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(s);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(s_tree, root_0);
            }
            // EolParserRules.g:134:11: ( modelDeclarationParameter )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==NAME) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // EolParserRules.g:0:0: modelDeclarationParameter
                    {
                    pushFollow(FOLLOW_modelDeclarationParameter_in_modelDeclarationParameters373);
                    modelDeclarationParameter11=modelDeclarationParameter();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, modelDeclarationParameter11.getTree());

                    }
                    break;

            }

            // EolParserRules.g:134:38: ( ',' modelDeclarationParameter )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==94) ) {
                    alt7=1;
                }


                switch (alt7) {
            	case 1 :
            	    // EolParserRules.g:134:39: ',' modelDeclarationParameter
            	    {
            	    char_literal12=(Token)match(input,94,FOLLOW_94_in_modelDeclarationParameters377); if (state.failed) return retval;
            	    pushFollow(FOLLOW_modelDeclarationParameter_in_modelDeclarationParameters380);
            	    modelDeclarationParameter13=modelDeclarationParameter();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, modelDeclarationParameter13.getTree());

            	    }
            	    break;

            	default :
            	    break loop7;
                }
            } while (true);

            cb=(Token)match(input,97,FOLLOW_97_in_modelDeclarationParameters386); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cb);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(MODELDECLARATIONPARAMETERS);
              	
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
    // $ANTLR end modelDeclarationParameters

    public static class modelDeclarationParameter_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start modelDeclarationParameter
    // EolParserRules.g:137:1: modelDeclarationParameter : NAME e= '=' STRING ;
    public final Etl_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter() throws RecognitionException {
        Etl_EolParserRules.modelDeclarationParameter_return retval = new Etl_EolParserRules.modelDeclarationParameter_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token e=null;
        Token NAME14=null;
        Token STRING15=null;

        org.eclipse.epsilon.common.parse.AST e_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME14_tree=null;
        org.eclipse.epsilon.common.parse.AST STRING15_tree=null;

        try {
            // EolParserRules.g:138:2: ( NAME e= '=' STRING )
            // EolParserRules.g:138:4: NAME e= '=' STRING
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            NAME14=(Token)match(input,NAME,FOLLOW_NAME_in_modelDeclarationParameter399); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME14_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME14);
            adaptor.addChild(root_0, NAME14_tree);
            }
            e=(Token)match(input,98,FOLLOW_98_in_modelDeclarationParameter403); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            e_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(e);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(e_tree, root_0);
            }
            STRING15=(Token)match(input,STRING,FOLLOW_STRING_in_modelDeclarationParameter406); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            STRING15_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(STRING15);
            adaptor.addChild(root_0, STRING15_tree);
            }
            if ( state.backtracking==0 ) {
              e.setType(MODELDECLARATIONPARAMETER);
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
    // $ANTLR end modelDeclarationParameter

    public static class operationDeclaration_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start operationDeclaration
    // EolParserRules.g:142:1: operationDeclaration : ( 'operation' | 'function' ) (ctx= typeName )? operationName= NAME op= '(' ( formalParameterList )? cp= ')' ( ':' returnType= typeName )? statementBlock ;
    public final Etl_EolParserRules.operationDeclaration_return operationDeclaration() throws RecognitionException {
        Etl_EolParserRules.operationDeclaration_return retval = new Etl_EolParserRules.operationDeclaration_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token operationName=null;
        Token op=null;
        Token cp=null;
        Token set16=null;
        Token char_literal18=null;
        Etl_EolParserRules.typeName_return ctx = null;

        Etl_EolParserRules.typeName_return returnType = null;

        Etl_EolParserRules.formalParameterList_return formalParameterList17 = null;

        Etl_EolParserRules.statementBlock_return statementBlock19 = null;


        org.eclipse.epsilon.common.parse.AST operationName_tree=null;
        org.eclipse.epsilon.common.parse.AST op_tree=null;
        org.eclipse.epsilon.common.parse.AST cp_tree=null;
        org.eclipse.epsilon.common.parse.AST set16_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal18_tree=null;

        try {
            // EolParserRules.g:147:2: ( ( 'operation' | 'function' ) (ctx= typeName )? operationName= NAME op= '(' ( formalParameterList )? cp= ')' ( ':' returnType= typeName )? statementBlock )
            // EolParserRules.g:147:4: ( 'operation' | 'function' ) (ctx= typeName )? operationName= NAME op= '(' ( formalParameterList )? cp= ')' ( ':' returnType= typeName )? statementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            set16=(Token)input.LT(1);
            set16=(Token)input.LT(1);
            if ( (input.LA(1)>=99 && input.LA(1)<=100) ) {
                input.consume();
                if ( state.backtracking==0 ) root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(set16), root_0);
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EolParserRules.g:147:30: (ctx= typeName )?
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==NAME) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==NAME||(LA8_1>=106 && LA8_1<=107)) ) {
                    alt8=1;
                }
            }
            else if ( ((LA8_0>=CollectionTypeName && LA8_0<=SpecialTypeName)) ) {
                alt8=1;
            }
            switch (alt8) {
                case 1 :
                    // EolParserRules.g:147:31: ctx= typeName
                    {
                    pushFollow(FOLLOW_typeName_in_operationDeclaration437);
                    ctx=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ctx.getTree());
                    if ( state.backtracking==0 ) {
                      setTokenType(ctx,TYPE);
                    }

                    }
                    break;

            }

            operationName=(Token)match(input,NAME,FOLLOW_NAME_in_operationDeclaration447); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            operationName_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(operationName);
            adaptor.addChild(root_0, operationName_tree);
            }
            op=(Token)match(input,101,FOLLOW_101_in_operationDeclaration451); if (state.failed) return retval;
            // EolParserRules.g:148:30: ( formalParameterList )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==NAME) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // EolParserRules.g:0:0: formalParameterList
                    {
                    pushFollow(FOLLOW_formalParameterList_in_operationDeclaration454);
                    formalParameterList17=formalParameterList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterList17.getTree());

                    }
                    break;

            }

            cp=(Token)match(input,102,FOLLOW_102_in_operationDeclaration459); if (state.failed) return retval;
            // EolParserRules.g:149:3: ( ':' returnType= typeName )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==103) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // EolParserRules.g:149:4: ':' returnType= typeName
                    {
                    char_literal18=(Token)match(input,103,FOLLOW_103_in_operationDeclaration465); if (state.failed) return retval;
                    pushFollow(FOLLOW_typeName_in_operationDeclaration470);
                    returnType=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, returnType.getTree());
                    if ( state.backtracking==0 ) {
                      setTokenType(returnType,TYPE);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_statementBlock_in_operationDeclaration476);
            statementBlock19=statementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock19.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cp);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(HELPERMETHOD);
              	
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
    // $ANTLR end operationDeclaration

    public static class importStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start importStatement
    // EolParserRules.g:152:1: importStatement : i= 'import' STRING sem= ';' ;
    public final Etl_EolParserRules.importStatement_return importStatement() throws RecognitionException {
        Etl_EolParserRules.importStatement_return retval = new Etl_EolParserRules.importStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token i=null;
        Token sem=null;
        Token STRING20=null;

        org.eclipse.epsilon.common.parse.AST i_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;
        org.eclipse.epsilon.common.parse.AST STRING20_tree=null;

        try {
            // EolParserRules.g:156:2: (i= 'import' STRING sem= ';' )
            // EolParserRules.g:156:4: i= 'import' STRING sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            i=(Token)match(input,104,FOLLOW_104_in_importStatement496); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            i_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(i);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(i_tree, root_0);
            }
            STRING20=(Token)match(input,STRING,FOLLOW_STRING_in_importStatement499); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            STRING20_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(STRING20);
            adaptor.addChild(root_0, STRING20_tree);
            }
            sem=(Token)match(input,92,FOLLOW_92_in_importStatement503); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              i.setType(IMPORT);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end importStatement

    public static class block_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start block
    // EolParserRules.g:160:1: block : ( statement )* -> ^( BLOCK ( statement )* ) ;
    public final Etl_EolParserRules.block_return block() throws RecognitionException {
        Etl_EolParserRules.block_return retval = new Etl_EolParserRules.block_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.statement_return statement21 = null;


        RewriteRuleSubtreeStream stream_statement=new RewriteRuleSubtreeStream(adaptor,"rule statement");
        try {
            // EolParserRules.g:164:2: ( ( statement )* -> ^( BLOCK ( statement )* ) )
            // EolParserRules.g:164:4: ( statement )*
            {
            // EolParserRules.g:164:4: ( statement )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0==FLOAT||LA11_0==INT||LA11_0==BOOLEAN||LA11_0==STRING||(LA11_0>=CollectionTypeName && LA11_0<=SpecialTypeName)||LA11_0==NAME||LA11_0==101||LA11_0==110||LA11_0==112||LA11_0==115||(LA11_0>=117 && LA11_0<=125)||LA11_0==145||LA11_0==148||(LA11_0>=155 && LA11_0<=158)) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // EolParserRules.g:0:0: statement
            	    {
            	    pushFollow(FOLLOW_statement_in_block524);
            	    statement21=statement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_statement.add(statement21.getTree());

            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);



            // AST REWRITE
            // elements: statement
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 165:2: -> ^( BLOCK ( statement )* )
            {
                // EolParserRules.g:165:5: ^( BLOCK ( statement )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(BLOCK, "BLOCK"), root_1);

                // EolParserRules.g:165:13: ( statement )*
                while ( stream_statement.hasNext() ) {
                    adaptor.addChild(root_1, stream_statement.nextTree());

                }
                stream_statement.reset();

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
    // $ANTLR end block

    public static class statementBlock_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statementBlock
    // EolParserRules.g:168:1: statementBlock : s= '{' block e= '}' ;
    public final Etl_EolParserRules.statementBlock_return statementBlock() throws RecognitionException {
        Etl_EolParserRules.statementBlock_return retval = new Etl_EolParserRules.statementBlock_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token s=null;
        Token e=null;
        Etl_EolParserRules.block_return block22 = null;


        org.eclipse.epsilon.common.parse.AST s_tree=null;
        org.eclipse.epsilon.common.parse.AST e_tree=null;

        try {
            // EolParserRules.g:173:2: (s= '{' block e= '}' )
            // EolParserRules.g:173:4: s= '{' block e= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            s=(Token)match(input,96,FOLLOW_96_in_statementBlock554); if (state.failed) return retval;
            pushFollow(FOLLOW_block_in_statementBlock557);
            block22=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, block22.getTree());
            e=(Token)match(input,97,FOLLOW_97_in_statementBlock561); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(s); 
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(e);
              	
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
    // $ANTLR end statementBlock

    public static class formalParameter_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start formalParameter
    // EolParserRules.g:176:1: formalParameter : NAME ( ':' pt= typeName )? -> ^( FORMAL NAME ( typeName )? ) ;
    public final Etl_EolParserRules.formalParameter_return formalParameter() throws RecognitionException {
        Etl_EolParserRules.formalParameter_return retval = new Etl_EolParserRules.formalParameter_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token NAME23=null;
        Token char_literal24=null;
        Etl_EolParserRules.typeName_return pt = null;


        org.eclipse.epsilon.common.parse.AST NAME23_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal24_tree=null;
        RewriteRuleTokenStream stream_103=new RewriteRuleTokenStream(adaptor,"token 103");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");
        RewriteRuleSubtreeStream stream_typeName=new RewriteRuleSubtreeStream(adaptor,"rule typeName");
        try {
            // EolParserRules.g:180:2: ( NAME ( ':' pt= typeName )? -> ^( FORMAL NAME ( typeName )? ) )
            // EolParserRules.g:180:4: NAME ( ':' pt= typeName )?
            {
            NAME23=(Token)match(input,NAME,FOLLOW_NAME_in_formalParameter579); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME23);

            // EolParserRules.g:180:9: ( ':' pt= typeName )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==103) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // EolParserRules.g:180:10: ':' pt= typeName
                    {
                    char_literal24=(Token)match(input,103,FOLLOW_103_in_formalParameter582); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_103.add(char_literal24);

                    pushFollow(FOLLOW_typeName_in_formalParameter586);
                    pt=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_typeName.add(pt.getTree());
                    if ( state.backtracking==0 ) {
                      setTokenType(pt,TYPE);
                    }

                    }
                    break;

            }



            // AST REWRITE
            // elements: NAME, typeName
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 181:3: -> ^( FORMAL NAME ( typeName )? )
            {
                // EolParserRules.g:181:6: ^( FORMAL NAME ( typeName )? )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(FORMAL, "FORMAL"), root_1);

                adaptor.addChild(root_1, stream_NAME.nextNode());
                // EolParserRules.g:181:20: ( typeName )?
                if ( stream_typeName.hasNext() ) {
                    adaptor.addChild(root_1, stream_typeName.nextTree());

                }
                stream_typeName.reset();

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

              	//	((org.eclipse.epsilon.common.parse.AST)retval.tree).setImaginary(true);
              	
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
    // $ANTLR end formalParameter

    public static class formalParameterList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start formalParameterList
    // EolParserRules.g:184:1: formalParameterList : formalParameter ( ',' formalParameter )* -> ^( PARAMLIST ( formalParameter )* ) ;
    public final Etl_EolParserRules.formalParameterList_return formalParameterList() throws RecognitionException {
        Etl_EolParserRules.formalParameterList_return retval = new Etl_EolParserRules.formalParameterList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token char_literal26=null;
        Etl_EolParserRules.formalParameter_return formalParameter25 = null;

        Etl_EolParserRules.formalParameter_return formalParameter27 = null;


        org.eclipse.epsilon.common.parse.AST char_literal26_tree=null;
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleSubtreeStream stream_formalParameter=new RewriteRuleSubtreeStream(adaptor,"rule formalParameter");
        try {
            // EolParserRules.g:188:2: ( formalParameter ( ',' formalParameter )* -> ^( PARAMLIST ( formalParameter )* ) )
            // EolParserRules.g:188:4: formalParameter ( ',' formalParameter )*
            {
            pushFollow(FOLLOW_formalParameter_in_formalParameterList620);
            formalParameter25=formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_formalParameter.add(formalParameter25.getTree());
            // EolParserRules.g:188:20: ( ',' formalParameter )*
            loop13:
            do {
                int alt13=2;
                int LA13_0 = input.LA(1);

                if ( (LA13_0==94) ) {
                    alt13=1;
                }


                switch (alt13) {
            	case 1 :
            	    // EolParserRules.g:188:21: ',' formalParameter
            	    {
            	    char_literal26=(Token)match(input,94,FOLLOW_94_in_formalParameterList623); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_94.add(char_literal26);

            	    pushFollow(FOLLOW_formalParameter_in_formalParameterList625);
            	    formalParameter27=formalParameter();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_formalParameter.add(formalParameter27.getTree());

            	    }
            	    break;

            	default :
            	    break loop13;
                }
            } while (true);



            // AST REWRITE
            // elements: formalParameter
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 189:2: -> ^( PARAMLIST ( formalParameter )* )
            {
                // EolParserRules.g:189:5: ^( PARAMLIST ( formalParameter )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(PARAMLIST, "PARAMLIST"), root_1);

                // EolParserRules.g:189:17: ( formalParameter )*
                while ( stream_formalParameter.hasNext() ) {
                    adaptor.addChild(root_1, stream_formalParameter.nextTree());

                }
                stream_formalParameter.reset();

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
    // $ANTLR end formalParameterList

    public static class executableAnnotation_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start executableAnnotation
    // EolParserRules.g:192:1: executableAnnotation : d= '$' x= . logicalExpression ;
    public final Etl_EolParserRules.executableAnnotation_return executableAnnotation() throws RecognitionException {
        Etl_EolParserRules.executableAnnotation_return retval = new Etl_EolParserRules.executableAnnotation_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token d=null;
        Token x=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression28 = null;


        org.eclipse.epsilon.common.parse.AST d_tree=null;
        org.eclipse.epsilon.common.parse.AST x_tree=null;

        try {
            // EolParserRules.g:193:2: (d= '$' x= . logicalExpression )
            // EolParserRules.g:193:4: d= '$' x= . logicalExpression
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            d=(Token)match(input,105,FOLLOW_105_in_executableAnnotation650); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            d_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(d);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(d_tree, root_0);
            }
            x=(Token)input.LT(1);
            matchAny(input); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            x_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(x);
            adaptor.addChild(root_0, x_tree);
            }
            pushFollow(FOLLOW_logicalExpression_in_executableAnnotation657);
            logicalExpression28=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression28.getTree());
            if ( state.backtracking==0 ) {
              d.setType(EXECUTABLEANNOTATION);
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
    // $ANTLR end executableAnnotation

    public static class annotation_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start annotation
    // EolParserRules.g:197:1: annotation : ( Annotation | executableAnnotation );
    public final Etl_EolParserRules.annotation_return annotation() throws RecognitionException {
        Etl_EolParserRules.annotation_return retval = new Etl_EolParserRules.annotation_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token Annotation29=null;
        Etl_EolParserRules.executableAnnotation_return executableAnnotation30 = null;


        org.eclipse.epsilon.common.parse.AST Annotation29_tree=null;

        try {
            // EolParserRules.g:198:2: ( Annotation | executableAnnotation )
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==Annotation) ) {
                alt14=1;
            }
            else if ( (LA14_0==105) ) {
                alt14=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 14, 0, input);

                throw nvae;
            }
            switch (alt14) {
                case 1 :
                    // EolParserRules.g:198:4: Annotation
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    Annotation29=(Token)match(input,Annotation,FOLLOW_Annotation_in_annotation671); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    Annotation29_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(Annotation29);
                    adaptor.addChild(root_0, Annotation29_tree);
                    }

                    }
                    break;
                case 2 :
                    // EolParserRules.g:198:17: executableAnnotation
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_executableAnnotation_in_annotation675);
                    executableAnnotation30=executableAnnotation();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, executableAnnotation30.getTree());

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
    // $ANTLR end annotation

    public static class annotationBlock_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start annotationBlock
    // EolParserRules.g:201:1: annotationBlock : ( annotation )+ -> ^( ANNOTATIONBLOCK ( annotation )+ ) ;
    public final Etl_EolParserRules.annotationBlock_return annotationBlock() throws RecognitionException {
        Etl_EolParserRules.annotationBlock_return retval = new Etl_EolParserRules.annotationBlock_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.annotation_return annotation31 = null;


        RewriteRuleSubtreeStream stream_annotation=new RewriteRuleSubtreeStream(adaptor,"rule annotation");
        try {
            // EolParserRules.g:205:2: ( ( annotation )+ -> ^( ANNOTATIONBLOCK ( annotation )+ ) )
            // EolParserRules.g:205:4: ( annotation )+
            {
            // EolParserRules.g:205:4: ( annotation )+
            int cnt15=0;
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==Annotation||LA15_0==105) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // EolParserRules.g:0:0: annotation
            	    {
            	    pushFollow(FOLLOW_annotation_in_annotationBlock692);
            	    annotation31=annotation();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_annotation.add(annotation31.getTree());

            	    }
            	    break;

            	default :
            	    if ( cnt15 >= 1 ) break loop15;
            	    if (state.backtracking>0) {state.failed=true; return retval;}
                        EarlyExitException eee =
                            new EarlyExitException(15, input);
                        throw eee;
                }
                cnt15++;
            } while (true);



            // AST REWRITE
            // elements: annotation
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 206:2: -> ^( ANNOTATIONBLOCK ( annotation )+ )
            {
                // EolParserRules.g:206:5: ^( ANNOTATIONBLOCK ( annotation )+ )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(ANNOTATIONBLOCK, "ANNOTATIONBLOCK"), root_1);

                if ( !(stream_annotation.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_annotation.hasNext() ) {
                    adaptor.addChild(root_1, stream_annotation.nextTree());

                }
                stream_annotation.reset();

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
    // $ANTLR end annotationBlock

    public static class typeName_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start typeName
    // EolParserRules.g:209:1: typeName : ( pathName | collectionType | specialType );
    public final Etl_EolParserRules.typeName_return typeName() throws RecognitionException {
        Etl_EolParserRules.typeName_return retval = new Etl_EolParserRules.typeName_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.pathName_return pathName32 = null;

        Etl_EolParserRules.collectionType_return collectionType33 = null;

        Etl_EolParserRules.specialType_return specialType34 = null;



        try {
            // EolParserRules.g:213:2: ( pathName | collectionType | specialType )
            int alt16=3;
            switch ( input.LA(1) ) {
            case NAME:
                {
                alt16=1;
                }
                break;
            case CollectionTypeName:
            case MapTypeName:
                {
                alt16=2;
                }
                break;
            case SpecialTypeName:
                {
                alt16=3;
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
                    // EolParserRules.g:213:4: pathName
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_pathName_in_typeName721);
                    pathName32=pathName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pathName32.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:213:15: collectionType
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_collectionType_in_typeName725);
                    collectionType33=collectionType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, collectionType33.getTree());

                    }
                    break;
                case 3 :
                    // EolParserRules.g:213:32: specialType
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_specialType_in_typeName729);
                    specialType34=specialType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, specialType34.getTree());

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(TYPE);
              	
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
    // $ANTLR end typeName

    public static class specialType_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start specialType
    // EolParserRules.g:216:1: specialType : SpecialTypeName s= '(' STRING e= ')' ;
    public final Etl_EolParserRules.specialType_return specialType() throws RecognitionException {
        Etl_EolParserRules.specialType_return retval = new Etl_EolParserRules.specialType_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token s=null;
        Token e=null;
        Token SpecialTypeName35=null;
        Token STRING36=null;

        org.eclipse.epsilon.common.parse.AST s_tree=null;
        org.eclipse.epsilon.common.parse.AST e_tree=null;
        org.eclipse.epsilon.common.parse.AST SpecialTypeName35_tree=null;
        org.eclipse.epsilon.common.parse.AST STRING36_tree=null;

        try {
            // EolParserRules.g:222:2: ( SpecialTypeName s= '(' STRING e= ')' )
            // EolParserRules.g:222:4: SpecialTypeName s= '(' STRING e= ')'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            SpecialTypeName35=(Token)match(input,SpecialTypeName,FOLLOW_SpecialTypeName_in_specialType746); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SpecialTypeName35_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(SpecialTypeName35);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(SpecialTypeName35_tree, root_0);
            }
            s=(Token)match(input,101,FOLLOW_101_in_specialType751); if (state.failed) return retval;
            STRING36=(Token)match(input,STRING,FOLLOW_STRING_in_specialType754); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            STRING36_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(STRING36);
            adaptor.addChild(root_0, STRING36_tree);
            }
            e=(Token)match(input,102,FOLLOW_102_in_specialType758); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(s); 
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(e);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(TYPE);
              	
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
    // $ANTLR end specialType

    public static class pathName_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pathName
    // EolParserRules.g:225:1: pathName : (metamodel= NAME '!' )? head= packagedType ;
    public final Etl_EolParserRules.pathName_return pathName() throws RecognitionException {
        Etl_EolParserRules.pathName_return retval = new Etl_EolParserRules.pathName_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token metamodel=null;
        Token char_literal37=null;
        Etl_EolParserRules.packagedType_return head = null;


        org.eclipse.epsilon.common.parse.AST metamodel_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal37_tree=null;

        try {
            // EolParserRules.g:226:2: ( (metamodel= NAME '!' )? head= packagedType )
            // EolParserRules.g:226:4: (metamodel= NAME '!' )? head= packagedType
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EolParserRules.g:226:4: (metamodel= NAME '!' )?
            int alt17=2;
            int LA17_0 = input.LA(1);

            if ( (LA17_0==NAME) ) {
                int LA17_1 = input.LA(2);

                if ( (LA17_1==106) ) {
                    alt17=1;
                }
            }
            switch (alt17) {
                case 1 :
                    // EolParserRules.g:226:5: metamodel= NAME '!'
                    {
                    metamodel=(Token)match(input,NAME,FOLLOW_NAME_in_pathName773); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    metamodel_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(metamodel);
                    adaptor.addChild(root_0, metamodel_tree);
                    }
                    char_literal37=(Token)match(input,106,FOLLOW_106_in_pathName775); if (state.failed) return retval;

                    }
                    break;

            }

            pushFollow(FOLLOW_packagedType_in_pathName784);
            head=packagedType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(head.getTree(), root_0);
            if ( state.backtracking==0 ) {

              			if (metamodel != null) {
              				(head!=null?((org.eclipse.epsilon.common.parse.AST)head.tree):null).token.setText(metamodel.getText() + "!" + (head!=null?((org.eclipse.epsilon.common.parse.AST)head.tree):null).token.getText());		
              			}
              		
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
    // $ANTLR end pathName

    public static class packagedType_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start packagedType
    // EolParserRules.g:235:1: packagedType : head= NAME ( '::' field= NAME )* ;
    public final Etl_EolParserRules.packagedType_return packagedType() throws RecognitionException {
        Etl_EolParserRules.packagedType_return retval = new Etl_EolParserRules.packagedType_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token head=null;
        Token field=null;
        Token string_literal38=null;

        org.eclipse.epsilon.common.parse.AST head_tree=null;
        org.eclipse.epsilon.common.parse.AST field_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal38_tree=null;

        try {
            // EolParserRules.g:236:2: (head= NAME ( '::' field= NAME )* )
            // EolParserRules.g:236:4: head= NAME ( '::' field= NAME )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            head=(Token)match(input,NAME,FOLLOW_NAME_in_packagedType802); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            head_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(head);
            adaptor.addChild(root_0, head_tree);
            }
            // EolParserRules.g:236:14: ( '::' field= NAME )*
            loop18:
            do {
                int alt18=2;
                int LA18_0 = input.LA(1);

                if ( (LA18_0==107) ) {
                    alt18=1;
                }


                switch (alt18) {
            	case 1 :
            	    // EolParserRules.g:236:15: '::' field= NAME
            	    {
            	    string_literal38=(Token)match(input,107,FOLLOW_107_in_packagedType805); if (state.failed) return retval;
            	    field=(Token)match(input,NAME,FOLLOW_NAME_in_packagedType810); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	       
            	      				head.setText(head.getText() + "::" + field.getText()); 
            	      				((CommonToken) head).setStopIndex(((CommonToken)field).getStopIndex());
            	      			
            	    }

            	    }
            	    break;

            	default :
            	    break loop18;
                }
            } while (true);


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
    // $ANTLR end packagedType

    public static class collectionType_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start collectionType
    // EolParserRules.g:244:1: collectionType : ( CollectionTypeName | MapTypeName ) ( (op= '(' tn= typeName ( ',' tn= typeName )* cp= ')' ) | (op= '<' tn= typeName ( ',' tn= typeName )* cp= '>' ) )? ;
    public final Etl_EolParserRules.collectionType_return collectionType() throws RecognitionException {
        Etl_EolParserRules.collectionType_return retval = new Etl_EolParserRules.collectionType_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Token cp=null;
        Token set39=null;
        Token char_literal40=null;
        Token char_literal41=null;
        Etl_EolParserRules.typeName_return tn = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;
        org.eclipse.epsilon.common.parse.AST cp_tree=null;
        org.eclipse.epsilon.common.parse.AST set39_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal40_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal41_tree=null;

        try {
            // EolParserRules.g:250:2: ( ( CollectionTypeName | MapTypeName ) ( (op= '(' tn= typeName ( ',' tn= typeName )* cp= ')' ) | (op= '<' tn= typeName ( ',' tn= typeName )* cp= '>' ) )? )
            // EolParserRules.g:250:5: ( CollectionTypeName | MapTypeName ) ( (op= '(' tn= typeName ( ',' tn= typeName )* cp= ')' ) | (op= '<' tn= typeName ( ',' tn= typeName )* cp= '>' ) )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            set39=(Token)input.LT(1);
            set39=(Token)input.LT(1);
            if ( (input.LA(1)>=CollectionTypeName && input.LA(1)<=MapTypeName) ) {
                input.consume();
                if ( state.backtracking==0 ) root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(set39), root_0);
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
            }

            // EolParserRules.g:251:3: ( (op= '(' tn= typeName ( ',' tn= typeName )* cp= ')' ) | (op= '<' tn= typeName ( ',' tn= typeName )* cp= '>' ) )?
            int alt21=3;
            alt21 = dfa21.predict(input);
            switch (alt21) {
                case 1 :
                    // EolParserRules.g:251:4: (op= '(' tn= typeName ( ',' tn= typeName )* cp= ')' )
                    {
                    // EolParserRules.g:251:4: (op= '(' tn= typeName ( ',' tn= typeName )* cp= ')' )
                    // EolParserRules.g:251:5: op= '(' tn= typeName ( ',' tn= typeName )* cp= ')'
                    {
                    op=(Token)match(input,101,FOLLOW_101_in_collectionType855); if (state.failed) return retval;
                    pushFollow(FOLLOW_typeName_in_collectionType860);
                    tn=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tn.getTree());
                    if ( state.backtracking==0 ) {
                      setTokenType(tn,TYPE);
                    }
                    // EolParserRules.g:251:50: ( ',' tn= typeName )*
                    loop19:
                    do {
                        int alt19=2;
                        int LA19_0 = input.LA(1);

                        if ( (LA19_0==94) ) {
                            alt19=1;
                        }


                        switch (alt19) {
                    	case 1 :
                    	    // EolParserRules.g:251:51: ',' tn= typeName
                    	    {
                    	    char_literal40=(Token)match(input,94,FOLLOW_94_in_collectionType865); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal40_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(char_literal40);
                    	    adaptor.addChild(root_0, char_literal40_tree);
                    	    }
                    	    pushFollow(FOLLOW_typeName_in_collectionType869);
                    	    tn=typeName();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, tn.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      setTokenType(tn,TYPE);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop19;
                        }
                    } while (true);

                    cp=(Token)match(input,102,FOLLOW_102_in_collectionType877); if (state.failed) return retval;

                    }


                    }
                    break;
                case 2 :
                    // EolParserRules.g:252:4: (op= '<' tn= typeName ( ',' tn= typeName )* cp= '>' )
                    {
                    // EolParserRules.g:252:4: (op= '<' tn= typeName ( ',' tn= typeName )* cp= '>' )
                    // EolParserRules.g:252:5: op= '<' tn= typeName ( ',' tn= typeName )* cp= '>'
                    {
                    op=(Token)match(input,108,FOLLOW_108_in_collectionType889); if (state.failed) return retval;
                    pushFollow(FOLLOW_typeName_in_collectionType894);
                    tn=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, tn.getTree());
                    if ( state.backtracking==0 ) {
                      setTokenType(tn,TYPE);
                    }
                    // EolParserRules.g:252:50: ( ',' tn= typeName )*
                    loop20:
                    do {
                        int alt20=2;
                        int LA20_0 = input.LA(1);

                        if ( (LA20_0==94) ) {
                            alt20=1;
                        }


                        switch (alt20) {
                    	case 1 :
                    	    // EolParserRules.g:252:51: ',' tn= typeName
                    	    {
                    	    char_literal41=(Token)match(input,94,FOLLOW_94_in_collectionType899); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal41_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(char_literal41);
                    	    adaptor.addChild(root_0, char_literal41_tree);
                    	    }
                    	    pushFollow(FOLLOW_typeName_in_collectionType903);
                    	    tn=typeName();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, tn.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      setTokenType(tn,TYPE);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop20;
                        }
                    } while (true);

                    cp=(Token)match(input,109,FOLLOW_109_in_collectionType911); if (state.failed) return retval;

                    }


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(op); 
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cp);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(TYPE);
              	
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
    // $ANTLR end collectionType

    public static class statement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statement
    // EolParserRules.g:256:1: statement : ( statementA | statementB );
    public final Etl_EolParserRules.statement_return statement() throws RecognitionException {
        Etl_EolParserRules.statement_return retval = new Etl_EolParserRules.statement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.statementA_return statementA42 = null;

        Etl_EolParserRules.statementB_return statementB43 = null;



        try {
            // EolParserRules.g:257:2: ( statementA | statementB )
            int alt22=2;
            alt22 = dfa22.predict(input);
            switch (alt22) {
                case 1 :
                    // EolParserRules.g:257:4: statementA
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_statementA_in_statement930);
                    statementA42=statementA();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementA42.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:257:17: statementB
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_statementB_in_statement934);
                    statementB43=statementB();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementB43.getTree());

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
    // $ANTLR end statement

    public static class statementA_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statementA
    // EolParserRules.g:260:1: statementA : ( assignmentStatement | expressionStatement | forStatement | ifStatement | whileStatement | switchStatement | returnStatement | breakStatement );
    public final Etl_EolParserRules.statementA_return statementA() throws RecognitionException {
        Etl_EolParserRules.statementA_return retval = new Etl_EolParserRules.statementA_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.assignmentStatement_return assignmentStatement44 = null;

        Etl_EolParserRules.expressionStatement_return expressionStatement45 = null;

        Etl_EolParserRules.forStatement_return forStatement46 = null;

        Etl_EolParserRules.ifStatement_return ifStatement47 = null;

        Etl_EolParserRules.whileStatement_return whileStatement48 = null;

        Etl_EolParserRules.switchStatement_return switchStatement49 = null;

        Etl_EolParserRules.returnStatement_return returnStatement50 = null;

        Etl_EolParserRules.breakStatement_return breakStatement51 = null;



        try {
            // EolParserRules.g:261:2: ( assignmentStatement | expressionStatement | forStatement | ifStatement | whileStatement | switchStatement | returnStatement | breakStatement )
            int alt23=8;
            alt23 = dfa23.predict(input);
            switch (alt23) {
                case 1 :
                    // EolParserRules.g:261:4: assignmentStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_assignmentStatement_in_statementA945);
                    assignmentStatement44=assignmentStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignmentStatement44.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:261:26: expressionStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_expressionStatement_in_statementA949);
                    expressionStatement45=expressionStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionStatement45.getTree());

                    }
                    break;
                case 3 :
                    // EolParserRules.g:261:48: forStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_forStatement_in_statementA953);
                    forStatement46=forStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, forStatement46.getTree());

                    }
                    break;
                case 4 :
                    // EolParserRules.g:262:5: ifStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_ifStatement_in_statementA959);
                    ifStatement47=ifStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ifStatement47.getTree());

                    }
                    break;
                case 5 :
                    // EolParserRules.g:262:19: whileStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_whileStatement_in_statementA963);
                    whileStatement48=whileStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, whileStatement48.getTree());

                    }
                    break;
                case 6 :
                    // EolParserRules.g:262:36: switchStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_switchStatement_in_statementA967);
                    switchStatement49=switchStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, switchStatement49.getTree());

                    }
                    break;
                case 7 :
                    // EolParserRules.g:262:54: returnStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_returnStatement_in_statementA971);
                    returnStatement50=returnStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, returnStatement50.getTree());

                    }
                    break;
                case 8 :
                    // EolParserRules.g:262:72: breakStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_breakStatement_in_statementA975);
                    breakStatement51=breakStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, breakStatement51.getTree());

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
    // $ANTLR end statementA

    public static class statementB_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statementB
    // EolParserRules.g:265:1: statementB : ( breakAllStatement | returnStatement | transactionStatement | abortStatement | continueStatement | throwStatement | deleteStatement );
    public final Etl_EolParserRules.statementB_return statementB() throws RecognitionException {
        Etl_EolParserRules.statementB_return retval = new Etl_EolParserRules.statementB_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.breakAllStatement_return breakAllStatement52 = null;

        Etl_EolParserRules.returnStatement_return returnStatement53 = null;

        Etl_EolParserRules.transactionStatement_return transactionStatement54 = null;

        Etl_EolParserRules.abortStatement_return abortStatement55 = null;

        Etl_EolParserRules.continueStatement_return continueStatement56 = null;

        Etl_EolParserRules.throwStatement_return throwStatement57 = null;

        Etl_EolParserRules.deleteStatement_return deleteStatement58 = null;



        try {
            // EolParserRules.g:266:2: ( breakAllStatement | returnStatement | transactionStatement | abortStatement | continueStatement | throwStatement | deleteStatement )
            int alt24=7;
            switch ( input.LA(1) ) {
            case 122:
                {
                alt24=1;
                }
                break;
            case 118:
                {
                alt24=2;
                }
                break;
            case 125:
                {
                alt24=3;
                }
                break;
            case 124:
                {
                alt24=4;
                }
                break;
            case 123:
                {
                alt24=5;
                }
                break;
            case 119:
                {
                alt24=6;
                }
                break;
            case 120:
                {
                alt24=7;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 24, 0, input);

                throw nvae;
            }

            switch (alt24) {
                case 1 :
                    // EolParserRules.g:266:4: breakAllStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_breakAllStatement_in_statementB987);
                    breakAllStatement52=breakAllStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, breakAllStatement52.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:266:24: returnStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_returnStatement_in_statementB991);
                    returnStatement53=returnStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, returnStatement53.getTree());

                    }
                    break;
                case 3 :
                    // EolParserRules.g:266:42: transactionStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_transactionStatement_in_statementB995);
                    transactionStatement54=transactionStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, transactionStatement54.getTree());

                    }
                    break;
                case 4 :
                    // EolParserRules.g:267:5: abortStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_abortStatement_in_statementB1001);
                    abortStatement55=abortStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, abortStatement55.getTree());

                    }
                    break;
                case 5 :
                    // EolParserRules.g:267:22: continueStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_continueStatement_in_statementB1005);
                    continueStatement56=continueStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, continueStatement56.getTree());

                    }
                    break;
                case 6 :
                    // EolParserRules.g:267:42: throwStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_throwStatement_in_statementB1009);
                    throwStatement57=throwStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, throwStatement57.getTree());

                    }
                    break;
                case 7 :
                    // EolParserRules.g:268:5: deleteStatement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_deleteStatement_in_statementB1015);
                    deleteStatement58=deleteStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, deleteStatement58.getTree());

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
    // $ANTLR end statementB

    public static class statementOrStatementBlock_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start statementOrStatementBlock
    // EolParserRules.g:271:1: statementOrStatementBlock : ( statement | statementBlock );
    public final Etl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock() throws RecognitionException {
        Etl_EolParserRules.statementOrStatementBlock_return retval = new Etl_EolParserRules.statementOrStatementBlock_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.statement_return statement59 = null;

        Etl_EolParserRules.statementBlock_return statementBlock60 = null;



        try {
            // EolParserRules.g:272:2: ( statement | statementBlock )
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==FLOAT||LA25_0==INT||LA25_0==BOOLEAN||LA25_0==STRING||(LA25_0>=CollectionTypeName && LA25_0<=SpecialTypeName)||LA25_0==NAME||LA25_0==101||LA25_0==110||LA25_0==112||LA25_0==115||(LA25_0>=117 && LA25_0<=125)||LA25_0==145||LA25_0==148||(LA25_0>=155 && LA25_0<=158)) ) {
                alt25=1;
            }
            else if ( (LA25_0==96) ) {
                alt25=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 25, 0, input);

                throw nvae;
            }
            switch (alt25) {
                case 1 :
                    // EolParserRules.g:272:4: statement
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_statement_in_statementOrStatementBlock1026);
                    statement59=statement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statement59.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:272:16: statementBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_statementBlock_in_statementOrStatementBlock1030);
                    statementBlock60=statementBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock60.getTree());

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
    // $ANTLR end statementOrStatementBlock

    public static class expressionOrStatementBlock_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expressionOrStatementBlock
    // EolParserRules.g:274:1: expressionOrStatementBlock : ( ':' logicalExpression | statementBlock );
    public final Etl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock() throws RecognitionException {
        Etl_EolParserRules.expressionOrStatementBlock_return retval = new Etl_EolParserRules.expressionOrStatementBlock_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token char_literal61=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression62 = null;

        Etl_EolParserRules.statementBlock_return statementBlock63 = null;


        org.eclipse.epsilon.common.parse.AST char_literal61_tree=null;

        try {
            // EolParserRules.g:275:2: ( ':' logicalExpression | statementBlock )
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==103) ) {
                alt26=1;
            }
            else if ( (LA26_0==96) ) {
                alt26=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 26, 0, input);

                throw nvae;
            }
            switch (alt26) {
                case 1 :
                    // EolParserRules.g:275:4: ':' logicalExpression
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    char_literal61=(Token)match(input,103,FOLLOW_103_in_expressionOrStatementBlock1039); if (state.failed) return retval;
                    pushFollow(FOLLOW_logicalExpression_in_expressionOrStatementBlock1042);
                    logicalExpression62=logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression62.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:275:29: statementBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_statementBlock_in_expressionOrStatementBlock1046);
                    statementBlock63=statementBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock63.getTree());

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
    // $ANTLR end expressionOrStatementBlock

    public static class ifStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ifStatement
    // EolParserRules.g:278:1: ifStatement : i= 'if' '(' logicalExpression ')' statementOrStatementBlock ( elseStatement )? ;
    public final Etl_EolParserRules.ifStatement_return ifStatement() throws RecognitionException {
        Etl_EolParserRules.ifStatement_return retval = new Etl_EolParserRules.ifStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token i=null;
        Token char_literal64=null;
        Token char_literal66=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression65 = null;

        Etl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock67 = null;

        Etl_EolParserRules.elseStatement_return elseStatement68 = null;


        org.eclipse.epsilon.common.parse.AST i_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal64_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal66_tree=null;

        try {
            // EolParserRules.g:279:2: (i= 'if' '(' logicalExpression ')' statementOrStatementBlock ( elseStatement )? )
            // EolParserRules.g:279:4: i= 'if' '(' logicalExpression ')' statementOrStatementBlock ( elseStatement )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            i=(Token)match(input,110,FOLLOW_110_in_ifStatement1059); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            i_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(i);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(i_tree, root_0);
            }
            char_literal64=(Token)match(input,101,FOLLOW_101_in_ifStatement1062); if (state.failed) return retval;
            pushFollow(FOLLOW_logicalExpression_in_ifStatement1065);
            logicalExpression65=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression65.getTree());
            char_literal66=(Token)match(input,102,FOLLOW_102_in_ifStatement1067); if (state.failed) return retval;
            pushFollow(FOLLOW_statementOrStatementBlock_in_ifStatement1070);
            statementOrStatementBlock67=statementOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementOrStatementBlock67.getTree());
            // EolParserRules.g:279:66: ( elseStatement )?
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==111) ) {
                int LA27_1 = input.LA(2);

                if ( (synpred42_EolParserRules()) ) {
                    alt27=1;
                }
            }
            switch (alt27) {
                case 1 :
                    // EolParserRules.g:0:0: elseStatement
                    {
                    pushFollow(FOLLOW_elseStatement_in_ifStatement1072);
                    elseStatement68=elseStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, elseStatement68.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              i.setType(IF);
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
    // $ANTLR end ifStatement

    public static class elseStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start elseStatement
    // EolParserRules.g:283:1: elseStatement : e= 'else' statementOrStatementBlock ;
    public final Etl_EolParserRules.elseStatement_return elseStatement() throws RecognitionException {
        Etl_EolParserRules.elseStatement_return retval = new Etl_EolParserRules.elseStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token e=null;
        Etl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock69 = null;


        org.eclipse.epsilon.common.parse.AST e_tree=null;

        try {
            // EolParserRules.g:287:2: (e= 'else' statementOrStatementBlock )
            // EolParserRules.g:287:4: e= 'else' statementOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            e=(Token)match(input,111,FOLLOW_111_in_elseStatement1095); if (state.failed) return retval;
            pushFollow(FOLLOW_statementOrStatementBlock_in_elseStatement1098);
            statementOrStatementBlock69=statementOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementOrStatementBlock69.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(e);
              	
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
    // $ANTLR end elseStatement

    public static class switchStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start switchStatement
    // EolParserRules.g:290:1: switchStatement : s= 'switch' '(' logicalExpression ')' '{' ( caseStatement )* ( defaultStatement )? '}' ;
    public final Etl_EolParserRules.switchStatement_return switchStatement() throws RecognitionException {
        Etl_EolParserRules.switchStatement_return retval = new Etl_EolParserRules.switchStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token s=null;
        Token char_literal70=null;
        Token char_literal72=null;
        Token char_literal73=null;
        Token char_literal76=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression71 = null;

        Etl_EolParserRules.caseStatement_return caseStatement74 = null;

        Etl_EolParserRules.defaultStatement_return defaultStatement75 = null;


        org.eclipse.epsilon.common.parse.AST s_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal70_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal72_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal73_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal76_tree=null;

        try {
            // EolParserRules.g:291:2: (s= 'switch' '(' logicalExpression ')' '{' ( caseStatement )* ( defaultStatement )? '}' )
            // EolParserRules.g:291:4: s= 'switch' '(' logicalExpression ')' '{' ( caseStatement )* ( defaultStatement )? '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            s=(Token)match(input,112,FOLLOW_112_in_switchStatement1112); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            s_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(s);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(s_tree, root_0);
            }
            char_literal70=(Token)match(input,101,FOLLOW_101_in_switchStatement1115); if (state.failed) return retval;
            pushFollow(FOLLOW_logicalExpression_in_switchStatement1118);
            logicalExpression71=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression71.getTree());
            char_literal72=(Token)match(input,102,FOLLOW_102_in_switchStatement1120); if (state.failed) return retval;
            char_literal73=(Token)match(input,96,FOLLOW_96_in_switchStatement1123); if (state.failed) return retval;
            // EolParserRules.g:291:49: ( caseStatement )*
            loop28:
            do {
                int alt28=2;
                int LA28_0 = input.LA(1);

                if ( (LA28_0==113) ) {
                    alt28=1;
                }


                switch (alt28) {
            	case 1 :
            	    // EolParserRules.g:0:0: caseStatement
            	    {
            	    pushFollow(FOLLOW_caseStatement_in_switchStatement1126);
            	    caseStatement74=caseStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, caseStatement74.getTree());

            	    }
            	    break;

            	default :
            	    break loop28;
                }
            } while (true);

            // EolParserRules.g:291:64: ( defaultStatement )?
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( (LA29_0==114) ) {
                alt29=1;
            }
            switch (alt29) {
                case 1 :
                    // EolParserRules.g:0:0: defaultStatement
                    {
                    pushFollow(FOLLOW_defaultStatement_in_switchStatement1129);
                    defaultStatement75=defaultStatement();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, defaultStatement75.getTree());

                    }
                    break;

            }

            char_literal76=(Token)match(input,97,FOLLOW_97_in_switchStatement1132); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              s.setType(SWITCH);
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
    // $ANTLR end switchStatement

    public static class caseStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start caseStatement
    // EolParserRules.g:295:1: caseStatement : c= 'case' logicalExpression ':' ( block | statementBlock ) ;
    public final Etl_EolParserRules.caseStatement_return caseStatement() throws RecognitionException {
        Etl_EolParserRules.caseStatement_return retval = new Etl_EolParserRules.caseStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Token char_literal78=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression77 = null;

        Etl_EolParserRules.block_return block79 = null;

        Etl_EolParserRules.statementBlock_return statementBlock80 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal78_tree=null;

        try {
            // EolParserRules.g:296:2: (c= 'case' logicalExpression ':' ( block | statementBlock ) )
            // EolParserRules.g:296:4: c= 'case' logicalExpression ':' ( block | statementBlock )
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,113,FOLLOW_113_in_caseStatement1151); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_logicalExpression_in_caseStatement1154);
            logicalExpression77=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression77.getTree());
            char_literal78=(Token)match(input,103,FOLLOW_103_in_caseStatement1156); if (state.failed) return retval;
            // EolParserRules.g:296:37: ( block | statementBlock )
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( (LA30_0==EOF||LA30_0==FLOAT||LA30_0==INT||LA30_0==BOOLEAN||LA30_0==STRING||(LA30_0>=CollectionTypeName && LA30_0<=SpecialTypeName)||LA30_0==NAME||LA30_0==97||LA30_0==101||LA30_0==110||(LA30_0>=112 && LA30_0<=115)||(LA30_0>=117 && LA30_0<=125)||LA30_0==145||LA30_0==148||(LA30_0>=155 && LA30_0<=158)) ) {
                alt30=1;
            }
            else if ( (LA30_0==96) ) {
                alt30=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 30, 0, input);

                throw nvae;
            }
            switch (alt30) {
                case 1 :
                    // EolParserRules.g:296:38: block
                    {
                    pushFollow(FOLLOW_block_in_caseStatement1160);
                    block79=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block79.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:296:46: statementBlock
                    {
                    pushFollow(FOLLOW_statementBlock_in_caseStatement1164);
                    statementBlock80=statementBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock80.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              c.setType(CASE);
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
    // $ANTLR end caseStatement

    public static class defaultStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start defaultStatement
    // EolParserRules.g:300:1: defaultStatement : d= 'default' ':' ( block | statementBlock ) ;
    public final Etl_EolParserRules.defaultStatement_return defaultStatement() throws RecognitionException {
        Etl_EolParserRules.defaultStatement_return retval = new Etl_EolParserRules.defaultStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token d=null;
        Token char_literal81=null;
        Etl_EolParserRules.block_return block82 = null;

        Etl_EolParserRules.statementBlock_return statementBlock83 = null;


        org.eclipse.epsilon.common.parse.AST d_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal81_tree=null;

        try {
            // EolParserRules.g:301:2: (d= 'default' ':' ( block | statementBlock ) )
            // EolParserRules.g:301:4: d= 'default' ':' ( block | statementBlock )
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            d=(Token)match(input,114,FOLLOW_114_in_defaultStatement1183); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            d_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(d);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(d_tree, root_0);
            }
            char_literal81=(Token)match(input,103,FOLLOW_103_in_defaultStatement1186); if (state.failed) return retval;
            // EolParserRules.g:301:22: ( block | statementBlock )
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( (LA31_0==EOF||LA31_0==FLOAT||LA31_0==INT||LA31_0==BOOLEAN||LA31_0==STRING||(LA31_0>=CollectionTypeName && LA31_0<=SpecialTypeName)||LA31_0==NAME||LA31_0==97||LA31_0==101||LA31_0==110||LA31_0==112||LA31_0==115||(LA31_0>=117 && LA31_0<=125)||LA31_0==145||LA31_0==148||(LA31_0>=155 && LA31_0<=158)) ) {
                alt31=1;
            }
            else if ( (LA31_0==96) ) {
                alt31=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 31, 0, input);

                throw nvae;
            }
            switch (alt31) {
                case 1 :
                    // EolParserRules.g:301:23: block
                    {
                    pushFollow(FOLLOW_block_in_defaultStatement1190);
                    block82=block();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, block82.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:301:31: statementBlock
                    {
                    pushFollow(FOLLOW_statementBlock_in_defaultStatement1194);
                    statementBlock83=statementBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, statementBlock83.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              d.setType(DEFAULT);
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
    // $ANTLR end defaultStatement

    public static class forStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start forStatement
    // EolParserRules.g:305:1: forStatement : f= 'for' '(' formalParameter 'in' logicalExpression ')' statementOrStatementBlock ;
    public final Etl_EolParserRules.forStatement_return forStatement() throws RecognitionException {
        Etl_EolParserRules.forStatement_return retval = new Etl_EolParserRules.forStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token f=null;
        Token char_literal84=null;
        Token string_literal86=null;
        Token char_literal88=null;
        Etl_EolParserRules.formalParameter_return formalParameter85 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression87 = null;

        Etl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock89 = null;


        org.eclipse.epsilon.common.parse.AST f_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal84_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal86_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal88_tree=null;

        try {
            // EolParserRules.g:306:2: (f= 'for' '(' formalParameter 'in' logicalExpression ')' statementOrStatementBlock )
            // EolParserRules.g:306:4: f= 'for' '(' formalParameter 'in' logicalExpression ')' statementOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            f=(Token)match(input,115,FOLLOW_115_in_forStatement1212); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            f_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(f);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(f_tree, root_0);
            }
            char_literal84=(Token)match(input,101,FOLLOW_101_in_forStatement1215); if (state.failed) return retval;
            pushFollow(FOLLOW_formalParameter_in_forStatement1218);
            formalParameter85=formalParameter();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameter85.getTree());
            string_literal86=(Token)match(input,116,FOLLOW_116_in_forStatement1220); if (state.failed) return retval;
            pushFollow(FOLLOW_logicalExpression_in_forStatement1223);
            logicalExpression87=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression87.getTree());
            char_literal88=(Token)match(input,102,FOLLOW_102_in_forStatement1225); if (state.failed) return retval;
            pushFollow(FOLLOW_statementOrStatementBlock_in_forStatement1228);
            statementOrStatementBlock89=statementOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementOrStatementBlock89.getTree());
            if ( state.backtracking==0 ) {
              f.setType(FOR);
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
    // $ANTLR end forStatement

    public static class whileStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start whileStatement
    // EolParserRules.g:310:1: whileStatement : w= 'while' '(' logicalExpression ')' statementOrStatementBlock ;
    public final Etl_EolParserRules.whileStatement_return whileStatement() throws RecognitionException {
        Etl_EolParserRules.whileStatement_return retval = new Etl_EolParserRules.whileStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token w=null;
        Token char_literal90=null;
        Token char_literal92=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression91 = null;

        Etl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock93 = null;


        org.eclipse.epsilon.common.parse.AST w_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal90_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal92_tree=null;

        try {
            // EolParserRules.g:311:2: (w= 'while' '(' logicalExpression ')' statementOrStatementBlock )
            // EolParserRules.g:311:4: w= 'while' '(' logicalExpression ')' statementOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            w=(Token)match(input,117,FOLLOW_117_in_whileStatement1244); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            w_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(w);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(w_tree, root_0);
            }
            char_literal90=(Token)match(input,101,FOLLOW_101_in_whileStatement1247); if (state.failed) return retval;
            pushFollow(FOLLOW_logicalExpression_in_whileStatement1250);
            logicalExpression91=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression91.getTree());
            char_literal92=(Token)match(input,102,FOLLOW_102_in_whileStatement1252); if (state.failed) return retval;
            pushFollow(FOLLOW_statementOrStatementBlock_in_whileStatement1255);
            statementOrStatementBlock93=statementOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementOrStatementBlock93.getTree());
            if ( state.backtracking==0 ) {
              w.setType(WHILE);
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
    // $ANTLR end whileStatement

    public static class returnStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start returnStatement
    // EolParserRules.g:315:1: returnStatement : r= 'return' ( logicalExpression )? sem= ';' ;
    public final Etl_EolParserRules.returnStatement_return returnStatement() throws RecognitionException {
        Etl_EolParserRules.returnStatement_return retval = new Etl_EolParserRules.returnStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token r=null;
        Token sem=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression94 = null;


        org.eclipse.epsilon.common.parse.AST r_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:319:2: (r= 'return' ( logicalExpression )? sem= ';' )
            // EolParserRules.g:319:4: r= 'return' ( logicalExpression )? sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            r=(Token)match(input,118,FOLLOW_118_in_returnStatement1277); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            r_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(r);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(r_tree, root_0);
            }
            // EolParserRules.g:319:16: ( logicalExpression )?
            int alt32=2;
            int LA32_0 = input.LA(1);

            if ( (LA32_0==FLOAT||LA32_0==INT||LA32_0==BOOLEAN||LA32_0==STRING||(LA32_0>=CollectionTypeName && LA32_0<=SpecialTypeName)||LA32_0==NAME||LA32_0==101||LA32_0==145||LA32_0==148||(LA32_0>=155 && LA32_0<=158)) ) {
                alt32=1;
            }
            switch (alt32) {
                case 1 :
                    // EolParserRules.g:0:0: logicalExpression
                    {
                    pushFollow(FOLLOW_logicalExpression_in_returnStatement1280);
                    logicalExpression94=logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression94.getTree());

                    }
                    break;

            }

            sem=(Token)match(input,92,FOLLOW_92_in_returnStatement1285); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              r.setType(RETURN);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end returnStatement

    public static class throwStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start throwStatement
    // EolParserRules.g:323:1: throwStatement : t= 'throw' ( logicalExpression )? sem= ';' ;
    public final Etl_EolParserRules.throwStatement_return throwStatement() throws RecognitionException {
        Etl_EolParserRules.throwStatement_return retval = new Etl_EolParserRules.throwStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token t=null;
        Token sem=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression95 = null;


        org.eclipse.epsilon.common.parse.AST t_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:327:2: (t= 'throw' ( logicalExpression )? sem= ';' )
            // EolParserRules.g:327:4: t= 'throw' ( logicalExpression )? sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            t=(Token)match(input,119,FOLLOW_119_in_throwStatement1308); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            t_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(t);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(t_tree, root_0);
            }
            // EolParserRules.g:327:15: ( logicalExpression )?
            int alt33=2;
            int LA33_0 = input.LA(1);

            if ( (LA33_0==FLOAT||LA33_0==INT||LA33_0==BOOLEAN||LA33_0==STRING||(LA33_0>=CollectionTypeName && LA33_0<=SpecialTypeName)||LA33_0==NAME||LA33_0==101||LA33_0==145||LA33_0==148||(LA33_0>=155 && LA33_0<=158)) ) {
                alt33=1;
            }
            switch (alt33) {
                case 1 :
                    // EolParserRules.g:0:0: logicalExpression
                    {
                    pushFollow(FOLLOW_logicalExpression_in_throwStatement1311);
                    logicalExpression95=logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression95.getTree());

                    }
                    break;

            }

            sem=(Token)match(input,92,FOLLOW_92_in_throwStatement1316); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              t.setType(THROW);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end throwStatement

    public static class deleteStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start deleteStatement
    // EolParserRules.g:331:1: deleteStatement : d= 'delete' ( logicalExpression )? sem= ';' ;
    public final Etl_EolParserRules.deleteStatement_return deleteStatement() throws RecognitionException {
        Etl_EolParserRules.deleteStatement_return retval = new Etl_EolParserRules.deleteStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token d=null;
        Token sem=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression96 = null;


        org.eclipse.epsilon.common.parse.AST d_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:335:2: (d= 'delete' ( logicalExpression )? sem= ';' )
            // EolParserRules.g:335:4: d= 'delete' ( logicalExpression )? sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            d=(Token)match(input,120,FOLLOW_120_in_deleteStatement1339); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            d_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(d);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(d_tree, root_0);
            }
            // EolParserRules.g:335:16: ( logicalExpression )?
            int alt34=2;
            int LA34_0 = input.LA(1);

            if ( (LA34_0==FLOAT||LA34_0==INT||LA34_0==BOOLEAN||LA34_0==STRING||(LA34_0>=CollectionTypeName && LA34_0<=SpecialTypeName)||LA34_0==NAME||LA34_0==101||LA34_0==145||LA34_0==148||(LA34_0>=155 && LA34_0<=158)) ) {
                alt34=1;
            }
            switch (alt34) {
                case 1 :
                    // EolParserRules.g:0:0: logicalExpression
                    {
                    pushFollow(FOLLOW_logicalExpression_in_deleteStatement1342);
                    logicalExpression96=logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression96.getTree());

                    }
                    break;

            }

            sem=(Token)match(input,92,FOLLOW_92_in_deleteStatement1347); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              d.setType(DELETE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end deleteStatement

    public static class breakStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start breakStatement
    // EolParserRules.g:339:1: breakStatement : b= 'break' sem= ';' ;
    public final Etl_EolParserRules.breakStatement_return breakStatement() throws RecognitionException {
        Etl_EolParserRules.breakStatement_return retval = new Etl_EolParserRules.breakStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token b=null;
        Token sem=null;

        org.eclipse.epsilon.common.parse.AST b_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:343:2: (b= 'break' sem= ';' )
            // EolParserRules.g:343:4: b= 'break' sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            b=(Token)match(input,121,FOLLOW_121_in_breakStatement1373); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            b_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(b);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(b_tree, root_0);
            }
            sem=(Token)match(input,92,FOLLOW_92_in_breakStatement1378); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              b.setType(BREAK);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end breakStatement

    public static class breakAllStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start breakAllStatement
    // EolParserRules.g:347:1: breakAllStatement : b= 'breakAll' sem= ';' ;
    public final Etl_EolParserRules.breakAllStatement_return breakAllStatement() throws RecognitionException {
        Etl_EolParserRules.breakAllStatement_return retval = new Etl_EolParserRules.breakAllStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token b=null;
        Token sem=null;

        org.eclipse.epsilon.common.parse.AST b_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:351:2: (b= 'breakAll' sem= ';' )
            // EolParserRules.g:351:4: b= 'breakAll' sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            b=(Token)match(input,122,FOLLOW_122_in_breakAllStatement1401); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            b_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(b);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(b_tree, root_0);
            }
            sem=(Token)match(input,92,FOLLOW_92_in_breakAllStatement1406); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              b.setType(BREAKALL);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end breakAllStatement

    public static class continueStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start continueStatement
    // EolParserRules.g:355:1: continueStatement : c= 'continue' sem= ';' ;
    public final Etl_EolParserRules.continueStatement_return continueStatement() throws RecognitionException {
        Etl_EolParserRules.continueStatement_return retval = new Etl_EolParserRules.continueStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Token sem=null;

        org.eclipse.epsilon.common.parse.AST c_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:359:2: (c= 'continue' sem= ';' )
            // EolParserRules.g:359:4: c= 'continue' sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,123,FOLLOW_123_in_continueStatement1429); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            sem=(Token)match(input,92,FOLLOW_92_in_continueStatement1434); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              c.setType(CONTINUE);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end continueStatement

    public static class abortStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start abortStatement
    // EolParserRules.g:363:1: abortStatement : a= 'abort' sem= ';' ;
    public final Etl_EolParserRules.abortStatement_return abortStatement() throws RecognitionException {
        Etl_EolParserRules.abortStatement_return retval = new Etl_EolParserRules.abortStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token a=null;
        Token sem=null;

        org.eclipse.epsilon.common.parse.AST a_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:367:2: (a= 'abort' sem= ';' )
            // EolParserRules.g:367:4: a= 'abort' sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            a=(Token)match(input,124,FOLLOW_124_in_abortStatement1457); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            a_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(a);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(a_tree, root_0);
            }
            sem=(Token)match(input,92,FOLLOW_92_in_abortStatement1462); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              a.setType(ABORT);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end abortStatement

    public static class transactionStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start transactionStatement
    // EolParserRules.g:371:1: transactionStatement : t= 'transaction' ( NAME ( ',' NAME )* )? statementOrStatementBlock ;
    public final Etl_EolParserRules.transactionStatement_return transactionStatement() throws RecognitionException {
        Etl_EolParserRules.transactionStatement_return retval = new Etl_EolParserRules.transactionStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token t=null;
        Token NAME97=null;
        Token char_literal98=null;
        Token NAME99=null;
        Etl_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock100 = null;


        org.eclipse.epsilon.common.parse.AST t_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME97_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal98_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME99_tree=null;

        try {
            // EolParserRules.g:372:2: (t= 'transaction' ( NAME ( ',' NAME )* )? statementOrStatementBlock )
            // EolParserRules.g:372:4: t= 'transaction' ( NAME ( ',' NAME )* )? statementOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            t=(Token)match(input,125,FOLLOW_125_in_transactionStatement1479); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            t_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(t);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(t_tree, root_0);
            }
            // EolParserRules.g:372:21: ( NAME ( ',' NAME )* )?
            int alt36=2;
            int LA36_0 = input.LA(1);

            if ( (LA36_0==NAME) ) {
                int LA36_1 = input.LA(2);

                if ( (synpred51_EolParserRules()) ) {
                    alt36=1;
                }
            }
            switch (alt36) {
                case 1 :
                    // EolParserRules.g:372:22: NAME ( ',' NAME )*
                    {
                    NAME97=(Token)match(input,NAME,FOLLOW_NAME_in_transactionStatement1483); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NAME97_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME97);
                    adaptor.addChild(root_0, NAME97_tree);
                    }
                    // EolParserRules.g:372:27: ( ',' NAME )*
                    loop35:
                    do {
                        int alt35=2;
                        int LA35_0 = input.LA(1);

                        if ( (LA35_0==94) ) {
                            alt35=1;
                        }


                        switch (alt35) {
                    	case 1 :
                    	    // EolParserRules.g:372:28: ',' NAME
                    	    {
                    	    char_literal98=(Token)match(input,94,FOLLOW_94_in_transactionStatement1486); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    char_literal98_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(char_literal98);
                    	    adaptor.addChild(root_0, char_literal98_tree);
                    	    }
                    	    NAME99=(Token)match(input,NAME,FOLLOW_NAME_in_transactionStatement1488); if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) {
                    	    NAME99_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME99);
                    	    adaptor.addChild(root_0, NAME99_tree);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop35;
                        }
                    } while (true);


                    }
                    break;

            }

            pushFollow(FOLLOW_statementOrStatementBlock_in_transactionStatement1494);
            statementOrStatementBlock100=statementOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, statementOrStatementBlock100.getTree());
            if ( state.backtracking==0 ) {
              t.setType(TRANSACTION);
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
    // $ANTLR end transactionStatement

    public static class assignmentStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start assignmentStatement
    // EolParserRules.g:376:1: assignmentStatement : logicalExpression ( (normal= ':=' | normal= '+=' | normal= '-=' | normal= '*=' | normal= '/=' | normal= '?=' ) | special= '::=' ) logicalExpression sem= ';' ;
    public final Etl_EolParserRules.assignmentStatement_return assignmentStatement() throws RecognitionException {
        Etl_EolParserRules.assignmentStatement_return retval = new Etl_EolParserRules.assignmentStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token normal=null;
        Token special=null;
        Token sem=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression101 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression102 = null;


        org.eclipse.epsilon.common.parse.AST normal_tree=null;
        org.eclipse.epsilon.common.parse.AST special_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:380:2: ( logicalExpression ( (normal= ':=' | normal= '+=' | normal= '-=' | normal= '*=' | normal= '/=' | normal= '?=' ) | special= '::=' ) logicalExpression sem= ';' )
            // EolParserRules.g:380:4: logicalExpression ( (normal= ':=' | normal= '+=' | normal= '-=' | normal= '*=' | normal= '/=' | normal= '?=' ) | special= '::=' ) logicalExpression sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_logicalExpression_in_assignmentStatement1514);
            logicalExpression101=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression101.getTree());
            // EolParserRules.g:380:22: ( (normal= ':=' | normal= '+=' | normal= '-=' | normal= '*=' | normal= '/=' | normal= '?=' ) | special= '::=' )
            int alt38=2;
            int LA38_0 = input.LA(1);

            if ( ((LA38_0>=126 && LA38_0<=131)) ) {
                alt38=1;
            }
            else if ( (LA38_0==132) ) {
                alt38=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 38, 0, input);

                throw nvae;
            }
            switch (alt38) {
                case 1 :
                    // EolParserRules.g:380:23: (normal= ':=' | normal= '+=' | normal= '-=' | normal= '*=' | normal= '/=' | normal= '?=' )
                    {
                    // EolParserRules.g:380:23: (normal= ':=' | normal= '+=' | normal= '-=' | normal= '*=' | normal= '/=' | normal= '?=' )
                    int alt37=6;
                    switch ( input.LA(1) ) {
                    case 126:
                        {
                        alt37=1;
                        }
                        break;
                    case 127:
                        {
                        alt37=2;
                        }
                        break;
                    case 128:
                        {
                        alt37=3;
                        }
                        break;
                    case 129:
                        {
                        alt37=4;
                        }
                        break;
                    case 130:
                        {
                        alt37=5;
                        }
                        break;
                    case 131:
                        {
                        alt37=6;
                        }
                        break;
                    default:
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 37, 0, input);

                        throw nvae;
                    }

                    switch (alt37) {
                        case 1 :
                            // EolParserRules.g:380:24: normal= ':='
                            {
                            normal=(Token)match(input,126,FOLLOW_126_in_assignmentStatement1520); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            normal_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(normal);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(normal_tree, root_0);
                            }

                            }
                            break;
                        case 2 :
                            // EolParserRules.g:380:37: normal= '+='
                            {
                            normal=(Token)match(input,127,FOLLOW_127_in_assignmentStatement1525); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            normal_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(normal);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(normal_tree, root_0);
                            }

                            }
                            break;
                        case 3 :
                            // EolParserRules.g:380:50: normal= '-='
                            {
                            normal=(Token)match(input,128,FOLLOW_128_in_assignmentStatement1530); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            normal_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(normal);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(normal_tree, root_0);
                            }

                            }
                            break;
                        case 4 :
                            // EolParserRules.g:380:63: normal= '*='
                            {
                            normal=(Token)match(input,129,FOLLOW_129_in_assignmentStatement1535); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            normal_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(normal);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(normal_tree, root_0);
                            }

                            }
                            break;
                        case 5 :
                            // EolParserRules.g:380:76: normal= '/='
                            {
                            normal=(Token)match(input,130,FOLLOW_130_in_assignmentStatement1540); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            normal_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(normal);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(normal_tree, root_0);
                            }

                            }
                            break;
                        case 6 :
                            // EolParserRules.g:380:89: normal= '?='
                            {
                            normal=(Token)match(input,131,FOLLOW_131_in_assignmentStatement1545); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            normal_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(normal);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(normal_tree, root_0);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                      normal.setType(ASSIGNMENT);
                    }

                    }
                    break;
                case 2 :
                    // EolParserRules.g:381:35: special= '::='
                    {
                    special=(Token)match(input,132,FOLLOW_132_in_assignmentStatement1557); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    special_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(special);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(special_tree, root_0);
                    }
                    if ( state.backtracking==0 ) {
                      special.setType(SPECIAL_ASSIGNMENT);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_logicalExpression_in_assignmentStatement1565);
            logicalExpression102=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression102.getTree());
            sem=(Token)match(input,92,FOLLOW_92_in_assignmentStatement1569); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end assignmentStatement

    public static class expressionStatement_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expressionStatement
    // EolParserRules.g:385:1: expressionStatement : ( ( postfixExpression op= '=' logicalExpression ) | logicalExpression ) sem= ';' ;
    public final Etl_EolParserRules.expressionStatement_return expressionStatement() throws RecognitionException {
        Etl_EolParserRules.expressionStatement_return retval = new Etl_EolParserRules.expressionStatement_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Token sem=null;
        Etl_EolParserRules.postfixExpression_return postfixExpression103 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression104 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression105 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;
        org.eclipse.epsilon.common.parse.AST sem_tree=null;

        try {
            // EolParserRules.g:389:2: ( ( ( postfixExpression op= '=' logicalExpression ) | logicalExpression ) sem= ';' )
            // EolParserRules.g:389:4: ( ( postfixExpression op= '=' logicalExpression ) | logicalExpression ) sem= ';'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EolParserRules.g:389:4: ( ( postfixExpression op= '=' logicalExpression ) | logicalExpression )
            int alt39=2;
            alt39 = dfa39.predict(input);
            switch (alt39) {
                case 1 :
                    // EolParserRules.g:389:5: ( postfixExpression op= '=' logicalExpression )
                    {
                    // EolParserRules.g:389:5: ( postfixExpression op= '=' logicalExpression )
                    // EolParserRules.g:389:6: postfixExpression op= '=' logicalExpression
                    {
                    pushFollow(FOLLOW_postfixExpression_in_expressionStatement1589);
                    postfixExpression103=postfixExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, postfixExpression103.getTree());
                    op=(Token)match(input,98,FOLLOW_98_in_expressionStatement1593); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
                    }
                    pushFollow(FOLLOW_logicalExpression_in_expressionStatement1596);
                    logicalExpression104=logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression104.getTree());
                    if ( state.backtracking==0 ) {
                      op.setType(OPERATOR);
                    }

                    }


                    }
                    break;
                case 2 :
                    // EolParserRules.g:389:78: logicalExpression
                    {
                    pushFollow(FOLLOW_logicalExpression_in_expressionStatement1603);
                    logicalExpression105=logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression105.getTree());

                    }
                    break;

            }

            sem=(Token)match(input,92,FOLLOW_92_in_expressionStatement1608); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(sem);
              	
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
    // $ANTLR end expressionStatement

    public static class logicalExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start logicalExpression
    // EolParserRules.g:392:1: logicalExpression : relationalExpression ( ( (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' ) | (op= '?' relationalExpression ( 'else' | ':' ) ) ) relationalExpression )* ;
    public final Etl_EolParserRules.logicalExpression_return logicalExpression() throws RecognitionException {
        Etl_EolParserRules.logicalExpression_return retval = new Etl_EolParserRules.logicalExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Token set108=null;
        Etl_EolParserRules.relationalExpression_return relationalExpression106 = null;

        Etl_EolParserRules.relationalExpression_return relationalExpression107 = null;

        Etl_EolParserRules.relationalExpression_return relationalExpression109 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;
        org.eclipse.epsilon.common.parse.AST set108_tree=null;

        try {
            // EolParserRules.g:393:2: ( relationalExpression ( ( (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' ) | (op= '?' relationalExpression ( 'else' | ':' ) ) ) relationalExpression )* )
            // EolParserRules.g:393:4: relationalExpression ( ( (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' ) | (op= '?' relationalExpression ( 'else' | ':' ) ) ) relationalExpression )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_relationalExpression_in_logicalExpression1620);
            relationalExpression106=relationalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression106.getTree());
            // EolParserRules.g:393:25: ( ( (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' ) | (op= '?' relationalExpression ( 'else' | ':' ) ) ) relationalExpression )*
            loop42:
            do {
                int alt42=2;
                int LA42_0 = input.LA(1);

                if ( ((LA42_0>=133 && LA42_0<=137)) ) {
                    alt42=1;
                }


                switch (alt42) {
            	case 1 :
            	    // EolParserRules.g:394:4: ( (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' ) | (op= '?' relationalExpression ( 'else' | ':' ) ) ) relationalExpression
            	    {
            	    // EolParserRules.g:394:4: ( (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' ) | (op= '?' relationalExpression ( 'else' | ':' ) ) )
            	    int alt41=2;
            	    int LA41_0 = input.LA(1);

            	    if ( ((LA41_0>=133 && LA41_0<=136)) ) {
            	        alt41=1;
            	    }
            	    else if ( (LA41_0==137) ) {
            	        alt41=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 41, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt41) {
            	        case 1 :
            	            // EolParserRules.g:394:5: (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' )
            	            {
            	            // EolParserRules.g:394:5: (op= 'or' | op= 'and' | op= 'xor' | op= 'implies' )
            	            int alt40=4;
            	            switch ( input.LA(1) ) {
            	            case 133:
            	                {
            	                alt40=1;
            	                }
            	                break;
            	            case 134:
            	                {
            	                alt40=2;
            	                }
            	                break;
            	            case 135:
            	                {
            	                alt40=3;
            	                }
            	                break;
            	            case 136:
            	                {
            	                alt40=4;
            	                }
            	                break;
            	            default:
            	                if (state.backtracking>0) {state.failed=true; return retval;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 40, 0, input);

            	                throw nvae;
            	            }

            	            switch (alt40) {
            	                case 1 :
            	                    // EolParserRules.g:394:6: op= 'or'
            	                    {
            	                    op=(Token)match(input,133,FOLLOW_133_in_logicalExpression1631); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 2 :
            	                    // EolParserRules.g:394:15: op= 'and'
            	                    {
            	                    op=(Token)match(input,134,FOLLOW_134_in_logicalExpression1636); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 3 :
            	                    // EolParserRules.g:394:25: op= 'xor'
            	                    {
            	                    op=(Token)match(input,135,FOLLOW_135_in_logicalExpression1641); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 4 :
            	                    // EolParserRules.g:394:35: op= 'implies'
            	                    {
            	                    op=(Token)match(input,136,FOLLOW_136_in_logicalExpression1646); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;

            	            }

            	            if ( state.backtracking==0 ) {
            	              op.setType(OPERATOR);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // EolParserRules.g:395:4: (op= '?' relationalExpression ( 'else' | ':' ) )
            	            {
            	            // EolParserRules.g:395:4: (op= '?' relationalExpression ( 'else' | ':' ) )
            	            // EolParserRules.g:395:5: op= '?' relationalExpression ( 'else' | ':' )
            	            {
            	            op=(Token)match(input,137,FOLLOW_137_in_logicalExpression1660); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	            }
            	            pushFollow(FOLLOW_relationalExpression_in_logicalExpression1663);
            	            relationalExpression107=relationalExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression107.getTree());
            	            set108=(Token)input.LT(1);
            	            if ( input.LA(1)==103||input.LA(1)==111 ) {
            	                input.consume();
            	                if ( state.backtracking==0 ) adaptor.addChild(root_0, (org.eclipse.epsilon.common.parse.AST)adaptor.create(set108));
            	                state.errorRecovery=false;state.failed=false;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return retval;}
            	                MismatchedSetException mse = new MismatchedSetException(null,input);
            	                throw mse;
            	            }

            	            if ( state.backtracking==0 ) {
            	              op.setType(TERNARY);
            	            }

            	            }


            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_relationalExpression_in_logicalExpression1681);
            	    relationalExpression109=relationalExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression109.getTree());

            	    }
            	    break;

            	default :
            	    break loop42;
                }
            } while (true);


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
    // $ANTLR end logicalExpression

    public static class relationalExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start relationalExpression
    // EolParserRules.g:400:1: relationalExpression : additiveExpression ( ( (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression | (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression ) )* ;
    public final Etl_EolParserRules.relationalExpression_return relationalExpression() throws RecognitionException {
        Etl_EolParserRules.relationalExpression_return retval = new Etl_EolParserRules.relationalExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Etl_EolParserRules.additiveExpression_return additiveExpression110 = null;

        Etl_EolParserRules.relationalExpression_return relationalExpression111 = null;

        Etl_EolParserRules.additiveExpression_return additiveExpression112 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;

        try {
            // EolParserRules.g:401:2: ( additiveExpression ( ( (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression | (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression ) )* )
            // EolParserRules.g:401:4: additiveExpression ( ( (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression | (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression ) )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_additiveExpression_in_relationalExpression1697);
            additiveExpression110=additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression110.getTree());
            // EolParserRules.g:401:23: ( ( (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression | (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression ) )*
            loop46:
            do {
                int alt46=2;
                alt46 = dfa46.predict(input);
                switch (alt46) {
            	case 1 :
            	    // EolParserRules.g:401:24: ( (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression | (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression )
            	    {
            	    // EolParserRules.g:401:24: ( (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression | (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression )
            	    int alt45=2;
            	    int LA45_0 = input.LA(1);

            	    if ( (LA45_0==98||(LA45_0>=138 && LA45_0<=140)) ) {
            	        alt45=1;
            	    }
            	    else if ( ((LA45_0>=108 && LA45_0<=109)||(LA45_0>=141 && LA45_0<=143)) ) {
            	        alt45=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 45, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt45) {
            	        case 1 :
            	            // EolParserRules.g:401:25: (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression
            	            {
            	            // EolParserRules.g:401:25: (op= '==' | op= '=' | op= '!=' | op= '?:' )
            	            int alt43=4;
            	            switch ( input.LA(1) ) {
            	            case 138:
            	                {
            	                alt43=1;
            	                }
            	                break;
            	            case 98:
            	                {
            	                alt43=2;
            	                }
            	                break;
            	            case 139:
            	                {
            	                alt43=3;
            	                }
            	                break;
            	            case 140:
            	                {
            	                alt43=4;
            	                }
            	                break;
            	            default:
            	                if (state.backtracking>0) {state.failed=true; return retval;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 43, 0, input);

            	                throw nvae;
            	            }

            	            switch (alt43) {
            	                case 1 :
            	                    // EolParserRules.g:401:26: op= '=='
            	                    {
            	                    op=(Token)match(input,138,FOLLOW_138_in_relationalExpression1704); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 2 :
            	                    // EolParserRules.g:401:35: op= '='
            	                    {
            	                    op=(Token)match(input,98,FOLLOW_98_in_relationalExpression1709); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 3 :
            	                    // EolParserRules.g:401:43: op= '!='
            	                    {
            	                    op=(Token)match(input,139,FOLLOW_139_in_relationalExpression1714); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 4 :
            	                    // EolParserRules.g:401:52: op= '?:'
            	                    {
            	                    op=(Token)match(input,140,FOLLOW_140_in_relationalExpression1719); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;

            	            }

            	            pushFollow(FOLLOW_relationalExpression_in_relationalExpression1723);
            	            relationalExpression111=relationalExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, relationalExpression111.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // EolParserRules.g:402:24: (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression
            	            {
            	            // EolParserRules.g:402:24: (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' )
            	            int alt44=5;
            	            switch ( input.LA(1) ) {
            	            case 109:
            	                {
            	                alt44=1;
            	                }
            	                break;
            	            case 108:
            	                {
            	                alt44=2;
            	                }
            	                break;
            	            case 141:
            	                {
            	                alt44=3;
            	                }
            	                break;
            	            case 142:
            	                {
            	                alt44=4;
            	                }
            	                break;
            	            case 143:
            	                {
            	                alt44=5;
            	                }
            	                break;
            	            default:
            	                if (state.backtracking>0) {state.failed=true; return retval;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 44, 0, input);

            	                throw nvae;
            	            }

            	            switch (alt44) {
            	                case 1 :
            	                    // EolParserRules.g:402:25: op= '>'
            	                    {
            	                    op=(Token)match(input,109,FOLLOW_109_in_relationalExpression1753); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 2 :
            	                    // EolParserRules.g:402:33: op= '<'
            	                    {
            	                    op=(Token)match(input,108,FOLLOW_108_in_relationalExpression1758); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 3 :
            	                    // EolParserRules.g:402:41: op= '>='
            	                    {
            	                    op=(Token)match(input,141,FOLLOW_141_in_relationalExpression1763); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 4 :
            	                    // EolParserRules.g:402:50: op= '<='
            	                    {
            	                    op=(Token)match(input,142,FOLLOW_142_in_relationalExpression1768); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;
            	                case 5 :
            	                    // EolParserRules.g:402:59: op= '<>'
            	                    {
            	                    op=(Token)match(input,143,FOLLOW_143_in_relationalExpression1773); if (state.failed) return retval;
            	                    if ( state.backtracking==0 ) {
            	                    op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	                    }

            	                    }
            	                    break;

            	            }

            	            pushFollow(FOLLOW_additiveExpression_in_relationalExpression1777);
            	            additiveExpression112=additiveExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression112.getTree());

            	            }
            	            break;

            	    }

            	    if ( state.backtracking==0 ) {
            	      op.setType(OPERATOR);
            	    }

            	    }
            	    break;

            	default :
            	    break loop46;
                }
            } while (true);


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
    // $ANTLR end relationalExpression

    public static class additiveExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start additiveExpression
    // EolParserRules.g:406:1: additiveExpression : multiplicativeExpression ( (op= '+' | op= '-' ) multiplicativeExpression )* ;
    public final Etl_EolParserRules.additiveExpression_return additiveExpression() throws RecognitionException {
        Etl_EolParserRules.additiveExpression_return retval = new Etl_EolParserRules.additiveExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Etl_EolParserRules.multiplicativeExpression_return multiplicativeExpression113 = null;

        Etl_EolParserRules.multiplicativeExpression_return multiplicativeExpression114 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;

        try {
            // EolParserRules.g:407:2: ( multiplicativeExpression ( (op= '+' | op= '-' ) multiplicativeExpression )* )
            // EolParserRules.g:407:4: multiplicativeExpression ( (op= '+' | op= '-' ) multiplicativeExpression )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1795);
            multiplicativeExpression113=multiplicativeExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression113.getTree());
            // EolParserRules.g:407:29: ( (op= '+' | op= '-' ) multiplicativeExpression )*
            loop48:
            do {
                int alt48=2;
                int LA48_0 = input.LA(1);

                if ( ((LA48_0>=144 && LA48_0<=145)) ) {
                    alt48=1;
                }


                switch (alt48) {
            	case 1 :
            	    // EolParserRules.g:407:30: (op= '+' | op= '-' ) multiplicativeExpression
            	    {
            	    // EolParserRules.g:407:30: (op= '+' | op= '-' )
            	    int alt47=2;
            	    int LA47_0 = input.LA(1);

            	    if ( (LA47_0==144) ) {
            	        alt47=1;
            	    }
            	    else if ( (LA47_0==145) ) {
            	        alt47=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 47, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt47) {
            	        case 1 :
            	            // EolParserRules.g:407:31: op= '+'
            	            {
            	            op=(Token)match(input,144,FOLLOW_144_in_additiveExpression1801); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // EolParserRules.g:407:39: op= '-'
            	            {
            	            op=(Token)match(input,145,FOLLOW_145_in_additiveExpression1806); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_multiplicativeExpression_in_additiveExpression1810);
            	    multiplicativeExpression114=multiplicativeExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, multiplicativeExpression114.getTree());
            	    if ( state.backtracking==0 ) {
            	      op.setType(OPERATOR);
            	    }

            	    }
            	    break;

            	default :
            	    break loop48;
                }
            } while (true);


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
    // $ANTLR end additiveExpression

    public static class multiplicativeExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start multiplicativeExpression
    // EolParserRules.g:411:1: multiplicativeExpression : unaryExpression ( (op= '*' | op= '/' ) unaryExpression )* ;
    public final Etl_EolParserRules.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException {
        Etl_EolParserRules.multiplicativeExpression_return retval = new Etl_EolParserRules.multiplicativeExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Etl_EolParserRules.unaryExpression_return unaryExpression115 = null;

        Etl_EolParserRules.unaryExpression_return unaryExpression116 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;

        try {
            // EolParserRules.g:412:2: ( unaryExpression ( (op= '*' | op= '/' ) unaryExpression )* )
            // EolParserRules.g:412:4: unaryExpression ( (op= '*' | op= '/' ) unaryExpression )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1828);
            unaryExpression115=unaryExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression115.getTree());
            // EolParserRules.g:412:20: ( (op= '*' | op= '/' ) unaryExpression )*
            loop50:
            do {
                int alt50=2;
                int LA50_0 = input.LA(1);

                if ( ((LA50_0>=146 && LA50_0<=147)) ) {
                    alt50=1;
                }


                switch (alt50) {
            	case 1 :
            	    // EolParserRules.g:412:21: (op= '*' | op= '/' ) unaryExpression
            	    {
            	    // EolParserRules.g:412:21: (op= '*' | op= '/' )
            	    int alt49=2;
            	    int LA49_0 = input.LA(1);

            	    if ( (LA49_0==146) ) {
            	        alt49=1;
            	    }
            	    else if ( (LA49_0==147) ) {
            	        alt49=2;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 49, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt49) {
            	        case 1 :
            	            // EolParserRules.g:412:22: op= '*'
            	            {
            	            op=(Token)match(input,146,FOLLOW_146_in_multiplicativeExpression1834); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	            }

            	            }
            	            break;
            	        case 2 :
            	            // EolParserRules.g:412:30: op= '/'
            	            {
            	            op=(Token)match(input,147,FOLLOW_147_in_multiplicativeExpression1839); if (state.failed) return retval;
            	            if ( state.backtracking==0 ) {
            	            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
            	            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
            	            }

            	            }
            	            break;

            	    }

            	    pushFollow(FOLLOW_unaryExpression_in_multiplicativeExpression1843);
            	    unaryExpression116=unaryExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, unaryExpression116.getTree());
            	    if ( state.backtracking==0 ) {
            	      op.setType(OPERATOR);
            	    }

            	    }
            	    break;

            	default :
            	    break loop50;
                }
            } while (true);


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
    // $ANTLR end multiplicativeExpression

    public static class unaryExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start unaryExpression
    // EolParserRules.g:416:1: unaryExpression : ( (op= 'not' | op= '-' ) )? shortcutOperatorExpression ;
    public final Etl_EolParserRules.unaryExpression_return unaryExpression() throws RecognitionException {
        Etl_EolParserRules.unaryExpression_return retval = new Etl_EolParserRules.unaryExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Etl_EolParserRules.shortcutOperatorExpression_return shortcutOperatorExpression117 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;

        try {
            // EolParserRules.g:417:2: ( ( (op= 'not' | op= '-' ) )? shortcutOperatorExpression )
            // EolParserRules.g:417:4: ( (op= 'not' | op= '-' ) )? shortcutOperatorExpression
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EolParserRules.g:417:4: ( (op= 'not' | op= '-' ) )?
            int alt52=2;
            int LA52_0 = input.LA(1);

            if ( (LA52_0==145||LA52_0==148) ) {
                alt52=1;
            }
            switch (alt52) {
                case 1 :
                    // EolParserRules.g:417:5: (op= 'not' | op= '-' )
                    {
                    // EolParserRules.g:417:5: (op= 'not' | op= '-' )
                    int alt51=2;
                    int LA51_0 = input.LA(1);

                    if ( (LA51_0==148) ) {
                        alt51=1;
                    }
                    else if ( (LA51_0==145) ) {
                        alt51=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 51, 0, input);

                        throw nvae;
                    }
                    switch (alt51) {
                        case 1 :
                            // EolParserRules.g:417:6: op= 'not'
                            {
                            op=(Token)match(input,148,FOLLOW_148_in_unaryExpression1864); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
                            }

                            }
                            break;
                        case 2 :
                            // EolParserRules.g:417:16: op= '-'
                            {
                            op=(Token)match(input,145,FOLLOW_145_in_unaryExpression1869); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                      op.setType(OPERATOR);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_shortcutOperatorExpression_in_unaryExpression1877);
            shortcutOperatorExpression117=shortcutOperatorExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, shortcutOperatorExpression117.getTree());

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
    // $ANTLR end unaryExpression

    public static class shortcutOperatorExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start shortcutOperatorExpression
    // EolParserRules.g:420:1: shortcutOperatorExpression : postfixExpression ( (op= '++' | op= '--' ) )? ;
    public final Etl_EolParserRules.shortcutOperatorExpression_return shortcutOperatorExpression() throws RecognitionException {
        Etl_EolParserRules.shortcutOperatorExpression_return retval = new Etl_EolParserRules.shortcutOperatorExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Etl_EolParserRules.postfixExpression_return postfixExpression118 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;

        try {
            // EolParserRules.g:421:2: ( postfixExpression ( (op= '++' | op= '--' ) )? )
            // EolParserRules.g:421:4: postfixExpression ( (op= '++' | op= '--' ) )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_postfixExpression_in_shortcutOperatorExpression1889);
            postfixExpression118=postfixExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, postfixExpression118.getTree());
            // EolParserRules.g:421:22: ( (op= '++' | op= '--' ) )?
            int alt54=2;
            int LA54_0 = input.LA(1);

            if ( ((LA54_0>=149 && LA54_0<=150)) ) {
                alt54=1;
            }
            switch (alt54) {
                case 1 :
                    // EolParserRules.g:421:23: (op= '++' | op= '--' )
                    {
                    // EolParserRules.g:421:23: (op= '++' | op= '--' )
                    int alt53=2;
                    int LA53_0 = input.LA(1);

                    if ( (LA53_0==149) ) {
                        alt53=1;
                    }
                    else if ( (LA53_0==150) ) {
                        alt53=2;
                    }
                    else {
                        if (state.backtracking>0) {state.failed=true; return retval;}
                        NoViableAltException nvae =
                            new NoViableAltException("", 53, 0, input);

                        throw nvae;
                    }
                    switch (alt53) {
                        case 1 :
                            // EolParserRules.g:421:24: op= '++'
                            {
                            op=(Token)match(input,149,FOLLOW_149_in_shortcutOperatorExpression1895); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
                            }

                            }
                            break;
                        case 2 :
                            // EolParserRules.g:421:35: op= '--'
                            {
                            op=(Token)match(input,150,FOLLOW_150_in_shortcutOperatorExpression1902); if (state.failed) return retval;
                            if ( state.backtracking==0 ) {
                            op_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(op);
                            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(op_tree, root_0);
                            }

                            }
                            break;

                    }

                    if ( state.backtracking==0 ) {
                      op.setType(OPERATOR);
                    }

                    }
                    break;

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
    // $ANTLR end shortcutOperatorExpression

    public static class postfixExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start postfixExpression
    // EolParserRules.g:424:1: postfixExpression : itemSelectorExpression ( ( NAVIGATION | POINT | ARROW ) fc= featureCall (is= '[' logicalExpression ']' )* )* ;
    public final Etl_EolParserRules.postfixExpression_return postfixExpression() throws RecognitionException {
        Etl_EolParserRules.postfixExpression_return retval = new Etl_EolParserRules.postfixExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token is=null;
        Token set120=null;
        Token char_literal122=null;
        Etl_EolParserRules.featureCall_return fc = null;

        Etl_EolParserRules.itemSelectorExpression_return itemSelectorExpression119 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression121 = null;


        org.eclipse.epsilon.common.parse.AST is_tree=null;
        org.eclipse.epsilon.common.parse.AST set120_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal122_tree=null;

        try {
            // EolParserRules.g:425:2: ( itemSelectorExpression ( ( NAVIGATION | POINT | ARROW ) fc= featureCall (is= '[' logicalExpression ']' )* )* )
            // EolParserRules.g:425:4: itemSelectorExpression ( ( NAVIGATION | POINT | ARROW ) fc= featureCall (is= '[' logicalExpression ']' )* )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_itemSelectorExpression_in_postfixExpression1920);
            itemSelectorExpression119=itemSelectorExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, itemSelectorExpression119.getTree());
            // EolParserRules.g:425:27: ( ( NAVIGATION | POINT | ARROW ) fc= featureCall (is= '[' logicalExpression ']' )* )*
            loop56:
            do {
                int alt56=2;
                int LA56_0 = input.LA(1);

                if ( (LA56_0==POINT||(LA56_0>=ARROW && LA56_0<=NAVIGATION)) ) {
                    alt56=1;
                }


                switch (alt56) {
            	case 1 :
            	    // EolParserRules.g:425:28: ( NAVIGATION | POINT | ARROW ) fc= featureCall (is= '[' logicalExpression ']' )*
            	    {
            	    set120=(Token)input.LT(1);
            	    set120=(Token)input.LT(1);
            	    if ( input.LA(1)==POINT||(input.LA(1)>=ARROW && input.LA(1)<=NAVIGATION) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(set120), root_0);
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }

            	    pushFollow(FOLLOW_featureCall_in_postfixExpression1934);
            	    fc=featureCall();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, fc.getTree());
            	    if ( state.backtracking==0 ) {
            	      setTokenType(fc,FEATURECALL);
            	    }
            	    // EolParserRules.g:426:35: (is= '[' logicalExpression ']' )*
            	    loop55:
            	    do {
            	        int alt55=2;
            	        int LA55_0 = input.LA(1);

            	        if ( (LA55_0==151) ) {
            	            alt55=1;
            	        }


            	        switch (alt55) {
            	    	case 1 :
            	    	    // EolParserRules.g:426:36: is= '[' logicalExpression ']'
            	    	    {
            	    	    is=(Token)match(input,151,FOLLOW_151_in_postfixExpression1943); if (state.failed) return retval;
            	    	    if ( state.backtracking==0 ) {
            	    	    is_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(is);
            	    	    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(is_tree, root_0);
            	    	    }
            	    	    pushFollow(FOLLOW_logicalExpression_in_postfixExpression1946);
            	    	    logicalExpression121=logicalExpression();

            	    	    state._fsp--;
            	    	    if (state.failed) return retval;
            	    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression121.getTree());
            	    	    char_literal122=(Token)match(input,152,FOLLOW_152_in_postfixExpression1948); if (state.failed) return retval;
            	    	    if ( state.backtracking==0 ) {
            	    	      is.setType(ITEMSELECTOR);
            	    	    }

            	    	    }
            	    	    break;

            	    	default :
            	    	    break loop55;
            	        }
            	    } while (true);


            	    }
            	    break;

            	default :
            	    break loop56;
                }
            } while (true);


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
    // $ANTLR end postfixExpression

    public static class itemSelectorExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start itemSelectorExpression
    // EolParserRules.g:430:1: itemSelectorExpression : primitiveExpression (is= '[' primitiveExpression ']' )* ;
    public final Etl_EolParserRules.itemSelectorExpression_return itemSelectorExpression() throws RecognitionException {
        Etl_EolParserRules.itemSelectorExpression_return retval = new Etl_EolParserRules.itemSelectorExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token is=null;
        Token char_literal125=null;
        Etl_EolParserRules.primitiveExpression_return primitiveExpression123 = null;

        Etl_EolParserRules.primitiveExpression_return primitiveExpression124 = null;


        org.eclipse.epsilon.common.parse.AST is_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal125_tree=null;

        try {
            // EolParserRules.g:431:2: ( primitiveExpression (is= '[' primitiveExpression ']' )* )
            // EolParserRules.g:431:4: primitiveExpression (is= '[' primitiveExpression ']' )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_primitiveExpression_in_itemSelectorExpression1970);
            primitiveExpression123=primitiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, primitiveExpression123.getTree());
            // EolParserRules.g:431:24: (is= '[' primitiveExpression ']' )*
            loop57:
            do {
                int alt57=2;
                int LA57_0 = input.LA(1);

                if ( (LA57_0==151) ) {
                    alt57=1;
                }


                switch (alt57) {
            	case 1 :
            	    // EolParserRules.g:431:25: is= '[' primitiveExpression ']'
            	    {
            	    is=(Token)match(input,151,FOLLOW_151_in_itemSelectorExpression1975); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    is_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(is);
            	    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(is_tree, root_0);
            	    }
            	    pushFollow(FOLLOW_primitiveExpression_in_itemSelectorExpression1978);
            	    primitiveExpression124=primitiveExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, primitiveExpression124.getTree());
            	    char_literal125=(Token)match(input,152,FOLLOW_152_in_itemSelectorExpression1980); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	      is.setType(ITEMSELECTOR);
            	    }

            	    }
            	    break;

            	default :
            	    break loop57;
                }
            } while (true);


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
    // $ANTLR end itemSelectorExpression

    public static class featureCall_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start featureCall
    // EolParserRules.g:435:1: featureCall : ( simpleFeatureCall | complexFeatureCall );
    public final Etl_EolParserRules.featureCall_return featureCall() throws RecognitionException {
        Etl_EolParserRules.featureCall_return retval = new Etl_EolParserRules.featureCall_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.simpleFeatureCall_return simpleFeatureCall126 = null;

        Etl_EolParserRules.complexFeatureCall_return complexFeatureCall127 = null;



        try {
            // EolParserRules.g:436:2: ( simpleFeatureCall | complexFeatureCall )
            int alt58=2;
            alt58 = dfa58.predict(input);
            switch (alt58) {
                case 1 :
                    // EolParserRules.g:436:4: simpleFeatureCall
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_simpleFeatureCall_in_featureCall1998);
                    simpleFeatureCall126=simpleFeatureCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, simpleFeatureCall126.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:436:24: complexFeatureCall
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_complexFeatureCall_in_featureCall2002);
                    complexFeatureCall127=complexFeatureCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, complexFeatureCall127.getTree());

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
    // $ANTLR end featureCall

    public static class simpleFeatureCall_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start simpleFeatureCall
    // EolParserRules.g:439:1: simpleFeatureCall : n= NAME ( parameterList )? ;
    public final Etl_EolParserRules.simpleFeatureCall_return simpleFeatureCall() throws RecognitionException {
        Etl_EolParserRules.simpleFeatureCall_return retval = new Etl_EolParserRules.simpleFeatureCall_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token n=null;
        Etl_EolParserRules.parameterList_return parameterList128 = null;


        org.eclipse.epsilon.common.parse.AST n_tree=null;

        try {
            // EolParserRules.g:440:2: (n= NAME ( parameterList )? )
            // EolParserRules.g:440:5: n= NAME ( parameterList )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            n=(Token)match(input,NAME,FOLLOW_NAME_in_simpleFeatureCall2016); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(n);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(n_tree, root_0);
            }
            // EolParserRules.g:440:13: ( parameterList )?
            int alt59=2;
            int LA59_0 = input.LA(1);

            if ( (LA59_0==101) ) {
                alt59=1;
            }
            switch (alt59) {
                case 1 :
                    // EolParserRules.g:0:0: parameterList
                    {
                    pushFollow(FOLLOW_parameterList_in_simpleFeatureCall2019);
                    parameterList128=parameterList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, parameterList128.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              n.setType(FEATURECALL);
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
    // $ANTLR end simpleFeatureCall

    public static class parameterList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start parameterList
    // EolParserRules.g:444:1: parameterList : op= '(' ( logicalExpression ( ',' logicalExpression )* )? ( ',' )? cp= ')' -> ^( PARAMETERS ( logicalExpression )* ) ;
    public final Etl_EolParserRules.parameterList_return parameterList() throws RecognitionException {
        Etl_EolParserRules.parameterList_return retval = new Etl_EolParserRules.parameterList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Token cp=null;
        Token char_literal130=null;
        Token char_literal132=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression129 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression131 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;
        org.eclipse.epsilon.common.parse.AST cp_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal130_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal132_tree=null;
        RewriteRuleTokenStream stream_101=new RewriteRuleTokenStream(adaptor,"token 101");
        RewriteRuleTokenStream stream_102=new RewriteRuleTokenStream(adaptor,"token 102");
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleSubtreeStream stream_logicalExpression=new RewriteRuleSubtreeStream(adaptor,"rule logicalExpression");
        try {
            // EolParserRules.g:450:2: (op= '(' ( logicalExpression ( ',' logicalExpression )* )? ( ',' )? cp= ')' -> ^( PARAMETERS ( logicalExpression )* ) )
            // EolParserRules.g:450:4: op= '(' ( logicalExpression ( ',' logicalExpression )* )? ( ',' )? cp= ')'
            {
            op=(Token)match(input,101,FOLLOW_101_in_parameterList2042); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_101.add(op);

            // EolParserRules.g:450:11: ( logicalExpression ( ',' logicalExpression )* )?
            int alt61=2;
            int LA61_0 = input.LA(1);

            if ( (LA61_0==FLOAT||LA61_0==INT||LA61_0==BOOLEAN||LA61_0==STRING||(LA61_0>=CollectionTypeName && LA61_0<=SpecialTypeName)||LA61_0==NAME||LA61_0==101||LA61_0==145||LA61_0==148||(LA61_0>=155 && LA61_0<=158)) ) {
                alt61=1;
            }
            switch (alt61) {
                case 1 :
                    // EolParserRules.g:450:12: logicalExpression ( ',' logicalExpression )*
                    {
                    pushFollow(FOLLOW_logicalExpression_in_parameterList2045);
                    logicalExpression129=logicalExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) stream_logicalExpression.add(logicalExpression129.getTree());
                    // EolParserRules.g:450:30: ( ',' logicalExpression )*
                    loop60:
                    do {
                        int alt60=2;
                        int LA60_0 = input.LA(1);

                        if ( (LA60_0==94) ) {
                            int LA60_1 = input.LA(2);

                            if ( (LA60_1==FLOAT||LA60_1==INT||LA60_1==BOOLEAN||LA60_1==STRING||(LA60_1>=CollectionTypeName && LA60_1<=SpecialTypeName)||LA60_1==NAME||LA60_1==101||LA60_1==145||LA60_1==148||(LA60_1>=155 && LA60_1<=158)) ) {
                                alt60=1;
                            }


                        }


                        switch (alt60) {
                    	case 1 :
                    	    // EolParserRules.g:450:31: ',' logicalExpression
                    	    {
                    	    char_literal130=(Token)match(input,94,FOLLOW_94_in_parameterList2048); if (state.failed) return retval; 
                    	    if ( state.backtracking==0 ) stream_94.add(char_literal130);

                    	    pushFollow(FOLLOW_logicalExpression_in_parameterList2050);
                    	    logicalExpression131=logicalExpression();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) stream_logicalExpression.add(logicalExpression131.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop60;
                        }
                    } while (true);


                    }
                    break;

            }

            // EolParserRules.g:450:57: ( ',' )?
            int alt62=2;
            int LA62_0 = input.LA(1);

            if ( (LA62_0==94) ) {
                alt62=1;
            }
            switch (alt62) {
                case 1 :
                    // EolParserRules.g:0:0: ','
                    {
                    char_literal132=(Token)match(input,94,FOLLOW_94_in_parameterList2056); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_94.add(char_literal132);


                    }
                    break;

            }

            cp=(Token)match(input,102,FOLLOW_102_in_parameterList2061); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_102.add(cp);



            // AST REWRITE
            // elements: logicalExpression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 451:3: -> ^( PARAMETERS ( logicalExpression )* )
            {
                // EolParserRules.g:451:6: ^( PARAMETERS ( logicalExpression )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(PARAMETERS, "PARAMETERS"), root_1);

                // EolParserRules.g:451:19: ( logicalExpression )*
                while ( stream_logicalExpression.hasNext() ) {
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
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).setImaginary(true);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(op);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cp);
              	
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
    // $ANTLR end parameterList

    public static class complexFeatureCall_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start complexFeatureCall
    // EolParserRules.g:454:1: complexFeatureCall : NAME op= '(' ( lambdaExpression | lambdaExpressionInBrackets ) ( ',' ( logicalExpression | lambdaExpressionInBrackets ) )* cp= ')' ;
    public final Etl_EolParserRules.complexFeatureCall_return complexFeatureCall() throws RecognitionException {
        Etl_EolParserRules.complexFeatureCall_return retval = new Etl_EolParserRules.complexFeatureCall_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token op=null;
        Token cp=null;
        Token NAME133=null;
        Token char_literal136=null;
        Etl_EolParserRules.lambdaExpression_return lambdaExpression134 = null;

        Etl_EolParserRules.lambdaExpressionInBrackets_return lambdaExpressionInBrackets135 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression137 = null;

        Etl_EolParserRules.lambdaExpressionInBrackets_return lambdaExpressionInBrackets138 = null;


        org.eclipse.epsilon.common.parse.AST op_tree=null;
        org.eclipse.epsilon.common.parse.AST cp_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME133_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal136_tree=null;

        try {
            // EolParserRules.g:459:2: ( NAME op= '(' ( lambdaExpression | lambdaExpressionInBrackets ) ( ',' ( logicalExpression | lambdaExpressionInBrackets ) )* cp= ')' )
            // EolParserRules.g:459:4: NAME op= '(' ( lambdaExpression | lambdaExpressionInBrackets ) ( ',' ( logicalExpression | lambdaExpressionInBrackets ) )* cp= ')'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            NAME133=(Token)match(input,NAME,FOLLOW_NAME_in_complexFeatureCall2089); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME133_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME133);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(NAME133_tree, root_0);
            }
            op=(Token)match(input,101,FOLLOW_101_in_complexFeatureCall2094); if (state.failed) return retval;
            // EolParserRules.g:459:18: ( lambdaExpression | lambdaExpressionInBrackets )
            int alt63=2;
            int LA63_0 = input.LA(1);

            if ( (LA63_0==NAME||(LA63_0>=153 && LA63_0<=154)) ) {
                alt63=1;
            }
            else if ( (LA63_0==101||LA63_0==151) ) {
                alt63=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 63, 0, input);

                throw nvae;
            }
            switch (alt63) {
                case 1 :
                    // EolParserRules.g:459:19: lambdaExpression
                    {
                    pushFollow(FOLLOW_lambdaExpression_in_complexFeatureCall2098);
                    lambdaExpression134=lambdaExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lambdaExpression134.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:459:38: lambdaExpressionInBrackets
                    {
                    pushFollow(FOLLOW_lambdaExpressionInBrackets_in_complexFeatureCall2102);
                    lambdaExpressionInBrackets135=lambdaExpressionInBrackets();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lambdaExpressionInBrackets135.getTree());

                    }
                    break;

            }

            // EolParserRules.g:460:3: ( ',' ( logicalExpression | lambdaExpressionInBrackets ) )*
            loop65:
            do {
                int alt65=2;
                int LA65_0 = input.LA(1);

                if ( (LA65_0==94) ) {
                    alt65=1;
                }


                switch (alt65) {
            	case 1 :
            	    // EolParserRules.g:460:4: ',' ( logicalExpression | lambdaExpressionInBrackets )
            	    {
            	    char_literal136=(Token)match(input,94,FOLLOW_94_in_complexFeatureCall2108); if (state.failed) return retval;
            	    // EolParserRules.g:460:9: ( logicalExpression | lambdaExpressionInBrackets )
            	    int alt64=2;
            	    switch ( input.LA(1) ) {
            	    case FLOAT:
            	    case INT:
            	    case BOOLEAN:
            	    case STRING:
            	    case CollectionTypeName:
            	    case MapTypeName:
            	    case SpecialTypeName:
            	    case NAME:
            	    case 145:
            	    case 148:
            	    case 155:
            	    case 156:
            	    case 157:
            	    case 158:
            	        {
            	        alt64=1;
            	        }
            	        break;
            	    case 101:
            	        {
            	        switch ( input.LA(2) ) {
            	        case NAME:
            	            {
            	            int LA64_4 = input.LA(3);

            	            if ( (LA64_4==POINT||(LA64_4>=ARROW && LA64_4<=NAVIGATION)||LA64_4==98||(LA64_4>=101 && LA64_4<=102)||(LA64_4>=106 && LA64_4<=109)||(LA64_4>=133 && LA64_4<=147)||(LA64_4>=149 && LA64_4<=151)||LA64_4==158) ) {
            	                alt64=1;
            	            }
            	            else if ( (LA64_4==94||LA64_4==103||(LA64_4>=153 && LA64_4<=154)) ) {
            	                alt64=2;
            	            }
            	            else {
            	                if (state.backtracking>0) {state.failed=true; return retval;}
            	                NoViableAltException nvae =
            	                    new NoViableAltException("", 64, 4, input);

            	                throw nvae;
            	            }
            	            }
            	            break;
            	        case 153:
            	        case 154:
            	            {
            	            alt64=2;
            	            }
            	            break;
            	        case FLOAT:
            	        case INT:
            	        case BOOLEAN:
            	        case STRING:
            	        case CollectionTypeName:
            	        case MapTypeName:
            	        case SpecialTypeName:
            	        case 101:
            	        case 145:
            	        case 148:
            	        case 155:
            	        case 156:
            	        case 157:
            	        case 158:
            	            {
            	            alt64=1;
            	            }
            	            break;
            	        default:
            	            if (state.backtracking>0) {state.failed=true; return retval;}
            	            NoViableAltException nvae =
            	                new NoViableAltException("", 64, 2, input);

            	            throw nvae;
            	        }

            	        }
            	        break;
            	    case 151:
            	        {
            	        alt64=2;
            	        }
            	        break;
            	    default:
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 64, 0, input);

            	        throw nvae;
            	    }

            	    switch (alt64) {
            	        case 1 :
            	            // EolParserRules.g:460:10: logicalExpression
            	            {
            	            pushFollow(FOLLOW_logicalExpression_in_complexFeatureCall2112);
            	            logicalExpression137=logicalExpression();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression137.getTree());

            	            }
            	            break;
            	        case 2 :
            	            // EolParserRules.g:460:30: lambdaExpressionInBrackets
            	            {
            	            pushFollow(FOLLOW_lambdaExpressionInBrackets_in_complexFeatureCall2116);
            	            lambdaExpressionInBrackets138=lambdaExpressionInBrackets();

            	            state._fsp--;
            	            if (state.failed) return retval;
            	            if ( state.backtracking==0 ) adaptor.addChild(root_0, lambdaExpressionInBrackets138.getTree());

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop65;
                }
            } while (true);

            cp=(Token)match(input,102,FOLLOW_102_in_complexFeatureCall2123); if (state.failed) return retval;

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(op);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(cp);
              	
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
    // $ANTLR end complexFeatureCall

    public static class lambdaExpressionInBrackets_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start lambdaExpressionInBrackets
    // EolParserRules.g:463:1: lambdaExpressionInBrackets : ( (lop= '(' lambdaExpression lcp= ')' ) | (lop= '[' lambdaExpression lcp= ']' ) );
    public final Etl_EolParserRules.lambdaExpressionInBrackets_return lambdaExpressionInBrackets() throws RecognitionException {
        Etl_EolParserRules.lambdaExpressionInBrackets_return retval = new Etl_EolParserRules.lambdaExpressionInBrackets_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token lop=null;
        Token lcp=null;
        Etl_EolParserRules.lambdaExpression_return lambdaExpression139 = null;

        Etl_EolParserRules.lambdaExpression_return lambdaExpression140 = null;


        org.eclipse.epsilon.common.parse.AST lop_tree=null;
        org.eclipse.epsilon.common.parse.AST lcp_tree=null;

        try {
            // EolParserRules.g:469:2: ( (lop= '(' lambdaExpression lcp= ')' ) | (lop= '[' lambdaExpression lcp= ']' ) )
            int alt66=2;
            int LA66_0 = input.LA(1);

            if ( (LA66_0==101) ) {
                alt66=1;
            }
            else if ( (LA66_0==151) ) {
                alt66=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 66, 0, input);

                throw nvae;
            }
            switch (alt66) {
                case 1 :
                    // EolParserRules.g:469:4: (lop= '(' lambdaExpression lcp= ')' )
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    // EolParserRules.g:469:4: (lop= '(' lambdaExpression lcp= ')' )
                    // EolParserRules.g:469:5: lop= '(' lambdaExpression lcp= ')'
                    {
                    lop=(Token)match(input,101,FOLLOW_101_in_lambdaExpressionInBrackets2144); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    lop_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(lop);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(lop_tree, root_0);
                    }
                    pushFollow(FOLLOW_lambdaExpression_in_lambdaExpressionInBrackets2147);
                    lambdaExpression139=lambdaExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lambdaExpression139.getTree());
                    lcp=(Token)match(input,102,FOLLOW_102_in_lambdaExpressionInBrackets2151); if (state.failed) return retval;

                    }


                    }
                    break;
                case 2 :
                    // EolParserRules.g:470:3: (lop= '[' lambdaExpression lcp= ']' )
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    // EolParserRules.g:470:3: (lop= '[' lambdaExpression lcp= ']' )
                    // EolParserRules.g:470:4: lop= '[' lambdaExpression lcp= ']'
                    {
                    lop=(Token)match(input,151,FOLLOW_151_in_lambdaExpressionInBrackets2162); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    lop_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(lop);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(lop_tree, root_0);
                    }
                    pushFollow(FOLLOW_lambdaExpression_in_lambdaExpressionInBrackets2165);
                    lambdaExpression140=lambdaExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lambdaExpression140.getTree());
                    lcp=(Token)match(input,152,FOLLOW_152_in_lambdaExpressionInBrackets2169); if (state.failed) return retval;

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		lop.setType(LAMBDAEXPR);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(lop);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(lcp);
              	
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
    // $ANTLR end lambdaExpressionInBrackets

    public static class lambdaExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start lambdaExpression
    // EolParserRules.g:473:1: lambdaExpression : ( formalParameterList )? (lt= '|' | lt= '=>' ) logicalExpression ;
    public final Etl_EolParserRules.lambdaExpression_return lambdaExpression() throws RecognitionException {
        Etl_EolParserRules.lambdaExpression_return retval = new Etl_EolParserRules.lambdaExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token lt=null;
        Etl_EolParserRules.formalParameterList_return formalParameterList141 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression142 = null;


        org.eclipse.epsilon.common.parse.AST lt_tree=null;

        try {
            // EolParserRules.g:477:2: ( ( formalParameterList )? (lt= '|' | lt= '=>' ) logicalExpression )
            // EolParserRules.g:477:4: ( formalParameterList )? (lt= '|' | lt= '=>' ) logicalExpression
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EolParserRules.g:477:4: ( formalParameterList )?
            int alt67=2;
            int LA67_0 = input.LA(1);

            if ( (LA67_0==NAME) ) {
                alt67=1;
            }
            switch (alt67) {
                case 1 :
                    // EolParserRules.g:0:0: formalParameterList
                    {
                    pushFollow(FOLLOW_formalParameterList_in_lambdaExpression2188);
                    formalParameterList141=formalParameterList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, formalParameterList141.getTree());

                    }
                    break;

            }

            // EolParserRules.g:477:25: (lt= '|' | lt= '=>' )
            int alt68=2;
            int LA68_0 = input.LA(1);

            if ( (LA68_0==153) ) {
                alt68=1;
            }
            else if ( (LA68_0==154) ) {
                alt68=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 68, 0, input);

                throw nvae;
            }
            switch (alt68) {
                case 1 :
                    // EolParserRules.g:477:26: lt= '|'
                    {
                    lt=(Token)match(input,153,FOLLOW_153_in_lambdaExpression2194); if (state.failed) return retval;

                    }
                    break;
                case 2 :
                    // EolParserRules.g:477:36: lt= '=>'
                    {
                    lt=(Token)match(input,154,FOLLOW_154_in_lambdaExpression2201); if (state.failed) return retval;

                    }
                    break;

            }

            pushFollow(FOLLOW_logicalExpression_in_lambdaExpression2205);
            logicalExpression142=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression142.getTree());

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(lt);
              	
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
    // $ANTLR end lambdaExpression

    public static class newExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start newExpression
    // EolParserRules.g:480:1: newExpression : n= 'new' tn= typeName ( parameterList )? ;
    public final Etl_EolParserRules.newExpression_return newExpression() throws RecognitionException {
        Etl_EolParserRules.newExpression_return retval = new Etl_EolParserRules.newExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token n=null;
        Etl_EolParserRules.typeName_return tn = null;

        Etl_EolParserRules.parameterList_return parameterList143 = null;


        org.eclipse.epsilon.common.parse.AST n_tree=null;

        try {
            // EolParserRules.g:481:2: (n= 'new' tn= typeName ( parameterList )? )
            // EolParserRules.g:481:4: n= 'new' tn= typeName ( parameterList )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            n=(Token)match(input,155,FOLLOW_155_in_newExpression2218); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(n);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(n_tree, root_0);
            }
            pushFollow(FOLLOW_typeName_in_newExpression2223);
            tn=typeName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, tn.getTree());
            if ( state.backtracking==0 ) {
              setTokenType(tn,TYPE);
            }
            // EolParserRules.g:481:50: ( parameterList )?
            int alt69=2;
            int LA69_0 = input.LA(1);

            if ( (LA69_0==101) ) {
                alt69=1;
            }
            switch (alt69) {
                case 1 :
                    // EolParserRules.g:0:0: parameterList
                    {
                    pushFollow(FOLLOW_parameterList_in_newExpression2227);
                    parameterList143=parameterList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, parameterList143.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              n.setType(NEW);
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
    // $ANTLR end newExpression

    public static class variableDeclarationExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start variableDeclarationExpression
    // EolParserRules.g:485:1: variableDeclarationExpression : (v= 'var' | v= 'ext' ) NAME ( ':' (n= 'new' )? t= typeName ( parameterList )? )? ;
    public final Etl_EolParserRules.variableDeclarationExpression_return variableDeclarationExpression() throws RecognitionException {
        Etl_EolParserRules.variableDeclarationExpression_return retval = new Etl_EolParserRules.variableDeclarationExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token v=null;
        Token n=null;
        Token NAME144=null;
        Token char_literal145=null;
        Etl_EolParserRules.typeName_return t = null;

        Etl_EolParserRules.parameterList_return parameterList146 = null;


        org.eclipse.epsilon.common.parse.AST v_tree=null;
        org.eclipse.epsilon.common.parse.AST n_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME144_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal145_tree=null;

        try {
            // EolParserRules.g:491:2: ( (v= 'var' | v= 'ext' ) NAME ( ':' (n= 'new' )? t= typeName ( parameterList )? )? )
            // EolParserRules.g:491:4: (v= 'var' | v= 'ext' ) NAME ( ':' (n= 'new' )? t= typeName ( parameterList )? )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EolParserRules.g:491:4: (v= 'var' | v= 'ext' )
            int alt70=2;
            int LA70_0 = input.LA(1);

            if ( (LA70_0==156) ) {
                alt70=1;
            }
            else if ( (LA70_0==157) ) {
                alt70=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 70, 0, input);

                throw nvae;
            }
            switch (alt70) {
                case 1 :
                    // EolParserRules.g:491:5: v= 'var'
                    {
                    v=(Token)match(input,156,FOLLOW_156_in_variableDeclarationExpression2251); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    v_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(v);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(v_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // EolParserRules.g:491:14: v= 'ext'
                    {
                    v=(Token)match(input,157,FOLLOW_157_in_variableDeclarationExpression2256); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    v_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(v);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(v_tree, root_0);
                    }

                    }
                    break;

            }

            NAME144=(Token)match(input,NAME,FOLLOW_NAME_in_variableDeclarationExpression2260); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME144_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME144);
            adaptor.addChild(root_0, NAME144_tree);
            }
            // EolParserRules.g:491:29: ( ':' (n= 'new' )? t= typeName ( parameterList )? )?
            int alt73=2;
            alt73 = dfa73.predict(input);
            switch (alt73) {
                case 1 :
                    // EolParserRules.g:491:30: ':' (n= 'new' )? t= typeName ( parameterList )?
                    {
                    char_literal145=(Token)match(input,103,FOLLOW_103_in_variableDeclarationExpression2263); if (state.failed) return retval;
                    // EolParserRules.g:491:36: (n= 'new' )?
                    int alt71=2;
                    int LA71_0 = input.LA(1);

                    if ( (LA71_0==155) ) {
                        alt71=1;
                    }
                    switch (alt71) {
                        case 1 :
                            // EolParserRules.g:0:0: n= 'new'
                            {
                            n=(Token)match(input,155,FOLLOW_155_in_variableDeclarationExpression2268); if (state.failed) return retval;

                            }
                            break;

                    }

                    pushFollow(FOLLOW_typeName_in_variableDeclarationExpression2274);
                    t=typeName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
                    if ( state.backtracking==0 ) {
                      setTokenType(t, TYPE);
                    }
                    // EolParserRules.g:491:81: ( parameterList )?
                    int alt72=2;
                    int LA72_0 = input.LA(1);

                    if ( (LA72_0==101) ) {
                        alt72=1;
                    }
                    switch (alt72) {
                        case 1 :
                            // EolParserRules.g:0:0: parameterList
                            {
                            pushFollow(FOLLOW_parameterList_in_variableDeclarationExpression2278);
                            parameterList146=parameterList();

                            state._fsp--;
                            if (state.failed) return retval;
                            if ( state.backtracking==0 ) adaptor.addChild(root_0, parameterList146.getTree());

                            }
                            break;

                    }


                    }
                    break;

            }


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		String txt = n != null ? "new" : v.getText();
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setText(txt);
              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getToken().setType(VAR);
              	
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
    // $ANTLR end variableDeclarationExpression

    public static class literalSequentialCollection_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start literalSequentialCollection
    // EolParserRules.g:494:1: literalSequentialCollection : l= CollectionTypeName ob= '{' ( expressionListOrRange )? cb= '}' ;
    public final Etl_EolParserRules.literalSequentialCollection_return literalSequentialCollection() throws RecognitionException {
        Etl_EolParserRules.literalSequentialCollection_return retval = new Etl_EolParserRules.literalSequentialCollection_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token l=null;
        Token ob=null;
        Token cb=null;
        Etl_EolParserRules.expressionListOrRange_return expressionListOrRange147 = null;


        org.eclipse.epsilon.common.parse.AST l_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;

        try {
            // EolParserRules.g:499:2: (l= CollectionTypeName ob= '{' ( expressionListOrRange )? cb= '}' )
            // EolParserRules.g:499:4: l= CollectionTypeName ob= '{' ( expressionListOrRange )? cb= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            l=(Token)match(input,CollectionTypeName,FOLLOW_CollectionTypeName_in_literalSequentialCollection2301); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            l_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(l);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(l_tree, root_0);
            }
            ob=(Token)match(input,96,FOLLOW_96_in_literalSequentialCollection2308); if (state.failed) return retval;
            // EolParserRules.g:500:11: ( expressionListOrRange )?
            int alt74=2;
            int LA74_0 = input.LA(1);

            if ( (LA74_0==FLOAT||LA74_0==INT||LA74_0==BOOLEAN||LA74_0==STRING||(LA74_0>=CollectionTypeName && LA74_0<=SpecialTypeName)||LA74_0==NAME||LA74_0==101||LA74_0==145||LA74_0==148||(LA74_0>=155 && LA74_0<=158)) ) {
                alt74=1;
            }
            switch (alt74) {
                case 1 :
                    // EolParserRules.g:0:0: expressionListOrRange
                    {
                    pushFollow(FOLLOW_expressionListOrRange_in_literalSequentialCollection2311);
                    expressionListOrRange147=expressionListOrRange();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionListOrRange147.getTree());

                    }
                    break;

            }

            cb=(Token)match(input,97,FOLLOW_97_in_literalSequentialCollection2316); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              l.setType(COLLECTION);
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
    // $ANTLR end literalSequentialCollection

    public static class expressionRange_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expressionRange
    // EolParserRules.g:504:1: expressionRange : logicalExpression exp= '..' logicalExpression ;
    public final Etl_EolParserRules.expressionRange_return expressionRange() throws RecognitionException {
        Etl_EolParserRules.expressionRange_return retval = new Etl_EolParserRules.expressionRange_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token exp=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression148 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression149 = null;


        org.eclipse.epsilon.common.parse.AST exp_tree=null;

        try {
            // EolParserRules.g:505:2: ( logicalExpression exp= '..' logicalExpression )
            // EolParserRules.g:505:4: logicalExpression exp= '..' logicalExpression
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_logicalExpression_in_expressionRange2331);
            logicalExpression148=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression148.getTree());
            exp=(Token)match(input,POINT_POINT,FOLLOW_POINT_POINT_in_expressionRange2335); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            exp_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(exp);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(exp_tree, root_0);
            }
            pushFollow(FOLLOW_logicalExpression_in_expressionRange2338);
            logicalExpression149=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression149.getTree());
            if ( state.backtracking==0 ) {
              exp.setType(EXPRRANGE);
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
    // $ANTLR end expressionRange

    public static class expressionList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expressionList
    // EolParserRules.g:509:1: expressionList : logicalExpression ( ',' logicalExpression )* ( ',' )? -> ^( EXPRLIST ( logicalExpression )+ ) ;
    public final Etl_EolParserRules.expressionList_return expressionList() throws RecognitionException {
        Etl_EolParserRules.expressionList_return retval = new Etl_EolParserRules.expressionList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token char_literal151=null;
        Token char_literal153=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression150 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression152 = null;


        org.eclipse.epsilon.common.parse.AST char_literal151_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal153_tree=null;
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleSubtreeStream stream_logicalExpression=new RewriteRuleSubtreeStream(adaptor,"rule logicalExpression");
        try {
            // EolParserRules.g:513:2: ( logicalExpression ( ',' logicalExpression )* ( ',' )? -> ^( EXPRLIST ( logicalExpression )+ ) )
            // EolParserRules.g:513:4: logicalExpression ( ',' logicalExpression )* ( ',' )?
            {
            pushFollow(FOLLOW_logicalExpression_in_expressionList2359);
            logicalExpression150=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_logicalExpression.add(logicalExpression150.getTree());
            // EolParserRules.g:513:22: ( ',' logicalExpression )*
            loop75:
            do {
                int alt75=2;
                int LA75_0 = input.LA(1);

                if ( (LA75_0==94) ) {
                    int LA75_1 = input.LA(2);

                    if ( (LA75_1==FLOAT||LA75_1==INT||LA75_1==BOOLEAN||LA75_1==STRING||(LA75_1>=CollectionTypeName && LA75_1<=SpecialTypeName)||LA75_1==NAME||LA75_1==101||LA75_1==145||LA75_1==148||(LA75_1>=155 && LA75_1<=158)) ) {
                        alt75=1;
                    }


                }


                switch (alt75) {
            	case 1 :
            	    // EolParserRules.g:513:23: ',' logicalExpression
            	    {
            	    char_literal151=(Token)match(input,94,FOLLOW_94_in_expressionList2362); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_94.add(char_literal151);

            	    pushFollow(FOLLOW_logicalExpression_in_expressionList2364);
            	    logicalExpression152=logicalExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_logicalExpression.add(logicalExpression152.getTree());

            	    }
            	    break;

            	default :
            	    break loop75;
                }
            } while (true);

            // EolParserRules.g:513:47: ( ',' )?
            int alt76=2;
            int LA76_0 = input.LA(1);

            if ( (LA76_0==94) ) {
                alt76=1;
            }
            switch (alt76) {
                case 1 :
                    // EolParserRules.g:0:0: ','
                    {
                    char_literal153=(Token)match(input,94,FOLLOW_94_in_expressionList2368); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_94.add(char_literal153);


                    }
                    break;

            }



            // AST REWRITE
            // elements: logicalExpression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 514:2: -> ^( EXPRLIST ( logicalExpression )+ )
            {
                // EolParserRules.g:514:5: ^( EXPRLIST ( logicalExpression )+ )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(EXPRLIST, "EXPRLIST"), root_1);

                if ( !(stream_logicalExpression.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_logicalExpression.hasNext() ) {
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
    // $ANTLR end expressionList

    public static class expressionListOrRange_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start expressionListOrRange
    // EolParserRules.g:517:1: expressionListOrRange : ( expressionRange | expressionList );
    public final Etl_EolParserRules.expressionListOrRange_return expressionListOrRange() throws RecognitionException {
        Etl_EolParserRules.expressionListOrRange_return retval = new Etl_EolParserRules.expressionListOrRange_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.expressionRange_return expressionRange154 = null;

        Etl_EolParserRules.expressionList_return expressionList155 = null;



        try {
            // EolParserRules.g:518:2: ( expressionRange | expressionList )
            int alt77=2;
            alt77 = dfa77.predict(input);
            switch (alt77) {
                case 1 :
                    // EolParserRules.g:518:4: expressionRange
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_expressionRange_in_expressionListOrRange2391);
                    expressionRange154=expressionRange();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionRange154.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:518:22: expressionList
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_expressionList_in_expressionListOrRange2395);
                    expressionList155=expressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionList155.getTree());

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
    // $ANTLR end expressionListOrRange

    public static class literalMapCollection_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start literalMapCollection
    // EolParserRules.g:521:1: literalMapCollection : m= MapTypeName ob= '{' ( keyvalExpressionList )? cb= '}' ;
    public final Etl_EolParserRules.literalMapCollection_return literalMapCollection() throws RecognitionException {
        Etl_EolParserRules.literalMapCollection_return retval = new Etl_EolParserRules.literalMapCollection_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token m=null;
        Token ob=null;
        Token cb=null;
        Etl_EolParserRules.keyvalExpressionList_return keyvalExpressionList156 = null;


        org.eclipse.epsilon.common.parse.AST m_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;

        try {
            // EolParserRules.g:526:2: (m= MapTypeName ob= '{' ( keyvalExpressionList )? cb= '}' )
            // EolParserRules.g:526:4: m= MapTypeName ob= '{' ( keyvalExpressionList )? cb= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            m=(Token)match(input,MapTypeName,FOLLOW_MapTypeName_in_literalMapCollection2414); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            m_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(m);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(m_tree, root_0);
            }
            ob=(Token)match(input,96,FOLLOW_96_in_literalMapCollection2419); if (state.failed) return retval;
            // EolParserRules.g:526:27: ( keyvalExpressionList )?
            int alt78=2;
            int LA78_0 = input.LA(1);

            if ( (LA78_0==FLOAT||LA78_0==INT||LA78_0==BOOLEAN||LA78_0==STRING||(LA78_0>=CollectionTypeName && LA78_0<=SpecialTypeName)||LA78_0==NAME||LA78_0==101||LA78_0==145||LA78_0==148||(LA78_0>=155 && LA78_0<=158)) ) {
                alt78=1;
            }
            switch (alt78) {
                case 1 :
                    // EolParserRules.g:0:0: keyvalExpressionList
                    {
                    pushFollow(FOLLOW_keyvalExpressionList_in_literalMapCollection2422);
                    keyvalExpressionList156=keyvalExpressionList();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, keyvalExpressionList156.getTree());

                    }
                    break;

            }

            cb=(Token)match(input,97,FOLLOW_97_in_literalMapCollection2427); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              m.setType(MAP);
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
    // $ANTLR end literalMapCollection

    public static class keyvalExpressionList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start keyvalExpressionList
    // EolParserRules.g:530:1: keyvalExpressionList : keyvalExpression ( ',' keyvalExpression )* ( ',' )? -> ^( KEYVALLIST ( keyvalExpression )+ ) ;
    public final Etl_EolParserRules.keyvalExpressionList_return keyvalExpressionList() throws RecognitionException {
        Etl_EolParserRules.keyvalExpressionList_return retval = new Etl_EolParserRules.keyvalExpressionList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token char_literal158=null;
        Token char_literal160=null;
        Etl_EolParserRules.keyvalExpression_return keyvalExpression157 = null;

        Etl_EolParserRules.keyvalExpression_return keyvalExpression159 = null;


        org.eclipse.epsilon.common.parse.AST char_literal158_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal160_tree=null;
        RewriteRuleTokenStream stream_94=new RewriteRuleTokenStream(adaptor,"token 94");
        RewriteRuleSubtreeStream stream_keyvalExpression=new RewriteRuleSubtreeStream(adaptor,"rule keyvalExpression");
        try {
            // EolParserRules.g:534:2: ( keyvalExpression ( ',' keyvalExpression )* ( ',' )? -> ^( KEYVALLIST ( keyvalExpression )+ ) )
            // EolParserRules.g:534:4: keyvalExpression ( ',' keyvalExpression )* ( ',' )?
            {
            pushFollow(FOLLOW_keyvalExpression_in_keyvalExpressionList2448);
            keyvalExpression157=keyvalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) stream_keyvalExpression.add(keyvalExpression157.getTree());
            // EolParserRules.g:534:21: ( ',' keyvalExpression )*
            loop79:
            do {
                int alt79=2;
                int LA79_0 = input.LA(1);

                if ( (LA79_0==94) ) {
                    int LA79_1 = input.LA(2);

                    if ( (LA79_1==FLOAT||LA79_1==INT||LA79_1==BOOLEAN||LA79_1==STRING||(LA79_1>=CollectionTypeName && LA79_1<=SpecialTypeName)||LA79_1==NAME||LA79_1==101||LA79_1==145||LA79_1==148||(LA79_1>=155 && LA79_1<=158)) ) {
                        alt79=1;
                    }


                }


                switch (alt79) {
            	case 1 :
            	    // EolParserRules.g:534:22: ',' keyvalExpression
            	    {
            	    char_literal158=(Token)match(input,94,FOLLOW_94_in_keyvalExpressionList2451); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_94.add(char_literal158);

            	    pushFollow(FOLLOW_keyvalExpression_in_keyvalExpressionList2453);
            	    keyvalExpression159=keyvalExpression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_keyvalExpression.add(keyvalExpression159.getTree());

            	    }
            	    break;

            	default :
            	    break loop79;
                }
            } while (true);

            // EolParserRules.g:534:45: ( ',' )?
            int alt80=2;
            int LA80_0 = input.LA(1);

            if ( (LA80_0==94) ) {
                alt80=1;
            }
            switch (alt80) {
                case 1 :
                    // EolParserRules.g:0:0: ','
                    {
                    char_literal160=(Token)match(input,94,FOLLOW_94_in_keyvalExpressionList2457); if (state.failed) return retval; 
                    if ( state.backtracking==0 ) stream_94.add(char_literal160);


                    }
                    break;

            }



            // AST REWRITE
            // elements: keyvalExpression
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 535:2: -> ^( KEYVALLIST ( keyvalExpression )+ )
            {
                // EolParserRules.g:535:5: ^( KEYVALLIST ( keyvalExpression )+ )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(KEYVALLIST, "KEYVALLIST"), root_1);

                if ( !(stream_keyvalExpression.hasNext()) ) {
                    throw new RewriteEarlyExitException();
                }
                while ( stream_keyvalExpression.hasNext() ) {
                    adaptor.addChild(root_1, stream_keyvalExpression.nextTree());

                }
                stream_keyvalExpression.reset();

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
    // $ANTLR end keyvalExpressionList

    public static class keyvalExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start keyvalExpression
    // EolParserRules.g:538:1: keyvalExpression : additiveExpression eq= '=' logicalExpression ;
    public final Etl_EolParserRules.keyvalExpression_return keyvalExpression() throws RecognitionException {
        Etl_EolParserRules.keyvalExpression_return retval = new Etl_EolParserRules.keyvalExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token eq=null;
        Etl_EolParserRules.additiveExpression_return additiveExpression161 = null;

        Etl_EolParserRules.logicalExpression_return logicalExpression162 = null;


        org.eclipse.epsilon.common.parse.AST eq_tree=null;

        try {
            // EolParserRules.g:540:2: ( additiveExpression eq= '=' logicalExpression )
            // EolParserRules.g:540:4: additiveExpression eq= '=' logicalExpression
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pushFollow(FOLLOW_additiveExpression_in_keyvalExpression2481);
            additiveExpression161=additiveExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, additiveExpression161.getTree());
            eq=(Token)match(input,98,FOLLOW_98_in_keyvalExpression2485); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            eq_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(eq);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(eq_tree, root_0);
            }
            pushFollow(FOLLOW_logicalExpression_in_keyvalExpression2488);
            logicalExpression162=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression162.getTree());
            if ( state.backtracking==0 ) {
              eq.setType(KEYVAL);
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
    // $ANTLR end keyvalExpression

    public static class enumLiteral_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start enumLiteral
    // EolParserRules.g:544:1: enumLiteral : (metamodel= NAME '!' )? (typename= packagedType )? '#' label= NAME ;
    public final Etl_EolParserRules.enumLiteral_return enumLiteral() throws RecognitionException {
        Etl_EolParserRules.enumLiteral_return retval = new Etl_EolParserRules.enumLiteral_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token metamodel=null;
        Token label=null;
        Token char_literal163=null;
        Token char_literal164=null;
        Etl_EolParserRules.packagedType_return typename = null;


        org.eclipse.epsilon.common.parse.AST metamodel_tree=null;
        org.eclipse.epsilon.common.parse.AST label_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal163_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal164_tree=null;

        try {
            // EolParserRules.g:545:2: ( (metamodel= NAME '!' )? (typename= packagedType )? '#' label= NAME )
            // EolParserRules.g:545:4: (metamodel= NAME '!' )? (typename= packagedType )? '#' label= NAME
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EolParserRules.g:545:4: (metamodel= NAME '!' )?
            int alt81=2;
            int LA81_0 = input.LA(1);

            if ( (LA81_0==NAME) ) {
                int LA81_1 = input.LA(2);

                if ( (LA81_1==106) ) {
                    alt81=1;
                }
            }
            switch (alt81) {
                case 1 :
                    // EolParserRules.g:545:5: metamodel= NAME '!'
                    {
                    metamodel=(Token)match(input,NAME,FOLLOW_NAME_in_enumLiteral2505); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    metamodel_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(metamodel);
                    adaptor.addChild(root_0, metamodel_tree);
                    }
                    char_literal163=(Token)match(input,106,FOLLOW_106_in_enumLiteral2507); if (state.failed) return retval;

                    }
                    break;

            }

            // EolParserRules.g:545:27: (typename= packagedType )?
            int alt82=2;
            int LA82_0 = input.LA(1);

            if ( (LA82_0==NAME) ) {
                alt82=1;
            }
            switch (alt82) {
                case 1 :
                    // EolParserRules.g:545:28: typename= packagedType
                    {
                    pushFollow(FOLLOW_packagedType_in_enumLiteral2515);
                    typename=packagedType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, typename.getTree());

                    }
                    break;

            }

            char_literal164=(Token)match(input,158,FOLLOW_158_in_enumLiteral2519); if (state.failed) return retval;
            label=(Token)match(input,NAME,FOLLOW_NAME_in_enumLiteral2524); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            label_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(label);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(label_tree, root_0);
            }
            if ( state.backtracking==0 ) {

              		label_tree.token.setType(ENUMERATION_VALUE);
              		label_tree.token.setText('#' + label_tree.token.getText());
              		if (typename != null) {
              			label_tree.token.setText(typename.tree.token.getText() + label_tree.token.getText());
              		}
              		if (metamodel != null) {
              			label_tree.token.setText(metamodel.getText() + '!' + label_tree.token.getText());
              		}
              	
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
    // $ANTLR end enumLiteral

    public static class primitiveExpression_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start primitiveExpression
    // EolParserRules.g:558:1: primitiveExpression : ( literalSequentialCollection | literalMapCollection | literal | ( ( NAME '!' )? ( packagedType )? '#' )=> enumLiteral | featureCall | collectionType | pathName | specialType | logicalExpressionInBrackets | newExpression | variableDeclarationExpression );
    public final Etl_EolParserRules.primitiveExpression_return primitiveExpression() throws RecognitionException {
        Etl_EolParserRules.primitiveExpression_return retval = new Etl_EolParserRules.primitiveExpression_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Etl_EolParserRules.literalSequentialCollection_return literalSequentialCollection165 = null;

        Etl_EolParserRules.literalMapCollection_return literalMapCollection166 = null;

        Etl_EolParserRules.literal_return literal167 = null;

        Etl_EolParserRules.enumLiteral_return enumLiteral168 = null;

        Etl_EolParserRules.featureCall_return featureCall169 = null;

        Etl_EolParserRules.collectionType_return collectionType170 = null;

        Etl_EolParserRules.pathName_return pathName171 = null;

        Etl_EolParserRules.specialType_return specialType172 = null;

        Etl_EolParserRules.logicalExpressionInBrackets_return logicalExpressionInBrackets173 = null;

        Etl_EolParserRules.newExpression_return newExpression174 = null;

        Etl_EolParserRules.variableDeclarationExpression_return variableDeclarationExpression175 = null;



        try {
            // EolParserRules.g:559:2: ( literalSequentialCollection | literalMapCollection | literal | ( ( NAME '!' )? ( packagedType )? '#' )=> enumLiteral | featureCall | collectionType | pathName | specialType | logicalExpressionInBrackets | newExpression | variableDeclarationExpression )
            int alt83=11;
            alt83 = dfa83.predict(input);
            switch (alt83) {
                case 1 :
                    // EolParserRules.g:559:4: literalSequentialCollection
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_literalSequentialCollection_in_primitiveExpression2540);
                    literalSequentialCollection165=literalSequentialCollection();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literalSequentialCollection165.getTree());

                    }
                    break;
                case 2 :
                    // EolParserRules.g:559:34: literalMapCollection
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_literalMapCollection_in_primitiveExpression2544);
                    literalMapCollection166=literalMapCollection();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literalMapCollection166.getTree());

                    }
                    break;
                case 3 :
                    // EolParserRules.g:559:57: literal
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_literal_in_primitiveExpression2548);
                    literal167=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, literal167.getTree());

                    }
                    break;
                case 4 :
                    // EolParserRules.g:560:5: ( ( NAME '!' )? ( packagedType )? '#' )=> enumLiteral
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_enumLiteral_in_primitiveExpression2570);
                    enumLiteral168=enumLiteral();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, enumLiteral168.getTree());

                    }
                    break;
                case 5 :
                    // EolParserRules.g:561:5: featureCall
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_featureCall_in_primitiveExpression2576);
                    featureCall169=featureCall();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, featureCall169.getTree());

                    }
                    break;
                case 6 :
                    // EolParserRules.g:561:19: collectionType
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_collectionType_in_primitiveExpression2580);
                    collectionType170=collectionType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, collectionType170.getTree());

                    }
                    break;
                case 7 :
                    // EolParserRules.g:562:3: pathName
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_pathName_in_primitiveExpression2586);
                    pathName171=pathName();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pathName171.getTree());

                    }
                    break;
                case 8 :
                    // EolParserRules.g:562:14: specialType
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_specialType_in_primitiveExpression2590);
                    specialType172=specialType();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, specialType172.getTree());

                    }
                    break;
                case 9 :
                    // EolParserRules.g:562:28: logicalExpressionInBrackets
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_logicalExpressionInBrackets_in_primitiveExpression2594);
                    logicalExpressionInBrackets173=logicalExpressionInBrackets();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpressionInBrackets173.getTree());

                    }
                    break;
                case 10 :
                    // EolParserRules.g:562:58: newExpression
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_newExpression_in_primitiveExpression2598);
                    newExpression174=newExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, newExpression174.getTree());

                    }
                    break;
                case 11 :
                    // EolParserRules.g:562:74: variableDeclarationExpression
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_variableDeclarationExpression_in_primitiveExpression2602);
                    variableDeclarationExpression175=variableDeclarationExpression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, variableDeclarationExpression175.getTree());

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
    // $ANTLR end primitiveExpression

    public static class logicalExpressionInBrackets_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start logicalExpressionInBrackets
    // EolParserRules.g:565:1: logicalExpressionInBrackets : ob= '(' logicalExpression cb= ')' ;
    public final Etl_EolParserRules.logicalExpressionInBrackets_return logicalExpressionInBrackets() throws RecognitionException {
        Etl_EolParserRules.logicalExpressionInBrackets_return retval = new Etl_EolParserRules.logicalExpressionInBrackets_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token ob=null;
        Token cb=null;
        Etl_EolParserRules.logicalExpression_return logicalExpression176 = null;


        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;

        try {
            // EolParserRules.g:571:2: (ob= '(' logicalExpression cb= ')' )
            // EolParserRules.g:571:4: ob= '(' logicalExpression cb= ')'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            ob=(Token)match(input,101,FOLLOW_101_in_logicalExpressionInBrackets2621); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ob_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ob);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(ob_tree, root_0);
            }
            pushFollow(FOLLOW_logicalExpression_in_logicalExpressionInBrackets2624);
            logicalExpression176=logicalExpression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, logicalExpression176.getTree());
            cb=(Token)match(input,102,FOLLOW_102_in_logicalExpressionInBrackets2628); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              ob.setType(EXPRESSIONINBRACKETS);
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
    // $ANTLR end logicalExpressionInBrackets

    public static class literal_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start literal
    // EolParserRules.g:575:1: literal : ( STRING | INT | FLOAT | BOOLEAN );
    public final Etl_EolParserRules.literal_return literal() throws RecognitionException {
        Etl_EolParserRules.literal_return retval = new Etl_EolParserRules.literal_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token set177=null;

        org.eclipse.epsilon.common.parse.AST set177_tree=null;

        try {
            // EolParserRules.g:576:2: ( STRING | INT | FLOAT | BOOLEAN )
            // EolParserRules.g:
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            set177=(Token)input.LT(1);
            if ( input.LA(1)==FLOAT||input.LA(1)==INT||input.LA(1)==BOOLEAN||input.LA(1)==STRING ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (org.eclipse.epsilon.common.parse.AST)adaptor.create(set177));
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
    // $ANTLR end literal

    // $ANTLR start synpred23_EolParserRules
    public final void synpred23_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:251:4: ( ( '(' typeName ( ',' typeName )* ')' ) )
        // EolParserRules.g:251:4: ( '(' typeName ( ',' typeName )* ')' )
        {
        // EolParserRules.g:251:4: ( '(' typeName ( ',' typeName )* ')' )
        // EolParserRules.g:251:5: '(' typeName ( ',' typeName )* ')'
        {
        match(input,101,FOLLOW_101_in_synpred23_EolParserRules855); if (state.failed) return ;
        pushFollow(FOLLOW_typeName_in_synpred23_EolParserRules860);
        typeName();

        state._fsp--;
        if (state.failed) return ;
        // EolParserRules.g:251:50: ( ',' typeName )*
        loop84:
        do {
            int alt84=2;
            int LA84_0 = input.LA(1);

            if ( (LA84_0==94) ) {
                alt84=1;
            }


            switch (alt84) {
        	case 1 :
        	    // EolParserRules.g:251:51: ',' typeName
        	    {
        	    match(input,94,FOLLOW_94_in_synpred23_EolParserRules865); if (state.failed) return ;
        	    pushFollow(FOLLOW_typeName_in_synpred23_EolParserRules869);
        	    typeName();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop84;
            }
        } while (true);

        match(input,102,FOLLOW_102_in_synpred23_EolParserRules877); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred23_EolParserRules

    // $ANTLR start synpred25_EolParserRules
    public final void synpred25_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:252:4: ( ( '<' typeName ( ',' typeName )* '>' ) )
        // EolParserRules.g:252:4: ( '<' typeName ( ',' typeName )* '>' )
        {
        // EolParserRules.g:252:4: ( '<' typeName ( ',' typeName )* '>' )
        // EolParserRules.g:252:5: '<' typeName ( ',' typeName )* '>'
        {
        match(input,108,FOLLOW_108_in_synpred25_EolParserRules889); if (state.failed) return ;
        pushFollow(FOLLOW_typeName_in_synpred25_EolParserRules894);
        typeName();

        state._fsp--;
        if (state.failed) return ;
        // EolParserRules.g:252:50: ( ',' typeName )*
        loop85:
        do {
            int alt85=2;
            int LA85_0 = input.LA(1);

            if ( (LA85_0==94) ) {
                alt85=1;
            }


            switch (alt85) {
        	case 1 :
        	    // EolParserRules.g:252:51: ',' typeName
        	    {
        	    match(input,94,FOLLOW_94_in_synpred25_EolParserRules899); if (state.failed) return ;
        	    pushFollow(FOLLOW_typeName_in_synpred25_EolParserRules903);
        	    typeName();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop85;
            }
        } while (true);

        match(input,109,FOLLOW_109_in_synpred25_EolParserRules911); if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred25_EolParserRules

    // $ANTLR start synpred26_EolParserRules
    public final void synpred26_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:257:4: ( statementA )
        // EolParserRules.g:257:4: statementA
        {
        pushFollow(FOLLOW_statementA_in_synpred26_EolParserRules930);
        statementA();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_EolParserRules

    // $ANTLR start synpred27_EolParserRules
    public final void synpred27_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:261:4: ( assignmentStatement )
        // EolParserRules.g:261:4: assignmentStatement
        {
        pushFollow(FOLLOW_assignmentStatement_in_synpred27_EolParserRules945);
        assignmentStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_EolParserRules

    // $ANTLR start synpred28_EolParserRules
    public final void synpred28_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:261:26: ( expressionStatement )
        // EolParserRules.g:261:26: expressionStatement
        {
        pushFollow(FOLLOW_expressionStatement_in_synpred28_EolParserRules949);
        expressionStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred28_EolParserRules

    // $ANTLR start synpred42_EolParserRules
    public final void synpred42_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:279:66: ( elseStatement )
        // EolParserRules.g:279:66: elseStatement
        {
        pushFollow(FOLLOW_elseStatement_in_synpred42_EolParserRules1072);
        elseStatement();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred42_EolParserRules

    // $ANTLR start synpred51_EolParserRules
    public final void synpred51_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:372:22: ( NAME ( ',' NAME )* )
        // EolParserRules.g:372:22: NAME ( ',' NAME )*
        {
        match(input,NAME,FOLLOW_NAME_in_synpred51_EolParserRules1483); if (state.failed) return ;
        // EolParserRules.g:372:27: ( ',' NAME )*
        loop86:
        do {
            int alt86=2;
            int LA86_0 = input.LA(1);

            if ( (LA86_0==94) ) {
                alt86=1;
            }


            switch (alt86) {
        	case 1 :
        	    // EolParserRules.g:372:28: ',' NAME
        	    {
        	    match(input,94,FOLLOW_94_in_synpred51_EolParserRules1486); if (state.failed) return ;
        	    match(input,NAME,FOLLOW_NAME_in_synpred51_EolParserRules1488); if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop86;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred51_EolParserRules

    // $ANTLR start synpred58_EolParserRules
    public final void synpred58_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:389:5: ( ( postfixExpression '=' logicalExpression ) )
        // EolParserRules.g:389:5: ( postfixExpression '=' logicalExpression )
        {
        // EolParserRules.g:389:5: ( postfixExpression '=' logicalExpression )
        // EolParserRules.g:389:6: postfixExpression '=' logicalExpression
        {
        pushFollow(FOLLOW_postfixExpression_in_synpred58_EolParserRules1589);
        postfixExpression();

        state._fsp--;
        if (state.failed) return ;
        match(input,98,FOLLOW_98_in_synpred58_EolParserRules1593); if (state.failed) return ;
        pushFollow(FOLLOW_logicalExpression_in_synpred58_EolParserRules1596);
        logicalExpression();

        state._fsp--;
        if (state.failed) return ;

        }


        }
    }
    // $ANTLR end synpred58_EolParserRules

    // $ANTLR start synpred73_EolParserRules
    public final void synpred73_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:401:24: ( ( ( '==' | '=' | '!=' | '?:' ) relationalExpression | ( '>' | '<' | '>=' | '<=' | '<>' ) additiveExpression ) )
        // EolParserRules.g:401:24: ( ( '==' | '=' | '!=' | '?:' ) relationalExpression | ( '>' | '<' | '>=' | '<=' | '<>' ) additiveExpression )
        {
        // EolParserRules.g:401:24: ( ( '==' | '=' | '!=' | '?:' ) relationalExpression | ( '>' | '<' | '>=' | '<=' | '<>' ) additiveExpression )
        int alt88=2;
        int LA88_0 = input.LA(1);

        if ( (LA88_0==98||(LA88_0>=138 && LA88_0<=140)) ) {
            alt88=1;
        }
        else if ( ((LA88_0>=108 && LA88_0<=109)||(LA88_0>=141 && LA88_0<=143)) ) {
            alt88=2;
        }
        else {
            if (state.backtracking>0) {state.failed=true; return ;}
            NoViableAltException nvae =
                new NoViableAltException("", 88, 0, input);

            throw nvae;
        }
        switch (alt88) {
            case 1 :
                // EolParserRules.g:401:25: ( '==' | '=' | '!=' | '?:' ) relationalExpression
                {
                if ( input.LA(1)==98||(input.LA(1)>=138 && input.LA(1)<=140) ) {
                    input.consume();
                    state.errorRecovery=false;state.failed=false;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    MismatchedSetException mse = new MismatchedSetException(null,input);
                    throw mse;
                }

                pushFollow(FOLLOW_relationalExpression_in_synpred73_EolParserRules1723);
                relationalExpression();

                state._fsp--;
                if (state.failed) return ;

                }
                break;
            case 2 :
                // EolParserRules.g:402:24: ( '>' | '<' | '>=' | '<=' | '<>' ) additiveExpression
                {
                if ( (input.LA(1)>=108 && input.LA(1)<=109)||(input.LA(1)>=141 && input.LA(1)<=143) ) {
                    input.consume();
                    state.errorRecovery=false;state.failed=false;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return ;}
                    MismatchedSetException mse = new MismatchedSetException(null,input);
                    throw mse;
                }

                pushFollow(FOLLOW_additiveExpression_in_synpred73_EolParserRules1777);
                additiveExpression();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred73_EolParserRules

    // $ANTLR start synpred102_EolParserRules
    public final void synpred102_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:491:30: ( ':' ( 'new' )? typeName ( parameterList )? )
        // EolParserRules.g:491:30: ':' ( 'new' )? typeName ( parameterList )?
        {
        match(input,103,FOLLOW_103_in_synpred102_EolParserRules2263); if (state.failed) return ;
        // EolParserRules.g:491:36: ( 'new' )?
        int alt92=2;
        int LA92_0 = input.LA(1);

        if ( (LA92_0==155) ) {
            alt92=1;
        }
        switch (alt92) {
            case 1 :
                // EolParserRules.g:0:0: 'new'
                {
                match(input,155,FOLLOW_155_in_synpred102_EolParserRules2268); if (state.failed) return ;

                }
                break;

        }

        pushFollow(FOLLOW_typeName_in_synpred102_EolParserRules2274);
        typeName();

        state._fsp--;
        if (state.failed) return ;
        // EolParserRules.g:491:81: ( parameterList )?
        int alt93=2;
        int LA93_0 = input.LA(1);

        if ( (LA93_0==101) ) {
            alt93=1;
        }
        switch (alt93) {
            case 1 :
                // EolParserRules.g:0:0: parameterList
                {
                pushFollow(FOLLOW_parameterList_in_synpred102_EolParserRules2278);
                parameterList();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }


        }
    }
    // $ANTLR end synpred102_EolParserRules

    // $ANTLR start synpred106_EolParserRules
    public final void synpred106_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:518:4: ( expressionRange )
        // EolParserRules.g:518:4: expressionRange
        {
        pushFollow(FOLLOW_expressionRange_in_synpred106_EolParserRules2391);
        expressionRange();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred106_EolParserRules

    // $ANTLR start synpred112_EolParserRules
    public final void synpred112_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:559:4: ( literalSequentialCollection )
        // EolParserRules.g:559:4: literalSequentialCollection
        {
        pushFollow(FOLLOW_literalSequentialCollection_in_synpred112_EolParserRules2540);
        literalSequentialCollection();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred112_EolParserRules

    // $ANTLR start synpred113_EolParserRules
    public final void synpred113_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:559:34: ( literalMapCollection )
        // EolParserRules.g:559:34: literalMapCollection
        {
        pushFollow(FOLLOW_literalMapCollection_in_synpred113_EolParserRules2544);
        literalMapCollection();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred113_EolParserRules

    // $ANTLR start synpred117_EolParserRules
    public final void synpred117_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:560:5: ( ( NAME '!' )? ( packagedType )? '#' )
        // EolParserRules.g:560:6: ( NAME '!' )? ( packagedType )? '#'
        {
        // EolParserRules.g:560:6: ( NAME '!' )?
        int alt94=2;
        int LA94_0 = input.LA(1);

        if ( (LA94_0==NAME) ) {
            int LA94_1 = input.LA(2);

            if ( (LA94_1==106) ) {
                alt94=1;
            }
        }
        switch (alt94) {
            case 1 :
                // EolParserRules.g:560:7: NAME '!'
                {
                match(input,NAME,FOLLOW_NAME_in_synpred117_EolParserRules2556); if (state.failed) return ;
                match(input,106,FOLLOW_106_in_synpred117_EolParserRules2558); if (state.failed) return ;

                }
                break;

        }

        // EolParserRules.g:560:18: ( packagedType )?
        int alt95=2;
        int LA95_0 = input.LA(1);

        if ( (LA95_0==NAME) ) {
            alt95=1;
        }
        switch (alt95) {
            case 1 :
                // EolParserRules.g:0:0: packagedType
                {
                pushFollow(FOLLOW_packagedType_in_synpred117_EolParserRules2562);
                packagedType();

                state._fsp--;
                if (state.failed) return ;

                }
                break;

        }

        match(input,158,FOLLOW_158_in_synpred117_EolParserRules2565); if (state.failed) return ;

        }
    }
    // $ANTLR end synpred117_EolParserRules

    // $ANTLR start synpred118_EolParserRules
    public final void synpred118_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:561:5: ( featureCall )
        // EolParserRules.g:561:5: featureCall
        {
        pushFollow(FOLLOW_featureCall_in_synpred118_EolParserRules2576);
        featureCall();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred118_EolParserRules

    // $ANTLR start synpred119_EolParserRules
    public final void synpred119_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:561:19: ( collectionType )
        // EolParserRules.g:561:19: collectionType
        {
        pushFollow(FOLLOW_collectionType_in_synpred119_EolParserRules2580);
        collectionType();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred119_EolParserRules

    // $ANTLR start synpred120_EolParserRules
    public final void synpred120_EolParserRules_fragment() throws RecognitionException {   
        // EolParserRules.g:562:3: ( pathName )
        // EolParserRules.g:562:3: pathName
        {
        pushFollow(FOLLOW_pathName_in_synpred120_EolParserRules2586);
        pathName();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred120_EolParserRules

    // Delegated rules

    public final boolean synpred112_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred112_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred117_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred117_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred73_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred73_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred23_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred23_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred102_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred102_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred42_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred42_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred51_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred51_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred119_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred119_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred106_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred106_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred25_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred25_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred26_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred58_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred58_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred118_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred118_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred113_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred113_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred28_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred28_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred120_EolParserRules() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred120_EolParserRules_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA21 dfa21 = new DFA21(this);
    protected DFA22 dfa22 = new DFA22(this);
    protected DFA23 dfa23 = new DFA23(this);
    protected DFA39 dfa39 = new DFA39(this);
    protected DFA46 dfa46 = new DFA46(this);
    protected DFA58 dfa58 = new DFA58(this);
    protected DFA73 dfa73 = new DFA73(this);
    protected DFA77 dfa77 = new DFA77(this);
    protected DFA83 dfa83 = new DFA83(this);
    static final String DFA21_eotS =
        "\114\uffff";
    static final String DFA21_eofS =
        "\1\3\113\uffff";
    static final String DFA21_minS =
        "\1\4\2\0\111\uffff";
    static final String DFA21_maxS =
        "\1\u00a5\2\0\111\uffff";
    static final String DFA21_acceptS =
        "\3\uffff\1\3\106\uffff\1\1\1\2";
    static final String DFA21_specialS =
        "\1\uffff\1\0\1\1\111\uffff}>";
    static final String[] DFA21_transitionS = {
            "\1\3\3\uffff\6\3\1\uffff\1\3\1\uffff\3\3\3\uffff\1\3\3\uffff"+
            "\1\3\100\uffff\1\3\1\uffff\1\3\1\uffff\5\3\1\1\2\3\1\uffff\1"+
            "\3\2\uffff\1\2\4\3\2\uffff\56\3\1\uffff\2\3\1\uffff\1\3",
            "\1\uffff",
            "\1\uffff",
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
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
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
            return "251:3: ( (op= '(' tn= typeName ( ',' tn= typeName )* cp= ')' ) | (op= '<' tn= typeName ( ',' tn= typeName )* cp= '>' ) )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA21_1 = input.LA(1);

                         
                        int index21_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred23_EolParserRules()) ) {s = 74;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index21_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA21_2 = input.LA(1);

                         
                        int index21_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred25_EolParserRules()) ) {s = 75;}

                        else if ( (true) ) {s = 3;}

                         
                        input.seek(index21_2);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 21, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA22_eotS =
        "\31\uffff";
    static final String DFA22_eofS =
        "\31\uffff";
    static final String DFA22_minS =
        "\1\4\20\uffff\1\0\7\uffff";
    static final String DFA22_maxS =
        "\1\u009e\20\uffff\1\0\7\uffff";
    static final String DFA22_acceptS =
        "\1\uffff\1\1\21\uffff\1\2\5\uffff";
    static final String DFA22_specialS =
        "\21\uffff\1\0\7\uffff}>";
    static final String[] DFA22_transitionS = {
            "\1\1\3\uffff\1\1\4\uffff\1\1\1\uffff\1\1\1\uffff\3\1\3\uffff"+
            "\1\1\115\uffff\1\1\10\uffff\1\1\1\uffff\1\1\2\uffff\1\1\1\uffff"+
            "\1\1\1\21\2\23\1\1\4\23\23\uffff\1\1\2\uffff\1\1\6\uffff\4\1",
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
            "",
            "",
            "",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA22_eot = DFA.unpackEncodedString(DFA22_eotS);
    static final short[] DFA22_eof = DFA.unpackEncodedString(DFA22_eofS);
    static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars(DFA22_minS);
    static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars(DFA22_maxS);
    static final short[] DFA22_accept = DFA.unpackEncodedString(DFA22_acceptS);
    static final short[] DFA22_special = DFA.unpackEncodedString(DFA22_specialS);
    static final short[][] DFA22_transition;

    static {
        int numStates = DFA22_transitionS.length;
        DFA22_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
        }
    }

    class DFA22 extends DFA {

        public DFA22(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 22;
            this.eot = DFA22_eot;
            this.eof = DFA22_eof;
            this.min = DFA22_min;
            this.max = DFA22_max;
            this.accept = DFA22_accept;
            this.special = DFA22_special;
            this.transition = DFA22_transition;
        }
        public String getDescription() {
            return "256:1: statement : ( statementA | statementB );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA22_17 = input.LA(1);

                         
                        int index22_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred26_EolParserRules()) ) {s = 1;}

                        else if ( (true) ) {s = 19;}

                         
                        input.seek(index22_17);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 22, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA23_eotS =
        "\25\uffff";
    static final String DFA23_eofS =
        "\25\uffff";
    static final String DFA23_minS =
        "\1\4\14\0\10\uffff";
    static final String DFA23_maxS =
        "\1\u009e\14\0\10\uffff";
    static final String DFA23_acceptS =
        "\15\uffff\1\3\1\4\1\5\1\6\1\7\1\10\1\1\1\2";
    static final String DFA23_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\10"+
        "\uffff}>";
    static final String[] DFA23_transitionS = {
            "\1\5\3\uffff\1\5\4\uffff\1\5\1\uffff\1\5\1\uffff\1\3\1\4\1\10"+
            "\3\uffff\1\6\115\uffff\1\11\10\uffff\1\16\1\uffff\1\20\2\uffff"+
            "\1\15\1\uffff\1\17\1\21\2\uffff\1\22\27\uffff\1\2\2\uffff\1"+
            "\1\6\uffff\1\12\1\13\1\14\1\7",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            ""
    };

    static final short[] DFA23_eot = DFA.unpackEncodedString(DFA23_eotS);
    static final short[] DFA23_eof = DFA.unpackEncodedString(DFA23_eofS);
    static final char[] DFA23_min = DFA.unpackEncodedStringToUnsignedChars(DFA23_minS);
    static final char[] DFA23_max = DFA.unpackEncodedStringToUnsignedChars(DFA23_maxS);
    static final short[] DFA23_accept = DFA.unpackEncodedString(DFA23_acceptS);
    static final short[] DFA23_special = DFA.unpackEncodedString(DFA23_specialS);
    static final short[][] DFA23_transition;

    static {
        int numStates = DFA23_transitionS.length;
        DFA23_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA23_transition[i] = DFA.unpackEncodedString(DFA23_transitionS[i]);
        }
    }

    class DFA23 extends DFA {

        public DFA23(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 23;
            this.eot = DFA23_eot;
            this.eof = DFA23_eof;
            this.min = DFA23_min;
            this.max = DFA23_max;
            this.accept = DFA23_accept;
            this.special = DFA23_special;
            this.transition = DFA23_transition;
        }
        public String getDescription() {
            return "260:1: statementA : ( assignmentStatement | expressionStatement | forStatement | ifStatement | whileStatement | switchStatement | returnStatement | breakStatement );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA23_1 = input.LA(1);

                         
                        int index23_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA23_2 = input.LA(1);

                         
                        int index23_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA23_3 = input.LA(1);

                         
                        int index23_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA23_4 = input.LA(1);

                         
                        int index23_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA23_5 = input.LA(1);

                         
                        int index23_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA23_6 = input.LA(1);

                         
                        int index23_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA23_7 = input.LA(1);

                         
                        int index23_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA23_8 = input.LA(1);

                         
                        int index23_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA23_9 = input.LA(1);

                         
                        int index23_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA23_10 = input.LA(1);

                         
                        int index23_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA23_11 = input.LA(1);

                         
                        int index23_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA23_12 = input.LA(1);

                         
                        int index23_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred27_EolParserRules()) ) {s = 19;}

                        else if ( (synpred28_EolParserRules()) ) {s = 20;}

                         
                        input.seek(index23_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 23, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA39_eotS =
        "\16\uffff";
    static final String DFA39_eofS =
        "\16\uffff";
    static final String DFA39_minS =
        "\1\4\12\0\3\uffff";
    static final String DFA39_maxS =
        "\1\u009e\12\0\3\uffff";
    static final String DFA39_acceptS =
        "\13\uffff\1\2\1\uffff\1\1";
    static final String DFA39_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\3\uffff}>";
    static final String[] DFA39_transitionS = {
            "\1\3\3\uffff\1\3\4\uffff\1\3\1\uffff\1\3\1\uffff\1\1\1\2\1\6"+
            "\3\uffff\1\4\115\uffff\1\7\53\uffff\1\13\2\uffff\1\13\6\uffff"+
            "\1\10\1\11\1\12\1\5",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            "",
            ""
    };

    static final short[] DFA39_eot = DFA.unpackEncodedString(DFA39_eotS);
    static final short[] DFA39_eof = DFA.unpackEncodedString(DFA39_eofS);
    static final char[] DFA39_min = DFA.unpackEncodedStringToUnsignedChars(DFA39_minS);
    static final char[] DFA39_max = DFA.unpackEncodedStringToUnsignedChars(DFA39_maxS);
    static final short[] DFA39_accept = DFA.unpackEncodedString(DFA39_acceptS);
    static final short[] DFA39_special = DFA.unpackEncodedString(DFA39_specialS);
    static final short[][] DFA39_transition;

    static {
        int numStates = DFA39_transitionS.length;
        DFA39_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA39_transition[i] = DFA.unpackEncodedString(DFA39_transitionS[i]);
        }
    }

    class DFA39 extends DFA {

        public DFA39(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 39;
            this.eot = DFA39_eot;
            this.eof = DFA39_eof;
            this.min = DFA39_min;
            this.max = DFA39_max;
            this.accept = DFA39_accept;
            this.special = DFA39_special;
            this.transition = DFA39_transition;
        }
        public String getDescription() {
            return "389:4: ( ( postfixExpression op= '=' logicalExpression ) | logicalExpression )";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA39_1 = input.LA(1);

                         
                        int index39_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA39_2 = input.LA(1);

                         
                        int index39_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA39_3 = input.LA(1);

                         
                        int index39_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA39_4 = input.LA(1);

                         
                        int index39_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA39_5 = input.LA(1);

                         
                        int index39_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA39_6 = input.LA(1);

                         
                        int index39_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA39_7 = input.LA(1);

                         
                        int index39_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA39_8 = input.LA(1);

                         
                        int index39_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA39_9 = input.LA(1);

                         
                        int index39_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA39_10 = input.LA(1);

                         
                        int index39_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred58_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 11;}

                         
                        input.seek(index39_10);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 39, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA46_eotS =
        "\14\uffff";
    static final String DFA46_eofS =
        "\1\1\13\uffff";
    static final String DFA46_minS =
        "\1\4\1\uffff\11\0\1\uffff";
    static final String DFA46_maxS =
        "\1\u00a3\1\uffff\11\0\1\uffff";
    static final String DFA46_acceptS =
        "\1\uffff\1\2\11\uffff\1\1";
    static final String DFA46_specialS =
        "\2\uffff\1\4\1\10\1\5\1\2\1\0\1\6\1\1\1\7\1\3\1\uffff}>";
    static final String[] DFA46_transitionS = {
            "\1\1\3\uffff\1\1\1\uffff\1\1\2\uffff\1\1\1\uffff\1\1\1\uffff"+
            "\3\1\3\uffff\1\1\3\uffff\1\1\100\uffff\1\1\1\uffff\1\1\1\uffff"+
            "\2\1\1\3\5\1\1\uffff\1\1\2\uffff\1\7\1\6\3\1\2\uffff\1\1\1\uffff"+
            "\25\1\1\2\1\4\1\5\1\10\1\11\1\12\1\uffff\1\1\2\uffff\1\1\3\uffff"+
            "\1\1\2\uffff\6\1\1\uffff\2\1",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            ""
    };

    static final short[] DFA46_eot = DFA.unpackEncodedString(DFA46_eotS);
    static final short[] DFA46_eof = DFA.unpackEncodedString(DFA46_eofS);
    static final char[] DFA46_min = DFA.unpackEncodedStringToUnsignedChars(DFA46_minS);
    static final char[] DFA46_max = DFA.unpackEncodedStringToUnsignedChars(DFA46_maxS);
    static final short[] DFA46_accept = DFA.unpackEncodedString(DFA46_acceptS);
    static final short[] DFA46_special = DFA.unpackEncodedString(DFA46_specialS);
    static final short[][] DFA46_transition;

    static {
        int numStates = DFA46_transitionS.length;
        DFA46_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA46_transition[i] = DFA.unpackEncodedString(DFA46_transitionS[i]);
        }
    }

    class DFA46 extends DFA {

        public DFA46(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 46;
            this.eot = DFA46_eot;
            this.eof = DFA46_eof;
            this.min = DFA46_min;
            this.max = DFA46_max;
            this.accept = DFA46_accept;
            this.special = DFA46_special;
            this.transition = DFA46_transition;
        }
        public String getDescription() {
            return "()* loopback of 401:23: ( ( (op= '==' | op= '=' | op= '!=' | op= '?:' ) relationalExpression | (op= '>' | op= '<' | op= '>=' | op= '<=' | op= '<>' ) additiveExpression ) )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA46_6 = input.LA(1);

                         
                        int index46_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_6);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA46_8 = input.LA(1);

                         
                        int index46_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA46_5 = input.LA(1);

                         
                        int index46_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_5);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA46_10 = input.LA(1);

                         
                        int index46_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_10);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA46_2 = input.LA(1);

                         
                        int index46_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_2);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA46_4 = input.LA(1);

                         
                        int index46_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_4);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA46_7 = input.LA(1);

                         
                        int index46_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA46_9 = input.LA(1);

                         
                        int index46_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_9);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA46_3 = input.LA(1);

                         
                        int index46_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred73_EolParserRules()) ) {s = 11;}

                        else if ( (true) ) {s = 1;}

                         
                        input.seek(index46_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 46, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA58_eotS =
        "\12\uffff";
    static final String DFA58_eofS =
        "\1\uffff\1\3\10\uffff";
    static final String DFA58_minS =
        "\1\27\2\4\1\uffff\1\11\1\4\1\uffff\1\4\2\11";
    static final String DFA58_maxS =
        "\1\27\1\u00a3\1\u009e\1\uffff\2\u009e\1\uffff\3\u009e";
    static final String DFA58_acceptS =
        "\3\uffff\1\1\2\uffff\1\2\3\uffff";
    static final String DFA58_specialS =
        "\12\uffff}>";
    static final String[] DFA58_transitionS = {
            "\1\1",
            "\1\3\3\uffff\6\3\1\uffff\1\3\1\uffff\3\3\3\uffff\1\3\3\uffff"+
            "\1\3\100\uffff\1\3\1\uffff\1\3\1\uffff\5\3\1\2\2\3\1\uffff\1"+
            "\3\2\uffff\5\3\2\uffff\1\3\1\uffff\44\3\2\uffff\6\3\1\uffff"+
            "\2\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\1\uffff\1\3\1\uffff\3\3\3\uffff"+
            "\1\4\106\uffff\1\3\6\uffff\1\5\1\3\52\uffff\1\3\2\uffff\1\3"+
            "\2\uffff\1\6\1\uffff\2\6\4\3",
            "",
            "\1\3\1\uffff\2\3\121\uffff\1\7\3\uffff\1\3\2\uffff\2\3\1\6"+
            "\2\uffff\4\3\27\uffff\17\3\1\uffff\3\3\1\uffff\2\6\3\uffff\1"+
            "\3",
            "\1\3\3\uffff\1\3\4\uffff\1\3\1\uffff\1\3\1\uffff\3\3\3\uffff"+
            "\1\10\115\uffff\1\3\53\uffff\1\3\2\uffff\1\3\4\uffff\2\6\4\3",
            "",
            "\1\3\3\uffff\1\3\4\uffff\1\3\1\uffff\1\3\1\uffff\3\3\3\uffff"+
            "\1\11\115\uffff\2\3\52\uffff\1\3\2\uffff\1\3\6\uffff\4\3",
            "\1\3\1\uffff\2\3\121\uffff\1\6\3\uffff\1\3\2\uffff\2\3\1\6"+
            "\2\uffff\4\3\27\uffff\17\3\1\uffff\3\3\1\uffff\2\6\3\uffff\1"+
            "\3",
            "\1\3\1\uffff\2\3\121\uffff\1\7\3\uffff\1\3\2\uffff\2\3\1\6"+
            "\2\uffff\4\3\27\uffff\17\3\1\uffff\3\3\1\uffff\2\6\3\uffff\1"+
            "\3"
    };

    static final short[] DFA58_eot = DFA.unpackEncodedString(DFA58_eotS);
    static final short[] DFA58_eof = DFA.unpackEncodedString(DFA58_eofS);
    static final char[] DFA58_min = DFA.unpackEncodedStringToUnsignedChars(DFA58_minS);
    static final char[] DFA58_max = DFA.unpackEncodedStringToUnsignedChars(DFA58_maxS);
    static final short[] DFA58_accept = DFA.unpackEncodedString(DFA58_acceptS);
    static final short[] DFA58_special = DFA.unpackEncodedString(DFA58_specialS);
    static final short[][] DFA58_transition;

    static {
        int numStates = DFA58_transitionS.length;
        DFA58_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA58_transition[i] = DFA.unpackEncodedString(DFA58_transitionS[i]);
        }
    }

    class DFA58 extends DFA {

        public DFA58(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 58;
            this.eot = DFA58_eot;
            this.eof = DFA58_eof;
            this.min = DFA58_min;
            this.max = DFA58_max;
            this.accept = DFA58_accept;
            this.special = DFA58_special;
            this.transition = DFA58_transition;
        }
        public String getDescription() {
            return "435:1: featureCall : ( simpleFeatureCall | complexFeatureCall );";
        }
    }
    static final String DFA73_eotS =
        "\22\uffff";
    static final String DFA73_eofS =
        "\2\2\20\uffff";
    static final String DFA73_minS =
        "\2\4\1\uffff\3\0\1\145\1\21\1\uffff\1\17\2\0\1\145\1\146\1\17\1"+
        "\0\1\146\1\0";
    static final String DFA73_maxS =
        "\1\u00a3\1\u009e\1\uffff\3\0\1\145\1\27\1\uffff\1\17\2\0\1\145\1"+
        "\146\1\17\1\0\1\146\1\0";
    static final String DFA73_acceptS =
        "\2\uffff\1\2\5\uffff\1\1\11\uffff";
    static final String DFA73_specialS =
        "\3\uffff\1\6\1\3\1\0\4\uffff\1\4\1\2\3\uffff\1\5\1\uffff\1\1}>";
    static final String[] DFA73_transitionS = {
            "\1\2\3\uffff\6\2\1\uffff\1\2\1\uffff\3\2\3\uffff\1\2\3\uffff"+
            "\1\2\100\uffff\1\2\1\uffff\1\2\1\uffff\7\2\1\1\1\uffff\1\2\2"+
            "\uffff\5\2\2\uffff\1\2\1\uffff\44\2\2\uffff\6\2\1\uffff\2\2",
            "\1\2\3\uffff\1\2\4\uffff\1\2\1\uffff\1\2\1\uffff\1\3\1\4\1"+
            "\6\3\uffff\1\5\110\uffff\2\2\3\uffff\1\2\10\uffff\1\2\1\uffff"+
            "\4\2\1\uffff\11\2\23\uffff\1\2\2\uffff\1\2\6\uffff\1\7\3\2",
            "",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\11",
            "\2\13\1\14\3\uffff\1\12",
            "",
            "\1\15",
            "\1\uffff",
            "\1\uffff",
            "\1\16",
            "\1\17",
            "\1\20",
            "\1\uffff",
            "\1\21",
            "\1\uffff"
    };

    static final short[] DFA73_eot = DFA.unpackEncodedString(DFA73_eotS);
    static final short[] DFA73_eof = DFA.unpackEncodedString(DFA73_eofS);
    static final char[] DFA73_min = DFA.unpackEncodedStringToUnsignedChars(DFA73_minS);
    static final char[] DFA73_max = DFA.unpackEncodedStringToUnsignedChars(DFA73_maxS);
    static final short[] DFA73_accept = DFA.unpackEncodedString(DFA73_acceptS);
    static final short[] DFA73_special = DFA.unpackEncodedString(DFA73_specialS);
    static final short[][] DFA73_transition;

    static {
        int numStates = DFA73_transitionS.length;
        DFA73_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA73_transition[i] = DFA.unpackEncodedString(DFA73_transitionS[i]);
        }
    }

    class DFA73 extends DFA {

        public DFA73(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 73;
            this.eot = DFA73_eot;
            this.eof = DFA73_eof;
            this.min = DFA73_min;
            this.max = DFA73_max;
            this.accept = DFA73_accept;
            this.special = DFA73_special;
            this.transition = DFA73_transition;
        }
        public String getDescription() {
            return "491:29: ( ':' (n= 'new' )? t= typeName ( parameterList )? )?";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA73_5 = input.LA(1);

                         
                        int index73_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_EolParserRules()) ) {s = 8;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index73_5);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA73_17 = input.LA(1);

                         
                        int index73_17 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_EolParserRules()) ) {s = 8;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index73_17);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA73_11 = input.LA(1);

                         
                        int index73_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_EolParserRules()) ) {s = 8;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index73_11);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA73_4 = input.LA(1);

                         
                        int index73_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_EolParserRules()) ) {s = 8;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index73_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA73_10 = input.LA(1);

                         
                        int index73_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_EolParserRules()) ) {s = 8;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index73_10);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA73_15 = input.LA(1);

                         
                        int index73_15 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_EolParserRules()) ) {s = 8;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index73_15);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA73_3 = input.LA(1);

                         
                        int index73_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred102_EolParserRules()) ) {s = 8;}

                        else if ( (true) ) {s = 2;}

                         
                        input.seek(index73_3);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 73, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA77_eotS =
        "\17\uffff";
    static final String DFA77_eofS =
        "\17\uffff";
    static final String DFA77_minS =
        "\1\4\14\0\2\uffff";
    static final String DFA77_maxS =
        "\1\u009e\14\0\2\uffff";
    static final String DFA77_acceptS =
        "\15\uffff\1\1\1\2";
    static final String DFA77_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13\2\uffff}>";
    static final String[] DFA77_transitionS = {
            "\1\5\3\uffff\1\5\4\uffff\1\5\1\uffff\1\5\1\uffff\1\3\1\4\1\10"+
            "\3\uffff\1\6\115\uffff\1\11\53\uffff\1\2\2\uffff\1\1\6\uffff"+
            "\1\12\1\13\1\14\1\7",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA77_eot = DFA.unpackEncodedString(DFA77_eotS);
    static final short[] DFA77_eof = DFA.unpackEncodedString(DFA77_eofS);
    static final char[] DFA77_min = DFA.unpackEncodedStringToUnsignedChars(DFA77_minS);
    static final char[] DFA77_max = DFA.unpackEncodedStringToUnsignedChars(DFA77_maxS);
    static final short[] DFA77_accept = DFA.unpackEncodedString(DFA77_acceptS);
    static final short[] DFA77_special = DFA.unpackEncodedString(DFA77_specialS);
    static final short[][] DFA77_transition;

    static {
        int numStates = DFA77_transitionS.length;
        DFA77_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA77_transition[i] = DFA.unpackEncodedString(DFA77_transitionS[i]);
        }
    }

    class DFA77 extends DFA {

        public DFA77(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 77;
            this.eot = DFA77_eot;
            this.eof = DFA77_eof;
            this.min = DFA77_min;
            this.max = DFA77_max;
            this.accept = DFA77_accept;
            this.special = DFA77_special;
            this.transition = DFA77_transition;
        }
        public String getDescription() {
            return "517:1: expressionListOrRange : ( expressionRange | expressionList );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA77_1 = input.LA(1);

                         
                        int index77_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA77_2 = input.LA(1);

                         
                        int index77_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA77_3 = input.LA(1);

                         
                        int index77_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA77_4 = input.LA(1);

                         
                        int index77_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA77_5 = input.LA(1);

                         
                        int index77_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA77_6 = input.LA(1);

                         
                        int index77_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA77_7 = input.LA(1);

                         
                        int index77_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_7);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA77_8 = input.LA(1);

                         
                        int index77_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_8);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA77_9 = input.LA(1);

                         
                        int index77_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_9);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA77_10 = input.LA(1);

                         
                        int index77_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_10);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA77_11 = input.LA(1);

                         
                        int index77_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_11);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA77_12 = input.LA(1);

                         
                        int index77_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred106_EolParserRules()) ) {s = 13;}

                        else if ( (true) ) {s = 14;}

                         
                        input.seek(index77_12);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 77, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA83_eotS =
        "\20\uffff";
    static final String DFA83_eofS =
        "\20\uffff";
    static final String DFA83_minS =
        "\1\4\2\0\1\uffff\1\0\13\uffff";
    static final String DFA83_maxS =
        "\1\u009e\2\0\1\uffff\1\0\13\uffff";
    static final String DFA83_acceptS =
        "\3\uffff\1\3\1\uffff\1\4\1\10\1\11\1\12\1\13\1\uffff\1\1\1\6\1\2"+
        "\1\5\1\7";
    static final String DFA83_specialS =
        "\1\0\1\1\1\2\1\uffff\1\3\13\uffff}>";
    static final String[] DFA83_transitionS = {
            "\1\3\3\uffff\1\3\4\uffff\1\3\1\uffff\1\3\1\uffff\1\1\1\2\1\6"+
            "\3\uffff\1\4\115\uffff\1\7\65\uffff\1\10\2\11\1\5",
            "\1\uffff",
            "\1\uffff",
            "",
            "\1\uffff",
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
            ""
    };

    static final short[] DFA83_eot = DFA.unpackEncodedString(DFA83_eotS);
    static final short[] DFA83_eof = DFA.unpackEncodedString(DFA83_eofS);
    static final char[] DFA83_min = DFA.unpackEncodedStringToUnsignedChars(DFA83_minS);
    static final char[] DFA83_max = DFA.unpackEncodedStringToUnsignedChars(DFA83_maxS);
    static final short[] DFA83_accept = DFA.unpackEncodedString(DFA83_acceptS);
    static final short[] DFA83_special = DFA.unpackEncodedString(DFA83_specialS);
    static final short[][] DFA83_transition;

    static {
        int numStates = DFA83_transitionS.length;
        DFA83_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA83_transition[i] = DFA.unpackEncodedString(DFA83_transitionS[i]);
        }
    }

    class DFA83 extends DFA {

        public DFA83(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 83;
            this.eot = DFA83_eot;
            this.eof = DFA83_eof;
            this.min = DFA83_min;
            this.max = DFA83_max;
            this.accept = DFA83_accept;
            this.special = DFA83_special;
            this.transition = DFA83_transition;
        }
        public String getDescription() {
            return "558:1: primitiveExpression : ( literalSequentialCollection | literalMapCollection | literal | ( ( NAME '!' )? ( packagedType )? '#' )=> enumLiteral | featureCall | collectionType | pathName | specialType | logicalExpressionInBrackets | newExpression | variableDeclarationExpression );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA83_0 = input.LA(1);

                         
                        int index83_0 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (LA83_0==CollectionTypeName) ) {s = 1;}

                        else if ( (LA83_0==MapTypeName) ) {s = 2;}

                        else if ( (LA83_0==FLOAT||LA83_0==INT||LA83_0==BOOLEAN||LA83_0==STRING) ) {s = 3;}

                        else if ( (LA83_0==NAME) ) {s = 4;}

                        else if ( (LA83_0==158) && (synpred117_EolParserRules())) {s = 5;}

                        else if ( (LA83_0==SpecialTypeName) ) {s = 6;}

                        else if ( (LA83_0==101) ) {s = 7;}

                        else if ( (LA83_0==155) ) {s = 8;}

                        else if ( ((LA83_0>=156 && LA83_0<=157)) ) {s = 9;}

                         
                        input.seek(index83_0);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA83_1 = input.LA(1);

                         
                        int index83_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred112_EolParserRules()) ) {s = 11;}

                        else if ( (synpred119_EolParserRules()) ) {s = 12;}

                         
                        input.seek(index83_1);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA83_2 = input.LA(1);

                         
                        int index83_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred113_EolParserRules()) ) {s = 13;}

                        else if ( (synpred119_EolParserRules()) ) {s = 12;}

                         
                        input.seek(index83_2);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA83_4 = input.LA(1);

                         
                        int index83_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred117_EolParserRules()) ) {s = 5;}

                        else if ( (synpred118_EolParserRules()) ) {s = 14;}

                        else if ( (synpred120_EolParserRules()) ) {s = 15;}

                         
                        input.seek(index83_4);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 83, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_operationDeclaration_in_operationDeclarationOrAnnotationBlock263 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationBlock_in_operationDeclarationOrAnnotationBlock267 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_91_in_modelDeclaration286 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_modelDeclaration289 = new BitSet(new long[]{0x0000000000000000L,0x00000001B0000000L});
    public static final BitSet FOLLOW_modelAlias_in_modelDeclaration291 = new BitSet(new long[]{0x0000000000000000L,0x0000000190000000L});
    public static final BitSet FOLLOW_modelDriver_in_modelDeclaration294 = new BitSet(new long[]{0x0000000000000000L,0x0000000110000000L});
    public static final BitSet FOLLOW_modelDeclarationParameters_in_modelDeclaration297 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_modelDeclaration302 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_93_in_modelAlias317 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_modelAlias320 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_modelAlias323 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_modelAlias326 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_95_in_modelDriver345 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_modelDriver348 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_96_in_modelDeclarationParameters370 = new BitSet(new long[]{0x0000000000800000L,0x0000000240000000L});
    public static final BitSet FOLLOW_modelDeclarationParameter_in_modelDeclarationParameters373 = new BitSet(new long[]{0x0000000000000000L,0x0000000240000000L});
    public static final BitSet FOLLOW_94_in_modelDeclarationParameters377 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_modelDeclarationParameter_in_modelDeclarationParameters380 = new BitSet(new long[]{0x0000000000000000L,0x0000000240000000L});
    public static final BitSet FOLLOW_97_in_modelDeclarationParameters386 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_modelDeclarationParameter399 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_modelDeclarationParameter403 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_STRING_in_modelDeclarationParameter406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_operationDeclaration427 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_operationDeclaration437 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_operationDeclaration447 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_operationDeclaration451 = new BitSet(new long[]{0x0000000000800000L,0x0000004000000000L});
    public static final BitSet FOLLOW_formalParameterList_in_operationDeclaration454 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_operationDeclaration459 = new BitSet(new long[]{0x0000000000000000L,0x0000008100000000L});
    public static final BitSet FOLLOW_103_in_operationDeclaration465 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_operationDeclaration470 = new BitSet(new long[]{0x0000000000000000L,0x0000008100000000L});
    public static final BitSet FOLLOW_statementBlock_in_operationDeclaration476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_104_in_importStatement496 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_STRING_in_importStatement499 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_importStatement503 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_block524 = new BitSet(new long[]{0x00000000008EA112L,0x3FE9402000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_96_in_statementBlock554 = new BitSet(new long[]{0x00000000008EA110L,0x3FE9402000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_block_in_statementBlock557 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_statementBlock561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_formalParameter579 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_formalParameter582 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_formalParameter586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameter_in_formalParameterList620 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_formalParameterList623 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_formalParameter_in_formalParameterList625 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_105_in_executableAnnotation650 = new BitSet(new long[]{0xFFFFFFFFFFFFFFF0L,0xFFFFFFFFFFFFFFFFL,0x0000003FFFFFFFFFL});
    public static final BitSet FOLLOW_logicalExpression_in_executableAnnotation657 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_Annotation_in_annotation671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_executableAnnotation_in_annotation675 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotation_in_annotationBlock692 = new BitSet(new long[]{0x0000000008000002L,0x0000020000000000L});
    public static final BitSet FOLLOW_pathName_in_typeName721 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_typeName725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_specialType_in_typeName729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SpecialTypeName_in_specialType746 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_specialType751 = new BitSet(new long[]{0x0000000000008000L});
    public static final BitSet FOLLOW_STRING_in_specialType754 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_specialType758 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_pathName773 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_pathName775 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_packagedType_in_pathName784 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_packagedType802 = new BitSet(new long[]{0x0000000000000002L,0x0000080000000000L});
    public static final BitSet FOLLOW_107_in_packagedType805 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_packagedType810 = new BitSet(new long[]{0x0000000000000002L,0x0000080000000000L});
    public static final BitSet FOLLOW_set_in_collectionType840 = new BitSet(new long[]{0x0000000000000002L,0x0000102000000000L});
    public static final BitSet FOLLOW_101_in_collectionType855 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_collectionType860 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_94_in_collectionType865 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_collectionType869 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_102_in_collectionType877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_collectionType889 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_collectionType894 = new BitSet(new long[]{0x0000000000000000L,0x0000200040000000L});
    public static final BitSet FOLLOW_94_in_collectionType899 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_collectionType903 = new BitSet(new long[]{0x0000000000000000L,0x0000200040000000L});
    public static final BitSet FOLLOW_109_in_collectionType911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementA_in_statement930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementB_in_statement934 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentStatement_in_statementA945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionStatement_in_statementA949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_forStatement_in_statementA953 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ifStatement_in_statementA959 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_whileStatement_in_statementA963 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_switchStatement_in_statementA967 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_statementA971 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_breakStatement_in_statementA975 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_breakAllStatement_in_statementB987 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_returnStatement_in_statementB991 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_transactionStatement_in_statementB995 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_abortStatement_in_statementB1001 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_continueStatement_in_statementB1005 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_throwStatement_in_statementB1009 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deleteStatement_in_statementB1015 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statement_in_statementOrStatementBlock1026 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementBlock_in_statementOrStatementBlock1030 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_expressionOrStatementBlock1039 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_expressionOrStatementBlock1042 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementBlock_in_expressionOrStatementBlock1046 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_110_in_ifStatement1059 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_ifStatement1062 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_ifStatement1065 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_ifStatement1067 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A100000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_statementOrStatementBlock_in_ifStatement1070 = new BitSet(new long[]{0x0000000000000002L,0x0000800000000000L});
    public static final BitSet FOLLOW_elseStatement_in_ifStatement1072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_111_in_elseStatement1095 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A100000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_statementOrStatementBlock_in_elseStatement1098 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_112_in_switchStatement1112 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_switchStatement1115 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_switchStatement1118 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_switchStatement1120 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_switchStatement1123 = new BitSet(new long[]{0x0000000000000000L,0x0006000200000000L});
    public static final BitSet FOLLOW_caseStatement_in_switchStatement1126 = new BitSet(new long[]{0x0000000000000000L,0x0006000200000000L});
    public static final BitSet FOLLOW_defaultStatement_in_switchStatement1129 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_switchStatement1132 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_113_in_caseStatement1151 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_caseStatement1154 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_caseStatement1156 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A100000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_block_in_caseStatement1160 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementBlock_in_caseStatement1164 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_114_in_defaultStatement1183 = new BitSet(new long[]{0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_defaultStatement1186 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A100000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_block_in_defaultStatement1190 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementBlock_in_defaultStatement1194 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_115_in_forStatement1212 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_forStatement1215 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_formalParameter_in_forStatement1218 = new BitSet(new long[]{0x0000000000000000L,0x0010000000000000L});
    public static final BitSet FOLLOW_116_in_forStatement1220 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_forStatement1223 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_forStatement1225 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A100000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_statementOrStatementBlock_in_forStatement1228 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_117_in_whileStatement1244 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_whileStatement1247 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_whileStatement1250 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_whileStatement1252 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A100000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_statementOrStatementBlock_in_whileStatement1255 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_118_in_returnStatement1277 = new BitSet(new long[]{0x00000000008EA110L,0x0000002010000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_returnStatement1280 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_returnStatement1285 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_119_in_throwStatement1308 = new BitSet(new long[]{0x00000000008EA110L,0x0000002010000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_throwStatement1311 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_throwStatement1316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_120_in_deleteStatement1339 = new BitSet(new long[]{0x00000000008EA110L,0x0000002010000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_deleteStatement1342 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_deleteStatement1347 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_121_in_breakStatement1373 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_breakStatement1378 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_breakAllStatement1401 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_breakAllStatement1406 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_123_in_continueStatement1429 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_continueStatement1434 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_abortStatement1457 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_abortStatement1462 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_125_in_transactionStatement1479 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A100000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_NAME_in_transactionStatement1483 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A140000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_94_in_transactionStatement1486 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_transactionStatement1488 = new BitSet(new long[]{0x00000000008EA110L,0x3FE940A140000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_statementOrStatementBlock_in_transactionStatement1494 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logicalExpression_in_assignmentStatement1514 = new BitSet(new long[]{0x0000000000000000L,0xC000000000000000L,0x000000000000001FL});
    public static final BitSet FOLLOW_126_in_assignmentStatement1520 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_127_in_assignmentStatement1525 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_128_in_assignmentStatement1530 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_129_in_assignmentStatement1535 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_130_in_assignmentStatement1540 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_131_in_assignmentStatement1545 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_132_in_assignmentStatement1557 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_assignmentStatement1565 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_assignmentStatement1569 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_expressionStatement1589 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_expressionStatement1593 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_expressionStatement1596 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_logicalExpression_in_expressionStatement1603 = new BitSet(new long[]{0x0000000000000000L,0x0000000010000000L});
    public static final BitSet FOLLOW_92_in_expressionStatement1608 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_relationalExpression_in_logicalExpression1620 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_133_in_logicalExpression1631 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_134_in_logicalExpression1636 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_135_in_logicalExpression1641 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_136_in_logicalExpression1646 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_137_in_logicalExpression1660 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_relationalExpression_in_logicalExpression1663 = new BitSet(new long[]{0x0000000000000000L,0x0000808000000000L});
    public static final BitSet FOLLOW_set_in_logicalExpression1665 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_relationalExpression_in_logicalExpression1681 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000003E0L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1697 = new BitSet(new long[]{0x0000000000000002L,0x0000300400000000L,0x000000000000FC00L});
    public static final BitSet FOLLOW_138_in_relationalExpression1704 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_98_in_relationalExpression1709 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_139_in_relationalExpression1714 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_140_in_relationalExpression1719 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_relationalExpression_in_relationalExpression1723 = new BitSet(new long[]{0x0000000000000002L,0x0000300400000000L,0x000000000000FC00L});
    public static final BitSet FOLLOW_109_in_relationalExpression1753 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_108_in_relationalExpression1758 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_141_in_relationalExpression1763 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_142_in_relationalExpression1768 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_143_in_relationalExpression1773 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_additiveExpression_in_relationalExpression1777 = new BitSet(new long[]{0x0000000000000002L,0x0000300400000000L,0x000000000000FC00L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1795 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_144_in_additiveExpression1801 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_145_in_additiveExpression1806 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_multiplicativeExpression_in_additiveExpression1810 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000030000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1828 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_146_in_multiplicativeExpression1834 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_147_in_multiplicativeExpression1839 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_unaryExpression_in_multiplicativeExpression1843 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x00000000000C0000L});
    public static final BitSet FOLLOW_148_in_unaryExpression1864 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_145_in_unaryExpression1869 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_shortcutOperatorExpression_in_unaryExpression1877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_postfixExpression_in_shortcutOperatorExpression1889 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000600000L});
    public static final BitSet FOLLOW_149_in_shortcutOperatorExpression1895 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_150_in_shortcutOperatorExpression1902 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_itemSelectorExpression_in_postfixExpression1920 = new BitSet(new long[]{0x0000000000001A02L});
    public static final BitSet FOLLOW_set_in_postfixExpression1923 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_featureCall_in_postfixExpression1934 = new BitSet(new long[]{0x0000000000001A02L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_151_in_postfixExpression1943 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_postfixExpression1946 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_152_in_postfixExpression1948 = new BitSet(new long[]{0x0000000000001A02L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_primitiveExpression_in_itemSelectorExpression1970 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_151_in_itemSelectorExpression1975 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_primitiveExpression_in_itemSelectorExpression1978 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_152_in_itemSelectorExpression1980 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000000000800000L});
    public static final BitSet FOLLOW_simpleFeatureCall_in_featureCall1998 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_complexFeatureCall_in_featureCall2002 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_simpleFeatureCall2016 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_parameterList_in_simpleFeatureCall2019 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_parameterList2042 = new BitSet(new long[]{0x00000000008EA110L,0x0000006040000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_parameterList2045 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_94_in_parameterList2048 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_parameterList2050 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_94_in_parameterList2056 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_parameterList2061 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_complexFeatureCall2089 = new BitSet(new long[]{0x0000000000000000L,0x0000002000000000L});
    public static final BitSet FOLLOW_101_in_complexFeatureCall2094 = new BitSet(new long[]{0x0000000000800000L,0x0000002000000000L,0x0000000006800000L});
    public static final BitSet FOLLOW_lambdaExpression_in_complexFeatureCall2098 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_lambdaExpressionInBrackets_in_complexFeatureCall2102 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_94_in_complexFeatureCall2108 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x000000007E920000L});
    public static final BitSet FOLLOW_logicalExpression_in_complexFeatureCall2112 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_lambdaExpressionInBrackets_in_complexFeatureCall2116 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_102_in_complexFeatureCall2123 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_lambdaExpressionInBrackets2144 = new BitSet(new long[]{0x0000000000800000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_lambdaExpression_in_lambdaExpressionInBrackets2147 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_lambdaExpressionInBrackets2151 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_151_in_lambdaExpressionInBrackets2162 = new BitSet(new long[]{0x0000000000800000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_lambdaExpression_in_lambdaExpressionInBrackets2165 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000001000000L});
    public static final BitSet FOLLOW_152_in_lambdaExpressionInBrackets2169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_formalParameterList_in_lambdaExpression2188 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000006000000L});
    public static final BitSet FOLLOW_153_in_lambdaExpression2194 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_154_in_lambdaExpression2201 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_lambdaExpression2205 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_155_in_newExpression2218 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_newExpression2223 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_parameterList_in_newExpression2227 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_156_in_variableDeclarationExpression2251 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_157_in_variableDeclarationExpression2256 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_variableDeclarationExpression2260 = new BitSet(new long[]{0x0000000000000002L,0x0000008000000000L});
    public static final BitSet FOLLOW_103_in_variableDeclarationExpression2263 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_155_in_variableDeclarationExpression2268 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_typeName_in_variableDeclarationExpression2274 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_parameterList_in_variableDeclarationExpression2278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_CollectionTypeName_in_literalSequentialCollection2301 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_literalSequentialCollection2308 = new BitSet(new long[]{0x00000000008EA110L,0x0000002200000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_expressionListOrRange_in_literalSequentialCollection2311 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_literalSequentialCollection2316 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logicalExpression_in_expressionRange2331 = new BitSet(new long[]{0x0000000000000400L});
    public static final BitSet FOLLOW_POINT_POINT_in_expressionRange2335 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_expressionRange2338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logicalExpression_in_expressionList2359 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_expressionList2362 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_expressionList2364 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_expressionList2368 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionRange_in_expressionListOrRange2391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionList_in_expressionListOrRange2395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MapTypeName_in_literalMapCollection2414 = new BitSet(new long[]{0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_literalMapCollection2419 = new BitSet(new long[]{0x00000000008EA110L,0x0000002200000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_keyvalExpressionList_in_literalMapCollection2422 = new BitSet(new long[]{0x0000000000000000L,0x0000000200000000L});
    public static final BitSet FOLLOW_97_in_literalMapCollection2427 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_keyvalExpression_in_keyvalExpressionList2448 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_keyvalExpressionList2451 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_keyvalExpression_in_keyvalExpressionList2453 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_keyvalExpressionList2457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_additiveExpression_in_keyvalExpression2481 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_keyvalExpression2485 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_keyvalExpression2488 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_enumLiteral2505 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_enumLiteral2507 = new BitSet(new long[]{0x0000000000800000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_packagedType_in_enumLiteral2515 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_158_in_enumLiteral2519 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_enumLiteral2524 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literalSequentialCollection_in_primitiveExpression2540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literalMapCollection_in_primitiveExpression2544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_primitiveExpression2548 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_enumLiteral_in_primitiveExpression2570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_featureCall_in_primitiveExpression2576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_primitiveExpression2580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathName_in_primitiveExpression2586 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_specialType_in_primitiveExpression2590 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_logicalExpressionInBrackets_in_primitiveExpression2594 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_newExpression_in_primitiveExpression2598 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_variableDeclarationExpression_in_primitiveExpression2602 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_logicalExpressionInBrackets2621 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_logicalExpressionInBrackets2624 = new BitSet(new long[]{0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_102_in_logicalExpressionInBrackets2628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_literal0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_101_in_synpred23_EolParserRules855 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_typeName_in_synpred23_EolParserRules860 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_94_in_synpred23_EolParserRules865 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_typeName_in_synpred23_EolParserRules869 = new BitSet(new long[]{0x0000000000000000L,0x0000004040000000L});
    public static final BitSet FOLLOW_102_in_synpred23_EolParserRules877 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_108_in_synpred25_EolParserRules889 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_typeName_in_synpred25_EolParserRules894 = new BitSet(new long[]{0x0000000000000000L,0x0000200040000000L});
    public static final BitSet FOLLOW_94_in_synpred25_EolParserRules899 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_typeName_in_synpred25_EolParserRules903 = new BitSet(new long[]{0x0000000000000000L,0x0000200040000000L});
    public static final BitSet FOLLOW_109_in_synpred25_EolParserRules911 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_statementA_in_synpred26_EolParserRules930 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignmentStatement_in_synpred27_EolParserRules945 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionStatement_in_synpred28_EolParserRules949 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_elseStatement_in_synpred42_EolParserRules1072 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_synpred51_EolParserRules1483 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_94_in_synpred51_EolParserRules1486 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_synpred51_EolParserRules1488 = new BitSet(new long[]{0x0000000000000002L,0x0000000040000000L});
    public static final BitSet FOLLOW_postfixExpression_in_synpred58_EolParserRules1589 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_synpred58_EolParserRules1593 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_logicalExpression_in_synpred58_EolParserRules1596 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred73_EolParserRules1701 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_relationalExpression_in_synpred73_EolParserRules1723 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_synpred73_EolParserRules1750 = new BitSet(new long[]{0x00000000008EA110L,0x0000002000000000L,0x0000000078120000L});
    public static final BitSet FOLLOW_additiveExpression_in_synpred73_EolParserRules1777 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_103_in_synpred102_EolParserRules2263 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_155_in_synpred102_EolParserRules2268 = new BitSet(new long[]{0x00000000008E0000L,0x0000000000000000L,0x0000000008000000L});
    public static final BitSet FOLLOW_typeName_in_synpred102_EolParserRules2274 = new BitSet(new long[]{0x0000000000000002L,0x0000002000000000L});
    public static final BitSet FOLLOW_parameterList_in_synpred102_EolParserRules2278 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expressionRange_in_synpred106_EolParserRules2391 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literalSequentialCollection_in_synpred112_EolParserRules2540 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literalMapCollection_in_synpred113_EolParserRules2544 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_synpred117_EolParserRules2556 = new BitSet(new long[]{0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_106_in_synpred117_EolParserRules2558 = new BitSet(new long[]{0x0000000000800000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_packagedType_in_synpred117_EolParserRules2562 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000040000000L});
    public static final BitSet FOLLOW_158_in_synpred117_EolParserRules2565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_featureCall_in_synpred118_EolParserRules2576 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_collectionType_in_synpred119_EolParserRules2580 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pathName_in_synpred120_EolParserRules2586 = new BitSet(new long[]{0x0000000000000002L});

}
