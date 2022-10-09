package com.example.mobile_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
    }

    public  void  setupSort()
    {
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this,R.array.spinIt, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner=findViewById(R.id.spinner);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(position == 0)
                {
                    sortByCountry();
                }
                else
                {
                    sortByNumberOfStars();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    public  void  sortByCountry()
    {
        String st = null;
        st = "Select * From Hotel ORDER BY Country";
        Sortirovka(st);
    }

    public  void  sortByNumberOfStars()
    {
        String st = null;
        st = "Select * From Hotel ORDER BY NumberOfStars";
        Sortirovka(st);
    }
    public  void  Sortirovka(String st)
    {
        data = new ArrayList<Hotel>();
        listView = findViewById(R.id.BD_Hotel);
        pAdapter = new Adapter(MainActivity.this, data);
        try
        {
            ConnectionHelper connectionHelpers = new ConnectionHelper();
            connection = connectionHelpers.connectionClass();
            if (connection !=null)
            {
                String query = st;
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
            else
            {
                ConnectionResult="Check Connection";
            }
        }
        catch (Exception ex)
        {
            Toast.makeText(MainActivity.this,"Что-то не так", Toast.LENGTH_LONG).show();
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