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

public class ActivityKetQuaSubmit extends AppCompatActivity {
    MonHoc monHoc;
    Button btnLamBai, btnThoat;
    TextView tvTenMonHoc, tvThoiGian, tvSlCauHoi, tvDoKho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_submit);

        monHoc = (MonHoc) MyVar.getAttribute(MyConstant.MON_HOC);

        tvTenMonHoc= (TextView) findViewById(R.id.tvTenMonHoc);
        tvThoiGian= (TextView) findViewById(R.id.tvThoiGian);
        tvSlCauHoi= (TextView) findViewById(R.id.tvSlCauHoi);
        tvDoKho= (TextView) findViewById(R.id.tvDoKho);

        btnLamBai = (Button) findViewById(R.id.btnLamBai);
        btnThoat = (Button) findViewById(R.id.btnThoat);

        tvTenMonHoc.setText(monHoc.getTenMonHoc());
        tvThoiGian.setText(monHoc.getThoiGian()+ " phút" );
        tvSlCauHoi.setText(monHoc.getSoLgCauHoi()+ " câu");
        tvDoKho.setText(monHoc.getDoKho()==1?monHoc.getDoKho()==2?"Khó":"Trung bình":"Dễ");

        btnLamBai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityKetQuaSubmit.this, ActivityDeThi.class);
                startActivity(intent);
            }
        });

        btnThoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityKetQuaSubmit.this, ActivityChonMonThiThu.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_ket_qua_submit, menu);
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
