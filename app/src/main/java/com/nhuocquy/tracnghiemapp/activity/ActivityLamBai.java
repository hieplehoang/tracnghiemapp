package com.nhuocquy.tracnghiemapp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.nhuocquy.tracnghiemapp.R;
import com.nhuocquy.tracnghiemapp.activity.photoview.ActivityPhotoView;
import com.nhuocquy.tracnghiemapp.adapter.GVAdapterDapAn;
import com.nhuocquy.tracnghiemapp.constant.MyConstant;
import com.nhuocquy.tracnghiemapp.constant.MyVar;
import com.nhuocquy.tracnghiemapp.model.Account;
import com.nhuocquy.tracnghiemapp.model.MonHoc;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class ActivityLamBai extends AppCompatActivity {
    public static String IS_SHOW_ANSWER = "isShowAnswer";
    boolean isShowAnswer = false;
    TextView tvCauHoi, tvGiaiThich;
    ImageView imgCauHoi;
    GVAdapterDapAn gridViewAdapter;
    GridView gridView;
    MonHoc monHoc;
    int cauHoiPos = 0;
    CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        monHoc = (MonHoc) MyVar.getAttribute(MyConstant.MON_HOC);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lam_bai);
        isShowAnswer = getIntent().getBooleanExtra(IS_SHOW_ANSWER, false);
        tvCauHoi = (TextView) findViewById(R.id.tvCauHoi);
        tvGiaiThich = (TextView) findViewById(R.id.tvGiaiThich);
        imgCauHoi = (ImageView) findViewById(R.id.imgCauHoi);
        imgCauHoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLamBai.this, ActivityPhotoView.class);
                intent.putExtra(ActivityPhotoView.LIST_IMAGE, (Serializable) monHoc.getDsCauHoi().get(cauHoiPos).getDsHinh());
                intent.putExtra(ActivityPhotoView.CAUHOI_POST, cauHoiPos);
                ActivityLamBai.this.startActivity(intent);
            }
        });

        gridView = (GridView) findViewById(R.id.gvCauhoi);

        gridViewAdapter = new GVAdapterDapAn(this, isShowAnswer);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_MOVE) {
                    return true;
                }
                return false;
            }

        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view.findViewById(R.id.imvHinhDapAn);

            }
        });

        //
        if (!isShowAnswer)
            timer = new CountDownTimer(monHoc.getThoiGian() * 60 * 1000, 1000) {
                String format = "%02d:%02d";

                @Override
                public void onTick(long millisUntilFinished) {
                    if (millisUntilFinished > 60000) {
                        ActivityLamBai.this.setTitle(String.format(format, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                    } else {
                        ActivityLamBai.this.setTitle(Html.fromHtml("<font color='#ff0000'>" + String.format(format, TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))) + "</font>"));
                    }
                }

                @Override
                public void onFinish() {
                    nopBai();
                }
            }.start();
        else
            ActivityLamBai.this.setTitle("Xem đáp án");
        setUpView();
    }

    public void setUpView() {
        tvCauHoi.setText(String.format("Câu %s: %s", cauHoiPos + 1, monHoc.getDsCauHoi().get(cauHoiPos).getNoiDung()));

        gridViewAdapter.setListDapAn(monHoc.getDsCauHoi().get(cauHoiPos).getDsDapAn());
        gridViewAdapter.notifyDataSetChanged();
        getListViewSize(gridView);

        imgCauHoi.setVisibility(monHoc.getDsCauHoi().get(cauHoiPos).getDsHinh().isEmpty() ? View.INVISIBLE : View.VISIBLE);
        if (isShowAnswer) {
            tvGiaiThich.setVisibility(View.VISIBLE);
            tvGiaiThich.setText(monHoc.getDsCauHoi().get(cauHoiPos).getGiaiThich());
        } else {
            tvGiaiThich.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_lam_bai, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.mnBtnSubmit:
                if (!isShowAnswer) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage("Bạn có chắc chắn nộp bài trước khi kết giờ chứ? Nếu nộp bài, bạn không thể quay lại bài làm của bạn được!")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    nopBai();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
                return true;
            case R.id.mnBtnLeft:
                if (cauHoiPos > 0) {
                    cauHoiPos--;
                    setUpView();
                }
                return true;
            case R.id.mnBtnRight:
                if (cauHoiPos < monHoc.getDsCauHoi().size() - 1) {
                    cauHoiPos++;
                    setUpView();
                }
                return true;
            case R.id.mnFeedback:
                Account account = (Account) MyVar.getAttribute(MyConstant.ACCOUNT);
                Intent intent = new Intent(this, ActivityFeedback.class);
                intent.putExtra("idAcc", account != null ? account.getId():0);
                intent.putExtra("idCauHoi", monHoc.getDsCauHoi().get(cauHoiPos).getId());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public static void getListViewSize(GridView myListView) {
        GVAdapterDapAn myListAdapter = (GVAdapterDapAn) myListView.getAdapter();
        if (myListAdapter == null) {
            //do nothing return null
            return;
        }
        //set listAdapter in loop for getting final size
        int totalHeight = 0;

        for (int size = 0; size < myListAdapter.getCount(); size++) {
            View listItem = myListAdapter.getView(size, null, myListView);
            listItem.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (listItem.getMeasuredHeightAndState() > totalHeight)
                totalHeight = listItem.getMeasuredHeight();
        }
        Log.e("Chuy chinh chep", totalHeight + "");
        totalHeight = 300;
        totalHeight = totalHeight * (myListAdapter.getCount() / 2 + (myListAdapter.getCount() % 2 > 0 ? 1 : 0));
        //setting listview item in adapter
        ViewGroup.LayoutParams params = myListView.getLayoutParams();
        params.height = totalHeight;
        myListView.setLayoutParams(params);
        // print height of adapter on log
//        Log.i("height of listItem:", String.valueOf(totalHeight));
    }

    boolean res = false;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!isShowAnswer) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                finish();
                                break;
                            case DialogInterface.BUTTON_NEGATIVE:

                                break;
                        }
                    }
                };
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLamBai.this);
                builder.setMessage("Bạn có muốn dừng làm bài không?").setPositiveButton("Yes", dialogClickListener).setNegativeButton("No", dialogClickListener).show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void nopBai() {
        if (!isShowAnswer) {
            timer.cancel();
            Intent intent = new Intent(this, ActivityKetQuaThi.class);
            startActivity(intent);
            finish();
        }
    }
}
