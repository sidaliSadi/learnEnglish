package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Listes extends AppCompatActivity {
private ListView lview;
private DataBaseManager db;
private String [] listes;
private TextView tv;
private Toolbar toolbar;
    ArrayAdapter<String> adapter;
    List<String> l;
    private static final int SUPPRIMER_LISTE = Menu.FIRST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);
        lview = findViewById(R.id.lView);
        tv = findViewById(R.id.tv);
        toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        db = new DataBaseManager(this);

        l = db.readListes();
        if ( l.size() == 0 ){
            tv.setText("Liste vide");
            listes = new String[]{};
        }else{
            listes = new String[l.size()];

            //recuperation des categories dans un tableau
            for (int i = 0; i < l.size(); i++) {
                listes[i] = l.get(i);
            }
        }


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listes);
        lview.setAdapter(adapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                show(adapter.getItem(position));
            }
        });
        registerForContextMenu(lview);
    }



    public void show(String s){
        Intent iii = new Intent(this, Traduction.class);
        iii.putExtra("nom_liste", s);
        startActivity(iii);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View vue, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, vue, menuInfo);
        menu.add(Menu.NONE, SUPPRIMER_LISTE, Menu.NONE, "Supprimer cette liste");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case SUPPRIMER_LISTE:
                String it = lview.getItemAtPosition(info.position).toString();
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show();
                if ( db.supprimerListe(it) ){
                    Toast.makeText(this, "Suupression reussie !", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                    return true;
                }
            default:
                return super.onContextItemSelected(item);
        }


    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
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
                break;
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
