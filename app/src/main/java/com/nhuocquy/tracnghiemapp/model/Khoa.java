package com.nhuocquy.tracnghiemapp.model;

import java.util.ArrayList;
import java.util.List;


public class Khoa {
	private long id;
	private String maKhoa;
	private String tenKhoa;
	private List<Nganh> dsNganh = new ArrayList<>();;

	public Khoa() {
	}

	public Khoa(String maKhoa, String tenKhoa) {
		super();
		this.maKhoa = maKhoa;
		this.tenKhoa = tenKhoa;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMaKhoa() {
		return maKhoa;
	}

	public void setMaKhoa(String maKhoa) {
		this.maKhoa = maKhoa;
	}

	public String getTenKhoa() {
		return tenKhoa;
	}

	public void setTenKhoa(String tenKhoa) {
		this.tenKhoa = tenKhoa;
	}

	public List<Nganh> getDsNganh() {
		return dsNganh;
	}

	public void setDsNganh(List<Nganh> dsNganh) {
		this.dsNganh = dsNganh;
	}

	public void addNganh(Nganh nganh) {
		dsNganh.add(nganh);
	}

	public void fetchAllNganh() {
		dsNganh.size();
	}

	@Override
	public String toString() {
		return "Khoa [id=" + id + ", maKhoa=" + maKhoa + ", tenKhoa=" + tenKhoa + ", dsNganh=" + dsNganh + "]";
	}

}
