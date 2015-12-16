package com.nhuocquy.tracnghiemapp.activity.photoview;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.service.ServiceThiThu;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ActivityPhotoView extends AppCompatActivity {

    private static final String ISLOCKED_ARG = "isLocked";
    public static final String LIST_IMAGE = "listImage";

    private ViewPager mViewPager;
    SamplePagerAdapter adapter;
    Messenger mService;
    boolean isBound;
    ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            mService = new Messenger(service);
            isBound = true;
            Toast.makeText(ActivityPhotoView.this, "Connected service",
                    Toast.LENGTH_LONG).show();
            for (String fileName : adapter.unRegister){
                adapter.registerService(fileName);
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            mService = null;
            isBound = false;
            Toast.makeText(ActivityPhotoView.this, "Disconect service",
                    Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);


        mViewPager = (HackyViewPager) findViewById(R.id.view_pager);
        setContentView(mViewPager);

//        List<String> listFile = new ArrayList<>();
//        listFile.add("0763742627.jpg");
//        listFile.add("17_earth_song.jpg");
//        listFile.add("514E0SN8Z2L.jpg");
//        listFile.add("Hanoi.jpg");
        List<String> listFile = (List<String>) getIntent().getSerializableExtra(LIST_IMAGE);
        if(listFile == null){
            listFile = new ArrayList<>();
        }
        Log.e("ActivityPhotoView", listFile.toString());
        adapter= new SamplePagerAdapter(this, listFile);
        mViewPager.setAdapter(adapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ActivityPhotoView.this.setTitle(String.format("%s/%s", position+1, adapter.getCount()));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if (savedInstanceState != null) {
            boolean isLocked = savedInstanceState.getBoolean(ISLOCKED_ARG, false);
            ((HackyViewPager) mViewPager).setLocked(isLocked);
        }
        doBindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindService();

    }

    void doBindService() {
        bindService(new Intent(ActivityPhotoView.this,
                ServiceThiThu.class), mConnection, Context.BIND_AUTO_CREATE);
        Toast.makeText(ActivityPhotoView.this, "Connecting service",
                Toast.LENGTH_LONG).show();

    }

    void doUnbindService() {
        if (isBound) {
            unbindService(mConnection);
            isBound = false;
        }
        Toast.makeText(ActivityPhotoView.this, "Disconnecting service",
                Toast.LENGTH_LONG).show();
    }

    class SamplePagerAdapter extends PagerAdapter {

        List<String> list;
        ActivityPhotoView activity;
        String tmpImage;
        int current, next;
        boolean isFirst = true;
        List<String> unRegister = new ArrayList<>();
        PhotoView photoView;

        class IncomingHandler extends Handler {
            /**
             * @param msg what: action
             *            obj: image name
             */
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case ServiceThiThu.GET_IMAGE:// new asyntask to getImage
                        current = ActivityPhotoView.this.mViewPager.getCurrentItem();
                        if (msg.obj.equals(list.get(current))) {
                            Log.e("ActiviPhotoView anh ve", msg.obj.toString());
                            String photoPath = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + list.get(current);
//                            photoView.setImageDrawable(Drawable.createFromPath(photoPath));
                            SamplePagerAdapter.this.notifyDataSetChanged();
                            unRegisterService(msg.obj.toString());
                        }
                        break;
                    default:
                        super.handleMessage(msg);
                }
            }
        }

        final Messenger mMessenger = new Messenger(new IncomingHandler());

        public SamplePagerAdapter(ActivityPhotoView activity, List<String> list) {
            this.activity = activity;
            this.list = list;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            photoView = new PhotoView(container.getContext());
            tmpImage = list.get(position);

            Log.e("ActivityPhotoView", tmpImage);
            String photoPath = activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/" + tmpImage;
            File file = new File(photoPath);

            if (file.exists()) {
                Drawable d = Drawable.createFromPath(photoPath);
                photoView.setImageDrawable(d);
            } else {
                registerService(tmpImage);
            }
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        public void unRegisterService(String fileName) {
            if (isBound) {
                if (mService != null) {
                    try {
                        Message msg = Message.obtain(null,
                                ServiceThiThu.MSG_UNREGISTER_CLIENT);
                        msg.replyTo = mMessenger;
                        msg.obj = fileName;
                        mService.send(msg);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void registerService(String fileName) {
            if (isBound) {
                if (mService != null) {
                    try {
                        Message msg = Message.obtain(null,
                                ServiceThiThu.GET_IMAGE);
                        msg.replyTo = mMessenger;
                        msg.obj = fileName;
                        mService.send(msg);

                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }else{
                unRegister.add(fileName);
            }
        }

    }

    public static String getExtention(String path) {
        return path.substring(path.length() - 7, path.length());
    }
}
