package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static java.lang.Thread.sleep;

public class difficultyStart extends AppCompatActivity {
    //joueur
    int niveau = 0;
    int manche = 0;
    int vie = 2;

    //jeu
    Button[] btns;
    Byte[] random_sequence;
    Byte[] pressed_sequence;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_facile);

        //jeu
        manche = 0;
        niveau = 0;
        vie = 2;



        //boutons jeu
        btns = new Button[4];
        btns[0] = findViewById(R.id.btn_facile_gauche);
        btns[1] = findViewById(R.id.btn_facile_droit);
        btns[2] = findViewById(R.id.btn_facile_bas);
        btns[3] = findViewById(R.id.btn_facile_haut);
        //reste...

        for(int b=0;b<btns.length;b++)
        {
            btns[b].setBackgroundColor(Color.BLUE);
        }
        setEnableButtons(btns,false); //activer les boutons
    }


    public void startNiveau(View view)
    {
        if(manche==0) //si aucune manche passée => on "crée" le niveau
        {
            if(niveau == 0) //facile : 3,4,5 boutons (3manches)
            {
                random_sequence = new Byte[5];
                view.setEnabled(false);
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
        }
        startManche(view,niveau,manche);
    }
    public void startManche(View view,int niveau, int manche)
    {
        if(manche < 3)
        {
            switchOnBouton(view,niveau,manche,0); //lance la sequence (récursivité)
            listenSequence(view);
        }
        else
        {
            manche = 0;
            setResult(Activity.RESULT_OK);
            finish(); //on sort
            //niveaux[niveau].setEnabled(true);
        }
    }
    @SuppressLint("ResourceAsColor")
    public void switchOnBouton(View view, int niveau, int manche, int button_count)
    {
        if(button_count < 3+manche) //manche 1 <=> 3 buttons
        {
            //tirage au sort + stockage sequence
            if(random_sequence[button_count] == null)
            {
                byte random_index = (byte) Math.floor(Math.random() * (4 + niveau)); //de 1 à 4
                random_sequence[button_count] = random_index;
            }

            //allumer bouton
            new CountDownTimer(200, 200) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    btns[random_sequence[button_count]].setBackgroundColor(Color.BLACK);
                    btns[random_sequence[button_count]].setText(String.valueOf(button_count+1));
                }
            }.start();

            //eteindre button
            new CountDownTimer(1500, 1500) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    btns[random_sequence[button_count]].setBackgroundColor(Color.BLUE);
                    btns[random_sequence[button_count]].setText("");
                    switchOnBouton(view, niveau, manche,button_count+1);

                }
            }.start();
        }
        else
            setEnableButtons(btns,true); //activer les boutons
    }
    public void listenSequence(View view)
    {
        pressed_sequence = new Byte[3+manche]; //"vider" la sequence
        for(byte b=0;b<btns.length;b++)
        {
            final byte finalB = b;
            btns[b].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pressed_sequence[findFirstFreeIndex(pressed_sequence)] = finalB;
                    if(pressed_sequence[pressed_sequence.length-1] != null) //Tout complété
                    {
                        setEnableButtons(btns,false);
                        finishManche(view,compareTwoTab(pressed_sequence,random_sequence));
                        //manche gagné si les 2 séquences sont identiques
                        /* //CORRESPOND A :
                        if(compareTwoTab(pressed_sequence,random_sequence))

                            finishManche(true);
                        else
                            finishManche(false);
                        */
                    }
                }
            });
        }
    }



    //FONCTIONS AIDE
    public int findFirstFreeIndex(Object[] tab)
    {
        int i=0;
        while(tab[i] != null)
            i++;
        return i;
    }
    public boolean compareTwoTab(Object[] t1, Object[] t2)
    {
        int size;
        if(t1.length>t2.length)
            size=t2.length;
        else size=t1.length;

        for(int i=0;i<size;i++)
        {
            if(t1[i] != t2[i])
                return false;
        }
        return true; //si on arrive ici : tout correspond
    }
    public void setEnableButtons(Button[] btns,boolean bool)
    {
        for(int i=0;i<btns.length;i++)
        {
            btns[i].setEnabled(bool);
        }
    }


    //FONCTIONS STYLE
    public void finishManche(View view,boolean success)
    {
        new CountDownTimer(300, 300) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onFinish() {
                for(int b=0;b<btns.length;b++)
                    btns[b].setBackgroundColor(success?Color.GREEN:Color.RED);
            }
        }.start();
        new CountDownTimer(1300, 1300) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onFinish() {
                for(int b=0;b<btns.length;b++)
                    btns[b].setBackgroundColor(Color.BLUE);
                if (success) {
                    manche++;
                    startNiveau(view);
                }
                else //si perdu
                {
                    view.setEnabled(true); //on peut reessayer
                    manche = 0; //manche 0
                }

            }
        }.start();
    }
}
