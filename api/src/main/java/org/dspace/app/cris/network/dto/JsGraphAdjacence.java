package org.dspace.app.cris.network.dto;

public class JsGraphAdjacence {
	
	private String nodeTo;
	
	private JsGraphData data;

	private String src;
	
	public String getNodeTo() {
		return nodeTo;
	}

	public void setNodeTo(String nodeTo) {
		this.nodeTo = nodeTo;
	}

	public JsGraphData getData() {
		return data;
	}

	public void setData(JsGraphData data) {
		this.data = data;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getSrc() {
		return src;
	}
	
}
