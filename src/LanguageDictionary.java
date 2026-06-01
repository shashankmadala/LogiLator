import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class LanguageDictionary {

    private ArrayList<DictionaryEntry> entries;

    public LanguageDictionary() {
        entries = new ArrayList<DictionaryEntry>();
    }

    public String translateWord(String word) throws Exception {
        for (int i = 0; i < entries.size(); i++) {
            DictionaryEntry current = entries.get(i);

            if (current.matches(word)) {
                return current.getTranslatedWord();
            }
        }

        java.io.FileWriter writer = new java.io.FileWriter("data/unknown_words.txt", true);
        writer.write(word + "\n");
        return null;
    }

    public void loadFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.length() != 0) {
                int eq = line.indexOf("=");

                if (eq != -1) {
                    String english = line.substring(0, eq);
                    String translated = line.substring(eq + 1);

                    entries.add(new DictionaryEntry(english, translated));
                }
            }
        }
    }
}
