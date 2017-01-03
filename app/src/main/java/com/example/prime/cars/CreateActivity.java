package com.example.prime.cars;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnToMain;
    EditText etMake, etModel, etLicence, etDate, etColor, etEngine;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnToMain=(Button)findViewById(R.id.btnToMain);
        btnToMain.setOnClickListener(this);

        etMake =(EditText) findViewById(R.id.etMake);
        etModel=(EditText) findViewById(R.id.etModel);
        etLicence =(EditText) findViewById(R.id.etLicence);
        etDate=(EditText) findViewById(R.id.etDate);
        etColor =(EditText) findViewById(R.id.etColor);
        etEngine=(EditText) findViewById(R.id.etEngine);

        dbHelper = new DBHelper(this);
    }

    public void onClick(View v){

        switch (v.getId()){
            case R.id.btnAdd:
                SQLiteDatabase database = dbHelper.getWritableDatabase();

                ContentValues contentValues = new ContentValues();
                String make = etMake.getText().toString();
                String model = etModel.getText().toString();
                String licence = etLicence.getText().toString();
                String date = etDate.getText().toString();
                String color = etColor.getText().toString();
                String engine = etEngine.getText().toString();

                if(make.equals("")||licence.equals("")){
                    Toast.makeText(CreateActivity.this, "Данные не введены!", Toast.LENGTH_LONG).show();
                }
                else {
                    contentValues.put(DBHelper.KEY_MAKE, make);
                    contentValues.put(DBHelper.KEY_MODEL, model);
                    contentValues.put(DBHelper.KEY_LICENCE, licence);
                    contentValues.put(DBHelper.KEY_DATE, date);
                    contentValues.put(DBHelper.KEY_COLOR, color);
                    contentValues.put(DBHelper.KEY_ENGINE, engine);

                    database.insert(DBHelper.TABLE_CARS, null, contentValues);

                    Toast.makeText(CreateActivity.this, "Сохранено!", Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.btnToMain:
                Intent intentMain = new Intent(CreateActivity.this, MainActivity.class);
                startActivity(intentMain);
                break;
        }
    }
}
