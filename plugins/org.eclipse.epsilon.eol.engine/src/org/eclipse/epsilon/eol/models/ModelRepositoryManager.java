/*********************************************************************
 * Copyright (c) 2024 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.eol.models;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class ModelRepositoryManager {
	
	protected static ModelRepositoryManager INSTANCE = new ModelRepositoryManager();
	protected LinkedHashMap<String, ModelRepository> repositories = new LinkedHashMap<>();
	
	public static ModelRepositoryManager getInstance() {
		return INSTANCE;
	}
	
	public HashMap<String, ModelRepository> getRepositories() {
		return repositories;
	}
	
	public void dispose() {
		for (ModelRepository repository : repositories.values()) {
			if (repository != null) repository.dispose();
		}
		repositories.clear();
	}
}