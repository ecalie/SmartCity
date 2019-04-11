package vue;

import javax.swing.*;
import java.awt.*;

public class FenetreMessages extends JFrame {
    private static FenetreMessages instance;

    private JTextArea logging;
    private JScrollPane scroll;

    private FenetreMessages() {
        super("Messages envoyés");

        logging = new JTextArea(20, 25);
        logging.setBackground(Color.GRAY);
        scroll = new JScrollPane(logging);
        this.add(scroll);

        this.setLocation(700,  0);
        this.pack();
        this.setExtendedState(JFrame.MAXIMIZED_VERT);
    }

    public static FenetreMessages getInstance() {
        if (instance == null)
            instance = new FenetreMessages();

        return instance;
    }

    public static void ajouter(String texte) {
        instance.logging.setText(instance.logging.getText() + "\n" + texte);
        instance.scroll.getVerticalScrollBar().setValue(instance.scroll.getVerticalScrollBar().getMaximum());
    }

}
