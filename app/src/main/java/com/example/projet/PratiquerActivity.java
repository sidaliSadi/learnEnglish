package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class PratiquerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv;
    private String[] listCategorie;
    private DataBaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pratiquer);
        db = new DataBaseManager(this);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        lv = findViewById(R.id.listView);



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
        Intent iii = new Intent(this, QuizActivity.class);
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
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);

            case R.id.maList:
                Intent i1 = new Intent(this,Listes.class);
                startActivity(i1);
                break;
        }
        return true;
    }
}
