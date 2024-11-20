package org.eclipse.epsilon.egl.parse;

import org.eclipse.epsilon.egl.EglModule;
import org.eclipse.epsilon.eol.execute.context.Variable;

public class EglTagConfiguration {
	
	protected String startMarkerTag = "[*-";
	protected String startCommentTag = "[*";
	protected String endCommentTag = "*]";
	protected String startOutputTag = "[%=";
	protected String startTag = "[%";
	protected String endTag = "%]";
	protected String endOutdentTag = "-%]";

	public EglTagConfiguration() {}
	
	public static void main(String[] args) throws Exception {
		
		EglModule module = new EglModule();
		module.getTemplateFactory().setTagConfiguration(new EglTagConfiguration("{{", "}}", "{*", "*}"));
		
		//((org.eclipse.epsilon.egl.internal.EglModule) module.getModule()).
		//	setTagConfiguration(new EglTagConfiguration("{{", "}}", "{*", "*}"));
		module.parse("{{ for (i in 1.to(2)){ }} {{=i}} {{ } }}");
		module.getContext().getFrameStack().put(Variable.createReadOnlyVariable("i", 2));
		System.out.println(module.execute());
	}
	
	public EglTagConfiguration(String startTag, String endTag, String startCommentTag, String endCommentTag) {
		this.startTag = startTag;
		this.startOutputTag = startTag + "=";
		this.endTag = endTag;
		this.startCommentTag = startCommentTag;
		this.startMarkerTag = startCommentTag + "-";
		this.endOutdentTag = "-" + endTag;
		this.endCommentTag = endCommentTag;
	}
	
	public String getStartMarkerTag() {
		return startMarkerTag;
	}

	public void setStartMarkerTag(String startMarkerTag) {
		this.startMarkerTag = startMarkerTag;
	}

	public String getStartCommentTag() {
		return startCommentTag;
	}

	public void setStartCommentTag(String startCommentTag) {
		this.startCommentTag = startCommentTag;
	}

	public String getEndCommentTag() {
		return endCommentTag;
	}

	public void setEndCommentTag(String endCommentTag) {
		this.endCommentTag = endCommentTag;
	}

	public String getStartOutputTag() {
		return startOutputTag;
	}

	public void setStartOutputTag(String startOutputTag) {
		this.startOutputTag = startOutputTag;
	}

	public String getStartTag() {
		return startTag;
	}

	public void setStartTag(String startTag) {
		this.startTag = startTag;
	}

	public String getEndTag() {
		return endTag;
	}

	public void setEndTag(String endTag) {
		this.endTag = endTag;
	}

	public String getEndOutdentTag() {
		return endOutdentTag;
	}

	public void setEndOutdentTag(String endOutdentTag) {
		this.endOutdentTag = endOutdentTag;
	}
	
}
