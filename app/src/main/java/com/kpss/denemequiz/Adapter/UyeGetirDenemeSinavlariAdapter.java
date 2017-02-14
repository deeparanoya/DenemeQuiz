package com.kpss.denemequiz.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpss.denemequiz.Async.AsyncBaseClass;
import com.kpss.denemequiz.Ayarlar.Ayarlar;
import com.kpss.denemequiz.Model.UyeGetirDenemeSinavlari;
import com.kpss.denemequiz.R;
import com.kpss.denemequiz.SoruActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mehmet on 14.1.2017.
 */

public class UyeGetirDenemeSinavlariAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    private List<UyeGetirDenemeSinavlari> listUyeGetirDenemeSinavlari;


    public UyeGetirDenemeSinavlariAdapter(Activity ac, List<UyeGetirDenemeSinavlari> ListUyeGetirDenemeSinavları) {
        this.mInflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listUyeGetirDenemeSinavlari = ListUyeGetirDenemeSinavları;
        context = ac;


    }


    @Override
    public int getCount() {
        return listUyeGetirDenemeSinavlari.size();
    }

    @Override
    public Object getItem(int position) {
        return listUyeGetirDenemeSinavlari.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
       // final Context context = parent.getContext();


        if (convertView == null) {
            holder = new ViewHolder();



            convertView = mInflater.inflate(R.layout.row_uyegetirdenemesinavlari, parent, false);

            holder.textViewBaslik = (TextView) convertView.findViewById(R.id.textViewBaslik);
            holder.textViewKonu = (TextView) convertView.findViewById(R.id.textViewKonu);
            holder.textViewDogru = (TextView) convertView.findViewById(R.id.textViewDogru);
            holder.textViewYanlis = (TextView) convertView.findViewById(R.id.textViewYanlis);
            holder.textViewPuan = (TextView) convertView.findViewById(R.id.textViewPuan);
            holder.textViewToplamGiren = (TextView) convertView.findViewById(R.id.textViewToplamGiren);
            holder.textViewSiralama = (TextView) convertView.findViewById(R.id.textViewSiralama);
            holder.textViewZaman = (TextView) convertView.findViewById(R.id.textViewZaman);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageViewUyeGetirDenemeSinavlari);
            holder.textViewBos = (TextView) convertView.findViewById(R.id.textViewBos);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }



        final UyeGetirDenemeSinavlari _UyeGetirDenemeSinavlari = listUyeGetirDenemeSinavlari.get(position);
        holder.textViewBaslik.setText(_UyeGetirDenemeSinavlari.DenemeSinaviAdi);
        holder.textViewKonu.setText(_UyeGetirDenemeSinavlari.Konu);
        holder.textViewDogru.setText("Doğru : " + _UyeGetirDenemeSinavlari.Dogru);
        holder.textViewYanlis.setText("Yanlış : " + _UyeGetirDenemeSinavlari.Yanlis);
        holder.textViewPuan.setText("Puan : " + _UyeGetirDenemeSinavlari.Puan);
        holder.textViewToplamGiren.setText("Toplam Giren : " + _UyeGetirDenemeSinavlari.ToplamGiren);
        holder.textViewSiralama.setText("Siralama : " + _UyeGetirDenemeSinavlari.Siralama);
        holder.textViewZaman.setText("Zaman : " + Ayarlar.getDurationString(Integer.parseInt(_UyeGetirDenemeSinavlari.Zaman)));
        holder.textViewBos.setText("Boş : " + _UyeGetirDenemeSinavlari.Bos);


        if (_UyeGetirDenemeSinavlari.Puan.toString().equals("0")) {
            Picasso.with(context)
                    .load(R.drawable.lockopen)
                    .placeholder(R.drawable.load)
                    .fit()
                    .error(R.drawable.load)         // optional
                    .into(holder.imageView);
        } else {
            Picasso.with(context)
                    .load(R.drawable.lock)
                    .placeholder(R.drawable.load)
                    .fit()
                    .error(R.drawable.load)         // optional
                    .into(holder.imageView);

        }


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncBaseClass mr = new AsyncBaseClass(context) {
                    @Override
                    protected String doInBackground(String... strings) {
                        String SinavaGirmisMi = _UyeGetirDenemeSinavlari.Puan.toString().equals("0") ? "Hayır" : "Evet";
                        Intent intent = new Intent(context, SoruActivity.class);
                        intent.putExtra("DenemeID", _UyeGetirDenemeSinavlari.id);
                        intent.putExtra("SinavaGirmisMi", SinavaGirmisMi);
                        context.startActivity(intent);
                        return null;
                    }


                };

                mr.execute();


                // TODO Auto-generated method stub


            }
        });


        return convertView;


    }


    private class ViewHolder {
        TextView textViewBaslik;
        TextView textViewKonu;
        TextView textViewDogru;
        TextView textViewYanlis;
        TextView textViewPuan;
        TextView textViewToplamGiren;
        TextView textViewSiralama;
        TextView textViewZaman;
        ImageView imageView;
        TextView textViewBos;
    }


}
