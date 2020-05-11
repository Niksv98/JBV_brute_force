package com.example.nils.botaniskietermini;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SemanticSearch extends AppCompatActivity {

    public DatabaseHelper myDB;
    private EditText searchTextField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sematic_search);
        myDB = new DatabaseHelper(this);
        // this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        searchTextField = (EditText) findViewById(R.id.searchName);

        searchTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchTextField.isFocused()) {
                    searchTextField.setCursorVisible(true);
                    searchTextField.setText("");
                }
            }
        });

       // ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layoutForDrawing);
        //cl.removeAllViews();
        //DrawThing draw=new DrawThing(this);
        //setContentView(draw);

    }
    public void hideKeyboard(View view) {
        view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            searchTextField.setCursorVisible(false);
        }
    }
    public void searchButtonClicked(View view) {
        hideKeyboard(view);
        LinearLayout ll = (LinearLayout) findViewById(R.id.layoutForInfo);
        ll.removeAllViewsInLayout();
        String searchTerm = searchTextField.getText().toString();
        String[] splitted = searchTerm.split(" ");
        ArrayList<TermForSemantic> terms = null;
        if(splitted.length>1)
            terms = myDB.getTermsFromDBForSemanticSearh(splitted[0]);
        else
            terms = myDB.getTermsFromDBForSemanticSearh(searchTerm);
        if(terms.size()>0) {
            TermForSemantic.setRes(getResources());

            TermForSemantic theFirst = null;
            for (int i = 0; i < terms.size(); i++) {
                terms.get(i).setColor();
                if (splitted[0].equals(terms.get(i).getTerm())) {
                    theFirst = terms.get(i);
                }
            }


            ArrayList<TermForSemantic> termsToDraw = new ArrayList<>();
            termsToDraw.add(null);
            termsToDraw.add(theFirst);
            termsToDraw.add(null);
            terms.remove(theFirst);
            if (terms.size() == 1) {
                termsToDraw.add(new TermForSemantic("right"));
                termsToDraw.add(null);
                termsToDraw.add(null);

                termsToDraw.add(terms.get(0));
                termsToDraw.add(null);
                termsToDraw.add(null);
            }

            if (terms.size() == 2) {
                termsToDraw.add(new TermForSemantic("right"));
                termsToDraw.add(null);
                termsToDraw.add(new TermForSemantic("left"));

                termsToDraw.add(terms.get(0));
                termsToDraw.add(null);
                termsToDraw.add(terms.get(1));
            } else if (terms.size() == 3) {
                termsToDraw.add(new TermForSemantic("right"));
                termsToDraw.add(new TermForSemantic("bellow"));
                termsToDraw.add(new TermForSemantic("left"));

                termsToDraw.add(terms.get(0));
                termsToDraw.add(terms.get(1));
                termsToDraw.add(terms.get(2));
            } else if (terms.size() == 4) {
                termsToDraw.add(new TermForSemantic("right"));
                termsToDraw.add(new TermForSemantic("bellow"));
                termsToDraw.add(new TermForSemantic("left"));

                termsToDraw.add(terms.get(0));
                termsToDraw.add(terms.get(1));
                termsToDraw.add(terms.get(2));

                termsToDraw.add(0, null);
                termsToDraw.add(0, new TermForSemantic("up"));
                termsToDraw.add(0, null);
                termsToDraw.add(0, null);
                termsToDraw.add(0, terms.get(3));
                termsToDraw.add(0, null);


            }

            GridView gridView = (GridView) findViewById(R.id.gridview);
            TermAdapter termAdapter = new TermAdapter(this, termsToDraw, getResources(), searchTerm);
            gridView.setAdapter(termAdapter);


          //  LinearLayout ll = (LinearLayout) findViewById(R.id.layoutForInfo);
            TextView textView = new TextView(this);
            textView.setText(getResources().getString(R.string.semanticInfo)+"\n\n");
            ll.addView(textView);



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

            //drawTerm(terms.get(0));
            //drawTerm(terms.get(0));
            //createTermOnCanva(terms.get(0));
        /*LinearLayout cl = (LinearLayout) findViewById(R.id.layoutForInfo);
        TextView textView = new TextView(this);
        textView.setText(R.string.semanticInfo);
        cl.addView(textView);*/
        }
    }

    public int getPaintFromGenus(String genus) {

        if (genus.equalsIgnoreCase("ģints")) {
            return getResources().getColor(R.color.genusColor);
        } else if (genus.equalsIgnoreCase("apakšģints")) {
            return getResources().getColor(R.color.subgenusColor);
        } else if (genus.equalsIgnoreCase("suga")) {
            return getResources().getColor(R.color.spieciesColor);
        } else if (genus.equalsIgnoreCase("pasuga")) {
            return getResources().getColor(R.color.subspieciesColor);
        } else if (genus.equalsIgnoreCase("varietāte")) {
            return getResources().getColor(R.color.varietyColor);
        } else if (genus.equalsIgnoreCase("forma")) {
            return getResources().getColor(R.color.formColor);
        } else if (genus.equalsIgnoreCase("šķirņu grupa")) {
            return getResources().getColor(R.color.hybridColor);
        } else {
            return getResources().getColor(R.color.transparent);
        }
    }

   /* public void drawTerm(TermForSemantic term) {
        ConstraintLayout c = (ConstraintLayout) findViewById(R.id.layoutForDrawing);
        TextView textView = new TextView(this);
        textView.setHeight(400);
        // textView.setBackground(new ColorCircleDrawable(getPaintFromGenus(term.getGenus())));
        textView.setText(term.getTerm());
        c.addView(textView);


    }*/
}

class ColorCircleDrawable extends Drawable {
    private final Paint mPaint;
    private int mRadius = 0;

    public ColorCircleDrawable(final int color) {
        this.mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.mPaint.setColor(color);
    }

    @Override
    public void draw(final Canvas canvas) {
        final Rect bounds = getBounds();
        canvas.drawCircle(bounds.centerX(), bounds.centerY(), 400, mPaint);
    }

    @Override
    protected void onBoundsChange(final Rect bounds) {
        super.onBoundsChange(bounds);
        mRadius =  Math.min(bounds.width(), bounds.height()) / 2;
    }

    @Override
    public void setAlpha(final int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(final ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}

class TermAdapter extends BaseAdapter {

    private final Context mContext;
    private final ArrayList<TermForSemantic> terms;
    Resources res;
    String searchedTerm;

    public TermAdapter(Context context, ArrayList<TermForSemantic> terms, Resources res, String  searchedTerm) {
        this.mContext = context;
        this.terms = terms;
        this.res = res;
        this.searchedTerm = searchedTerm;
    }


    @Override
    public int getCount() {
        return terms.size();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public Object getItem(int position) {
        return null;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(mContext);
        TermForSemantic t = terms.get(position);

        if(terms.get(position)!= null) {

            if (terms.get(position).getTerm().equals("left")) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.left, 0, 0, 0);
                textView.setBackgroundColor(res.getColor(R.color.transparent));
            } else if (terms.get(position).getTerm().equals("right")) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.right, 0, 0, 0);
                textView.setBackgroundColor(res.getColor(R.color.transparent));
            } else if (terms.get(position).getTerm().equals("bellow")) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.bellow, 0, 0, 0);
                textView.setBackgroundColor(res.getColor(R.color.transparent));
            } else if (terms.get(position).getTerm().equals("up")) {
                textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up, 0, 0, 0);
                textView.setBackgroundColor(res.getColor(R.color.transparent));
            } else {
                textView.setGravity(Gravity.CENTER);
                if (terms.get(position).getTerm().equals(searchedTerm))
                    textView.setText(""+ terms.get(position).getTerm() + "* ");
                else
                    textView.setText(""+ terms.get(position).getTerm()+"");
                textView.setBackground(new ColorCircleDrawable(terms.get(position).getColor()));
                if(terms.get(position).getTerm().equals(res.getString(R.string.semanticInfo)))
                    textView.setTextSize(12);
                else
                    textView.setTextSize(18);
                //textView.setHeight(200);
            }
        }
        else
        {
            textView.setText(" ");

        }

        return textView;
    }

}

