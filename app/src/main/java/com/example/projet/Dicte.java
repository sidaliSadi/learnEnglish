package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Dicte extends AppCompatActivity {
    private EditText reponse;
    ImageButton son;
    Button verifier, generer, voirReponse;
    DataBaseManager db;
    TextView tv;
    TextToSpeech t1;
     Mot m = null;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dicte);
        reponse = findViewById(R.id.reponse);
        son = findViewById(R.id.btnSon);
        verifier = findViewById(R.id.btnVerifier);
        tv = findViewById(R.id.tvvv);
        voirReponse = findViewById(R.id.btnVoirReponse);
        generer = findViewById(R.id.btnRegenerer);

        db = new DataBaseManager(this);

        m = db.dicteQuestion();
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        //ecouter le fichier son
        son.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(m.getTraduction(), TextToSpeech.QUEUE_FLUSH, null);
            }
        });

        verifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String r = reponse.getText()+"";
                if ( r != "" ){
                    if ( r.equals(m.getTraduction()) ){
                        Toast.makeText(Dicte.this, "Bravooo", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Dicte.this, "Nonnnnnnn", Toast.LENGTH_SHORT).show();
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
                m = db.dicteQuestion();
            }
        });

    }
}
