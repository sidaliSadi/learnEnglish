package com.example.projet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String[] m;
    private ListView lv ;
    private DataBaseManager db;
    private Button btnSuivant;
    private static ImageView imgView;
    private static int i,j;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        lv = findViewById(R.id.listView);
        btnSuivant = findViewById(R.id.btnSuivant);
        imgView = findViewById(R.id.imageView);

        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("QUIZZ");
        setSupportActionBar(toolbar);




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
