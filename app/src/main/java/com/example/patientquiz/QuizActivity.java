package com.example.patientquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QuizActivity extends AppCompatActivity {
    TextView tvQuestion;
    JSONArray questions;
    JSONArray answers;
    JSONObject question;
    AnswerButton[] answerButtons = new AnswerButton[4];
    int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        setTitle(getIntent().getStringExtra("TITLE") + " - quiz");
        tvQuestion = findViewById(R.id.tvQuestion);
        try {
            questions = new JSONArray(getIntent().getStringExtra("QUESTIONS"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("MyLog", e.toString());
        }
        initializeQuestion();
        Button btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCorrect = true;
                for(int i = 0; i < 4; i++){
                    if(!answerButtons[i].checkAnswer()){
                        isCorrect = false;
                    }
                    if (isCorrect) {
                        Toast.makeText(getApplicationContext(), "Odpowiedź prawidłowa", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Odpowiedź nieprawidłowa", Toast.LENGTH_LONG).show();
                    }
                }
                index++;
                initializeQuestion();
            }
        });

    }

    private void initializeQuestion(){
        try {
            question = questions.getJSONObject(index);
            answers = question.getJSONArray("answers");
            tvQuestion.setText(question.getString("question"));
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("MyLog", e.toString());
        }
        ToggleButton[] buttons = new ToggleButton[4];
        buttons[0] = findViewById(R.id.answerButton1);
        buttons[1] = findViewById(R.id.answerButton2);
        buttons[2] = findViewById(R.id.answerButton3);
        buttons[3] = findViewById(R.id.answerButton4);
        for(int i = 0; i < 4; i++){
            buttons[i].setChecked(false);
            JSONObject answer;
            try {
                answer = answers.getJSONObject(i);
                answerButtons[i] = new AnswerButton(buttons[i], answer);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
