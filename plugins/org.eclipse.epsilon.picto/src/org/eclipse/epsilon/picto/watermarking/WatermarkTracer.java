package org.eclipse.epsilon.picto.watermarking;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class WatermarkTracer {
	
	protected List<WatermarkTrace> traces = new ArrayList<WatermarkTrace>();
	
	public synchronized String getWatermark(IEolContext context, Object element, String property) {
		WatermarkTrace trace = traces.stream().filter(t -> t.element == element && t.property.equals(property)).findFirst().orElseGet(() -> {
			WatermarkTrace t = new WatermarkTrace();
			t.setElement(element);
			t.setProperty(property);
			t.setContext(context);
			String watermark = "";
			for (int i=0;i<=traces.size();i++) watermark += "⁠"; //<- This is not an empty string; it is an invisible character
			t.setWatermark(watermark);
			traces.add(t);
			return t;
		});
		return trace.getWatermark();
	}
	
	public WatermarkTrace getTrace(String watermark) {
		return traces.stream().filter(t -> t.watermark.equals(watermark)).findFirst().orElse(null);
	}
	
	
	
}
