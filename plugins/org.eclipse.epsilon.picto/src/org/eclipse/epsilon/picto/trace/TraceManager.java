package org.eclipse.epsilon.picto.trace;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class TraceManager {
	
	protected List<Trace> traces = new ArrayList<Trace>();
	// More zero-width characters to consider \u2061 - \u2064
	protected String zeroWidthChar = "\u2060";
	
	public synchronized String getTag(IEolContext context, Object element, String property) {
		System.out.println(property + " -> " + element );
		Trace trace = traces.stream().filter(t -> t.element == element && t.property.equals(property)).findFirst().orElseGet(() -> {
			Trace t = new Trace();
			t.setElement(element);
			t.setProperty(property);
			t.setContext(context);
			String tag = "";
			for (int i=0;i<=traces.size();i++) tag += zeroWidthChar;
			t.setTag(tag);
			traces.add(t);
			System.out.println(traces.size());
			return t;
		});
		return trace.getTag();
	}
	
	public Trace getTrace(String tag) {
		return traces.stream().filter(t -> t.tag.equals(tag)).findFirst().orElse(null);
	}
	
	public void clear() {
		traces.clear();
	}

	public String getZeroWidthCharacter() {
		return zeroWidthChar;
	}
	
	
	
}
