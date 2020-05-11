package com.example.nils.botaniskietermini;

import androidx.annotation.NonNull;

public class LangTerm implements Comparable {


    private String title;
    private String term;
    private String term_gend;
    private int term_prio;
    private boolean isEmpty;


    public LangTerm(String title, String term, String term_gend) {
        this.title = title;
        this.term = term;
        this.term_gend = term_gend;
        term_prio = -1;
        this.isEmpty = isEmpty;
    }

    public LangTerm(String title, String term, boolean isEmpty) {
        this.title = title;
        this.term = term;
        term_gend = "";
        term_prio = -1;
        this.isEmpty = isEmpty;
    }

    public LangTerm(String title, String term, String term_gend, int term_prio, boolean isEmpty) {
        this.title = title;
        this.term = term;
        this.isEmpty = isEmpty;
        if(title.equalsIgnoreCase("la") || title.equalsIgnoreCase("en")) {
            this.term_gend = "";
            this.term_prio = -1;
        }
        else {
            this.term_gend = term_gend;
            this.term_prio = term_prio;
        }
    }

    public LangTerm(String title) {
        this.title = title;
        if(title.equalsIgnoreCase("la") || title.equalsIgnoreCase("en")) {
            term_gend = "";
            term_prio = -1;
        }

    }

    public static int getLangIndex(String lang) {
        int index = -1;
        for(int i = 0; i < Languages.values().length;i++)
            if(lang.equalsIgnoreCase(Languages.values()[i].name())) {
                index = i;
                break;
            }

        return  index;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getTerm_gend() {
        return term_gend;
    }

    public void setTerm_gend(String term_gend) {
        this.term_gend = term_gend;
    }

    public int getTerm_prio() {
        return term_prio;
    }

    public void setTerm_prio(int term_prio) {
        this.term_prio = term_prio;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    @Override
    public String toString() {
        return "Language{" +
                "title='" + title + '\'' +
                ", term='" + term + '\'' +
                ", term_gend='" + term_gend + '\'' +
                ", term_prio=" + term_prio +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
        LangTerm t = (LangTerm)o;
        if(this.getTerm_prio()>((LangTerm) o).getTerm_prio()) return 1;
        else return -1;

    }
}
