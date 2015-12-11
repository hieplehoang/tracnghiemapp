package com.nhuocquy.tracnghiemapp.dao;

import com.nhuocquy.tracnghiemapp.model.Khoa;
import com.nhuocquy.tracnghiemapp.model.Nganh;

import java.util.List;

/**
 * Created by NhuocQuy on 12/10/2015.
 */
public class UtilDao {
    public static String[] listKhoaName(List<Khoa> list) {
        if (list == null)
            return new String[]{"Chọn khoa"};
        String[] arr = new String[list.size() + 1];
        arr[0] = "Chọn khoa";
        for (int i = 0; i < list.size(); i++) {
            arr[i + 1] = list.get(i).getTenKhoa();
        }
        return arr;
    }

    public static String[] listNganhNameOfKhoa(List<Khoa> list, int pos) {
        if (list == null || pos < 0)
            return new String[]{"Chọn ngành."};
        Khoa khoa = list.get(pos);
        String[] arr = new String[khoa.getDsNganh().size() + 1];
        arr[0] = "Chọn ngành.";
        for (int i = 0; i < khoa.getDsNganh().size(); i++) {
            arr[i + 1] = khoa.getDsNganh().get(i).getTenNganh();
        }
        return arr;
    }
    public static String[] listMonHocOfNganh(List<Khoa> list, int posKhoa, int posNganh){
        if (list == null || posKhoa < 0 || posNganh < 0)
            return new String[]{"Chọn môn học."};
        Khoa khoa = list.get(posKhoa);
        Nganh nganh = khoa.getDsNganh().get(posNganh);
        String[] arr = new String[nganh.getDsMonHoc().size() + 1];
        arr[0] = "Chọn môn học.";
        for (int i = 0; i < nganh.getDsMonHoc().size(); i++) {
            arr[i + 1] = nganh.getDsMonHoc().get(i).getTenMonHoc() + " - " + nganh.getDsMonHoc().get(i).getMaMH();
        }
        return arr;
    }
}
