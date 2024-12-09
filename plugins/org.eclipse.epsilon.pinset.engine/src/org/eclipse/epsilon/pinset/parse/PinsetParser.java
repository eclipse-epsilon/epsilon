// $ANTLR 3.1b1 Pinset.g 2024-12-09 10:43:37

package org.eclipse.epsilon.pinset.parse;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

/*******************************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Dimitrios Kolovos - initial API and EDL demo implementation
 *     Pablo Sanchez - API and language discussion
 *     Alfonso de la Vega - initial API and implementation
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
public class PinsetParser extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FLOAT", "DIGIT", "EXPONENT", "FLOAT_TYPE_SUFFIX", "INT", "POINT", "POINT_POINT", "ARROW", "NAVIGATION", "BOOLEAN", "EscapeSequence", "STRING", "StrangeNameLiteral", "CollectionTypeName", "MapTypeName", "SpecialTypeName", "Letter", "SpecialNameChar", "JavaIDDigit", "NAME", "WS", "COMMENT", "LINE_COMMENT", "Annotation", "FORMAL", "PARAMLIST", "ASSIGNMENT", "SPECIAL_ASSIGNMENT", "HELPERMETHOD", "StatementBlock", "FOR", "IF", "ELSE", "TERNARY", "WHILE", "SWITCH", "CASE", "DEFAULT", "RETURN", "BREAK", "BREAKALL", "CONTINUE", "TRANSACTION", "COLLECTION", "ABORT", "CollectionType", "ModelElementType", "PARAMETERS", "NewExpression", "VAR", "NEW", "ANNOTATIONBLOCK", "EXECUTABLEANNOTATION", "DELETE", "THROW", "EXPRLIST", "EXPRRANGE", "NativeType", "MultiplicativeExpression", "OPERATOR", "EXPRESSIONINBRACKETS", "FeatureCall", "EOLMODULE", "BLOCK", "FEATURECALL", "LAMBDAEXPR", "TYPE", "ENUMERATION_VALUE", "IMPORT", "MODELDECLARATION", "NAMESPACE", "ALIAS", "DRIVER", "MODELDECLARATIONPARAMETERS", "MODELDECLARATIONPARAMETER", "ITEMSELECTOR", "MAP", "KEYVAL", "KEYVALLIST", "PRE", "POST", "EXTENDS", "GUARD", "DATASET", "NAMESLIST", "ALIASEDNAME", "PROPERTIES", "COLUMN", "REFERENCE", "GRID", "GRIDKEYS", "GRIDHEADER", "GRIDBODY", "FROM", "NESTEDFROM", "PINSETMODULE", "'model'", "';'", "'alias'", "','", "'driver'", "'{'", "'}'", "'='", "'operation'", "'function'", "'('", "')'", "':'", "'import'", "'$'", "'!'", "'::'", "'<'", "'>'", "'if'", "'else'", "'switch'", "'case'", "'default'", "'for'", "'in'", "'while'", "'return'", "'throw'", "'delete'", "'break'", "'breakAll'", "'continue'", "'abort'", "'transaction'", "':='", "'+='", "'-='", "'*='", "'/='", "'?='", "'::='", "'or'", "'and'", "'xor'", "'implies'", "'?'", "'=='", "'!='", "'?:'", "'>='", "'<='", "'<>'", "'+'", "'-'", "'*'", "'/'", "'not'", "'++'", "'--'", "'['", "']'", "'|'", "'=>'", "'new'", "'var'", "'ext'", "'#'", "'pre'", "'post'", "'guard'", "'extends'", "'dataset'", "'over'", "'from'", "'as'", "'properties'", "'reference'", "'column'", "'grid'", "'keys'", "'header'", "'body'"
    };
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
    public static final int ALIASEDNAME=89;
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
    public static final int GRIDHEADER=95;
    public static final int T__125=125;
    public static final int LAMBDAEXPR=69;
    public static final int MAP=80;
    public static final int T__128=128;
    public static final int T__127=127;
    public static final int PINSETMODULE=99;
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
    public static final int GRIDBODY=96;
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
    public static final int GRID=93;
    public static final int Annotation=27;
    public static final int CONTINUE=45;
    public static final int ENUMERATION_VALUE=71;
    public static final int OPERATOR=63;
    public static final int EXPONENT=6;
    public static final int STRING=15;
    public static final int T__148=148;
    public static final int T__147=147;
    public static final int T__149=149;
    public static final int NAMESLIST=88;
    public static final int T__100=100;
    public static final int NAMESPACE=74;
    public static final int COLLECTION=47;
    public static final int NEW=54;
    public static final int EXTENDS=85;
    public static final int T__102=102;
    public static final int PRE=83;
    public static final int T__101=101;
    public static final int PROPERTIES=90;
    public static final int POST=84;
    public static final int ALIAS=75;
    public static final int DRIVER=76;
    public static final int COLUMN=91;
    public static final int T__180=180;
    public static final int DATASET=87;
    public static final int T__182=182;
    public static final int T__181=181;
    public static final int FROM=97;
    public static final int KEYVAL=81;
    public static final int POINT_POINT=10;
    public static final int GUARD=86;
    public static final int HELPERMETHOD=32;
    public static final int StatementBlock=33;
    public static final int GRIDKEYS=94;
    public static final int T__177=177;
    public static final int T__176=176;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int ABORT=48;
    public static final int T__173=173;
    public static final int StrangeNameLiteral=16;
    public static final int T__172=172;
    public static final int T__175=175;
    public static final int T__174=174;
    public static final int FOR=34;
    public static final int BLOCK=67;
    public static final int T__171=171;
    public static final int T__170=170;
    public static final int PARAMETERS=51;
    public static final int SpecialNameChar=21;
    public static final int BOOLEAN=13;
    public static final int NAME=23;
    public static final int SWITCH=39;
    public static final int T__169=169;
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
    public static final int REFERENCE=92;
    public static final int T__118=118;
    public static final int T__115=115;
    public static final int WS=24;
    public static final int NESTEDFROM=98;
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
    public Pinset_EolParserRules gEolParserRules;
    public Pinset_ErlParserRules gErlParserRules;
    public Pinset_PinsetParserRules gPinsetParserRules;
    // delegators


        public PinsetParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public PinsetParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gEolParserRules = new Pinset_EolParserRules(input, state, this);
            gErlParserRules = new Pinset_ErlParserRules(input, state, this);
            gPinsetParserRules = new Pinset_PinsetParserRules(input, state, this);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return PinsetParser.tokenNames; }
    public String getGrammarFileName() { return "Pinset.g"; }


    public static class pinsetModule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pinsetModule
    // Pinset.g:103:1: pinsetModule : ( importStatement )* ( pinsetModuleContent )* EOF -> ^( PINSETMODULE ( importStatement )* ( pinsetModuleContent )* ) ;
    public final PinsetParser.pinsetModule_return pinsetModule() throws RecognitionException {
        PinsetParser.pinsetModule_return retval = new PinsetParser.pinsetModule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token EOF3=null;
        Pinset_EolParserRules.importStatement_return importStatement1 = null;

        PinsetParser.pinsetModuleContent_return pinsetModuleContent2 = null;


        org.eclipse.epsilon.common.parse.AST EOF3_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_pinsetModuleContent=new RewriteRuleSubtreeStream(adaptor,"rule pinsetModuleContent");
        RewriteRuleSubtreeStream stream_importStatement=new RewriteRuleSubtreeStream(adaptor,"rule importStatement");
        try {
            // Pinset.g:104:3: ( ( importStatement )* ( pinsetModuleContent )* EOF -> ^( PINSETMODULE ( importStatement )* ( pinsetModuleContent )* ) )
            // Pinset.g:104:5: ( importStatement )* ( pinsetModuleContent )* EOF
            {
            // Pinset.g:104:5: ( importStatement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==113) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Pinset.g:0:0: importStatement
            	    {
            	    pushFollow(FOLLOW_importStatement_in_pinsetModule77);
            	    importStatement1=importStatement();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_importStatement.add(importStatement1.getTree());

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            // Pinset.g:104:22: ( pinsetModuleContent )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==Annotation||(LA2_0>=108 && LA2_0<=109)||LA2_0==114||(LA2_0>=168 && LA2_0<=169)||LA2_0==172) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Pinset.g:104:23: pinsetModuleContent
            	    {
            	    pushFollow(FOLLOW_pinsetModuleContent_in_pinsetModule81);
            	    pinsetModuleContent2=pinsetModuleContent();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_pinsetModuleContent.add(pinsetModuleContent2.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            EOF3=(Token)match(input,EOF,FOLLOW_EOF_in_pinsetModule85); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF3);



            // AST REWRITE
            // elements: importStatement, pinsetModuleContent
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 105:3: -> ^( PINSETMODULE ( importStatement )* ( pinsetModuleContent )* )
            {
                // Pinset.g:105:6: ^( PINSETMODULE ( importStatement )* ( pinsetModuleContent )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(PINSETMODULE, "PINSETMODULE"), root_1);

                // Pinset.g:105:21: ( importStatement )*
                while ( stream_importStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_importStatement.nextTree());

                }
                stream_importStatement.reset();
                // Pinset.g:105:38: ( pinsetModuleContent )*
                while ( stream_pinsetModuleContent.hasNext() ) {
                    adaptor.addChild(root_1, stream_pinsetModuleContent.nextTree());

                }
                stream_pinsetModuleContent.reset();

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
    // $ANTLR end pinsetModule

    public static class pinsetModuleContent_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pinsetModuleContent
    // Pinset.g:108:1: pinsetModuleContent : ( pre | post | datasetRule | annotationBlock | operationDeclaration );
    public final PinsetParser.pinsetModuleContent_return pinsetModuleContent() throws RecognitionException {
        PinsetParser.pinsetModuleContent_return retval = new PinsetParser.pinsetModuleContent_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Pinset_ErlParserRules.pre_return pre4 = null;

        Pinset_ErlParserRules.post_return post5 = null;

        Pinset_PinsetParserRules.datasetRule_return datasetRule6 = null;

        Pinset_EolParserRules.annotationBlock_return annotationBlock7 = null;

        Pinset_EolParserRules.operationDeclaration_return operationDeclaration8 = null;



        try {
            // Pinset.g:109:3: ( pre | post | datasetRule | annotationBlock | operationDeclaration )
            int alt3=5;
            switch ( input.LA(1) ) {
            case 168:
                {
                alt3=1;
                }
                break;
            case 169:
                {
                alt3=2;
                }
                break;
            case 172:
                {
                alt3=3;
                }
                break;
            case Annotation:
            case 114:
                {
                alt3=4;
                }
                break;
            case 108:
            case 109:
                {
                alt3=5;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // Pinset.g:109:5: pre
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_pre_in_pinsetModuleContent112);
                    pre4=pre();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pre4.getTree());

                    }
                    break;
                case 2 :
                    // Pinset.g:109:11: post
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_post_in_pinsetModuleContent116);
                    post5=post();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, post5.getTree());

                    }
                    break;
                case 3 :
                    // Pinset.g:109:18: datasetRule
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_datasetRule_in_pinsetModuleContent120);
                    datasetRule6=datasetRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, datasetRule6.getTree());

                    }
                    break;
                case 4 :
                    // Pinset.g:109:32: annotationBlock
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_annotationBlock_in_pinsetModuleContent124);
                    annotationBlock7=annotationBlock();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, annotationBlock7.getTree());

                    }
                    break;
                case 5 :
                    // Pinset.g:109:50: operationDeclaration
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_operationDeclaration_in_pinsetModuleContent128);
                    operationDeclaration8=operationDeclaration();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, operationDeclaration8.getTree());

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
    // $ANTLR end pinsetModuleContent

    // Delegated rules
    public Pinset_EolParserRules.transactionStatement_return transactionStatement() throws RecognitionException { return gEolParserRules.transactionStatement(); }
    public Pinset_EolParserRules.statementA_return statementA() throws RecognitionException { return gEolParserRules.statementA(); }
    public Pinset_ErlParserRules.pre_return pre() throws RecognitionException { return gErlParserRules.pre(); }
    public Pinset_EolParserRules.keyvalExpressionList_return keyvalExpressionList() throws RecognitionException { return gEolParserRules.keyvalExpressionList(); }
    public Pinset_PinsetParserRules.grid_return grid() throws RecognitionException { return gPinsetParserRules.grid(); }
    public Pinset_EolParserRules.block_return block() throws RecognitionException { return gEolParserRules.block(); }
    public Pinset_EolParserRules.newExpression_return newExpression() throws RecognitionException { return gEolParserRules.newExpression(); }
    public Pinset_EolParserRules.expressionList_return expressionList() throws RecognitionException { return gEolParserRules.expressionList(); }
    public Pinset_EolParserRules.literalSequentialCollection_return literalSequentialCollection() throws RecognitionException { return gEolParserRules.literalSequentialCollection(); }
    public Pinset_EolParserRules.continueStatement_return continueStatement() throws RecognitionException { return gEolParserRules.continueStatement(); }
    public Pinset_EolParserRules.variableDeclarationExpression_return variableDeclarationExpression() throws RecognitionException { return gEolParserRules.variableDeclarationExpression(); }
    public Pinset_EolParserRules.logicalExpression_return logicalExpression() throws RecognitionException { return gEolParserRules.logicalExpression(); }
    public Pinset_PinsetParserRules.from_return from() throws RecognitionException { return gPinsetParserRules.from(); }
    public Pinset_EolParserRules.defaultStatement_return defaultStatement() throws RecognitionException { return gEolParserRules.defaultStatement(); }
    public Pinset_PinsetParserRules.nestedFrom_return nestedFrom() throws RecognitionException { return gPinsetParserRules.nestedFrom(); }
    public Pinset_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock() throws RecognitionException { return gEolParserRules.expressionOrStatementBlock(); }
    public Pinset_EolParserRules.literalMapCollection_return literalMapCollection() throws RecognitionException { return gEolParserRules.literalMapCollection(); }
    public Pinset_EolParserRules.pathName_return pathName() throws RecognitionException { return gEolParserRules.pathName(); }
    public Pinset_EolParserRules.primitiveExpression_return primitiveExpression() throws RecognitionException { return gEolParserRules.primitiveExpression(); }
    public Pinset_EolParserRules.shortcutOperatorExpression_return shortcutOperatorExpression() throws RecognitionException { return gEolParserRules.shortcutOperatorExpression(); }
    public Pinset_PinsetParserRules.aliasedName_return aliasedName() throws RecognitionException { return gPinsetParserRules.aliasedName(); }
    public Pinset_EolParserRules.modelAlias_return modelAlias() throws RecognitionException { return gEolParserRules.modelAlias(); }
    public Pinset_EolParserRules.itemSelectorExpression_return itemSelectorExpression() throws RecognitionException { return gEolParserRules.itemSelectorExpression(); }
    public Pinset_EolParserRules.assignmentStatement_return assignmentStatement() throws RecognitionException { return gEolParserRules.assignmentStatement(); }
    public Pinset_EolParserRules.annotationBlock_return annotationBlock() throws RecognitionException { return gEolParserRules.annotationBlock(); }
    public Pinset_PinsetParserRules.header_return header() throws RecognitionException { return gPinsetParserRules.header(); }
    public Pinset_EolParserRules.operationDeclaration_return operationDeclaration() throws RecognitionException { return gEolParserRules.operationDeclaration(); }
    public Pinset_EolParserRules.executableAnnotation_return executableAnnotation() throws RecognitionException { return gEolParserRules.executableAnnotation(); }
    public Pinset_EolParserRules.expressionRange_return expressionRange() throws RecognitionException { return gEolParserRules.expressionRange(); }
    public Pinset_EolParserRules.unaryExpression_return unaryExpression() throws RecognitionException { return gEolParserRules.unaryExpression(); }
    public Pinset_EolParserRules.collectionType_return collectionType() throws RecognitionException { return gEolParserRules.collectionType(); }
    public Pinset_EolParserRules.annotation_return annotation() throws RecognitionException { return gEolParserRules.annotation(); }
    public Pinset_ErlParserRules.extendz_return extendz() throws RecognitionException { return gErlParserRules.extendz(); }
    public Pinset_EolParserRules.statementB_return statementB() throws RecognitionException { return gEolParserRules.statementB(); }
    public Pinset_EolParserRules.specialType_return specialType() throws RecognitionException { return gEolParserRules.specialType(); }
    public Pinset_EolParserRules.enumLiteral_return enumLiteral() throws RecognitionException { return gEolParserRules.enumLiteral(); }
    public Pinset_EolParserRules.modelDriver_return modelDriver() throws RecognitionException { return gEolParserRules.modelDriver(); }
    public Pinset_PinsetParserRules.column_return column() throws RecognitionException { return gPinsetParserRules.column(); }
    public Pinset_PinsetParserRules.reference_return reference() throws RecognitionException { return gPinsetParserRules.reference(); }
    public Pinset_EolParserRules.additiveExpression_return additiveExpression() throws RecognitionException { return gEolParserRules.additiveExpression(); }
    public Pinset_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock() throws RecognitionException { return gEolParserRules.statementOrStatementBlock(); }
    public Pinset_PinsetParserRules.nameslist_return nameslist() throws RecognitionException { return gPinsetParserRules.nameslist(); }
    public Pinset_EolParserRules.forStatement_return forStatement() throws RecognitionException { return gEolParserRules.forStatement(); }
    public Pinset_EolParserRules.breakStatement_return breakStatement() throws RecognitionException { return gEolParserRules.breakStatement(); }
    public Pinset_PinsetParserRules.gkeys_return gkeys() throws RecognitionException { return gPinsetParserRules.gkeys(); }
    public Pinset_PinsetParserRules.properties_return properties() throws RecognitionException { return gPinsetParserRules.properties(); }
    public Pinset_EolParserRules.modelDeclarationParameters_return modelDeclarationParameters() throws RecognitionException { return gEolParserRules.modelDeclarationParameters(); }
    public Pinset_EolParserRules.elseStatement_return elseStatement() throws RecognitionException { return gEolParserRules.elseStatement(); }
    public Pinset_EolParserRules.logicalExpressionInBrackets_return logicalExpressionInBrackets() throws RecognitionException { return gEolParserRules.logicalExpressionInBrackets(); }
    public Pinset_EolParserRules.lambdaExpressionInBrackets_return lambdaExpressionInBrackets() throws RecognitionException { return gEolParserRules.lambdaExpressionInBrackets(); }
    public Pinset_EolParserRules.keyvalExpression_return keyvalExpression() throws RecognitionException { return gEolParserRules.keyvalExpression(); }
    public Pinset_EolParserRules.featureCall_return featureCall() throws RecognitionException { return gEolParserRules.featureCall(); }
    public Pinset_ErlParserRules.guard_return guard() throws RecognitionException { return gErlParserRules.guard(); }
    public Pinset_EolParserRules.returnStatement_return returnStatement() throws RecognitionException { return gEolParserRules.returnStatement(); }
    public Pinset_EolParserRules.literal_return literal() throws RecognitionException { return gEolParserRules.literal(); }
    public Pinset_EolParserRules.expressionListOrRange_return expressionListOrRange() throws RecognitionException { return gEolParserRules.expressionListOrRange(); }
    public Pinset_EolParserRules.formalParameter_return formalParameter() throws RecognitionException { return gEolParserRules.formalParameter(); }
    public Pinset_EolParserRules.statement_return statement() throws RecognitionException { return gEolParserRules.statement(); }
    public Pinset_EolParserRules.statementBlock_return statementBlock() throws RecognitionException { return gEolParserRules.statementBlock(); }
    public Pinset_EolParserRules.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gEolParserRules.multiplicativeExpression(); }
    public Pinset_EolParserRules.abortStatement_return abortStatement() throws RecognitionException { return gEolParserRules.abortStatement(); }
    public Pinset_PinsetParserRules.columnGenerator_return columnGenerator() throws RecognitionException { return gPinsetParserRules.columnGenerator(); }
    public Pinset_EolParserRules.parameterList_return parameterList() throws RecognitionException { return gEolParserRules.parameterList(); }
    public Pinset_EolParserRules.relationalExpression_return relationalExpression() throws RecognitionException { return gEolParserRules.relationalExpression(); }
    public Pinset_PinsetParserRules.datasetRule_return datasetRule() throws RecognitionException { return gPinsetParserRules.datasetRule(); }
    public Pinset_EolParserRules.whileStatement_return whileStatement() throws RecognitionException { return gEolParserRules.whileStatement(); }
    public Pinset_EolParserRules.formalParameterList_return formalParameterList() throws RecognitionException { return gEolParserRules.formalParameterList(); }
    public Pinset_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock() throws RecognitionException { return gEolParserRules.operationDeclarationOrAnnotationBlock(); }
    public Pinset_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter() throws RecognitionException { return gEolParserRules.modelDeclarationParameter(); }
    public Pinset_EolParserRules.packagedType_return packagedType() throws RecognitionException { return gEolParserRules.packagedType(); }
    public Pinset_ErlParserRules.erlModuleContent_return erlModuleContent() throws RecognitionException { return gErlParserRules.erlModuleContent(); }
    public Pinset_EolParserRules.throwStatement_return throwStatement() throws RecognitionException { return gEolParserRules.throwStatement(); }
    public Pinset_EolParserRules.deleteStatement_return deleteStatement() throws RecognitionException { return gEolParserRules.deleteStatement(); }
    public Pinset_EolParserRules.importStatement_return importStatement() throws RecognitionException { return gEolParserRules.importStatement(); }
    public Pinset_EolParserRules.breakAllStatement_return breakAllStatement() throws RecognitionException { return gEolParserRules.breakAllStatement(); }
    public Pinset_EolParserRules.typeName_return typeName() throws RecognitionException { return gEolParserRules.typeName(); }
    public Pinset_EolParserRules.complexFeatureCall_return complexFeatureCall() throws RecognitionException { return gEolParserRules.complexFeatureCall(); }
    public Pinset_EolParserRules.switchStatement_return switchStatement() throws RecognitionException { return gEolParserRules.switchStatement(); }
    public Pinset_EolParserRules.expressionStatement_return expressionStatement() throws RecognitionException { return gEolParserRules.expressionStatement(); }
    public Pinset_PinsetParserRules.gbody_return gbody() throws RecognitionException { return gPinsetParserRules.gbody(); }
    public Pinset_EolParserRules.simpleFeatureCall_return simpleFeatureCall() throws RecognitionException { return gEolParserRules.simpleFeatureCall(); }
    public Pinset_EolParserRules.modelDeclaration_return modelDeclaration() throws RecognitionException { return gEolParserRules.modelDeclaration(); }
    public Pinset_EolParserRules.postfixExpression_return postfixExpression() throws RecognitionException { return gEolParserRules.postfixExpression(); }
    public Pinset_ErlParserRules.post_return post() throws RecognitionException { return gErlParserRules.post(); }
    public Pinset_EolParserRules.ifStatement_return ifStatement() throws RecognitionException { return gEolParserRules.ifStatement(); }
    public Pinset_EolParserRules.lambdaExpression_return lambdaExpression() throws RecognitionException { return gEolParserRules.lambdaExpression(); }
    public Pinset_EolParserRules.caseStatement_return caseStatement() throws RecognitionException { return gEolParserRules.caseStatement(); }


 

    public static final BitSet FOLLOW_importStatement_in_pinsetModule77 = new BitSet(new long[]{0x0000000008000000L,0x0006300000000000L,0x0000130000000000L});
    public static final BitSet FOLLOW_pinsetModuleContent_in_pinsetModule81 = new BitSet(new long[]{0x0000000008000000L,0x0004300000000000L,0x0000130000000000L});
    public static final BitSet FOLLOW_EOF_in_pinsetModule85 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_pre_in_pinsetModuleContent112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_post_in_pinsetModuleContent116 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_datasetRule_in_pinsetModuleContent120 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_annotationBlock_in_pinsetModuleContent124 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_operationDeclaration_in_pinsetModuleContent128 = new BitSet(new long[]{0x0000000000000002L});

}