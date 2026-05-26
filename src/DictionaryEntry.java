public class DictionaryEntry {
    private String englishWord;
    private String translatedWord;

    // set up a new entry. this is when we read a line from the file
    public DictionaryEntry(String englishWord, String translatedWord) {
        this.englishWord = englishWord;
        this.translatedWord = translatedWord;
    }

    public String getEnglishWord() {
        return englishWord;
    }

    public String getTranslatedWord() {
        return translatedWord;
    }

    // check if this entry is the word we're looking for. we learned equalsIgnoreCase, we searched up how to use it properly.
    public boolean matches(String word) {
        return englishWord.equalsIgnoreCase(word);
    }
}
