package com.nhuocquy.tracnghiemapp.model;

import java.util.ArrayList;
import java.util.List;

public class CauHoi {
	public static final int EASY = 1;
	public static final int MEDIUM = 2;
	public static final int HARD = 3;
	private long id;
	private String noiDung;
	private List<String> dsHinh = new ArrayList<>();
	private String giaiThich;
	private List<DapAn> dsDapAn  = new ArrayList<>();
	private int doKho;
	public CauHoi() {
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNoiDung() {
		return noiDung;
	}
	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}
	public List<String> getDsHinh() {
		return dsHinh;
	}
	public void setDsHinh(List<String> dsHinh) {
		this.dsHinh = dsHinh;
	}
	public String getGiaiThich() {
		return giaiThich;
	}
	public void setGiaiThich(String giaiThich) {
		this.giaiThich = giaiThich;
	}
	public List<DapAn> getDsDapAn() {
		return dsDapAn;
	}
	public void setDsDapAn(List<DapAn> dsDapAn) {
		this.dsDapAn = dsDapAn;
	}
	
	public int getDoKho() {
		return doKho;
	}
	public void setDoKho(int doKho) {
		this.doKho = doKho;
	}
		
	
	@Override
	public String toString() {
		return "CauHoi [id=" + id + ", noiDung=" + noiDung + ", dsHinh=" + dsHinh + ", giaiThich=" + giaiThich
				+ ", dsDapAn=" + dsDapAn + ", doKho=" + doKho + "]";
	}
	public void fetchAll(){
		dsDapAn.size();
		dsHinh.size();
	}
	//--------------------------------//
	public double getDiemThi(double diem){
		double slgDung = 0, slgChon=0;
		for (DapAn dapAn : dsDapAn) {
			slgDung = slgDung + (dapAn.isLaDADung() ? 1 : 0); 
			slgChon = slgChon + (dapAn.isSelected() ? 1 : 0);
		}
		if(slgChon != slgDung){
			slgChon = 0;
		}else{
			slgChon = 0;
			slgDung = 0;
			for(DapAn dapAn: dsDapAn){
				slgChon += dapAn.isLaDADung() && dapAn.isSelected() ? 1: 0;
				slgDung += dapAn.isLaDADung() ? 1: 0;
			}
			slgChon = slgChon/slgDung * diem;
		}
		return slgChon;
	}
}
