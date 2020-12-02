package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Tuple;
import ua.edu.ucu.tries.RWayTrie;
import ua.edu.ucu.tries.Trie;

import java.util.ArrayList;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches() {
        this.trie = new RWayTrie();
    }

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        int res = 0;
        for (String string : strings) {
            if (string.length() > 2) {
                Tuple tuple = new Tuple(string, string.length());
                trie.add(tuple);
                res += 1;
            }
        }
        return res;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        Iterable<String> allWords = trie.wordsWithPrefix(pref);
        ArrayList<String> result = new ArrayList<>();

        for (String element : allWords) {
            if (element.length() - pref.length() == k) {
                break;
            }
            result.add(element);
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
