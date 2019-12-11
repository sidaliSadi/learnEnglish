package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class Traduction extends AppCompatActivity {
    private DataBaseManager db;
    private ListView lview;
    private String[] m;
    private ImageButton imgBtn;
    private static ImageView imgView;
    private Button btnSuivant;
    private TextToSpeech t1;
    private TextView tv_mot;
    private static int i,j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduction);
        //lview = findViewById(R.id.lvv);
        imgBtn = findViewById(R.id.imageButton);
        imgView = findViewById(R.id.imageView);
        btnSuivant = findViewById(R.id.btn_suivant);
        tv_mot = findViewById(R.id.tv_mot);

        //recuperer litem
        Intent intent = getIntent();
        String nom = intent.getStringExtra("nom_liste");
        db = new DataBaseManager(this);

        final List<Mot> ll = db.readListMots(nom);

        m = new String[ll.size()];

        //recuperation des categories dans un tableau
        i = 0;
        j = 0;

        final int taille = ll.size();


        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.US);
                }
            }
        });

        File imgFile = new  File(ll.get(i).getImgLocal());

        if(imgFile.exists()) {

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            Traduction.imgView.setImageBitmap(myBitmap);

        }
        tv_mot.setText(ll.get(i).getMot()+" : "+ll.get(i).getTraduction());
        i++;

        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on a parcouru toute notre liste
                 if (i == taille){
                    Toast.makeText(Traduction.this, "La liste est terminée", Toast.LENGTH_SHORT).show();
                    btnSuivant.setEnabled(true);
                    j = 0;
                    i = 0;
                }else{

                    File imgFile = new  File(ll.get(i).getImgLocal());

                    //ajouter l'image
                    if(imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        Traduction.imgView.setImageBitmap(myBitmap);

                    }
                     tv_mot.setText(ll.get(i).getMot()+" : "+ll.get(i).getTraduction());
                     i++;
                     j++;

                }
            }
        });



        imgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t1.speak(ll.get(j).getTraduction(), TextToSpeech.QUEUE_FLUSH, null);

            }
        });

        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, m);
        //lview.setAdapter(adapter);
    }
}
