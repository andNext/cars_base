package com.example.prime.cars;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.prime.cars.MainActivity.idAuto;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnUpd, btnDel, btnToMainUpd;
    EditText etMakeUpd, etModelUpd, etLicenceUpd, etDateUpd, etColorUpd, etEngineUpd;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        etMakeUpd =(EditText) findViewById(R.id.etMakeUpd);
        etModelUpd=(EditText) findViewById(R.id.etModelUpd);
        etLicenceUpd =(EditText) findViewById(R.id.etLicenceUpd);
        etDateUpd=(EditText) findViewById(R.id.etDateUpd);
        etColorUpd =(EditText) findViewById(R.id.etColorUpd);
        etEngineUpd=(EditText) findViewById(R.id.etEngineUpd);

        btnUpd=(Button)findViewById(R.id.btnUpd);
        btnUpd.setOnClickListener(this);

        btnToMainUpd=(Button)findViewById(R.id.btnToMainUpd);
        btnToMainUpd.setOnClickListener(this);

        btnDel=(Button)findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);

        dbHelper = new DBHelper(this);

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor;

        String selectQuery = "SELECT * FROM cars WHERE _id = " + idAuto;
        cursor = database.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()&&cursor.getCount()!=0){
            int makeIndex = cursor.getColumnIndex(DBHelper.KEY_MAKE);
            int modelIndex = cursor.getColumnIndex(DBHelper.KEY_MODEL);
            int licenceIndex = cursor.getColumnIndex(DBHelper.KEY_LICENCE);
            int dateIndex = cursor.getColumnIndex(DBHelper.KEY_DATE);
            int colorIndex = cursor.getColumnIndex(DBHelper.KEY_COLOR);
            int engineIndex = cursor.getColumnIndex(DBHelper.KEY_ENGINE);

            do{
                etMakeUpd.setText(cursor.getString(makeIndex));
                etModelUpd.setText(cursor.getString(modelIndex));
                etLicenceUpd.setText(cursor.getString(licenceIndex));
                etDateUpd.setText(cursor.getString(dateIndex));
                etColorUpd.setText(cursor.getString(colorIndex));
                etEngineUpd.setText(cursor.getString(engineIndex));
            }while(cursor.moveToNext());
        }
        else{
            Toast.makeText(ViewActivity.this, "Данные введены неправильно!", Toast.LENGTH_LONG).show();

            TextView txt = (TextView)findViewById(R.id.tv);
            txt.setText("Проверте правильность номера автомобиля!");

            btnDel.setVisibility(View.INVISIBLE);
            btnUpd.setVisibility(View.INVISIBLE);
            etMakeUpd.setVisibility(View.INVISIBLE);
            etModelUpd.setVisibility(View.INVISIBLE);
            etLicenceUpd.setVisibility(View.INVISIBLE);
            etDateUpd.setVisibility(View.INVISIBLE);
            etColorUpd.setVisibility(View.INVISIBLE);
            etEngineUpd.setVisibility(View.INVISIBLE);

        }

            cursor.close();

    }

    private void backToMain(){
        Intent intentToMainUpd = new Intent(ViewActivity.this, MainActivity.class);
        startActivity(intentToMainUpd);
    }

    public void onClick(View v){
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (v.getId()){
            case R.id.btnUpd:
                String make = etMakeUpd.getText().toString();
                String licence = etLicenceUpd.getText().toString();
                String model = etModelUpd.getText().toString();
                String date = etDateUpd.getText().toString();
                String color = etColorUpd.getText().toString();
                String engine = etEngineUpd.getText().toString();

                if(make.equals("")||licence.equals("")){
                    Toast.makeText(ViewActivity.this, "Данные не введены!", Toast.LENGTH_LONG).show();
                }
                else{
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DBHelper.KEY_MAKE, make);
                    contentValues.put(DBHelper.KEY_MODEL, model);
                    contentValues.put(DBHelper.KEY_LICENCE, licence);
                    contentValues.put(DBHelper.KEY_DATE, date);
                    contentValues.put(DBHelper.KEY_COLOR, color);
                    contentValues.put(DBHelper.KEY_ENGINE, engine);

                    database.update(DBHelper.TABLE_CARS, contentValues, DBHelper.KEY_ID + "= ?", new String[] {Integer.toString(idAuto)});
                }
                backToMain();
                break;
            case R.id.btnDel:
                database.delete(DBHelper.TABLE_CARS,DBHelper.KEY_ID + "=" + idAuto, null);
                backToMain();
                break;
            case R.id.btnToMainUpd:
                backToMain();
                break;
        }
    }

}
