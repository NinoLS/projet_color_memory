package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import static java.lang.Thread.sleep;

public class difficultyFacile extends AppCompatActivity {
    //joueur
    byte manche = 1;
    byte vie = 2;

    //jeu
    Button btn_gauche;
    Button btn_droit;
    Button btn_bas;
    Button btn_haut;
    Button[] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_facile);

        //boutons
        btn_gauche = findViewById(R.id.btn_facile_gauche);
        btn_droit = findViewById(R.id.btn_facile_droit);
        btn_bas = findViewById(R.id.btn_facile_bas);
        btn_haut = findViewById(R.id.btn_facile_haut);
        btns = new Button[4];
        btns[0] = btn_gauche;
        btns[1] = btn_droit;
        btns[2] = btn_bas;
        btns[3] = btn_haut;

        //séquences parties
        startManche((byte)0);

    }

    private void startManche(byte manche)
    {
        byte random_index;
        for(int i=0;i<3;i++)
        {
            random_index = (byte)Math.floor(Math.random()*4); //de 1 à 4
            btns[random_index].setBackgroundColor(Color.RED);
            //Thread.sleep(2000);

        }

    }

}