package com.example.colormemory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class difficultySelector extends AppCompatActivity {
    //niveaux
    Button[] niveaux;
    int niveau = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selector);

        //niveaux
        niveaux = new Button[4];
        niveaux[0] = findViewById(R.id.btn_facile);
        niveaux[1] = findViewById(R.id.btn_difficile);
        niveaux[2] = findViewById(R.id.btn_expert);
        niveaux[3] = findViewById(R.id.btn_chrono);

    }

    public void startNiveauFacile(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        //set_intent_1.putExtras(b_valider_bundle);

        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                niveau++;
                niveaux[niveau].setEnabled(true);
            }
        }
    }
}
