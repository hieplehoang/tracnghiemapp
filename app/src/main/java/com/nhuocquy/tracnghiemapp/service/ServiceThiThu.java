package com.nhuocquy.tracnghiemapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ServiceThiThu extends Service {
    public static final int MSG_REGISTER_CLIENT = 1;
    public static final int MSG_UNREGISTER_CLIENT = 2;
    public static final int GET_IMAGE = 3;
    Map<String, Set<Messenger>> mapRegister = new HashMap<>();

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_REGISTER_CLIENT:
                    String fileName = (String) msg.obj;
                    Set<Messenger> list = mapRegister.get(fileName);
                    if (list == null) {
                        list = new HashSet<>();
                        mapRegister.put(fileName, list);
                    }
                    list.add(msg.replyTo);
                    break;
                case MSG_UNREGISTER_CLIENT:
                    String fileName2 = (String) msg.obj;
                    Set<Messenger> list2 = mapRegister.get(fileName2);
                    if (list2 != null) {
                        list2.remove(msg.obj);
                        if (list2.isEmpty())
                            mapRegister.remove(fileName2);
                    }
                    break;
                case GET_IMAGE:// new asyntask to getImage
                    // register
                     String fileName3 = (String) msg.obj;
                    Log.e("ServiceThiThu", fileName3);
                    Set<Messenger> list3 = mapRegister.get(fileName3);
                    if (list3 == null) {
                        list3 = new HashSet<>();
                        mapRegister.put(fileName3, list3);
                    }
                    list3.add(msg.replyTo);

                    new AsyncTask<String, Void, String>() {
                        @Override
                        protected void onPreExecute() {
                            super.onPreExecute();
                        }

                        @Override
                        protected String doInBackground(String... params) {
                            List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
                            messageConverters.add(new ByteArrayHttpMessageConverter());
                            RestTemplate res = new RestTemplate();
                            res.setMessageConverters(messageConverters);

                            HttpHeaders headers = new HttpHeaders();
                            headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

                            HttpEntity<String> entity = new HttpEntity<String>(headers);

                            ResponseEntity<byte[]> response = res.exchange(
                                    "http://www2.hcmuaf.edu.vn/data/quoctuan/" +params[0],
                                    HttpMethod.GET, entity, byte[].class, "1");

                            if (response.getStatusCode() == HttpStatus.OK) {
                                String photoPath = ServiceThiThu.this.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + params[0];
                                try {
                                    FileOutputStream fos = new FileOutputStream(photoPath);
                                    fos.write(response.getBody());
                                    fos.close();

                                    Log.e("ServiceThiThu Done", params[0]);

                                    Set<Messenger> list = mapRegister.get(params[0]);
                                    Log.e("ServiceThiThu Done", mapRegister.toString());
                                    if (list != null) {
                                        for (Messenger m : list) {
                                            Message msg = Message.obtain(null,
                                                    ServiceThiThu.GET_IMAGE);
                                            msg.obj = params[0];
                                            try {
                                                m.send(msg);
                                                Log.e("ServiceThiThu send", params[0]);
                                            } catch (RemoteException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return params[0];
                        }

                        @Override
                        protected void onPostExecute(String s) {
                            super.onPostExecute(s);
                        }
                    }.execute(fileName3);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    final Messenger mMessenger = new Messenger(new IncomingHandler());

    public ServiceThiThu() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "Service start", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service detroy", Toast.LENGTH_LONG).show();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
