public class LinearProbing {
    static final int M = 20000; // Fixed size
    String[] table = new String[M];
    int[] lineNumbers = new int[M];

    public static int oldHashCode(String key) {
        int hash = 0;
        int skip = Math.max(1, key.length() / 8);
        for (int i = 0; i < key.length(); i += skip) {
            hash = (hash * 37) + key.charAt(i);
        }
        return Math.abs(hash % M);
    }

    public static int newHashCode(String key) {
        int hash = 0;
        for (int i = 0; i < key.length(); i++) {
            hash = (hash * 31) + key.charAt(i);
        }
        return Math.abs(hash % M);
    }
    //put the words into the hashtable using linear probing
    public void insert(String word, int lineNumber, boolean useEarlyHash) {
        int hashIndex = (useEarlyHash ? oldHashCode(word) : newHashCode(word)) % M;
        if (hashIndex < 0) hashIndex += M; //works for negative hash values.


        for (int i = 0; i < M; i++) {
            int probeIndex = (hashIndex + i) % M; // Linear probing
            if (table[probeIndex] == null) {
                table[probeIndex] = word;
                lineNumbers[probeIndex] = lineNumber;
                return;
            }
        }
    }
    //how many comparisons does it take to find a word
    public int search(String word, boolean useEarlyHash) {
        int hashIndex = (useEarlyHash ? oldHashCode(word) : newHashCode(word)) % M;
        if (hashIndex < 0) hashIndex += M; //works for negative hash values.

        for (int i = 0; i < M; i++) {
            int probeIndex = (hashIndex + i) % M;

            if (table[probeIndex] == null) {
                return i + 1; // Stop search if empty slot is encountered.
            }
            if (table[probeIndex].equals(word)) {
                return i + 1; // Return comparisons made when word is found.
            }
        }
        return M; // Worst case, table full
    }
    //does the search even find the word
    public boolean contains(String word, boolean useEarlyHash) {
        int hashIndex = (useEarlyHash ? oldHashCode(word) : newHashCode(word)) % M;
        if (hashIndex < 0) hashIndex += M; //works for negative hash values.

        for (int i = 0; i < M; i++) {
            int probeIndex = (hashIndex + i) % M;

            if (table[probeIndex] == null) {
                return false; // Stop search if empty slot is encountered.
            }
            if (table[probeIndex].equals(word)) {
                return true; // Return comparisons made when word is found.
            }
        }
        return false; // Worst case, table full
}
}