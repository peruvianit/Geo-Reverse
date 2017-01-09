package it.peruvianit.bean;

public class GeoConfig {
	String csvColumnCode;
	String csvColumnAddress;
	String pathDirectoryIn;
	String pathDirectoryProcess;
	
	public String getCsvColumnCode() {
		return csvColumnCode;
	}
	public void setCsvColumnCode(String csvColumnCode) {
		this.csvColumnCode = csvColumnCode;
	}
	public String getCsvColumnAddress() {
		return csvColumnAddress;
	}
	public void setCsvColumnAddress(String csvColumnAddress) {
		this.csvColumnAddress = csvColumnAddress;
	}
	public String getPathDirectoryIn() {
		return pathDirectoryIn;
	}
	public void setPathDirectoryIn(String pathDirectoryIn) {
		this.pathDirectoryIn = pathDirectoryIn;
	}
	public String getPathDirectoryProcess() {
		return pathDirectoryProcess;
	}
	public void setPathDirectoryProcess(String pathDirectoryProcess) {
		this.pathDirectoryProcess = pathDirectoryProcess;
	}
}
