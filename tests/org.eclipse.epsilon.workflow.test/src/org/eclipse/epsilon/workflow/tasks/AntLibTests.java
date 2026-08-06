/*******************************************************************************
 * Copyright (c) 2012 Antonio Garc��a-Dom��nguez.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garc��a-Dom��nguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.workflow.tasks;

import java.io.File;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Typedef;
import org.eclipse.epsilon.common.util.FileUtil;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Checks that the &lt;antlib&gt; document for the workflow tasks is valid.
 *
 * @author Antonio Garcia-Dominguez
 */
public class AntLibTests {

	private static final String ANTLIB_PATH = "/org/eclipse/epsilon/workflow/tasks/tasks.xml";

	@Test
	public void antlibIsValid() throws Exception {
		final Project p = new Project();
		final File antlibFile = FileUtil.getFileStandalone(ANTLIB_PATH, AntLibTests.class);

		final Typedef td = new Typedef();
		td.setProject(p);
		td.setFile(antlibFile);
		td.execute();

		final Document d = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(antlibFile);
		final NodeList taskdefs = d.getElementsByTagName("taskdef");
		for (int i = 0; i < taskdefs.getLength(); ++i) {
			final Element e = (Element)taskdefs.item(i);
			p.createTask(e.getAttribute("name"));
		}
	}
}
