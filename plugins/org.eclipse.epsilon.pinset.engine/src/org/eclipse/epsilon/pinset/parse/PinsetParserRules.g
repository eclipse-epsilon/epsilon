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
parser grammar PinsetParserRules;

options {backtrack=true; output=AST;}

tokens {
  DATASET;
  NAMESLIST;
  ALIASEDNAME;
  PROPERTIES;
  COLUMN;
  GUARD;
  REFERENCE;
  GRID;
  GRIDKEYS;
  GRIDHEADER;
  GRIDBODY;
  FROM;
  NESTEDFROM;
}

datasetRule
  @after {
    $tree.getExtraTokens().add($ob);
    $tree.getExtraTokens().add($cb);
  }
  : r='dataset'^ NAME 'over'! formalParameter from? ob='{'!
    guard?
    properties?
    columnGenerator*
  cb='}'!
  {$r.setType(DATASET);}
  ;

columnGenerator
  : reference |
    annotationBlock? column |
    annotationBlock? grid |
    nestedFrom
  ;

nestedFrom
  : nf='from'^ NAME expressionOrStatementBlock '{'!
    properties?
    columnGenerator*
  '}'!
  {$nf.setType(NESTEDFROM);}
  ;

nameslist
   @after {
    $tree.getExtraTokens().add($cb);
  }
 : nl='['^ aliasedName (','! aliasedName)* cb=']'!
   {$nl.setType(NAMESLIST);}
 ;

aliasedName
 : an=NAME^ ('as'! NAME)?
   {$an.setType(ALIASEDNAME);}
 ;

properties
  :
  sf='properties'^ nameslist
  {$sf.setType(PROPERTIES);}
  ;

reference
  : r='reference'^ NAME nameslist
  {$r.setType(REFERENCE);}
  ;

column
  : cd='column'^ NAME expressionOrStatementBlock
  {$cd.setType(COLUMN);}
  ;

grid
  @after {
    $tree.getExtraTokens().add($ob);
    $tree.getExtraTokens().add($cb);
  }
  : cd='grid'^ ob='{'
    gkeys
    header
    gbody
  cb='}'
  {$cd.setType(GRID);}
  ;

gkeys
  : gk='keys'^ expressionOrStatementBlock
  {$gk.setType(GRIDKEYS);}
  ;

header
  : gh='header'^ expressionOrStatementBlock
  {$gh.setType(GRIDHEADER);}
  ;

gbody
  : gb='body'^ expressionOrStatementBlock
  {$gb.setType(GRIDBODY);}
  ;

from
  : ff='from'^ expressionOrStatementBlock
  {$ff.setType(FROM);}
  ;
