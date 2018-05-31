package com.example.zengt9949.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Get intent
        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_NAME);
        /*String address = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_ADDRESS);
        String phone = intent.getStringExtra(MainActivity.EXTRA_MESSAGE_PHONE);*/

        //Capture the layout's textView and set the string as the text
        TextView textView1 = findViewById(R.id.textView4);
        textView1.setText(name);
        /*TextView textView2 = findViewById(R.id.textView5);
        textView2.setText(address);
        TextView textView3 = findViewById(R.id.textView6);
        textView3.setText(phone);*/

    }
}
