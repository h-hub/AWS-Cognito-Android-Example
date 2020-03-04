package com.example.amplifyexample2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;

import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    Button btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.signup);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());

                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );

        Toast toast3 = Toast.makeText(getApplicationContext(), "On Create", Toast.LENGTH_SHORT);
        toast3.show();

    }

    public void gotoNext(View view) {

        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText password = (EditText) findViewById(R.id.password);

        final Map<String, String> attributes = new HashMap<>();
        attributes.put("email", email.getText().toString());
        attributes.put("name", name.getText().toString());

        AWSMobileClient.getInstance().signUp(email.getText().toString(), password.getText().toString(), attributes, null, new Callback<SignUpResult>() {
            @Override
            public void onResult(final SignUpResult signUpResult) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Sign-up callback state: " + signUpResult.getConfirmationState());
                        if (!signUpResult.getConfirmationState()) {
                            final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();
                            Log.i("INIT", "Confirm sign-up with: " + details.getDestination());
                        } else {
                            Log.i("INIT", "Sign-up done. ");
                        }
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                Log.e(TAG, "Sign-up error", e);
            }
        });

        Intent dsp = new Intent(MainActivity.this, CognitoActivity.class);
        startActivity(dsp);


    }
}
