package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Quiz2 extends AppCompatActivity {
    private Spinner spinne;
    private  DataBaseManager db_manager;
    Button verifier, generer, voirReponse;
    private EditText reponse;
    TextView tv;
    TextView tvMot;
    Mot m = null;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz2);
        db_manager = new DataBaseManager(this);
        tvMot = findViewById(R.id.tvllll);
        reponse = findViewById(R.id.reponse);
        verifier = findViewById(R.id.btnVerifier);
        tv = findViewById(R.id.tvvv);
        voirReponse = findViewById(R.id.btnVoirReponse);
        generer = findViewById(R.id.btnRegenerer);

        //recuperation du spinner
        spinne = findViewById(R.id.categorie);
        //recuperer les listes crees par lutilisateur
        List<String> categorires = db_manager.readListes();

        //ajouter dans l'adapteur
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorires);
        spinne.setAdapter(adapter);

        m = db_manager.quiz2Question(spinne.getSelectedItem().toString());
        //String l = spinne.getSelectedItem().toString();
        tvMot.setText(m.getMot()+" == .....");

        verifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = reponse.getText()+"";
                if ( r != "" ){
                    if ( r.equals(m.getTraduction()) ){
                        Toast.makeText(Quiz2.this, "Bravooo", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Quiz2.this, "Nonnnnnnn", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        voirReponse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ( x == 0 ){
                    tv.setText(m.getTraduction());
                    x++;
                    voirReponse.setText("masquer reponse");
                }else{
                    tv.setText("");
                    x--;
                    voirReponse.setText("voir reponse");
                }
            }
        });

        generer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = db_manager.quiz2Question(spinne.getSelectedItem().toString());
                tvMot.setText(m.getMot()+" == .....");
            }
        });
    }
}
