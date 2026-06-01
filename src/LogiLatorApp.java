import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LogiLatorApp {
    private static JTextArea input;
    private static JTextArea output;
    private static JComboBox<String> languageBox;

    public static void main(String[] args) {
        JFrame frame = new JFrame("LogiLator");

        input = new JTextArea(6, 30);
        output = new JTextArea(6, 30);
        output.setEditable(false);

        languageBox = new JComboBox<String>();
        languageBox.addItem("French");
        languageBox.addItem("Spanish");
        languageBox.addItem("Italian");

        JButton translateButton = new JButton("Translate");
        translateButton.setBackground(new Color(70, 130, 180));
        translateButton.setForeground(Color.WHITE);
        translateButton.setOpaque(true);
        translateButton.setBorderPainted(false);

        translateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                translateText();
            }
        });

        Color bg = new Color(214, 233, 248);
        Color labelColor = new Color(30, 60, 90);

        input.setBackground(Color.WHITE);
        output.setBackground(new Color(255, 255, 240));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(bg);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel inLabel = new JLabel("Enter English text:");
        inLabel.setForeground(labelColor);
        panel.add(inLabel);
        panel.add(new JScrollPane(input));

        JLabel langLabel = new JLabel("Choose language:");
        langLabel.setForeground(labelColor);
        panel.add(langLabel);
        panel.add(languageBox);

        panel.add(translateButton);

        JLabel outLabel = new JLabel("Translation:");
        outLabel.setForeground(labelColor);
        panel.add(outLabel);
        panel.add(new JScrollPane(output));

        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void translateText() {
        try {
            String language = (String) languageBox.getSelectedItem();
            String fileName = "";

            if (language.equals("French")) {
                fileName = "french_dictionary.txt";
            } else if (language.equals("Spanish")) {
                fileName = "spanish_dictionary.txt";
            } else {
                fileName = "italian_dictionary.txt";
            }

            LanguageDictionary dict = new LanguageDictionary();
            dict.loadFromFile(findDataFile(fileName));

            Translator translator = new Translator(dict);
            String result = translator.translateAndMakeSense(input.getText(), language);
            output.setText(result);
        } catch (Exception ex) {
            output.setText("Error: " + ex.getMessage());
        }
    }

    public static String findDataFile(String fileName) {
        String[] paths = {
            "data/" + fileName,
            "../LogiLator/data/" + fileName,
            "LogiLator/data/" + fileName
        };
        for (int i = 0; i < paths.length; i++) {
            if (new File(paths[i]).exists()) {
                return paths[i];
            }
        }
        return "data/" + fileName;
    }
}