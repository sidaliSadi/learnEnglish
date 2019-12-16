package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.List;

public class SuppressionActivity extends AppCompatActivity {
    private ImageButton btn_recherche;
    private Button btn_suppComplet, btn_supPartiel;
    private TextView tv, tv_externe;
    private EditText e_mot;
    private DataBaseManager db;
    private ImageView imgView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_suppression);
        btn_recherche = findViewById(R.id.btn_recherche);
        btn_suppComplet = findViewById(R.id.btn_complet);
        btn_supPartiel = findViewById(R.id.btn_partiel);
        e_mot = findViewById(R.id.recherche);
        db = new DataBaseManager(this);
        tv = findViewById(R.id.tv_mot);
        imgView = findViewById(R.id.imgVieww);
        tv_externe = findViewById(R.id.lienExterne);

        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Supprimer Une Traduction");
        setSupportActionBar(toolbar);
        btn_supPartiel.setEnabled(false);
        btn_suppComplet.setEnabled((false));

        //si je click sur le btn recherche
        btn_recherche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = e_mot.getText()+"";
                if ( m != "" ){
                    //si le mot nest pas vide
                    List<Mot> liste = db.Mots(m);
                    if ( liste.isEmpty() ){
                        Toast.makeText(SuppressionActivity.this, "Le mot n'existe pas !", Toast.LENGTH_SHORT).show();
                    }else {
                        tv.setText(liste.get(0).getMot() + " : " + liste.get(0).getTraduction());
                       //on test si il a une image ou pas
                        if ( liste.get(0).getImgLocal().equals("default") ){
                            //affichage image par defaut
                            Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaut);
                            imgView.setImageBitmap(myBitmap);
                            tv_externe.setText("Voir image externe");
                            Toast.makeText(SuppressionActivity.this, "web", Toast.LENGTH_SHORT).show();
                        }else{

                            File fff = new File(liste.get(0).getImgLocal());
                            if (fff.exists()) {

                                Bitmap myBitmap = BitmapFactory.decodeFile(fff.getAbsolutePath());

                                imgView.setImageBitmap(myBitmap);

                            }
                        }


                        btn_supPartiel.setEnabled(true);
                        btn_suppComplet.setEnabled((true));
                    }
                }else{
                    Toast.makeText(SuppressionActivity.this, "Veuillez introduire un mot ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_suppComplet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = e_mot.getText()+"";
                //suppression de limage
                List<Mot> liste = db.Mots(m);

                if ( liste.get(0).getImgLocal().equals("default") ){
                    Toast.makeText(SuppressionActivity.this, "Déja supprimée !", Toast.LENGTH_SHORT).show();
                }else{
                    File fichier = new File(liste.get(0).getImgLocal());
                    if ( fichier.exists() ){
                        fichier.delete();
                        Toast.makeText(SuppressionActivity.this, "fichier supprimé", Toast.LENGTH_SHORT).show();
                    }
                }
                boolean b = db.supprimerTraduction(m, liste.get(0).getId());
                if ( b ){
                    Toast.makeText(SuppressionActivity.this, "Suppression traduction reussie", Toast.LENGTH_SHORT).show();
                    btn_suppComplet.setEnabled(false);
                    btn_supPartiel.setEnabled(false);
                    tv.setText("");
                    tv_externe.setText("");

                        Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaut);
                        imgView.setImageBitmap(myBitmap);

                }else{
                    Toast.makeText(SuppressionActivity.this, "Suppression traduction echoué", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_supPartiel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = e_mot.getText()+"";

                //suppression de limage
                List<Mot> liste = db.Mots(m);

                File fichier = new File(liste.get(0).getImgLocal());
                if ( fichier.exists() ){
                    fichier.delete();
                    Toast.makeText(SuppressionActivity.this, "fichier supprimé", Toast.LENGTH_SHORT).show();
                }
                //mettre à jour dans la base imageLocal = defaut
                if ( db.supprimerImage(m)){
                    Toast.makeText(SuppressionActivity.this, "modification reussie !", Toast.LENGTH_SHORT).show();
                }
                //affichage image par defaut
                Bitmap myBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.defaut);
                imgView.setImageBitmap(myBitmap);

                tv_externe.setText("Voir image externe");
                btn_supPartiel.setEnabled(false);

            }
        });

        tv_externe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String m = e_mot.getText()+"";
                List<Mot> ll = db.Mots(m);
                String url = ll.get(0).getImgWeb();
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


}
