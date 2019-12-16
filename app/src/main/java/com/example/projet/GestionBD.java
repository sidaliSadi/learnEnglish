package com.example.projet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.nbsp.materialfilepicker.MaterialFilePicker;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import java.util.List;

public class GestionBD extends AppCompatActivity {

    private DataBaseManager db_manager;
    private Button btn_son, btn_image, btn_valider;
    private EditText e_mot, e_traduction, e_categorie, e_urlImage;
    private String mot, traduction, categorie, imagePath = "", urlImage;
    private Toolbar toolbar;
    private Spinner spinne;
    private CheckBox web, local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.gestion_bd);
        db_manager = new DataBaseManager(this);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        /*récuperer les bouttons*/
        btn_image = (Button) findViewById(R.id.btn_img);
        btn_valider = (Button) findViewById(R.id.btn_valider);

        /*récuperer les edits text*/
        e_mot = (EditText) findViewById(R.id.mot);
        e_traduction = (EditText) findViewById(R.id.traduction);
        e_urlImage = (EditText) findViewById(R.id.imgWeb);

        web = findViewById(R.id.check_externe);
        local = findViewById(R.id.check_interne);

        //recuperation du spinner
        spinne = findViewById(R.id.categorie);
        //recuperer la liste des categories
        List<String> categorires = db_manager.readCategories();

        //ajouter dans l'adapteur
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categorires);
        spinne.setAdapter(adapter);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
        }
/**************************************************************************************************/
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
        web.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if ( isChecked ){
                    urlImage = "default";
                    Toast.makeText(GestionBD.this, "Un mot sans image externe", Toast.LENGTH_SHORT).show();
                }else{
                    urlImage = "";
                    Toast.makeText(GestionBD.this, "Un mot avec image externe", Toast.LENGTH_SHORT).show();
                }
            }}
        );

        local.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                if(isChecked){
                    btn_image.setEnabled(false);
                    imagePath = "default";
                    Toast.makeText(GestionBD.this, "Un mot sans image Locale", Toast.LENGTH_SHORT).show();

                }else{
                    btn_image.setEnabled(true);
                    imagePath = "";
                    Toast.makeText(GestionBD.this, "Un mot avec une image locale", Toast.LENGTH_SHORT).show();
                }
            }}
        );

        btn_valider.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mot = e_mot.getText()+"";
                traduction = e_traduction.getText()+"";
                categorie = spinne.getSelectedItem().toString();
                //maintenant url image
                if (urlImage == ""){
                    urlImage = e_urlImage.getText()+"";
                }

                if ( mot == "" || traduction == "" || categorie == "" || imagePath == "" || urlImage == ""){
                    Toast.makeText(GestionBD.this, "imagePath ="+imagePath+" et urlImage = "+urlImage, Toast.LENGTH_SHORT).show();

                }else{
                    //tout est rempli on insert dans la base de données
                    db_manager.insert(mot, traduction, categorie, urlImage, imagePath);
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
        }
        return true;
    }

}
