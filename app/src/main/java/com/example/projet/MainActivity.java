package com.example.projet;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar ;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnStart = findViewById(R.id.start);
        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

    }

    public void startClick (View button){
        if(button == btnStart){
            Intent ii = new Intent(this, ApprendreActivity.class);
            startActivity(ii);
        }
    }


    public void onBackPressed() {
        // 5 - Handle back click to close menu
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.acceuil:
                Intent i = new Intent(this,MainActivity.class);
                startActivity(i);
                break;
            case R.id.apprendre :
                Intent i1 = new Intent(this, ApprendreActivity.class);
                startActivity(i1);
                break;
            case R.id.quiz :
                Intent i2 = new Intent(this, PratiquerActivity.class);
                startActivity(i2);
                break;
            case R.id.lire:
                Intent i3 = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://edition.cnn.com/" ) );
                startActivity(i3);
                break;

            case R.id.ajout:
                Intent i4 = new Intent(this, GestionBD.class);
                startActivity(i4);

            case R.id.quitter:
                finish();
                break;

        }
        this.drawerLayout.closeDrawer(GravityCompat.START);

        return true;
    }



    // 1 - Configure Toolbar
    private void configureToolBar(){
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    // 2 - Configure Drawer Layout
    private void configureDrawerLayout(){
        this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    // 3 - Configure NavigationView
    private void configureNavigationView(){
        this.navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
    }


}
