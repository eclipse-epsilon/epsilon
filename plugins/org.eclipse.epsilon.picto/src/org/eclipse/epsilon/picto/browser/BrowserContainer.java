/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.browser;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.FormColors;

public class BrowserContainer extends Composite {
	
	protected boolean borderVisible = false;
	
	public BrowserContainer(Composite parent, int style) {
		super(parent, style);
		
		FillLayout layout = new FillLayout();
		layout.marginHeight = 5;
		layout.marginWidth = 5;
		setLayout(layout);
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				Rectangle b = BrowserContainer.this.getBounds();
				GC gc = e.gc;
				if (borderVisible) {
					
					Color color = new FormColors(Display.getCurrent()).getBorderColor();
					gc.setForeground(color);
					gc.drawRectangle(0, 0, b.width - 1, b.height - 1);
				}
			}
		});
		
	}
	
	public boolean isBorderVisible() {
		return borderVisible;
	}
	
	public void setBorderVisible(boolean borderVisible) {
		this.borderVisible = borderVisible;
	}
	
}
