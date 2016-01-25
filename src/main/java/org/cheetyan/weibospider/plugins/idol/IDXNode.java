package org.cheetyan.weibospider.plugins.idol;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IDXNode {
	// DREREFERENCE
	private String dreReference;
	
	// DREDATE
	private String dreDate;
	
	// DREDBNAME
	private String dreDbName;
	
	// DRETITLE
	private String dreTitle;
	
	// DRECONTENT
	private String dreContent;
	
	// DREFIELD
	private Map<String, String> dreFields;
	
	public String getDreReference() {
		return dreReference;
	}



	public void setDreReference(String dreReference) {
		this.dreReference = dreReference;
	}



	public String getDreDate() {
		return dreDate;
	}



	public void setDreDate(String dreDate) {
		this.dreDate = dreDate;
	}



	public String getDreDbName() {
		return dreDbName;
	}



	public void setDreDbName(String dreDbName) {
		this.dreDbName = dreDbName;
	}

	public String getDreTitle() {
		return dreTitle;
	}



	public void setDreTitle(String dreTitle) {
		this.dreTitle = dreTitle;
	}



	public String getDreContent() {
		return dreContent;
	}



	public void setDreContent(String dreContent) {
		this.dreContent = dreContent;
	}



	public Map<String, String> getDreFields() {
		return dreFields;
	}



	public void setDreFields(Map<String, String> dreFields) {
		this.dreFields = dreFields;
	}


	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		// DREREFERENCE
		sb.append("#DREREFERENCE ").append(dreReference).append("\r\n");
		
		// DREDATE
		sb.append("#DREDATE ").append(dreDate).append("\r\n");
		
		// DREDBNAME
		sb.append("#DREDBNAME ").append(dreDbName).append("\r\n");
		
		// DRETITLE
		sb.append("#DRETITLE ").append(dreTitle).append("\r\n");
		
		// DRECONTENT
		sb.append("#DRECONTENT ").append(dreContent).append("\r\n");
		
		// DREFIELD
		Set<Entry<String, String>> sets = dreFields.entrySet();  
        for(Entry<String, String> entry : sets) {    
            sb.append("#DREFIELD ").append(entry.getKey()).append("=\"")
            	.append(entry.getValue() != null ? entry.getValue() : "").append("\"\r\n");
        }  
		
        // DREENDDOC
        sb.append("#DREENDDOC\r\n");
        
		return sb.toString();
	}
	
}
