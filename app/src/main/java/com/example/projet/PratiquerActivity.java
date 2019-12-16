package com.example.projet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class PratiquerActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView lv;
    private String[] listCategorie;
    private DataBaseManager db;
    private  int images[] = {R.drawable.animaux, R.drawable.fruitetlegume, R.drawable.ordinateur,
            R.drawable.internet, R.drawable.informatique,R.drawable.famille};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pratiquer);
        db = new DataBaseManager(this);
        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("Testez Vos Connaissances");
        setSupportActionBar(toolbar);

        lv = findViewById(R.id.listView);



        List<String> categories = db.readCategories();
        listCategorie = new String[categories.size()];

        //recuperation des categories dans un tableau
        for (int i = 0; i < categories.size() ; i++) {
            listCategorie[i] = categories.get(i);
        }

        final MyAdapter adapter = new MyAdapter(this, listCategorie, images);
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
                break;
            case R.id.maList:
                Intent i1 = new Intent(this,Listes.class);
                startActivity(i1);
                break;
        }
        return true;
    }


    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        int rImgs[];

        MyAdapter (Context c, String title[], int imgs[]) {
            super(c, R.layout.row, R.id.textView1, title);
            this.context = c;
            this.rTitle = title;
            this.rImgs = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView myTitle = row.findViewById(R.id.textView1);


            // now set our resources on views
            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitle[position]);


            return row;
        }
    }
}
