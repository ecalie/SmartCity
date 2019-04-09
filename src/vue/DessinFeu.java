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
            g.drawLine(feu.getPosition().getX() - Constante.largeurRoute / 4, feu.getPosition().getY(), feu.getPosition().getX() + Constante.largeurRoute / 4, feu.getPosition().getY());
         else if (feu.getOrientation() == Direction.Sud)
            g.drawLine(feu.getPosition().getX() - Constante.largeurRoute / 4, feu.getPosition().getY(), feu.getPosition().getX() + Constante.largeurRoute / 4, feu.getPosition().getY());
         else if (feu.getOrientation() == Direction.Ouest)
            g.drawLine(feu.getPosition().getX(), feu.getPosition().getY() - Constante.largeurRoute / 4, feu.getPosition().getX(), feu.getPosition().getY() + Constante.largeurRoute / 4);
         else if (feu.getOrientation() == Direction.Est)
            g.drawLine(feu.getPosition().getX(), feu.getPosition().getY() - Constante.largeurRoute / 4, feu.getPosition().getX(), feu.getPosition().getY() + Constante.largeurRoute / 4);
    }

}
