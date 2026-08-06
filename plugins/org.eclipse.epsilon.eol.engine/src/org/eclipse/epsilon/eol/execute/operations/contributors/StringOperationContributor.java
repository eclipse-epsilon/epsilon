/*******************************************************************************
 * Copyright (c) 2012 The University of York.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Dimitrios Kolovos - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.eol.execute.operations.contributors;

import java.io.FileOutputStream;
import java.io.StringWriter;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.epsilon.common.util.CollectionUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

public class StringOperationContributor extends OperationContributor {
	
	@Override
	public boolean contributesTo(Object target) {
		return (target instanceof String || target instanceof Character);
	}
	
	public Object toEnum() throws Exception {
		return getContext().getModelRepository().getEnumerationValue(getTarget() + "");
	}
	
	@Override
	public void setTarget(Object target) {
		super.setTarget(target instanceof Character ? target+"" : target);			
	}
	
	public String escapeXml() throws Exception {
		Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
		Text text = document.createTextNode(getTarget() + "");
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		DOMSource source = new DOMSource(text);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(source, result);
		return writer.toString();
	}
	
	public String firstToUpperCase() {
		String value = (String) getTarget();
		if (value.isEmpty()) return value;
		else return value.substring(0,1).toUpperCase() + value.substring(1, value.length());
	}
	
	public String characterAt(int index) {
		String value = (String) getTarget();
		return value.charAt(index) + "";
	}
	
	public String firstToLowerCase() {
		String value = (String) getTarget();
		if (value.isEmpty()) return value;
		else return value.substring(0,1).toLowerCase() + value.substring(1, value.length());
	}
	
	public String ftuc() {
		return firstToUpperCase();
	}
	
	public String ftlc() {
		return firstToLowerCase();
	}
	
	public void store(String where) throws Exception {
		String value = (String) getTarget();
		try (FileOutputStream fos = new FileOutputStream(where)) {
			fos.write(value.getBytes());
			fos.flush();
		}
	}
	
	public boolean isSubstringOf(String str) {
		String value = (String) getTarget();
		return (str == null ? false : str.indexOf(value) > -1);
	}
	
	public List<String> toCharSequence() {
		String value = (String) getTarget();
		List<String> charSeq = CollectionUtil.createDefaultList();
		for (int i = 0; i < value.length(); i++) {
			charSeq.add(value.charAt(i)+"");
		}
		return charSeq;
	}
	
	public String pad(int width, String padding, boolean right) {
		String result = (String) getTarget();
		for (int pad = width-result.length(); pad > 0; --pad) {
			if (right) {
				result = result + padding;
			}
			else {
				result = padding + result;
			}
		}
		return result;
	}
}
