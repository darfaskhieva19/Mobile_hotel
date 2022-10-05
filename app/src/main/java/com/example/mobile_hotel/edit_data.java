package com.example.mobile_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import android.widget.Toast;

public class edit_data extends AppCompatActivity {

    Connection connection;
    String Image;
    /*private ImageView imageButton;*/
    EditText Country, City, Title, NumberOfStars;
    View v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_data);

        /*Hotel = getIntent().getParcelableExtra("Hotel");

        imageView=findViewById(R.id.Image);

        Country= findViewById(R.id.Country);
        Country.setText(Hotel.getCountry());

        City=findViewById(R.id.City);
        City.setText(Hotel.getCity());

        Title=findViewById(R.id.Title);
        Title.setText(Hotel.getTitle());

        NumberOfStars=findViewById(R.id.NumberOfStars);
        NumberOfStars.setText(Hotel.getNumberOfStars());*/
    }

    public void GoBack(View view) { //Выход на главный экран
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public  void GoEdit(View view){ //Редактирование данных

    }
    public void GoDelete (View view){//Удаление данных

    }
}
