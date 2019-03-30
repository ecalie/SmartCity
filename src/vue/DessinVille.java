package vue;

import observer.Observer;
import modele.Route;
import modele.Ville;
import modele.Voiture;
import modele.agent.Feu;

import javax.swing.*;
import java.awt.*;

public class DessinVille extends JPanel implements Observer {

    private Ville ville;

    public DessinVille(Ville ville) {
        super();
        this.ville = ville;
        this.setPreferredSize(new Dimension(ville.getLongueur(), ville.getLargeur()));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Route r : ville.getRoutes())
            new DessinRoute(r).dessiner(g);
        for (Feu f : ville.getFeux())
            new DessinFeu(f).dessiner(g);
        for (Voiture v : ville.getVoitures())
            new DessinVoiture(v).dessiner(g);
    }

    @Override
    public void update() {
        repaint();
    }
}
