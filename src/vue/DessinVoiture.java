package vue;

import modele.Constante;
import modele.Direction;
import modele.Voiture;

import java.awt.*;

public class DessinVoiture {

    private Voiture voiture;

    public DessinVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public void dessiner(Graphics g) {
        g.setColor(Color.RED);
        g.drawLine(voiture.getX(), voiture.getY(), voiture.getX(), voiture.getY());
        g.setColor(Color.BLUE);
        if (voiture.getDirection() == Direction.Nord || voiture.getDirection() == Direction.Sud)
            g.fillOval(voiture.getX() - Constante.largeurVoiture / 2, voiture.getY() - Constante.longueurVoiture / 2,
                    Constante.largeurVoiture, Constante.longueurVoiture);
        else
            g.fillOval(voiture.getX() - Constante.longueurVoiture / 2, voiture.getY() - Constante.largeurVoiture / 2,
                    Constante.longueurVoiture, Constante.largeurVoiture);
    }
}
