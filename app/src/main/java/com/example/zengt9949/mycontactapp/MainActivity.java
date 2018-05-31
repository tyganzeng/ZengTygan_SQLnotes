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

        StringBuffer result = new StringBuffer();

        while(res.moveToNext()){
            try {

                for(int i = 0; i < 4; i++){
                    switch(i){
                        case 0: result = result.append("ID: ");
                            break;
                        case 1: result = result.append("NAME: ");
                            break;
                        case 2: result = result.append("ADDRESS: ");
                            break;
                        default: result = result.append("PHONE: ");
                            break;
                    }
                    result = result.append(res.getString(i) + "\n");
                }
                result = result.append("\n");
            }
            catch(Exception e){

            }
        }
        res.moveToFirst();
        Log.d("MyContactApp",res.getString(1));
        Log.d("MyContactApp","MainActivity: viewData: assembled stringBuffer");
        showMessage("Data", result.toString());

    }

    public void showMessage(String title, String message) {
        Log.d("MyContactApp","MainActivity: showMessage: building alert dialog");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE_NAME = "com.example.zengt9949.mycontactapp.NAME";
    public static final String EXTRA_MESSAGE_ADDRESS = "com.example.zengt9949.mycontactapp.ADDRESS";
    public static final String EXTRA_MESSAGE_PHONE = "com.example.zengt9949.mycontactapp.PHONE";
   // public static final String EXTRA_MESSAGE_NAME = "com.example.zengt9949.mycontactapp.NAME";
    public void SearchRecord(View view){
        Log.d("MyContactApp","MainACtivity: showMessage: launching SearchActivity");
        Cursor res = myDb.getAllData();
        //int id = 0;
        boolean found = false;
        StringBuffer result = new StringBuffer();
        while(res.moveToNext()){
            try {
                if(editName.getText().toString().equals(res.getString(1))){
                    //id = res.getPosition();
                    for(int i = 0; i < 4; i++){
                        switch(i){
                            case 0: result = result.append("ID: ");
                            break;
                            case 1: result = result.append("NAME: ");
                                break;
                            case 2: result = result.append("ADDRESS: ");
                                break;
                            default: result = result.append("PHONE: ");
                                break;
                        }
                        result = result.append(res.getString(i) + "\n");
                    }
                    found = true;
                    Log.d("MyContactApp","Original String: " + editName.getText().toString());
                    //.d("MyContactApp","String has been found at " + id + " ,it is: " + res.getString(1));
                    result = result.append("\n");

                } else {
                    Log.d("MyContactApp", "Not found");
                    Log.d("MyContactApp","Original String: " + editName.getText().toString());
                    //Log.d("MyContactApp","String has been found at " + id + " ,it is: " + res.getString(1));
                }
            }
            catch(Exception e){
                Log.d("MyContactApp","Error searching");
            }
        }
        Log.d("MyContactApp","String search complete");
        //res.moveToPosition(id);
        //Log.d("MyContactApp","The string at " + id + " is: " + result);

        Intent intent = new Intent(this, SearchActivity.class);
        Log.d("MyContactApp","intent instantiated");
        //Log.d("MyContactApp","the found string is: " + res.getString(id));
        if(found){
            intent.putExtra(EXTRA_MESSAGE_NAME, result.toString());
        } else {
            intent.putExtra(EXTRA_MESSAGE_NAME, "No result found.");
        }
        /*intent.putExtra(EXTRA_MESSAGE_NAME, res.getString(1));
        intent.putExtra(EXTRA_MESSAGE_ADDRESS, res.getString(2));
        intent.putExtra(EXTRA_MESSAGE_PHONE, res.getString(3));*/
        Log.d("MyContactApp","MainACtivity: showMessage: intent putExtra complete");

        startActivity(intent);
    }

}
