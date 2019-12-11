package com.example.projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.regex.Pattern;

public class GestionBD extends AppCompatActivity {
    private DataBaseManager db_manager;
    private Button btn_son, btn_image, btn_valider;
    private EditText e_mot, e_traduction, e_categorie, e_urlImage;
    private String mot, traduction, categorie,sonPath = "", imagePath = "", urlImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestion_bd);
        db_manager = new DataBaseManager(this);

        /*récuperer les bouttons*/
        btn_image = (Button) findViewById(R.id.btn_img);
        btn_son = (Button) findViewById(R.id.btn_son);
        btn_valider = (Button) findViewById(R.id.btn_valider);

        /*récuperer les edits text*/
        e_mot = (EditText) findViewById(R.id.mot);
        e_traduction = (EditText) findViewById(R.id.traduction);
        e_categorie = (EditText) findViewById(R.id.categorie);
        e_urlImage = (EditText) findViewById(R.id.imgWeb);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
         != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }

        btn_image.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(GestionBD.this)
                        .withRequestCode(10)
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });

        /*ajouuuuuuuuut fichier son*/
        btn_son.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new MaterialFilePicker()
                        .withActivity(GestionBD.this)
                        .withRequestCode(100)
                        .withFilter(Pattern.compile(".*\\.mp3$"))
                        .withHiddenFiles(true) // Show hidden files and folders
                        .start();
            }
        });

        btn_valider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mot = e_mot.getText()+"";
                traduction = e_traduction.getText()+"";
                categorie = e_categorie.getText()+"";
                urlImage = e_urlImage.getText()+"";

                if ( mot == "" || traduction == "" || categorie == "" || imagePath == "" || sonPath == ""){
                    Toast.makeText(GestionBD.this, "Veuillez remplir les champs !", Toast.LENGTH_SHORT).show();
                }else{
                    //tout est rempli on insert dans la base de données
                        db_manager.insert(mot, traduction, categorie, urlImage, imagePath, sonPath);
                    Toast.makeText(GestionBD.this, "Insertion réussie", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 10 && resultCode == RESULT_OK) {
            imagePath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            Toast.makeText(this, imagePath, Toast.LENGTH_SHORT).show();
        }
        if (requestCode == 100 && resultCode == RESULT_OK){
            sonPath = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);
            //db_manager.insert("koko", "roi de la jungle", "animaux", "default",sonPath,"default");
            Toast.makeText(this, sonPath, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 101:{
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, "Permission accordée", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permission non accordée", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


}
