package com.kpss.denemequiz.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kpss.denemequiz.AppController;
import com.kpss.denemequiz.Async.AsyncBaseClass;
import com.kpss.denemequiz.Ayarlar.Ayarlar;
import com.kpss.denemequiz.Business.UyeBS;
import com.kpss.denemequiz.Model.UyeGetirDenemeSinaviIDSorulari;
import com.kpss.denemequiz.Model.UyeSinavCevapGetir;
import com.kpss.denemequiz.R;
import com.kpss.denemequiz.SinavBitirActivity;
import com.kpss.denemequiz.SoruActivity;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

/**
 * Created by Mehmet on 15.1.2017.
 */

public class DenemeSinaviSorulariAdapter extends PagerAdapter {

    Context context;
    List<UyeSinavCevapGetir> listUyeSinavCevapGetir;
    AlertDialog builder;
    int Dogru = 0;
    int Yanlis = 0;
    int Bos = 0;
    String DenemeSinaviID;
    private LayoutInflater mInflater;
    private List<UyeGetirDenemeSinaviIDSorulari> listUyeGetirDenemeSinaviIDSorulari;
    private Boolean SinavaGirmisMi;

    public DenemeSinaviSorulariAdapter(Context _Context, List<UyeGetirDenemeSinaviIDSorulari> UyeGetirDenemeSinaviIDSorulari, List<UyeSinavCevapGetir> UyeSinavCevapGetir, String
            SinavaGirmismi) {

        this.listUyeGetirDenemeSinaviIDSorulari = UyeGetirDenemeSinaviIDSorulari;
        this.listUyeSinavCevapGetir = UyeSinavCevapGetir;
        this.SinavaGirmisMi = SinavaGirmismi.equals("Evet") ? true : false;
        context = _Context;


    }


    @Override
    public int getCount() {
        return listUyeGetirDenemeSinaviIDSorulari.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        this.mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View satirView;


        satirView = mInflater.inflate(R.layout.row_denemesinavisoru, container, false);

        ImageView imageViewDenemeSinaviSoru = (ImageView) satirView.findViewById(R.id.imageViewDenemeSinaviSoru);
        TextView textViewSoruSayisi = (TextView) satirView.findViewById(R.id.textViewSoruSayisi);
        TextView textViewZaman = (TextView) satirView.findViewById(R.id.textViewZaman);
        RadioButton RadioButtonA = (RadioButton) satirView.findViewById(R.id.radioButtonA);
        RadioButton RadioButtonB = (RadioButton) satirView.findViewById(R.id.radioButtonB);
        RadioButton RadioButtonC = (RadioButton) satirView.findViewById(R.id.radioButtonC);
        RadioButton RadioButtonD = (RadioButton) satirView.findViewById(R.id.radioButtonD);
        RadioButton RadioButtonE = (RadioButton) satirView.findViewById(R.id.radioButtonE);
        RadioGroup RadioGroupCevap = (RadioGroup) satirView.findViewById(R.id.RadiGroupCevap);
        final Button ButtonDenemeSinaviBitir = (Button) satirView.findViewById(R.id.ButtonSinavBitir);

        final UyeGetirDenemeSinaviIDSorulari _UyeGetirDenemeSinaviIDSorulari = listUyeGetirDenemeSinaviIDSorulari.get(position);
        Date _date = new Date();


        final int Zaman = (int) ((_date.getTime() - Ayarlar.SinavBaslangic) / 1000);
        textViewSoruSayisi.setText("Soru : " + Integer.toString(position + 1));
        if (!SinavaGirmisMi) {
            textViewZaman.setText(Ayarlar.getDurationString(Zaman));
        } else {

            textViewZaman.setText("Cevap : " + _UyeGetirDenemeSinaviIDSorulari.Cevap);
            ButtonDenemeSinaviBitir.setText("SINAV DETAYLARI");
            RadioGroupCevap.setEnabled(false);
            RadioButtonA.setEnabled(false);
            RadioButtonB.setEnabled(false);
            RadioButtonC.setEnabled(false);
            RadioButtonD.setEnabled(false);
            RadioButtonE.setEnabled(false);
        }


        Picasso.with(context)
                .load(Ayarlar.Domain + _UyeGetirDenemeSinaviIDSorulari.ResimPath)
                .placeholder(R.drawable.load) // optional
                .error(R.drawable.load)         // optional
                .into(imageViewDenemeSinaviSoru);


        ButtonDenemeSinaviBitir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                satirView.setEnabled(false);

                if (AppController.getInstance().isConnected()) {


                    DenemeSinaviID = listUyeGetirDenemeSinaviIDSorulari.get(1).DenemeSinaviID;


                    AsyncBaseClass mr = new AsyncBaseClass(context) {

                        @Override
                        protected String doInBackground(String... strings) {

                            final UyeBS _UyeBs = new UyeBS();
                            for (int i = 0; i < listUyeSinavCevapGetir.size(); i++) {

                                final String SoruID = listUyeSinavCevapGetir.get(i).SoruID;
                                String GercekCevap = listUyeGetirDenemeSinaviIDSorulari.get(i).Cevap;
                                final String Cevap = listUyeSinavCevapGetir.get(i).Cevap.equalsIgnoreCase("null") ? listUyeSinavCevapGetir.get(i).Cevap = "X" : listUyeSinavCevapGetir.get(i).Cevap;
                                final String UyeID = AppController.getInstance().profileID;
                                if (GercekCevap.equalsIgnoreCase(Cevap)) {
                                    Dogru++;

                                } else if (Cevap.equalsIgnoreCase("X")) {
                                    Bos++;
                                } else {
                                    Yanlis++;
                                }
                                if (!SinavaGirmisMi) {


                                    ((SoruActivity) context).runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            _UyeBs.UyeSinavCevapEkle(SoruID, Cevap, UyeID);
                                        }
                                    });

                                }

                            }

                            return "";

                        }

                        @Override
                        protected void onPostExecute(String response) {
                            if (!SinavaGirmisMi) {

                                UyeBS _UyeBs = new UyeBS();
                                _UyeBs.UyeDenemeSinaviDetayEkle(DenemeSinaviID, Integer.toString(Dogru), Integer.toString(Yanlis), AppController.getInstance().profileID, Integer.toString
                                        (Zaman), Integer.toString(Bos));
                            }
                            ButtonDenemeSinaviBitir.setEnabled(false);
                            Intent intent = new Intent(context, SinavBitirActivity.class);
                            intent.putExtra("DenemeID", DenemeSinaviID);


                            context.startActivity(intent);
                        }
                    };

                    mr.execute();


                } else {
                    AlertDialogShow();
                }

                satirView.setEnabled(true);
            }
        });


        String Cevap = listUyeSinavCevapGetir.get(position).Cevap;

        try {


            switch (Cevap) {
                case "A":
                    RadioGroupCevap.check(R.id.radioButtonA);
                    break;
                case "B":
                    RadioGroupCevap.check(R.id.radioButtonB);
                    break;
                case "C":
                    RadioGroupCevap.check(R.id.radioButtonC);
                    break;
                case "D":
                    RadioGroupCevap.check(R.id.radioButtonD);
                    break;
                case "E":
                    RadioGroupCevap.check(R.id.radioButtonE);
                    break;

                default:
                    break;
            }
        } catch (Exception ex) {

        }


        RadioGroupCevap.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) { //set the Model to hold the answer the user picked
                    case R.id.radioButtonA:

                        listUyeSinavCevapGetir.get(position).Cevap = "A";


                        break;
                    case R.id.radioButtonB:

                        listUyeSinavCevapGetir.get(position).Cevap = "B";

                        break;
                    case R.id.radioButtonC:

                        listUyeSinavCevapGetir.get(position).Cevap = "C";
                        break;
                    case R.id.radioButtonD:

                        listUyeSinavCevapGetir.get(position).Cevap = "D";

                        break;
                    case R.id.radioButtonE:

                        listUyeSinavCevapGetir.get(position).Cevap = "E";

                        break;
                    default:

                        break;
                }

            }
        });


        container.addView(satirView);


        return satirView;


    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {


        container.removeView((LinearLayout) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == (LinearLayout) object);

    }

    private void AlertDialogShow() {


        if (builder == null) builder = new AlertDialog.Builder(context)

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
