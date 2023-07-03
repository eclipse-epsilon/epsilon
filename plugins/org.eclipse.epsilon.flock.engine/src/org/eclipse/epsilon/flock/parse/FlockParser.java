// $ANTLR 3.1b1 Flock.g 2023-07-03 20:54:50

package org.eclipse.epsilon.flock.parse;

import java.net.URI;
import org.eclipse.epsilon.common.parse.EpsilonTreeAdaptor;
import org.eclipse.epsilon.common.parse.AST;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

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
public class FlockParser extends org.eclipse.epsilon.common.parse.EpsilonParser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "FLOAT", "DIGIT", "EXPONENT", "FLOAT_TYPE_SUFFIX", "INT", "POINT", "POINT_POINT", "ARROW", "NAVIGATION", "BOOLEAN", "EscapeSequence", "STRING", "StrangeNameLiteral", "CollectionTypeName", "MapTypeName", "SpecialTypeName", "Letter", "SpecialNameChar", "JavaIDDigit", "NAME", "WS", "COMMENT", "LINE_COMMENT", "Annotation", "FORMAL", "PARAMLIST", "ASSIGNMENT", "SPECIAL_ASSIGNMENT", "HELPERMETHOD", "StatementBlock", "FOR", "IF", "ELSE", "TERNARY", "WHILE", "SWITCH", "CASE", "DEFAULT", "RETURN", "BREAK", "BREAKALL", "CONTINUE", "TRANSACTION", "COLLECTION", "ABORT", "CollectionType", "ModelElementType", "PARAMETERS", "NewExpression", "VAR", "NEW", "ANNOTATIONBLOCK", "EXECUTABLEANNOTATION", "DELETE", "THROW", "EXPRLIST", "EXPRRANGE", "NativeType", "MultiplicativeExpression", "OPERATOR", "EXPRESSIONINBRACKETS", "FeatureCall", "EOLMODULE", "BLOCK", "FEATURECALL", "LAMBDAEXPR", "TYPE", "ENUMERATION_VALUE", "IMPORT", "MODELDECLARATION", "NAMESPACE", "ALIAS", "DRIVER", "MODELDECLARATIONPARAMETERS", "MODELDECLARATIONPARAMETER", "ITEMSELECTOR", "MAP", "KEYVAL", "KEYVALLIST", "PRE", "POST", "EXTENDS", "GUARD", "FLOCKMODULE", "RETYPE", "RETYPEPACKAGE", "DELETEPACKAGE", "MIGRATE", "IGNORING", "'model'", "';'", "'alias'", "','", "'driver'", "'{'", "'}'", "'='", "'operation'", "'function'", "'('", "')'", "':'", "'import'", "'$'", "'!'", "'::'", "'<'", "'>'", "'if'", "'else'", "'switch'", "'case'", "'default'", "'for'", "'in'", "'while'", "'return'", "'throw'", "'delete'", "'break'", "'breakAll'", "'continue'", "'abort'", "'transaction'", "':='", "'+='", "'-='", "'*='", "'/='", "'?='", "'::='", "'or'", "'and'", "'xor'", "'implies'", "'?'", "'=='", "'!='", "'?:'", "'>='", "'<='", "'<>'", "'+'", "'-'", "'*'", "'/'", "'not'", "'++'", "'--'", "'['", "']'", "'|'", "'=>'", "'new'", "'var'", "'ext'", "'#'", "'pre'", "'post'", "'guard'", "'extends'", "'retype'", "'package'", "'to'", "'migrate'", "'ignoring'", "'when'"
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
    public static final int RETYPE=88;
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
    public static final int DELETEPACKAGE=90;
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
    public static final int MIGRATE=91;
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
    public static final int IGNORING=92;
    public static final int FOR=34;
    public static final int BLOCK=67;
    public static final int FLOCKMODULE=87;
    public static final int RETYPEPACKAGE=89;
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
    public Flock_EolParserRules gEolParserRules;
    public Flock_ErlParserRules gErlParserRules;
    // delegators


        public FlockParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public FlockParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            gEolParserRules = new Flock_EolParserRules(input, state, this);
            gErlParserRules = new Flock_ErlParserRules(input, state, this);
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return FlockParser.tokenNames; }
    public String getGrammarFileName() { return "Flock.g"; }


    	@Override
    	public void prepareForGUnit() {
    		super.prepareForGUnit();
    		this.setDeepTreeAdaptor(new EpsilonTreeAdaptor((URI) null) {
    			@Override
    		    public Object errorNode(TokenStream arg0, Token token, Token arg2,
    		    		RecognitionException arg3) {
    		    	AST ast = new AST(arg2, uri);
    		    	return ast;
    		    }
    		});
    		gEolParserRules.prepareForGUnit();
    	}


    public static class flockModule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start flockModule
    // Flock.g:114:1: flockModule : ( importStatement )* ( modelDeclaration )* ( flockModuleContent )* EOF -> ^( FLOCKMODULE ( importStatement )* ( modelDeclaration )* ( flockModuleContent )* ) ;
    public final FlockParser.flockModule_return flockModule() throws RecognitionException {
        FlockParser.flockModule_return retval = new FlockParser.flockModule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token EOF4=null;
        Flock_EolParserRules.importStatement_return importStatement1 = null;

        Flock_EolParserRules.modelDeclaration_return modelDeclaration2 = null;

        FlockParser.flockModuleContent_return flockModuleContent3 = null;


        org.eclipse.epsilon.common.parse.AST EOF4_tree=null;
        RewriteRuleTokenStream stream_EOF=new RewriteRuleTokenStream(adaptor,"token EOF");
        RewriteRuleSubtreeStream stream_modelDeclaration=new RewriteRuleSubtreeStream(adaptor,"rule modelDeclaration");
        RewriteRuleSubtreeStream stream_importStatement=new RewriteRuleSubtreeStream(adaptor,"rule importStatement");
        RewriteRuleSubtreeStream stream_flockModuleContent=new RewriteRuleSubtreeStream(adaptor,"rule flockModuleContent");
        try {
            // Flock.g:115:2: ( ( importStatement )* ( modelDeclaration )* ( flockModuleContent )* EOF -> ^( FLOCKMODULE ( importStatement )* ( modelDeclaration )* ( flockModuleContent )* ) )
            // Flock.g:115:4: ( importStatement )* ( modelDeclaration )* ( flockModuleContent )* EOF
            {
            // Flock.g:115:4: ( importStatement )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0==106) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // Flock.g:0:0: importStatement
            	    {
            	    pushFollow(FOLLOW_importStatement_in_flockModule104);
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

            // Flock.g:115:21: ( modelDeclaration )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==93) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // Flock.g:0:0: modelDeclaration
            	    {
            	    pushFollow(FOLLOW_modelDeclaration_in_flockModule107);
            	    modelDeclaration2=modelDeclaration();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_modelDeclaration.add(modelDeclaration2.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // Flock.g:115:39: ( flockModuleContent )*
            loop3:
            do {
                int alt3=2;
                int LA3_0 = input.LA(1);

                if ( (LA3_0==Annotation||(LA3_0>=101 && LA3_0<=102)||LA3_0==107||LA3_0==122||(LA3_0>=161 && LA3_0<=162)||LA3_0==165||LA3_0==168) ) {
                    alt3=1;
                }


                switch (alt3) {
            	case 1 :
            	    // Flock.g:115:40: flockModuleContent
            	    {
            	    pushFollow(FOLLOW_flockModuleContent_in_flockModule111);
            	    flockModuleContent3=flockModuleContent();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) stream_flockModuleContent.add(flockModuleContent3.getTree());

            	    }
            	    break;

            	default :
            	    break loop3;
                }
            } while (true);

            EOF4=(Token)match(input,EOF,FOLLOW_EOF_in_flockModule115); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_EOF.add(EOF4);



            // AST REWRITE
            // elements: importStatement, modelDeclaration, flockModuleContent
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 116:2: -> ^( FLOCKMODULE ( importStatement )* ( modelDeclaration )* ( flockModuleContent )* )
            {
                // Flock.g:116:5: ^( FLOCKMODULE ( importStatement )* ( modelDeclaration )* ( flockModuleContent )* )
                {
                org.eclipse.epsilon.common.parse.AST root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
                root_1 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot((org.eclipse.epsilon.common.parse.AST)adaptor.create(FLOCKMODULE, "FLOCKMODULE"), root_1);

                // Flock.g:116:19: ( importStatement )*
                while ( stream_importStatement.hasNext() ) {
                    adaptor.addChild(root_1, stream_importStatement.nextTree());

                }
                stream_importStatement.reset();
                // Flock.g:116:36: ( modelDeclaration )*
                while ( stream_modelDeclaration.hasNext() ) {
                    adaptor.addChild(root_1, stream_modelDeclaration.nextTree());

                }
                stream_modelDeclaration.reset();
                // Flock.g:116:54: ( flockModuleContent )*
                while ( stream_flockModuleContent.hasNext() ) {
                    adaptor.addChild(root_1, stream_flockModuleContent.nextTree());

                }
                stream_flockModuleContent.reset();

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
    // $ANTLR end flockModule

    public static class flockModuleContent_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start flockModuleContent
    // Flock.g:119:1: flockModuleContent : ( retyping | deletion | migrateRule | erlModuleContent );
    public final FlockParser.flockModuleContent_return flockModuleContent() throws RecognitionException {
        FlockParser.flockModuleContent_return retval = new FlockParser.flockModuleContent_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        FlockParser.retyping_return retyping5 = null;

        FlockParser.deletion_return deletion6 = null;

        FlockParser.migrateRule_return migrateRule7 = null;

        Flock_ErlParserRules.erlModuleContent_return erlModuleContent8 = null;



        try {
            // Flock.g:120:2: ( retyping | deletion | migrateRule | erlModuleContent )
            int alt4=4;
            switch ( input.LA(1) ) {
            case 165:
                {
                alt4=1;
                }
                break;
            case 122:
                {
                alt4=2;
                }
                break;
            case 168:
                {
                alt4=3;
                }
                break;
            case Annotation:
            case 101:
            case 102:
            case 107:
            case 161:
            case 162:
                {
                alt4=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }

            switch (alt4) {
                case 1 :
                    // Flock.g:120:4: retyping
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_retyping_in_flockModuleContent142);
                    retyping5=retyping();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, retyping5.getTree());

                    }
                    break;
                case 2 :
                    // Flock.g:120:15: deletion
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_deletion_in_flockModuleContent146);
                    deletion6=deletion();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, deletion6.getTree());

                    }
                    break;
                case 3 :
                    // Flock.g:120:26: migrateRule
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_migrateRule_in_flockModuleContent150);
                    migrateRule7=migrateRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, migrateRule7.getTree());

                    }
                    break;
                case 4 :
                    // Flock.g:120:40: erlModuleContent
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_erlModuleContent_in_flockModuleContent154);
                    erlModuleContent8=erlModuleContent();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, erlModuleContent8.getTree());

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
    // $ANTLR end flockModuleContent

    public static class retyping_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start retyping
    // Flock.g:123:1: retyping : ( retyping_package | retyping_classifier );
    public final FlockParser.retyping_return retyping() throws RecognitionException {
        FlockParser.retyping_return retval = new FlockParser.retyping_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        FlockParser.retyping_package_return retyping_package9 = null;

        FlockParser.retyping_classifier_return retyping_classifier10 = null;



        try {
            // Flock.g:124:2: ( retyping_package | retyping_classifier )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==165) ) {
                int LA5_1 = input.LA(2);

                if ( (LA5_1==166) ) {
                    alt5=1;
                }
                else if ( (LA5_1==NAME) ) {
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
                    // Flock.g:124:4: retyping_package
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_retyping_package_in_retyping165);
                    retyping_package9=retyping_package();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, retyping_package9.getTree());

                    }
                    break;
                case 2 :
                    // Flock.g:124:23: retyping_classifier
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_retyping_classifier_in_retyping169);
                    retyping_classifier10=retyping_classifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, retyping_classifier10.getTree());

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
    // $ANTLR end retyping

    public static class retyping_package_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start retyping_package
    // Flock.g:126:1: retyping_package : rt= 'retype' 'package' originalPackage= NAME 'to' migratedPackage= NAME ( guard )? ;
    public final FlockParser.retyping_package_return retyping_package() throws RecognitionException {
        FlockParser.retyping_package_return retval = new FlockParser.retyping_package_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token rt=null;
        Token originalPackage=null;
        Token migratedPackage=null;
        Token string_literal11=null;
        Token string_literal12=null;
        FlockParser.guard_return guard13 = null;


        org.eclipse.epsilon.common.parse.AST rt_tree=null;
        org.eclipse.epsilon.common.parse.AST originalPackage_tree=null;
        org.eclipse.epsilon.common.parse.AST migratedPackage_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal11_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal12_tree=null;

        try {
            // Flock.g:127:2: (rt= 'retype' 'package' originalPackage= NAME 'to' migratedPackage= NAME ( guard )? )
            // Flock.g:127:4: rt= 'retype' 'package' originalPackage= NAME 'to' migratedPackage= NAME ( guard )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            rt=(Token)match(input,165,FOLLOW_165_in_retyping_package180); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rt_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(rt);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(rt_tree, root_0);
            }
            string_literal11=(Token)match(input,166,FOLLOW_166_in_retyping_package183); if (state.failed) return retval;
            originalPackage=(Token)match(input,NAME,FOLLOW_NAME_in_retyping_package188); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            originalPackage_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(originalPackage);
            adaptor.addChild(root_0, originalPackage_tree);
            }
            string_literal12=(Token)match(input,167,FOLLOW_167_in_retyping_package190); if (state.failed) return retval;
            migratedPackage=(Token)match(input,NAME,FOLLOW_NAME_in_retyping_package195); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            migratedPackage_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(migratedPackage);
            adaptor.addChild(root_0, migratedPackage_tree);
            }
            // Flock.g:127:76: ( guard )?
            int alt6=2;
            int LA6_0 = input.LA(1);

            if ( (LA6_0==170) ) {
                alt6=1;
            }
            switch (alt6) {
                case 1 :
                    // Flock.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_retyping_package197);
                    guard13=guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard13.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              rt.setType(RETYPEPACKAGE); rt.setText("RETYPEPACKAGE");
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
    // $ANTLR end retyping_package

    public static class retyping_classifier_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start retyping_classifier
    // Flock.g:131:1: retyping_classifier : rt= 'retype' originalType= packagedType 'to' migratedType= packagedType ( guard )? ;
    public final FlockParser.retyping_classifier_return retyping_classifier() throws RecognitionException {
        FlockParser.retyping_classifier_return retval = new FlockParser.retyping_classifier_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token rt=null;
        Token string_literal14=null;
        Flock_EolParserRules.packagedType_return originalType = null;

        Flock_EolParserRules.packagedType_return migratedType = null;

        FlockParser.guard_return guard15 = null;


        org.eclipse.epsilon.common.parse.AST rt_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal14_tree=null;

        try {
            // Flock.g:132:2: (rt= 'retype' originalType= packagedType 'to' migratedType= packagedType ( guard )? )
            // Flock.g:132:4: rt= 'retype' originalType= packagedType 'to' migratedType= packagedType ( guard )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            rt=(Token)match(input,165,FOLLOW_165_in_retyping_classifier221); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rt_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(rt);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(rt_tree, root_0);
            }
            pushFollow(FOLLOW_packagedType_in_retyping_classifier226);
            originalType=packagedType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, originalType.getTree());
            string_literal14=(Token)match(input,167,FOLLOW_167_in_retyping_classifier228); if (state.failed) return retval;
            pushFollow(FOLLOW_packagedType_in_retyping_classifier233);
            migratedType=packagedType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, migratedType.getTree());
            // Flock.g:132:75: ( guard )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==170) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // Flock.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_retyping_classifier235);
                    guard15=guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard15.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              rt.setType(RETYPE); rt.setText("RETYPE");
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
    // $ANTLR end retyping_classifier

    public static class deletion_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start deletion
    // Flock.g:136:1: deletion : ( deletion_package | deletion_classifier );
    public final FlockParser.deletion_return deletion() throws RecognitionException {
        FlockParser.deletion_return retval = new FlockParser.deletion_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        FlockParser.deletion_package_return deletion_package16 = null;

        FlockParser.deletion_classifier_return deletion_classifier17 = null;



        try {
            // Flock.g:137:2: ( deletion_package | deletion_classifier )
            int alt8=2;
            int LA8_0 = input.LA(1);

            if ( (LA8_0==122) ) {
                int LA8_1 = input.LA(2);

                if ( (LA8_1==166) ) {
                    alt8=1;
                }
                else if ( (LA8_1==NAME) ) {
                    alt8=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 8, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 8, 0, input);

                throw nvae;
            }
            switch (alt8) {
                case 1 :
                    // Flock.g:137:4: deletion_package
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_deletion_package_in_deletion256);
                    deletion_package16=deletion_package();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, deletion_package16.getTree());

                    }
                    break;
                case 2 :
                    // Flock.g:137:23: deletion_classifier
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_deletion_classifier_in_deletion260);
                    deletion_classifier17=deletion_classifier();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, deletion_classifier17.getTree());

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
    // $ANTLR end deletion

    public static class deletion_package_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start deletion_package
    // Flock.g:139:1: deletion_package : del= 'delete' 'package' pkg= NAME ( guard )? ;
    public final FlockParser.deletion_package_return deletion_package() throws RecognitionException {
        FlockParser.deletion_package_return retval = new FlockParser.deletion_package_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token del=null;
        Token pkg=null;
        Token string_literal18=null;
        FlockParser.guard_return guard19 = null;


        org.eclipse.epsilon.common.parse.AST del_tree=null;
        org.eclipse.epsilon.common.parse.AST pkg_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal18_tree=null;

        try {
            // Flock.g:140:3: (del= 'delete' 'package' pkg= NAME ( guard )? )
            // Flock.g:140:5: del= 'delete' 'package' pkg= NAME ( guard )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            del=(Token)match(input,122,FOLLOW_122_in_deletion_package272); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            del_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(del);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(del_tree, root_0);
            }
            string_literal18=(Token)match(input,166,FOLLOW_166_in_deletion_package275); if (state.failed) return retval;
            pkg=(Token)match(input,NAME,FOLLOW_NAME_in_deletion_package280); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            pkg_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(pkg);
            adaptor.addChild(root_0, pkg_tree);
            }
            // Flock.g:140:39: ( guard )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==170) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // Flock.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_deletion_package282);
                    guard19=guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard19.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              del.setType(DELETEPACKAGE); del.setText("DELETEPACKAGE");
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
    // $ANTLR end deletion_package

    public static class deletion_classifier_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start deletion_classifier
    // Flock.g:144:1: deletion_classifier : del= 'delete' type= packagedType ( guard )? ;
    public final FlockParser.deletion_classifier_return deletion_classifier() throws RecognitionException {
        FlockParser.deletion_classifier_return retval = new FlockParser.deletion_classifier_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token del=null;
        Flock_EolParserRules.packagedType_return type = null;

        FlockParser.guard_return guard20 = null;


        org.eclipse.epsilon.common.parse.AST del_tree=null;

        try {
            // Flock.g:145:3: (del= 'delete' type= packagedType ( guard )? )
            // Flock.g:145:5: del= 'delete' type= packagedType ( guard )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            del=(Token)match(input,122,FOLLOW_122_in_deletion_classifier305); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            del_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(del);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(del_tree, root_0);
            }
            pushFollow(FOLLOW_packagedType_in_deletion_classifier310);
            type=packagedType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, type.getTree());
            // Flock.g:145:37: ( guard )?
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==170) ) {
                alt10=1;
            }
            switch (alt10) {
                case 1 :
                    // Flock.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_deletion_classifier312);
                    guard20=guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard20.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              del.setType(DELETE); del.setText("DELETE");
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
    // $ANTLR end deletion_classifier

    public static class migrateRule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start migrateRule
    // Flock.g:150:1: migrateRule : ( fullRule | ignoringRule );
    public final FlockParser.migrateRule_return migrateRule() throws RecognitionException {
        FlockParser.migrateRule_return retval = new FlockParser.migrateRule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        FlockParser.fullRule_return fullRule21 = null;

        FlockParser.ignoringRule_return ignoringRule22 = null;



        try {
            // Flock.g:151:3: ( fullRule | ignoringRule )
            int alt11=2;
            int LA11_0 = input.LA(1);

            if ( (LA11_0==168) ) {
                int LA11_1 = input.LA(2);

                if ( (synpred13_Flock()) ) {
                    alt11=1;
                }
                else if ( (true) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }
            switch (alt11) {
                case 1 :
                    // Flock.g:151:5: fullRule
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_fullRule_in_migrateRule334);
                    fullRule21=fullRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, fullRule21.getTree());

                    }
                    break;
                case 2 :
                    // Flock.g:151:16: ignoringRule
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_ignoringRule_in_migrateRule338);
                    ignoringRule22=ignoringRule();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ignoringRule22.getTree());

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
    // $ANTLR end migrateRule

    public static class fullRule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start fullRule
    // Flock.g:153:1: fullRule : mig= 'migrate' originalType= packagedType ( ignoring )? ( guard )? ob= '{' body= block cb= '}' ;
    public final FlockParser.fullRule_return fullRule() throws RecognitionException {
        FlockParser.fullRule_return retval = new FlockParser.fullRule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token mig=null;
        Token ob=null;
        Token cb=null;
        Flock_EolParserRules.packagedType_return originalType = null;

        Flock_EolParserRules.block_return body = null;

        FlockParser.ignoring_return ignoring23 = null;

        FlockParser.guard_return guard24 = null;


        org.eclipse.epsilon.common.parse.AST mig_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;

        try {
            // Flock.g:158:3: (mig= 'migrate' originalType= packagedType ( ignoring )? ( guard )? ob= '{' body= block cb= '}' )
            // Flock.g:158:5: mig= 'migrate' originalType= packagedType ( ignoring )? ( guard )? ob= '{' body= block cb= '}'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            mig=(Token)match(input,168,FOLLOW_168_in_fullRule359); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            mig_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(mig);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(mig_tree, root_0);
            }
            pushFollow(FOLLOW_packagedType_in_fullRule364);
            originalType=packagedType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, originalType.getTree());
            // Flock.g:158:46: ( ignoring )?
            int alt12=2;
            int LA12_0 = input.LA(1);

            if ( (LA12_0==169) ) {
                alt12=1;
            }
            switch (alt12) {
                case 1 :
                    // Flock.g:0:0: ignoring
                    {
                    pushFollow(FOLLOW_ignoring_in_fullRule366);
                    ignoring23=ignoring();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, ignoring23.getTree());

                    }
                    break;

            }

            // Flock.g:158:56: ( guard )?
            int alt13=2;
            int LA13_0 = input.LA(1);

            if ( (LA13_0==170) ) {
                alt13=1;
            }
            switch (alt13) {
                case 1 :
                    // Flock.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_fullRule369);
                    guard24=guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard24.getTree());

                    }
                    break;

            }

            ob=(Token)match(input,98,FOLLOW_98_in_fullRule374); if (state.failed) return retval;
            pushFollow(FOLLOW_block_in_fullRule379);
            body=block();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, body.getTree());
            cb=(Token)match(input,99,FOLLOW_99_in_fullRule383); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              mig.setType(MIGRATE); mig.setText("MIGRATE");
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
    // $ANTLR end fullRule

    public static class ignoringRule_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ignoringRule
    // Flock.g:162:1: ignoringRule : mig= 'migrate' originalType= packagedType ignoring ( guard )? ;
    public final FlockParser.ignoringRule_return ignoringRule() throws RecognitionException {
        FlockParser.ignoringRule_return retval = new FlockParser.ignoringRule_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token mig=null;
        Flock_EolParserRules.packagedType_return originalType = null;

        FlockParser.ignoring_return ignoring25 = null;

        FlockParser.guard_return guard26 = null;


        org.eclipse.epsilon.common.parse.AST mig_tree=null;

        try {
            // Flock.g:163:3: (mig= 'migrate' originalType= packagedType ignoring ( guard )? )
            // Flock.g:163:5: mig= 'migrate' originalType= packagedType ignoring ( guard )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            mig=(Token)match(input,168,FOLLOW_168_in_ignoringRule411); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            mig_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(mig);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(mig_tree, root_0);
            }
            pushFollow(FOLLOW_packagedType_in_ignoringRule416);
            originalType=packagedType();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, originalType.getTree());
            pushFollow(FOLLOW_ignoring_in_ignoringRule418);
            ignoring25=ignoring();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, ignoring25.getTree());
            // Flock.g:163:55: ( guard )?
            int alt14=2;
            int LA14_0 = input.LA(1);

            if ( (LA14_0==170) ) {
                alt14=1;
            }
            switch (alt14) {
                case 1 :
                    // Flock.g:0:0: guard
                    {
                    pushFollow(FOLLOW_guard_in_ignoringRule420);
                    guard26=guard();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard26.getTree());

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              mig.setType(MIGRATE); mig.setText("MIGRATE");
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
    // $ANTLR end ignoringRule

    public static class ignoring_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start ignoring
    // Flock.g:167:1: ignoring : ign= 'ignoring' propertyList ;
    public final FlockParser.ignoring_return ignoring() throws RecognitionException {
        FlockParser.ignoring_return retval = new FlockParser.ignoring_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token ign=null;
        FlockParser.propertyList_return propertyList27 = null;


        org.eclipse.epsilon.common.parse.AST ign_tree=null;

        try {
            // Flock.g:168:3: (ign= 'ignoring' propertyList )
            // Flock.g:168:5: ign= 'ignoring' propertyList
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            ign=(Token)match(input,169,FOLLOW_169_in_ignoring444); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ign_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(ign);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(ign_tree, root_0);
            }
            pushFollow(FOLLOW_propertyList_in_ignoring447);
            propertyList27=propertyList();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, propertyList27.getTree());
            if ( state.backtracking==0 ) {
              ign.setType(IGNORING); ign.setText("IGNORING");
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
    // $ANTLR end ignoring

    public static class propertyList_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start propertyList
    // Flock.g:172:1: propertyList : NAME ( ',' NAME )* -> ( NAME )* ;
    public final FlockParser.propertyList_return propertyList() throws RecognitionException {
        FlockParser.propertyList_return retval = new FlockParser.propertyList_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token NAME28=null;
        Token char_literal29=null;
        Token NAME30=null;

        org.eclipse.epsilon.common.parse.AST NAME28_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal29_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME30_tree=null;
        RewriteRuleTokenStream stream_96=new RewriteRuleTokenStream(adaptor,"token 96");
        RewriteRuleTokenStream stream_NAME=new RewriteRuleTokenStream(adaptor,"token NAME");

        try {
            // Flock.g:173:3: ( NAME ( ',' NAME )* -> ( NAME )* )
            // Flock.g:173:5: NAME ( ',' NAME )*
            {
            NAME28=(Token)match(input,NAME,FOLLOW_NAME_in_propertyList467); if (state.failed) return retval; 
            if ( state.backtracking==0 ) stream_NAME.add(NAME28);

            // Flock.g:173:10: ( ',' NAME )*
            loop15:
            do {
                int alt15=2;
                int LA15_0 = input.LA(1);

                if ( (LA15_0==96) ) {
                    alt15=1;
                }


                switch (alt15) {
            	case 1 :
            	    // Flock.g:173:11: ',' NAME
            	    {
            	    char_literal29=(Token)match(input,96,FOLLOW_96_in_propertyList470); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_96.add(char_literal29);

            	    NAME30=(Token)match(input,NAME,FOLLOW_NAME_in_propertyList472); if (state.failed) return retval; 
            	    if ( state.backtracking==0 ) stream_NAME.add(NAME30);


            	    }
            	    break;

            	default :
            	    break loop15;
                }
            } while (true);



            // AST REWRITE
            // elements: NAME
            // token labels: 
            // rule labels: retval
            // token list labels: 
            // rule list labels: 
            if ( state.backtracking==0 ) {
            retval.tree = root_0;
            RewriteRuleSubtreeStream stream_retval=new RewriteRuleSubtreeStream(adaptor,"token retval",retval!=null?retval.tree:null);

            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();
            // 174:3: -> ( NAME )*
            {
                // Flock.g:175:3: ( NAME )*
                while ( stream_NAME.hasNext() ) {
                    adaptor.addChild(root_0, stream_NAME.nextNode());

                }
                stream_NAME.reset();

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
    // $ANTLR end propertyList

    public static class guard_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start guard
    // Flock.g:177:1: guard : w= 'when' expressionOrStatementBlock ;
    public final FlockParser.guard_return guard() throws RecognitionException {
        FlockParser.guard_return retval = new FlockParser.guard_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token w=null;
        Flock_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock31 = null;


        org.eclipse.epsilon.common.parse.AST w_tree=null;

        try {
            // Flock.g:178:3: (w= 'when' expressionOrStatementBlock )
            // Flock.g:178:5: w= 'when' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            w=(Token)match(input,170,FOLLOW_170_in_guard495); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            w_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(w);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(w_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_guard498);
            expressionOrStatementBlock31=expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock31.getTree());
            if ( state.backtracking==0 ) {
              w.setType(GUARD);  w.setText("GUARD");
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

    // $ANTLR start synpred13_Flock
    public final void synpred13_Flock_fragment() throws RecognitionException {   
        // Flock.g:151:5: ( fullRule )
        // Flock.g:151:5: fullRule
        {
        pushFollow(FOLLOW_fullRule_in_synpred13_Flock334);
        fullRule();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred13_Flock

    // Delegated rules
    public Flock_EolParserRules.relationalExpression_return relationalExpression() throws RecognitionException { return gEolParserRules.relationalExpression(); }
    public Flock_EolParserRules.statementA_return statementA() throws RecognitionException { return gEolParserRules.statementA(); }
    public Flock_EolParserRules.newExpression_return newExpression() throws RecognitionException { return gEolParserRules.newExpression(); }
    public Flock_EolParserRules.itemSelectorExpression_return itemSelectorExpression() throws RecognitionException { return gEolParserRules.itemSelectorExpression(); }
    public Flock_EolParserRules.keyvalExpressionList_return keyvalExpressionList() throws RecognitionException { return gEolParserRules.keyvalExpressionList(); }
    public Flock_EolParserRules.collectionType_return collectionType() throws RecognitionException { return gEolParserRules.collectionType(); }
    public Flock_EolParserRules.abortStatement_return abortStatement() throws RecognitionException { return gEolParserRules.abortStatement(); }
    public Flock_EolParserRules.keyvalExpression_return keyvalExpression() throws RecognitionException { return gEolParserRules.keyvalExpression(); }
    public Flock_EolParserRules.whileStatement_return whileStatement() throws RecognitionException { return gEolParserRules.whileStatement(); }
    public Flock_ErlParserRules.extendz_return extendz() throws RecognitionException { return gErlParserRules.extendz(); }
    public Flock_EolParserRules.modelDeclarationParameters_return modelDeclarationParameters() throws RecognitionException { return gEolParserRules.modelDeclarationParameters(); }
    public Flock_EolParserRules.forStatement_return forStatement() throws RecognitionException { return gEolParserRules.forStatement(); }
    public Flock_EolParserRules.breakAllStatement_return breakAllStatement() throws RecognitionException { return gEolParserRules.breakAllStatement(); }
    public Flock_EolParserRules.variableDeclarationExpression_return variableDeclarationExpression() throws RecognitionException { return gEolParserRules.variableDeclarationExpression(); }
    public Flock_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock() throws RecognitionException { return gEolParserRules.expressionOrStatementBlock(); }
    public Flock_EolParserRules.literal_return literal() throws RecognitionException { return gEolParserRules.literal(); }
    public Flock_ErlParserRules.post_return post() throws RecognitionException { return gErlParserRules.post(); }
    public Flock_EolParserRules.shortcutOperatorExpression_return shortcutOperatorExpression() throws RecognitionException { return gEolParserRules.shortcutOperatorExpression(); }
    public Flock_EolParserRules.lambdaExpression_return lambdaExpression() throws RecognitionException { return gEolParserRules.lambdaExpression(); }
    public Flock_EolParserRules.typeName_return typeName() throws RecognitionException { return gEolParserRules.typeName(); }
    public Flock_EolParserRules.statementB_return statementB() throws RecognitionException { return gEolParserRules.statementB(); }
    public Flock_EolParserRules.primitiveExpression_return primitiveExpression() throws RecognitionException { return gEolParserRules.primitiveExpression(); }
    public Flock_EolParserRules.literalSequentialCollection_return literalSequentialCollection() throws RecognitionException { return gEolParserRules.literalSequentialCollection(); }
    public Flock_EolParserRules.complexFeatureCall_return complexFeatureCall() throws RecognitionException { return gEolParserRules.complexFeatureCall(); }
    public Flock_EolParserRules.statementBlock_return statementBlock() throws RecognitionException { return gEolParserRules.statementBlock(); }
    public Flock_EolParserRules.statement_return statement() throws RecognitionException { return gEolParserRules.statement(); }
    public Flock_EolParserRules.formalParameter_return formalParameter() throws RecognitionException { return gEolParserRules.formalParameter(); }
    public Flock_EolParserRules.postfixExpression_return postfixExpression() throws RecognitionException { return gEolParserRules.postfixExpression(); }
    public Flock_EolParserRules.packagedType_return packagedType() throws RecognitionException { return gEolParserRules.packagedType(); }
    public Flock_EolParserRules.multiplicativeExpression_return multiplicativeExpression() throws RecognitionException { return gEolParserRules.multiplicativeExpression(); }
    public Flock_EolParserRules.expressionList_return expressionList() throws RecognitionException { return gEolParserRules.expressionList(); }
    public Flock_EolParserRules.logicalExpressionInBrackets_return logicalExpressionInBrackets() throws RecognitionException { return gEolParserRules.logicalExpressionInBrackets(); }
    public Flock_EolParserRules.additiveExpression_return additiveExpression() throws RecognitionException { return gEolParserRules.additiveExpression(); }
    public Flock_EolParserRules.modelDeclarationParameter_return modelDeclarationParameter() throws RecognitionException { return gEolParserRules.modelDeclarationParameter(); }
    public Flock_EolParserRules.pathName_return pathName() throws RecognitionException { return gEolParserRules.pathName(); }
    public Flock_EolParserRules.lambdaExpressionInBrackets_return lambdaExpressionInBrackets() throws RecognitionException { return gEolParserRules.lambdaExpressionInBrackets(); }
    public Flock_EolParserRules.returnStatement_return returnStatement() throws RecognitionException { return gEolParserRules.returnStatement(); }
    public Flock_EolParserRules.modelDriver_return modelDriver() throws RecognitionException { return gEolParserRules.modelDriver(); }
    public Flock_EolParserRules.modelDeclaration_return modelDeclaration() throws RecognitionException { return gEolParserRules.modelDeclaration(); }
    public Flock_EolParserRules.executableAnnotation_return executableAnnotation() throws RecognitionException { return gEolParserRules.executableAnnotation(); }
    public Flock_EolParserRules.deleteStatement_return deleteStatement() throws RecognitionException { return gEolParserRules.deleteStatement(); }
    public Flock_EolParserRules.enumLiteral_return enumLiteral() throws RecognitionException { return gEolParserRules.enumLiteral(); }
    public Flock_EolParserRules.defaultStatement_return defaultStatement() throws RecognitionException { return gEolParserRules.defaultStatement(); }
    public Flock_EolParserRules.breakStatement_return breakStatement() throws RecognitionException { return gEolParserRules.breakStatement(); }
    public Flock_EolParserRules.expressionStatement_return expressionStatement() throws RecognitionException { return gEolParserRules.expressionStatement(); }
    public Flock_EolParserRules.caseStatement_return caseStatement() throws RecognitionException { return gEolParserRules.caseStatement(); }
    public Flock_EolParserRules.continueStatement_return continueStatement() throws RecognitionException { return gEolParserRules.continueStatement(); }
    public Flock_EolParserRules.parameterList_return parameterList() throws RecognitionException { return gEolParserRules.parameterList(); }
    public Flock_EolParserRules.specialType_return specialType() throws RecognitionException { return gEolParserRules.specialType(); }
    public Flock_EolParserRules.transactionStatement_return transactionStatement() throws RecognitionException { return gEolParserRules.transactionStatement(); }
    public Flock_EolParserRules.operationDeclaration_return operationDeclaration() throws RecognitionException { return gEolParserRules.operationDeclaration(); }
    public Flock_EolParserRules.throwStatement_return throwStatement() throws RecognitionException { return gEolParserRules.throwStatement(); }
    public Flock_EolParserRules.block_return block() throws RecognitionException { return gEolParserRules.block(); }
    public Flock_EolParserRules.simpleFeatureCall_return simpleFeatureCall() throws RecognitionException { return gEolParserRules.simpleFeatureCall(); }
    public Flock_ErlParserRules.pre_return pre() throws RecognitionException { return gErlParserRules.pre(); }
    public Flock_EolParserRules.featureCall_return featureCall() throws RecognitionException { return gEolParserRules.featureCall(); }
    public Flock_EolParserRules.expressionListOrRange_return expressionListOrRange() throws RecognitionException { return gEolParserRules.expressionListOrRange(); }
    public Flock_EolParserRules.statementOrStatementBlock_return statementOrStatementBlock() throws RecognitionException { return gEolParserRules.statementOrStatementBlock(); }
    public Flock_EolParserRules.literalMapCollection_return literalMapCollection() throws RecognitionException { return gEolParserRules.literalMapCollection(); }
    public Flock_EolParserRules.assignmentStatement_return assignmentStatement() throws RecognitionException { return gEolParserRules.assignmentStatement(); }
    public Flock_EolParserRules.switchStatement_return switchStatement() throws RecognitionException { return gEolParserRules.switchStatement(); }
    public Flock_EolParserRules.elseStatement_return elseStatement() throws RecognitionException { return gEolParserRules.elseStatement(); }
    public Flock_ErlParserRules.erlModuleContent_return erlModuleContent() throws RecognitionException { return gErlParserRules.erlModuleContent(); }
    public Flock_EolParserRules.annotation_return annotation() throws RecognitionException { return gEolParserRules.annotation(); }
    public Flock_EolParserRules.modelAlias_return modelAlias() throws RecognitionException { return gEolParserRules.modelAlias(); }
    public Flock_EolParserRules.importStatement_return importStatement() throws RecognitionException { return gEolParserRules.importStatement(); }
    public Flock_EolParserRules.formalParameterList_return formalParameterList() throws RecognitionException { return gEolParserRules.formalParameterList(); }
    public Flock_EolParserRules.annotationBlock_return annotationBlock() throws RecognitionException { return gEolParserRules.annotationBlock(); }
    public Flock_EolParserRules.expressionRange_return expressionRange() throws RecognitionException { return gEolParserRules.expressionRange(); }
    public Flock_EolParserRules.logicalExpression_return logicalExpression() throws RecognitionException { return gEolParserRules.logicalExpression(); }
    public Flock_EolParserRules.unaryExpression_return unaryExpression() throws RecognitionException { return gEolParserRules.unaryExpression(); }
    public Flock_EolParserRules.ifStatement_return ifStatement() throws RecognitionException { return gEolParserRules.ifStatement(); }
    public Flock_EolParserRules.operationDeclarationOrAnnotationBlock_return operationDeclarationOrAnnotationBlock() throws RecognitionException { return gEolParserRules.operationDeclarationOrAnnotationBlock(); }

    public final boolean synpred13_Flock() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_Flock_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


 

    public static final BitSet FOLLOW_importStatement_in_flockModule104 = new BitSet(new long[]{0x0000000008000000L,0x04000C6020000000L,0x0000012600000000L});
    public static final BitSet FOLLOW_modelDeclaration_in_flockModule107 = new BitSet(new long[]{0x0000000008000000L,0x0400086020000000L,0x0000012600000000L});
    public static final BitSet FOLLOW_flockModuleContent_in_flockModule111 = new BitSet(new long[]{0x0000000008000000L,0x0400086000000000L,0x0000012600000000L});
    public static final BitSet FOLLOW_EOF_in_flockModule115 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_retyping_in_flockModuleContent142 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deletion_in_flockModuleContent146 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_migrateRule_in_flockModuleContent150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_erlModuleContent_in_flockModuleContent154 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_retyping_package_in_retyping165 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_retyping_classifier_in_retyping169 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_165_in_retyping_package180 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_166_in_retyping_package183 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_retyping_package188 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_167_in_retyping_package190 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_retyping_package195 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_guard_in_retyping_package197 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_165_in_retyping_classifier221 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_packagedType_in_retyping_classifier226 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000008000000000L});
    public static final BitSet FOLLOW_167_in_retyping_classifier228 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_packagedType_in_retyping_classifier233 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_guard_in_retyping_classifier235 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deletion_package_in_deletion256 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_deletion_classifier_in_deletion260 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_deletion_package272 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000004000000000L});
    public static final BitSet FOLLOW_166_in_deletion_package275 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_deletion_package280 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_guard_in_deletion_package282 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_122_in_deletion_classifier305 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_packagedType_in_deletion_classifier310 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_guard_in_deletion_classifier312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fullRule_in_migrateRule334 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ignoringRule_in_migrateRule338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_168_in_fullRule359 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_packagedType_in_fullRule364 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L,0x0000060000000000L});
    public static final BitSet FOLLOW_ignoring_in_fullRule366 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_guard_in_fullRule369 = new BitSet(new long[]{0x0000000000000000L,0x0000000400000000L});
    public static final BitSet FOLLOW_98_in_fullRule374 = new BitSet(new long[]{0x00000000008EA110L,0xFFA5008800000000L,0x00000001E0480000L});
    public static final BitSet FOLLOW_block_in_fullRule379 = new BitSet(new long[]{0x0000000000000000L,0x0000000800000000L});
    public static final BitSet FOLLOW_99_in_fullRule383 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_168_in_ignoringRule411 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_packagedType_in_ignoringRule416 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000020000000000L});
    public static final BitSet FOLLOW_ignoring_in_ignoringRule418 = new BitSet(new long[]{0x0000000000000002L,0x0000000000000000L,0x0000040000000000L});
    public static final BitSet FOLLOW_guard_in_ignoringRule420 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_169_in_ignoring444 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_propertyList_in_ignoring447 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NAME_in_propertyList467 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
    public static final BitSet FOLLOW_96_in_propertyList470 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_propertyList472 = new BitSet(new long[]{0x0000000000000002L,0x0000000100000000L});
    public static final BitSet FOLLOW_170_in_guard495 = new BitSet(new long[]{0x0000000000000000L,0x0000020400000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_guard498 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_fullRule_in_synpred13_Flock334 = new BitSet(new long[]{0x0000000000000002L});

}