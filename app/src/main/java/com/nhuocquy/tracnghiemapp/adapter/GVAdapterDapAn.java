package com.nhuocquy.tracnghiemapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.activity.ActivityHinhDapAn;
import com.nhuocquy.tracnghiemapp.activity.photoview.ActivityPhotoView;
import com.nhuocquy.tracnghiemapp.model.DapAn;

import java.util.List;

/**
 * Created by TrangPig on 12/8/2015.
 */
public class GVAdapterDapAn extends ArrayAdapter<DapAn> {
    private Context mContext;
    private List<DapAn> listDapAn;

    public GVAdapterDapAn(Context context) {
        super(context, R.layout.item_dap_an);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return listDapAn == null ? 0 : listDapAn.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CheckBox checkBox;
        ImageView imageView;
        TextView textView;
        if (convertView == null) {
            convertView =((Activity) mContext).getLayoutInflater().inflate(R.layout.item_dap_an, null);
        }
        checkBox = (CheckBox) convertView.findViewById(R.id.ckboxDapAn);
        imageView = (ImageView) convertView.findViewById(R.id.imvHinhDapAn);
        textView = (TextView) convertView.findViewById(R.id.tvDapAn);
        //
        final DapAn dapAn = listDapAn.get(position);
        Log.e("Adapter...", dapAn.toString());
        if (dapAn.getHinh() != null && !dapAn.getHinh().equals("")) {
            imageView.setImageResource(R.drawable.code);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
        textView.setText(dapAn.getNoiDungDA());

//        checkBox.setText(convert(dapAn.getThuTu()));
        checkBox.setText(convert(position));
        checkBox.setChecked(dapAn.isSelected());

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dapAn.setSelected(checkBox.isChecked());
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityPhotoView.class);
                mContext.startActivity(intent);
            }
        });
        return convertView;
    }

    public String convert(int thuTu) {
        return Character.getName(thuTu + 65).substring(Character.getName(thuTu + 65).length() - 1);
    }

    public void setListDapAn(List<DapAn> listDapAn) {
        this.listDapAn = listDapAn;
    }
}
