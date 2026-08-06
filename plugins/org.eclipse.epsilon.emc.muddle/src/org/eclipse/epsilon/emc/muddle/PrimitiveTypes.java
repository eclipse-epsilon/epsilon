/*******************************************************************************
 * Copyright (c) 2014 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitris Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.emc.muddle;

public class PrimitiveTypes {
	
	protected static IntegerType integerType = MuddleFactory.eINSTANCE.createIntegerType();
	protected static StringType stringType = MuddleFactory.eINSTANCE.createStringType();
	protected static BooleanType booleanType = MuddleFactory.eINSTANCE.createBooleanType();
	protected static RealType realType = MuddleFactory.eINSTANCE.createRealType();
	
	public static IntegerType getIntegerType() {
		return integerType;
	}
	
	public static StringType getStringType() {
		return stringType;
	}
	
	public static BooleanType getBooleanType() {
		return booleanType;
	}
	
	public static RealType getRealType() {
		return realType;
	}
}
