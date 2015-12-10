package com.nhuocquy.tracnghiemapp.dao;

import com.nhuocquy.tracnghiemapp.model.Khoa;

import java.util.List;

/**
 * Created by NhuocQuy on 12/10/2015.
 */
public interface KhoaDao {
    public void save(List<Khoa> list) throws  Exception;
    public List<Khoa> getListKhoa() throws  Exception;
}
