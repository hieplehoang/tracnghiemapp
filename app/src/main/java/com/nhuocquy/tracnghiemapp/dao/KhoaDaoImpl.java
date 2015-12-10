package com.nhuocquy.tracnghiemapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.nhuocquy.tracnghiemapp.db.DataBaseHelper;
import com.nhuocquy.tracnghiemapp.model.Khoa;
import com.nhuocquy.tracnghiemapp.model.MonHoc;
import com.nhuocquy.tracnghiemapp.model.Nganh;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhuocQuy on 12/10/2015.
 */
public class KhoaDaoImpl extends DataBaseHelper implements KhoaDao {
    //
    String IDKHOA = "id";
    String MA_KHOA = "maKhoa";
    String TEN_KHOA = "tenKhoa";
    String TABLE_KHOA = "khoa";
    //
    String IDNGANH = "id";
    String MA_NGANH = "maNganh";
    String TEN_NGANH = "tenNganh";
    String NGANH_KHOA_ID = "khoa_id";
    String TABLE_NGANH = "nganh";
    //
    String IDMONHOC = "id";
    String MA_MONHOC = "maMH";
    String TEN_MONHOC = "tenMonHoc";
    String THOI_GIAN = "thoiGian";
    String TABLE_MONHOC = "nganh";
    //
    String ID_NGANH = "Nganh_id";
    String ID_MONHOC = "dsMonHoc_id";
    String TABLE_NGANH_MONHOC = "nganh_monhoc";
    public KhoaDaoImpl(Context context) {
        super(context);
    }

    @Override
    public void save(List<Khoa> list) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues, contentValues2, contentValues3;

        try {
            for (Khoa khoa : list) {
                contentValues = new ContentValues();
                contentValues.put(IDKHOA, khoa.getId());
                contentValues.put(MA_KHOA, khoa.getMaKhoa());
                contentValues.put(TEN_KHOA, khoa.getTenKhoa());
                db.insert(TABLE_KHOA, null, contentValues);
                for (Nganh nganh : khoa.getDsNganh()) {
                    contentValues2 = new ContentValues();
                    contentValues2.put(IDNGANH, nganh.getId());
                    contentValues2.put(MA_NGANH, nganh.getMaNganh());
                    contentValues2.put(TEN_NGANH, nganh.getTenNganh());
                    contentValues2.put(NGANH_KHOA_ID, khoa.getId());
                    db.insert(TABLE_NGANH, null, contentValues2);
                    for (MonHoc monHoc : nganh.getDsMonHoc()) {
                        contentValues3 = new ContentValues();
                        contentValues3.put(IDMONHOC, monHoc.getId());
                        contentValues3.put(MA_MONHOC, monHoc.getMaMH());
                        contentValues3.put(TEN_MONHOC, monHoc.getTenMonHoc());
                        contentValues3.put(THOI_GIAN, monHoc.getThoiGian());
                        db.insert(TABLE_MONHOC, null, contentValues3);
                        contentValues3 = new ContentValues();
                        contentValues3.put(ID_MONHOC, monHoc.getId());
                        contentValues3.put(ID_NGANH, nganh.getId());
                        db.insert(TABLE_NGANH_MONHOC, null, contentValues3);
                    }
                }
            }
        } catch (SQLiteException e) {
            throw e;
        } finally {
            if (db != null)
                db.close();
        }
    }

    @Override
    public List<Khoa> getListKhoa() throws Exception {
        List<Khoa> list = new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();
            Cursor res = db.rawQuery("select * from " + TABLE_KHOA, null);
            res.moveToFirst();
            while (res.isAfterLast() == false) {
                list.add(new Khoa(res.getInt(res.getColumnIndex(IDKHOA)), res.getString(res.getColumnIndex(MA_KHOA)),res.getString(res.getColumnIndex(TEN_KHOA)) ));
                res.moveToNext();
            }
            for (Khoa khoa: list ) {
                res = db.rawQuery("select * from " + TABLE_NGANH + " where " + NGANH_KHOA_ID + " ? ", new String[]{String.valueOf(khoa.getId())});
            }
        } catch (SQLiteException e) {
            throw e;
        } finally {
            if (db != null)
                db.close();
        }
        return list;
    }
}
