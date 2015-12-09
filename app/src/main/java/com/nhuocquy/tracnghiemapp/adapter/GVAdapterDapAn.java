package com.nhuocquy.tracnghiemapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhuocquy.tracnghiemapp.R;

/**
 * Created by TrangPig on 12/8/2015.
 */
public class GVAdapterDapAn extends ArrayAdapter<GVAdapterDapAn.GridItem> {
    private Context mContext;

    public GVAdapterDapAn(Context context) {
        super(context, R.layout.item_dap_an);
        this.mContext = context;
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
//    public AddressBook[] getmThumbIds() {
//        return mThumbIds;
//    }
//    public void setmThumbIds(AddressBook[] mThumbIds) {
//        this.mThumbIds = mThumbIds;
//    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null){
            grid = new View(mContext);
            grid = inflater.inflate(R.layout.item_dap_an, null);
            CheckBox checkBox = (CheckBox) grid.findViewById(R.id.checkbox);
            ImageView imageView = (ImageView) grid.findViewById(R.id.image);
            TextView textView = (TextView) grid.findViewById(R.id.text);

            imageView.setImageResource(mThumbIds[position].getDrawable());
            textView.setText(mThumbIds[position].getName());
            checkBox.setText(mThumbIds[position].getStt());
        } else {
            grid = (View) convertView;
        }
        return grid;
    }
    private GridItem[] mThumbIds = {
            new GridItem("A","đáp án A: "+R.drawable.icon5, R.drawable.icon5),
            new GridItem("B","Đáp án B: "+R.drawable.icon5,R.drawable.icon5),
            new GridItem("C","Đáp án C: "+R.drawable.icon6,R.drawable.icon6),
            new GridItem("D","Đáp án D: "+R.drawable.icon6,R.drawable.icon6)};
//            new AddressBook("persional "+R.drawable.sample_6,R.drawable.sample_6),
//            new AddressBook("persional "+R.drawable.sample_7,R.drawable.sample_7),
//            new AddressBook("persional "+R.drawable.sample0, R.drawable.sample0),
//            new AddressBook("persional "+R.drawable.sample1, R.drawable.sample1),
//            new AddressBook("persional "+R.drawable.sample2, R.drawable.sample_3),
//            new AddressBook("persional "+R.drawable.sample_4,R.drawable.sample_4),
//            new AddressBook("persional "+R.drawable.sample_5,R.drawable.sample_5),
//            new AddressBook("persional "+R.drawable.sample_6,R.drawable.sample_6),
//            new AddressBook("persional "+R.drawable.sample_7,R.drawable.sample_7),
//            new AddressBook("persional "+R.drawable.sample0, R.drawable.sample0),
//            new AddressBook("persional "+R.drawable.sample1, R.drawable.sample1),
//            new AddressBook("persional "+R.drawable.sample2, R.drawable.sample2),
//            new AddressBook("persional "+R.drawable.sample_3,R.drawable.sample_3),
//            new AddressBook("persional "+R.drawable.sample_4,R.drawable.sample_4),
//            new AddressBook("persional "+R.drawable.sample_5,R.drawable.sample_5),
//            new AddressBook("persional "+R.drawable.sample_6,R.drawable.sample_6),
//            new AddressBook("persional "+R.drawable.sample_7,R.drawable.sample_7)};

    public class GridItem {
        String stt;
        String text;
        int image;
        public GridItem(String stt, String text, int image){
            this.stt = stt;
            this.text = text;
            this.image = image;
        }
        public String getStt() {
            return stt;}
        public void setStt(String name) {
            this.stt = name;}
        public String getName() {
            return text;}
        public void setName(String name) {
            this.text = name;}
        public int getDrawable() {
            return image;}
        public void setDrawable(int drawable) {
            this.image = drawable;}
    }
}
