package org.eclipse.epsilon.common.parse;

import java.util.UUID;

import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.TokenSource;

public class IdentifiableCommonTokenStream extends CommonTokenStream {
	
	protected String id;
	
	public IdentifiableCommonTokenStream(TokenSource source) {
		super(source);
		id = UUID.randomUUID().toString();
	}
	
	public String getId() {
		return id;
	}
}
