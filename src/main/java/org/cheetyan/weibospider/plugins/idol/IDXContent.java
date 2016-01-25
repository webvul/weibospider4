package org.cheetyan.weibospider.plugins.idol;

import java.util.ArrayList;
import java.util.List;

public class IDXContent {
	private List<IDXNode> idxNodes;

	public IDXContent() {
		idxNodes = new ArrayList<>();
	}

	public List<IDXNode> getIdxNodes() {
		return idxNodes;
	}

	public void setIdxNodes(List<IDXNode> idxNodes) {
		this.idxNodes = idxNodes;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (IDXNode n : idxNodes) {
			sb.append(n.toString()).append("\r\n");
		}
		sb.append("#DREENDDATAREFERENCE\r\n\r\n");
		return sb.toString();
	}
}
