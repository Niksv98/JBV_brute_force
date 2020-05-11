package com.example.nils.botaniskietermini;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

//class for data adding manually
public class TranslationAdd extends AppCompatActivity {

    DatabaseHelper myDB;

    private EditText lv;
    private EditText la;
    private EditText en;
    private EditText de;
    private EditText ru;
    private EditText wiki;
    private CheckBox genus;
    private Button addB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation_add);
        myDB = new DatabaseHelper(this);

        lv = (EditText) findViewById(R.id.lvInput);
        la = (EditText) findViewById(R.id.laInput);
        en = (EditText) findViewById(R.id.enInput);
        de = (EditText) findViewById(R.id.deInput);
        ru = (EditText) findViewById(R.id.ruInput);
        wiki = (EditText) findViewById(R.id.wikiInput);
        genus = (CheckBox) findViewById(R.id.genusCheckBox);
        addB = (Button) findViewById(R.id.addDataButton);
    }


    //* no need for addind data manually*/
    public void addData(View view){
      /*  int genusSelected = 0;
        if (genus.isChecked())
            genusSelected = 1;
        else
            genusSelected = 0;
        myDB.insertData(
                lv.getText().toString(),
                la.getText().toString(),
                en.getText().toString(),
                de.getText().toString(),
                ru.getText().toString(),
                wiki.getText().toString(),
                genusSelected);
        lv.setText("");
        la.setText("");
        en.setText("");
        de.setText("");
        ru.setText("");
        wiki.setText("https://en.wikipedia.org/wiki/");*/
    }

    public void deleteData(View view){
//        myDB.deleteData();
    }
}
