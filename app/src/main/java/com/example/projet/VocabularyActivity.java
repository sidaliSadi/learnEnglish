package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.util.ArrayList;
import java.util.List;

public class VocabularyActivity extends AppCompatActivity {

    private ListView listeView;
    private Mot[] mots;
    private DataBaseManager db;
    private Button btn_creer;
    private EditText enom;
    private Toolbar toolbar;
    SparseBooleanArray sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        listeView = findViewById(R.id.lView);
        btn_creer = findViewById(R.id.btn_creer);
        enom = findViewById(R.id.list_name);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Mot à apprendre");
        setSupportActionBar(toolbar);


        btn_creer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Mot selectedWords[] = new Mot[sp.size()];
                ArrayList<Mot> selectedWords = new ArrayList<>();
                sp = listeView.getCheckedItemPositions();

                for(int i = 0; i< sp.size(); i++){
                    if ( sp.valueAt(i) ){
                        selectedWords.add( (Mot) listeView.getItemAtPosition(sp.keyAt(i)));
                    }
                }
                if ( sp.size() == 0 ){
                    Toast.makeText(VocabularyActivity.this, "Veuillez selectionnez des mots s'ils vous plait", Toast.LENGTH_SHORT).show();
                }else{




                    //maintenant on ajoute dans la table liste
                    if ( db.verifier( selectedWords.get(0).getCategorie() ) ){
                        for (int i = 0; i < selectedWords.size() ; i++) {
                            db.insertToList(selectedWords.get(i).getId(),enom.getText()+"", selectedWords.get(i).getCategorie());
                        }
                        Toast.makeText(VocabularyActivity.this, "creation réussi", Toast.LENGTH_SHORT).show();

                    }else{
                        btn_creer.setEnabled(false);
                        Toast.makeText(VocabularyActivity.this, "creation refusée ! une liste est déja ", Toast.LENGTH_SHORT).show();
                    }
                }
                sp.clear();
            }
        });

        //recuperer litem
        Intent intent = getIntent();
        String categorie = intent.getStringExtra("categorie");
        db = new DataBaseManager(this);

        //mots = new String[]{categorie, "takhna"};

        List<Mot> listMots = db.readMots(categorie);
        mots = new Mot[listMots.size()];

        //recuperation des categories dans un tableau
        for (int i = 0; i < listMots.size() ; i++) {
            mots[i] = listMots.get(i);
        }

        listeView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        final ArrayAdapter<Mot> adapter = new ArrayAdapter<Mot>(this, android.R.layout.simple_list_item_checked, mots);
        listeView.setAdapter(adapter);
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
                Intent i = new Intent(this, MainActivity.class);
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

