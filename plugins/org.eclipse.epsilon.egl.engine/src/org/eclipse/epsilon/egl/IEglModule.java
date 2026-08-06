/*********************************************************************
 * Copyright (c) 2020 The University of York.
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.egl;

import java.net.URI;
import java.util.Collection;

import org.eclipse.epsilon.egl.execute.context.IEglContext;
import org.eclipse.epsilon.egl.formatter.Formatter;
import org.eclipse.epsilon.eol.IEolModule;

/**
 * 
 * @since 1.6
 */
public interface IEglModule extends IEolModule {

	@Override
	IEglContext getContext();

	EglTemplateFactory getTemplateFactory();

	void setTemplateFactory(EglTemplateFactory factory);

	EglTemplate getCurrentTemplate();

	boolean parse(String code, URI uri) throws Exception;

	void reset();

	void setDefaultFormatters(Collection<Formatter> defaultFormatters);
}
