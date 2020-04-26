package com.example.nils.botaniskietermini;

import android.content.res.Resources;
import android.util.Log;

public class TermForSemantic {
    private String term;
    //not used. Only sematic search by LA
    // private String lang;
    private String genus;
    private int color;
    public void setColor() {
        this.color = getPaintFromGenus(genus);
        Log.d("COLOR", this.color + ":"+ genus);
    }



    public static Resources getRes() {
        return res;
    }

    public static void setRes(Resources res) {
        TermForSemantic.res = res;
    }

    private static Resources res;

    public TermForSemantic(String term, String genus) {
        this.term = term;
        this.genus = genus;

    }

    public TermForSemantic(String term) {
        this.term = term;

    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public int getPaintFromGenus(String genus) {

        if (genus.equalsIgnoreCase("ģints")) {
            return res.getColor(R.color.genusColor);
        } else if (genus.equalsIgnoreCase("apakšģints")) {
            return res.getColor(R.color.subgenusColor);
        } else if (genus.equalsIgnoreCase("suga")) {
            return res.getColor(R.color.spieciesColor);
        } else if (genus.equalsIgnoreCase("pasuga")) {
            return res.getColor(R.color.subspieciesColor);
        } else if (genus.equalsIgnoreCase("varietāte")) {
            return res.getColor(R.color.varietyColor);
        } else if (genus.equalsIgnoreCase("forma")) {
            return res.getColor(R.color.formColor);
        } else if (genus.equalsIgnoreCase("šķirņu grupa")) {
            return res.getColor(R.color.hybridColor);
        } else {
            return res.getColor(R.color.transparent);
        }
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }


    public int getColor() {
        return color;
    }
}