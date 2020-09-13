package com.example.patientquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AsyncResponse{
    ArrayList<String> topicNames = new ArrayList<>();
    ArrayList<String> topicIDs = new ArrayList<>();
    DataGetter dataGetter = new DataGetter();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        dataGetter.delegate = this;
        dataGetter.execute("https://patient.ang3r.pl/topics.json");
    }

    @Override
    public void processFinish(String response) {
        Log.d("JSON", response);
        try {
            JSONObject fromResponse = new JSONObject(response);
            JSONArray topicArray = fromResponse.getJSONArray("topics");
            Log.d("topics", topicArray.getString(0));
            for (int i = 0; i < topicArray.length(); i++) {
                JSONObject topicDetails = topicArray.getJSONObject(i);
                topicNames.add(topicDetails.getString("description"));
                topicIDs.add(topicDetails.getString("id"));
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        CustomAdapter customAdapter = new CustomAdapter(this, topicNames, topicIDs);
        recyclerView.setAdapter(customAdapter);
    }
}
