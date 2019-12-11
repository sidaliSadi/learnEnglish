package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.nbsp.materialfilepicker.MaterialFilePicker;

import java.util.List;

public class VocabularyActivity extends AppCompatActivity {

    private ListView listeView;
    private Mot[] mots;
    private DataBaseManager db;
    private Button btn_creer, btn_liste;
    private EditText enom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);
        listeView = findViewById(R.id.lView);
        btn_creer = findViewById(R.id.btn_creer);
        enom = findViewById(R.id.list_name);
        btn_liste = findViewById(R.id.btn_listes);

        btn_liste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(VocabularyActivity.this, Listes.class);
                startActivity(in);
            }
        });
        btn_creer.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SparseBooleanArray sp = listeView.getCheckedItemPositions();
                if ( sp.size() == 0 ){
                    Toast.makeText(VocabularyActivity.this, "Veuillez selectionnez des mots s'ils vous plait", Toast.LENGTH_SHORT).show();
                }else{
                    Mot selectedWords[] = new Mot[sp.size()];

                    for(int i=0;i<sp.size();i++){
                        if(sp.valueAt(i)== true){
                            selectedWords[i] = (Mot) listeView.getItemAtPosition(i);
                        }
                    }
                    //maintenant on ajoute dans la table liste
                    if ( db.verifier( selectedWords[0].getCategorie() ) ){
                        for (int i = 0; i < selectedWords.length ; i++) {
                            db.insertToList(selectedWords[i].getId(),enom.getText()+"", selectedWords[i].getCategorie());
                        }
                        Toast.makeText(VocabularyActivity.this, "creation réussi", Toast.LENGTH_SHORT).show();

                    }else{
                        btn_creer.setEnabled(false);
                        Toast.makeText(VocabularyActivity.this, "creation refusée ! une liste est déja ", Toast.LENGTH_SHORT).show();
                    }
                }
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


}

