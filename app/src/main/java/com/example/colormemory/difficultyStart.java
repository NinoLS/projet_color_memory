package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static java.lang.Thread.sleep;

public class difficultyStart extends AppCompatActivity {
    //joueur
    int n_DIFF;
    int n_MANCHE_MIN;
    int n_MANCHE;
    int n_MANCHE_MAX;
    int n_TEMPS_REPONSE;
    int n_VIES;

    //jeu
    Button[] btns;
    Byte[] random_sequence;
    Byte[] pressed_sequence;
    TextView tv_vies;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_facile);

        //parametre diffculte
        Intent intent = getIntent();
        n_DIFF = intent.getIntExtra("DIFF",0);
        n_MANCHE_MIN = intent.getIntExtra("MANCHE_MIN",1);
        n_MANCHE = n_MANCHE_MIN;
        n_MANCHE_MAX = intent.getIntExtra("MANCHE_MAX",10);
        n_VIES = intent.getIntExtra("VIES",2);
        if(n_DIFF == 3)
            n_TEMPS_REPONSE = intent.getIntExtra("TEMPS_REPONSE",2);

        //boutons jeu
        tv_vies = findViewById(R.id.tv_vies);
        tv_vies.setText("Vies: " + n_VIES);
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


    public void startDifficulte(View view)
    {
        if(n_MANCHE == n_MANCHE_MIN) //si aucun niveau passée => on "crée" la difficulté
        {
            view.setEnabled(false);
            random_sequence = new Byte[n_MANCHE_MAX];
        }
        startManche(view);
    }
    public void startManche(View view)
    {
        if(n_MANCHE <= n_MANCHE_MAX)
        {
            switchOnBouton(view,0); //lance la sequence (récursivité)
            if(n_DIFF < 3)
                listenSequence(view);
            else chronoSequence(view);
        }
        else
        {
            setResult(Activity.RESULT_OK);
            finish(); //on sort
        }
    }
    @SuppressLint("ResourceAsColor")
    public void switchOnBouton(View view, int button_count)
    {
        if(button_count < n_MANCHE) //ex: [facile,manche 0] => 1 bouton
        {
            //tirage au sort + stockage sequence
            if(random_sequence[button_count] == null)
            {
                byte random_index = (byte) Math.floor(Math.random() * (4 + n_DIFF)); //de 1 à 4
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
                    switchOnBouton(view,button_count+1);

                }
            }.start();
        }
        else
            setEnableButtons(btns,true); //activer les boutons
    }
    public void listenSequence(View view)
    {
        pressed_sequence = new Byte[n_MANCHE]; //"vider" la sequence (manches d'avant)
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

    public void chronoSequence(View view)
    {
        //difficulté 3 <=> TIMER
        new CountDownTimer(n_TEMPS_REPONSE*1000*n_MANCHE,n_TEMPS_REPONSE*1000*n_MANCHE)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(findFirstFreeIndex(pressed_sequence) == pressed_sequence.length)
                {
                    if(compareTwoTab(pressed_sequence,random_sequence));
                }
            }
        };
    }
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
        new CountDownTimer(1200, 1200) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onFinish() {
                for(int b=0;b<btns.length;b++)
                    btns[b].setBackgroundColor(Color.BLUE);
                if (success) {
                    n_MANCHE++;
                    startManche(view);
                }
                else //si perdu
                {
                    if(n_VIES>0)
                    {
                        view.setEnabled(true); //on peut reessayer
                        n_MANCHE = n_MANCHE_MIN; //retour 1ere manche
                        n_VIES--;
                        tv_vies.setText("Vies: " + n_VIES);
                        //Toast.makeText(difficultyStart.this, "Encore "+n_VIES+" vie"+((n_VIES>1)?"s":""), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        setResult(Activity.RESULT_CANCELED);
                        finish(); //on sort
                    }
                }

            }
        }.start();
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

}
