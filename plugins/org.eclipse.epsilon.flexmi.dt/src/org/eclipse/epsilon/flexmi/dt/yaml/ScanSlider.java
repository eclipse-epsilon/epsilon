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

public class ScanSlider {
	private static final int INITIAL_POS = 0;

	private int pos;
	private ICharacterScanner scanner;

	public ScanSlider(ICharacterScanner scanner) {
		this.pos = INITIAL_POS;
		this.scanner = scanner;
	}

	public void moveBack() {
		if (scanner.getColumn() <= 0) {
			return;
		}
		scanner.unread();
		pos--;
	}

	public int readBefore() {
		scanner.unread();
		int cbefore = scanner.read();
		return cbefore;
	}

	public void resetScanner() {
		while (pos > INITIAL_POS) {
			scanner.unread();
			pos--;
		}
		while (pos < INITIAL_POS) {
			scanner.read();
			pos++;
		}
		
	}

	public int moveForward() {
		int value = scanner.read();
		if (value != ICharacterScanner.EOF) {
			pos++;
		}
		return value;
	}

}