package edu.brown.cs32.lyzhang.crassus.backend.AutoComplete;

import java.util.LinkedList;
import java.util.List;

public class Trie {
    List<Trie> children; 
    char value;
    List<String> fullWords;
    
    /**
     * A constructor for a new trie
     *  
     */
    public Trie () {
        this.children = new LinkedList<Trie>();
        this.fullWords = new LinkedList<String>();
        
    }
    
    /**
     * A method called to get the children of a trie
     * @return a List containing all the children of the trie
     *  
     */
    private List<Trie> getChildren() {
        return this.children;
    }
    
    /**
     * A method called to get the full words stored at the trie
     * @return a List containing all the full words of the trie
     *  
     */
    private List<String> getFullWords() {
        return this.fullWords;
    }
    
    /**
     * A method called to get the value stored at the trie
     * @return a char representing the comparing value of the trie
     *  
     */
    private char getValue() {
        return this.value;
    }
    
    /**
     * A method called to set the value of the trie
     * @param v - a char representing the value to set
     *  
     */
    private void setValue(char v) {
        this.value = v;
    }
    
    /**
     * A method called to set the children of the trie
     * @param l - a list representing the children of the trie
     *  
     */
    private void setChildren(List<Trie> l) {
        this.children = l;
    }
    
    /**
     * A method called to set the full words of the trie
     * @param l - a list representing the full words of the trie
     *  
     */
    private void setWords(List<String> l) {
        this.fullWords = l;
    }
    
    /**
     * A method to search for the full words beginning with the given string
     * @param s - a string representing the prefix of the word
     * @param t - a trie used for recursion in the method
     * @param words - a list of strings representing the found words
     *  
     */
    public void searchSuffixes(String s, Trie t, List<String> words) {
        List <Trie> currChilds = t.getChildren();
        char [] comp = s.toCharArray();
        int childCtr = 0;
        if (s.length() == 0) {
                for (int i = 0; i < t.getFullWords().size(); i++) {
                        String toAdd = t.getFullWords().get(i);
                        if (!words.contains(toAdd)) {
                                words.add(toAdd);
                        }
                }
        }
        while (childCtr < currChilds.size()) {
                Trie tempComp = currChilds.get(childCtr);

                if (s.length() == 0) {
                        for (int i = 0; i < tempComp.getFullWords().size(); i++) {
                        String toAdd = tempComp.getFullWords().get(i);
                        if (!words.contains(toAdd)) {
                                words.add(toAdd);
                        }
                }
                        searchSuffixes(s, tempComp, words);
                }
                else {
                        if (tempComp.getValue() == comp[0]) {   
                                searchSuffixes((s.substring(1)), tempComp, words);
                        }
                }
                childCtr++;
        }
        
    }
    
    /**
     * A method to search for the full words possible to find with the given string and the given number of edits
     * @param s - a string representing the initial word
     * @param t - a trie used for recursion in the method
     * @param words - a list of strings representing the found words
     * @param edit - an int representing the number of allowed edits
     *  
     */
    public void searchLED(String s, Trie t, List<String> words, int edit) {
        List <Trie> currChilds = t.getChildren();
        char [] comp = s.toCharArray();
        int childCtr = 0;
        if (s.length() == 0 ) {
                for (int i = 0; i < t.getFullWords().size(); i++) {
                        String toAdd = t.getFullWords().get(i);
                        if (!words.contains(toAdd)) {
                                words.add(toAdd);
                        }
                }
        }
        if (currChilds.size() == 0 && edit > 0 && s.length() > 0) {
                searchLED(s.substring(1), t, words, (edit - 1));
         }
        while (childCtr < currChilds.size()) {
                Trie tempComp = currChilds.get(childCtr);
                if (s.length() > 0) {
                        if (comp[0] == tempComp.getValue()) {
                                        searchLED(s.substring(1), tempComp, words, edit);
                        }
                }
                if (edit > 0) {
                        if (s.length() > 0) {
                                        searchLED((s.substring(1)), tempComp, words, (edit -1));
                                        searchLED((s.substring(1)), t, words, (edit - 1));
                        }
                        searchLED(s, tempComp, words, (edit - 1));
                }
                childCtr++;
        }
    }

    
    /**
     * A method used to search if the given word is in the trie
     * @param s - the initial string to be searched for
     * @param t - a trie used for recursion
     * @return the input string if it is found in the trie, null otherwise
     *  
     */
    public String searchWord(String s, Trie t) {
        List <Trie> currChilds = t.getChildren();
        char [] comp = s.toCharArray();
        int childCtr = 0;
        if (s.length() == 0) {
                if (t.getFullWords().size() > 0) {
                        return t.getFullWords().get(0);
                }
                else {
                        return null;
                }
        }
        if (s.length() > 0) {
                while (childCtr < currChilds.size()) {
                        Trie tempComp = currChilds.get(childCtr);
                        if (comp[0] == tempComp.getValue()) {
                                return searchWord(s.substring(1), tempComp);
                        }
                        else {
                                childCtr++;
                        }
                }
        }
        return null; 
    }
 
    /**
     * A method to insert strings in the trie.
     * @param s - a string to insert into the trie
     * @param t - a trie used for recursion
     *  
     */
    public void insert(String s, Trie t) {
        List<Trie> currChilds = t.getChildren();
        Trie compTrie = t;
        char [] comparer = s.toCharArray();
        int sLength = s.length();
        if (sLength == 0) {
                return;
        }
        int compInd = 0;
        int childCtr = 0;
        while (compInd < comparer.length) {
                while (childCtr < currChilds.size()) {
                        compTrie = currChilds.get(childCtr);
                    if ((compTrie.getValue()) == comparer[compInd]) {
                        currChilds = compTrie.getChildren();
                        childCtr = 0;
                        compInd ++;
                        if (compInd == sLength) {
                                if (!(compTrie.getFullWords().contains(s))) {
                                        compTrie.getFullWords().add(s);

                                }
                                return;
                        }
                    }
                    else {
                        childCtr++;
                    }
                }
                Trie newChild = new Trie();
                newChild.setValue(comparer[compInd]);
                List<Trie> newC = new LinkedList<Trie>();
                List<String> newW = new LinkedList<String>();
                newChild.setChildren(newC);
                newChild.setWords(newW);
                currChilds.add(newChild);
                currChilds = newChild.getChildren();
                compTrie = newChild;
                childCtr = 0;
                compInd++;
        }
        compTrie.getFullWords().add(s);
    }
    
}
