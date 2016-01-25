package org.cheetyan.weibospider.model.tx;

import javax.json.JsonException;
import javax.json.JsonObject;
import javax.persistence.Embeddable;
@Embeddable
public class Comp {
	private String begin_year;
	private String company_name;
	private String department_name;
	private String end_year;
	private String compid;

	public Comp() {
	};

	public Comp(JsonObject json) throws JsonException {
		this.begin_year = String.valueOf(json.getInt("begin_year"));
		this.company_name = json.getString("company_name");
		this.department_name = json.getString("department_name");
		this.end_year = String.valueOf(json.getInt("end_year"));
		if(!json.isNull("id"))this.compid = String.valueOf(json.getInt("id"));
	}

	public Comp(String begin_year, String company_name, String department_name, String end_year, String compid) {
		super();
		this.begin_year = begin_year;
		this.company_name = company_name;
		this.department_name = department_name;
		this.end_year = end_year;
		this.compid = compid;
	}

	public String getBegin_year() {
		return begin_year;
	}

	public void setBegin_year(String begin_year) {
		this.begin_year = begin_year;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}

	public String getEnd_year() {
		return end_year;
	}

	public void setEnd_year(String end_year) {
		this.end_year = end_year;
	}

	public String getCompid() {
		return compid;
	}

	public void setCompid(String compid) {
		this.compid = compid;
	}

}
