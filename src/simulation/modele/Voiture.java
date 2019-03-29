package simulation.modele;

import observer.Observable;
import simulation.modele.agent.Feu;

import static simulation.modele.Direction.*;

public class Voiture extends Observable implements Runnable {
    private int x;
    private int y;
    private Direction direction;
    private Ville ville;
    /* Le feu où est arrêtée la voiture. */
    private Feu feu;

    public Voiture(Point pt, Direction direction, Ville ville) {
        this.x = pt.getX();
        this.y = pt.getY();
        this.direction = direction;
        this.ville = ville;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getDirection() {
        return direction;
    }

    public Feu getFeu() {
        return feu;
    }

    private void avancer() {
        while (true) {

            boolean continuer;
            if (feu != null) {
                continuer = feu.getCouleur() == Couleur.Vert;
            } else
                continuer = !this.chercherFeuRouge() && !this.chercherVoiture();

            while (continuer) {
                feu = null;
                if (direction == Nord)
                    y--;
                if (direction == Sud)
                    y++;
                if (direction == Est)
                    x++;
                if (direction == Ouest)
                    x--;

                // Si la voiture sort de la ville, elle réapparaît à un point d'entrée quelconque
                if (sortie()) {
                    Point pt = ville.randomPointEntree();
                    x = pt.getX();
                    y = pt.getY();
                    direction = ville.getPointsEntrees().get(pt);
                }

                continuer = !this.chercherFeuRouge() && !this.chercherVoiture();
                try {
                    Thread.sleep(Constante.tempsPause);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                this.notifier();
            }/*
            try {
                if (feu != null) {
                    synchronized (this) {
                        this.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
    }

    private boolean chercherFeuRouge() {
        if (direction == Direction.Nord)
            feu = ville.presenceFeuRouge(x, y - 5 - Constante.longueurVoiture / 2, x, y - Constante.longueurVoiture / 2);
        else if (direction == Direction.Sud)
            feu = ville.presenceFeuRouge(x, y + Constante.longueurVoiture / 2, x, y + 5 + Constante.longueurVoiture / 2);
        else if (direction == Direction.Est)
            feu = ville.presenceFeuRouge(x + Constante.longueurVoiture / 2, y, x + 5 + Constante.longueurVoiture / 2, y);
        else
            feu = ville.presenceFeuRouge(x - 5 - Constante.longueurVoiture / 2, y, x - Constante.longueurVoiture / 2, y);

        return (feu != null);
    }

    private boolean chercherVoiture() {
        Voiture tmp = ville.presenceVoiture(this);
        if (tmp != null)
            feu = tmp.feu;
        return (tmp != null);
    }

    private boolean sortie() {
        return ((x < 0 && direction == Direction.Ouest) ||
                (x > ville.getLongueur() && direction == Direction.Est) ||
                (y < 0 && direction == Direction.Nord) ||
                (y > ville.getLargeur() && direction == Direction.Sud));
    }

    @Override
    public void run() {
        this.avancer();
    }
}
