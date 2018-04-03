package com.example.secureapplication.gncattendance;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by sys on 14-07-2015.
 */
public class HttpTime extends AsyncTask<String,Integer,String> {

    HttpURLConnection c = null;

    @Override
    protected String doInBackground(String... str) {
        try
        {
            String get_url = str[0].replace(" ", "%20");

            int timeout = 3000;

            URL u = new URL(Gobal.CallUrl+"time.php?"+get_url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    return sb.toString();
            }


           /* HttpParams httpParameters = new BasicHttpParams();
            int timeoutConnection = 3000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            int timeoutSocket = 60000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
            HttpClient Client = new DefaultHttpClient(httpParameters);
            HttpGet httpget;
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            httpget = new HttpGet(Gobal.CallUrl+"time.php?"+get_url);
            String content = Client.execute(httpget, responseHandler);
            return content;*/
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return "connection Failed";
    }
}
