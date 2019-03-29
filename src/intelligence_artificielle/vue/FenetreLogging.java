package intelligence_artificielle.vue;

import javax.swing.*;
import java.awt.*;

public class FenetreLogging extends JFrame {
    private static FenetreLogging instance;

    private static JTextArea logging;

    public static FenetreLogging getInstance() {
        if (instance == null)
            instance = new FenetreLogging();

        return instance;
    }

    private FenetreLogging() {
        super("Logging");

        logging = new JTextArea(20,25);
        logging.setBackground(Color.GRAY);
        this.add(new JScrollPane(logging));

        this.pack();
        this.setLocation(750,0);
    }

    public static void ajouter(String texte) {
        logging.setText(logging.getText() + "\n" + texte);
    }

}
