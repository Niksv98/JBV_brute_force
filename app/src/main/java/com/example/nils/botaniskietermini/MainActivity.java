package com.example.nils.botaniskietermini;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.text.util.Linkify;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static String searchTerm ="";

    public DatabaseHelper myDB;
    //need for loading all endings
    TermEndingHelper helperForEndings = new TermEndingHelper();

    private EditText searchTextField;
    private ArrayAdapter languagesAdapter;

    private String[] allCodesTitlesInView = {"LA", "DE", "EN", "LV", "RU", "WIKI", "TEZAURS", "T*", "INFO"};
    public static String[] languagesPriorities = {"LA", "LV", "EN", "DE", "RU"};
    public static String[] languagesWithPrioValue = { "LV", "DE", "RU"};
    private static String[] addLangCodes = {"(GB)","(US)","(AT)","(CH)"};
    private static ArrayList<String> allConstructionTitles = new ArrayList<String>() {{
        add("CloverLeaf");
        add("Flower");
        add("Leaf");
        add("Plant");
        add("Root");
        add("Seed");
    }};
    //TODO so var nomainit pie insatlacijas
    public static boolean isLatvian = true;

    public Spelling spellObj;
    String fileText;

    Button suggest1;
    Button suggest2;
    Button suggest3;

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        String term = getIntent().getStringExtra("term");

        myDB = new DatabaseHelper(this);

        searchTextField = (EditText) findViewById(R.id.searchName);

        if(term!=null) {
            searchTextField.setText(term);
            searchButtonClicked(getCurrentFocus());
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        }
        // languageSpinner = (Spinner) findViewById(R.id.languageSpinner);
        languagesAdapter = (ArrayAdapter.createFromResource(this, R.array.languageCodes, android.R.layout.simple_spinner_dropdown_item));

        /* uztaisit onclick listeneru, lai paraditu un neraditu mirgojoso kursoru search text fieldam.
        languagesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        languageSpinner.setAdapter(languagesAdapter);
        languageSpinner.setSelection(3);
        */
        searchTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchTextField.isFocused()) {
                    searchTextField.setCursorVisible(true);
                    searchTextField.setText("");
                }
            }
        });
        final ImageView ZoomImages = (ImageView) findViewById(R.id.image2);
        ZoomImages.setVisibility(View.INVISIBLE);
        updateCurrentContent(false);
        setTitle(R.string.appName);
        Button buttonSearch = (Button) findViewById(R.id.searchButton);
        buttonSearch.setText(getResources().getString(R.string.searchButtonText));

        //Python
//        if (! Python.isStarted()) {
//            Python.start(new AndroidPlatform(this));
//        }

        suggest1 = findViewById(R.id.suggestionButton1);
        suggest2 = findViewById(R.id.suggestionButton2);
        suggest3 = findViewById(R.id.suggestionButton3);
        suggest1.setVisibility(View.INVISIBLE);
        suggest2.setVisibility(View.INVISIBLE);
        suggest3.setVisibility(View.INVISIBLE);

        //Faila nolasisanas un vardnicas izveidosanas bloks
        try{
            InputStream is = getAssets().open("termini.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            fileText = new String(buffer);
            fileText.toLowerCase();
        } catch (IOException ex){
            ex.printStackTrace();
        }
        try {
            spellObj = new Spelling(fileText);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // paredzets datu pievienosanai (netiek izmantots)
    public void newData(View view) {
        Intent intent = new Intent(this, TranslationAdd.class);
        startActivity(intent);
    }

    /**no need for 5 searching buttons, now it is working with one search button*/
    public void searchButtonClicked(View view){
        searchLanguageButton(view);
    }
    public void suggestButton1Clicked(View view){
        searchTranslation(suggest1.getText().toString(), false);
        suggest1.setVisibility(View.INVISIBLE);
        suggest2.setVisibility(View.INVISIBLE);
        suggest3.setVisibility(View.INVISIBLE);
    }
    public void suggestButton2Clicked(View view){
        searchTranslation(suggest2.getText().toString(), false);
        suggest1.setVisibility(View.INVISIBLE);
        suggest2.setVisibility(View.INVISIBLE);
        suggest3.setVisibility(View.INVISIBLE);
    }
    public void suggestButton3Clicked(View view){
        searchTranslation(suggest3.getText().toString(), false);
        suggest1.setVisibility(View.INVISIBLE);
        suggest2.setVisibility(View.INVISIBLE);
        suggest3.setVisibility(View.INVISIBLE);
    }

    private void searchLanguageButton(View view){
        suggest1.setVisibility(View.INVISIBLE);
        suggest2.setVisibility(View.INVISIBLE);
        suggest3.setVisibility(View.INVISIBLE);

        final ImageView ZoomImages = (ImageView) findViewById(R.id.image2);
        ZoomImages.setVisibility(View.INVISIBLE);

        final ImageView pikto = (ImageView) findViewById(R.id.pictogram);
        pikto.setVisibility(View.INVISIBLE);
        String search = searchTextField.getText().toString();
        hideKeyboard(view);

        if (search.equals("")) {
            LinearLayout child = (LinearLayout)this.findViewById(R.id.linearLayout2);
            fillWhenEmptyEntry(child);
            Toast.makeText(this, getResources().getText(R.string.ToastInsertTerm), Toast.LENGTH_SHORT).show();
        } else if (search.length() < 3) {
            LinearLayout child = (LinearLayout)this.findViewById(R.id.linearLayout2);
            fillWhenEmptyEntry(child);
            Toast.makeText(this, getResources().getText(R.string.ToastTooShort), Toast.LENGTH_SHORT).show();
        } else {
            searchTranslation(search, false);
        }
    }

    public void fillWhenEmptyEntry(LinearLayout layout) {
        if(layout != null) {
            if (layout.getChildCount() > 0)
                layout.removeAllViews();
        }
    }

    public void createInfoLayout(LinearLayout constraintLayout, boolean isColors, boolean isTezaurs) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 20, 25, 10);

        TextView textView1 = new TextView(this);
        //textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView1.setText(getResources().getString(R.string.gendersLegend2)+"\n");
        textView1.setTextSize(TypedValue.COMPLEX_UNIT_PT,7);
        textView1.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        textView1.setGravity(Gravity.CENTER);
        ll.addView(textView1);

    if(isColors) {
        TypedArray ta = getResources().obtainTypedArray(R.array.legendsColors);

        LinearLayout layoutForColors = new LinearLayout(this);
        layoutForColors.setOrientation(LinearLayout.HORIZONTAL);
        layoutForColors.setGravity(Gravity.CENTER);
        String[] infoTextForLegends = getResources().getStringArray(R.array.termBackgroundText);
        for (int i = 0; i < infoTextForLegends.length - 3; i++) {
            TextView textView3 = new TextView(this);
            textView3.setText("\t  ");
            int temp = ta.getColor(i, 0);
            textView3.setBackgroundColor(temp);
            layoutForColors.addView(textView3);

            TextView textView4 = new TextView(this);
            textView4.setText(" - " + infoTextForLegends[i] + "");
            textView4.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
            textView4.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            layoutForColors.addView(textView4);
        }
        ll.addView(layoutForColors);

        layoutForColors = new LinearLayout(this);
        layoutForColors.setOrientation(LinearLayout.HORIZONTAL);
        layoutForColors.setGravity(Gravity.CENTER);

        for (int i = infoTextForLegends.length - 3; i < infoTextForLegends.length; i++) {
            TextView textView3 = new TextView(this);
            textView3.setText("\t  ");
            int temp = ta.getColor(i, 0);
            textView3.setBackgroundColor(temp);
            layoutForColors.addView(textView3);

            TextView textView4 = new TextView(this);
            textView4.setText(" - " + infoTextForLegends[i] + " ");
            textView4.setTextSize(TypedValue.COMPLEX_UNIT_PT, 6);
            textView4.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
            layoutForColors.addView(textView4);
        }
        ll.addView(layoutForColors);
    }

    if(isTezaurs) {
        TextView textView2 = new TextView(this);
        textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        textView2.setText("\n"+ getResources().getString(R.string.definitionTextTezaurs));//T* - definīcija no www.tezaurs.lv (definīcijas atlasītas automātiski un var būt neprecīzas)");
        textView2.setGravity(Gravity.CENTER);
        textView2.setTextSize(TypedValue.COMPLEX_UNIT_PT, 7);
        textView2.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        textView2.setGravity(Gravity.CENTER);
        ll.addView(textView2);
    }

    constraintLayout.addView(ll);
}

    public void addTextViewsInLayout(TermInfoToView toView, LinearLayout linearLayout) throws NoSuchFieldException, IllegalAccessException {
        float largeChars = 13;
        float smallChars = 10;
        float linkChars = 7;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(25, 20, 25, 10);
        int counter = 0;

        for(String t: allCodesTitlesInView)
        {
            if(LangTerm.getLangIndex(t) != -1)
            {
                ArrayList<LangTerm> terms = toView.getLangTermValuesFromTitle(t);
                if(terms.size()!=0)
                {
                    LinearLayout ll = new LinearLayout(this);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    TextView textView1 = new TextView(this);
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView1.setText(t + ":\t  ");
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_PT, largeChars);
                    if(t.equalsIgnoreCase("LA")) {
                        //Log.d("is italic", "is italic" + t);
                        textView1.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                        //textView1.setTypeface(Typeface.create(textView1.getTypeface(), Typeface.ITALIC));
                    }
                    ll.addView(textView1);

                    ArrayList<LangTerm> langTerms = toView.getLangTermValuesFromTitle(t);
                    String res = "";
                    LinearLayout lSynonims = new LinearLayout(this);
                    lSynonims.setOrientation(LinearLayout.VERTICAL);


                    for(LangTerm l:langTerms) {


                        TextView textViewForTerms = new TextView(this);
                        SpannableString styledString = null;
                        String record = "";
                        if(langTerms.size()==1 || langTerms.indexOf((LangTerm) l) == langTerms.size()-1)
                            record= l.getTerm() + " " + l.getTerm_gend();
                        else
                            record = l.getTerm() + " " + l.getTerm_gend()+", ";
                        styledString = new SpannableString(record);

                        String[] cuttedString = l.getTerm().split("(?=((\\([A-Z]{2}\\))))");

                        if(cuttedString.length>1) {
                            for (int i = 1; i < cuttedString.length; i++) {
                                int indexOf = l.getTerm().indexOf(cuttedString[i]);

                                ClickableSpan clickableSpan = new ClickableSpan() {
                                    @Override
                                    public void onClick(View textView) {
                                        AlertDialog alertDialog = new AlertDialog(MainActivity.this) {

                                            @Override
                                            public boolean dispatchTouchEvent(MotionEvent event) {
                                                dismiss();
                                                return false;
                                            }
                                        };
                                        //  alertDialog.setTitle("Valsts kodi");
                                        //alertDialog.setMessage("AT – Austrijā AT – in Austria\n CH - Šveicē CH – in Switzerland\n GB - Lielbritānijā GB – in Great Britain\n US – Amerikas Savienotajās Valstīs US – in United States of America");
                                        alertDialog.setMessage(getResources().getString(R.string.countryCodeTitles));
                                        String allCountryCodes = getResources().getString(R.string.countryCodes);

                                        alertDialog.setMessage(allCountryCodes);
                                        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.selectedTabText)));
                                        alertDialog.setCanceledOnTouchOutside(true);

                                        alertDialog.show();

                                    }
                                };
                                styledString.setSpan(clickableSpan, indexOf, (indexOf + 4), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                textViewForTerms.setMovementMethod(LinkMovementMethod.getInstance());
                            }
                        }


                        if(t.equalsIgnoreCase(DatabaseHelper.findLanguage) && (l.getTerm().contains(searchTerm) || searchTerm.contains(l.getTerm()) ))
                            styledString.setSpan(new BackgroundColorSpan(setGenusBackgroundToTransLTextViewSpanString(toView.getGenusForBackground())) , 0, l.getTerm().length(), 0);
                        else
                            styledString.setSpan(new StyleSpan(Typeface.NORMAL) , 0, l.getTerm().length(), 0);

                        if(l.getTerm_gend()!="")
                            styledString.setSpan(new StyleSpan(Typeface.ITALIC), l.getTerm().length()+1, record.length(), 0);

                        textViewForTerms.setText(styledString);
                        textViewForTerms.setTextSize(TypedValue.COMPLEX_UNIT_PT, smallChars);
                        if(l.getTitle().equals("LA"))
                            textViewForTerms.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
                        textViewForTerms.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                        lSynonims.addView(textViewForTerms);
                    }
                    ll.addView(lSynonims);

                    linearLayout.addView(ll);
                }
            }
            else
            {
                String res = toView.getStringValuesFromTitle(t);
                if(!res.isEmpty())
                {
                    LinearLayout ll = new LinearLayout(this);
                    ll.setOrientation(LinearLayout.HORIZONTAL);
                    TextView textView1 = new TextView(this);
                    textView1.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView1.setText(t + ":\t  ");
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_PT, smallChars - 1);

                    ll.addView(textView1);

                    TextView textView2 = new TextView(this);
                    textView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    textView2.setText(toView.getStringValuesFromTitle(t));

                    textView2.setTextSize(TypedValue.COMPLEX_UNIT_PT,smallChars);
                    if(t.equals("WIKI") || t.equals("TEZAURS")) {
                        textView2.setAutoLinkMask(Linkify.WEB_URLS);
                        textView2.setMovementMethod(LinkMovementMethod.getInstance());
                        textView2.setClickable(true);
                        textView2.setTextSize(TypedValue.COMPLEX_UNIT_PT,linkChars);

                    }
                    else
                        textView2.setTextSize(TypedValue.COMPLEX_UNIT_PT,smallChars-2);

                    ll.addView(textView2);

                    linearLayout.addView(ll);
                }
            }
            counter++;
        }
    }

    public void addPictogramToPictures(final String term, final String termEn) {
        final Intent[] intent = {null};
        final Context con = this;
        final boolean[] isTwoImageForOneTerm = {false};
        final String imageName = myDB.getNoteFromDB(term);
        if(allConstructionTitles.contains(imageName)) {
            final String nameOfClass[] = new String[1];
            ImageView picto = (ImageView) findViewById(R.id.pictogram);
            picto.setVisibility(View.VISIBLE);

            picto.setOnClickListener(new View.OnClickListener() { //lai izsauktu klasi
                public void onClick(View v) {
                    try {
                         if(termEn.equals("petiole")) {
                            isTwoImageForOneTerm[0] = true;
                            AlertDialog.Builder builder = new AlertDialog.Builder(con);
                            builder.setTitle(getResources().getText(R.string.ConstructionImageSelection));
                            final String[] leafAndCompundLeafName = new String[2];
                            leafAndCompundLeafName[0] = (getResources().getText(R.string.constructionOfSimpleLeaf).toString());
                            leafAndCompundLeafName[1] = (getResources().getText(R.string.constructionOfCompoundLeaf).toString());

                            builder.setItems(leafAndCompundLeafName, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                      /*  if(which == 1) nameOfClass[0] = "CloverLeaf";
                                        else nameOfClass[0] =  "Leaf";
                                        Log.d("nameOfClass", ":"+nameOfClass[0]);*/
                                      try {


                                          if (which == 0)
                                              intent[0] = new Intent(getApplicationContext(), Class.forName("com.example.nils.botaniskietermini.ConstructionOfLeaf"));
                                          else
                                              intent[0] = new Intent(getApplicationContext(), Class.forName("com.example.nils.botaniskietermini.ConstructionOfCloverLeaf"));

                                          intent[0].putExtra("term", termEn);
                                          startActivity(intent[0]);
                                      }
                                      catch (Exception ex)
                                      {

                                      }
                                }
                            });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }

                        else {
                             intent[0] = new Intent(getApplicationContext(), Class.forName("com.example.nils.botaniskietermini.ConstructionOf" + imageName));

                             intent[0].putExtra("term", termEn);
                             startActivity(intent[0]);
                         }
                    } catch (ClassNotFoundException e) {//|IllegalAccessException |InstantiationException e) {
                        e.printStackTrace();

                    }
                }
            });
        }
    }

    public int setGenusBackgroundToTransLTextViewSpanString(String genus){
        if (genus.equalsIgnoreCase("ģints")) {
            return getResources().getColor(R.color.genusColor);
        }else if (genus.equalsIgnoreCase("apakšģints")){
            return getResources().getColor(R.color.subgenusColor);
        }else if (genus.equalsIgnoreCase("suga")){
            return getResources().getColor(R.color.spieciesColor); }
        else if (genus.equalsIgnoreCase("pasuga")) {
            return getResources().getColor(R.color.subspieciesColor);
        }else if (genus.equalsIgnoreCase("varietāte")){
            return getResources().getColor(R.color.varietyColor);
        }else if (genus.equalsIgnoreCase("forma")){
            return getResources().getColor(R.color.formColor);
        }else if (genus.equalsIgnoreCase("šķirņu grupa")){
            return getResources().getColor(R.color.hybridColor);
        }
        else {
            return getResources().getColor(R.color.transparent);
        }
    }

    public void searchTranslation(final String search, boolean again) {

            ArrayList<TermAndLang> allSimilarsList = new ArrayList<>();
            ArrayList<String> allEndings = TermEndingHelper.getEndingsFromTerm(search);
            for (String s : allEndings) {
                allSimilarsList.addAll(myDB.getSimilars(s));
            }

            final ArrayList<TermAndLang> allSimilarsWithoutDuplicatesList = new ArrayList<>();

            for (int i = 0; i < allSimilarsList.size(); i++) {
                if (!allSimilarsWithoutDuplicatesList.contains(allSimilarsList.get(i))) {
                    allSimilarsWithoutDuplicatesList.add(allSimilarsList.get(i));
                }
            }
            String[] allSimArray = new String[allSimilarsWithoutDuplicatesList.size()];

            for (int i = 0; i < allSimilarsWithoutDuplicatesList.size(); i++) {
                allSimArray[i] = allSimilarsWithoutDuplicatesList.get(i).getTerm() + "; " + allSimilarsWithoutDuplicatesList.get(i).getLang();
            }

            if (allSimArray.length > 1) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getResources().getText(R.string.entries));
                builder.setItems(allSimArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String searchInDB = allSimilarsWithoutDuplicatesList.get(which).getTerm();
                        searchTerm = searchInDB;
                        startSearchingForTerm(searchInDB);
                    }

                });

                AlertDialog dialog = builder.create();
                dialog.show();

            } else if (allSimArray.length == 1) {
                searchTerm = allSimilarsList.get(0).getTerm();

                startSearchingForTerm(allSimilarsList.get(0).getTerm());
            } else {
                LinearLayout child = (LinearLayout) this.findViewById(R.id.linearLayout2);
                fillWhenEmptyEntry(child);
                if(!again) {
                    final String searchUpdated = Character.isUpperCase(search.charAt(0)) ? Character.toLowerCase(search.charAt(0)) + search.substring(1) : Character.toUpperCase(search.charAt(0)) + search.substring(1);
                    ArrayList<TermInfo> foundedTerms = myDB.getTermsFromDB(searchUpdated);
                    if(foundedTerms.size()>0) {
                        new AlertDialog.Builder(this).setTitle(getResources().getString(R.string.ToastIsItRightTerm))
                                .setMessage(searchUpdated)
                                .setPositiveButton(getResources().getString(R.string.yes),
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                searchTranslation(searchUpdated, true);
                                                dialog.dismiss();
                                            }
                                        })
                                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                })
                                .create()
                                .show();
                    }
                    else {
                        Toast.makeText(this, getResources().getText(R.string.ToastNotInDB), Toast.LENGTH_SHORT).show();

                        //Python block
//
//                        Python py = Python.getInstance();
//                        PyObject pyf = py.getModule("spell");   //here you put the python script name
//                        PyObject obj = pyf.callAttr("test",search);    //here you put the function definition name
//                        PyObject obj2 = pyf.callAttr("test(2)");
//                        PyObject obj3 = pyf.callAttr("test(3)");

                        ArrayList<String> results = spellObj.correct(search);

                        if(results.size() > 0){
                            suggest1.setText(results.get(0));
                            suggest1.setVisibility(View.VISIBLE);
                            if(results.size() > 1) {
                                suggest2.setText(results.get(1));
                                suggest2.setVisibility(View.VISIBLE);
                                if(results.size() > 2){
                                    suggest3.setText(results.get(2));
                                    suggest3.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    }
                }
                else {
                    Toast.makeText(this, getResources().getText(R.string.ToastNotInDB), Toast.LENGTH_SHORT).show();

                    //Python block
//
//                    Python py = Python.getInstance();
//                    PyObject pyf = py.getModule("spell");   //here you put the python script name
//                    PyObject obj = pyf.callAttr("test",search);    //here you put the function definition name
//                    PyObject obj2 = pyf.callAttr("test(2)");
//                    PyObject obj3 = pyf.callAttr("test(3)");

                    ArrayList<String> results = spellObj.correct(search);

                    if(results.size() > 0){
                        suggest1.setText(results.get(0));
                        suggest1.setVisibility(View.VISIBLE);
                        if(results.size() > 1) {
                            suggest2.setText(results.get(1));
                            suggest2.setVisibility(View.VISIBLE);
                            if(results.size() > 2){
                                suggest3.setText(results.get(2));
                                suggest3.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
            }
    }

    public void startSearchingForTerm(String searchTerm) {
    ArrayList<TermInfo> foundedTerms = myDB.getTermsFromDB(searchTerm);

    if (foundedTerms.size() > 0) {
        TermInfo foundedT = foundedTerms.get(0);
        ArrayList<TermInfo> synonims = myDB.getAllSynonims(foundedT);
        TermInfoToView toView = new TermInfoToView();
        ArrayList<LangTerm> termsLA = new ArrayList<LangTerm>();
        if(!foundedT.getAllTermsInLang()[LangTerm.getLangIndex("LA")].getTerm().equalsIgnoreCase(""))
            termsLA.add(new LangTerm("LA", foundedT.getAllTermsInLang()[LangTerm.getLangIndex("LA")].getTerm(), foundedT.getAllTermsInLang()[LangTerm.getLangIndex("LA")].getTerm_gend()));
        toView.setLA_term(termsLA);

        ArrayList<LangTerm> termsEN = new ArrayList<LangTerm>();
        if(!foundedT.getAllTermsInLang()[LangTerm.getLangIndex("EN")].getTerm().equalsIgnoreCase(""))
            termsEN.add(new LangTerm("EN", foundedT.getAllTermsInLang()[LangTerm.getLangIndex("EN")].getTerm(), foundedT.getAllTermsInLang()[LangTerm.getLangIndex("EN")].getTerm_gend()));
        toView.setEN_term(termsEN);
        toView.setWikiLink_text(foundedT.getWiki_link());
        toView.setTezLink_text(foundedT.getTez_link());
        toView.setTezDef_text(foundedT.getDef_tez());
        toView.setGenusForBackground(foundedT.getGenus());
        toView.setComment(foundedT.getComment());
        for(String s:languagesWithPrioValue) {
            if(foundedT.getAllTermsInLang()[LangTerm.getLangIndex(s)].getTerm().length()>0)
                toView.setPrioLangTerm(TermInfo.getTermsByPriority(synonims, s), s);
            else
                toView.setPrioLangTerm(new ArrayList<LangTerm>(), s);
        }

        fillWhenEmptyEntry((LinearLayout) findViewById(R.id.linearLayout2));
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);

        try {
            if(termsEN.size()>0)
                addPictogramToPictures(searchTerm, termsEN.get(0).getTerm());
            addTextViewsInLayout(toView, linearLayout);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        boolean isTezaurs = (toView.getTezDef_text().equals(""))? false:true;
        //TODO pamainit algoritmu, ka nosaka, vai vajag krasas vai ne
        boolean isColors = (toView.getGenusForBackground().equals("") || (toView.getGenusForBackground().equals("Flower")) || (toView.getGenusForBackground().equals("Plant")) || (toView.getGenusForBackground().equals("CloverLeaf")) || (toView.getGenusForBackground().equals("Root")) || (toView.getGenusForBackground().equals("Seed")) || (toView.getGenusForBackground().equals("Leaf")) )? false:true;

        createInfoLayout(linearLayout, isColors, isTezaurs );
        if(termsLA.size()>0) {
            final String[] imageNameForTerm = {termsLA.get(0).getTerm()};
            if (isImageForTerm(imageNameForTerm[0])) {
                ImageView images = (ImageView) findViewById(R.id.image2);
                images.setVisibility(View.VISIBLE);
                images.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) { //zoom
                        final ImageView ZoomImages = (ImageView) findViewById(R.id.imageView4);
                        String[] termTemp = imageNameForTerm[0].split(" ");
                        if(termTemp.length > 1)
                            imageNameForTerm[0] = termTemp[0] + "_" + termTemp[1];
                        ZoomImages.setImageResource(getResources().getIdentifier(imageNameForTerm[0].toLowerCase(), "drawable", getPackageName()));
                        ZoomImages.setVisibility(View.VISIBLE);
                        ZoomImages.setOnClickListener(new View.OnClickListener() {
                            public void onClick(View v) {
                                ZoomImages.setVisibility(View.GONE);
                            }
                        });

                    }
                });
            }
        }
    } else {
        fillWhenEmptyEntry((LinearLayout) findViewById(R.id.linearLayout2));
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.ToastNotInDB), Toast.LENGTH_SHORT).show();
    }
}

    public boolean isImageForTerm(String term) {
        String[] termTemp = term.split(" ");
        if(termTemp.length > 1)
            term = termTemp[0] + "_" + termTemp[1];

        boolean result = false;

        Field[] drawables = R.drawable.class.getFields();
        for (Field f : drawables) {
            if (f.getName().toLowerCase().equalsIgnoreCase(term.toLowerCase())) {
                 result = true;
                break;
            }
        }
        return result;
    }

    public void hideKeyboard(View view) {
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            searchTextField.setCursorVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_jbv, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()){
            /*case R.id.main_screen:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;*/
            case R.id.about:
                intent = new Intent(this, AboutApp.class);
                startActivity(intent);
                break;
            case R.id.gendersTranslations:
                intent = new Intent(this, TranslationOfGenders.class);
                startActivity(intent);
                break;
            case R.id.allImages:
                intent = new Intent(this, ConstructionOfPlantAll.class);
                startActivity(intent);
                break;
            case R.id.sources:
                intent = new Intent(this, TranslationReferences.class);
                startActivity(intent);
                break;
            case R.id.publications:
                intent = new Intent(this, Publications.class);
                startActivity(intent);
                break;
            case R.id.app_manual:
                intent = new Intent(this, UserManual.class);
                startActivity(intent);
                break;
            case R.id.semanticSearch:
                intent = new Intent(this, SemanticSearch.class);
                startActivity(intent);
                break;
            case R.id.app_lang:
                SwitchLanguage();
                break;

        }
        return true;
    }

    public void updateCurrentContent(boolean needToChange) {
        String currentContent = "";
        if(needToChange)
            currentContent = (MainActivity.isLatvian)? "us":"lv";
        else
            currentContent = (MainActivity.isLatvian)? "lv":"us";
        try {
            Configuration config = new Configuration(getResources().getConfiguration());
            config.locale = new Locale(currentContent);
            getResources().updateConfiguration(config, getResources().getDisplayMetrics());
            if(needToChange)
                MainActivity.isLatvian = (MainActivity.isLatvian)? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

     public void SwitchLanguage(){
         updateCurrentContent(true);

         setTitle(R.string.appName);
         Button buttonSearch = (Button) findViewById(R.id.searchButton);
         buttonSearch.setText(getResources().getString(R.string.searchButtonText));
         invalidateOptionsMenu();
         LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout2);
         linearLayout.removeAllViews();
         ImageView pict = (ImageView) findViewById(R.id.pictogram);
         pict.setVisibility(View.INVISIBLE);
         //isLatvian = (isLatvian)? false : true;
    }

}
