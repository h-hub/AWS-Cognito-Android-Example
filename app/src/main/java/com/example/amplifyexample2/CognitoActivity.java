package com.example.amplifyexample2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class CognitoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cognito);
    }

    public void goback(View view) {
        Intent dsp = new Intent(CognitoActivity.this, MainActivity.class);
        startActivity(dsp);
    }
}
