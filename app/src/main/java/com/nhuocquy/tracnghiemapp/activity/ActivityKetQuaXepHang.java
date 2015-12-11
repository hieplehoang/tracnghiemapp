package com.nhuocquy.tracnghiemapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.adapter.LVAdapterDauBang;
import com.nhuocquy.tracnghiemapp.model.DauBang;

import java.util.ArrayList;
import java.util.List;

public class ActivityKetQuaXepHang extends AppCompatActivity {
    List<DauBang> list;
    ListView listView;
    LVAdapterDauBang lvAdapterDauBang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_xep_hang);
        listView = (ListView) findViewById(R.id.lvDauBang);
        // tao co so du lieu tam
        list = new ArrayList<>();
        list.add(new DauBang());
        list.add(new DauBang());
        list.add(new DauBang());
        list.add(new DauBang());
        list.add(new DauBang());
        list.add(new DauBang());
        list.add(new DauBang());
        list.add(new DauBang());

        lvAdapterDauBang = new LVAdapterDauBang(this,list);
        listView.setAdapter(lvAdapterDauBang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_ket_qua, menu);
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
