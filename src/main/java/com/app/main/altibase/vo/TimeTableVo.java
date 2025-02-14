package com.app.main.altibase.vo;

public class TimeTableVo implements Cloneable{
	private String line;
	private String trainNo;
	private String weektag;
	private String codeDiv;
	private String startStation;
	private String startStationName;
	private String endStation;
	private String endStationName;
	private String basicDate;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public String getLine() {
		return line;
	}
	public void setLine(String line) {
		this.line = line;
	}
	public String getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}
	public String getWeektag() {
		return weektag;
	}
	public void setWeektag(String weektag) {
		this.weektag = weektag;
	}
	public String getCodeDiv() {
		return codeDiv;
	}
	public void setCodeDiv(String codeDiv) {
		this.codeDiv = codeDiv;
	}
	public String getStartStation() {
		return startStation;
	}
	public void setStartStation(String startStation) {
		this.startStation = startStation;
	}
	public String getStartStationName() {
		return startStationName;
	}
	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}
	public String getEndStation() {
		return endStation;
	}
	public void setEndStation(String endStation) {
		this.endStation = endStation;
	}
	public String getEndStationName() {
		return endStationName;
	}
	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}
	public String getBasicDate() {
		return basicDate;
	}
	public void setBasicDate(String basicDate) {
		this.basicDate = basicDate;
	}
	
	@Override
	public String toString() {
		String str = "";
		
		str = "["+ "호선="   + line + ";"
				+ "열차번호=" + trainNo          + ";"
				+ "주말코드=" + weektag          + ";"
				+ "급행코드=" + codeDiv          + ";"
				+ "출발역="  + startStation     + ";"
				+ "출발역명=" + startStationName + ";"
				+ "도착역="  + endStation       + ";"
				+ "도착역명=" + endStationName   + ";"
				+ "등록일="  + basicDate        + ";"
				+"]";
		
		return str;
	}
}
