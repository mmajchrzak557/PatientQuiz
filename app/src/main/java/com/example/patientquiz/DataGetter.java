package com.example.patientquiz;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class DataGetter extends AsyncTask<String, Void, String> {
    AsyncResponse delegate = null;
    @Override
    protected String doInBackground(String... strings) {
        String urlString = strings[0];
        URLConnection con;
        BufferedReader bufferedReader = null;
        try{
            URL url = new URL(urlString);
            con = url.openConnection();
            con.connect();
            bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder stringBuffer = new StringBuilder();
            String line;
            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line);
            }
            Log.d("JSON", stringBuffer.toString());
            return stringBuffer.toString();
        } catch(Exception e){
            Log.d("JSON", e.toString());
            return null;
        } finally{
            if(bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void onPostExecute(String response) {
        delegate.processFinish(response);
    }
}
