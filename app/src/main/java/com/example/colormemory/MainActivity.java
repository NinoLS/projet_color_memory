package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import database.User;
import database.UserManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void GoTo(View v){
        Intent intent = new Intent(this,SignUpActivity.class);
        startActivity(intent);
    }

    public void login(View v){
        EditText email_input = findViewById(R.id.loginEmail_input);
        String email = email_input.getText().toString().trim();

        EditText password_input = findViewById(R.id.loginPassword_input);
        String password = password_input.getText().toString().trim();

        UserManager userManager = new UserManager(this);
        userManager.open();
        if(password.length() > 4 && email.length() > 5){
            User userForCheck = userManager.readUser(email);
            if(!userForCheck.getName().equals("null")){
                if(email.equals(userForCheck.getEmail()) && password.equals(userForCheck.getPassword())){
                    Log.d("INFO : ", "USER CONNECTED !");
                    Intent intent = new Intent(this,difficultySelector.class);
                    intent.putExtra("email",email);
                    startActivityForResult(intent,0);
                }else {
                    Log.d("WRONG : ", "Email or password invalid");
                    Toast.makeText(getBaseContext(), "Identifiants invalides", Toast.LENGTH_LONG).show();
                }
            }else{
                Log.d("WRONG : ", "User not found");
                Toast.makeText(getBaseContext(), "Utilisateur non inscrit, inscrivez-vous ?", Toast.LENGTH_LONG).show();
            }
        }else{
            Log.d("WRONG : ", "Email or password not valid");
            Toast.makeText(getBaseContext(), "Format des identifiants non valide", Toast.LENGTH_LONG).show();
        }
        userManager.close();
    }
}