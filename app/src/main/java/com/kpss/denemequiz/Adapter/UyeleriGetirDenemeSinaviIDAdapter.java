package com.kpss.denemequiz.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kpss.denemequiz.Ayarlar.Ayarlar;
import com.kpss.denemequiz.Model.UyeleriGetirDenemeSinavID;
import com.kpss.denemequiz.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Mehmet on 18.1.2017.
 */

public class UyeleriGetirDenemeSinaviIDAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    private List<UyeleriGetirDenemeSinavID> listUyeleriGetirDenemeSinavID;

    public UyeleriGetirDenemeSinaviIDAdapter(Activity ac, List<UyeleriGetirDenemeSinavID> ListUyeleriGetirDenemeSinavID) {
        this.mInflater = (LayoutInflater) ac.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.listUyeleriGetirDenemeSinavID = ListUyeleriGetirDenemeSinavID;
        context = ac;


    }

    @Override
    public int getCount() {
        return listUyeleriGetirDenemeSinavID.size();
    }

    @Override
    public Object getItem(int position) {
        return listUyeleriGetirDenemeSinavID.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        View satirView;


        satirView = mInflater.inflate(R.layout.row_sinav_bitir, null);
        TextView textViewKullaniciAdiSinav = (TextView) satirView.findViewById(R.id.textViewKullaniciAdiSinav);
        TextView textViewKullaniciAdiSiralama = (TextView) satirView.findViewById(R.id.textViewKullaniciAdiSiralama);
        TextView textViewDogruSinavBitir = (TextView) satirView.findViewById(R.id.textViewDogruSinavBitir);
        TextView textViewYanlisSinavBitir = (TextView) satirView.findViewById(R.id.textViewYanlisSinavBitir);
        TextView textViewZamanSinavBitir = (TextView) satirView.findViewById(R.id.textViewZamanSinavBitir);
        TextView textViewKullaniciAdiPuan = (TextView) satirView.findViewById(R.id.textViewKullaniciAdiPuan);
        ImageView imageViewSinavBitir=(ImageView) satirView.findViewById(R.id.imageViewSinavBitir);
        TextView textViewBosSinavBitir = (TextView) satirView.findViewById(R.id.textViewBosSinavBitir);




        final UyeleriGetirDenemeSinavID _UyeleriGetirDenemeSinavID = listUyeleriGetirDenemeSinavID.get(position);

        textViewKullaniciAdiSinav.setText(_UyeleriGetirDenemeSinavID.KullaniciAdi);
        textViewKullaniciAdiSiralama.setText("Sıralama : "+_UyeleriGetirDenemeSinavID.Ranked);
        textViewDogruSinavBitir.setText("Doğru : "+_UyeleriGetirDenemeSinavID.Dogru);
        textViewYanlisSinavBitir.setText("Yanlış : " +_UyeleriGetirDenemeSinavID.Yanlis);
        textViewZamanSinavBitir.setText("Zaman : "+ Ayarlar.getDurationString(Integer.parseInt(_UyeleriGetirDenemeSinavID.Zaman)));
        textViewKullaniciAdiPuan.setText("Puan : "+ _UyeleriGetirDenemeSinavID.Puan);
        textViewBosSinavBitir.setText("Boş : "+ _UyeleriGetirDenemeSinavID.Bos);


        Picasso.with(context)
                .load("https://graph.facebook.com/"+_UyeleriGetirDenemeSinavID.UyeID+"/picture?height=100&width=100")
                .placeholder(R.drawable.load)
                .fit()
                .error(R.drawable.load)         // optional
                .into(imageViewSinavBitir);


        return satirView;


    }


}
