package com.nhuocquy.tracnghiemapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonHoc {
	private long id;
	private String tenMonHoc;
	private int thoiGian;
	private int soLgCauHoi;
	private String maMH;
	private List<CauHoi> dsCauHoi = new ArrayList<>();;
	private int doKho;

	public MonHoc() {
	}

	public MonHoc(String tenMonHoc, int thoiGian, int soLgCauHoi, String maMH) {
		super();
		this.tenMonHoc = tenMonHoc;
		this.thoiGian = thoiGian;
		this.soLgCauHoi = soLgCauHoi;
		this.maMH = maMH;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenMonHoc() {
		return tenMonHoc;
	}

	public void setTenMonHoc(String tenMonHoc) {
		this.tenMonHoc = tenMonHoc;
	}

	public int getThoiGian() {
		return thoiGian;
	}

	public void setThoiGian(int thoiGian) {
		this.thoiGian = thoiGian;
	}

	public int getSoLgCauHoi() {
		return soLgCauHoi;
	}

	public void setSoLgCauHoi(int soLgCauHoi) {
		this.soLgCauHoi = soLgCauHoi;
	}

	public String getMaMH() {
		return maMH;
	}

	public void setMaMH(String maMH) {
		this.maMH = maMH;
	}

	public List<CauHoi> getDsCauHoi() {
		return dsCauHoi;
	}

	public void setDsCauHoi(List<CauHoi> dsCauHoi) {
		this.dsCauHoi = dsCauHoi;
	}

	public int getDoKho() {
		return doKho;
	}

	public void setDoKho(int doKho) {
		this.doKho = doKho;
	}

	@Override
	public String toString() {
		return "Monhoc [id=" + id + ", tenMonHoc=" + tenMonHoc + ", thoiGian=" + thoiGian + ", soLgCauHoi=" + soLgCauHoi
				+ ", maMH=" + maMH + ", dsCauHoi=" + dsCauHoi + "]";
	}

	// --------------------------------//

	public double calDiemThi() {
		double diemThi = 0;
		double diemTungCau = 10.0/soLgCauHoi;
		for (CauHoi cauHoi : dsCauHoi) {
			diemThi += cauHoi.getDiemThi(diemTungCau);
		}
		return diemThi;
	}
}
