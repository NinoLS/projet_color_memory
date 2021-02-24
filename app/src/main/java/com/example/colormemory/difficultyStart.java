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

public class difficultyStart extends AppCompatActivity {
    //joueur
    int niveau = 0;
    int manche = 0;
    int vie = 2;

    /*niveaux
     * 0 - facile
     * 1 - difficile
     * 2 - expert
     * 3 - chrono ?
     */

    //jeu
    Button[] btns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_facile);
    }


    public void startNiveau(View view) {
        if(niveau == 0) //facile
        {
            btns = new Button[4];
            btns[0] = findViewById(R.id.btn_facile_gauche);
            btns[1] = findViewById(R.id.btn_facile_droit);
            btns[2] = findViewById(R.id.btn_facile_bas);
            btns[3] = findViewById(R.id.btn_facile_haut);
        }
        if(niveau == 1) //difficile
        {

        }
        if(niveau == 2) //expert
        {

        }
        if(niveau == 3) //chrono ?
        {

        }

        startManche(view,niveau,manche);
    }

    public void startManche(View view,int niveau, int manche)
    {
        if(manche < 3)
        {
            allumeBouton(view,niveau,manche,0);
            this.manche++; //validation de la manche <=> à voir ou placer !
        }
    }

    @SuppressLint("ResourceAsColor")
    public void allumeBouton(View view,int niveau,int manche,int button_count)
    {
        if(button_count<=1+manche)
        {
            //tirage au sort
            byte random_index;
            random_index = (byte) Math.floor(Math.random() * (4 + niveau)); //de 1 à 4
            btns[random_index].setBackgroundColor(Color.RED);
            btns[random_index].setText(String.valueOf(button_count+1));

            //allumer button
            new CountDownTimer(1700, 1500) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    btns[random_index].setBackgroundColor(R.color.purple_700);
                    btns[random_index].setText("");
                    allumeBouton(view, niveau, manche,button_count+1);

                }
            }.start();
        }
    }
}