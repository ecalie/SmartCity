package vue;

import modele.Constante;
import modele.Couleur;
import modele.Direction;
import modele.agent.Feu;

import java.awt.*;

public class DessinFeu {

    private Feu feu;

    public DessinFeu(Feu feu) {
        this.feu = feu;
    }

    public void dessiner(Graphics g) {
        if (feu.getCouleur() == Couleur.Rouge)
            g.setColor(Color.RED);
        else
            g.setColor(Color.GREEN);

        if (feu.getOrientation() == Direction.Nord)
            g.drawLine(feu.getX() - Constante.largeurRoute / 4, feu.getY(), feu.getX() + Constante.largeurRoute / 4, feu.getY());
         else if (feu.getOrientation() == Direction.Sud)
            g.drawLine(feu.getX() - Constante.largeurRoute / 4, feu.getY(), feu.getX() + Constante.largeurRoute / 4, feu.getY());
         else if (feu.getOrientation() == Direction.Ouest)
            g.drawLine(feu.getX(), feu.getY() - Constante.largeurRoute / 4, feu.getX(), feu.getY() + Constante.largeurRoute / 4);
         else if (feu.getOrientation() == Direction.Est)
            g.drawLine(feu.getX(), feu.getY() - Constante.largeurRoute / 4, feu.getX(), feu.getY() + Constante.largeurRoute / 4);
    }

}
