package com.nhuocquy.tracnghiemapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.activity.ActivityHinhDapAn;
import com.nhuocquy.tracnghiemapp.model.DapAn;

/**
 * Created by TrangPig on 12/8/2015.
 */
public class GVAdapterDapAn extends ArrayAdapter<DapAn> {
    private Context mContext;
    private DapAn[] mThumbIds;
    public GVAdapterDapAn(Context context, DapAn[] mThumbIds) {
        super(context, R.layout.item_dap_an);
        this.mContext = context;
        this.mThumbIds = mThumbIds;
    }
    @Override
    public int getCount() {
        return mThumbIds.length;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    public Context getmContext() {
        return mContext;
    }
    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CheckBox checkBox;
        ImageView imageView;
        TextView textView;
        if(convertView==null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.item_dap_an, null);
        } else {
            grid = (View) convertView;
        }
        checkBox = (CheckBox) grid.findViewById(R.id.ckboxDapAn);
        imageView = (ImageView) grid.findViewById(R.id.imvHinhDapAn);
        textView = (TextView) grid.findViewById(R.id.tvDapAn);
        imageView.setImageResource(R.drawable.code);
        textView.setText(mThumbIds[position].getNoiDungDA());
        checkBox.setText(convert(mThumbIds[position].getThuTu()));
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("GVDapAnAdapter", "bat lỗi chaeckbox");
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getmContext(), ActivityHinhDapAn.class);
                getmContext().startActivity(intent);
                Log.e("ActivityDeThi", "lỗi itemimage click" );
            }
        });
        return grid;
    }
    public String convert(int thuTu){
        return Character.getName(thuTu+65).substring(Character.getName(thuTu+65).length()-1);
    }
    class ViewHolder {
        ImageView imageview;
        CheckBox checkbox;
    }
}
