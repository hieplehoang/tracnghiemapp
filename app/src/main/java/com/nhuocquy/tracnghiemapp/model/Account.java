package com.nhuocquy.tracnghiemapp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
	private long id;
	private String tenAcc;
	private Date ngaySinh;
	private int MSSV;
	private String lop;
	private String username;
	private String password;
	private boolean kichHoat;
	private boolean locked;
	private String keyKichHoat;
	private List<XepHangMonHoc> dsXepHang = new ArrayList<>();;
	private long khoaID;
	private long nganhID;
	private Khoa khoa;
	private Nganh nganh;

	public Account() {
	}

	public Account(String tenAcc, Date ngaySinh, int mSSV, String lop, String username, String password, long khoaID,
			long nganhID) {
		super();
		this.tenAcc = tenAcc;
		this.ngaySinh = ngaySinh;
		MSSV = mSSV;
		this.lop = lop;
		this.username = username;
		this.password = password;
		this.khoaID = khoaID;
		this.nganhID = nganhID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTenAcc() {
		return tenAcc;
	}

	public void setTenAcc(String tenAcc) {
		this.tenAcc = tenAcc;
	}

	public Date getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(Date ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public int getMSSV() {
		return MSSV;
	}

	public void setMSSV(int mSSV) {
		MSSV = mSSV;
	}

	public String getLop() {
		return lop;
	}

	public void setLop(String lop) {
		this.lop = lop;
	}

	public List<XepHangMonHoc> getDsXepHang() {
		return dsXepHang;
	}

	public void setDsXepHang(List<XepHangMonHoc> dsXepHang) {
		this.dsXepHang = dsXepHang;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getKhoaID() {
		return khoaID;
	}

	public void setKhoaID(long khoaID) {
		this.khoaID = khoaID;
	}

	public long getNganhID() {
		return nganhID;
	}

	public void setNganhID(long nganhID) {
		this.nganhID = nganhID;
	}

	public Khoa getKhoa() {
		return khoa;
	}

	public void setKhoa(Khoa khoa) {
		this.khoa = khoa;
	}

	public Nganh getNganh() {
		return nganh;
	}

	public void setNganh(Nganh nganh) {
		this.nganh = nganh;
	}

	public boolean isKichHoat() {
		return kichHoat;
	}

	public void setKichHoat(boolean kichHoat) {
		this.kichHoat = kichHoat;
	}

	public String getKeyKichHoat() {
		return keyKichHoat;
	}

	public void setKeyKichHoat(String keyKichHoat) {
		this.keyKichHoat = keyKichHoat;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", tenAcc=" + tenAcc + ", ngaySinh=" + ngaySinh + ", MSSV=" + MSSV + ", lop=" + lop
				+ ", username=" + username + ", password=" + password + ", dsXepHang=" + dsXepHang + ", khoaID="
				+ khoaID + ", nganhID=" + nganhID + ", khoa=" + khoa + ", nganh=" + nganh + "]";
	}

}
