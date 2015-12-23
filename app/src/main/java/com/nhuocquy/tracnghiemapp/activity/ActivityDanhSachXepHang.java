package com.nhuocquy.tracnghiemapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.adapter.LVAdapterXepHang;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.XepHangMonHoc;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ActivityDanhSachXepHang extends AppCompatActivity {
    List<XepHangMonHoc> list;
    Account account;
    ListView lvDsXepHang;
    LVAdapterXepHang lvAdapterXepHang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_xep_hang);

        account = (Account) MyVar.getAttribute(MyConstant.ACCOUNT);
        lvDsXepHang = (ListView) findViewById(R.id.lvXepHang);

        AsyncTask<Long, Void, List<XepHangMonHoc>> async = new AsyncTask<Long, Void, List<XepHangMonHoc>>() {
            final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityDanhSachXepHang.this, ActivityDanhSachXepHang.this.getResources().getString(R.string.wait), ActivityDanhSachXepHang.this.getResources().getString(R.string.conecting), true);
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
            protected List<XepHangMonHoc> doInBackground(Long... params) {
                try {
                    XepHangMonHoc[] xepHangMonHoc = rest.getForObject(String.format(URL.LIST_XEP_PHANG, URL.IP,params[0]), XepHangMonHoc[].class);
                    return Arrays.asList(xepHangMonHoc);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(List<XepHangMonHoc> xepHangMonHoc) {
                ringProgressDialog.dismiss();
                if (xepHangMonHoc == null) {
                    Toast.makeText(ActivityDanhSachXepHang.this, "Không thể kết nối máy chủ!", Toast.LENGTH_LONG).show();
                } else {
//                    MyVar.setAttribute(MyConstant.LIST_XEP_HANG_MON_HOC, xepHangMonHoc);
                    lvAdapterXepHang.setList(xepHangMonHoc);
                    lvAdapterXepHang.notifyDataSetChanged();
//                    Toast.makeText(ActivityDanhSachXepHang.this, "ok!", Toast.LENGTH_LONG).show();

                }
            }
        };

            async.execute(account.getId());

            lvAdapterXepHang = new LVAdapterXepHang(this);
            lvDsXepHang.setAdapter(lvAdapterXepHang);

            lvDsXepHang.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ActivityDanhSachXepHang.this, ActivityKetQuaXepHang.class);
                    intent.putExtra(ActivityKetQuaXepHang.ID_MOMHOC, lvAdapterXepHang.getXepHang(position).getIdMonHoc());
                    intent.putExtra(ActivityKetQuaXepHang.DO_KHO, lvAdapterXepHang.getXepHang(position).getDoKho());
                    startActivity(intent);
                }
            });

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
