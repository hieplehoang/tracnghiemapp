package com.nhuocquy.tracnghiemapp.model;

import java.util.ArrayList;
import java.util.List;

public class XepHangMonHoc {
	private long id;
	private long idMonHoc;
	private String tenMonHoc;
	private int xepHang;
	private int viTri;
	private List<DauBang> dsDauBang = new ArrayList<>();;

	public XepHangMonHoc() {
	}

	public XepHangMonHoc(long id, String tenMonHoc, int xepHang, List<DauBang> dsDauBang) {
		this.id = id;
		this.tenMonHoc = tenMonHoc;
		this.xepHang = xepHang;
		this.dsDauBang = dsDauBang;
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

	public int getXepHang() {
		return xepHang;
	}

	public void setXepHang(int xepHang) {
		this.xepHang = xepHang;
	}

	public List<DauBang> getDsDauBang() {
		return dsDauBang;
	}

	public void setDsDauBang(List<DauBang> dsDauBang) {
		this.dsDauBang = dsDauBang;
	}

	public int getViTri() {
		return viTri;
	}

	public void setViTri(int viTri) {
		this.viTri = viTri;
	}

	public void addDauBang(DauBang dauBang) {
		dsDauBang.add(dauBang);
	}

	public long getIdMonHoc() {
		return idMonHoc;
	}

	public void setIdMonHoc(long idMonHoc) {
		this.idMonHoc = idMonHoc;
	}

	@Override
	public String toString() {
		return "XepHangMonHoc [id=" + id + ", idMonHoc=" + idMonHoc + ", tenMonHoc=" + tenMonHoc + ", xepHang="
				+ xepHang + ", viTri=" + viTri + ", dsDauBang=" + dsDauBang + "]";
	}


	

}
