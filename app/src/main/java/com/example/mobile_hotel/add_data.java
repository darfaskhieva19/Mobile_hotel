package com.example.mobile_hotel;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Base64;


public class add_data extends AppCompatActivity {

    EditText Country, City, Title, NumberOfStars;
    ImageView Image;
    Connection connection;
    String ConnectionResult = "";
    String Img="";
    Hotel hotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        Country = (EditText) findViewById(R.id.Country);
        City = (EditText) findViewById(R.id.City);
        Title = (EditText) findViewById(R.id.Title);
        NumberOfStars = (EditText) findViewById(R.id.NumberOfStars);
        Image = (ImageView) findViewById(R.id.Img);

        /*Bundle obj = getIntent().getExtras();
        if(obj!=null){
            hotel = obj.getParcelable(Hotel.class.getSimpleName());
            Country.setText(hotel.getCountry());
            City.setText(hotel.getCity());
            Title.setText(hotel.getTitle());
            NumberOfStars.setText(Integer.toString(hotel.getNumberOfStars()));
        }*/
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
        byte[] b = byteArrayOutputStream.toByteArray();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Img= Base64.getEncoder().encodeToString(b);
            return Img;
        }
        return "";
    }

    public void add(View view) { //Добавление записи
        EditText Country = findViewById(R.id.Country);
        EditText City = findViewById(R.id.City);
        EditText Title = findViewById(R.id.Title);
        EditText NumberOfStars = findViewById(R.id.NumberOfStars);

        try {
            String query="";
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection !=null)
            {
                if(Image==null)
                {
                    query = "INSERT INTO Hotel (Country, City, Title, NumberOfStars) VALUES ('"+Country.getText()+"', '"+City.getText()+"', '"+Title.getText()+"', '"+NumberOfStars.getText()+"')";
                }
                else{
                    query = "INSERT INTO Hotel (Country, City, Title, NumberOfStars, Image) VALUES ('" + Country.getText() + "', '" + City.getText() + "', '" + Title.getText() + "', '"+NumberOfStars.getText()+"' '"+ Image +"')";
                }

                Statement statement = connection.createStatement();
                Toast.makeText(this, "Успешное добавление записи!", Toast.LENGTH_LONG).show();
                statement.executeUpdate(query);
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
            Image.setImageResource(R.drawable.photo);
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }
    }

    public void ChoosePhoto(View view) {
        getImg();
    }
}