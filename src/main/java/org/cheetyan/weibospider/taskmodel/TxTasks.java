package org.cheetyan.weibospider.taskmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TxTasks {
	private String baseURL;
	private Map<String, String> map;
	private List<Task> tasks;
	
	public List<Task> getTxTasks1(){
		List<Task> tasklist = new ArrayList<Task>();	
		for(Task ts:tasks){
			if(ts.getType()==1){
				tasklist.add(ts);
			}
		}
		return tasklist;
	}
	public List<Task> getTxTasks2(){
		List<Task> tasklist = new ArrayList<Task>();	
		for(Task ts:tasks){
			if(ts.getType()==2){
				tasklist.add(ts);
			}
		}
		return tasklist;
	}
	public List<Task> getTxTasks3(){
		List<Task> tasklist = new ArrayList<Task>();	
		for(Task ts:tasks){
			if(ts.getType()==3){
				tasklist.add(ts);
			}
		}
		return tasklist;
	}
	

	public Map<String, String> getMap() {
		return map;
	}
	public void setMap(Map<String, String> map) {
		this.map = map;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	
	public String getBaseURL() {
		return baseURL;
	}
	public void setBaseURL(String baseURL) {
		this.baseURL = baseURL;
	}
	@Override
	public String toString() {
		return "TxTasks [map=" + map + ", tasks=" + tasks + "]";
	}

}
