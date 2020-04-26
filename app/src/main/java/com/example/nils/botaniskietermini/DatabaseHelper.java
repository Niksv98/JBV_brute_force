package com.example.nils.botaniskietermini;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "jbvData20";
    public static final String DATABASE_FILE_NAME = "jbvData_v19.sql";
    public static final String TABLE_NAME = "translations_table";
    public static final String DELOCATION = "data/data/com.example.nils.botaniskietermini/";
    public static final String COL_ID = "ID";
    public static final String COL_LV = "LV";
    public static final String COL_LV_GEND = "LV_apz";
    public static final String COL_LV_PRIO = "LV_prio";
    public static final String COL_LA = "LA";
    public static final String COL_EN = "EN";
    public static final String COL_DE = "DE";
    public static final String COL_DE_GEND = "DE_apz";
    public static final String COL_DE_PRIO = "DE_prio";
    public static final String COL_RU = "RU";
    public static final String COL_RU_GEND = "RU_apz";
    public static final String COL_RU_PRIO = "RU_prio";
    public static final String COL_WIKIlINK = "wiki_link";
    public static final String COL_GENUS = "piezimes";
    public static final String COL_IMAGE = "IMAGE";
    public static final String COL_DEF_TEZ = "def_tez";
    public static final String COL_TEZLINK = "link_tez";
    public static final String COL_INFO = "info";

    Context mContext;
    SQLiteDatabase mDatabase;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME +
                " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_LV + " TEXT, " +
                COL_LV_GEND + " TEXT, " +
                COL_LV_PRIO + " INTEGER, " +
                COL_LA + " TEXT, " +
                COL_EN + " TEXT, " +
                COL_DE + " TEXT, " +
                COL_DE_GEND + " TEXT, " +
                COL_DE_PRIO + " INTEGER, " +
                COL_RU + " TEXT, " +
                COL_RU_GEND + " TEXT, " +
                COL_RU_PRIO + " INTEGER, " +
                COL_WIKIlINK + " TEXT, " +
                COL_GENUS + " INTEGER, " +
                COL_DEF_TEZ + " TEXT, " +
                COL_TEZLINK + " TEXT, " +
                COL_INFO + " TEXT);");
       /* File file = new File();
        SQLiteDatabase.openOrCreateDatabase(file,null);*/
        /*try {
            insertFromFile(mContext);
            Toast.makeText(mContext, "Pievienošana veiksmīga", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
        }*/

        InputStream dataFile = null;
        try {
            dataFile = mContext.getAssets().open(DATABASE_FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(dataFile, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String insertLine = "";
        db.beginTransaction();
        try {
            while ((insertLine = br.readLine()) != null) {
                db.execSQL(insertLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME, null);
        onCreate(db);
    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DATABASE_NAME).getPath();
        Toast.makeText(mContext, dbPath, Toast.LENGTH_SHORT).show();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    //*no need for adding data manually*/
    public void insertData(String latvian, String latin, String english, String german, String russian, String wiki, int isGenus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_LV, latvian);
        contentValues.put(COL_LA, latin);
        contentValues.put(COL_EN, english);
        contentValues.put(COL_DE, german);
        contentValues.put(COL_RU, russian);
        contentValues.put(COL_WIKIlINK, wiki);
        contentValues.put(COL_GENUS, isGenus);
        db.insert(TABLE_NAME, null, contentValues);
        String path = db.getPath();
        Toast.makeText(mContext, "Ceļš: " + path, Toast.LENGTH_SHORT).show();
        db.close();
    }



//    !!! FUNKCIJA, AR KURU ATTELU MEGINU PARVEIDOT PAR BLOB - @NILSB
  /*  public byte[] getImageBytes(String search, String languageSearch){
        String firstLanguage;
        SQLiteDatabase db = this.getWritableDatabase();
        String query;
        Cursor cursor;
        byte[] imageBytes;

        firstLanguage = getLanguageFromGivenLanguage(languageSearch);
        if (search.equals("")) {
            query = "SELECT " + COL_IMAGE + " FROM " + TABLE_NAME + " WHERE " + firstLanguage + " LIKE '" + search + "'";
        } else {
            query = "SELECT " + COL_IMAGE + " FROM " + TABLE_NAME + " WHERE " + firstLanguage + " LIKE '%" + search.substring(0, search.length() - 1) + "%'";
        }
        cursor = db.rawQuery(query, null);
        *//*if (cursor.moveToFirst()) {
            imageBytes = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE));
        } else {
            imageBytes = null;
        }*//*
        imageBytes = cursor.getBlob(cursor.getColumnIndex(COL_IMAGE));
        return imageBytes;
//        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
    }*/


//    !!! FUNKCIJA, AR KURU PIEVIENOT ATTELU - @NILSB
    /*public void storeImageToDB(Context context){
        SQLiteDatabase db = this.getWritableDatabase();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Bitmap imageBitmmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.floksis_pic)).getBitmap();
        imageBitmmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
        byte[] image = byteArrayOutputStream.toByteArray();
        long insert = db.insert(TABLE_NAME, IMG, image);
    }*/

    public static String findLanguage;

    //not used anymore
    public String getLangToSynonims(String searcL) {
       SQLiteDatabase db = this.getWritableDatabase();
       String result = "";
       String query ="";


            query = "SELECT LV, LA, EN, DE, RU FROM " + TABLE_NAME + " WHERE " + COL_LA + " = \'" + searcL + "\' OR " + COL_EN + " = \'" + searcL + "\' OR " + COL_LV + " =\'" + searcL + "\' OR " + COL_DE + " = \'" + searcL + "\' OR " + COL_RU + " = \'" + searcL + "\'";//CONTAINS("+COL_LA+", '"+search+"') OR CONTAINS("+COL_DE+", '"+search+"') OR CONTAINS("+COL_EN+", '"+search+"') OR CONTAINS("+COL_LV+", '"+search+"') OR CONTAINS("+COL_RU+", '"+search+"')";
            Cursor cursor = db.rawQuery(query, null);

            String[] allColumnsNames = cursor.getColumnNames();
            if (cursor.moveToFirst()) {
                    for (String s : allColumnsNames) {
                        String termTemp = cursor.getString(cursor.getColumnIndex(s));
                        if(!termTemp.equals("")) {
                            result = s;

                        }

                    }
                }



        return result;
    }

    public ArrayList<TermInfo> getAllSynonims(TermInfo search) {
        ArrayList<TermInfo> result = new ArrayList<>();
        Log.d("LANG", "!"+search.getLangToFindSynonims());
        SQLiteDatabase db = this.getWritableDatabase();

        String query="";
        Cursor cursor = null;

        query = "SELECT * FROM " + TABLE_NAME + " WHERE "+search.getLangToFindSynonims()+" = \'" + search.getAllTermsInLang()[LangTerm.getLangIndex(search.getLangToFindSynonims())].getTerm() + "\' ";
        cursor = db.rawQuery(query, null);

       /* for(String s:MainActivity.languagesPriorities)
            if (!search.getAllTermsInLang()[LangTerm.getLangIndex(s)].getTerm().equals("")) {
                query = "SELECT * FROM " + TABLE_NAME + " WHERE "+s+" = \'" + search.getAllTermsInLang()[LangTerm.getLangIndex(s)].getTerm() + "\' ";
                cursor = db.rawQuery(query, null);
                if(cursor.getCount()>1)
                    break;
        }*/
       if(cursor!=null) {
           String[] allColumnsNames = cursor.getColumnNames();
           if (cursor.moveToFirst()) {
               do {
                   String[] temp = new String[14];
                   int[] prio = new int[3];
                   int i = 0;
                   for (String s : allColumnsNames) {
                       if (s.equalsIgnoreCase("LV_prio"))
                           prio[0] = cursor.getInt(cursor.getColumnIndex(s));
                       else if (s.equalsIgnoreCase("DE_prio"))
                           prio[1] = cursor.getInt(cursor.getColumnIndex(s));
                       else if (s.equalsIgnoreCase("RU_prio"))
                           prio[2] = cursor.getInt(cursor.getColumnIndex(s));
                       else {
                           temp[i] = cursor.getString(cursor.getColumnIndex(s));

                           i++;

                       }
                   }
                   boolean[] isEmptyTerms = new boolean[Languages.values().length];

                   for (int j = 0; j < Languages.values().length; j++) {
                       if (temp[LangTerm.getLangIndex(Languages.values()[j].name())].equals("")) {
                           isEmptyTerms[j] = true;
                       }
                   }
                   result.add(new TermInfo(new LangTerm[]{new LangTerm("LV", temp[1], temp[2], prio[0], isEmptyTerms[0]), new LangTerm("LA", temp[3], isEmptyTerms[1]), new LangTerm("EN", temp[4], isEmptyTerms[2]), new LangTerm("GE", temp[5], temp[6], prio[1], isEmptyTerms[3]), new LangTerm("RU", temp[7], temp[8], prio[2], isEmptyTerms[4])}, temp[9], temp[10], temp[11], temp[12], temp[13]));

               } while (cursor.moveToNext());

           }
       }
        return result;

    }

    public  ArrayList<TermAndLang> getSimilars(String search) {
        ArrayList<TermAndLang> results = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query;
        if (!search.equals("")) {

            //query = "SELECT LV, LA, EN, DE, RU FROM " + TABLE_NAME + " WHERE LOWER(" + COL_LA + ") LIKE \'%" + search + "%\' OR LOWER(" + COL_EN + ") LIKE \'%" + search + "%\' OR LOWER(" + COL_LV + ") LIKE \'%" + search + "%\' OR LOWER(" + COL_DE + ") LIKE \'%" + search + "%\' OR LOWER(" + COL_RU + ") LIKE \'%" + search + "%\'";
            query = "SELECT LV, LA, EN, DE, RU FROM " + TABLE_NAME + " WHERE " + COL_LA + " LIKE \'%" + search + "%\' OR " + COL_EN + " LIKE \'%" + search + "%\' OR " + COL_LV + " LIKE \'%" + search + "%\' OR " + COL_DE + " LIKE \'%" + search + "%\' OR " + COL_RU + " LIKE \'%" + search + "%\'";

            Cursor cursor = db.rawQuery(query, null);

            String[] allColumnsNames = cursor.getColumnNames();
            if (cursor.moveToFirst()) {
                do {

                    for (String s : allColumnsNames) {
                        String termTemp = cursor.getString(cursor.getColumnIndex(s));
                        if(termTemp.contains(search))
                            results.add(new TermAndLang(termTemp, s));

                    }
                }while (cursor.moveToNext()) ;
            }
        }
        return results;

    }

    public  ArrayList<TermInfo> getTermForImages(String search) {
        ArrayList<TermInfo> results = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query;
        if (!search.equals("")) {
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_EN + " = \'" + search + "\'";// OR " + COL_LV + " LIKE \'%" + search + "%\' OR " + COL_DE + " LIKE \'%" + search + "%\' OR " + COL_RU + " LIKE \'%" + search + "%\'";
            Cursor cursor = db.rawQuery(query, null);
            String[] allColumnsNames = cursor.getColumnNames();
            if (cursor != null) {

                if (cursor.moveToFirst()) {
                    do {
                        String[] temp = new String[14];
                        int[] prio = new int[3];
                        int i = 0;
                        for (String s : allColumnsNames) {
                            if (s.equalsIgnoreCase("LV_prio"))
                                prio[0] = cursor.getInt(cursor.getColumnIndex(s));
                            else if (s.equalsIgnoreCase("DE_prio"))
                                prio[1] = cursor.getInt(cursor.getColumnIndex(s));
                            else if (s.equalsIgnoreCase("RU_prio"))
                                prio[2] = cursor.getInt(cursor.getColumnIndex(s));
                            else {
                                temp[i] = cursor.getString(cursor.getColumnIndex(s));

                                i++;

                            }
                        }
                        boolean[] isEmptyTerms = new boolean[Languages.values().length];

                        for (int j = 0; j < Languages.values().length; j++) {
                            if (temp[LangTerm.getLangIndex(Languages.values()[j].name())].equals("")) {
                                isEmptyTerms[j] = true;
                            }
                        }
                        results.add(new TermInfo(new LangTerm[]{new LangTerm("LV", temp[1], temp[2], prio[0], isEmptyTerms[0]), new LangTerm("LA", temp[3], isEmptyTerms[1]), new LangTerm("EN", temp[4], isEmptyTerms[2]), new LangTerm("GE", temp[5], temp[6], prio[1], isEmptyTerms[3]), new LangTerm("RU", temp[7], temp[8], prio[2], isEmptyTerms[4])}, temp[9], temp[10], temp[11], temp[12], temp[13]));

                    } while (cursor.moveToNext());

                }
            }
        }
        return results;

    }

    public ArrayList<TermInfo> getTermsFromDB(String search) {
        ArrayList<TermInfo> result = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query;
        if (!search.equals("")) {
          //  query = "SELECT * FROM " + TABLE_NAME + " WHERE LOWER(" + COL_LA + ") LIKE \'%"+search+"%\' OR LOWER(" + COL_EN + ") LIKE \'%"+search+"%\' OR LOWER(" + COL_LV + ") LIKE \'%"+search+"%\' OR LOWER("+COL_DE+") LIKE \'%"+search+"%\' OR LOWER("+COL_RU+") LIKE \'%"+search+ "%\' " ;
            query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_LA + " LIKE \'%"+search+"%\' OR " + COL_EN + " LIKE \'%"+search+"%\' OR " + COL_LV + " LIKE \'%"+search+"%\' OR "+COL_DE+" LIKE \'%"+search+"%\' OR "+COL_RU+" LIKE \'%"+search+ "%\' " ;

           // Log.d("query",search+" "+ query );
            Cursor cursor = db.rawQuery(query, null);
            String[] allColumnsNames = cursor.getColumnNames();
            boolean isFoundExact = false;
            if (cursor.moveToFirst()) {
                do {

                    String[] temp = new String[14];
                    int[] prio = new int[3];
                    int i = 0;
                    for (String s : allColumnsNames) {
                        if (s.equalsIgnoreCase("LV_prio"))
                            prio[0] = cursor.getInt(cursor.getColumnIndex(s));
                        else if (s.equalsIgnoreCase("DE_prio"))
                            prio[1] = cursor.getInt(cursor.getColumnIndex(s));
                        else if (s.equalsIgnoreCase("RU_prio"))
                            prio[2] = cursor.getInt(cursor.getColumnIndex(s));
                        else {
                            temp[i] = cursor.getString(cursor.getColumnIndex(s));
                            if(temp[i].equalsIgnoreCase(search))
                            {
                                findLanguage = s;
                                isFoundExact = true;
                            }
                            i++;
                        }
                    }
                    boolean[] isEmptyTerms = new boolean[Languages.values().length];

                    for (int j = 0; j < Languages.values().length; j++) {
                        if (temp[LangTerm.getLangIndex(Languages.values()[j].name())].equals("")) {
                            isEmptyTerms[j] = true;
                        }
                    }
                    if(isFoundExact)
                    {
                        result.add(0, new TermInfo(new LangTerm[]{new LangTerm("LV", temp[1], temp[2], prio[0], isEmptyTerms[0]), new LangTerm("LA", temp[3], isEmptyTerms[1]), new LangTerm("EN", temp[4], isEmptyTerms[2]), new LangTerm("GE", temp[5], temp[6], prio[1], isEmptyTerms[3]), new LangTerm("RU", temp[7], temp[8], prio[2], isEmptyTerms[4])}, temp[9], temp[10], temp[11], temp[12], temp[13]));
                        isFoundExact = false;
                    }
                    else
                        result.add(new TermInfo(new LangTerm[]{new LangTerm("LV", temp[1], temp[2], prio[0], isEmptyTerms[0]), new LangTerm("LA", temp[3], isEmptyTerms[1]), new LangTerm("EN", temp[4], isEmptyTerms[2]), new LangTerm("GE", temp[5], temp[6], prio[1], isEmptyTerms[3]), new LangTerm("RU", temp[7], temp[8], prio[2], isEmptyTerms[4])}, temp[9], temp[10], temp[11], temp[12], temp[13]));   } while (cursor.moveToNext());

            } while (cursor.moveToNext());

            }

        return result;

    }

    public ArrayList<TermForSemantic> getTermsFromDBForSemanticSearh(String search) {
        ArrayList<TermForSemantic> result = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        if (!search.equals("")) {
            String query = "SELECT LA, piezimes  FROM " + TABLE_NAME + " WHERE " + COL_LA +" LIKE \'"+search+"%\'";// OR " + COL_EN + " LIKE \'%"+search+"%\' OR " + COL_LV + " LIKE \'%"+search+"%\' OR "+COL_DE+" LIKE \'%"+search+"%\' OR "+COL_RU+" LIKE \'%"+search+ "%\' " ;
            Cursor cursor = db.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {

                    result.add(new TermForSemantic(cursor.getString(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        }
        return result;
    }

    public String getNoteFromDB(String search) {
        String result = "";

        SQLiteDatabase db = this.getWritableDatabase();

        String query;
        if (!search.equals("")) {
            query = "SELECT piezimes FROM " + TABLE_NAME + " WHERE " + COL_LA + " = \'" + search + "\' OR " + COL_EN + " = \'" + search + "\' OR " + COL_LV + " = \'" + search + "\' OR " + COL_DE + " = \'" + search + "\' OR " + COL_RU + " = \'" + search + "\'";//CONTAINS("+COL_LA+", '"+search+"') OR CONTAINS("+COL_DE+", '"+search+"') OR CONTAINS("+COL_EN+", '"+search+"') OR CONTAINS("+COL_LV+", '"+search+"') OR CONTAINS("+COL_RU+", '"+search+"')";

            //query = "SELECT piezimes FROM " + TABLE_NAME + " WHERE " + COL_LA + " LIKE \'%" + search + "%\' OR " + COL_EN + " LIKE \'%" + search + "%\' OR " + COL_LV + " LIKE \'%" + search + "%\' OR " + COL_DE + " LIKE \'%" + search + "%\' OR " + COL_RU + " LIKE \'%" + search + "%\'";//CONTAINS("+COL_LA+", '"+search+"') OR CONTAINS("+COL_DE+", '"+search+"') OR CONTAINS("+COL_EN+", '"+search+"') OR CONTAINS("+COL_LV+", '"+search+"') OR CONTAINS("+COL_RU+", '"+search+"')";
            Cursor cursor = db.rawQuery(query, null);
            Log.d("QUERY", query);
            if (cursor.moveToFirst()) {
                do {
                    result = cursor.getString(0);
                } while (cursor.moveToNext());
            }
        }
        return result;

    }

}
