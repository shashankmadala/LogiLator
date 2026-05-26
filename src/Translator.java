public class Translator {
    private String language;
    private String phrase; 
    private String word;

    public Translator(String language, String phrase, String word){
        this.language = language;
        this.phrase = phrase;
        this.word = word;
    }
    public String getLanguage(){
        return language;
    }
    public String getPhrase(){
        return phrase;
    }
    public String getWord(){
        return word;
    }
}
