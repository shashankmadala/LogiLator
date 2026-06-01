import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Translator {

    // Pulls the API key securely from your system environment variables
    private static final String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY");

    private LanguageDictionary dictionary;
    private PhraseProcessor processor;

    public Translator(LanguageDictionary dictionary) {
        this.dictionary = dictionary;
        processor = new PhraseProcessor();
    }

    public String translate(String input) throws Exception {
        ArrayList<String> words = processor.breakPhrase(input);

        String translated = "";
        for (int i = 0; i < words.size(); i++) {
            String word = words.get(i);
            String translation = dictionary.translateWord(word);

            if (translation == null) {
                translation = word;
            }

            if (i == 0) {
                translated = translation;
            } else {
                translated = translated + " " + translation;
            }
        }

        return translated;
    }

    // takes the raw translated phrase and asks Gemini to reorder the words
    public String makeSense(String language, String rawPhrase) throws Exception {
        if (GEMINI_API_KEY == null || GEMINI_API_KEY.isEmpty()) {
            throw new IllegalStateException("GEMINI_API_KEY environment variable is not set.");
        }

        String prompt = "please reorder these words to sound natural in " + language
                + ". Do not add or remove words. Output only the phrase: " + rawPhrase;

        String body = "{\"contents\":[{\"parts\":[{\"text\":\"" + prompt + "\"}]}]}";

        URL url = new URL("https://generativelanguage.googleapis.com/v1beta/models/"
                + "gemini-2.0-flash:generateContent");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("x-goog-api-key", GEMINI_API_KEY);
        conn.setDoOutput(true);

        OutputStream out = conn.getOutputStream();
        out.write(body.getBytes("UTF-8"));
        out.close();

        return getTextFromResponse(readStream(conn.getInputStream())).trim();
    }

    public String translateAndMakeSense(String input, String language) throws Exception {
        String raw = translate(input);
        return makeSense(language, raw);
    }

    private String readStream(InputStream stream) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String all = "";
        String line = reader.readLine();
        while (line != null) {
            all = all + line;
            line = reader.readLine();
        }
        reader.close();
        return all;
    }

    private String getTextFromResponse(String json) {
        int start = json.indexOf("\"text\"");
        if (start == -1) {
            return null;
        }

        int quote = json.indexOf("\"", json.indexOf(":", start) + 1);
        if (quote == -1) {
            return null;
        }

        int i = quote + 1;
        String answer = "";
        while (i < json.length() && json.charAt(i) != '"') {
            answer = answer + json.charAt(i);
            i++;
        }
        return answer;
    }
}