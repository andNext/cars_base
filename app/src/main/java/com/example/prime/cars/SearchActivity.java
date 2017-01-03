package com.example.prime.cars;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.prime.cars.MainActivity.idAuto;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnSearch, btnToMainS, btnToViewS;
    EditText etIdS,etMakeS,etModelS;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        btnSearch=(Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this);

        btnToMainS=(Button)findViewById(R.id.btnToMainS);
        btnToMainS.setOnClickListener(this);

        btnToViewS=(Button)findViewById(R.id.btnToViewS);
        btnToViewS.setOnClickListener(this);

        etIdS =(EditText) findViewById(R.id.etIdS);
        etMakeS =(EditText) findViewById(R.id.etMakeS);
        etModelS=(EditText) findViewById(R.id.etModelS);

        dbHelper = new DBHelper(this);

        btnToViewS.setVisibility(View.INVISIBLE);
        etIdS.setVisibility(View.INVISIBLE);
    }


    public void onClick(View v){
        switch (v.getId()) {

            case R.id.btnSearch:
                String make = etMakeS.getText().toString();
                String model = etModelS.getText().toString();

                if(make.equals("")){
                    Toast.makeText(SearchActivity.this, "Данные не введены!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    SQLiteDatabase database = dbHelper.getWritableDatabase();
                    Cursor cursor;
                    String selectQuery;
                    if(model.equals("")){
                        selectQuery = "SELECT * FROM cars WHERE make='"+make+"'";
                    }
                    else{
                        selectQuery = "SELECT * FROM cars WHERE make='"+make+"' AND model='"+model+"'";
                    }

                    cursor = database.rawQuery(selectQuery,null);
                    LinearLayout layoutSearch = (LinearLayout)findViewById(R.id.layout333);
                    if(cursor.moveToFirst()&&cursor.getCount()!=0){
                        do{
                            String id = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID));
                            String makes = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_MAKE));
                            String models = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_MODEL));
                            String licence = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_LICENCE));

                            TextView txt = new TextView(this);
                            txt.setText("#" + id + " " + makes.toUpperCase() + " " + licence+ " " + models.toUpperCase() + "\n");
                            txt.setTextColor(Color.rgb(255,0,0));
                            txt.setTextSize(16);
                            txt.setPadding(25, 0, 15, 0);
                            layoutSearch.addView(txt);
                        }while(cursor.moveToNext());
                        btnSearch.setVisibility(View.INVISIBLE);
                        btnToViewS.setVisibility(View.VISIBLE);
                        etIdS.setVisibility(View.VISIBLE);
                    }
                    else{
                        TextView txt = new TextView(this);
                        txt.setText("Не найдено подходящих автомобилей!");
                        txt.setTextColor(Color.rgb(71,0,39));
                        txt.setTextSize(16);
                        txt.setPadding(25, 0, 15, 0);
                        layoutSearch.addView(txt);
                        btnSearch.setVisibility(View.INVISIBLE);
                    }

                    cursor.close();

                }

                break;
            case R.id.btnToMainS:
                Intent intentToMainS = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intentToMainS);
                break;
            case R.id.btnToViewS:
                String Id = etIdS.getText().toString();
                if(Id.equalsIgnoreCase("")){
                    Toast.makeText(SearchActivity.this, "Введите номер машины!", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(Id)==0){

                    Toast.makeText(SearchActivity.this, "Нет нужного поля!", Toast.LENGTH_LONG).show();
                }
                else{
                    idAuto = Integer.parseInt(Id);

                    Intent intentView = new Intent(SearchActivity.this, ViewActivity.class);
                    startActivity(intentView );
                }

                break;
        }
    }
}
