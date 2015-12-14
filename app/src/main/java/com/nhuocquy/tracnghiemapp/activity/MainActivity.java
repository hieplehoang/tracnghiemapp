package com.nhuocquy.tracnghiemapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.activity.photoview.ActivityPhotoView;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.db.DataBaseHelper;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.service.ServiceThiThu;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class MainActivity extends AppCompatActivity {
    public final static int RESQUEST_CODE_DANG_NHAP = 1;
    Button btnThiThu, btnThiOnline, btnXepHang, btnDangNhap, btnDangKi;
    TextView tvXinChao;
    boolean isDangNhap, isWifiEable;
    Account account;
    SharedPreferences ref = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, ServiceThiThu.class);
        startService(intent);

        ref = getSharedPreferences(MyConstant.REF_NAME, MODE_PRIVATE);

        btnThiThu = (Button) findViewById(R.id.btnThiThu);
        btnThiOnline = (Button) findViewById(R.id.btnThiThat);
        btnXepHang = (Button) findViewById(R.id.btnXepHang);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        btnDangKi = (Button) findViewById(R.id.btnDangKi);
        tvXinChao = (TextView) findViewById(R.id.tvXinChao);
        btnThiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityChonMonThiThu.class);
                startActivity(intent);
            }
        });
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDangNhap) {
                    SharedPreferences.Editor edit = ref.edit();
                    edit.remove(MyConstant.ID_ACCOUNT);
                    edit.commit();
                    MyVar.setAttribute(MyConstant.ACCOUNT, null);
                    finish();
                    startActivity(getIntent());
                } else {
                    Intent intent = new Intent(MainActivity.this, ActivityDangNhap.class);
                    startActivityForResult(intent, RESQUEST_CODE_DANG_NHAP);
                }
            }
        });

        btnDangKi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityDangKi.class);
                startActivity(intent);
            }
        });


        btnXepHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ActivityPhotoView.class);
                account = (Account) MyVar.getAttribute(MyConstant.ACCOUNT);
                if(account != null) {
                    Intent intent = new Intent(MainActivity.this, ActivityDanhSachXepHang.class);
                    startActivity(intent);
                }else
                    Toast.makeText(MainActivity.this, "No Account!!!", Toast.LENGTH_LONG).show();
            }
        });

        autoLogin();
    }

    private void autoLogin() {
        // --check network
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mMobile = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        isWifiEable = mWifi.isConnected() || mMobile.isConnected();
        // check network--

        //--check Account is login

        long idAccount = ref.getLong(MyConstant.ID_ACCOUNT, -1);
        isDangNhap = (account = (Account) MyVar.getAttribute(MyConstant.ACCOUNT)) != null;
        // check Account is login--

        if (isWifiEable) { // neu wifi enable
            if (!isDangNhap) { // neu chua dang nhap
                if (idAccount != -1) { // va da luu tai khoang
                    new AsyncTask<Long, Void, Account>() {
                        RestTemplate rest;
                        final ProgressDialog ringProgressDialog = ProgressDialog.show(MainActivity.this, MainActivity.this.getResources().getString(R.string.wait), MainActivity.this.getResources().getString(R.string.conecting), true);

                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                            rest = new RestTemplate();
                            rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                            ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setReadTimeout(MyConstant.READ_TIME_OUT);
                            ((SimpleClientHttpRequestFactory) rest.getRequestFactory()).setConnectTimeout(MyConstant.CONNECT_TIME_OUT);
                        }

                        @Override
                        protected Account doInBackground(Long... idAccounts) {
                            try {
                                Account account = rest.getForObject(String.format(URL.LOGIN_IDACCOUNT, URL.IP, idAccounts[0]), Account.class);
                                return account;
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Account account) {
                            super.onPostExecute(account);
                            ringProgressDialog.dismiss();
                            if (account == null) {
                                setUpForNoneLogin();
                                Toast.makeText(MainActivity.this, "Không thể kết nối máy chủ!", Toast.LENGTH_LONG).show();
                            } else {
                                MyVar.setAttribute(MyConstant.ACCOUNT, account);
                                setUpForLogined();
                            }
                        }
                    }.execute(idAccount);
                }
            } else { // da dang nhap
                MyVar.setAttribute(MyConstant.ACCOUNT, account);
                setUpForLogined();
            }
        } else {// neu wifi khong enable
            Toast.makeText(MainActivity.this, "No Iternet access!!!", Toast.LENGTH_LONG).show();
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent  intent = new Intent(this, ServiceThiThu.class);
        stopService(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case RESQUEST_CODE_DANG_NHAP:
                if (resultCode == Activity.RESULT_OK) {
                    finish();
                    startActivity(getIntent());
                }
                break;
        }
    }

    private void setUpForLogined() {
        Account account = (Account) MyVar.getAttribute("account");
        tvXinChao.setText(String.format("Xin chao %s!", account.getTenAcc()));
        tvXinChao.setVisibility(View.VISIBLE);

        btnDangNhap.setText(getString(R.string.main_dang_xuat));
        btnDangKi.setVisibility(View.INVISIBLE);
    }

    private void setUpForNoneLogin() {
        btnDangKi.setVisibility(View.VISIBLE);
        btnDangNhap.setText(getString(R.string.main_dang_nhap));
        tvXinChao.setVisibility(View.INVISIBLE);
    }

}
