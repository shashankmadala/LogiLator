import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// all the word pairs for one language
public class LanguageDictionary {

    private ArrayList<DictionaryEntry> entries;

    public LanguageDictionary() {
        entries = new ArrayList<DictionaryEntry>();
    }

    public String translateWord(String word) {
        for (int i = 0; i < entries.size(); i++) {
            DictionaryEntry current = entries.get(i);

            if (current.matches(word)) {
                return current.getTranslatedWord();
            }
        }
        return null;
    }

    public void loadFromFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scan = new Scanner(file);

        while (scan.hasNextLine()) {
            String line = scan.nextLine();

            if (line.length() == 0) {
                continue;
            }

            int eq = line.indexOf("=");
            if (eq == -1) {
                continue;
            }

            String english = line.substring(0, eq);
            String translated = line.substring(eq + 1);

            entries.add(new DictionaryEntry(english, translated));
        }

        scan.close();
    }
}
