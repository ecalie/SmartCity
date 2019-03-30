package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreLogging extends JFrame {
    private static FenetreLogging instance;

    private JTextArea logging;
    private JScrollPane scroll;

    private FenetreLogging() {
        super("Logging");

        logging = new JTextArea(20, 25);
        logging.setBackground(Color.GRAY);
        scroll = new JScrollPane(logging);
        this.add(scroll);

        this.setLocation(800,  0);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_VERT);
    }

    public static FenetreLogging getInstance() {
        if (instance == null)
            instance = new FenetreLogging();

        return instance;
    }

    public static void ajouter(String texte) {
        instance.logging.setText(instance.logging.getText() + "\n" + texte);
        instance.scroll.getVerticalScrollBar().setValue(instance.scroll.getVerticalScrollBar().getMaximum());
    }

}
