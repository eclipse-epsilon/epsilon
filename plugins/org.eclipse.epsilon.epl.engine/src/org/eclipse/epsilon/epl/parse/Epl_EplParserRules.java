package org.eclipse.epsilon.epl.parse;

// $ANTLR 3.1b1 EplParserRules.g 2023-07-03 20:54:40

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
public class Epl_EplParserRules extends org.eclipse.epsilon.common.parse.EpsilonParser {
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
    public static final int DOMAIN=89;
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
    public static final int ROLE=90;
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
    public static final int T__100=100;
    public static final int NAMESPACE=74;
    public static final int COLLECTION=47;
    public static final int NEW=54;
    public static final int EXTENDS=85;
    public static final int T__102=102;
    public static final int PRE=83;
    public static final int T__101=101;
    public static final int POST=84;
    public static final int ALIAS=75;
    public static final int DRIVER=76;
    public static final int DO=94;
    public static final int OPTIONAL=96;
    public static final int KEYVAL=81;
    public static final int POINT_POINT=10;
    public static final int GUARD=86;
    public static final int T__99=99;
    public static final int HELPERMETHOD=32;
    public static final int StatementBlock=33;
    public static final int T__177=177;
    public static final int T__176=176;
    public static final int T__179=179;
    public static final int T__178=178;
    public static final int ABORT=48;
    public static final int T__173=173;
    public static final int StrangeNameLiteral=16;
    public static final int ONMATCH=93;
    public static final int T__172=172;
    public static final int EPLMODULE=98;
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
    public static final int NO=95;
    public static final int T__124=124;
    public static final int FLOAT=4;
    public static final int T__123=123;
    public static final int ACTIVE=97;
    public static final int T__120=120;
    public static final int NativeType=61;
    public static final int INT=8;
    public static final int ANNOTATIONBLOCK=55;
    public static final int RETURN=42;
    public static final int KEYVALLIST=82;
    public static final int PATTERN=87;
    public static final int NOMATCH=92;
    public static final int FEATURECALL=68;
    public static final int CollectionType=49;
    public static final int T__119=119;
    public static final int ASSIGNMENT=30;
    public static final int T__118=118;
    public static final int CARDINALITY=88;
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
    public static final int MATCH=91;
    public static final int T__105=105;

    // delegates
    // delegators
    public EplParser gEpl;


        public Epl_EplParserRules(TokenStream input, EplParser gEpl) {
            this(input, new RecognizerSharedState(), gEpl);
        }
        public Epl_EplParserRules(TokenStream input, RecognizerSharedState state, EplParser gEpl) {
            super(input, state);
            this.gEpl = gEpl;
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return EplParser.tokenNames; }
    public String getGrammarFileName() { return "EplParserRules.g"; }



    public void setTokenType(ParserRuleReturnScope tree, int type) {
    	((CommonTree) tree.getTree()).getToken().setType(type);
    }



    public static class eplModuleContent_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start eplModuleContent
    // EplParserRules.g:64:1: eplModuleContent : ( pattern | erlModuleContent );
    public final Epl_EplParserRules.eplModuleContent_return eplModuleContent() throws RecognitionException {
        Epl_EplParserRules.eplModuleContent_return retval = new Epl_EplParserRules.eplModuleContent_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Epl_EplParserRules.pattern_return pattern1 = null;

        Epl_ErlParserRules.erlModuleContent_return erlModuleContent2 = null;



        try {
            // EplParserRules.g:65:2: ( pattern | erlModuleContent )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==171) ) {
                alt1=1;
            }
            else if ( (LA1_0==Annotation||(LA1_0>=107 && LA1_0<=108)||LA1_0==113||(LA1_0>=167 && LA1_0<=168)) ) {
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
                    // EplParserRules.g:65:4: pattern
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_pattern_in_eplModuleContent86);
                    pattern1=pattern();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, pattern1.getTree());

                    }
                    break;
                case 2 :
                    // EplParserRules.g:65:14: erlModuleContent
                    {
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

                    pushFollow(FOLLOW_erlModuleContent_in_eplModuleContent90);
                    erlModuleContent2=gEpl.erlModuleContent();

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
    // $ANTLR end eplModuleContent

    public static class pattern_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start pattern
    // EplParserRules.g:68:1: pattern : pt= 'pattern' NAME role ( ',' role )* (ob= '{' ( match | do_ | nomatch | onmatch )* cb= '}' )? ;
    public final Epl_EplParserRules.pattern_return pattern() throws RecognitionException {
        Epl_EplParserRules.pattern_return retval = new Epl_EplParserRules.pattern_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token pt=null;
        Token ob=null;
        Token cb=null;
        Token NAME3=null;
        Token char_literal5=null;
        Epl_EplParserRules.role_return role4 = null;

        Epl_EplParserRules.role_return role6 = null;

        Epl_EplParserRules.match_return match7 = null;

        Epl_EplParserRules.do__return do_8 = null;

        Epl_EplParserRules.nomatch_return nomatch9 = null;

        Epl_EplParserRules.onmatch_return onmatch10 = null;


        org.eclipse.epsilon.common.parse.AST pt_tree=null;
        org.eclipse.epsilon.common.parse.AST ob_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME3_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal5_tree=null;

        try {
            // EplParserRules.g:74:2: (pt= 'pattern' NAME role ( ',' role )* (ob= '{' ( match | do_ | nomatch | onmatch )* cb= '}' )? )
            // EplParserRules.g:75:2: pt= 'pattern' NAME role ( ',' role )* (ob= '{' ( match | do_ | nomatch | onmatch )* cb= '}' )?
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            pt=(Token)match(input,171,FOLLOW_171_in_pattern110); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            pt_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(pt);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(pt_tree, root_0);
            }
            NAME3=(Token)match(input,NAME,FOLLOW_NAME_in_pattern113); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME3_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME3);
            adaptor.addChild(root_0, NAME3_tree);
            }
            pushFollow(FOLLOW_role_in_pattern115);
            role4=role();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, role4.getTree());
            // EplParserRules.g:75:26: ( ',' role )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( (LA2_0==102) ) {
                    alt2=1;
                }


                switch (alt2) {
            	case 1 :
            	    // EplParserRules.g:75:27: ',' role
            	    {
            	    char_literal5=(Token)match(input,102,FOLLOW_102_in_pattern118); if (state.failed) return retval;
            	    pushFollow(FOLLOW_role_in_pattern121);
            	    role6=role();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, role6.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
                }
            } while (true);

            // EplParserRules.g:75:39: (ob= '{' ( match | do_ | nomatch | onmatch )* cb= '}' )?
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==104) ) {
                alt4=1;
            }
            switch (alt4) {
                case 1 :
                    // EplParserRules.g:75:40: ob= '{' ( match | do_ | nomatch | onmatch )* cb= '}'
                    {
                    ob=(Token)match(input,104,FOLLOW_104_in_pattern128); if (state.failed) return retval;
                    // EplParserRules.g:75:48: ( match | do_ | nomatch | onmatch )*
                    loop3:
                    do {
                        int alt3=5;
                        switch ( input.LA(1) ) {
                        case 174:
                            {
                            alt3=1;
                            }
                            break;
                        case 177:
                            {
                            alt3=2;
                            }
                            break;
                        case 179:
                            {
                            alt3=3;
                            }
                            break;
                        case 178:
                            {
                            alt3=4;
                            }
                            break;

                        }

                        switch (alt3) {
                    	case 1 :
                    	    // EplParserRules.g:75:49: match
                    	    {
                    	    pushFollow(FOLLOW_match_in_pattern132);
                    	    match7=match();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, match7.getTree());

                    	    }
                    	    break;
                    	case 2 :
                    	    // EplParserRules.g:75:57: do_
                    	    {
                    	    pushFollow(FOLLOW_do__in_pattern136);
                    	    do_8=do_();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, do_8.getTree());

                    	    }
                    	    break;
                    	case 3 :
                    	    // EplParserRules.g:75:63: nomatch
                    	    {
                    	    pushFollow(FOLLOW_nomatch_in_pattern140);
                    	    nomatch9=nomatch();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, nomatch9.getTree());

                    	    }
                    	    break;
                    	case 4 :
                    	    // EplParserRules.g:75:73: onmatch
                    	    {
                    	    pushFollow(FOLLOW_onmatch_in_pattern144);
                    	    onmatch10=onmatch();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, onmatch10.getTree());

                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    cb=(Token)match(input,105,FOLLOW_105_in_pattern150); if (state.failed) return retval;

                    }
                    break;

            }

            if ( state.backtracking==0 ) {
              pt.setType(PATTERN);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

              		((org.eclipse.epsilon.common.parse.AST)retval.tree).getExtraTokens().add(pt);
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
    // $ANTLR end pattern

    public static class role_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start role
    // EplParserRules.g:79:1: role : ( no )? NAME ( ',' NAME )* n= ':' t= typeName ( cardinality )? ( domain | guard | optional | active )* ;
    public final Epl_EplParserRules.role_return role() throws RecognitionException {
        Epl_EplParserRules.role_return retval = new Epl_EplParserRules.role_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token n=null;
        Token NAME12=null;
        Token char_literal13=null;
        Token NAME14=null;
        Epl_EolParserRules.typeName_return t = null;

        Epl_EplParserRules.no_return no11 = null;

        Epl_EplParserRules.cardinality_return cardinality15 = null;

        Epl_EplParserRules.domain_return domain16 = null;

        Epl_ErlParserRules.guard_return guard17 = null;

        Epl_EplParserRules.optional_return optional18 = null;

        Epl_EplParserRules.active_return active19 = null;


        org.eclipse.epsilon.common.parse.AST n_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME12_tree=null;
        org.eclipse.epsilon.common.parse.AST char_literal13_tree=null;
        org.eclipse.epsilon.common.parse.AST NAME14_tree=null;

        try {
            // EplParserRules.g:80:2: ( ( no )? NAME ( ',' NAME )* n= ':' t= typeName ( cardinality )? ( domain | guard | optional | active )* )
            // EplParserRules.g:80:4: ( no )? NAME ( ',' NAME )* n= ':' t= typeName ( cardinality )? ( domain | guard | optional | active )*
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EplParserRules.g:80:4: ( no )?
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==172) ) {
                alt5=1;
            }
            switch (alt5) {
                case 1 :
                    // EplParserRules.g:0:0: no
                    {
                    pushFollow(FOLLOW_no_in_role167);
                    no11=no();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, no11.getTree());

                    }
                    break;

            }

            NAME12=(Token)match(input,NAME,FOLLOW_NAME_in_role170); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NAME12_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME12);
            adaptor.addChild(root_0, NAME12_tree);
            }
            // EplParserRules.g:80:13: ( ',' NAME )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==102) ) {
                    alt6=1;
                }


                switch (alt6) {
            	case 1 :
            	    // EplParserRules.g:80:14: ',' NAME
            	    {
            	    char_literal13=(Token)match(input,102,FOLLOW_102_in_role173); if (state.failed) return retval;
            	    NAME14=(Token)match(input,NAME,FOLLOW_NAME_in_role176); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    NAME14_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(NAME14);
            	    adaptor.addChild(root_0, NAME14_tree);
            	    }

            	    }
            	    break;

            	default :
            	    break loop6;
                }
            } while (true);

            n=(Token)match(input,111,FOLLOW_111_in_role182); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(n);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(n_tree, root_0);
            }
            pushFollow(FOLLOW_typeName_in_role187);
            t=gEpl.typeName();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, t.getTree());
            if ( state.backtracking==0 ) {
              setTokenType(t, TYPE);
            }
            // EplParserRules.g:80:69: ( cardinality )?
            int alt7=2;
            int LA7_0 = input.LA(1);

            if ( (LA7_0==159) ) {
                alt7=1;
            }
            switch (alt7) {
                case 1 :
                    // EplParserRules.g:0:0: cardinality
                    {
                    pushFollow(FOLLOW_cardinality_in_role191);
                    cardinality15=cardinality();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, cardinality15.getTree());

                    }
                    break;

            }

            // EplParserRules.g:80:82: ( domain | guard | optional | active )*
            loop8:
            do {
                int alt8=5;
                switch ( input.LA(1) ) {
                case 124:
                case 173:
                    {
                    alt8=1;
                    }
                    break;
                case 169:
                    {
                    alt8=2;
                    }
                    break;
                case 175:
                    {
                    alt8=3;
                    }
                    break;
                case 176:
                    {
                    alt8=4;
                    }
                    break;

                }

                switch (alt8) {
            	case 1 :
            	    // EplParserRules.g:80:83: domain
            	    {
            	    pushFollow(FOLLOW_domain_in_role195);
            	    domain16=domain();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, domain16.getTree());

            	    }
            	    break;
            	case 2 :
            	    // EplParserRules.g:80:92: guard
            	    {
            	    pushFollow(FOLLOW_guard_in_role199);
            	    guard17=gEpl.guard();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, guard17.getTree());

            	    }
            	    break;
            	case 3 :
            	    // EplParserRules.g:80:100: optional
            	    {
            	    pushFollow(FOLLOW_optional_in_role203);
            	    optional18=optional();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, optional18.getTree());

            	    }
            	    break;
            	case 4 :
            	    // EplParserRules.g:80:111: active
            	    {
            	    pushFollow(FOLLOW_active_in_role207);
            	    active19=active();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, active19.getTree());

            	    }
            	    break;

            	default :
            	    break loop8;
                }
            } while (true);

            if ( state.backtracking==0 ) {
              n.setType(ROLE);
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
    // $ANTLR end role

    public static class no_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start no
    // EplParserRules.g:84:1: no : n= 'no' ;
    public final Epl_EplParserRules.no_return no() throws RecognitionException {
        Epl_EplParserRules.no_return retval = new Epl_EplParserRules.no_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token n=null;

        org.eclipse.epsilon.common.parse.AST n_tree=null;

        try {
            // EplParserRules.g:84:4: (n= 'no' )
            // EplParserRules.g:84:6: n= 'no'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            n=(Token)match(input,172,FOLLOW_172_in_no224); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            n_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(n);
            adaptor.addChild(root_0, n_tree);
            }
            if ( state.backtracking==0 ) {
              n.setType(NO);
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
    // $ANTLR end no

    public static class cardinality_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start cardinality
    // EplParserRules.g:86:1: cardinality : c= '[' bound ( '..' bound )? cb= ']' ;
    public final Epl_EplParserRules.cardinality_return cardinality() throws RecognitionException {
        Epl_EplParserRules.cardinality_return retval = new Epl_EplParserRules.cardinality_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Token cb=null;
        Token string_literal21=null;
        Epl_EplParserRules.bound_return bound20 = null;

        Epl_EplParserRules.bound_return bound22 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;
        org.eclipse.epsilon.common.parse.AST cb_tree=null;
        org.eclipse.epsilon.common.parse.AST string_literal21_tree=null;

        try {
            // EplParserRules.g:90:2: (c= '[' bound ( '..' bound )? cb= ']' )
            // EplParserRules.g:90:4: c= '[' bound ( '..' bound )? cb= ']'
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,159,FOLLOW_159_in_cardinality243); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_bound_in_cardinality246);
            bound20=bound();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, bound20.getTree());
            // EplParserRules.g:90:17: ( '..' bound )?
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0==POINT_POINT) ) {
                alt9=1;
            }
            switch (alt9) {
                case 1 :
                    // EplParserRules.g:90:18: '..' bound
                    {
                    string_literal21=(Token)match(input,POINT_POINT,FOLLOW_POINT_POINT_in_cardinality249); if (state.failed) return retval;
                    pushFollow(FOLLOW_bound_in_cardinality252);
                    bound22=bound();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, bound22.getTree());

                    }
                    break;

            }

            cb=(Token)match(input,160,FOLLOW_160_in_cardinality258); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
              c.setType(CARDINALITY);
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (org.eclipse.epsilon.common.parse.AST)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
            if ( state.backtracking==0 ) {

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
    // $ANTLR end cardinality

    public static class bound_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start bound
    // EplParserRules.g:94:1: bound : ( INT | '*' );
    public final Epl_EplParserRules.bound_return bound() throws RecognitionException {
        Epl_EplParserRules.bound_return retval = new Epl_EplParserRules.bound_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token set23=null;

        org.eclipse.epsilon.common.parse.AST set23_tree=null;

        try {
            // EplParserRules.g:95:2: ( INT | '*' )
            // EplParserRules.g:
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            set23=(Token)input.LT(1);
            if ( input.LA(1)==INT||input.LA(1)==154 ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (org.eclipse.epsilon.common.parse.AST)adaptor.create(set23));
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
    // $ANTLR end bound

    public static class domain_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start domain
    // EplParserRules.g:98:1: domain : (c= 'in' | c= 'from' ) expressionOrStatementBlock ;
    public final Epl_EplParserRules.domain_return domain() throws RecognitionException {
        Epl_EplParserRules.domain_return retval = new Epl_EplParserRules.domain_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Epl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock24 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EplParserRules.g:98:8: ( (c= 'in' | c= 'from' ) expressionOrStatementBlock )
            // EplParserRules.g:99:2: (c= 'in' | c= 'from' ) expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            // EplParserRules.g:99:2: (c= 'in' | c= 'from' )
            int alt10=2;
            int LA10_0 = input.LA(1);

            if ( (LA10_0==124) ) {
                alt10=1;
            }
            else if ( (LA10_0==173) ) {
                alt10=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 10, 0, input);

                throw nvae;
            }
            switch (alt10) {
                case 1 :
                    // EplParserRules.g:99:3: c= 'in'
                    {
                    c=(Token)match(input,124,FOLLOW_124_in_domain291); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
                    }

                    }
                    break;
                case 2 :
                    // EplParserRules.g:99:11: c= 'from'
                    {
                    c=(Token)match(input,173,FOLLOW_173_in_domain296); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
                    root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
                    }

                    }
                    break;

            }

            pushFollow(FOLLOW_expressionOrStatementBlock_in_domain300);
            expressionOrStatementBlock24=gEpl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock24.getTree());
            if ( state.backtracking==0 ) {
              c.setType(DOMAIN);
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
    // $ANTLR end domain

    public static class match_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start match
    // EplParserRules.g:103:1: match : c= 'match' expressionOrStatementBlock ;
    public final Epl_EplParserRules.match_return match() throws RecognitionException {
        Epl_EplParserRules.match_return retval = new Epl_EplParserRules.match_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Epl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock25 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EplParserRules.g:103:7: (c= 'match' expressionOrStatementBlock )
            // EplParserRules.g:104:2: c= 'match' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,174,FOLLOW_174_in_match316); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_match319);
            expressionOrStatementBlock25=gEpl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock25.getTree());
            if ( state.backtracking==0 ) {
              c.setType(MATCH);
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
    // $ANTLR end match

    public static class optional_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start optional
    // EplParserRules.g:108:1: optional : c= 'optional' expressionOrStatementBlock ;
    public final Epl_EplParserRules.optional_return optional() throws RecognitionException {
        Epl_EplParserRules.optional_return retval = new Epl_EplParserRules.optional_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Epl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock26 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EplParserRules.g:108:10: (c= 'optional' expressionOrStatementBlock )
            // EplParserRules.g:109:2: c= 'optional' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,175,FOLLOW_175_in_optional335); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_optional338);
            expressionOrStatementBlock26=gEpl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock26.getTree());
            if ( state.backtracking==0 ) {
              c.setType(OPTIONAL);
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
    // $ANTLR end optional

    public static class active_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start active
    // EplParserRules.g:113:1: active : c= 'active' expressionOrStatementBlock ;
    public final Epl_EplParserRules.active_return active() throws RecognitionException {
        Epl_EplParserRules.active_return retval = new Epl_EplParserRules.active_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Epl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock27 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EplParserRules.g:113:8: (c= 'active' expressionOrStatementBlock )
            // EplParserRules.g:114:2: c= 'active' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,176,FOLLOW_176_in_active354); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_active357);
            expressionOrStatementBlock27=gEpl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock27.getTree());
            if ( state.backtracking==0 ) {
              c.setType(ACTIVE);
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
    // $ANTLR end active

    public static class do__return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start do_
    // EplParserRules.g:118:1: do_ : c= 'do' expressionOrStatementBlock ;
    public final Epl_EplParserRules.do__return do_() throws RecognitionException {
        Epl_EplParserRules.do__return retval = new Epl_EplParserRules.do__return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Epl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock28 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EplParserRules.g:118:5: (c= 'do' expressionOrStatementBlock )
            // EplParserRules.g:119:2: c= 'do' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,177,FOLLOW_177_in_do_373); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_do_376);
            expressionOrStatementBlock28=gEpl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock28.getTree());
            if ( state.backtracking==0 ) {
              c.setType(DO);
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
    // $ANTLR end do_

    public static class onmatch_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start onmatch
    // EplParserRules.g:123:1: onmatch : c= 'onmatch' expressionOrStatementBlock ;
    public final Epl_EplParserRules.onmatch_return onmatch() throws RecognitionException {
        Epl_EplParserRules.onmatch_return retval = new Epl_EplParserRules.onmatch_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Epl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock29 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EplParserRules.g:123:9: (c= 'onmatch' expressionOrStatementBlock )
            // EplParserRules.g:124:2: c= 'onmatch' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,178,FOLLOW_178_in_onmatch392); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_onmatch395);
            expressionOrStatementBlock29=gEpl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock29.getTree());
            if ( state.backtracking==0 ) {
              c.setType(ONMATCH);
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
    // $ANTLR end onmatch

    public static class nomatch_return extends ParserRuleReturnScope {
        org.eclipse.epsilon.common.parse.AST tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start nomatch
    // EplParserRules.g:128:1: nomatch : c= 'nomatch' expressionOrStatementBlock ;
    public final Epl_EplParserRules.nomatch_return nomatch() throws RecognitionException {
        Epl_EplParserRules.nomatch_return retval = new Epl_EplParserRules.nomatch_return();
        retval.start = input.LT(1);

        org.eclipse.epsilon.common.parse.AST root_0 = null;

        Token c=null;
        Epl_EolParserRules.expressionOrStatementBlock_return expressionOrStatementBlock30 = null;


        org.eclipse.epsilon.common.parse.AST c_tree=null;

        try {
            // EplParserRules.g:128:9: (c= 'nomatch' expressionOrStatementBlock )
            // EplParserRules.g:129:2: c= 'nomatch' expressionOrStatementBlock
            {
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.nil();

            c=(Token)match(input,179,FOLLOW_179_in_nomatch411); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            c_tree = (org.eclipse.epsilon.common.parse.AST)adaptor.create(c);
            root_0 = (org.eclipse.epsilon.common.parse.AST)adaptor.becomeRoot(c_tree, root_0);
            }
            pushFollow(FOLLOW_expressionOrStatementBlock_in_nomatch414);
            expressionOrStatementBlock30=gEpl.expressionOrStatementBlock();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expressionOrStatementBlock30.getTree());
            if ( state.backtracking==0 ) {
              c.setType(NOMATCH);
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
    // $ANTLR end nomatch

    // Delegated rules


 

    public static final BitSet FOLLOW_pattern_in_eplModuleContent86 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_erlModuleContent_in_eplModuleContent90 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_171_in_pattern110 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_pattern113 = new BitSet(new long[]{0x0000000000800000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_role_in_pattern115 = new BitSet(new long[]{0x0000000000000002L,0x0000014000000000L});
    public static final BitSet FOLLOW_102_in_pattern118 = new BitSet(new long[]{0x0000000000800000L,0x0000000000000000L,0x0000100000000000L});
    public static final BitSet FOLLOW_role_in_pattern121 = new BitSet(new long[]{0x0000000000000002L,0x0000014000000000L});
    public static final BitSet FOLLOW_104_in_pattern128 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x000E400000000000L});
    public static final BitSet FOLLOW_match_in_pattern132 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x000E400000000000L});
    public static final BitSet FOLLOW_do__in_pattern136 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x000E400000000000L});
    public static final BitSet FOLLOW_nomatch_in_pattern140 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x000E400000000000L});
    public static final BitSet FOLLOW_onmatch_in_pattern144 = new BitSet(new long[]{0x0000000000000000L,0x0000020000000000L,0x000E400000000000L});
    public static final BitSet FOLLOW_105_in_pattern150 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_no_in_role167 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_role170 = new BitSet(new long[]{0x0000000000000000L,0x0000804000000000L});
    public static final BitSet FOLLOW_102_in_role173 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_NAME_in_role176 = new BitSet(new long[]{0x0000000000000000L,0x0000804000000000L});
    public static final BitSet FOLLOW_111_in_role182 = new BitSet(new long[]{0x00000000008E0000L});
    public static final BitSet FOLLOW_typeName_in_role187 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L,0x0001A20080000000L});
    public static final BitSet FOLLOW_cardinality_in_role191 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L,0x0001A20000000000L});
    public static final BitSet FOLLOW_domain_in_role195 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L,0x0001A20000000000L});
    public static final BitSet FOLLOW_guard_in_role199 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L,0x0001A20000000000L});
    public static final BitSet FOLLOW_optional_in_role203 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L,0x0001A20000000000L});
    public static final BitSet FOLLOW_active_in_role207 = new BitSet(new long[]{0x0000000000000002L,0x1000000000000000L,0x0001A20000000000L});
    public static final BitSet FOLLOW_172_in_no224 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_159_in_cardinality243 = new BitSet(new long[]{0x0000000000000100L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_bound_in_cardinality246 = new BitSet(new long[]{0x0000000000000400L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_POINT_POINT_in_cardinality249 = new BitSet(new long[]{0x0000000000000100L,0x0000000000000000L,0x0000000004000000L});
    public static final BitSet FOLLOW_bound_in_cardinality252 = new BitSet(new long[]{0x0000000000000000L,0x0000000000000000L,0x0000000100000000L});
    public static final BitSet FOLLOW_160_in_cardinality258 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_bound0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_124_in_domain291 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_173_in_domain296 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_domain300 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_174_in_match316 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_match319 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_175_in_optional335 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_optional338 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_176_in_active354 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_active357 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_177_in_do_373 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_do_376 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_178_in_onmatch392 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_onmatch395 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_179_in_nomatch411 = new BitSet(new long[]{0x0000000000000000L,0x0000810000000000L});
    public static final BitSet FOLLOW_expressionOrStatementBlock_in_nomatch414 = new BitSet(new long[]{0x0000000000000002L});

}
