package com.nhuocquy.tracnghiemapp.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.dto.Feedback;
import com.nhuocquy.tracnghiemapp.model.dto.MyStatus;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class ActivityFeedback extends Activity {
    Button btnSend, btnCancel;
    EditText edtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final long idAcc = getIntent().getLongExtra("idAcc", 0);
        final long idCauHoi = getIntent().getLongExtra("idCauHoi", 0);


        btnSend = (Button) findViewById(R.id.btnSend);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        edtContent = (EditText) findViewById(R.id.edtContent);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                    final String  content = edtContent.getText().toString();
                new AsyncTask<Void, Void, MyStatus>() {
                    RestTemplate rest;
                    Feedback feedback;
                    final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityFeedback.this, ActivityFeedback.this.getResources().getString(R.string.wait), ActivityFeedback.this.getResources().getString(R.string.conecting), true);
                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        rest = new RestTemplate();
                        rest.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                        ((SimpleClientHttpRequestFactory)rest.getRequestFactory()).setReadTimeout(MyConstant.READ_TIME_OUT);
                        ((SimpleClientHttpRequestFactory)rest.getRequestFactory()).setConnectTimeout(MyConstant.CONNECT_TIME_OUT);
                    }

                    @Override
                    protected MyStatus doInBackground(Void... params) {
                        feedback = new Feedback(idAcc,idCauHoi,content);
                        MyStatus myStatus= null;
                        try {
                            myStatus = rest.postForObject(String.format(URL.SUMMIT_FEEDBACK, URL.IP), feedback, MyStatus.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return myStatus;
                    }

                    @Override
                    protected void onPostExecute(MyStatus myStatus) {
                        super.onPostExecute(myStatus);
                        ringProgressDialog.dismiss();
                        if(myStatus == null){
                            Toast.makeText(ActivityFeedback.this, "DisConnect", Toast.LENGTH_LONG).show();
                        }else{
                            if(myStatus.getCode() == MyStatus.CODE_SUCCESS) {
                                finish();
                                Toast.makeText(ActivityFeedback.this, "Cảm ơn bạn đã gửi góp ý cho tôi!hi", Toast.LENGTH_LONG).show();
                            }
                            else
                                Toast.makeText(ActivityFeedback.this, "DisConnect", Toast.LENGTH_LONG).show();
                        }
                    }
                }.execute();
            }
        });
    }
    public void toast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_customize,
                (ViewGroup) findViewById(R.id.custom_toast_layout_id));
        TextView tvMessage = (TextView) layout.findViewById(R.id.tvMessage);
        tvMessage.setText(message);
        // Create Custom Toast
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
