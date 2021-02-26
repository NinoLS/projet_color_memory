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
        intent.putExtra("niveau",0);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauDifficile(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("niveau",1);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauExpert(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("niveau",2);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauChrono(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("niveau",3);

        startActivityForResult(intent,0,intent.getExtras());
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
