package it.peruvianit.bean;

import java.io.Serializable;

public class SummaryGeo implements Serializable{
	private static final long serialVersionUID = 4992241552569407752L;

	private ProcessBean processBean;
	
	private Integer rowLastWorking = 0;
	private Integer rowLastSuccess = 0;
	private Integer rowLastFail = 0;
	private Integer rowSuccess = 0;
	private Integer rowFail = 0;
	private Integer rowWorking = 0;
	private Integer rowTotal = 0;
	
	@SuppressWarnings("unused")
	private Double percentualLost = 0.00D;
	@SuppressWarnings("unused")
	private Double percentualProgress = 0.00D;

	public SummaryGeo() {
		super();
	}
	
	public ProcessBean getProcessBean() {
		return processBean;
	}
	public void setProcessBean(ProcessBean processBean) {
		this.processBean = processBean;
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
	public Integer getRowLastFail() {
		return rowLastFail;
	}
	public void setRowLastFail(Integer rowLastFail) {
		this.rowLastFail = rowLastFail;
	}
	public Integer getRowSuccess() {
		return rowSuccess;
	}
	public void setRowSuccess(Integer rowSuccess) {
		this.rowSuccess = rowSuccess;
	}
	public Integer getRowFail() {
		return rowFail;
	}
	public void setRowFail(Integer rowFail) {
		this.rowFail = rowFail;
	}
	public Integer getRowWorking() {
		return rowWorking;
	}
	public void setRowWorking(Integer rowWorking) {
		this.rowWorking = rowWorking;
	}
	public Integer getRowTotal() {
		return rowTotal;
	}
	public void setRowTotal(Integer rowTotal) {
		this.rowTotal = rowTotal;
	}
	
	public Double getPercentualLost() {
		return (double) (((this.rowLastFail + this.rowFail)*100)/(this.rowLastWorking + this.rowWorking));
	}
	public Double getPercentualProgress() {
		return (double)((this.rowLastWorking + this.rowWorking)*100)/this.rowTotal;
	}
}
