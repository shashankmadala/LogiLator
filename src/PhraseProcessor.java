import java.util.ArrayList;
import java.util.Scanner;

public class PhraseProcessor {

    public String cleanInput(String input) {
        String cleaned = "";
        String lower = input.toLowerCase();

        for (int i = 0; i < lower.length(); i++) {
            char ch = lower.charAt(i);

            if ((ch >= 'a' && ch <= 'z') || ch == ' ') {
                cleaned = cleaned + ch;
            }
        }

        return cleaned.trim();
    }

    public ArrayList<String> breakPhrase(String input) {
        ArrayList<String> words = new ArrayList<String>();

        String cleaned = cleanInput(input);

        Scanner scan = new Scanner(cleaned);
        while (scan.hasNext()) {
            words.add(scan.next());
        }
        scan.close();

        return words;
    }
}
