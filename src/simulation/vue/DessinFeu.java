package simulation.vue;

import simulation.modele.agent.Feu;
import simulation.modele.Constante;
import simulation.modele.Couleur;
import simulation.modele.Direction;

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
        {
            g.drawLine(feu.getX() - Constante.largeurRoute / 4, feu.getY(), feu.getX() + Constante.largeurRoute / 4, feu.getY());
            g.drawLine(feu.getX()-10, feu.getY()+Constante.champVisionFeu,feu.getX()+10, feu.getY() + Constante.champVisionFeu);
        }
        else if (feu.getOrientation() == Direction.Sud)
        {
            g.drawLine(feu.getX() - Constante.largeurRoute / 4, feu.getY(), feu.getX() + Constante.largeurRoute / 4, feu.getY());
            g.drawLine(feu.getX()-10, feu.getY()-Constante.champVisionFeu,feu.getX()+10, feu.getY() - Constante.champVisionFeu);
        }
        else if (feu.getOrientation() == Direction.Ouest)
        {
            g.drawLine(feu.getX(), feu.getY() - Constante.largeurRoute / 4, feu.getX(), feu.getY() + Constante.largeurRoute / 4);
            g.drawLine(feu.getX()+Constante.champVisionFeu, feu.getY()-10,feu.getX()+Constante.champVisionFeu, feu.getY() +10);
        }
        else if (feu.getOrientation() == Direction.Est)
        {
            g.drawLine(feu.getX(), feu.getY() - Constante.largeurRoute / 4, feu.getX(), feu.getY() + Constante.largeurRoute / 4);
            g.drawLine(feu.getX()-Constante.champVisionFeu, feu.getY()-10,feu.getX()-Constante.champVisionFeu, feu.getY() +10);
        }
    }

}
