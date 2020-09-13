package com.example.patientquiz;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;


public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> implements AsyncResponse {

    private ArrayList<String> topicNames;
    private ArrayList<String> topicIDs;
    private Context context;
    private AsyncResponse adapterContext;
    private DataGetter infoDataGetter;

    static final String EXTRA_MESSAGE = "com.example.patientquiz.MESSAGE";

    CustomAdapter(Context baseContext, ArrayList<String> TopicNames, ArrayList<String> TopicIDs) {
        context = baseContext;
        topicNames = TopicNames;
        topicIDs = TopicIDs;
        adapterContext = this;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rowlayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int pos) {
        final int position = holder.getAdapterPosition();
        holder.name.setText(topicNames.get(position));
        holder.ID.setText("ID: " + topicIDs.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                infoDataGetter = new DataGetter();
                infoDataGetter.delegate = adapterContext;
                infoDataGetter.execute(createURL(Integer.parseInt(topicIDs.get(position))));
            }
        });

    }

    @Override
    public int getItemCount() {
        return topicNames.size();
    }

    @Override
    public void processFinish(String response) {
        if(response != null){
            Intent intent = new Intent(context, InfoActivity.class);
            intent.putExtra(EXTRA_MESSAGE, response);
            Log.d("MyLog", response);
            context.startActivity(intent);
        } else{
            Toast.makeText(context, "Błąd wczytywania danych", Toast.LENGTH_LONG).show();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, ID;
        MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            ID = itemView.findViewById(R.id.email);
        }
    }

    private String createURL(int id){
        return "https://patient.ang3r.pl/topics/" + id + "/info.json";
    }
}
