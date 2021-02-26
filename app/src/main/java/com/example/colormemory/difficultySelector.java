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
        intent.putExtra("NIVEAU",0);
        intent.putExtra("MANCHE_MIN",1);
        intent.putExtra("MANCHE_MAX",10);
        intent.putExtra("TEMPS_REPONSE",0);
        intent.putExtra("VIES",2);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauDifficile(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("NIVEAU",0);
        intent.putExtra("MANCHE_MIN",3);
        intent.putExtra("MANCHE_MAX",15);
        intent.putExtra("TEMPS_REPONSE",0);
        intent.putExtra("VIES",2);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauExpert(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("NIVEAU",2);
        intent.putExtra("MANCHE_MIN",5);
        intent.putExtra("MANCHE_MAX",20);
        intent.putExtra("TEMPS_REPONSE",0);
        intent.putExtra("VIES",3);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauChrono(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("NIVEAU",3);
        intent.putExtra("MANCHE_MIN",1);
        intent.putExtra("MANCHE_MAX",500); //500 maximum ?? (ou 0 - infini)
        intent.putExtra("TEMPS_REPONSE",2); //2 secondes par boutons
        intent.putExtra("VIES",3);

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
