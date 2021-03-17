package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.sql.Date;
import java.util.Objects;

import database.Score;
import database.ScoreManager;
import database.User;
import database.UserManager;

public class SignUpActivity extends AppCompatActivity {
    UserManager userManager = new UserManager(this);
    ScoreManager scoreManager = new ScoreManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void register(View v){
        EditText nameInput = findViewById(R.id.login_input);
        String name = nameInput.getText().toString().trim();

        EditText passwordInput = findViewById(R.id.password_input);
        String password = passwordInput.getText().toString().trim();

        EditText emailInput = findViewById(R.id.email_input);
        String email = emailInput.getText().toString().trim();

        RadioGroup genderChoiceGroup = findViewById(R.id.genderChoice_group);
        RadioButton genderChoiceButton = findViewById(genderChoiceGroup.getCheckedRadioButtonId());
        String genderChoice = genderChoiceButton.getText().toString();
        byte genderNumber;
        if(genderChoice.equals("Men")){
            genderNumber = 1;
        }else{
            genderNumber = 0;
        }


        DatePicker birth = findViewById(R.id.birthDate_input);
        String birthParsed = birth.getDayOfMonth() + "/" + birth.getMonth() + "/" + birth.getYear();

        Log.d("INFO : ", name + " / " + password + " / " + birthParsed + " / " + email + " / " + genderChoice + " / " + genderNumber);


        //Check if inputs are OK
        if(name.length() > 2 && password.length() > 4 && birthParsed.length() > 7 && email.length() > 5){
            User user =new User(name, password, birthParsed, email, genderNumber);
            Score score = new Score(email, 0);
            //Try to get a user, with the name inserted. If not, return null user
            userManager.open();
            User checkUser = new User("null", "null", "null", "null", (byte) 0);
            //readUser in a try/catch to avoid application crash because of null cursor
            try{
                checkUser  = userManager.readUser(email);
            }catch (Exception e){
                Log.d("ERROR : ", e.getMessage());
            }
            userManager.close();

            if(checkUser.getName().equals("null")){
                userManager.open();
                scoreManager.open();
                if(userManager.createUser(user) == -1){
                    Log.d("WRONG : ", "CAN'T CREATE USER");
                }
                if(scoreManager.createScore(score) == -1){
                    Log.d("WRONG :", "CAN'T CREATE SCORE FOR A USER");
                }
                userManager.close();
                scoreManager.close();
                Log.d("INFO :", "USER CREATED : " + name);
                Log.d("INFO :", "SCORE CREATED, ID : " + score.getPlayer());
            }else {
                Log.d("WRONG :", "EMAIL ALREADY USED : " + email);
            }
        }else{
            Log.d("WRONG :", "INPUT WRONG");
        }
    }
}