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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class QuizActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String[] m;
    private ListView lv ;
    private DataBaseManager db;
    private Button btnSuivant;
    private static ImageView imgView;
    private TextView qst;
    private RadioGroup rg;
    private RadioButton rb_selected, reponse1, reponse2, reponse3, reponse4;
    private int score = 0, nbrRepition = 4;
    private String c;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        btnSuivant = findViewById(R.id.btnSuivant);
        imgView = findViewById(R.id.imageView);
        rg = findViewById(R.id.radioGroup);
        qst = findViewById(R.id.question);

        toolbar = findViewById(R.id.toolbar1);
        toolbar.setTitle("QUIZZ");
        setSupportActionBar(toolbar);
        /******************************************************************************/

        Intent i = getIntent();
        c = i.getStringExtra("categorie");

        reponse1 = findViewById(R.id.reponse1);
        reponse2 = findViewById(R.id.reponse2);
        reponse3 = findViewById(R.id.reponse3);
        reponse4 = findViewById(R.id.reponse4);
        db = new DataBaseManager(QuizActivity.this);

        nextTurn();




    }

    public void nextTurn(){



        final Mot m = db.quizQuestion(c);
        List<String> options = new ArrayList<String>();
        options =  db.quizOptions(c);
        options.add(m.getTraduction());

        if ( m.getImgLocal().equals("default") ){
            //affichage idu mot
            qst.setText(m.getMot()+" == ..........");
        }else {
            qst.setText("");
            File imgFile = new File(m.getImgLocal());

            if (imgFile.exists()) {

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

               imgView.setImageBitmap(myBitmap);
            }else{
                Toast.makeText(this, m.getMot(), Toast.LENGTH_SHORT).show();
            }




        }
        Collections.shuffle(options);
        reponse1.setText(options.get(0));
        reponse2.setText(options.get(1));
        reponse3.setText(options.get(2));
        reponse4.setText(options.get(3));
        btnSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rbId = rg.getCheckedRadioButtonId();
                rb_selected = findViewById(rbId);

                if ( nbrRepition == 0 ){
                    if ( m.getTraduction().equals(rb_selected.getText()) ){
                        score = score + 1;
                    }
                    Toast.makeText(QuizActivity.this, "Score est "+score, Toast.LENGTH_SHORT).show();
                    btnSuivant.setEnabled(false);
                }else{
                    nbrRepition--;
                    if ( m.getTraduction().equals(rb_selected.getText()) ){
                        score = score + 1;
                    }
                    nextTurn();
                }
            }
        });
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
