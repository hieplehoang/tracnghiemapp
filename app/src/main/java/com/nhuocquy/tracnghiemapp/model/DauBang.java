package com.nhuocquy.tracnghiemapp.model;

public class DauBang {

	private long id;
	private String ten;
	private double diem;
	private int xepHang;
	public DauBang() {
	}
	
	public DauBang(String ten, double diem, int xepHang) {
		super();
		this.ten = ten;
		this.diem = diem;
		this.xepHang = xepHang;
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTen() {
		return ten;
	}
	public void setTen(String ten) {
		this.ten = ten;
	}
	public double getDiem() {
		return diem;
	}
	public void setDiem(double diem) {
		this.diem = diem;
	}
	
	public int getXepHang() {
		return xepHang;
	}
	public void setXepHang(int xepHang) {
		this.xepHang = xepHang;
	}

	@Override
	public String toString() {
		return "DauBang [id=" + id + ", ten=" + ten + ", diem=" + diem + ", xepHang=" + xepHang + "]";
	}
	
	
	
}
