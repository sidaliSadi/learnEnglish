package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Listes extends AppCompatActivity {
private ListView lview;
private DataBaseManager db;
private String [] listes;
private TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listes);
        lview = findViewById(R.id.lView);
        tv = findViewById(R.id.tv);

        db = new DataBaseManager(this);

        List<String> l = db.readListes();
        if ( l.size() == 0 ){
            tv.setText("Liste vide");
        }else{
            listes = new String[l.size()];

            //recuperation des categories dans un tableau
            for (int i = 0; i < l.size(); i++) {
                listes[i] = l.get(i);
            }
        }


        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listes);
        lview.setAdapter(adapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                show(adapter.getItem(position));
            }
        });
    }
    public void show(String s){
        Intent iii = new Intent(this, Traduction.class);
        iii.putExtra("nom_liste", s);
        startActivity(iii);
    }
}
