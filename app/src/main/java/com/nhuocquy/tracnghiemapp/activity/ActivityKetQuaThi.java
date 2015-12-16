package com.nhuocquy.tracnghiemapp.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.MonHoc;
import com.nhuocquy.tracnghiemapp.model.dto.MyStatus;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ActivityKetQuaThi extends AppCompatActivity {
    MonHoc monHoc;
    Button buttonSubmit, buttonXepHang;
    TextView tvMonHoc, tvDiem;
    String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_thi);

        monHoc = (MonHoc) MyVar.getAttribute(MyConstant.MON_HOC);
        tvMonHoc = (TextView) findViewById(R.id.tvMonHoc);
        tvDiem = (TextView) findViewById(R.id.tvDiem);

        buttonSubmit = (Button) findViewById(R.id.btnSubmitDiem);
        buttonXepHang = (Button) findViewById(R.id.btnXepHang);

        Log.e("ActivityKetQuaThi",monHoc.toString());
        tvMonHoc.setText(monHoc.getTenMonHoc());
        tvDiem.setText(String.format("%.2f",monHoc.calDiemThi()));

        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Account account = (Account) MyVar.getAttribute(MyConstant.ACCOUNT);

                if(account == null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(ActivityKetQuaThi.this);
                    builder.setTitle("Nhập tên");

                    final EditText input = new EditText(ActivityKetQuaThi.this);
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    builder.setView(input);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            name = input.getText().toString();


                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    builder.show();
                }
                new AsyncTask<Void, Void,MyStatus>(){
                    ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityKetQuaThi.this, ActivityKetQuaThi.this.getResources().getString(R.string.wait), ActivityKetQuaThi.this.getResources().getString(R.string.conecting), true);
                    @Override
                    protected MyStatus doInBackground(Void... params) {

                        RestTemplate rest = new RestTemplate();
                        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                        ((SimpleClientHttpRequestFactory)rest.getRequestFactory()).setReadTimeout(MyConstant.READ_TIME_OUT);
                        ((SimpleClientHttpRequestFactory)rest.getRequestFactory()).setConnectTimeout(MyConstant.CONNECT_TIME_OUT);

                        HttpMessageConverter formHttpMessageConverter = new FormHttpMessageConverter();
                        List<HttpMessageConverter<?>> list = new ArrayList<HttpMessageConverter<?>>();
                        list.add(formHttpMessageConverter);
                        list.add(new MappingJackson2HttpMessageConverter());
                        rest.setMessageConverters(list);

                        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
                        map.add("idMonHoc", monHoc.getId() + "" );
                        map.add("idAccount", (account != null? account.getId(): 0 )+ "");
                        map.add("tenAcc", (account != null? account.getTenAcc(): name ));
                        map.add("diem", monHoc.calDiemThi() + "");
                        map.add("doKho", monHoc.getDoKho()+"");
                        ringProgressDialog.dismiss();
                        try {
                            MyStatus myStatus = rest.postForObject(String.format(URL.SUMMIT_DIEM_THITHU, URL.IP), map, MyStatus.class);
                            return myStatus;
                        }catch (Exception e){
                            e.printStackTrace();
                            return null;
                        }
                    }

                    @Override
                    protected void onPostExecute(MyStatus myStatus) {
                        super.onPostExecute(myStatus);
                        ringProgressDialog.dismiss();
                        if(myStatus == null){
                            Toast.makeText(ActivityKetQuaThi.this, "Không thể kết nối máy chủ!", Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(ActivityKetQuaThi.this, "Submit thành công!", Toast.LENGTH_LONG).show();
                        }
                    }

                    ;
                }.execute();
            }
        });

        buttonXepHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityKetQuaThi.this, ActivityKetQuaXepHang.class);
                intent.putExtra(ActivityKetQuaXepHang.ID_MOMHOC, monHoc.getId());
                intent.putExtra(ActivityKetQuaXepHang.DO_KHO, monHoc.getDoKho());
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
