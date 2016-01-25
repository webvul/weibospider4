package org.cheetyan.weibospider.model.tx;

import java.util.List;


public class StatusTXWapper {

	private List<StatusTX> statuses;
	private long totalNumber;
    private int hasnext;
    
    
	public StatusTXWapper() {
		super();
	}
	public StatusTXWapper(List<StatusTX> statuses, long totalNumber, int hasnext) {
		super();
		this.statuses = statuses;
		this.totalNumber = totalNumber;
		this.hasnext = hasnext;
	}
	public List<StatusTX> getStatuses() {
		return statuses;
	}
	public void setStatuses(List<StatusTX> statuses) {
		this.statuses = statuses;
	}
	public long getTotalNumber() {
		return totalNumber;
	}
	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}
	public int getHasnext() {
		return hasnext;
	}
	public void setHasnext(int hasnext) {
		this.hasnext = hasnext;
	}

}
