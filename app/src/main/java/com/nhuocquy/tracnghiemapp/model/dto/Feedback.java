package com.nhuocquy.tracnghiemapp.model.dto;

public class Feedback {
	long idAcc, idCauHoi;
	String content;
	boolean isResponse;
	public Feedback() {
		// TODO Auto-generated constructor stub
	}
	public Feedback(long idAcc, long idCauHoi, String content) {
		super();
		this.idAcc = idAcc;
		this.idCauHoi = idCauHoi;
		this.content = content;
	}
	public Feedback(long idAcc, long idCauHoi, String content, boolean isResponse) {
		super();
		this.idAcc = idAcc;
		this.idCauHoi = idCauHoi;
		this.content = content;
		this.isResponse = isResponse;
	}
	public long getIdAcc() {
		return idAcc;
	}
	public void setIdAcc(long idAcc) {
		this.idAcc = idAcc;
	}
	public long getIdCauHoi() {
		return idCauHoi;
	}
	public void setIdCauHoi(long idCauHoi) {
		this.idCauHoi = idCauHoi;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public boolean isResponse() {
		return isResponse;
	}
	public void setResponse(boolean isResponse) {
		this.isResponse = isResponse;
	}
	@Override
	public String toString() {
		return "Feedback [idAcc=" + idAcc + ", idCauHoi=" + idCauHoi + ", content=" + content + ", isResponse="
				+ isResponse + "]";
	}
	
	
}
