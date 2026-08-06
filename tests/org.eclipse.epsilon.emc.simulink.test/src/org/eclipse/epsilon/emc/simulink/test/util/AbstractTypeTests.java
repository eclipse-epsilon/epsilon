/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.emc.simulink.test.util;

public class AbstractTypeTests extends AbstractSimulinkTest {

	protected static final String BLOCK = "Block";

	protected enum Type {
		TYPE("var fo = new %s; assert(fo.type.println = '%s');"), 
		IS_KIND_OF("var fo = new %s; assert(fo.isKindOf('%s') and %s);"), 
		IS_TYPE_OF("var fo = new %s; assert(fo.isTypeOf('%s') and %s);"), 
		ALL("var fo = new %s; assert(%s.all.size().println = %d);"), 
		ALL_OF_TYPE("var fo = new %s; assert(%s.allOfKind.size().println = %d);"), // FIXME allOfKind() breaks; 
		ALL_OF_KIND("var fo = new %s; assert(%s.allOfType.size().println = %d);"); // FIXME allOfType() breaks;
		
		private String eol;
		private static final String  SF_PARENT_CHART	= "var chart = new `sflib/Chart`; ";
		
		Type(String eol){
			this.eol = eol;
		}
		
		public String eol(Object... args) {
			return String.format(eol, args);
		}
		public String eolNotEmptyConstructor(Object... args) {
			return SF_PARENT_CHART + String.format(eol, args);
		}
	}
	
	protected enum Collection {
		ALL("var fo = new %s; ", " assert(%s.all.size().println = %d);"), 
		ALL_OF_TYPE("var fo = new %s; ", "assert(%s.allOfKind.size().println = %d);"), // FIXME allOfKind() breaks; 
		ALL_OF_KIND("var fo = new %s; ", "assert(%s.allOfType.size().println = %d);"), // FIXME allOfType() breaks;
		COLLECT(	"var collection = %s.all; for (e in collection) { e.name.println; } ","assert(collection.size().println = %d);");
		
		private String prep;
		private String assertion;
		private static final String  SF_PARENT_CHART	= "var chart = new `sflib/Chart`; ";
		private static final String  SF_ADD	= "chart.add(fo); ";
		
		Collection(String prep, String assertion){
			this.prep = prep;
			this.assertion = assertion;
		}
		
		public String eol(Object... args) {
			return String.format(prep + assertion, args);
		}
		public String eolEmpty(Object... args) {
			return SF_PARENT_CHART + String.format(prep + SF_ADD, args);
		}
		
		public String collect(Object... args) {
			if (this.equals(COLLECT)) {
				return SF_PARENT_CHART + String.format(prep + SF_ADD, args);
			} else {
				return "";
			}
			
		}
		
		
	}
	
}
