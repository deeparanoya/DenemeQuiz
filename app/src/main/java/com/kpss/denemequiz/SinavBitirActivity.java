package com.kpss.denemequiz;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.kpss.denemequiz.Adapter.UyeleriGetirDenemeSinaviIDAdapter;
import com.kpss.denemequiz.Async.AsyncBaseClass;
import com.kpss.denemequiz.Ayarlar.Ayarlar;
import com.kpss.denemequiz.Business.UyeBS;
import com.kpss.denemequiz.Model.UyeGetirDenemeSinavlari;
import com.squareup.picasso.Picasso;

public class SinavBitirActivity extends Activity {

    AlertDialog builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinav_bitir);


        if (AppController.getInstance().isConnected()) {

            AdView mAdView = (AdView) findViewById(R.id.adViewSinavBitir);
            AdRequest adRequest = new AdRequest.Builder().build();
            mAdView.loadAd(adRequest);


            SinavBitirActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Intent i = getIntent();
                    String DenemeID = i.getStringExtra("DenemeID");

                    TextView textViewKullaniciAdiSinav = (TextView) findViewById(R.id.textViewKullaniciAdiSinavB);
                    TextView textViewKullaniciAdiSiralama = (TextView) findViewById(R.id.textViewKullaniciAdiSiralamB);
                    TextView textViewDogruSinavBitir = (TextView) findViewById(R.id.textViewDogruSinavBitirB);
                    TextView textViewYanlisSinavBitir = (TextView) findViewById(R.id.textViewYanlisSinavBitirB);
                    TextView textViewZamanSinavBitir = (TextView) findViewById(R.id.textViewZamanSinavBitirB);
                    TextView textViewKullaniciAdiPuan = (TextView) findViewById(R.id.textViewKullaniciAdiPuanB);
                    TextView textViewToplamGirenB = (TextView) findViewById(R.id.textViewToplamGirenB);
                    TextView textViewBosSinavBitirB = (TextView) findViewById(R.id.textViewBosSinavBitirB);


                    ImageView imageViewSinavBitirB = (ImageView) findViewById(R.id.imageViewSinavBitirB);

                    Picasso.with(getApplicationContext())
                            .load(AppController.getInstance().profilImageUrl)
                            .placeholder(R.drawable.load)
                            .fit()
                            .error(R.drawable.load)         // optional
                            .into(imageViewSinavBitirB);

                    UyeBS _UyeBs = new UyeBS();

                    UyeGetirDenemeSinavlari _UyeGetirDenemeSinavlariSingle = _UyeBs.UyeGetirDenemeSinavlariSingle(AppController.getInstance().profileID, DenemeID);


                    textViewKullaniciAdiSinav.setText(AppController.getInstance().profilName);
                    textViewKullaniciAdiSiralama.setText("Sıralama : " + _UyeGetirDenemeSinavlariSingle.Siralama);
                    textViewDogruSinavBitir.setText("Doğru : " + _UyeGetirDenemeSinavlariSingle.Dogru);
                    textViewYanlisSinavBitir.setText("Yanlış : " + _UyeGetirDenemeSinavlariSingle.Yanlis);
                    textViewZamanSinavBitir.setText("Zaman : " + Ayarlar.getDurationString(Integer.parseInt(_UyeGetirDenemeSinavlariSingle.Zaman)));
                    textViewKullaniciAdiPuan.setText("Puan : " + _UyeGetirDenemeSinavlariSingle.Puan);
                    textViewToplamGirenB.setText("Toplam Giren : " + _UyeGetirDenemeSinavlariSingle.ToplamGiren);
                    textViewBosSinavBitirB.setText("Boş : " + _UyeGetirDenemeSinavlariSingle.Bos);

                    final ListView SinavBitirListView = (ListView) findViewById(R.id.ListViewSinavBitir);
                    try {


                        UyeleriGetirDenemeSinaviIDAdapter oA = new UyeleriGetirDenemeSinaviIDAdapter(SinavBitirActivity.this, _UyeBs.UyeleriGetirDenemeSinavID(DenemeID));
                        SinavBitirListView.setAdapter(oA);
                    } catch (Exception e) {

                    }


                    ShareDialog   shareDialog = new ShareDialog(SinavBitirActivity.this);

                    if(shareDialog.canShow(ShareLinkContent.class))
                    {
                        ShareLinkContent shareLinkContent = new ShareLinkContent.Builder()
                                .setContentTitle("PEARLKPSS TÜRKİYE GENELİ KPSS DENEME SINAVI")
                                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.kpss.denemequiz"))
                                .setContentDescription("Türkiye Geneli Kpss Deneme Sınavında "+ _UyeGetirDenemeSinavlariSingle.Siralama+". oldum")
                                .setImageUrl(Uri.parse("http://kpssdenemesinavi.somee.com/192.png"))
                                .build();

                        shareDialog.show(shareLinkContent);
                    }
                }
            });

        } else {

            AlertDialogShow();
        }

    }

    @Override
    public void onBackPressed() {

        AsyncBaseClass mr = new AsyncBaseClass(SinavBitirActivity.this) {
            @Override
            protected String doInBackground(String... strings) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
                return null;
            }


        };

        mr.execute();






    }

    private void AlertDialogShow() {


        if (builder == null) builder = new AlertDialog.Builder(SinavBitirActivity.this)

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
