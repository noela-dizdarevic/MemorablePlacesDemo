package com.noela.memorableplacesdemo;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class MainActivity extends AppCompatActivity {

    static ArrayList<String> places = new ArrayList<String>();
    static ArrayList<LatLng> locations = new ArrayList<>();
    static ArrayAdapter arrayAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.listTextView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.noela.memorableplacesdemo", Context.MODE_PRIVATE);

        ArrayList<String> latitude = new ArrayList<>();
        ArrayList<String> longtitude = new ArrayList<>();

        places.clear();
        latitude.clear();
        longtitude.clear();
        locations.clear();

        try {

            places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("places", ObjectSerializer.serialize(new ArrayList<String>())));


            latitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("latitude", ObjectSerializer.serialize(new ArrayList<String>())));


            longtitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("longtitude", ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (places.size() > 0 && latitude.size() > 0 && longtitude.size() > 0) {


            if (places.size() == latitude.size() && latitude.size() == longtitude.size()) {

                for (int i = 0; i < latitude.size(); i++) {

                    locations.add(new LatLng(Double.parseDouble(latitude.get(i)),Double.parseDouble(longtitude.get(i))));

                }

            }


        } else {

            places.add("Add a new places...");
            locations.add(new LatLng(0, 0));

        }
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,places);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {



                Intent intent = new Intent(getApplicationContext(),MapsActivity.class);

                intent.putExtra("placeNumber", i);

                startActivity(intent);





            }
        });
    }

}
