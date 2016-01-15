package com.nhuocquy.tracnghiemapp.asyntask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.constant.URL;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.MonHoc;
import com.nhuocquy.tracnghiemapp.model.dto.MyStatus;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NhuocQuy on 1/15/2016.
 */
public class SubmitDiem extends AsyncTask<Void, Void,MyStatus> {
    Activity activity;

    public SubmitDiem(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected MyStatus doInBackground(Void... params) {
        MonHoc monHoc = (MonHoc) MyVar.getAttribute(MyConstant.MON_HOC);
        Account account = (Account) MyVar.getAttribute(MyConstant.ACCOUNT);
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
        map.add("tenAcc", (account != null? account.getTenAcc(): "Noname" ));
        map.add("diem", monHoc.calDiemThi() + "");
        map.add("doKho", monHoc.getDoKho() + "");
        MyStatus myStatus = null;
        while(true) {
            try {
                myStatus = rest.postForObject(String.format(URL.SUMMIT_DIEM_THITHU, URL.IP), map, MyStatus.class);
                return myStatus;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onPostExecute(MyStatus myStatus) {
        super.onPostExecute(myStatus);
        if(myStatus == null){
            Toast.makeText(activity, "Không thể kết nối máy chủ!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(activity, "Submit thành công!", Toast.LENGTH_LONG).show();
        }
    }

    ;
}
