package org.eclipse.epsilon.picto.trace;

import org.eclipse.epsilon.eol.execute.context.IEolContext;

public class Trace {
		
		protected Object element;
		protected String property;
		protected IEolContext context;
		//TODO: There's no reason to store the watermark. We should store its length only.
		protected String tag;
		
		public Object getElement() {
			return element;
		}
		
		public void setElement(Object element) {
			this.element = element;
		}
		
		public String getProperty() {
			return property;
		}
		
		public void setProperty(String property) {
			this.property = property;
		}
		
		public String getTag() {
			return tag;
		}
		
		public void setTag(String tag) {
			this.tag = tag;
		}
		
		public void setContext(IEolContext context) {
			this.context = context;
		}
		
		public IEolContext getContext() {
			return context;
		}
	}