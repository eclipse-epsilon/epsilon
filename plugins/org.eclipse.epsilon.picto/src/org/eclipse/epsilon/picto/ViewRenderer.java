/*********************************************************************
* Copyright (c) 2008 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Point;

public class ViewRenderer {
	
	protected Browser browser;
	protected double zoom = 1.0;
	protected XmlHelper xmlHelper = new XmlHelper();
	
	public ViewRenderer(Browser browser) {
		this.browser = browser;
	}
	
	public Browser getBrowser() {
		return browser;
	}
	
	public Point getScrollPosition() {
		Double scrollX = (Double) browser.evaluate("return window.pageXOffset;");
		Double scrollY = (Double) browser.evaluate("return window.pageYOffset;");
		return new Point(
				(scrollX != null) ? scrollX.intValue() : 0,
				(scrollY != null) ? scrollY.intValue() : 0);
	}
	
	public Point getPrintableArea() {
		return new Point(
			((Double) browser.evaluate("return document.body.scrollWidth")).intValue(),
			((Double) browser.evaluate("return document.body.scrollHeight")).intValue()
		);
	}
	
	public void setScrollPosition(Point point) {
		browser.evaluate("window.scroll(" + point.x + ", " + point.y + ");");
	}
	
	public void zoom(ZoomType type) {
		if (type == ZoomType.IN) zoom = Math.min(zoom + 0.1, 3.0);
		else if (type == ZoomType.OUT) zoom = Math.max(0.1, zoom - 0.1);
		else zoom = 1.0;
		
		browser.execute("javascript:document.body.style.zoom=" + zoom + ";");
	}
	
	public double getZoom() {
		return zoom;
	}
	
	public void display(Exception ex) {
		display(getVerbatim(ex.getMessage()));
	}
	
	public void display(String text) {
		if (browser.isDisposed()) return;
		
		browser.setText(text.replace("${picto-zoom}", zoom + ""));
	}
	
	public void nothingToRender() {
		display(getHtml(""));
	}
	
	public String getHtml(String content) {
		return "<html><head></head><body>" + content + "</body></html>";
	}
	
	public String getVerbatim(String content) {
		return getHtml("<pre>\n" + xmlHelper.escapeHtml(content) + "</pre>");
	}
	
	public enum ZoomType {
		IN,
		OUT,
		ACTUAL
	}
	
}
