public class PasswordCheck {
    public static boolean isStrongPassword(String password, SeparateChaining chaining, LinearProbing probing, boolean useEarlyHash) {
        //check the length
        if (password.length() < 8) return false;

        //check if its in the word list
        boolean foundInChaining = chaining.contains(password, useEarlyHash);
        boolean foundInProbing = probing.contains(password, useEarlyHash);

        if (foundInChaining || foundInProbing) {
            return false; // Password is a dictionary word.
        }

        //check if its a word with one digit
        if (Character.isDigit(password.charAt(password.length() - 1))) {
            // Extract prefix (word without the last character)
            String prefix = password.substring(0, password.length() - 1);

            foundInChaining = chaining.contains(prefix, useEarlyHash);
            foundInProbing = probing.contains(prefix, useEarlyHash);

            if (foundInChaining || foundInProbing) {
                return false; //dictionary word followed by a digit.
            }
        }

        //passed every check
        return true;
    }
}