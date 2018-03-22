package com.helpme;

import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Model.user;
import Model.userDB;

public class sign_up extends AppCompatActivity {
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Typeface face = Typeface.createFromAsset(getAssets(), "Fonts/Nabila.ttf");
        ((TextView) findViewById(R.id.Logo)).setTypeface(face);
        registerButton = findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user newUser = new user(
                        ((AutoCompleteTextView) findViewById(R.id.eMail)).getText().toString(),
                        ((AutoCompleteTextView) findViewById(R.id.firstName)).getText().toString(),
                        ((AutoCompleteTextView) findViewById(R.id.midName)).getText().toString(),
                        ((AutoCompleteTextView) findViewById(R.id.lastName)).getText().toString(),
                        "M",
                        "",
                        ((AutoCompleteTextView) findViewById(R.id.password)).getText().toString(),
                        ((AutoCompleteTextView) findViewById(R.id.phoneNumber)).getText().toString(),
                        ((AutoCompleteTextView) findViewById(R.id.userName)).getText().toString(),
                        10
                );
                userDB dbUser = new userDB(sign_up.this);
                dbUser.addUser(newUser);
            }
        });
    }
}
