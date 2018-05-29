package com.example.zengt9949.mycontactapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    EditText editAddress;
    EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editAddress = findViewById(R.id.editText_address);
        editPhone = findViewById(R.id.editText_phone);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated database");
    }

    public void addData(View view){
        Log.d("MyContactApp","MainActivity: Adding data");
        boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(), editPhone.getText().toString());

        if(isInserted == true){
            Toast.makeText(MainActivity.this,"Success - contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this,"Failed - contact no inserted", Toast.LENGTH_LONG).show();
        }
    }
    public void viewData (View view){
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp","MainActivity: viewData: received cursor" + res.getCount());
        if(res.getCount() == 0){
            showMessage("Error", "No data found in database");
        }

        StringBuffer buffer = new StringBuffer();

        while(res.moveToNext()){
            try {
                for(int i = 0; i < 4 ; i ++) {
                    buffer.append(res.getString(i) + "\n");
                }
            }
            catch(Exception e){

            }
        }
        Log.d("MyContactApp","MainActivity: viewData: assembled stringBuffer");
        showMessage("Data", buffer.toString());

    }

    public void showMessage(String title, String message) {
        Log.d("MyContactApp","MainActivity: showMessage: building alert dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.zengt9949.mycontactapp.MESSAGE";
    public void SearchRecord(View view){
        Log.d("MyContactApp","MainACtivity: showMessage: launching SearchActivity");
        Cursor res = myDb.getAllData();
        int id = 0;
        while(res.moveToNext()){
            try {
                for(int i = 0; i < 4 ; i ++) {
                    if(editName.getText().toString().equals(res.getString(i))){
                        id = i;
                    }
                }
            }
            catch(Exception e){

            }
        }

        Log.d("MyContactApp","MainACtivity: showMessage: ID found: " + id);
        Intent intent = new Intent(this, SearchActivity.class);
        Log.d("MyContactApp","MainACtivity: showMessage: intent instantiated");
        intent.putExtra(EXTRA_MESSAGE, res.getString(id));
        Log.d("MyContactApp","MainACtivity: showMessage: intent putExtra complete");
        startActivity(intent);
    }

}
