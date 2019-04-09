package modele.agent;

import modele.*;
import modele.message.Message;
import modele.message.MessageArrivee;
import modele.message.MessageDepart;
import modele.message.MessageSortie;
import observer.Observable;

public class Voiture extends Observable implements Runnable, Agent {
    private Point position;
    private Direction direction;
    private Ville ville;
    private Feu feu; // le prochain feu
    private boolean arrete;
    private boolean dansIntersection;
    private boolean prioritaire;

    public Voiture(Ville ville) {
        this.ville = ville;
        this.position = ville.randomPointEntree();
        this.direction = ville.getPointEntree(this.position);
        this.prioritaire = false;
        ville.ajouter(this);
    }

    public Voiture(Ville ville, boolean prioritaire) {
        this(ville);
        this.prioritaire = prioritaire;
    }

    public Point getPosition() {
        return this.position;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isDansIntersection() {
        return dansIntersection;
    }

    public boolean isPrioritaire() {
        return prioritaire;
    }

    private void avancer() {
        while (true) {

            // regarder s'il y a un feu ou une voiture devant
            this.see();

            while (!arrete) {
                // déplacer la voiture
                if (direction == Direction.Nord)
                    position.setY(position.getY() - 1);
                else if (direction == Direction.Sud)
                    position.setY(position.getY() + 1);
                else if (direction == Direction.Est)
                    position.setX(position.getX() + 1);
                else if (direction == Direction.Ouest)
                    position.setX(position.getX() - 1);

                // Si la voiture sort de la ville, elle réapparaît à un point d'entrée quelconque
                if (sortie()) {
                    position = ville.randomPointEntree();
                    direction = ville.getPointEntree(position);
                    System.out.println(position);
                }

                this.see();

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


    private void see() {
        if (feu == null) {
            feu = ville.presenceFeu(position, direction);

            if (feu != null) {
                new MessageArrivee(this, feu).envoyer();
            }

            arrete = ville.presenceVoiture(position, direction);
        } else if (feu.getCouleur() == Couleur.Rouge) {
            if ((direction == Direction.Nord || direction == Direction.Ouest)
                    && position.distance(feu.getPosition()) < 5
                    && position.distance(feu.getPosition()) > 0)
                arrete = true;
            else if ((direction == Direction.Sud || direction == Direction.Est)
                    && position.distance(feu.getPosition()) > -5
                    && position.distance(feu.getPosition()) < 0)
                arrete = true;
            else
                arrete = ville.presenceVoiture(position, direction);

            // le feu est passé rouge après que la voiture est passée le feu
            // elle est peut-être encore dans l'intersection
            if (!arrete) {
                if ((direction == Direction.Nord || direction == Direction.Ouest)
                        && position.distance(feu.getPosition()) < -Constante.longueurVoiture - Constante.largeurRoute) {
                    // la voiture est sortie de l'intersection
                    new MessageSortie(this, feu).envoyer();
                    dansIntersection = false;
                    feu = null;
                } else if ((direction == Direction.Sud || direction == Direction.Est)
                        && position.distance(feu.getPosition()) > Constante.longueurVoiture + Constante.largeurRoute) {
                    // la voiture est sortie de l'intersection
                    new MessageSortie(this, feu).envoyer();
                    dansIntersection = false;
                    feu = null;
                }
            }

        } else if (arrete) {
            arrete = ville.presenceVoiture(position, direction);

        } else if (dansIntersection) {
            if (((direction == Direction.Nord || direction == Direction.Ouest) && position.distance(feu.getPosition()) < -Constante.longueurVoiture - Constante.largeurRoute) ||
                    ((direction == Direction.Sud || direction == Direction.Est) && position.distance(feu.getPosition()) > Constante.longueurVoiture + Constante.largeurRoute)) {
                // la voiture est sortie de l'intersection
                new MessageSortie(this, feu).envoyer();
                dansIntersection = false;
                feu = null;
            }
        } else {
            arrete = ville.presenceVoiture(position, direction);
            if (position.distance(feu.getPosition()) == 0) {
                dansIntersection = true;
                new MessageDepart(this, feu).envoyer();
            }
        }
    }

    private boolean sortie() {
        return ((position.getX() < 0 && direction == Direction.Ouest) ||
                (position.getX() > ville.getLongueur() && direction == Direction.Est) ||
                (position.getY() < 0 && direction == Direction.Nord) ||
                (position.getY() > ville.getLargeur() && direction == Direction.Sud));
    }

    @Override
    public void envoyerMessage(Message message) {
        message.envoyer();
    }

    @Override
    public void traiterMessage(Message message) {
    }

    @Override
    public void run() {
        this.avancer();
    }
}
