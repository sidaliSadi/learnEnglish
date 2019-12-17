package com.example.projet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DataBaseManager extends SQLiteOpenHelper {
    private static final String DB_name = "Dic.db";
    private static final  int VERSION = 4;

     public DataBaseManager (Context context){
         super(context, DB_name, null, VERSION);
     }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String strSql = "create table dictionnaire ("
                       +"id integer primary key autoincrement,"
                       +"mot text not null,"
                       +"traduction text not null,"
                        +"categorie text not null,"
                        +"imageWeb text,"
                        +"imageLocal text,"
                        +"son text"
                        +")";
        db.execSQL(strSql);
        Log.i("DATABASE" , "OnCreate invoked");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String tmp = "create table tmp ("
                +"id integer primary key autoincrement,"
                +"mot text not null,"
                +"traduction text not null,"
                +"categorie text not null,"
                +"imageWeb text,"
                +"imageLocal text"
                +")";
        db.execSQL(tmp);
        String insertion = "insert into tmp select id,mot,traduction,categorie,imageWeb,imageLocal from dictionnaire";
        db.execSQL(insertion);
        db.execSQL("drop table dictionnaire");
        db.execSQL("alter table tmp rename to dictionnaire");
         //Ajout de la table liste

    }

    public void insert(String mot, String traduction, String categorie, String imgWeb, String imgLocal){
         String insertSql = "insert into dictionnaire (mot, traduction, categorie, imageWeb, imageLocal) values ('" +
                 mot +"', '"+ traduction +"', '"+ categorie+"', '"+imgWeb+"','"+imgLocal+"')";
         this.getWritableDatabase().execSQL(insertSql);
        Log.i("DATABASE" , "insert invoked");
    }

    //inserer un mot dans la liste
    public void insertToList(int id_mot, String nom_liste, String categorie){
        String insertSql = "insert into liste(id_mot, nom_list, categorie) values ('" +
                id_mot +"', '"+ nom_liste+"','"+categorie+"' "+")";

        this.getWritableDatabase().execSQL(insertSql);
        Log.i("DATABASE" , "insert invoked");
    }

    public List<String> readCategories(){
         List<String> categories = new ArrayList<>();

        String sql = "SELECT DISTINCT categorie FROM dictionnaire";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
            String cat = cursor.getString(0);
            categories.add(cat);
            cursor.moveToNext();
        }
        cursor.close();
        return categories;
    }

    public List<Mot> readMots(String cat){
        List<Mot> mots = new ArrayList<>();

        String sql = "SELECT * FROM dictionnaire WHERE categorie = "+"?";
        String params[] = {cat};
        Cursor cursor = this.getReadableDatabase().rawQuery(sql,params);
        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
            Mot m = new Mot(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
            mots.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return mots;
    }

    //verifier si une liste de mot d'une certaine categorie à été ajouté !
    public boolean verifier(String categorie_l){
         String sql = "Select * from liste where categorie = "+"?";
         String p[] = {categorie_l};
         Cursor c = this.getReadableDatabase().rawQuery(sql, p);
         c.moveToFirst();
         if ( c.isAfterLast() ){
             //cette categorie ne contient pas encore une liste
             return true;
         }else{
             return false;
         }
    }

    //selectionner les listes que l'utilisateur à construit
    public List<String> readListes(){
        List<String> listes = new ArrayList<>();

        String sql = "SELECT DISTINCT categorie,nom_list FROM liste";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, null);
        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
            String nom_liste = cursor.getString(1);
            listes.add(nom_liste);
            cursor.moveToNext();
        }
        cursor.close();
        return listes;
    }

    //selectionner les mots de la liste
    public List<Mot> readListMots(String nom){
        List<Mot> mots = new ArrayList<>();
        String MY_QUERY = "SELECT * FROM table_a a INNER JOIN table_b b ON a.id=b.other_id WHERE b.property_id=?";

        String sql = "SELECT * FROM liste inner join dictionnaire on liste.id_mot=dictionnaire.id where nom_list = "+"?";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, new String[]{nom});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Mot m = new Mot(cursor.getInt(0), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),cursor.getString(9));
            mots.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return mots;
    }

    //Une methode de recherche
    public List<Mot> Mots(String mot){
        List<Mot> mots = new ArrayList<>();
        String sql = "SELECT * FROM dictionnaire where mot= "+"?";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, new String[]{mot});
        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
            Mot m = new Mot(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5));
            mots.add(m);
            cursor.moveToNext();
        }
        cursor.close();
        return mots;
    }

    //supprimer une traduction
    public boolean supprimerTraduction(String m, int identifiant){
         //on test si le mot se retrouve dans la table liste
        this.getWritableDatabase().delete("liste","id_mot="+identifiant,null);
         return this.getWritableDatabase().delete("dictionnaire", "mot"+"=+?", new String[]{m}) > 0;
    }

    //modifier une ligne
    public boolean supprimerImage(String m){
         ContentValues cv = new ContentValues();
         cv.put("imageLocal", "default");
        return this.getWritableDatabase().update("dictionnaire",cv,"mot=?",new String[]{m}) > 0;
     }

    //supprimer une liste
    public boolean supprimerListe(String m){
        return this.getWritableDatabase().delete("liste", "nom_list"+"=+?", new String[]{m}) > 0;
    }

    //au hasard Question quiz
    public Mot quizQuestion(String c){
        Mot m ;
        String sql = "SELECT * FROM dictionnaire where categorie = "+"?"+"order by random() limit 1";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, new String[]{c});
        cursor.moveToFirst();
        m = new Mot(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),cursor.getString(5));
        cursor.close();
        return m;
    }

    //Une methode de recherche
    public List<String> quizOptions(String c){
        List<String> mots = new ArrayList<>();
        String sql = "SELECT traduction FROM dictionnaire where categorie = "+"?"+"order by random() limit 3";
        Cursor cursor = this.getReadableDatabase().rawQuery(sql, new String[]{c});
        cursor.moveToFirst();
        while (! cursor.isAfterLast()){
             mots.add(cursor.getString(0));
            cursor.moveToNext();
        }
        cursor.close();
        return mots;
    }


}
