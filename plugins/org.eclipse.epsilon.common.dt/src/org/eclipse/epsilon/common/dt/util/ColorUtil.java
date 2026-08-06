/*******************************************************************************
 * Copyright (c) 2008 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.common.dt.util;

import org.eclipse.swt.graphics.Color;

public class ColorUtil {

	public static String toHex(Color color){
		String hex = "";
		hex = hex.concat(toHex(color.getRed()));
		hex = hex.concat(toHex(color.getGreen()));
		hex = hex.concat(toHex(color.getBlue()));
		return hex;
	}
	
	private static String toHex(int w) 
    {
    	int rem;
    	String output="";
    	String digit;
    	String backwards="";
    	
    	do
    	{    		
    		rem=w%16;
    		digit=dToHex(rem);
    		w=w/16;
    		output+=digit;
    	}
    	while(w/16!=0);
    	
    	rem=w%16;
    	
    	digit=dToHex(rem);
    	
    	output=output+digit;
    	
    	for(int i=output.length()-1; i>=0; i--)
    	{
    		backwards+=output.charAt(i);
    	}
    	
    	return backwards;
    }
    
    
    private static String dToHex(int rem)
    {		
    	
    	String str1=String.valueOf(rem);
    	
    		if(str1.equals("10"))
    			str1="A";
    		
    		else if(str1.equals("11"))
    			str1="B";
    		
    		else if(str1.equals("12"))
    			str1="C";
    		
    		else if(str1.equals("13"))
    			str1="D";
    		
    		else if(str1.equals("14"))
    			str1="E";
    		
    		else if(str1.equals("15"))
    			str1="F";
   
    	return str1;
    }

}
