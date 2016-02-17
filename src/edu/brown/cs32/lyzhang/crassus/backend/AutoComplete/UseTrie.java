package edu.brown.cs32.lyzhang.crassus.backend.AutoComplete;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class UseTrie {

    Map<String, Map<String, Integer>> prevWord;
    Map<String, Integer> frequency;
    Trie root;
    boolean prefix;
    boolean led;
    boolean whitespace;
    boolean smart;
    boolean gui;
    List<String> words;
    List<String> wordAnsw;
    Map<String, Integer> nW;
    String origSearch;
    WordCompare wrdComp;
    String searchesPrevWord;

    /**
     * The constructor for a useTrie, used to set up the trie
     *
     * @param p - a boolean representing if the prefix search is to be used
     * @param l - a boolean representing if the led search is to be used
     * @param w - a boolean representing if the whitespace search is to be used
     * @param s - a boolean representing if the smart search is to be used
     * @param g - a boolean representing if the gui is to be used
     *  
     */
    public UseTrie(boolean p, boolean l, boolean w, boolean s, boolean g) {
        prevWord = new HashMap<String, Map<String, Integer>>();
        frequency = new HashMap<String, Integer>();
        prefix = p;
        led = l;
        whitespace = w;
        smart = s;
        gui = g;
        root = new Trie();
        words = new ArrayList<String>();
        nW = new HashMap<String, Integer>();
        wordAnsw = new ArrayList<String>();
        wrdComp = new WordCompare();
        origSearch = "";
        searchesPrevWord = "";

    }

    /**
     * A method used to read the words from each file and insert the words into
     * the trie
     *
     * @param files - a list of file names of words used to populate the trie
     *  
     */
    private void setUp(List<String> files) {
        String input;
        String pWord = "";
        String nWord = "";
        int wordCount = 0;
        String file;
        for (int k = 0; k < files.size(); k++) {
            file = files.get(k);
            try  {
                BufferedReader wordReader = new BufferedReader(new FileReader(file));
                while ((input = wordReader.readLine()) != null) {
                    input = input.replaceAll("[^a-zA-Z\\s]+", " ");
                    input = input.trim();
                    input = input.toLowerCase();
                    String[] words = input.split("\\s+");
                    for (int i = 0; i < words.length; i++) {
                        nWord = words[i];
                        root.insert(words[i], root);
                        if (frequency.containsKey(nWord)) {
                            wordCount = frequency.get(nWord);
                            wordCount++;
                            frequency.put(nWord, wordCount);
                        } else {
                            frequency.put(nWord, 1);
                        }
                        if (this.prevWord.containsKey(pWord)) {
                            Map<String, Integer> tPrev = this.prevWord.get(pWord);
                            int val = 1;
                            if (tPrev.containsKey(nWord)) {
                                val = tPrev.get(nWord);
                                val += 1;
                            }
                            tPrev.put(nWord, val);
                            prevWord.put(pWord, tPrev);
                        } else {
                            Map<String, Integer> newPrev = new HashMap<String, Integer>();
                            newPrev.put(nWord, 1);
                            this.prevWord.put(pWord, newPrev);
                        }
                        pWord = nWord;
                    }
                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                System.out.println("ERROR: Need to enter a valid input file");
                e.printStackTrace();
                System.exit(0);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * a method used to initialize the Trie then either the gui or the terminal
     * reader
     *
     * @param edits - an int, representing the total number of edits allowed to
     * be made to the search input
     * @param allFiles - a List of all the files used to initialize the Trie
     *  
     */
    public void initializer(int edits, List<String> allFiles) {
        setUp(allFiles);
        allFiles.clear();
        //terminalReader(edits);
    }

    public void initializerFromString(int edits, List<String> allWords) {
        String input;
        String pWord = "";
        String nWord = "";
        int wordCount = 0;
        for (int k = 0; k < allWords.size(); k++) {
            input = allWords.get(k);
            input = input.replaceAll("[^a-zA-Z0-9\\s]+", " ");
            input = input.trim();
            input = input.toLowerCase();
            //String[] words = input.split("\\s+");
            // lyzhang change
            String[] words = new String[1]; 
            words[0]= input;
            for (int i = 0; i < words.length; i++) {
                nWord = words[i];
                root.insert(words[i], root);
                if (frequency.containsKey(nWord)) {
                    wordCount = frequency.get(nWord);
                    wordCount++;
                    frequency.put(nWord, wordCount);
                } else {
                    frequency.put(nWord, 1);
                }
                if (this.prevWord.containsKey(pWord)) {
                    Map<String, Integer> tPrev = this.prevWord.get(pWord);
                    int val = 1;
                    if (tPrev.containsKey(nWord)) {
                        val = tPrev.get(nWord);
                        val += 1;
                    }
                    tPrev.put(nWord, val);
                    prevWord.put(pWord, tPrev);
                } else {
                    Map<String, Integer> newPrev = new HashMap<String, Integer>();
                    newPrev.put(nWord, 1);
                    this.prevWord.put(pWord, newPrev);
                }
                pWord = nWord;
            }
        }
    }

//        /**
//         * A method used to read the user's input from the terminal and then search for corrections
//         * @param edits - an int representing the total number of edits that can be made
//         *  
//         */
//        public void terminalReader(int edits) {
//                String userSearch = "";
//                List<String> retrnVals = new LinkedList<String>();
//                System.out.println("Ready");
//                try (BufferedReader inpRead = new BufferedReader(new InputStreamReader(System.in))) {
//                        while ((userSearch = inpRead.readLine()).length() > 0) {
//                                retrnVals = Searcher(userSearch, edits);
//                                for (int k = 0; k < 5; k++) {
//                                        if (retrnVals.size() > k) {
//                                                userSearch = retrnVals.get(k);
//                                                System.out.println(userSearch);
//                                        }
//                                }
//                                System.out.println();
//                        }
//                } catch (IOException e) {
//                        System.out.println("ERROR: User input error.");
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                }
//                System.exit(0);
//        }
    /**
     * A method used to order the word list based on the ranking preferences
     *
     * @param wrds - a list of all the words returned by the searches, that need
     * to be ranked
     * @param inp - the array of the user's input, separated by any type of
     * spaces
     * @return a list of strings, representing the words found by the searches
     * in ranked order
     *  
     */
    private List<String> rankOptions(List<String> wrds, String[] inp) {
        String temWord;
        String precedWords = "";
        int inpLen = inp.length;
        for (int k = 0; k < (inpLen - 1); k++) {
            precedWords = precedWords.concat((inp[k].concat(" ")));
        }
        if (wrds.size() == 1) {
            temWord = wrds.get(0);
            wrds.remove(temWord);
            temWord = (precedWords + temWord);
            wrds.add(temWord);
            return wrds;
        }
        if (wrds.size() == 0) {
            return wrds;
        }
        String prW = "";
        if (inpLen > 1) {
            prW = inp[(inpLen - 2)];
            this.searchesPrevWord = prW;
            if (prevWord.containsKey(prW)) {
                this.nW = prevWord.get(prW);
            }
        }
        String lastW = inp[(inpLen - 1)];
        this.origSearch = lastW;
        PriorityQueue<String> prio = new PriorityQueue<String>(5, this.wrdComp);
        for (int i = 0; i < wrds.size(); i++) {
            if (prio.size() < 5) {
                prio.add(wrds.get(i));
            } else {
                prio.add(words.get(i));
                prio.poll();
            }
        }
        for (int k = 0; k < 5; k++) {
            if (prio.size() > 0) {
                this.wordAnsw.add(0, (precedWords + prio.poll()));
            }
        }
        return this.wordAnsw;
    }

    /**
     * A method to test two strings based on my smart compare
     *
     * @param w1 - a string to compare
     * @param w2 - a other string to compare
     * @return The string which is a better match based off of the smart search
     * algorithm
     *  
     */
    private String compSmart(String w1, String w2) {
        char[] c1 = w1.toCharArray();
        char[] c2 = w2.toCharArray();
        int count1 = 0;
        int count2 = 0;
        char[] cOrig = this.origSearch.toCharArray();
        if (w1.startsWith(this.origSearch)) {
            count1 += 10;
        }
        if (w2.startsWith(this.origSearch)) {
            count2 += 10;
        }
        if (w1.contains(this.origSearch)) {
            count1 += 5;
        }
        if (w2.contains(this.origSearch)) {
            count2 += 5;
        }
        for (int i = 0; i < Math.max(c1.length, c2.length); i++) {
            if (i < c1.length && i < cOrig.length) {
                if (c1[i] == cOrig[i]) {
                    count1++;
                }
            }
            if (i < c1.length && i < cOrig.length) {
                if (c1[i] == cOrig[i]) {
                    count2++;
                }
            }
            if (i >= cOrig.length) {
                if (count1 == count2) {
                    if (c1.length < c2.length) {
                        return w1;
                    } else if (c1.length > c2.length) {
                        return w2;
                    } else {
                        if (w1.compareTo(w2) <= 0) {
                            return w1;
                        } else {
                            return w2;
                        }
                    }
                } else {
                    if (count1 >= count2) {
                        return w1;
                    } else {
                        return w2;
                    }
                }
            }
        }
        if (count1 == count2) {
            if (w1.compareTo(w2) <= 0) {
                return w1;
            } else {
                return w2;
            }
        } else if (count1 > count2) {
            return w1;
        } else {
            return w2;
        }
    }

    /**
     * A method used to call all the necessary searches and get a list of the
     * possible corrections
     *
     * @param userSearch - a string representing the user's input search
     * @param edit - an int representing the number of allowed edits
     * @return a list of strings, representing the words found by the searches
     * in ranked order
     *  
     */
    public List<String> Searcher(String userSearch, int edit) {
        if (userSearch.length() == 0) {
            System.exit(0);
        }
        userSearch = userSearch.trim();
        this.origSearch = "";
        userSearch = userSearch.toLowerCase();
        userSearch = userSearch.replaceAll("[^a-zA-Z0-9\\s]+", " ");
        userSearch = userSearch.trim();
        this.words.clear();
        this.wordAnsw.clear();
        if (userSearch.length() == 0) {
            return this.words;
        }
        //String[] inpArr = userSearch.split("\\s+");
        String[] inpArr = new String[1]; 
        inpArr[0]= userSearch;
        this.origSearch = inpArr[inpArr.length - 1];
        String inp = inpArr[(inpArr.length - 1)];
        String wrd = root.searchWord(inp, root);
        String adder;
        if (wrd != null) {
            this.words.add(wrd);
        }
        if (this.prefix || this.smart) {
            this.root.searchSuffixes(inp, this.root, this.words);
        }
        if (this.led || this.smart) {
            this.root.searchLED(inp, this.root, this.words, edit);
        }
        if (this.whitespace || this.smart) {
            String s1;
            String s2;
            for (int i = 0; i < inp.length(); i++) {
                s1 = inp.substring(0, i);
                s2 = inp.substring(i);
                String a1;
                String a2;
                a1 = this.root.searchWord(s1, this.root);
                a2 = this.root.searchWord(s2, this.root);
                if (a1 != null && a2 != null) {
                    adder = a1 + " " + a2;
                    if (!this.words.contains(adder) && !(a1.equals("")) && !(a2.equals(""))) {
                        this.words.add(adder);
                    }
                }
            }
        }
        return rankOptions(this.words, inpArr);
    }

    /**
     * Class for a comparator, to rank suggestions.
     *
     *  
     *
     */
    private class WordCompare implements Comparator<String> {

        int ct1 = 0;
        int ct2 = 0;
        String s1 = "";
        String s2 = "";
        String[] tem;

        /**
         * A constructor for the comparator
         *
         *  
         */
        public WordCompare() {
        }

        /**
         * A method to compare two strings
         *
         * @param st1 - the first string to be compared
         * @param st2 - the second string to be compared
         *  
         */
        @Override
        public int compare(String st1, String st2) {
            s1 = st1;
            s2 = st2;
            ct1 = 0;
            ct2 = 0;
            String tempSmart;
            Map<String, Integer> m = null;
            if (st1.contains(" ")) {
                tem = st1.split(" ");
                s1 = tem[0];
            }
            if (st2.contains(" ")) {
                String[] tem = st2.split(" ");
                s2 = tem[0];
            }
            if (prevWord.containsKey(searchesPrevWord) && !prevWord.equals("")) {
                m = prevWord.get(searchesPrevWord);
                if (m.containsKey(s1)) {
                    ct1 = m.get(s1);
                }
                if (m.containsKey(s2)) {
                    ct2 = m.get(s2);
                }
            }
            if (smart) {
                tempSmart = compSmart(s1, s2);
                if (tempSmart.equals(s1)) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (s1.equals(origSearch) && (!(s2.equals(origSearch)))) {
                return 1;
            } else if ((!(s1.equals(origSearch)) && s2.equals(origSearch))) {
                return -1;
            }
            if (ct1 > ct2) {
                return 1;
            } else if (ct1 < ct2) {
                return -1;
            }
            if (frequency.get(s1) > frequency.get(s2)) {
                return 1;
            } else if (frequency.get(s1) < frequency.get(s2)) {
                return -1;
            }
            if (s1.compareTo(s2) < 0) {
                return 1;
            } else if (s1.compareTo(s2) > 0) {
                return -1;
            }
            return 0;
        }
    }
}
