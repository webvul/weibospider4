package org.cheetyan.weibospider.model.tx;


public class PagingTX implements java.io.Serializable {
	//private static final long serialVersionUID = -3285857427993796670L;
   
	/**
	 * 
	 */
	private static final long serialVersionUID = -6845750518816453792L;
	private int pageflag=0; //分页标识（0：第一页，1：向下翻页，2：向上翻页）
	private int pagetime=0; //本页起始时间（第一页：填0，向上翻页：填上一次请求返回的第一条记录时间，向下翻页：填上一次请求返回的最后一条记录时间）
	private int reqnum=50; //每次请求记录的条数（1-70条）
	private int lastid=0;//用于翻页，和pagetime配合使用（第一页：填0，向上翻页：填上一次请求返回的第一条记录id，向下翻页：填上一次请求返回的最后一条记录id）
	
	
	public PagingTX() {
		super();
	}

	public PagingTX(int pageflag, int pagetime, int reqnum, int lastid) {
		super();
		this.pageflag = pageflag;
		this.pagetime = pagetime;
		this.reqnum = reqnum;
		this.lastid = lastid;
	}

	public PagingTX(int pageflag, int pagetime, int reqnum) {
		super();
		this.pageflag = pageflag;
		this.pagetime = pagetime;
		this.reqnum = reqnum;
	}

	public PagingTX(int pageflag, int pagetime) {
		super();
		this.pageflag = pageflag;
		this.pagetime = pagetime;
	}

	public PagingTX(int pageflag) {
		super();
		this.pageflag = pageflag;
	}

	public int getPageflag() {
		return pageflag;
	}

	public void setPageflag(int pageflag) {
		this.pageflag = pageflag;
	}
	public int getPagetime() {
		return pagetime;
	}
	public void setPagetime(int pagetime) {
		this.pagetime = pagetime;
	}
	public int getReqnum() {
		return reqnum;
	}
	public void setReqnum(int reqnum) {
		this.reqnum = reqnum;
	}
	public int getLastid() {
		return lastid;
	}
	public void setLastid(int lastid) {
		this.lastid = lastid;
	}

	@Override
	public String toString() {
		return "PagingTX [pageflag=" + pageflag + ", pagetime=" + pagetime
				+ ", reqnum=" + reqnum + ", lastid=" + lastid + "]";
	}
	
}

