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


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnToAdd, btnToSearch, btnToView;
    EditText etId;
    DBHelper dbHelper;
    public static int idAuto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout layoutt = (LinearLayout) findViewById(R.id.layout);

        btnToAdd=(Button)findViewById(R.id.btnToAdd);
        btnToAdd.setOnClickListener(this);

        btnToSearch=(Button)findViewById(R.id.btnToSearch);
        btnToSearch.setOnClickListener(this);

        btnToView=(Button)findViewById(R.id.btnToView);
        btnToView.setOnClickListener(this);

        etId =(EditText) findViewById(R.id.etId);

        dbHelper = new DBHelper(this);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DBHelper.TABLE_CARS, null, null, null, null, null, null);

        if(cursor.moveToFirst()&&cursor.getCount()!=0){

                do{
                    String id = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_ID));
                    String make = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_MAKE));
                    String model = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_MODEL));
                    String licence = cursor.getString(cursor.getColumnIndex(DBHelper.KEY_LICENCE));

                    TextView txt = new TextView(this);
                    txt.setText("#" + id + " " + make.toUpperCase() + " " + licence+ " " + model.toUpperCase() + "\n");
                    //txt.setTextColor(Color.rgb(65,61,81));
                    txt.setTextColor(Color.rgb(0,0,255));
                    txt.setTextSize(16);
                    txt.setPadding(25, 0, 15, 0);
                    layoutt.addView(txt);
                }while(cursor.moveToNext());

        }
        else{
            TextView txt = new TextView(this);
            txt.setText("Добавьте автомобили!");
            txt.setTextColor(Color.rgb(71,0,39));
            txt.setTextSize(16);
            txt.setPadding(25, 0, 15, 0);
            layoutt.addView(txt);
        }
        cursor.close();
    }


    public void onClick(View v){
        switch (v.getId()) {

            case R.id.btnToAdd:
                Intent intentCreate = new Intent(MainActivity.this, CreateActivity.class);
                startActivity(intentCreate);
                break;
            case R.id.btnToSearch:
                Intent intentSearch = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intentSearch);
                break;
            case R.id.btnToView:
                String Id = etId.getText().toString();
                if(Id.equalsIgnoreCase("")){
                    Toast.makeText(MainActivity.this, "Введите номер машины!", Toast.LENGTH_LONG).show();
                }
                else if(Integer.parseInt(Id)==0){

                    Toast.makeText(MainActivity.this, "Нет нужного поля!", Toast.LENGTH_LONG).show();
                }
                else{
                    idAuto = Integer.parseInt(Id);

                    Intent intentView = new Intent(MainActivity.this, ViewActivity.class);
                    startActivity(intentView );
                }

                break;
        }
    }
}