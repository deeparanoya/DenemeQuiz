package com.kpss.denemequiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kpss.denemequiz.Adapter.UyeGetirDenemeSinavlariAdapter;
import com.kpss.denemequiz.Business.UyeBS;
import com.kpss.denemequiz.Model.UyeGetirDenemeSinavlari;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MainActivity extends Activity {

    AlertDialog builder;
    ListView uyeListView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    UyeGetirDenemeSinavlariAdapter _UyeGetirDenemeSinavlariAdapter;
    UyeBS _UyeBs;
    List<UyeGetirDenemeSinavlari> listUyeGetirDenemeSinavlari;
    ProgressDialog _ProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageViewUserPicture = (ImageView) findViewById(R.id.imageViewUserPicture);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        uyeListView = (ListView) findViewById(R.id.listView);


        if (AppController.getInstance().isConnected()) {
            AdView mAdView = (AdView) findViewById(R.id.adView);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);


            Picasso.with(getApplicationContext())
                    .load("https://graph.facebook.com/" + AppController.getInstance().profileID + "/picture?height=90&width=90")
                    .placeholder(R.drawable.load) // optional
                    .error(R.drawable.load)         // optional
                    .into(imageViewUserPicture);

            _UyeBs = new UyeBS();


            _UyeBs.UyeEkle(AppController.getInstance().profilName, AppController.getInstance().profileID);


            try {

                CreateContent();


                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

                    @Override
                    public void onRefresh() {


                        refreshContent();

                    }
                });


            } catch (Exception e) {

            }


        } else {
            AlertDialogShow();
        }


    }

    private void refreshContent() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                _UyeGetirDenemeSinavlariAdapter = new UyeGetirDenemeSinavlariAdapter(MainActivity.this, _UyeBs.UyeGetirDenemeSinavlari(AppController.getInstance().profileID));
                uyeListView.setAdapter(_UyeGetirDenemeSinavlariAdapter);
                mSwipeRefreshLayout.setRefreshing(false);


            }
        }, 1000);

    }


    private void CreateContent() {

        if (_ProgressDialog == null) {
            _ProgressDialog = new ProgressDialog(MainActivity.this);
            _ProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        if (!_ProgressDialog.isShowing()) _ProgressDialog.show();

        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                _UyeGetirDenemeSinavlariAdapter = new UyeGetirDenemeSinavlariAdapter(MainActivity.this, _UyeBs.UyeGetirDenemeSinavlari(AppController.getInstance().profileID));
                uyeListView.setAdapter(_UyeGetirDenemeSinavlariAdapter);

                if (_ProgressDialog.isShowing()) _ProgressDialog.dismiss();

            }
        });

    }

    private void AlertDialogShow() {


        if (builder == null) builder = new AlertDialog.Builder(MainActivity.this)

                .setMessage("İnternet Bağlantısı Yok")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        if (!builder.isShowing()) builder.show();

    }


}
