/*******************************************************************************
 * Copyright (c) 2016 Aston University.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * Contributors:
 *     Antonio Garcia-Dominguez - initial API and implementation
 ******************************************************************************/
package org.eclipse.epsilon.examples.testlang.dt;

import org.eclipse.epsilon.common.dt.AbstractEpsilonUIPlugin;

public class TestLangPlugin extends AbstractEpsilonUIPlugin {

	public static TestLangPlugin getDefault() {
		return (TestLangPlugin) plugins.get(TestLangPlugin.class);
	}
}
