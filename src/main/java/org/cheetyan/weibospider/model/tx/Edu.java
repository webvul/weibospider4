package org.cheetyan.weibospider.model.tx;

import javax.json.*;
import javax.persistence.Embeddable;
@Embeddable
public class Edu {
	private String departmentid;
	private String eduid;
	private String level;
	private String schoolid;
	private String year;

	public Edu() {
	};

	public Edu(JsonObject json) throws JsonException {
		this.departmentid = String.valueOf(json.getInt("departmentid"));
		this.eduid = String.valueOf(json.getInt("id"));
		this.level = String.valueOf(json.getInt("level"));
		this.schoolid = String.valueOf(json.getInt("schoolid"));
		this.year = String.valueOf(json.getInt("year"));

	}

	public String getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(String departmentid) {
		this.departmentid = departmentid;
	}

	public String getEduid() {
		return eduid;
	}

	public void setEduid(String eduid) {
		this.eduid = eduid;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

}
