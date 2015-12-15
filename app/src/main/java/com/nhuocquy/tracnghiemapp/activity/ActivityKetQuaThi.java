package com.nhuocquy.tracnghiemapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.model.MonHoc;

public class ActivityKetQuaThi extends AppCompatActivity {
    MonHoc monHoc;
    Button buttonSubmit, buttonXepHang;
    TextView tvMonHoc, tvDiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_thi);

        monHoc = (MonHoc) MyVar.getAttribute(MyConstant.MON_HOC);
        tvMonHoc = (TextView) findViewById(R.id.tvMonHoc);
        tvDiem = (TextView) findViewById(R.id.tvDiem);

        buttonSubmit = (Button) findViewById(R.id.btnSubmit);
        buttonXepHang = (Button) findViewById(R.id.btnXepHang);

        tvMonHoc.setText(monHoc.getTenMonHoc());
        tvDiem.setText(""+monHoc.calDiemThi());

//        buttonSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        buttonXepHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityKetQuaThi.this, ActivityKetQuaXepHang.class);
                intent.putExtra(ActivityKetQuaXepHang.ID_MOMHOC, monHoc.getId());
                intent.putExtra(ActivityKetQuaXepHang.DO_KHO,monHoc.getDoKho());
                startActivity(intent);
            }
        });

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
