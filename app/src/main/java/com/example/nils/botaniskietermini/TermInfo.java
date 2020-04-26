package com.example.nils.botaniskietermini;

import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class TermInfo {


    static String[] allPrioLang = {"LV", "DE", "RU"};
    private int id;
    private LangTerm[] allTermsInLang = new LangTerm[]{new LangTerm("LV"), new LangTerm("LA"), new LangTerm("EN"), new LangTerm("DE"), new LangTerm("RU")};
    private String WIKI;
    private String genus;
    private String def_tez;
    private String TEZAURS;
    private Genus genusEnum;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    private String comment;

    public TermInfo(){}

    public TermInfo( LangTerm [] langs,  String wiki_link, String genus, String def_tez, String tez_link, String comment) {
        this.allTermsInLang = langs;
        this.WIKI = wiki_link;
        this.genus = genus;
        this.def_tez = def_tez;
        this.TEZAURS = tez_link;
        setGenusEnum();
        this.comment = comment;
    }

    public String getLangToFindSynonims()
    {
        LangTerm[] allTermsInLangChangePriotity = new LangTerm[allTermsInLang.length];
        allTermsInLangChangePriotity[0] = allTermsInLang[1];
        allTermsInLangChangePriotity[1] = allTermsInLang[1];
        allTermsInLangChangePriotity[2] = allTermsInLang[2];
        allTermsInLangChangePriotity[3] = allTermsInLang[3];
        allTermsInLangChangePriotity[4] = allTermsInLang[4];

        String res = "";
        for(LangTerm l: allTermsInLangChangePriotity)
        {
            Log.d("Term",":"+l);
            if(!l.getTerm().equals(""))
            {
                res = l.getTitle();
                break;
            }
        }
        return res;
    }


    public void setGenusEnum() {
        if (genus != null) {
            if (genus.equals("ģints"))
                genusEnum = Genus.gints;
            else if (genus.equals("apakšģints"))
                genusEnum = Genus.apaksgints;
            else if (genus.equals("suga"))
                genusEnum = Genus.suga;
            else if (genus.equals("pasuga"))
                genusEnum = Genus.pasuga;
            else if (genus.equals("varietāte"))
                genusEnum = Genus.varietate;
            else if (genus.equals("forma"))
                genusEnum = Genus.forma;
            else if (genus.equals("šķirņu grupa"))
                genusEnum = Genus.skirnu_grupa;
            else
                genusEnum = Genus.none;
        }
    }

    public static String getTermFromCodeOrGen2(String code, TermInfo info) {
        String result = "";
        String[] nonLangTermValues = {"WIKI", "genus", "T*", "TEZAURS"};

        for (String  s: nonLangTermValues) {
            if (s.equalsIgnoreCase(code)) {
                try {
                    try {
                        result = info.getClass().getDeclaredField(s).get(info).toString();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                } catch (NoSuchFieldException e) {
                    try {
                        result = info.getClass().getDeclaredField("def_tez").get(info).toString();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (NoSuchFieldException e1) {
                        e1.printStackTrace();
                    }
                    break;
                }
            }
        }

        return result;

    }

    public static boolean isNotEmptyField(String code, TermInfo info) throws NoSuchFieldException {

        boolean result = true;
        String[] allFieldsTitlesWithTAsteric = new String[info.getClass().getDeclaredFields().length];
        int i = 0;
        for (Field f: info.getClass().getDeclaredFields()) {
            allFieldsTitlesWithTAsteric[i] = f.getName();
            i++;
        }

        allFieldsTitlesWithTAsteric[info.getClass().getDeclaredFields().length-1] = "T*";

        for (String  s: allFieldsTitlesWithTAsteric) {
           if (s.equalsIgnoreCase(code)) {
                try {
                   if (info.getClass().getDeclaredField(s).get(info).toString().length() < 1)
                        result = false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    try {
                        if (info.getClass().getDeclaredField("def_tez").get(info).toString().length() < 1) {
                            result = false;
                        }
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    }
                }
               break;
            }
        }
        return result;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LangTerm[] getAllTermsInLang() {
        return allTermsInLang;
    }

    public void setAllTermsInLang(LangTerm[] allTermsInLang) {
        this.allTermsInLang = allTermsInLang;
    }

    public String getWiki_link() {
        return WIKI;
    }

    public void setWiki_link(String wiki_link) {
        this.WIKI = wiki_link;
    }

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public String getDef_tez() {
        return def_tez;
    }

    public void setDef_tez(String def_tez) {
        this.def_tez = def_tez;
    }

    public String getTez_link() {
        return TEZAURS;
    }

    public void setTez_link(String tez_link) {
        this.TEZAURS = tez_link;
    }

    public Genus getGenusEnum() {
        return genusEnum;
    }

    public static ArrayList<LangTerm> getTermsByPriority(ArrayList<TermInfo> termInfos, String lang) {
        ArrayList<LangTerm> langTerms = new ArrayList<>();
        boolean isLangPrio = false;
        if(termInfos.size()>0) {
            String termInfoString = termInfos.get(0).getAllTermsInLang()[LangTerm.getLangIndex(lang)].getTerm();
            for (TermInfo t : termInfos) {
                if (!t.getAllTermsInLang()[LangTerm.getLangIndex(lang)].getTerm().equalsIgnoreCase(termInfoString))
                    isLangPrio = true;
            }


            if (isLangPrio) {
                ArrayList<LangTerm> allSynonims = new ArrayList<>();
                for (TermInfo t : termInfos) {
                    allSynonims.add(t.getAllTermsInLang()[LangTerm.getLangIndex(lang)]);
                }

                Collections.sort(allSynonims);

                for (LangTerm t : allSynonims)
                    langTerms.add(t);//t.getTerm() + " " + t.getTerm_gend() + ";";


            } else {
                langTerms.add(termInfos.get(0).getAllTermsInLang()[LangTerm.getLangIndex(lang)]);
                //result+=termInfos.get(0).getAllTermsInLang()[LangTerm.getLangIndex(lang)].getTerm() + " " + termInfos.get(0).getAllTermsInLang()[LangTerm.getLangIndex(lang)].getTerm_gend();
            }
        }
        return langTerms;
    }

    ///not used anymore
    public static String[] calculateAllInfoByPriority(ArrayList<TermInfo> terms, int whichPrio) {
        String[] result = new String[terms.size()*3];
        String[] prioLanguages = {"LV", "GE", "RU"};
        String[] prioLanguagesApz = {"LV_gend", "GE_gend", "RU_gend"};
        String[] prioLanguagesPrio = {"lv_prio", "de_prio", "ru_prio"};
        int i = -1;
        for(TermInfo t:terms)
        {
            for(Field f:t.getClass().getDeclaredFields())
                if(f.getName().equalsIgnoreCase(prioLanguages[whichPrio])) {
                    try {
                        result[++i] =  t.getClass().getDeclaredField(prioLanguages[whichPrio].toLowerCase()).get(t).toString();
                        result[++i] =  t.getClass().getDeclaredField(prioLanguagesApz[whichPrio].toLowerCase()).get(t).toString();
                        result[++i] =  ""+t.getClass().getDeclaredField(prioLanguagesPrio[whichPrio].toLowerCase()).get(t);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }
        }
        return result;
    }

    @Override
    public String toString() {
        return "TermInfo{" +
                "id=" + id +
                ", lang='" + Arrays.toString(allTermsInLang) + '\'' +


                ", wiki_link='" + WIKI + '\'' +
                ", genus='" + genus + '\'' +
                ", def_tez='" + def_tez + '\'' +
                ", tez_link='" + TEZAURS + '\'' +
                '}';
    }
}
