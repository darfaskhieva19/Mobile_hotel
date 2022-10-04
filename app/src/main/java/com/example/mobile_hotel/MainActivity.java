package com.example.mobile_hotel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    View v;
    Connection connection;
    List<Hotel> data;
    ListView listView;
    Adapter pAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        v = findViewById(com.google.android.material.R.id.ghost_view);

        GetTextFromSQL(v);
    }
    public void enterMobile() {
        pAdapter.notifyDataSetInvalidated();
        listView.setAdapter(pAdapter);
    }
    public void GetTextFromSQL(View v) {
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

    public void GoAdd(View view) {
        Intent intent = new Intent(this, add_data.class);
        startActivity(intent);
    }

}