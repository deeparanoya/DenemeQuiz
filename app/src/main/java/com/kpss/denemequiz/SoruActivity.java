package com.kpss.denemequiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.kpss.denemequiz.Adapter.DenemeSinaviSorulariAdapter;
import com.kpss.denemequiz.Ayarlar.Ayarlar;
import com.kpss.denemequiz.Business.UyeBS;

import java.util.Date;

public class SoruActivity extends Activity {
    AlertDialog builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soru);

        if (AppController.getInstance().isConnected()) {

            AdView mAdView = (AdView) findViewById(R.id.adViewSoru);

            AdRequest adRequest = new AdRequest.Builder().build();

            mAdView.loadAd(adRequest);


            final ViewPager SoruViewPager = (ViewPager) findViewById(R.id.ViewPagerDenemeSinaviSoru);
            try {
                Intent i = getIntent();
                String DenemeID = i.getStringExtra("DenemeID");
                String SinavaGirmisMi = i.getStringExtra("SinavaGirmisMi");

                UyeBS _UyeBs = new UyeBS();

                Date _date = new Date();

                Ayarlar.SinavBaslangic = _date.getTime();
                DenemeSinaviSorulariAdapter oA = new DenemeSinaviSorulariAdapter(this, _UyeBs.UyeGetirDenemeSinaviIDSorulari(DenemeID), _UyeBs.UyeCevaplariGetirDenemeSinaviID(i
                        .getStringExtra("DenemeID"), AppController.getInstance().profileID), SinavaGirmisMi);
                SoruViewPager.setAdapter(oA);
            } catch (Exception e) {


            }

        }

        else {AlertDialogShow();}

    }

    private void AlertDialogShow() {


        if (builder == null) builder = new AlertDialog.Builder(SoruActivity.this)

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
