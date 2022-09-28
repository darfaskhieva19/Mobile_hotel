package com.example.mobile_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class add_data extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);
    }

    public void delete(View view) { //Удаление записи
        try {
            /*Country.setText("");
            City.setText("");
            Title.setText("");
            NumberOfStars.setText("");*/
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }

    public void back(View view) { //Выход на главный экран
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void add(View view) { //Добавление записи

    }
}