package com.kpss.denemequiz.Async;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Mehmet on 27.1.2017.
 */

public abstract class AsyncBaseClass extends AsyncTask<String, Integer, String> {

    public ProgressDialog progressDialog = null;
    private Context _Context;
    private String message = null;

    public AsyncBaseClass(Context _Context) {

        this._Context=_Context;
    }

    /**
     * İşlem öncesi yapılacaklar bu metod içinde gerçekleşir. UI thread içinde çalışır.
     */
    @Override
    protected void onPreExecute() {
        // İşlem göstergeçini devam eden işlemlerde kullanıcıyı bilgilendirmek için kullanacağız.
        progressDialog = new ProgressDialog(_Context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.show();
    }

    /**
     * Arkaplan işlemimiz burada gerçekleşir. Android bu işlem için bir thread açar bu sebeple burada direk UI içinde bir işlem yapmamalıyız.
     */
    @Override
    protected String doInBackground(String... strings) {


        return null;
    }

    /**
     * Arkaplan işlemimiz bittiğinde bu metod çalışır.
     */
    @Override
    protected void onPostExecute(String response) {
        progressDialog.setCancelable(true);

        if (null == message) {
            progressDialog.setTitle("Tamamlandı");

            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }


        } else {
            progressDialog.setTitle("Error :(");
            progressDialog.setMessage(message);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }



        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
            }
        }, null == message ? 700 : 3000);
    }

    /**
     * Arkaplan işlemi iptal edildiğinde bu metod çalışır.
     */
    @Override
    protected void onCancelled() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }


    }

    public String getMessage() {
        return message;
    }
}
