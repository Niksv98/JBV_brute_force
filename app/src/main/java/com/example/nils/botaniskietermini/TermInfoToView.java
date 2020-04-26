package com.example.nils.botaniskietermini;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class TermInfoToView {
    private ArrayList<LangTerm>  LA_term;
    private ArrayList<LangTerm> DE_terms;
    private ArrayList<LangTerm>  EN_term;
    private ArrayList<LangTerm> LV_terms;
    private ArrayList<LangTerm> RU_terms;
    private String wikiLink_text;
    private String tezLink_text;
    private String tezDef_text;
    private String genusForBackground;
    private String comment;

    public ArrayList<LangTerm>  getLA_term() {
        return LA_term;
    }

    public void setLA_term(ArrayList<LangTerm>  LA_term) {
        this.LA_term = LA_term;
    }

    public ArrayList<LangTerm> getDE_terms() {
        return DE_terms;
    }

    public void setDE_terms(ArrayList<LangTerm> DE_terms) {
        this.DE_terms = DE_terms;
    }

    public ArrayList<LangTerm>  getEN_term() {
        return EN_term;
    }

    public void setEN_term(ArrayList<LangTerm>  EN_term) {
        this.EN_term = EN_term;
    }

    public ArrayList<LangTerm> getLV_terms() {
        return LV_terms;
    }

    public void setLV_terms(ArrayList<LangTerm> LV_terms) {
        this.LV_terms = LV_terms;
    }

    public ArrayList<LangTerm> getRU_terms() {
        return RU_terms;
    }

    public void setRU_terms(ArrayList<LangTerm> RU_terms) {
        this.RU_terms = RU_terms;
    }

    public String getWikiLink_text() {
        return wikiLink_text;
    }

    public void setWikiLink_text(String wikiLink_text) {
        this.wikiLink_text = wikiLink_text;
    }

    public String getTezLink_text() {
        return tezLink_text;
    }

    public void setTezLink_text(String tezLink_text) {
        this.tezLink_text = tezLink_text;
    }

    public String getTezDef_text() {
        return tezDef_text;
    }

    public void setTezDef_text(String tezDef_text) {
        this.tezDef_text = tezDef_text;
    }

    public String getGenusForBackground() {
        return genusForBackground;
    }

    public void setGenusForBackground(String genusForBackground) { this.genusForBackground = genusForBackground; }

    public String getComment() { return comment; }

    public void setComment(String comment) { this.comment = comment;  }

    public String[] getAllTexts() throws NoSuchFieldException, IllegalAccessException {
         String[] allTexts= new String[this.getClass().getDeclaredFields().length];
         for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
             allTexts[i] = this.getClass().getDeclaredField(this.getClass().getDeclaredFields()[i].getName()).get(this).toString();
         }

        return allTexts;
    }

    public TermInfoToView() {
    }

    public ArrayList<LangTerm> getLangTermValuesFromTitle(String s) throws NoSuchFieldException, IllegalAccessException {
        String whichField = "";
        for (int i = 0; i < this.getClass().getDeclaredFields().length; i++) {
            if(this.getClass().getDeclaredFields()[i].getName().contains(s))
                whichField = this.getClass().getDeclaredFields()[i].getName();
        }

        return ( ArrayList<LangTerm> ) this.getClass().getDeclaredField(whichField).get(this);
    }

    public String getStringValuesFromTitle(String s) throws NoSuchFieldException, IllegalAccessException {
        if(s.equalsIgnoreCase("WIKI"))
            return ( String ) this.getClass().getDeclaredField("wikiLink_text").get(this);
        else if(s.equalsIgnoreCase("TEZAURS"))
            return ( String ) this.getClass().getDeclaredField("tezLink_text").get(this);
        else if(s.equalsIgnoreCase("T*"))
            return ( String ) this.getClass().getDeclaredField("tezDef_text").get(this);
        else if(s.equalsIgnoreCase("INFO"))
            return ( String ) this.getClass().getDeclaredField("comment").get(this);
        else
            return "";
    }

    public void setPrioLangTerm(ArrayList<LangTerm> res, String s) {
        if(s.equalsIgnoreCase("LV"))
            setLV_terms(res);
        else if (s.equalsIgnoreCase("DE"))
            setDE_terms(res);
        else if( s.equalsIgnoreCase("RU"))
            setRU_terms(res);
    }
}
