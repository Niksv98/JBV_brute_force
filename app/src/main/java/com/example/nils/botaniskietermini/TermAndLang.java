package com.example.nils.botaniskietermini;

public class TermAndLang {
    private String term;
    private String lang;

    public TermAndLang(String term, String lang) {
        this.term = term;
        this.lang = lang;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public boolean equals(Object o){
        if(o instanceof TermAndLang){
            TermAndLang toCompare = (TermAndLang) o;
            return this.term.equals(toCompare.term);
        }
        return false;
    }
}
