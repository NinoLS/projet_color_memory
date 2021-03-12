package com.example.colormemory;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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
    float n_POIDS;
    int n_POINTS = 0;

    //jeu
    Button[] btns;
    int[] btns_colors;
    int[] btns_ids;
    Byte[] random_sequence;
    Byte[] pressed_sequence;
    TextView tv_vies;
    TextView tv_niveau;
    TextView tv_points;
    Boolean chrono = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty);

        //parametre difficulte
        Intent intent = getIntent();
        n_DIFF = intent.getIntExtra("DIFF",0);
        n_NIVEAU_MIN = 1;
        n_NIVEAU = n_NIVEAU_MIN;
        n_NIVEAU_MAX = 3;
        n_MANCHE_MIN = intent.getIntExtra("MANCHE_MIN",1);
        n_MANCHE = n_MANCHE_MIN;
        n_MANCHE_MAX = intent.getIntExtra("MANCHE_MAX",10);
        n_VIES = intent.getIntExtra("VIES",2);
        n_POIDS = intent.getIntExtra("POIDS",1);

        if(n_DIFF == 3)
            n_TEMPS_REPONSE = intent.getIntExtra("TEMPS_REPONSE",2);

        //compteurs
        tv_vies = findViewById(R.id.tv_vies);
        tv_vies.setText("Vies: " + n_VIES);
        tv_niveau = findViewById(R.id.tv_niveau);
        tv_niveau.setText("Niveau: " + n_NIVEAU);
        tv_points = findViewById(R.id.tv_pointsManche);

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

        btns_colors = new int[10];
        btns_colors[0] = Color.rgb(76,175,80);
        btns_colors[1] = Color.rgb(255,235,59);
        btns_colors[2] = Color.rgb(244,67,54);
        btns_colors[3] = Color.rgb(3,169,244);
        btns_colors[4] = Color.rgb(138,49,49);
        btns_colors[7] = Color.rgb(119,119,119);
        btns_colors[8] = Color.rgb(185,38,195);
        btns_colors[6] = Color.rgb(103,58,183);
        btns_colors[5] = Color.rgb(255,152,0);
        btns_colors[9] = Color.rgb(9,213,193);

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
            Intent intent = new Intent(difficultyStart.this,difficultySelector.class);
            intent.putExtra("POINTS",(float)n_POINTS);
            setResult(Activity.RESULT_OK,intent);
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
            //else chronoSequence(); //fait dans le CountDownTimer (sequence boutons)
            if(n_DIFF == 3 && (n_MANCHE-1)%5==0) //points toutes les 5 manches (chrono) à changer ?
            {
                n_POINTS += n_POIDS * (n_MANCHE-1)/5;
                tv_points.setText("Points: " + n_POINTS);
            }
        }
        else
        {
            n_POINTS += n_POIDS * n_NIVEAU;
            n_NIVEAU++;
            tv_points.setText("Points: " + n_POINTS);
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
            new CountDownTimer(1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                }

                @Override
                public void onFinish() {
                    btns[random_sequence[button_count]].setBackgroundColor(btns_colors[random_sequence[button_count]]);
                    btns[random_sequence[button_count]].setText("");
                    switchOnBouton(button_count+1);

                }
            }.start();
        }
        else //séquence terminée
        {
            setEnableButtons(true); //activer les boutons
            if(n_DIFF == 3)
                chronoSequence(); //pour le niveau CHRONO
        }
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
                        if(chrono)
                            chronoFinish();
                        setEnableButtons(false);
                    }
                }
            });
        }

        //difficulté 3 <=> TIMER
        chrono = true;                          //1000 (ms->s) trop long, trop facile
        new CountDownTimer(n_TEMPS_REPONSE*600*n_MANCHE,n_TEMPS_REPONSE*1000*n_MANCHE)
        {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                if(chrono)
                    chronoFinish();
            }
        }.start();
    }
    public void chronoFinish()
    {
        chrono = false;
        setEnableButtons(false);
        if(pressed_sequence[pressed_sequence.length-1] != null) //si tout complété
        {
            finishManche(compareTwoTab(pressed_sequence,random_sequence));
        }
        else finishManche(false);
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
                    btns[b].setBackgroundColor(btns_colors[b]);
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
                        Intent intent = new Intent(difficultyStart.this,difficultySelector.class);
                        intent.putExtra("POINTS",(float)n_POINTS);
                        setResult(Activity.RESULT_CANCELED,intent);
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
    @SuppressLint("ResourceAsColor")
    public void displayButtons(int number)
    {
        btns = new Button[number];
        for(int b=0;b<number;b++)
        {
            btns[b] = findViewById(btns_ids[b]);
            btns[b].setVisibility(View.VISIBLE);
            btns[b].setBackgroundColor(btns_colors[b]);
        }
        setEnableButtons(false); //activer les boutons
    }

}
