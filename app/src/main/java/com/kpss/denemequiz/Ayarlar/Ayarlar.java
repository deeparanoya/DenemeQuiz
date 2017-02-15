package com.kpss.denemequiz.Ayarlar;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Date;

/**
 * Created by Mehmet on 8.1.2017.
 */

public class Ayarlar  {

    public static final String Domain = "http://kpssdenemesinavi.somee.com/"; //Web Service kurulumunu yaptığınız site adresi
    public static final String WebServiceDomain = "http://kpssdenemesinavi.somee.com/AndroidService/WebService.asmx"; //Web servis yolu
    public static final String SoapAction = "http://tempuri.org/";
    public static final String Key = "100"; //Webserviste method kullanma şifresi.
    public static Long SinavBaslangic = null; //Kullanıcı bir deneme sınavına başladığında başlangıç zamanını atar
    public static String GecisReklam="ca-app-pub-0947510276857574/9309314790"; //Admob Inıtal Reklam idsi

    //Saniyeyi zamana çevirir. Örn: 00:06:48
    public static String getDurationString(int seconds) {

        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;

        return twoDigitString(hours) + ":" + twoDigitString(minutes) + ":" + twoDigitString(seconds);
    }

    public static String twoDigitString(int number) {

        if (number == 0) {
            return "00";
        }

        if (number / 10 == 0) {
            return "0" + number;
        }

        return String.valueOf(number);
    }



}
