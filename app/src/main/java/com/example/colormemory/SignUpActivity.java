package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import database.User;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void register(View v){
        String name = findViewById(R.id.login_input).toString().trim();
        String password = findViewById(R.id.password_input).toString().trim();
        DatePicker birth = findViewById(R.id.birthDate_input);
        String birthParsed = birth.getDayOfMonth() + "/" + birth.getMonth() + "/" + birth.getYear();

        if(name.length() < 2 ||password.length() < 4 || birthParsed.length() < 0){
            User user =new User(name, password, birthParsed);
        }else{
            Log.d("WRONG", "EMPTY");
        }

    }
}