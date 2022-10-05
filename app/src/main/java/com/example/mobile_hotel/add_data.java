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



public class add_data extends AppCompatActivity {

    Connection connection;
    String Image;
    private ImageView imageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_data);

        String image = "";
        imageButton = findViewById(R.id.Photo);
    }

    public void delete(View view) { //Очистить
        try {
           /* Country.setText("");
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
                    query = "INSERT INTO Hotel (Country, City, Title, NumberOfStars, Image) VALUES ('" + Country.getText() + "', '" + City.getText() + "', '"+ Image +"', '" + Title.getText() + "', '"+NumberOfStars.getText()+")";
                }

                Statement statement = connection.createStatement();
                Toast.makeText(this, "Успешное добавление!", Toast.LENGTH_LONG).show();
                statement.executeUpdate(query);
            }
        }
        catch (Exception ex)
        {
            Log.e("Error", ex.getMessage());
        }

    }

    public void ChoosePhoto(View view) {

    }
    /*private void gImg()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(intent,1);
    }*/
}