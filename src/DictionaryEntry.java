public class DictionaryEntry {
    private String englishWord;
    private String translatedWord;

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

    public boolean matches(String word) {
        return englishWord.equalsIgnoreCase(word);
    }
}
