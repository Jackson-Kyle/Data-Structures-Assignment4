//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.*;
import java.net.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        //pull list of words from URL
        URL dictionary = new URL("https://www.mit.edu/~ecprice/wordlist.10000");
        BufferedReader reader = new BufferedReader(new InputStreamReader(dictionary.openStream()));

        SeparateChaining oldChaining = new SeparateChaining();
        SeparateChaining newChaining = new SeparateChaining();
        LinearProbing oldProbing = new LinearProbing();
        LinearProbing newProbing = new LinearProbing();

        String word;
        int lineNumber = 1;
        //read in all of the words
        while ((word = reader.readLine()) != null) {
            oldChaining.insert(word.trim(), lineNumber, true);
            newChaining.insert(word.trim(), lineNumber, false);
            oldProbing.insert(word.trim(), lineNumber, true);
            newProbing.insert(word.trim(), lineNumber, false);
            lineNumber++;
        }
        reader.close();

        //let the user insert the passwords
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter passwords to check (comma-separated): ");
        String input = scanner.nextLine();
        scanner.close();

        //clean the input
        String[] passwords = input.split(",");
        for (int i = 0; i < passwords.length; i++) {
            passwords[i] = passwords[i].trim();
        }
        //test the passwords
        for (String password : passwords) {
            System.out.println("Password: " + password);
            System.out.println("Using old hashCode");
            System.out.println("Chaining comparisons: " + oldChaining.search(password, true));
            System.out.println("Probing comparisons: " + oldProbing.search(password, true));
            boolean isStrongEarly = PasswordCheck.isStrongPassword(password, oldChaining, oldProbing, true);
            System.out.println("Is the password strong: " + isStrongEarly);
            System.out.println("Using modern hashCode:");
            System.out.println("Chaining comparisons: " + newChaining.search(password, false));
            System.out.println("Probing comparisons: " + newProbing.search(password, false));
            boolean isStrongCurrent = PasswordCheck.isStrongPassword(password, newChaining, newProbing, false);
            System.out.println("Is the password strong: " + isStrongCurrent);
            System.out.println();
        }
    }
}