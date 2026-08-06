/*********************************************************************
* Copyright (c) 2022 The University of York.
*
* This program and the accompanying materials are made
* available under the terms of the Eclipse Public License 2.0
* which is available at https://www.eclipse.org/legal/epl-2.0/
*
* SPDX-License-Identifier: EPL-2.0
**********************************************************************/
package org.eclipse.epsilon.picto.transformers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.eclipse.epsilon.common.dt.EpsilonCommonsPlugin;
import org.eclipse.epsilon.picto.PictoView;
import org.eclipse.epsilon.picto.ViewContent;
import org.eclipse.epsilon.picto.preferences.PictoPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;

public class KrokiContentTransformer implements ViewContentTransformer {

	private static final Pattern KROKI_FORMAT_REGEX = Pattern.compile("kroki-(?<format>[a-zA-Z]+)", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean canTransform(ViewContent content) {
		return KROKI_FORMAT_REGEX.matcher(content.getFormat()).matches();
	}

	@Override
	public String getLabel(ViewContent content) {
		return "Kroki";
	}

	@Override
	public ViewContent transform(ViewContent content, PictoView pictoView) throws Exception {
		final Matcher matcher = KROKI_FORMAT_REGEX.matcher(content.getFormat());

		if (matcher.matches()) {
			final String format = matcher.group("format");
			return new ViewContent("svg", krokiToRawSvg(format, content.getText()), content);
		} else {
			return new ViewContent("text", String.format("BUG: Kroki format could not be extracted from '%s'", content.getFormat()), content);
		}
	}

	private String krokiToRawSvg(String format, String text) throws IOException {
		final URL url = new URL(String.format("%s/%s/svg", getKrokiBaseURL(), format));

		final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "text-plain");
		try (OutputStream os = conn.getOutputStream(); OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
			osw.write(text);
		}
		conn.connect();

		String svgOutput = new BufferedReader(
			new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))
			.lines()
			.collect(Collectors.joining("\n"));
		
		return svgOutput;
	}

	private String getKrokiBaseURL() {
		IPreferenceStore preferenceStore = EpsilonCommonsPlugin.getDefault().getPreferenceStore();
		return preferenceStore.isDefault(PictoPreferencePage.KROKI_URL)
			? PictoPreferencePage.DEFAULT_KROKI_URL : preferenceStore.getString(PictoPreferencePage.KROKI_URL);
	}

}
