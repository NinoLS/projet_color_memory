package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;

import java.util.Timer;

import static java.lang.Thread.sleep;

public class difficultyFacile extends AppCompatActivity {
    //joueur
    int manche = 1;
    int vie = 2;

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


    }

    /*niveaux
     * 0 - facile
     * 1 - difficile
     * 2 - expert
     * 3 - chrono ?
     */

    public void startNiveauFacile(View view) {
        manche = 0;
        startManche(view,0,manche);

    }

    public void startManche(View view,int niveau, int manche)
    {
        if(manche < 3)
        {
            allumeBouton(view,niveau,manche,0);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void allumeBouton(View view,int niveau,int manche,int button_count)
    {
        if(button_count < 4)
        {
            byte random_index;
            random_index = (byte) Math.floor(Math.random() * (4 + niveau)); //de 1 à 4
            btns[random_index].setBackgroundColor(Color.RED);
            btns[random_index].setText(String.valueOf(button_count));
            new CountDownTimer(1700, 1500) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    btns[random_index].setBackgroundColor(R.color.purple_700);
                    btns[random_index].setText("");
                    allumeBouton(view, niveau, manche, button_count + 1);

                }
            }.start();
        }
    }
}