package com.example.patientquiz;

import android.util.Log;
import android.view.View;
import android.widget.ToggleButton;

import org.json.JSONException;
import org.json.JSONObject;

class AnswerButton{
    private ToggleButton button;
    private String answerText;
    private boolean correct;

    AnswerButton(final ToggleButton button, JSONObject answer){
        this.button = button;
        try {
            answerText = answer.getString("answer");
            correct = answer.getBoolean("isCorrect");
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("MyLog", e.toString());
        }
        button.setText(answerText);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setText(answerText);
            }
        });
    }

    Boolean checkAnswer(){
        return button.isChecked() == correct;
    }
}
