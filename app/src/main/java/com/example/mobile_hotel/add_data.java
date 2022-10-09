package com.example.mobile_hotel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;
import java.util.List;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class add_data extends AppCompatActivity {

    EditText Country, City, Title, NumberOfStars;
    ImageView Photo;
    Connection connection;
    String ConnectionResult = "";
    String Img = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        Country = (EditText) findViewById(R.id.Country);
        City = (EditText) findViewById(R.id.City);
        Title = (EditText) findViewById(R.id.Title);
        NumberOfStars = (EditText) findViewById(R.id.NumberOfStars);
        Photo = (ImageView) findViewById(R.id.Img);
    }

    @Override
    protected void onActivityResult(int request, int result, @Nullable Intent data) {
    try {
        super.onActivityResult(request, result, data);
        if (request == 1 && data != null && data.getData() != null) {
            if (result == RESULT_OK) {
                Log.d("MyLog", "Image URI : " + data.getData());
                Photo.setImageURI(data.getData());
                Bitmap bitmap = ((BitmapDrawable) Photo.getDrawable()).getBitmap();
                encodeImg(bitmap);
            }
        }
    }
        catch (Exception ex) {
        Toast.makeText(add_data.this,"Что-то не так, ошибка", Toast.LENGTH_LONG).show();
    }
}
    private void getImg()
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    public String encodeImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(bytes);
        }
        return "";
    }

    public void add(View view) { //Добавление записи
        EditText Country = findViewById(R.id.Country);
        EditText City = findViewById(R.id.City);
        EditText Title = findViewById(R.id.Title);
        EditText NumberOfStars = findViewById(R.id.NumberOfStars);

        try {
                ConnectionHelper connectionHelpers = new ConnectionHelper();
                connection = connectionHelpers.connectionClass();
                if (connection != null) {
                    String query = "INSERT INTO Hotel (Country, City, Title, NumberOfStars, Image) VALUES ('" + Country.getText() + "', '" + City.getText() + "', '" + Title.getText() + "', '" + NumberOfStars.getText() + "' , '" + Img + "')";
                    Statement statement = connection.createStatement();
                    statement.executeUpdate(query);
                    Toast.makeText(this, "Успешное добавление записи!", Toast.LENGTH_LONG).show();
                }
                else {
                    ConnectionResult = "Check Connection";
                }
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

    public void delete(View view) { //Очистить поля
        try {
            Country.setText("");
            City.setText("");
            Title.setText("");
            NumberOfStars.setText("");
            Photo.setImageResource(R.drawable.photo);
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }

    public void ChoosePhoto(View view)
    {
        getImg();
    }
}