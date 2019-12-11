package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;


public class ApprendreActivity extends AppCompatActivity {
    private ListView lv ;
    private String[] listCategorie;
    private DataBaseManager db;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apprendre);
        db = new DataBaseManager(this);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);


        lv = (ListView) findViewById(R.id.listView);
        //Mot m1 = new Mot("chat", "cat", "Animaux","dd","dd","fsq" );
        //Mot m2 = new Mot("Fraise", "Strawberry", "Fruits et Legumes","dd","dd","fsq" );

        List<String> categories = db.readCategories();
        listCategorie = new String[categories.size()];

        //recuperation des categories dans un tableau
        for (int i = 0; i < categories.size() ; i++) {
            listCategorie[i] = categories.get(i);
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listCategorie);
        lv.setAdapter(adapter);




        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                show(adapter.getItem(position));
            }
        });


    }

    public void show(String s){
        Intent iii = new Intent(this, VocabularyActivity.class);
        iii.putExtra("categorie", s);
        startActivity(iii);
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
        }
        return true;
    }

}

