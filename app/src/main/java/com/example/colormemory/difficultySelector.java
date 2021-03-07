package com.example.colormemory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class difficultySelector extends AppCompatActivity {
    //difficultés
    Button[] d_BTNS_DIFF;
    int d_DIFF = 0;

    //
    float d_POINTS;
    TextView tv_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_difficulty_selector);

        //user
        d_POINTS = 0;  //à changer par rapport à la BDD
        tv_score = findViewById(R.id.tv_score);
        tv_score.setText("Score: " + d_POINTS);

        //boutons de difficultés
        d_BTNS_DIFF = new Button[4];
        d_BTNS_DIFF[0] = findViewById(R.id.btn_facile);
        d_BTNS_DIFF[1] = findViewById(R.id.btn_difficile);
        d_BTNS_DIFF[2] = findViewById(R.id.btn_expert);
        d_BTNS_DIFF[3] = findViewById(R.id.btn_chrono);
    }

    public void startNiveauFacile(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("DIFF",0);
        intent.putExtra("MANCHE_MIN",1);
        intent.putExtra("MANCHE_MAX",3); //à changer 3 par 10
        intent.putExtra("TEMPS_REPONSE",0);
        intent.putExtra("VIES",2);
        intent.putExtra("POIDS",1);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauDifficile(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("DIFF",0);
        intent.putExtra("MANCHE_MIN",3);
        intent.putExtra("MANCHE_MAX",15);
        intent.putExtra("TEMPS_REPONSE",0);
        intent.putExtra("VIES",2);
        intent.putExtra("POIDS",1.5);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauExpert(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("DIFF",2);
        intent.putExtra("MANCHE_MIN",5);
        intent.putExtra("MANCHE_MAX",20);
        intent.putExtra("TEMPS_REPONSE",0);
        intent.putExtra("VIES",3);
        intent.putExtra("POIDS",3);

        startActivityForResult(intent,0,intent.getExtras());
    }
    public void startNiveauChrono(View view) {
        Intent intent = new Intent(difficultySelector.this,difficultyStart.class);
        intent.putExtra("DIFF",3);
        intent.putExtra("MANCHE_MIN",1);
        intent.putExtra("MANCHE_MAX",250); //250 byte, maximum ?? (ou 0 - infini?)
        intent.putExtra("TEMPS_REPONSE",2); //2 secondes par boutons
        intent.putExtra("VIES",3);
        intent.putExtra("POIDS",2);

        startActivityForResult(intent,0,intent.getExtras());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0)
        {
            if(resultCode == Activity.RESULT_OK)
            {
                d_DIFF++;
                d_BTNS_DIFF[d_DIFF].setEnabled(true);
            }
            d_POINTS += data.getFloatExtra("POINTS",0);
            tv_score.setText("Score: " + d_POINTS);
        }
    }
}
