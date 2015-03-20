package com.example.beataturczuk.parser.Internet;


import android.util.Log;

import com.example.beataturczuk.parser.Helpers.CommandData;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by beataturczuk on 15.03.15.
 */
public final class JsonParser {

    private JsonParser() {

    }

   // private CommandData data = new CommandData();
    public static String httpGetRequest(String url) {

        InputStream inputStream = null;
        String result = "";
        try {


            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, CommandData.TIMEOUT);

            URL connectURL = new URL(CommandData.URL_ADDRESS);
            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();
            String request = conn.getRequestMethod().toString();

            if (request.equals("GET")) {
                HttpClient httpclient = new DefaultHttpClient(params);
                URI website = new URI(CommandData.URL_ADDRESS);
                HttpGet httpGet = new HttpGet();
                httpGet.setURI(website);
                HttpResponse httpResponse = httpclient.execute(httpGet);

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

            } else if (request.equals("POST")) {

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json; charset=UTF-8");

            }



            // convert inputstream to string
            if (inputStream != null) {
                result = convertInputStreamToString(inputStream);
            } else {
                result = "Did not work!";
            }

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), CommandData.NUMOFDIGITS);
        String line;
        String result = "";
        while ((line = bufferedReader.readLine()) != null) {
            result += line;
            inputStream.close();

            return result;
        }
        return result;
    }
}

