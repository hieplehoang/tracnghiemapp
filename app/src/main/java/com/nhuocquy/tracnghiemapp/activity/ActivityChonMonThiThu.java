package com.nhuocquy.tracnghiemapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.nhuocquy.tracnghiemapp.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityChonMonThiThu extends AppCompatActivity {

    private Spinner spinnerKhoa, spinnerNganh, spinnerMon;
    private Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon_thi_thu);

        addItemsOnSpinner2();
        addListenerOnButton();
//        addListenerOnSpinnerItemSelection();
    }

    // add items into spinner dynamically
    public void addItemsOnSpinner2() {

        spinnerKhoa = (Spinner) findViewById(R.id.spinKhoa);
        List<String> list = new ArrayList<String>();
        list.add(" Khoa CNTT ");
        list.add(" Khoa Kinh tế ");
        list.add(" Khoa Chăn Nuôi ");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKhoa.setAdapter(dataAdapter);

        spinnerNganh = (Spinner) findViewById(R.id.spinNganh);
        list = new ArrayList<String>();
        list.add(" Ngành CNTT ");
        list.add(" Ngành Kinh tế ");
        list.add(" Ngành Chăn Nuôi ");
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNganh.setAdapter(dataAdapter);

        spinnerMon = (Spinner) findViewById(R.id.spinMonHoc);
        list = new ArrayList<String>();
        list.add(" Môn CNTT ");
        list.add(" Môn Kinh tế ");
        list.add(" Môn Chăn Nuôi ");
        dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMon.setAdapter(dataAdapter);
    }

//    public void addListenerOnSpinnerItemSelection() {
//        spinner1 = (Spinner) findViewById(R.id.spinKhoa);
//        spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
//    }

    // get the selected dropdown list value
    public void addListenerOnButton() {

        btnSubmit = (Button) findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityChonMonThiThu.this, ActivityKetQuaSubmit.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
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
