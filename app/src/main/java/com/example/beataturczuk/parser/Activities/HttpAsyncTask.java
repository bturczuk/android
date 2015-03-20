package com.example.beataturczuk.parser.Activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.beataturczuk.parser.Helpers.CommandData;
import com.example.beataturczuk.parser.Helpers.SharedPreferencesHelper;
import com.example.beataturczuk.parser.Internet.JsonParser;
import com.example.beataturczuk.parser.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by beataturczuk on 18.03.15.
 */
class HttpAsyncTask extends AsyncTask<String, String, String> {


    public HttpAsyncTask(Activity activity) {
        this.activity = activity;
    }

    private Activity activity;

    @Override
    protected String doInBackground(String... urls) {

        return JsonParser.httpGetRequest(urls[0]);
    }


    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        try {
            JSONObject json = new JSONObject(result);

            String str;
            String body;
            String author;

            JSONArray articles = json.getJSONArray(CommandData.PRODUCTS);

            for (int i = 0; i < articles.length(); i++) {
                try {
                    body = articles.getJSONObject(i).getString(CommandData.BODY);
                    author = articles.getJSONObject(i).getString(CommandData.AUTHOR);
                    str = "MESSAGE: " + body + "\n\nAUTHOR: " + author;

                    SharedPreferencesHelper helper = new SharedPreferencesHelper(activity);
                    helper.setEtResponse(str);

                    ((TextView) activity.findViewById(R.id.etResponse)).setText(str);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
