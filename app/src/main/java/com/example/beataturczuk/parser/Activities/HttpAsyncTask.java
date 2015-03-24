package com.example.beataturczuk.parser.Activities;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import com.example.beataturczuk.parser.Helpers.CommandData;
import com.example.beataturczuk.parser.Helpers.DBHelper;
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
        mydb = new DBHelper(activity);
    }

    private Activity activity;
    private DBHelper mydb;

    @Override
    protected String doInBackground(String... urls) {

        return JsonParser.httpGetRequest(urls[0]);
    }


    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {

        try {
            JSONObject json = new JSONObject(result);
            JSONArray articles = json.getJSONArray(CommandData.PRODUCTS);

            String str;
            String body;
            String author;


            for (int i = 0; i < articles.length(); i++) {
                try {
                    JSONObject jObject = articles.getJSONObject(i);
                    body = jObject.getString(CommandData.BODY).toString();
                    author = jObject.getString(CommandData.AUTHOR).toString();
                    str = "MESSAGE: " + body + "\n\nAUTHOR: " + author;

                    mydb.insertData(body, author);

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
