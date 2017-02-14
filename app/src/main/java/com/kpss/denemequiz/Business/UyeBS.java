package com.kpss.denemequiz.Business;

import com.kpss.denemequiz.Async.AsyncWebService;
import com.kpss.denemequiz.Ayarlar.Ayarlar;
import com.kpss.denemequiz.Model.Uye;
import com.kpss.denemequiz.Model.UyeGetirDenemeSinaviIDSorulari;
import com.kpss.denemequiz.Model.UyeGetirDenemeSinavlari;
import com.kpss.denemequiz.Model.UyeGetirDenemeSinavlariSiralama;
import com.kpss.denemequiz.Model.UyeSinavCevapGetir;
import com.kpss.denemequiz.Model.UyeleriGetirDenemeSinavID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;


/**
 * Created by Mehmet on 9.1.2017.
 */

public class UyeBS {

    private Boolean donenDegerUyeEkle;

    private Uye donenDegerLoginKontrol;
    private List<UyeGetirDenemeSinaviIDSorulari> donenDegerUyeGetirDenemeSinaviIDSorulari;
    private List<UyeGetirDenemeSinavlari> donenDegerUyeGetirDenemeSinavlari;
    private UyeGetirDenemeSinavlari donenDegerUyeGetirDenemeSinavlariSingle;
    private List<UyeGetirDenemeSinavlariSiralama> donenDegerUyeGetirDenemeSinavlariSiralama;
    private List<UyeleriGetirDenemeSinavID> donenDegerUyeleriGetirDenemeSinavID;
    private List<UyeSinavCevapGetir> donenDegerUyeSinavCevapGetir;


    public Boolean UyeEkle(String KullaniciAdi, String UyeID) {


        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("KullaniciAdi", KullaniciAdi);
        parameters.put("UyeID", UyeID);
        parameters.put("key", Ayarlar.Key);

        try {
            donenDegerUyeEkle = ((new AsyncWebService("UyeEkle", parameters).execute().get()).equalsIgnoreCase("true")) ? true : false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeEkle;

    }
    public Uye LoginKontrol(String KullaniciAdi, String UyeID) {

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("KullaniciAdi", KullaniciAdi);
        parameters.put("UyeID", UyeID);
        parameters.put("Key", Ayarlar.Key);


        JSONObject contacts = null;
        donenDegerLoginKontrol = new Uye();

        try {
            contacts = new JSONObject(new AsyncWebService("LoginKontrol", parameters).execute().get());


            String id = contacts.getString("id");
            String _KullaniciAdi = contacts.getString("KullaniciAdi");
            String _Password = contacts.getString("Password");
            donenDegerLoginKontrol.id = id;
            donenDegerLoginKontrol.KullaniciAdi = _KullaniciAdi;
            donenDegerLoginKontrol.Password = _Password;


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        return donenDegerLoginKontrol;
    }
    public List<UyeGetirDenemeSinavlari> UyeGetirDenemeSinavlari(String uyeID) {

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("uyeID", uyeID);
        parameters.put("Key", Ayarlar.Key);
        JSONArray contacts = null;

        donenDegerUyeGetirDenemeSinavlari = new ArrayList<UyeGetirDenemeSinavlari>();

        try {
            contacts = new JSONArray(new AsyncWebService("UyeGetirDenemeSinavlari", parameters).execute().get());


            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String id = c.getString("id");
                String DenemeSinaviAdi = c.getString("DenemeSinaviAdi");
                String Konu = c.getString("Konu");
                String Dogru = c.getString("Dogru").equalsIgnoreCase("null") ? "0" : c.getString("Dogru");
                String Yanlis = c.getString("Yanlis").equalsIgnoreCase("null") ? "0" : c.getString("Yanlis");
                String Puan = c.getString("Puan").equalsIgnoreCase("null") ? "0" : c.getString("Puan");
                String ToplamGiren = c.getString("ToplamGiren").equalsIgnoreCase("null") ? "0" : c.getString("ToplamGiren");
                String Siralama = c.getString("Ranked").equalsIgnoreCase("null") ? "0" : c.getString("Ranked");
                String Zaman = c.getString("Zaman").equalsIgnoreCase("null") ? "0" : c.getString("Zaman");
                String Bos = c.getString("Bos").equalsIgnoreCase("null") ? "0" : c.getString("Bos");

                UyeGetirDenemeSinavlari _UyeGetirDenemeSinavi = new UyeGetirDenemeSinavlari();
                _UyeGetirDenemeSinavi.id = id;
                _UyeGetirDenemeSinavi.DenemeSinaviAdi = DenemeSinaviAdi;
                _UyeGetirDenemeSinavi.Konu = Konu;
                _UyeGetirDenemeSinavi.Dogru = Dogru;
                _UyeGetirDenemeSinavi.Yanlis = Yanlis;
                _UyeGetirDenemeSinavi.Puan = Puan;
                _UyeGetirDenemeSinavi.Siralama = Siralama;
                _UyeGetirDenemeSinavi.ToplamGiren = ToplamGiren;
                _UyeGetirDenemeSinavi.Zaman = Zaman;
                _UyeGetirDenemeSinavi.Bos = Bos;

                // adding contact to contact list
                donenDegerUyeGetirDenemeSinavlari.add(_UyeGetirDenemeSinavi);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeGetirDenemeSinavlari;
    }
    public UyeGetirDenemeSinavlari UyeGetirDenemeSinavlariSingle(String uyeID, String DenemeSinavID) {

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("DenemeSinaviID", DenemeSinavID);
        parameters.put("UyeID", uyeID);
        parameters.put("Key", Ayarlar.Key);
        JSONArray contacts = null;

        donenDegerUyeGetirDenemeSinavlariSingle = new UyeGetirDenemeSinavlari();

        try {
            contacts = new JSONArray(new AsyncWebService("UyeGetirDenemeSinavIDDetay", parameters).execute().get());


            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String id = c.getString("id");
                String DenemeSinaviAdi = c.getString("DenemeSinaviAdi");
                String Konu = c.getString("Konu");
                String Dogru = c.getString("Dogru").equalsIgnoreCase("null") ? "0" : c.getString("Dogru");
                String Yanlis = c.getString("Yanlis").equalsIgnoreCase("null") ? "0" : c.getString("Yanlis");
                String Puan = c.getString("Puan").equalsIgnoreCase("null") ? "0" : c.getString("Puan");
                String ToplamGiren = c.getString("ToplamGiren").equalsIgnoreCase("null") ? "0" : c.getString("ToplamGiren");
                String Siralama = c.getString("Ranked").equalsIgnoreCase("null") ? "0" : c.getString("Ranked");
                String Zaman = c.getString("Zaman").equalsIgnoreCase("null") ? "0" : c.getString("Zaman");
                String Bos = c.getString("Bos").equalsIgnoreCase("null") ? "0" : c.getString("Bos");


                donenDegerUyeGetirDenemeSinavlariSingle.id = id;
                donenDegerUyeGetirDenemeSinavlariSingle.DenemeSinaviAdi = DenemeSinaviAdi;
                donenDegerUyeGetirDenemeSinavlariSingle.Konu = Konu;
                donenDegerUyeGetirDenemeSinavlariSingle.Dogru = Dogru;
                donenDegerUyeGetirDenemeSinavlariSingle.Yanlis = Yanlis;
                donenDegerUyeGetirDenemeSinavlariSingle.Puan = Puan;
                donenDegerUyeGetirDenemeSinavlariSingle.Siralama = Siralama;
                donenDegerUyeGetirDenemeSinavlariSingle.ToplamGiren = ToplamGiren;
                donenDegerUyeGetirDenemeSinavlariSingle.Zaman = Zaman;
                donenDegerUyeGetirDenemeSinavlariSingle.Bos = Bos;


            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeGetirDenemeSinavlariSingle;
    }
    public List<UyeGetirDenemeSinaviIDSorulari> UyeGetirDenemeSinaviIDSorulari(String DenemeSinaviID) {

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("DenemeSinaviID", DenemeSinaviID);
        parameters.put("Key", Ayarlar.Key);
        JSONArray contacts = null;

        donenDegerUyeGetirDenemeSinaviIDSorulari = new ArrayList<UyeGetirDenemeSinaviIDSorulari>();

        try {
            contacts = new JSONArray(new AsyncWebService("UyeGetirDenemeSinaviIDSorulari", parameters).execute().get());


            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String id = c.getString("id");
                String ResimPath = c.getString("ResimPath");
                String Cevap = c.getString("Cevap");
                String _DenemeSinaviID = c.getString("DenemeSinaviID");


                UyeGetirDenemeSinaviIDSorulari _UyeGetirDenemeSinaviIDSorulari = new UyeGetirDenemeSinaviIDSorulari();
                _UyeGetirDenemeSinaviIDSorulari.id = id;
                _UyeGetirDenemeSinaviIDSorulari.ResimPath = ResimPath;
                _UyeGetirDenemeSinaviIDSorulari.Cevap = Cevap;
                _UyeGetirDenemeSinaviIDSorulari.DenemeSinaviID = _DenemeSinaviID;

                donenDegerUyeGetirDenemeSinaviIDSorulari.add(_UyeGetirDenemeSinaviIDSorulari);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeGetirDenemeSinaviIDSorulari;
    }
    public List<UyeSinavCevapGetir> UyeCevaplariGetirDenemeSinaviID(String DenemeSinaviID, String UyeID) {

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("DenemeSinaviID", DenemeSinaviID);
        parameters.put("UyeID", UyeID);
        parameters.put("Key", Ayarlar.Key);
        JSONArray contacts = null;

        donenDegerUyeSinavCevapGetir = new ArrayList<UyeSinavCevapGetir>();

        try {
            contacts = new JSONArray(new AsyncWebService("UyeCevaplariGetirDenemeSinaviID", parameters).execute().get());


            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String id = c.getString("id");
                String _UyeID = c.getString("UyeID");
                String Cevap = c.getString("Cevap");
                String _SoruID = c.getString("id1");


                UyeSinavCevapGetir _UyeSinavCevapGetir = new UyeSinavCevapGetir();
                _UyeSinavCevapGetir.id = id;
                _UyeSinavCevapGetir.Cevap = Cevap;
                _UyeSinavCevapGetir.UyeID = _UyeID;
                _UyeSinavCevapGetir.SoruID = _SoruID;

                donenDegerUyeSinavCevapGetir.add(_UyeSinavCevapGetir);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeSinavCevapGetir;
    }
    public boolean UyeSinavCevapEkle(String SoruID, String Cevap, String UyeID) {


        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("SoruID", SoruID);
        parameters.put("Cevap", Cevap);
        parameters.put("UyeID", UyeID);
        parameters.put("Key", Ayarlar.Key);

        try {
            donenDegerUyeEkle = ((new AsyncWebService("UyeSinaviDetayEkle", parameters).execute().get()).equalsIgnoreCase("true")) ? true : false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeEkle;


    }
    public boolean UyeDenemeSinaviDetayEkle(String DenemeSinaviID, String Dogru, String Yanlis, String UyeID, String Zaman, String Bos) {

        int ToplamSoru=Integer.parseInt(Dogru)+Integer.parseInt(Yanlis)+Integer.parseInt(Bos);
        double BirSoruDeger=100/ToplamSoru;
        double ToplamPuan=(Integer.parseInt(Dogru)*BirSoruDeger)-Integer.parseInt(Yanlis);

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("DenemeSinaviID", DenemeSinaviID);
        parameters.put("Dogru", Dogru);
        parameters.put("Yanlis", Yanlis);
        parameters.put("Zaman", Zaman);
        parameters.put("UyeID", UyeID);
        parameters.put("Bos", Bos);
        parameters.put("Puan", Double.toString(ToplamPuan));
        parameters.put("Key", Ayarlar.Key);

        try {
            donenDegerUyeEkle = ((new AsyncWebService("UyeDenemeSinaviDetayEkle", parameters).execute().get()).equalsIgnoreCase("true")) ? true : false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeEkle;


    }
    public List<UyeleriGetirDenemeSinavID> UyeleriGetirDenemeSinavID(String DenemeSinaviID) {

        HashMap<String, String> parameters = new HashMap<String, String>();
        parameters.put("DenemeSinaviID", DenemeSinaviID);
        parameters.put("Key", Ayarlar.Key);
        JSONArray contacts = null;

        donenDegerUyeleriGetirDenemeSinavID = new ArrayList<UyeleriGetirDenemeSinavID>();

        try {
            contacts = new JSONArray(new AsyncWebService("UyeleriGetirDenemeSinavID", parameters).execute().get());


            // looping through All Contacts
            for (int i = 0; i < contacts.length(); i++) {
                JSONObject c = contacts.getJSONObject(i);

                String KullaniciAdi = c.getString("KullaniciAdi");
                String Dogru = c.getString("Dogru");
                String Yanlis = c.getString("Yanlis");
                String Zaman = c.getString("Zaman");
                String Bos = c.getString("Bos");
                String Puan = c.getString("Puan");
                String Ranked = c.getString("Ranked");
                String UyeID=c.getString("id");


                UyeleriGetirDenemeSinavID _UyeleriGetirDenemeSinavID = new UyeleriGetirDenemeSinavID();
                _UyeleriGetirDenemeSinavID.KullaniciAdi=KullaniciAdi;
                _UyeleriGetirDenemeSinavID.Dogru=Dogru;
                _UyeleriGetirDenemeSinavID.Yanlis=Yanlis;
                _UyeleriGetirDenemeSinavID.Zaman=Zaman;
                _UyeleriGetirDenemeSinavID.Puan=Puan;
                _UyeleriGetirDenemeSinavID.Ranked=Ranked;
                _UyeleriGetirDenemeSinavID.UyeID=UyeID;
                _UyeleriGetirDenemeSinavID.Bos=Bos;


                donenDegerUyeleriGetirDenemeSinavID.add(_UyeleriGetirDenemeSinavID);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return donenDegerUyeleriGetirDenemeSinavID;
    }

}
