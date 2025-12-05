/********************************************************************************
 * Copyright (c) 2025 University of York
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *   Antonio Garcia-Dominguez - initial API and implementation
 ********************************************************************************/
package org.eclipse.epsilon.rdf.emf.config;

import java.io.InputStream;
import java.io.Writer;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.DumperOptions.FlowStyle;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

public class RDFResourceConfigurationIO {

	private RDFResourceConfigurationIO() {}

	public static RDFResourceConfiguration load(InputStream is) {
		Yaml yaml = createYaml();
		return yaml.loadAs(is, RDFResourceConfiguration.class);
	}

	protected static Yaml createYaml() {
		// The custom classloader constructor is needed for OSGi compatibility
		CustomClassLoaderConstructor constructor = new CustomClassLoaderConstructor(
				RDFResourceConfigurationIO.class.getClassLoader(), new LoaderOptions());

		DumperOptions dumpOptions = new DumperOptions();
		dumpOptions.setDefaultFlowStyle(FlowStyle.BLOCK);
		Representer representer = new Representer(dumpOptions);
		representer.addClassTag(RDFResourceConfiguration.class, Tag.MAP);

		Yaml yaml = new Yaml(constructor, representer);
		return yaml;
	}

	public static void save(RDFResourceConfiguration config, Writer w) {
		createYaml().dump(config, w);
	}

}
