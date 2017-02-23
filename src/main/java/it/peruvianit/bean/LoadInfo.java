package it.peruvianit.bean;

public class LoadInfo {
	
	private Integer rowTotal;
	private Integer rowLastWorking;
	private Integer rowLastSuccess;
	private Integer rowLastFails = 0;
	
	public Integer getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(Integer rowTotal) {
		this.rowTotal = rowTotal;
	}
	public Integer getRowLastWorking() {
		return rowLastWorking;
	}
	public void setRowLastWorking(Integer rowLastWorking) {
		this.rowLastWorking = rowLastWorking;
	}
	public Integer getRowLastSuccess() {
		return rowLastSuccess;
	}
	public void setRowLastSuccess(Integer rowLastSuccess) {
		this.rowLastSuccess = rowLastSuccess;
	}
	public Integer getRowLastFails() {
		return rowLastFails;
	}
	public void setRowLastFails(Integer rowLastFails) {
		this.rowLastFails = rowLastFails;
	}
}
