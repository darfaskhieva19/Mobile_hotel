package com.example.mobile_hotel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    View v;
    Connection connection;
    String ConnectionResult = "";
    List<Hotel> data;
    ListView listView;
    Adapter pAdapter;

    EditText search;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        v = findViewById(com.google.android.material.R.id.ghost_view);
        GetTextFromSQL(v);

        Sortirovka();
        //String[] it = {"<по умолчанию>", "Страна", "Кол-во звезд отеля"};
    }

    public  void  Sortirovka() //сортировка
    {
        try {
            spinner = findViewById(R.id.Spin);
            List<String> list = new ArrayList<String>();
            list.add("Без фильтра");
            list.add("Сортировка по странам");
            list.add("Сортировка по возрастанию кол-во звезд");
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            String st = null;
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    switch (position) {
                        case 0: {
                            sortByN(st);

                        }
                        break;
                        case 1: {
                            sortByCountry(st);

                        }
                        break;
                        case 2: {
                            sortByNumberOfStars(st);
                        }
                        break;
                    }
                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то не так", Toast.LENGTH_LONG).show();
        }
    }

    public  void  sortByN(String st) //запрос на сортировку
    {
        try {
            st = "Select * From Hotel";
            selectSort(st);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то не так", Toast.LENGTH_LONG).show();
        }
    }
    public  void  sortByCountry(String st)//запрос на сортировку
    {
        try {
            st = "Select * From Hotel ORDER BY Country";
            selectSort(st);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то не так", Toast.LENGTH_LONG).show();
        }
    }

    public  void  sortByNumberOfStars(String st)//запрос на сортировку
    {
        try {
            st = "Select * From Hotel ORDER BY NumberOfStars";
            selectSort(st);
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то так", Toast.LENGTH_LONG).show();
        }
    }

    public  void  selectSort(String s)//вывод сортировки
    {
        data = new ArrayList<Hotel>();
        listView = findViewById(R.id.BD_Hotel);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null)
            {
                String query = "Select * From Hotel";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    Hotel tempMask = new Hotel
                            (resultSet.getInt("ID"),
                                    resultSet.getString("Country"),
                                    resultSet.getString("City"),
                                    resultSet.getString("Title"),
                                    Integer.parseInt(resultSet.getString("NumberOfStars")),
                                    resultSet.getString("Image")
                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        enterMobile();
    }

    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }

    public void GetTextFromSQL(View v) { //вывод БД
        data = new ArrayList<Hotel>();
        listView = findViewById(R.id.BD_Hotel);
        pAdapter = new Adapter(MainActivity.this, data);
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if (connection != null)
            {
                String query = "Select * From Hotel";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);

                while (resultSet.next())
                {
                    Hotel tempMask = new Hotel
                            (resultSet.getInt("ID"),
                                    resultSet.getString("Country"),
                                    resultSet.getString("City"),
                                    resultSet.getString("Title"),
                                    Integer.parseInt(resultSet.getString("NumberOfStars")),
                                    resultSet.getString("Image")
                            );
                    data.add(tempMask);
                    pAdapter.notifyDataSetInvalidated();
                }
                connection.close();
            }
        }
        catch (SQLException throwables)
        {
            throwables.printStackTrace();
        }
        enterMobile();
    }

    public void GoAdd(View view) { //переход на добавление новой записи
        Intent intent = new Intent(this, add_data.class);
        startActivity(intent);
    }

}