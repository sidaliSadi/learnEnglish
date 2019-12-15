package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
    private Button btnSuivant, btn_externe;
    private TextToSpeech t1;
    private TextView tv_mot;
    private Toolbar toolbar;
    private static int i,j;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traduction);
        //lview = findViewById(R.id.lvv);
        imgBtn = findViewById(R.id.imageButton);
        imgView = findViewById(R.id.imageView);
        btnSuivant = findViewById(R.id.btn_partiel);
        tv_mot = findViewById(R.id.tv_mot);
        btn_externe = findViewById(R.id.btn_externe);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

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
        tv_mot.setText(ll.get(i).getMot()+ " :\n"+ll.get(i).getTraduction());
        i++;

        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on a parcouru toute notre liste
                 if (i == taille){
                    Toast.makeText(Traduction.this, "La liste est terminée", Toast.LENGTH_SHORT).show();
                    btnSuivant.setEnabled(false);
                    i = 0;
                }else{

                    File imgFile = new  File(ll.get(i).getImgLocal());

                    //ajouter l'image
                    if(imgFile.exists()) {

                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                        Traduction.imgView.setImageBitmap(myBitmap);

                    }
                     tv_mot.setText(ll.get(i).getMot()+" : \n"+ll.get(i).getTraduction());
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
        //ouvrir l'image externe dans un navigateur
        btn_externe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = ll.get(j).getImgWeb();
                Uri uri = Uri.parse(url);
                Intent inten = new Intent(Intent.ACTION_VIEW, uri);
                // Verify that the intent will resolve to an activity
                if (inten.resolveActivity(getPackageManager()) != null) {
                    // Here we use an intent without a Chooser unlike the next example
                    startActivity(inten);
                }
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.quitter:
                finish();
                break;
            case R.id.acceuil:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);

            case R.id.maList:
                Intent i1 = new Intent(this,Listes.class);
                startActivity(i1);
                break;
            case R.id.recherche:
                Intent i2 = new Intent(this,Recherche.class);
                startActivity(i2);
                break;
        }
        return true;
    }
}
