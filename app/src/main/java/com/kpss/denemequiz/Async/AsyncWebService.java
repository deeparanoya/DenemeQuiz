package com.kpss.denemequiz.Async;

import android.os.AsyncTask;

import com.kpss.denemequiz.Ayarlar.Ayarlar;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Mehmet on 10.1.2017.
 */

public class AsyncWebService extends AsyncTask<String, String, String> {


    private String methodName;
    private String donenDeger;
    private Map<String, String> parameters;


    public AsyncWebService(String methodName, Map<String, String> parameters) {

        this.methodName = methodName;
        this.parameters = parameters;

    }


    @Override
    protected void onPostExecute(String s) {
        donenDeger = s;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {
            donenDeger = WebServiceBaglan();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        return donenDeger;
    }


    private String WebServiceBaglan() throws IOException, XmlPullParserException {
        String SOAP_ACTION = Ayarlar.SoapAction + methodName;
        String METHOD_NAME = methodName;
        String NAMESPACE = "http://tempuri.org/";
        String URL = Ayarlar.WebServiceDomain;
        String donenDeger = "";

            SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
            if (parameters != null) {
                for (Map.Entry<String, String> para : parameters.entrySet()) {
                    Request.addProperty(para.getKey(), para.getValue());
                }
            }

            SoapSerializationEnvelope soapEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            soapEnvelope.dotNet = true;
            soapEnvelope.setOutputSoapObject(Request);

            HttpTransportSE transport = new HttpTransportSE(URL);

            transport.call(SOAP_ACTION, soapEnvelope);
            SoapPrimitive resultString = (SoapPrimitive) soapEnvelope.getResponse();
            donenDeger = (resultString).toString();




        return donenDeger;


    }


}










