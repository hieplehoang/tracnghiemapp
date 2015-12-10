package com.nhuocquy.tracnghiemapp.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.Khoa;
import com.nhuocquy.tracnghiemapp.model.dto.MyStatus;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class ActivityDangKi extends AppCompatActivity implements View.OnClickListener {
    private EditText edtMSSV;
    private EditText edtPass;
    private EditText edtRepass;
    private EditText edtName;
    private EditText edtClass;
    private TextView tvBirthDay;
    private ImageButton bntNgaySinh;
    private Spinner spinKhoa;
    private Spinner spinNganh;
    private Button btnSignUp;


    List<Khoa> listKhoa;
    int khoaPos = -1;
    int nganhPos = -1;

    ArrayAdapter<String> spinnerArrayAdapterKhoa, spinnerArrayAdapterNganh;

    private void findViews() {
        edtMSSV = (EditText) findViewById(R.id.edtMSSV);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtRepass = (EditText) findViewById(R.id.edtRepass);
        edtName = (EditText) findViewById(R.id.edtName);
        edtClass = (EditText) findViewById(R.id.edtClass);
        tvBirthDay = (TextView) findViewById(R.id.tvBirthDay);
        bntNgaySinh = (ImageButton) findViewById(R.id.bntNgaySinh);
        spinKhoa = (Spinner) findViewById(R.id.spinKhoa);
        spinNganh = (Spinner) findViewById(R.id.spinNganh);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        bntNgaySinh.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == bntNgaySinh) {
            openDateDialog();
        } else if (v == btnSignUp) {
            signUp();
        }
    }

    private void signUp() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        boolean isWifiEable = mWifi.isConnected() || mMobile.isConnected();
        if (!isWifiEable) {
            Toast.makeText(ActivityDangKi.this, "No internet access!", Toast.LENGTH_LONG).show();
            return;
        }
        String mssv = edtMSSV.getText().toString();
        if (mssv.equals("")) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa nhập mssv!", Toast.LENGTH_LONG).show();
            return;
        }
        String pass = edtPass.getText().toString();
        if (pass.equals("")) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa nhập password!", Toast.LENGTH_LONG).show();
            return;
        }
        String repass = edtRepass.getText().toString();
        if (repass.equals("")) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa nhập lại password!", Toast.LENGTH_LONG).show();
            return;
        }
        if (!pass.equals(repass)) {
            Toast.makeText(ActivityDangKi.this, "Password và nhập lại password không trùng khớp!", Toast.LENGTH_LONG).show();
            return;
        }

        String name = edtName.getText().toString();
        if (name.equals("")) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa nhập tên!", Toast.LENGTH_LONG).show();
            return;
        }
        String classs = edtClass.getText().toString();
        if( classs.equals("")) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa nhập lớp!", Toast.LENGTH_LONG).show();
            return;
        };
        String birtdDay = tvBirthDay.getText().toString();
        if (birtdDay.equals("")) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa chọn ngày sinh!", Toast.LENGTH_LONG).show();
            return;
        }
        if (khoaPos < 0) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa chọn khoa!", Toast.LENGTH_LONG).show();
            return;
        }
        if (nganhPos < 0) {
            Toast.makeText(ActivityDangKi.this, "Bạn chưa chọn ngành!", Toast.LENGTH_LONG).show();
            return;
        }
        final Account account = new Account();
        account.setUsername(mssv);
        account.setMSSV(Integer.parseInt(mssv));
        account.setPassword(pass);
        account.setTenAcc(name);
        account.setLop(classs);
        account.setKhoaID(listKhoa.get(khoaPos).getId());
        account.setNganhID(listKhoa.get(khoaPos).getDsNganh().get(nganhPos).getId());
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        try {
            account.setNgaySinh(f.parse(birtdDay));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        new AsyncTask<Account, Void, MyStatus>() {
            final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityDangKi.this, ActivityDangKi.this.getResources().getString(R.string.wait), ActivityDangKi.this.getResources().getString(R.string.conecting), true);
            RestTemplate rest;

            @Override
            protected void onPreExecute() {
                rest = new RestTemplate();
                rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            }

            @Override
            protected MyStatus doInBackground(Account... params) {
                try {
                    return rest.postForObject(String.format(URL.REGISTER, URL.IP), params[0], MyStatus.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(MyStatus myStatus) {
                ringProgressDialog.dismiss();
                if (myStatus == null) {
                    Toast.makeText(ActivityDangKi.this, "Không thể kết nối server!", Toast.LENGTH_LONG).show();
                } else {
                    if (myStatus.getCode() == MyStatus.CODE_SUCCESS) {
                        new AlertDialog.Builder(ActivityDangKi.this)
                                .setTitle("Đăng ký thành công.")
                                .setMessage("Bạn vui lòng vào mail sinh viên để kích hoạt tài khoản!")
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        ActivityDangKi.this.finish();
                                    }
                                }).show();
                    } else {
                        Toast.makeText(ActivityDangKi.this, myStatus.getObj().toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }.execute(account);

    }

    private void openDateDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener;
        DatePickerDialog datePickerDialog;

        onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvBirthDay.setText(String.format("%s/%s/%s", dayOfMonth, monthOfYear + 1, year));
            }
        };

        datePickerDialog = new DatePickerDialog(ActivityDangKi.this, onDateSetListener, 1990, 1, 1);
        datePickerDialog.show();
    }

    private void setUpView() {
        spinnerArrayAdapterKhoa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listKhoaName(listKhoa)); //selected item will look like a spinner set from XML
        spinnerArrayAdapterKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKhoa.setAdapter(spinnerArrayAdapterKhoa);

        spinKhoa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                khoaPos = position - 1;
//                spinnerArrayAdapterNganh.clear();
//                String[] nganhName = listNganhNameOfKhoa(listKhoa, position);
//                for (int i = 0; i < nganhName.length; i++) {
//                    spinnerArrayAdapterNganh.insert(nganhName[i], i);
//                }
                spinnerArrayAdapterNganh = new ArrayAdapter<String>(ActivityDangKi.this, android.R.layout.simple_spinner_item, listNganhNameOfKhoa(listKhoa, khoaPos)); //selected item will look like a spinner set from XML
                spinnerArrayAdapterNganh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinNganh.setAdapter(spinnerArrayAdapterNganh);
                spinnerArrayAdapterNganh.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerArrayAdapterNganh = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listNganhNameOfKhoa(listKhoa, khoaPos)); //selected item will look like a spinner set from XML
        spinnerArrayAdapterNganh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNganh.setAdapter(spinnerArrayAdapterNganh);

        spinNganh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nganhPos = position - 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ki);

        findViews();

        getDataBaseFromServer();

        setUpView();
    }

    public void getDataBaseFromServer() {
        listKhoa = (List<Khoa>) MyVar.getAttribute(MyConstant.LIST_KHOA);
        if (listKhoa == null) {
            ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            boolean isWifiEable = mWifi.isConnected() || mMobile.isConnected();
            if (!isWifiEable) {
                Toast.makeText(ActivityDangKi.this, "No internet access! Không thể tai dử liệu nền!", Toast.LENGTH_LONG).show();
                return;
            }
            new AsyncTask<Void, Void, List<Khoa>>() {
                final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityDangKi.this, ActivityDangKi.this.getResources().getString(R.string.wait), ActivityDangKi.this.getResources().getString(R.string.conecting), true);
                RestTemplate rest;

                @Override
                protected void onPreExecute() {
                    rest = new RestTemplate();
                    rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                }

                @Override
                protected List<Khoa> doInBackground(Void... params) {
                    Khoa[] khoas = rest.getForObject(String.format(URL.SYNC_DATA_KHOA, URL.IP), Khoa[].class);
                    return Arrays.asList(khoas);
                }

                @Override
                protected void onPostExecute(List<Khoa> khoas) {
                    ringProgressDialog.dismiss();
                    if (khoas == null) {
                        Toast.makeText(ActivityDangKi.this, "Không thể kết nối máy chủ! Không thể tai dử liệu nền!", Toast.LENGTH_LONG).show();
                    } else {
                        MyVar.setAttribute(MyConstant.LIST_KHOA, khoas);
                        listKhoa = khoas;
                        Toast.makeText(ActivityDangKi.this, "ok!", Toast.LENGTH_LONG).show();
                        //
                        spinnerArrayAdapterKhoa = new ArrayAdapter<String>(ActivityDangKi.this, android.R.layout.simple_spinner_item, listKhoaName(listKhoa)); //selected item will look like a spinner set from XML
                        spinnerArrayAdapterKhoa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinKhoa.setAdapter(spinnerArrayAdapterKhoa);
                        spinnerArrayAdapterKhoa.notifyDataSetChanged();
                    }
                }
            }.execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_dang_ki, menu);
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

    private String[] listKhoaName(List<Khoa> list) {
        if (list == null)
            return new String[]{"Chọn khoa"};
        String[] arr = new String[list.size() + 1];
        arr[0] = "Chọn khoa";
        for (int i = 0; i < list.size(); i++) {
            arr[i + 1] = list.get(i).getTenKhoa();
        }
        return arr;
    }

    private String[] listNganhNameOfKhoa(List<Khoa> list, int pos) {
        if (list == null || pos < 0)
            return new String[]{"Chọn ngành."};
        Khoa khoa = list.get(pos);
        String[] arr = new String[khoa.getDsNganh().size() + 1];
        arr[0] = "Chọn ngành.";
        for (int i = 0; i < khoa.getDsNganh().size(); i++) {
            arr[i + 1] = khoa.getDsNganh().get(i).getTenNganh();
        }
        return arr;
    }
}

