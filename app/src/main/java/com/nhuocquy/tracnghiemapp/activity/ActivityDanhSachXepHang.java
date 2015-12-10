package com.nhuocquy.tracnghiemapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.adapter.LVAdapterXepHang;
import com.nhuocquy.tracnghiemapp.model.XepHangMonHoc;

import java.util.ArrayList;
import java.util.List;

public class ActivityDanhSachXepHang extends AppCompatActivity {
List<XepHangMonHoc> list;
    ListView dsXepHang;
    LVAdapterXepHang lvAdapterXepHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_xep_hang);

        dsXepHang = (ListView) findViewById(R.id.lvXepHang);

        // tao co so du lieu tam
        list = new ArrayList<>();
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());
        list.add(new XepHangMonHoc());



        lvAdapterXepHang = new LVAdapterXepHang(this,list);
        dsXepHang.setAdapter(lvAdapterXepHang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_danh_sach_xep_hang, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
