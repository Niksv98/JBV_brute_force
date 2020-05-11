package com.example.nils.botaniskietermini;

import java.io.*;
import java.util.*;

class Spelling {

    private final Map<String, Double> dictionary = new HashMap<String, Double>();

    List<Character> charList = new ArrayList<Character>();
    String charString = "aābcčdeēfgģhiījkķlļmnņoprsštuūvzžwxyqбвгдёжзийлпуфхцчшщъыьэюяäöüß";
    String[] wordArray;
    List<String> wordList;
    double wordCount;

    public Spelling(String text) throws IOException {

        for(char c : charString.toCharArray()) {
            charList.add(c);
        }

        wordArray = text.split(" ");
        wordCount = wordArray.length;

        wordList = Arrays.asList(wordArray);

        for(String word : wordList){
            double number = (Double)(Collections.frequency(wordList, word)/wordCount);
            dictionary.put(word, number);
        }

    }

    private final ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for(int i=0; i < word.length(); ++i)
            result.add(word.substring(0, i) + word.substring(i+1));
        for(int i=0; i < word.length()-1; ++i)
            result.add(word.substring(0, i) + word.substring(i+1, i+2) + word.substring(i, i+1) + word.substring(i+2));
        for(int i=0; i < word.length(); ++i)
            for(char c : charList)
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i+1));
        for(int i=0; i <= word.length(); ++i)
            for(char c : charList)
                result.add(word.substring(0, i) + String.valueOf(c) + word.substring(i));
        return result;
    }

    public final ArrayList<String> correct(String word) {
        word.toLowerCase();
        ArrayList<String> list = edits(word);
        Map<String, Double> candidates = new HashMap<String, Double>();
        ArrayList<String> tempList = new ArrayList<String>();
        Map<Double, String> tempMap = new HashMap<Double, String>();

        for(String s : list) {
            if(dictionary.containsKey(s))
                candidates.put(s, dictionary.get(s));
            for (String w : edits(s))
                if (dictionary.containsKey(w))
                    candidates.put(w, dictionary.get(w));
        }

        if(candidates.size() > 0) {
            for(String i : candidates.keySet()){
                tempMap.put(candidates.get(i), i);
            }
            if(candidates.size() > 3){
                for (int i = 0; i < 3; i++) {
                    tempList.add(tempMap.get(Collections.max(candidates.values())));
                    candidates.remove(tempMap.get(Collections.max(candidates.values())));
                }
                return tempList;
            }
            else{
                for(String a : candidates.keySet()){
                    tempList.add(a);
                }
                return tempList;
            }
        }

        return tempList;
    }

    // public static void main(String[] args) throws IOException {
    //     String file = "termini.txt";

    //     File f = new File(file);
    //     FileReader in = new FileReader(f);
    //     char[] buffer = new char[(int)f.length()];
    //     in.read(buffer);

    //     String text = new String(buffer);
    //     text.toLowerCase();
    //     in.close();

    //     Spelling spellObj = new Spelling(text);

    //     System.out.println(spellObj.correct("lepa"));
    // }
}