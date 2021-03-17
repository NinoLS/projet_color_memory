package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        EditText name_input = findViewById(R.id.loginName_input);
        String name_inputContent = name_input.getText().toString().trim();

        EditText password_input
    }
}