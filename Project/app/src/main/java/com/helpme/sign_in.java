package com.helpme;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import Model.userDB;

public class sign_in extends AppCompatActivity {

    Button signIn, signUp;
    userDB dbUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Typeface face = Typeface.createFromAsset(getAssets(), "Fonts/Nabila.ttf");
        ((TextView) findViewById(R.id.Logo)).setTypeface(face);
        signUp = findViewById(R.id.signUpBtn);
        signIn = findViewById(R.id.signInBtn);
        dbUser = new userDB(this);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbUser.getUserByUserNameAndChecksPassword(
                        ((AutoCompleteTextView) findViewById(R.id.userName)).getText().toString(),
                        ((AutoCompleteTextView) findViewById(R.id.password)).getText().toString()
                );
            }
        });
    }
}
