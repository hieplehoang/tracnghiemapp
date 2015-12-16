package com.nhuocquy.tracnghiemapp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.dao.UtilDao;
import com.nhuocquy.tracnghiemapp.model.Khoa;
import com.nhuocquy.tracnghiemapp.model.MonHoc;
import com.nhuocquy.tracnghiemapp.service.ServiceThiThu;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

public class ActivityChonMonThiThu extends AppCompatActivity implements View.OnClickListener {
    private Spinner spinKhoa;
    private Spinner spinNganh;
    private Spinner spinMonHoc;
    private Spinner spinDoKho;
    private Button btnSubmit;


    List<Khoa> listKhoa;
    int khoaPos = -1;
    int nganhPos = -1;
    int monHocPos = -1;
    int doKho = -1;

    ArrayAdapter<String> spinnerAAKhoa, spinnerAANganh, spinnerAAMonHoc;

    private void findViews() {
        spinKhoa = (Spinner) findViewById(R.id.spinKhoa);
        spinNganh = (Spinner) findViewById(R.id.spinNganh);
        spinMonHoc = (Spinner) findViewById(R.id.spinMonHoc);
        spinDoKho = (Spinner) findViewById(R.id.spinDoKho);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnSubmit) {
            submitChonMon();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_mon_thi_thu);

        findViews();

        getDataBaseFromServer();

        setUpSpinner();
    }

    private void submitChonMon() {
        if(khoaPos < 0){
            Toast.makeText(ActivityChonMonThiThu.this, "Bạn chưa chọn khoa!", Toast.LENGTH_LONG).show();
            return;
        }if(nganhPos < 0){
            Toast.makeText(ActivityChonMonThiThu.this, "Bạn chưa chọn ngành!", Toast.LENGTH_LONG).show();
            return;
        }
        if(monHocPos < 0){
            Toast.makeText(ActivityChonMonThiThu.this, "Bạn chưa chọn môn học!", Toast.LENGTH_LONG).show();
            return;
        }
        if(doKho < 0){
            Toast.makeText(ActivityChonMonThiThu.this, "Bạn chưa chọn độ khó!", Toast.LENGTH_LONG).show();
            return;
        }
            
        new AsyncTask<Long, Void, MonHoc>() {
            final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityChonMonThiThu.this, ActivityChonMonThiThu.this.getResources().getString(R.string.wait), ActivityChonMonThiThu.this.getResources().getString(R.string.conecting), true);
            RestTemplate rest;

            @Override
            protected void onPreExecute() {
                rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setReadTimeout(MyConstant.READ_TIME_OUT);
                ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setConnectTimeout(MyConstant.CONNECT_TIME_OUT);
            }

            @Override
            protected MonHoc doInBackground(Long... params) {
                try {
                    MonHoc monHoc = rest.getForObject(String.format(URL.THITHU, URL.IP, params[0], params[1]), MonHoc.class);
                    return monHoc;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(MonHoc monHoc) {
                ringProgressDialog.dismiss();
                if(monHoc == null){
                    Toast.makeText(ActivityChonMonThiThu.this, "Không thể kết nối máy chủ! Không thể tai dử liệu nền!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(ActivityChonMonThiThu.this, monHoc.getTenMonHoc(), Toast.LENGTH_LONG).show();
                    MyVar.setAttribute(MyConstant.MON_HOC, monHoc);
                    Intent intent = new Intent(ActivityChonMonThiThu.this, ActivityNhanDeThi.class);
                    ActivityChonMonThiThu.this.startActivity(intent);

                    Intent intent2 = new Intent(ActivityChonMonThiThu.this, ServiceThiThu.class);
                    intent2.putExtra(ServiceThiThu.COMAMND_GET_ALL_IMAGE,ServiceThiThu.GET_ALL_IMAGE);
                    startService(intent2);
                }
            }
        }.execute(listKhoa.get(khoaPos).getDsNganh().get(nganhPos).getDsMonHoc().get(monHocPos).getId(), (long) doKho);
    }

    private void getDataBaseFromServer() {
        listKhoa = (List<Khoa>) MyVar.getAttribute(MyConstant.LIST_KHOA);
        if (listKhoa == null) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            boolean isWifiEable = mWifi.isConnected() || mMobile.isConnected();
            if (!isWifiEable) {
                Toast.makeText(ActivityChonMonThiThu.this, "No internet access! Không thể tai dử liệu nền!", Toast.LENGTH_LONG).show();
                return;
            }
            new AsyncTask<Void, Void, List<Khoa>>() {
                final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityChonMonThiThu.this, ActivityChonMonThiThu.this.getResources().getString(R.string.wait), ActivityChonMonThiThu.this.getResources().getString(R.string.conecting), true);
                RestTemplate rest;

                @Override
                protected void onPreExecute() {
                    rest = new RestTemplate();
                    rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setReadTimeout(MyConstant.READ_TIME_OUT);
                    ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setConnectTimeout(MyConstant.CONNECT_TIME_OUT);
                }

                @Override
                protected List<Khoa> doInBackground(Void... params) {
                    try {
                        Khoa[] khoas = rest.getForObject(String.format(URL.SYNC_DATA_KHOA, URL.IP), Khoa[].class);
                        return Arrays.asList(khoas);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                    }
                }

                @Override
                protected void onPostExecute(List<Khoa> khoas) {
                    ringProgressDialog.dismiss();
                    if (khoas == null) {
                        Toast.makeText(ActivityChonMonThiThu.this, "Không thể kết nối máy chủ! Không thể tai dử liệu nền!", Toast.LENGTH_LONG).show();
                    } else {
                        MyVar.setAttribute(MyConstant.LIST_KHOA, khoas);
                        listKhoa = khoas;
                        Toast.makeText(ActivityChonMonThiThu.this, "ok!", Toast.LENGTH_LONG).show();
                        //
                        khoaPos = -1;
                        spinnerAAKhoa = new ArrayAdapter<String>(ActivityChonMonThiThu.this, android.R.layout.simple_spinner_item, UtilDao.listKhoaName(listKhoa)); //selected item will look like a spinner set from XML
                        spinnerAAKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinKhoa.setAdapter(spinnerAAKhoa);
                        spinnerAAKhoa.notifyDataSetChanged();
                    }
                }
            }.execute();
        }
    }

    private void setUpSpinner() {
        spinnerAAKhoa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, UtilDao.listKhoaName(listKhoa)); //selected item will look like a spinner set from XML
        spinnerAAKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKhoa.setAdapter(spinnerAAKhoa);

        spinKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                khoaPos = position - 1;

                spinnerAANganh = new ArrayAdapter<String>(ActivityChonMonThiThu.this, android.R.layout.simple_spinner_item, UtilDao.listNganhNameOfKhoa(listKhoa, khoaPos));
                spinnerAANganh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinNganh.setAdapter(spinnerAANganh);
                nganhPos = -1;
                spinnerAANganh.notifyDataSetChanged();

                spinnerAAMonHoc = new ArrayAdapter<String>(ActivityChonMonThiThu.this, android.R.layout.simple_spinner_item, UtilDao.listMonHocOfNganh(listKhoa, khoaPos, -1));
                spinnerAAMonHoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinMonHoc.setAdapter(spinnerAAMonHoc);
                monHocPos = -1;
                spinnerAAMonHoc.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nganhPos = position - 1;

                spinnerAAMonHoc = new ArrayAdapter<String>(ActivityChonMonThiThu.this, android.R.layout.simple_spinner_item, UtilDao.listMonHocOfNganh(listKhoa, khoaPos, nganhPos));
                spinnerAAMonHoc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinMonHoc.setAdapter(spinnerAAMonHoc);
                monHocPos = -1;
                spinnerAAMonHoc.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinMonHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                monHocPos = position - 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> spinnerAADoKho = new ArrayAdapter<String>(ActivityChonMonThiThu.this, android.R.layout.simple_spinner_item, new String[]{"Dể", "Trung bình", "Khó"});
        spinnerAADoKho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDoKho.setAdapter(spinnerAADoKho);

        spinDoKho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                doKho = position+1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
