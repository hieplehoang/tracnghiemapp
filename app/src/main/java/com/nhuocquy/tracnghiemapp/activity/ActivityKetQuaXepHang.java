package com.nhuocquy.tracnghiemapp.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.adapter.LVAdapterDauBang;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.DauBang;
import com.nhuocquy.tracnghiemapp.model.XepHangMonHoc;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class ActivityKetQuaXepHang extends AppCompatActivity {
    public static String ID_MOMHOC = "idMonHoc";
    public static String DO_KHO = "doKho";
    Account acc;
    ListView listView;
    LVAdapterDauBang lvAdapterDauBang;
    long idMonHoc;
    int doKho;

    private TextView tvMonHoc;
    private TextView tvDoKho;
    private TextView tvDiemCaoNhat;
    private TextView tvXepHangMonHoc;
    private TextView tvViTri;

    private void findViews() {
        tvMonHoc = (TextView) findViewById(R.id.tvMonHoc);
        tvDoKho = (TextView) findViewById(R.id.tvDoKho);
        tvDiemCaoNhat = (TextView) findViewById(R.id.tvDiemCaoNhat);
        tvXepHangMonHoc = (TextView) findViewById(R.id.tvXepHangMonHocKQXH);
        tvViTri = (TextView) findViewById(R.id.tvViTri);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ket_qua_xep_hang);

        findViews();

        idMonHoc = getIntent().getLongExtra(ID_MOMHOC, -1);
        doKho = getIntent().getIntExtra(DO_KHO, -1);

        if (idMonHoc == -1 || doKho == -1)
            throw new NullPointerException();

        acc = (Account) MyVar.getAttribute(MyConstant.ACCOUNT);
        listView = (ListView) findViewById(R.id.lvDauBang);

        lvAdapterDauBang = new LVAdapterDauBang(this);
        listView.setAdapter(lvAdapterDauBang);


        new AsyncTask<Long, Void, XepHangMonHoc>() {
            final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityKetQuaXepHang.this, ActivityKetQuaXepHang.this.getResources().getString(R.string.wait), ActivityKetQuaXepHang.this.getResources().getString(R.string.conecting), true);
            RestTemplate rest;

            @Override
            protected void onPreExecute() {
                rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setReadTimeout(MyConstant.READ_TIME_OUT);
                ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setConnectTimeout(MyConstant.CONNECT_TIME_OUT);
            }

            @Override
            protected XepHangMonHoc doInBackground(Long... params) {
                XepHangMonHoc xepHangMonHoc;
                Log.e("ActivityKetQuaXepHang", String.format("%s/%s/%s", params[0], params[1], params[2]));
                try {
                    if (params[0] != -1) {
                        xepHangMonHoc = rest.getForObject(String.format(URL.XEP_HANG_WITH_LOGIN, URL.IP, params[0], params[1], params[2]), XepHangMonHoc.class);
                        Log.e("ActivityKetQuaXepHang",String.format(URL.XEP_HANG_WITH_LOGIN, URL.IP, params[0], params[1], params[2]));
                    }
                    else {
                        xepHangMonHoc = rest.getForObject(String.format(URL.XEP_HANG_WITHOUT_LOGIN, URL.IP, params[1], params[2]), XepHangMonHoc.class);
                        Log.e("ActivityKetQuaXepHang",String.format(URL.XEP_HANG_WITHOUT_LOGIN, URL.IP, params[1], params[2]));
                    }
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
                    tvMonHoc.setText("");
                    tvDoKho.setText("");
                    tvDiemCaoNhat.setText("");
                    tvXepHangMonHoc.setText("");
                    tvViTri.setText("");
                } else {
                    lvAdapterDauBang.setList(xepHangMonHoc.getDsDauBang());
                    lvAdapterDauBang.notifyDataSetChanged();

                    tvMonHoc.setText(xepHangMonHoc.getTenMonHoc());
                    tvDoKho.setText(xepHangMonHoc.doKho());
                    tvDiemCaoNhat.setText(String.format("%.1f điểm",xepHangMonHoc.getDiemCaoNhat()));
                    tvXepHangMonHoc.setText(String.valueOf(xepHangMonHoc.getXepHang()));
                    tvViTri.setText(String.valueOf(xepHangMonHoc.getViTri()));
                }
            }
        }.execute(acc != null ? acc.getId() : -1, idMonHoc, (long) doKho);


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
