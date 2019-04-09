package vue;

import modele.Constante;
import modele.Direction;
import modele.agent.Voiture;

import java.awt.*;

public class DessinVoiture {

    private Voiture voiture;

    public DessinVoiture(Voiture voiture) {
        this.voiture = voiture;
    }

    public void dessiner(Graphics g) {
        g.setColor(Color.BLUE);
        if (voiture.getDirection() == Direction.Nord)
            g.fillOval(voiture.getPosition().getX()- Constante.largeurVoiture / 2, voiture.getPosition().getY() ,
                    Constante.largeurVoiture, Constante.longueurVoiture);

        else if (voiture.getDirection() == Direction.Sud)
            g.fillOval(voiture.getPosition().getX()-Constante.largeurVoiture/2, voiture.getPosition().getY() - Constante.longueurVoiture ,
                    Constante.largeurVoiture, Constante.longueurVoiture);

        else if (voiture.getDirection() == Direction.Est)
            g.fillOval(voiture.getPosition().getX() - Constante.longueurVoiture , voiture.getPosition().getY() - Constante.largeurVoiture / 2,
                    Constante.longueurVoiture, Constante.largeurVoiture);

        else if (voiture.getDirection() == Direction.Ouest)
            g.fillOval(voiture.getPosition().getX() , voiture.getPosition().getY() - Constante.largeurVoiture / 2,
                    Constante.longueurVoiture, Constante.largeurVoiture);
    }
}
