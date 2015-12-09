package com.nhuocquy.tracnghiemapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ActivityDangNhap extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox checkboxLdn;
    private Button btnDangNhapLogin;

    private void findViews() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        checkboxLdn = (CheckBox) findViewById(R.id.checkbox_ldn);
        btnDangNhapLogin = (Button) findViewById(R.id.btnDangNhapLogin);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        SharedPreferences ref = getSharedPreferences(MyConstant.REF_NAME, MODE_PRIVATE);

        findViews();

        String username = ref.getString(MyConstant.USERNAME,"");
        edtUsername.setText(username);

        btnDangNhapLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = edtUsername.getText().toString();
                final String password = edtPassword.getText().toString();
                final boolean isLuuDangNhap = checkboxLdn.isChecked();
                new AsyncTask<Void, Void, Account>() {
                    final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityDangNhap.this, ActivityDangNhap.this.getResources().getString(R.string.wait), ActivityDangNhap.this.getResources().getString(R.string.conecting), true);
                    RestTemplate rest;

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        rest = new RestTemplate();
                        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                    }

                    @Override
                    protected Account doInBackground(Void... params) {
                        Account account = rest.getForObject(String.format(URL.LOGIN_USERNAME_PASSWORD, URL.IP, username, password), Account.class);
                        return account;
                    }

                    @Override
                    protected void onPostExecute(Account account) {
                        ringProgressDialog.dismiss();
                        super.onPostExecute(account);
                        if (account == null) {
                            Toast.makeText(ActivityDangNhap.this, "Tài khoảng và mật khẩu không trùng khớp!", Toast.LENGTH_LONG).show();
                        } else {
                            if (account.isLocked()) {
                                Toast.makeText(ActivityDangNhap.this, "Tài khoảng của bạn đã bị lock!", Toast.LENGTH_LONG).show();
                            } else if (account.isKichHoat()) {
                                MyVar.setAttribute( MyConstant.ACCOUNT,account);
                                if(isLuuDangNhap){
                                    SharedPreferences ref = getSharedPreferences(MyConstant.REF_NAME, MODE_PRIVATE);
                                    SharedPreferences.Editor edit = ref.edit();
                                    edit.putLong(MyConstant.ID_ACCOUNT, account.getId());
                                    edit.putString(MyConstant.USERNAME,username);
                                    edit.commit();
                                }
                                setResult(Activity.RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(ActivityDangNhap.this, "Tài khoảng chưa kích hoạt! Bạn vui lòng vào mail kích hoạt!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }.execute();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_dang_nhap, menu);
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
