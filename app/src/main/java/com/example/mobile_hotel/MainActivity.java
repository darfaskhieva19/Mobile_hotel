package com.example.mobile_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GoAdd(View view) {
        Intent intent  = new Intent(this, add_data.class);
        startActivity(intent);
    }

    public void GetTextFromSql(View view) {

    }
}