package com.example.nils.botaniskietermini;

import android.util.Log;

import java.io.*;
import java.util.*;

class Spelling {

    private final Map<String, Double> dictionary = new HashMap<>();

    List<Character> charList = new ArrayList<>();
    String charString = "aābcčdeēfgģhiījkķlļmnņoprsštuūvzžwxyqбвгдёжзийлпуфхцчшщъыьэюяäöüß";
    String[] wordArray;
    List<String> wordList;
    double wordCount;

    public Spelling(String text) {

        for(char c : charString.toCharArray()) {
            charList.add(c);
        }

        wordArray = text.split(" ");
        wordCount = wordArray.length;
        wordList = Arrays.asList(wordArray);

        for(String word : wordList){
            double number = Collections.frequency(wordList, word)/wordCount;
            dictionary.put(word, number);
        }
    }

    private final ArrayList<String> edits(String word) {
        ArrayList<String> result = new ArrayList<>();
        for(int i=0; i < word.length(); ++i)
            result.add(word.substring(0, i) + word.substring(i+1));
        for(int i=0; i < word.length()-1; ++i)
            result.add(word.substring(0, i) + word.substring(i+1, i+2) + word.substring(i, i+1) + word.substring(i+2));
        for(int i=0; i < word.length(); ++i)
            for(char c : charList)
                result.add(word.substring(0, i) + c + word.substring(i+1));
        for(int i=0; i <= word.length(); ++i)
            for(char c : charList)
                result.add(word.substring(0, i) + c + word.substring(i));
        return result;
    }

    public final ArrayList<String> correct(String word) {
        word.toLowerCase();
        ArrayList<String> list = edits(word);
        Map<String, Double> candidates = new HashMap<>();
        ArrayList<String> results = new ArrayList<>();
        Map<Double, String> tempMap = new HashMap<>();

        for(String s : list) {
            if(dictionary.containsKey(s)) {
                Log.d("CORRECTION_INFO",s);
                candidates.put(s, dictionary.get(s));
            }
            for (String w : edits(s))
                if (dictionary.containsKey(w)) {
                    candidates.put(w, dictionary.get(w));
                }
        }

        if(candidates.size() > 0) {
            for(String i : candidates.keySet()){
                tempMap.put(candidates.get(i), i);
            }
            if(candidates.size() > 3){
                for (int i = 0; i < 3; i++) {
                    results.add(tempMap.get(Collections.max(candidates.values())));
                    candidates.remove(tempMap.get(Collections.max(candidates.values())));
                    tempMap.remove(tempMap.get(Collections.max(candidates.values())));
                }
                return results;
            }
            else{
                for(String a : candidates.keySet()){
                    results.add(a);
                }
                return results;
            }
        }
        return results;
    }
}