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
    int n_NIVEAU;
    int n_NIVEAU_MIN;
    int n_NIVEAU_MAX;
    int n_MANCHE_MIN;
    int n_MANCHE;
    int n_MANCHE_MAX;
    int n_TEMPS_REPONSE;
    int n_VIES;

    //jeu
    Button[] btns;
    int[] btns_ids;
    Byte[] random_sequence;
    Byte[] pressed_sequence;
    TextView tv_vies;
    TextView tv_niveau;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_facile);

        //parametre diffculte
        Intent intent = getIntent();
        n_DIFF = intent.getIntExtra("DIFF",0);
        n_NIVEAU_MIN = 1;
        n_NIVEAU = n_NIVEAU_MIN;
        n_NIVEAU_MAX = 7;
        n_MANCHE_MIN = intent.getIntExtra("MANCHE_MIN",1);
        n_MANCHE = n_MANCHE_MIN;
        n_MANCHE_MAX = intent.getIntExtra("MANCHE_MAX",10);
        n_VIES = intent.getIntExtra("VIES",2);

        if(n_DIFF == 3)
            n_TEMPS_REPONSE = intent.getIntExtra("TEMPS_REPONSE",2);

        //compteurs
        tv_vies = findViewById(R.id.tv_vies);
        tv_vies.setText("Vies: " + n_VIES);
        tv_niveau = findViewById(R.id.tv_niveau);
        tv_niveau.setText("Niveau: " + n_NIVEAU);

        //ordres des boutons (id)
        btns_ids = new int[10];
        btns_ids[0] = R.id.btn_bas;
        btns_ids[1] = R.id.btn_haut;
        btns_ids[2] = R.id.btn_gauche;
        btns_ids[3] = R.id.btn_droit;
        btns_ids[4] = R.id.btn_haut_haut;
        btns_ids[7] = R.id.btn_bas_bas;
        btns_ids[8] = R.id.btn_bas_gauche;
        btns_ids[6] = R.id.btn_haut_droit;
        btns_ids[5] = R.id.btn_haut_gauche;
        btns_ids[9] = R.id.btn_bas_droit;

        startDifficulty();
    }




    public void startDifficulty()
    {
        new CountDownTimer(2000,2000)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                startNiveau();
            }
        }.start();
    }
    public void startNiveau()
    {
        if(n_NIVEAU <= n_NIVEAU_MAX)
        {
            displayButtons(n_NIVEAU+3); //niveau 1 -> 4=3+1 boutons
            tv_niveau.setText("Niveau: " + n_NIVEAU); //affichage niveau
            random_sequence = new Byte[n_MANCHE_MAX]; //nouvelle sequence random
            n_MANCHE = n_MANCHE_MIN; //1ere manche
            startManche();
        }
        else
        {
            setResult(Activity.RESULT_OK);
            finish(); //on sort
        }
    }
    public void startManche()
    {
        if(n_MANCHE <= n_MANCHE_MAX)
        {
            switchOnBouton(0); //lance la sequence (récursivité)
            if(n_DIFF < 3)
                listenSequence();
            else chronoSequence(); //pour le niveau chrono
        }
        else
        {
            n_NIVEAU++;
            startNiveau();
        }
    }
    @SuppressLint("ResourceAsColor")
    public void switchOnBouton(int button_count)
    {
        if(button_count < n_MANCHE) //ex: [facile,manche 0] => 1 bouton
        {
            //tirage au sort + stockage sequence
            if(random_sequence[button_count] == null)
            {
                byte random_index = (byte) Math.floor(Math.random() * (3 + n_NIVEAU));
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
                    //btns[random_sequence[button_count]].setText(String.valueOf(button_count+1));
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
                    switchOnBouton(button_count+1);

                }
            }.start();
        }
        else
            setEnableButtons(true); //activer les boutons
    }


    public void listenSequence()
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
                        setEnableButtons(false);
                        finishManche(compareTwoTab(pressed_sequence,random_sequence));
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
    public void chronoSequence()
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
    public void finishManche(boolean success)
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
                    startManche();
                }
                else //si perdu
                {
                    if(n_VIES>0)
                    {
                        //encore des vies => reste niveau actuelle
                        n_MANCHE = n_MANCHE_MIN; //retour 1ere manche
                        n_VIES--;
                        tv_vies.setText("Vies: " + n_VIES);
                        startNiveau(); //on recommence le niveau
                        //Toast.makeText(difficultyStart.this, "Encore "+n_VIES+" vie"+((n_VIES>1)?"s":""), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        //plus de vies => on sort ? OU retour au niveau 1
                        n_NIVEAU = 0;
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
    public void setEnableButtons(boolean bool)
    {
        for(int i=0;i<btns.length;i++)
        {
            btns[i].setEnabled(bool);
        }
    }
    public void displayButtons(int number)
    {
        btns = new Button[number];
        for(int b=0;b<number;b++)
        {
            btns[b] = findViewById(btns_ids[b]);
            btns[b].setVisibility(View.VISIBLE);
            btns[b].setBackgroundColor(Color.BLUE);
        }
        setEnableButtons(false); //activer les boutons
    }

}
