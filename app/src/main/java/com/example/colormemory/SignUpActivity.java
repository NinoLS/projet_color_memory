package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import database.User;
import database.UserManager;

public class SignUpActivity extends AppCompatActivity {
    UserManager userManager = new UserManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    //TODO : Check if new user is not in DB before request
    public void register(View v){
        EditText nameInput = findViewById(R.id.login_input);
        String name = nameInput.getText().toString().trim();

        EditText passwordInput = findViewById(R.id.password_input);
        String password = passwordInput.getText().toString().trim();


        DatePicker birth = findViewById(R.id.birthDate_input);
        String birthParsed = birth.getDayOfMonth() + "/" + birth.getMonth() + "/" + birth.getYear();

        //Check if inputs are OK
        if(name.length() > 2 && password.length() > 4 && birthParsed.length() > 7){
            User user =new User(name, password, birthParsed);
            userManager.open();
            userManager.createUser(user);
            userManager.close();
        }else{
            Log.d("WRONG :", "INPUT WRONG");
        }
    }
}