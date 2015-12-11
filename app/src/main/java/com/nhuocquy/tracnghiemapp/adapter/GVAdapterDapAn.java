package com.nhuocquy.tracnghiemapp.adapter;

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
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CheckBox checkBox;
        ImageView imageView;
        TextView textView;
        if (convertView == null) {
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.item_dap_an, null);
        } else {
            grid = (View) convertView;
        }
        checkBox = (CheckBox) grid.findViewById(R.id.ckboxDapAn);
        imageView = (ImageView) grid.findViewById(R.id.imvHinhDapAn);
        textView = (TextView) grid.findViewById(R.id.tvDapAn);
        //
        final DapAn dapAn = listDapAn.get(position);

        if (dapAn.getHinh() != null && !dapAn.getHinh().equals("")) {
            imageView.setImageResource(R.drawable.code);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.INVISIBLE);
        }
        textView.setText(dapAn.getNoiDungDA());
        checkBox.setText(convert(dapAn.getThuTu()));
        checkBox.setSelected(dapAn.isSelected());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                dapAn.setSelected(isChecked);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ActivityHinhDapAn.class);
                mContext.startActivity(intent);
                Log.e("ActivityDeThi", "lỗi itemimage click");
            }
        });
        return grid;
    }

    public String convert(int thuTu) {
        return Character.getName(thuTu + 65).substring(Character.getName(thuTu + 65).length() - 1);
    }

    public void setListDapAn(List<DapAn> listDapAn) {
        this.listDapAn = listDapAn;
    }
}
