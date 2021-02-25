package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class difficultySelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selector);
    }

    public void startNiveauFacile(View view) {
        Intent intent = new Intent(getApplicationContext(),difficultyStart.class);
        //set_intent_1.putExtras(b_valider_bundle);

        startActivityForResult(intent,0);
    }
}