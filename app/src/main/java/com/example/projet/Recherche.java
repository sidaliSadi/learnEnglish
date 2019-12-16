package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class Recherche extends AppCompatActivity {
    private ImageButton btn_recherche;
    private EditText e_mot;
    private ImageView img;
    private DataBaseManager db;
    private TextView tv;
    private Button btn_externe;
    private List<Mot> liste;
    private ImageButton imgBtn;
    private TextToSpeech t1;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recherche);

        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Recherche d'un mot");
        setSupportActionBar(toolbar);


        btn_recherche = findViewById(R.id.btn_recherche);
        imgBtn = findViewById(R.id.imageButton);

        e_mot = findViewById(R.id.recherche);
        db = new DataBaseManager(this);
        tv = findViewById(R.id.tv_mot);
        img = findViewById(R.id.imgVieww);
        btn_externe = findViewById(R.id.btn_externe);

        btn_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = e_mot.getText()+"";
                if ( m != "" ){
                    //si le mot nest pas vide
                    liste = db.Mots(m);
                    if ( liste.isEmpty() ){
                        Toast.makeText(Recherche.this, "Le mot n'existe pas !", Toast.LENGTH_SHORT).show();
                    }else {
                        tv.setText(liste.get(0).getMot() + " : " + liste.get(0).getTraduction());
                        //on test si il a une image ou pas
                        if ( liste.get(0).getImgLocal().equals("default") ){
                            //affichage image par defaut
                            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaut);
                            img.setImageBitmap(myBitmap);
                            Toast.makeText(Recherche.this, "web", Toast.LENGTH_SHORT).show();
                        }else{

                            File fff = new File(liste.get(0).getImgLocal());
                            if (fff.exists()) {
                                //Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaut);
                                Toast.makeText(Recherche.this, liste.get(0).getImgLocal(), Toast.LENGTH_SHORT).show();
                                Bitmap myBitmap = BitmapFactory.decodeFile(fff.getAbsolutePath());
                                img.setImageBitmap(myBitmap);

                            }
                        }

                    }
                }else{
                    Toast.makeText(Recherche.this, "Veuillez introduire un mot ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });
        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(liste.get(0).getTraduction(), TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        //ouvrir l'image externe dans un navigateur
        btn_externe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liste.get(0).getImgWeb().equals("default")) {
                    Toast.makeText(Recherche.this, "Pas d'image externe", Toast.LENGTH_SHORT).show();
                } else {
                    String url = liste.get(0).getImgWeb();
                    Uri uri = Uri.parse(url);
                    Intent inten = new Intent(Intent.ACTION_VIEW, uri);
                    // Verify that the intent will resolve to an activity
                    if (inten.resolveActivity(getPackageManager()) != null) {
                        // Here we use an intent without a Chooser unlike the next example
                        startActivity(inten);
                    }
                }
            }
        });
    }
}
