package com.app.main.altibase.vo;

public class TimeTableMappingVo implements Cloneable{
	private String line;
	private String trnno;
	private String dayCd;
	private String directAt;
	private String orgStnCd;
	private String orgStnNm;
	private String dstStnCd;
	private String dstStnNm;
	private String updateDt;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public String toString() {
		String str = "";
		
		str = "["+ "호선="   + line + ";"
				+ "열차번호=" + trnno     + ";"
				+ "주말코드=" + dayCd     + ";"
				+ "급행코드=" + directAt  + ";"
				+ "출발역="  + orgStnCd  + ";"
				+ "출발역명=" + orgStnNm + ";"
				+ "도착역="  + dstStnCd  + ";"
				+ "도착역명=" + dstStnNm  + ";"
				+ "등록일="  + updateDt  + ";"
				+"]";
		
		return str;
	}


	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getTrnno() {
		return trnno;
	}

	public void setTrnno(String trnno) {
		this.trnno = trnno;
	}

	public String getDayCd() {
		return dayCd;
	}

	public void setDayCd(String dayCd) {
		this.dayCd = dayCd;
	}

	public String getDirectAt() {
		return directAt;
	}

	public void setDirectAt(String directAt) {
		this.directAt = directAt;
	}

	public String getOrgStnCd() {
		return orgStnCd;
	}

	public void setOrgStnCd(String orgStnCd) {
		this.orgStnCd = orgStnCd;
	}

	public String getOrgStnNm() {
		return orgStnNm;
	}

	public void setOrgStnNm(String orgStnNm) {
		this.orgStnNm = orgStnNm;
	}

	public String getDstStnCd() {
		return dstStnCd;
	}

	public void setDstStnCd(String dstStnCd) {
		this.dstStnCd = dstStnCd;
	}

	public String getDstStnNm() {
		return dstStnNm;
	}

	public void setDstStnNm(String dstStnNm) {
		this.dstStnNm = dstStnNm;
	}
	
	public String getUpdateDt() {
		return updateDt;
	}

	public void setUpdateDt(String updateDt) {
		this.updateDt = updateDt;
	}
}
