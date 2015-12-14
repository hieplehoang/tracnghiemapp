package com.nhuocquy.tracnghiemapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.adapter.LVAdapterDauBang;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.DauBang;
import com.nhuocquy.tracnghiemapp.model.MonHoc;
import com.nhuocquy.tracnghiemapp.model.XepHangMonHoc;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ActivityKetQuaXepHang extends AppCompatActivity {
    MonHoc monHoc;
    Account acc;
    XepHangMonHoc xepHangMonHoc;
    List<DauBang> list;
    ListView listView;
    LVAdapterDauBang lvAdapterDauBang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_xep_hang);

        monHoc = (MonHoc) MyVar.getAttribute(MyConstant.MON_HOC);
        acc = (Account) MyVar.getAttribute(MyConstant.ACCOUNT);
        listView = (ListView) findViewById(R.id.lvDauBang);

        new AsyncTask<Long, Void, XepHangMonHoc>() {
            final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityKetQuaXepHang.this, ActivityKetQuaXepHang.this.getResources().getString(R.string.wait), ActivityKetQuaXepHang.this.getResources().getString(R.string.conecting), true);
            RestTemplate rest;
            XepHangMonHoc xepHangMonHoc;
            @Override
            protected void onPreExecute() {
                rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setReadTimeout(MyConstant.READ_TIME_OUT);
                ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setConnectTimeout(MyConstant.CONNECT_TIME_OUT);
            }

            @Override
            protected XepHangMonHoc doInBackground(Long... params) {
                try {
                    if(params[0] != -1)
                     xepHangMonHoc = rest.getForObject(String.format(URL.XEPHANG_ACC, URL.IP,params[0],params[1],params[2]), XepHangMonHoc.class);
                    else
                        xepHangMonHoc = rest.getForObject(String.format(URL.XEPHANG, URL.IP,params[1],params[2]), XepHangMonHoc.class);
                    return xepHangMonHoc;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(XepHangMonHoc xepHangMonHoc) {
                ringProgressDialog.dismiss();
                if (xepHangMonHoc == null) {
                    Toast.makeText(ActivityKetQuaXepHang.this, "Không thể kết nối máy chủ! Không thể tai dử liệu nền!", Toast.LENGTH_LONG).show();
                } else {
                    MyVar.setAttribute(MyConstant.XEP_HANG_MON_HOC, xepHangMonHoc);
                    Toast.makeText(ActivityKetQuaXepHang.this, "ok!", Toast.LENGTH_LONG).show();
                }
            }
        }.execute(acc != null ? acc.getId() : -1,monHoc.getId(),(long)monHoc.getDoKho());

        xepHangMonHoc =(XepHangMonHoc) MyVar.getAttribute(MyConstant.XEP_HANG_MON_HOC);
        list = xepHangMonHoc.getDsDauBang();
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
