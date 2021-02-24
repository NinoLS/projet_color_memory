package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
    Byte[] random_sequence;
    Byte[] pressed_sequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_facile);
        manche = 0;
        niveau = 0;
        vie = 2;
    }


    public void startNiveau(View view) {
        if(manche==0) //si aucune manche passée => on "crée" le niveau
        {
            if(niveau == 0) //facile
            {
                btns = new Button[4];
                btns[0] = findViewById(R.id.btn_facile_gauche);
                btns[1] = findViewById(R.id.btn_facile_droit);
                btns[2] = findViewById(R.id.btn_facile_bas);
                btns[3] = findViewById(R.id.btn_facile_haut);
                random_sequence = new Byte[5]; //5 buttons max (3,4,5) <=> 3 manches
                setEnableButtons(btns,true);
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
            allumeBouton(view,niveau,manche,0); //lance la sequence (avec le récursive)
            setEnableButtons(btns,true);
            attendreSequence();
        }
    }

    @SuppressLint("ResourceAsColor")
    public void allumeBouton(View view,int niveau,int manche,int button_count)
    {
        if(button_count < 3+manche) //manche 1 <=> 3 buttons
        {
            //tirage au sort + stockage sequence
            if(random_sequence[button_count] == null)
            {
                byte random_index = (byte) Math.floor(Math.random() * (4 + niveau)); //de 1 à 4
                random_sequence[button_count] = random_index;
            }

            btns[random_sequence[button_count]].setBackgroundColor(Color.RED);
            btns[random_sequence[button_count]].setText(String.valueOf(button_count+1));

            //allumer button
            new CountDownTimer(1700, 1500) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    btns[random_sequence[button_count]].setBackgroundColor(R.color.purple_700);
                    btns[random_sequence[button_count]].setText("");
                    allumeBouton(view, niveau, manche,button_count+1);

                }
            }.start();
        }
    }



    public boolean attendreSequence()
    {
        pressed_sequence = new Byte[3+manche];

        for(byte b=0;b<btns.length;b++)
        {
            final byte finalB = b;
            btns[b].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(difficultyStart.this, "B"+finalB, Toast.LENGTH_SHORT).show();
                    pressed_sequence[findFirstFreeIndex(pressed_sequence)] = finalB;
                    if(pressed_sequence[pressed_sequence.length-1] != null) //Tout complété
                    {
                        setEnableButtons(btns,false);
                        if(compareTwoTab(pressed_sequence,random_sequence))
                        {
                            manche++;
                            Toast.makeText(difficultyStart.this, "BON!!!", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });
        }
        /*
        btns[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(difficultyStart.this, "GAUCHE", Toast.LENGTH_SHORT).show();
            }
        });
        btns[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed_sequence[findFirstFreeIndex(pressed_sequence)] = 1;
                if(pressed_sequence[pressed_sequence.length-1] != null)
                    setEnableButtons(btns,false);
                Toast.makeText(difficultyStart.this, "DROIT", Toast.LENGTH_SHORT).show();
            }
        });
        btns[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed_sequence[findFirstFreeIndex(pressed_sequence)] = 2;
                if(pressed_sequence[pressed_sequence.length-1] != null)
                    setEnableButtons(btns,false);
                Toast.makeText(difficultyStart.this, "BAS", Toast.LENGTH_SHORT).show();
            }
        });
        btns[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pressed_sequence[findFirstFreeIndex(pressed_sequence)] = 3;
                if(pressed_sequence[pressed_sequence.length-1] != null)
                    setEnableButtons(btns,false);
                Toast.makeText(difficultyStart.this, "HAUT", Toast.LENGTH_SHORT).show();
            }
        });*/
        /*
        if(pressed_sequence.equals(random_sequence))
        {
            Toast.makeText(this, "YESSS", Toast.LENGTH_SHORT).show();
            return true;
        }
        else
        {
            Toast.makeText(this, "nooo", Toast.LENGTH_SHORT).show();
            return false;
        }*/
        return false;
    }





    //FONCTIONS AIDE

    public int findFirstFreeIndex(Object[] tab)
    {
        int i=0;
        while(tab[i] != null)
            i++;
        return i;
    }

    public void setEnableButtons(Button[] btns,boolean bool)
    {
        for(int i=0;i<btns.length;i++)
        {
            btns[i].setEnabled(bool);
        }
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
}
