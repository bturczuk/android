package com.example.beataturczuk.parser.Activities;


import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import com.example.beataturczuk.parser.Helpers.CommandData;
import com.example.beataturczuk.parser.Internet.NetworkConnection;
import com.example.beataturczuk.parser.R;

public class MainActivity extends Activity {

    private TextView etResponse;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // call AsynTask to perform network operation on separate thread
        new HttpAsyncTask(this).execute(CommandData.URL_ADDRESS);

        if (!NetworkConnection.networkStatus(getApplicationContext())) {
            NetworkConnection.noNetworkDialog(this);
        } else {
            return;
        }

        etResponse = (TextView) findViewById(R.id.etResponse);
    }
}

