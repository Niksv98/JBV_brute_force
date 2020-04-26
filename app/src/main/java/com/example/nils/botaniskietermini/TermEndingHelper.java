package com.example.nils.botaniskietermini;

import android.util.Log;

import java.util.ArrayList;

public class TermEndingHelper {
    public static ArrayList<TermEnding> allEndings = new ArrayList<>();

    public TermEndingHelper() {
        allEndings.add(new TermEnding("es", "e"));
        allEndings.add(new TermEnding("as", "a"));
        allEndings.add(new TermEnding("či", "cis"));
        allEndings.add(new TermEnding("dži", "dzis"));
        allEndings.add(new TermEnding("ji", "is"));
        allEndings.add(new TermEnding("ļi", "lis"));
        allEndings.add(new TermEnding("ļļi", "llis"));
        allEndings.add(new TermEnding("ņi", "ņš"));
        allEndings.add(new TermEnding("ņi", "nis"));
        allEndings.add(new TermEnding("ši", "sis"));
        allEndings.add(new TermEnding("ši", "tis"));
        allEndings.add(new TermEnding("šļi", "slis"));
        allEndings.add(new TermEnding("šņi", "snis"));
        allEndings.add(new TermEnding("šņi", "nis"));
        allEndings.add(new TermEnding("ži", "dis"));
        allEndings.add(new TermEnding("ņi", "nis"));
        allEndings.add(new TermEnding("i", "s"));
        allEndings.add(new TermEnding("i", "š"));
    }

    public static ArrayList<String> getEndingsFromTerm(String searchTerm) {
        ArrayList<String> termsWithAllPosibleENdings = new ArrayList<>();


        for (TermEnding t: allEndings) {
           if (searchTerm.endsWith(t.plural)) {
                termsWithAllPosibleENdings.add(searchTerm.substring(0, (searchTerm.length() - t.plural.length()))+t.singular);
                 } else if (searchTerm.endsWith(t.singular)) {
                termsWithAllPosibleENdings.add(searchTerm.substring(0, (searchTerm.length() - t.singular.length()))+t.plural);
            }
        }


        ArrayList<String> termsWithAllPosibleENdingsWithoutDuplicates = new ArrayList<>();
        for(String s: termsWithAllPosibleENdings){
        if(!termsWithAllPosibleENdingsWithoutDuplicates.contains(s)) {
            termsWithAllPosibleENdingsWithoutDuplicates.add(s);
            }
        }
        termsWithAllPosibleENdingsWithoutDuplicates.add(searchTerm);
        return  termsWithAllPosibleENdingsWithoutDuplicates;
    }

    class  TermEnding
    {
        private String plural;
        private String singular;


        public TermEnding(String plural, String singular) {
            this.singular = singular;
            this.plural = plural;
        }

        public String getSingular() {
            return singular;
        }

        public void setSingular(String singular) {
            this.singular = singular;
        }

        public String getPlural() {
            return plural;
        }

        public void setPlural(String plural) {
            this.plural = plural;
        }
    }

}
