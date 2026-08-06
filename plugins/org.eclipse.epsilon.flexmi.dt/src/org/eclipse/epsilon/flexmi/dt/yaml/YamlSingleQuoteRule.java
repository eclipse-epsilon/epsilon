/*
 * Copyright 2018 Albert Tregnaghi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *		http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 *
 */
package org.eclipse.epsilon.flexmi.dt.yaml;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

public class YamlSingleQuoteRule implements IPredicateRule{

	private IToken success;

	public YamlSingleQuoteRule(IToken success){
		this.success=success;
	}
	
	@Override
	public IToken evaluate(ICharacterScanner scanner) {
		return evaluate(scanner,false);
	}

	@Override
	public IToken getSuccessToken() {
		return success;
	}

	@Override
	public IToken evaluate(ICharacterScanner scanner, boolean resume) {
		ScanSlider slider = new ScanSlider(scanner);
		int first = slider.moveForward();
		if (first!='\''){
			if (first!=ICharacterScanner.EOF){
				slider.resetScanner();
			}
			return Token.UNDEFINED;
		}
		
		int readBefore=-1;
		int read=0;
		boolean foundClosing=false;
		while ( (read=slider.moveForward())!=ICharacterScanner.EOF){
			if (read=='\n'){
				break;
			}
			if (read=='#' && foundClosing){
				// we handle comments like a line brake
				slider.moveBack(); // we do not want to highlight the #
				break;
			}
			if (Character.isWhitespace(read)){
				/* just ignore*/
				continue;
			}
			if (read=='\''){
				if (foundClosing && readBefore=='\''){
					foundClosing=false;
				}else{
					foundClosing=true;
				}
			}else{
				foundClosing=false;
			}
			readBefore=read;
		}
		if (foundClosing){
			return success;
		}
		slider.resetScanner();
		return Token.UNDEFINED;
	}

}
