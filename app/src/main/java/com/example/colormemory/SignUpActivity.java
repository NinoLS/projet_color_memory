package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import database.User;
import database.UserManager;

public class SignUpActivity extends AppCompatActivity {
    UserManager userManager = new UserManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    //TODO : Fixer le problème de cursor null
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
            //Try to get a user, with the name inserted. If not, return null user
            userManager.open();
            User checkUser = new User("null", "null", "null");
            //readUser in a try/catch to avoid application crash because of null cursor
            try{
                checkUser  = userManager.readUser(name);
            }catch (Exception e){
                Log.d("ERROR : ", e.getMessage());
            }
            userManager.close();

            if(checkUser.getName().equals("null")){
                userManager.open();
                if(userManager.createUser(user) == -1){
                    Log.d("WRONG : ", "CAN'T CREATE USER");
                }
                userManager.close();
                Log.d("INFO :", "USER CREATED : " + name);

            }else {
                Log.d("WRONG :", "USERNAME ALREADY USED : " + name);
            }
        }else{
            Log.d("WRONG :", "INPUT WRONG");
        }
    }
}