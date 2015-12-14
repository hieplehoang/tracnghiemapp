package com.nhuocquy.tracnghiemapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.model.XepHangMonHoc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrangPig on 12/10/2015.
 */
public class LVAdapterXepHang extends ArrayAdapter<XepHangMonHoc>{
    private Context mContext;
    List<XepHangMonHoc> list =new ArrayList<>();
    public LVAdapterXepHang(Context context) {
        super(context, R.layout.item_xep_hang_mon_hoc);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tvMonHoc, tvXepHang, tvViTri,tvDoKho;
        if(convertView==null){
            view = new View(mContext);
            view = inflater.inflate(R.layout.item_xep_hang_mon_hoc, null);
        } else {
            view = (View) convertView;
        }
        tvMonHoc = (TextView) view.findViewById(R.id.tvTenMonHoc);
        tvXepHang = (TextView) view.findViewById(R.id.tvXepHangMonHoc);
        tvViTri = (TextView) view.findViewById(R.id.tvViTriMonHoc);
        tvDoKho = (TextView) view.findViewById(R.id.tvDoKho);

        tvMonHoc.setText("Chuyên đề Java " + position);
        tvXepHang.setText("5");
        tvViTri.setText("10");
        tvDoKho.setText("Khó " + position);
        return view;
    }

    public void setList(List<XepHangMonHoc> list) {
        this.list = list;
    }
}
