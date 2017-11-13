package com.example.seenu.vts;

public class SA_Container {

}


class LocSummary {
	// private variables
	String _slNo;
	String _locVehNo;
	String _locStatus;
	String _locSpeed;
	String _locName;
	String _locDate;
	String _locTime;
	String _locStatusFlag;

	// constructor
	public LocSummary(String slNo,String vehNo,String status,String speed,String locName,String locDate,String locTime,String locStatusFlag) {
		this._slNo = slNo;
		this._locVehNo = vehNo;
		this._locStatus = status;
		this._locSpeed = speed;
		this._locName = locName;
		this._locDate = locDate;
		this._locTime = locTime;
		this._locStatusFlag = locStatusFlag;
	}

	public String getSlNo() {
		return this._slNo;
	}

	public void setSlNo(String slNo) {
		this._slNo = slNo;
	}
	
	public String getVehNo() {
		return this._locVehNo;
	}

	public void setVehNo(String vehNo) {
		this._locVehNo = vehNo;
	}

	public String getStatus() {
		return this._locStatus;
	}

	public void setStatus(String status) {
		this._locStatus = status;
	}
	
	public String getSpeed() {
		return this._locSpeed;
	}

	public void setSpeed(String speed) {
		this._locSpeed = speed;
	}
	
	public String getLocName() {
		return this._locName;
	}

	public void setLocName(String locName) {
		this._locName = locName;
	}
	
	public String getLocDate() {
		return this._locDate;
	}

	public void setLocDate(String locDate) {
		this._locDate = locDate;
	}
	
	public String getLocTime() {
		return this._locTime;
	}

	public void setLocTime(String locTime) {
		this._locTime = locTime;
	}
	
	public String getStatusFlag() {
		return this._locStatusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this._locStatusFlag = statusFlag;
	}
}
