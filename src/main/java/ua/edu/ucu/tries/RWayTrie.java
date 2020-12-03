package ua.edu.ucu.tries;

import ua.edu.ucu.immutable.Queue;

import java.util.ArrayList;

public class RWayTrie implements Trie {
    private static Node root;
    private static final Integer R = 26;

    private final int MIN_WORD_LENGTH = 2;
    private int length;

    public RWayTrie() {
        root = new Node();
        root.isEndOfWord = true;
        length = 0;
    }

    @Override
    public void add(Tuple t) {
        if (t.weight <= MIN_WORD_LENGTH) {
            System.out.println("Please, provide valid arguments");
            return;
        }

        String word = t.term;
        int index;
        Node current = root;

        for (int i = 0; i < t.weight; i++) {
            // char - 'a' - get letter in range [0-26)
            index = getIndex(word, i);
            if (current.next[index] == null) {
                current.next[index] = new Node(word.charAt(i));
            }
            current = current.next[index];

        }
        current.isEndOfWord = true;
        current.nodeWord = t.term;
        this.length += 1;
    }

    @Override
    public boolean contains(String word) {
        if (word.length() <= MIN_WORD_LENGTH) {
            return false;
        }

        int index;
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            index = getIndex(word, i);
            if (current.next[index] == null) {
                return false;
            }
            current = current.next[index];
        }
        return current != null && current.isEndOfWord;
    }

    @Override
    public boolean delete(String word) {
        if (word.length() <= MIN_WORD_LENGTH) {
            return false;
        }

        if (size() == 0 || !contains(word)) {
            return false;
        }

        Node current = root;
        int index;
        for (int i = 0; i < word.length(); i++) {
            index = getIndex(word, i);
            if (current.next[index] == null) {
                return false;
            }
            current = current.next[index];
        }
        current.isEndOfWord = false;
        current.nodeWord = null;
        length--;
        return true;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        ArrayList<String> result = new ArrayList<>();
        Node current = root;

        for (int i = 0; i < s.length(); i++) {
            current = current.next[s.charAt(i) - 'a'];
        }
        if (current == null) {
            return result;

        } else if (current.isEndOfWord) {
            result.add(current.nodeWord);
        }

        Queue q = new Queue();
        q.enqueue(current);
        while (q.isNotEmpty()) {
            Node curr = (Node) q.dequeue();
            for (int i = 0; i < R; i++) {
                if (curr.next[i] != null) {
                    q.enqueue(curr.next[i]);
                    if (curr.next[i].nodeWord != null) {
                        result.add(curr.next[i].nodeWord);
                    }
                }
            }
        }

        return result;
    }

    @Override
    public int size() {
        return this.length;
    }

    private static class Node {
        Node[] next = new Node[R];
        boolean isEndOfWord;
        private Character val;
        private String nodeWord;

        Node() {
            this.isEndOfWord = false;
            for (int i = 0; i < R; i++) {
                // init R child elements
                next[i] = null;
            }
        }

        Node(Character value) {
            this();
            this.val = value;
        }
    }

    private static int getIndex(Character i) {
        int res = i - 'a';
        if (res > R || res < 0) {
            throw new IndexOutOfBoundsException();
        }
        return res;
    }

    private static int getIndex(String word, int i) {
        return getIndex(word.charAt(i));
    }

}