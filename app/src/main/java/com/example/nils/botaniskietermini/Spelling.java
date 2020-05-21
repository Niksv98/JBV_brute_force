package com.example.nils.botaniskietermini;

<<<<<<< HEAD
import java.io.*;
=======
>>>>>>> not_finished
import java.util.*;

class Spelling {

    private final Map<String, Double> dictionary = new HashMap<String, Double>();

    List<Character> charList = new ArrayList<Character>();
    String charString = "aābcčdeēfgģhiījkķlļmnņoprsštuūvzžwxyqбвгдёжзийлпуфхцчшщъыьэюяäöüß";
    List<String> wordList;
    double wordCount;

    public Spelling(String text) throws IOException {

        for(char c : charString.toCharArray()) {
            charList.add(c);
        }

        text.toLowerCase();

        wordList = Arrays.asList(text.split(" "));

        wordCount = wordList.size();

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
<<<<<<< HEAD
        Map<String, Double> candidates = new HashMap<String, Double>();
        ArrayList<String> tempList = new ArrayList<String>();
        Map<Double, String> tempMap = new HashMap<Double, String>();

        for(String s : list) {
            if(dictionary.containsKey(s))
                candidates.put(s, dictionary.get(s));
            for (String w : edits(s))
                if (dictionary.containsKey(w))
=======
        Map<String, Double> candidates = new HashMap<>();
        ValueComparator bvc = new ValueComparator(candidates);
        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);
        ArrayList<String> tempList = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

        for(String s : list){
            if(dictionary.containsKey(s))
                candidates.put(s, dictionary.get(s));
            for(String w : edits(s))
                if(dictionary.containsKey(w))
>>>>>>> not_finished
                    candidates.put(w, dictionary.get(w));
        }

        sorted_map.putAll(candidates);
        tempList.addAll(sorted_map.keySet());

        if(tempList.size() > 0) {

            if(tempList.size() > 3){
                for (int i = 0; i < 3; i++) {
<<<<<<< HEAD
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
=======
                    result.add(tempList.get(i));
                }
                return result;
            }
            else{
                for(String a : tempList){
                    result.add(a);
                }
                return result;
            }
        }
        return result;
>>>>>>> not_finished
    }
}