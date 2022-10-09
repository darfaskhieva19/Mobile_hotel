package com.example.mobile_hotel;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
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

    Connection connection;
    View v;
    EditText Country, City, Title, NumberOfStars;
    ImageView Photo;
    Hotel hotel;
    String Img=null;
    String ConnectionResult="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_data);

        Country = findViewById(R.id.Ed_Country);
        City = findViewById(R.id.Ed_City);
        Title = findViewById(R.id.Ed_Title);
        NumberOfStars = findViewById(R.id.Ed_NumberOfStars);
        Photo = findViewById(R.id.UpPhoto);
        hotel = getIntent().getParcelableExtra("Hotel");
        Country.setText(hotel.getCountry());
        City.setText(hotel.getCity());
        Title.setText(hotel.getTitle());
        NumberOfStars.setText(Integer.toString(hotel.getNumberOfStars()));
        Photo.setImageBitmap(getImgBitmap(hotel.getImage()));
        v = findViewById(com.google.android.material.R.id.ghost_view);
    }

    protected void onActivityResult(int request, int result, @Nullable Intent data) {
        try {
            super.onActivityResult(request, result, data);
            if (request == 1 && data != null && data.getData() != null) {
                if (result == RESULT_OK) {
                    Log.d("MyLog", "Image URI : " + data.getData());

                    Photo.setImageURI(data.getData());
                    Bitmap bitmap = ((BitmapDrawable) Photo.getDrawable()).getBitmap();
                    toString(bitmap);
                }
            }
        } catch (Exception ex) {
            Toast.makeText(this, "Ошибка", Toast.LENGTH_LONG).show();
        }
    }

    public String toString(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Img = Base64.getEncoder().encodeToString(b);
            return Img;
        }
        return "";
    }

    private Bitmap getImgBitmap(String encodedImg) {
        if(encodedImg!=null&& !encodedImg.equals("null")) {
            byte[] bytes = new byte[0];
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                bytes = Base64.getDecoder().decode(encodedImg);
            }
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return BitmapFactory.decodeResource(update_data.this.getResources(),R.drawable.photo);
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
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                } else {
                    ConnectionResult = "Check Connection";
                }
            } catch (Exception ex) {
                Log.e("Error", ex.getMessage());
            }
        }
    }

    public void GoDelete(View view) { //Удаление
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null)
            {
                String str = "DELETE FROM Hotel WHERE ID = "+hotel.getID()+"";
                Statement statement = connection.createStatement();
                Toast.makeText(this, "Успешное удаление записи!", Toast.LENGTH_LONG).show();
                statement.executeUpdate(str);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }

    public void OnClickImg(View view) {
        try {
            Intent intentChooser = new Intent();
            intentChooser.setType("image/*");
            intentChooser.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentChooser, 1);
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"Ошибка", Toast.LENGTH_LONG).show();
        }
    }
}