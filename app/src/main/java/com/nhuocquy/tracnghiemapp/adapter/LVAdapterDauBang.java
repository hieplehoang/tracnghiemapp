package com.nhuocquy.tracnghiemapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.model.DauBang;

import java.util.List;

/**
 * Created by TrangPig on 12/8/2015.
 */
public class LVAdapterDauBang extends ArrayAdapter<DauBang>{
    private Context mContext;
    List<DauBang> list;
    public LVAdapterDauBang(Context context,List<DauBang> list) {
        super(context, R.layout.item_xep_hang_mon_hoc);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView tvMonHoc, tvXepHang, tvDiem;
        if(convertView==null){
            view = new View(mContext);
            view = inflater.inflate(R.layout.item_dau_bang, null);
        } else {
            view = (View) convertView;
        }
        tvMonHoc = (TextView) view.findViewById(R.id.tvTenDauBang);
        tvXepHang = (TextView) view.findViewById(R.id.tvXepHang);
        tvDiem = (TextView) view.findViewById(R.id.tvDiem);

        tvMonHoc.setText("Nguyễn Văn Thanh " + position);
        tvDiem.setText(position+3);
        tvXepHang.setText( position);
        return view;
    }
}
