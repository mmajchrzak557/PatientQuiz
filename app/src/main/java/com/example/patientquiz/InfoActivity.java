package com.example.patientquiz;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class InfoActivity extends AppCompatActivity{
    private String content;
    private String title;
    private JSONArray questions;
    private Context context;
    private int fontSize = 12;
    WebView myWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String message = getIntent().getStringExtra(CustomAdapter.EXTRA_MESSAGE);
        Log.d("MyLog", message);
        try {
            JSONObject obj  = new JSONObject(message);
            JSONObject temp = new JSONObject(obj.getString("topic"));
            questions = temp.getJSONArray("questions");
            content   = temp.getString("content");
            title     = temp.getString("title");
        } catch (JSONException e) {
            Log.d("MyLog", e.toString());
        }
        setTitle(title);
        setContentView(R.layout.activity_info);

        myWebView = findViewById(R.id.webview);
        setWebSettings(myWebView.getSettings());
        myWebView.loadData(content, "text/html", "utf-8");
        Button btnQuiz = findViewById(R.id.button);
        context = this;
        btnQuiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, QuizActivity.class);
                i.putExtra("QUESTIONS", questions.toString());
                i.putExtra("TITLE", title);
                context.startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.font_size_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.font_size_change:
                showDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setWebSettings (WebSettings settings){
        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        settings.setAppCacheEnabled(false);
        settings.setBlockNetworkImage(true);
        settings.setLoadsImagesAutomatically(true);
        settings.setGeolocationEnabled(false);
        settings.setNeedInitialFocus(false);
        settings.setTextZoom(map(fontSize, 12, 20, 100, 200));
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View layout = inflater.inflate(R.layout.font_size_dialog, null);
        builder.setView(layout)
                .setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                })
                .setTitle("Wybierz rozmiar czcionki");
        AlertDialog dialog = builder.create();
        final TextView tvFontSize = layout.findViewById(R.id.tvFontSize);
        tvFontSize.setText(String.valueOf(fontSize));
        SeekBar sbFontSize = layout.findViewById(R.id.sbFontSize);
        sbFontSize.setProgress(fontSize - 12);
        sbFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    tvFontSize.setText(String.valueOf(progress + 12));
                    fontSize = progress + 12;
                    myWebView.getSettings().setTextZoom(map(fontSize, 12, 20, 100, 200));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
//        Dialog dialog = new Dialog(this);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.font_size_dialog, null);
//        dialog.setContentView(layout);
//        final TextView tvFontSize = layout.findViewById(R.id.tvFontSize);
//        tvFontSize.setText("dupa");
//        SeekBar sbFontSize = layout.findViewById(R.id.sbFontSize);
//        sbFontSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                tvFontSize.setText(String.valueOf(progress + 12));
//                fontSize = progress + 12;
//                setWebSettings(myWebView.getSettings());
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });
        dialog.show();
    }
    private int map(int value, float min1, float max1, float min2, float max2){
        return (int) (((value - min1)/(max1 - min1))*(max2 - min2) + min2);
    }
}
