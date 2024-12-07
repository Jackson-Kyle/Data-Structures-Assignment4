import java.util.*;
public class SeparateChaining {static final int M = 1000; //fixed size
    List<Entry>[] table;

    static class Entry {
        String word;
        int lineNumber;

        Entry(String word, int lineNumber) {
            this.word = word;
            this.lineNumber = lineNumber;
        }
    }

    public SeparateChaining() {
        table = new LinkedList[M];
    }
    //old hashcode
    public static int oldHashCode(String key) {
        int hash = 0;
        int skip = Math.max(1, key.length() / 8);
        for (int i = 0; i < key.length(); i += skip) {
            hash = (hash * 37) + key.charAt(i);
        }
        return Math.abs(hash % M);
    }
    //modern version
    public static int newHashCode(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31) + key.charAt(i);
        }
        return Math.abs(hash % M);
    }
    //place words into hastable using separate chaining
    public void insert(String word, int lineNumber, boolean useEarlyHash) {
        int hashIndex = (useEarlyHash ? oldHashCode(word) : newHashCode(word)) % M;
        if (hashIndex < 0) hashIndex += M; //works for negative hash values.
        if (table[hashIndex] == null) {
            table[hashIndex] = new LinkedList<>();
        }
        table[hashIndex].add(new Entry(word, lineNumber));
    }
    //chwck how many comparisons the search takes
    public int search(String word, boolean useEarlyHash) {
        int hashIndex = useEarlyHash ? oldHashCode(word) : newHashCode(word);

        if (table[hashIndex] == null) {
            return 0; //if null then return 0
        }
        int comparisons = 0;
        for (Entry entry : table[hashIndex]) {
            comparisons++;
            if (entry.word.equals(word)) {
                return comparisons;
            }
        }
        return comparisons; //if we cant find it then return total comparisons
    }

    //does the search even find the word
    public boolean contains(String word, boolean useEarlyHash) {
        int hashIndex = (useEarlyHash ? oldHashCode(word) : newHashCode(word)) % M;
        if (hashIndex < 0) hashIndex += M; //works for negative hash values.
        if (table[hashIndex] == null) {
            return false; //if null then return false
        }
        int comparisons = 0;
        for (Entry entry : table[hashIndex]) {
            if (entry.word.equals(word)) {
                return true;
            }
        }
        return false; //if we cant find it then return false

    }
}
