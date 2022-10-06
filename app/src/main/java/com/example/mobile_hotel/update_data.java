package com.example.mobile_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Base64;

public class update_data extends AppCompatActivity {
    EditText Kod, Country, City, Title, NumberOfStars;
    ImageView Photo;
    Connection connection;
    String ConnectionResult="";
    Hotel hotel;
    View v;
    String Img;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);

        hotel=getIntent().getParcelableExtra("Hotel");
        Country = (EditText) findViewById(R.id.Ed_Country);
        Country.setText(hotel.getCountry());
        City = (EditText) findViewById(R.id.Ed_City);
        City.setText(hotel.getCity());
        Title = (EditText) findViewById(R.id.Ed_Title);
        Title.setText(hotel.getCity());
        NumberOfStars = (EditText) findViewById(R.id.Ed_NumberOfStars);
        NumberOfStars.setText(hotel.getNumberOfStars());

        Photo=findViewById(R.id.Photo);
        v =findViewById(com.google.android.material.R.id.ghost_view);
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if (encodedImg != null) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(update_data.this.getResources(),
                R.drawable.ic_launcher_foreground);
    }

    public void onClickImage(View view)
    {
        Intent intentChooser= new Intent();
        intentChooser.setType("image/*");
        intentChooser.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intentChooser,1);
    }

    public String encodeImg(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Img= Base64.getEncoder().encodeToString(b);
            return Img;
        }
        return "";
    }

    public void deleteImg(View v)
    {
        ImageView Picture = (ImageView) findViewById(R.id.Photo);
        Picture.setImageBitmap(null);
        Picture.setImageResource(R.drawable.photo);
    }

    public void GoDelete(View view) { //Удаление
        try {
            ConnectionHelper connectionHelpers = new ConnectionHelper();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = "Delete From Hotel where ID = '"+hotel.getID()+"' ";
                Statement statement = connection.createStatement();
                statement.executeUpdate(query);
                Kod.setText("");
                Country.setText("");
                City.setText("");
                Title.setText("");
                NumberOfStars.setText("");
                Photo.setImageResource(R.drawable.photo);
                Toast.makeText(this, "Успешное удаление данных!", Toast.LENGTH_LONG).show();
            }
            else
            {
                ConnectionResult="Check Connection";
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }

    public void GoBack(View view) { //Возвращение на главную страницу
        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void GoEdit(View view) { //Изменение данных
        if(Country.getText().length() == 0 || City.getText().length() == 0 || Title.getText().length() == 0|| NumberOfStars.getText().length() == 0)
        {
            Toast.makeText(this, "Заполните поля!", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            try {
                ConnectionHelper connectionHelpers = new ConnectionHelper();
                connection = connectionHelpers.connectionClass();
                if (connection != null) {
                    String query = "UPDATE Hotel SET Country = '" + Country.getText().toString() + "' , City = '" + City.getText().toString() + "', Title = '" + Title.getText().toString() + "', NumberOfStars = '" + NumberOfStars.getText().toString() + "' , Image = '" + Img + "' where ID = '" + hotel.getID() + "'";
                    Statement statement = connection.createStatement();
                    Toast.makeText(this, "Успешное изменение данных!", Toast.LENGTH_LONG).show();
                    statement.executeUpdate(query);
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
        }
    }
}