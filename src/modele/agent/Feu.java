package modele.agent;

import modele.Couleur;
import modele.Direction;
import modele.Point;
import modele.message.Message;
import modele.message.MessageChangementEtat;
import observer.Observable;

public class Feu extends Observable implements Agent {

    private Point position;
    private Direction orientation;
    private Couleur couleur;

    public Feu(Point position, Direction orientation) {
        this.position = position;
        this.orientation = orientation;

        if (orientation == Direction.Est | orientation == Direction.Ouest)
            this.couleur = Couleur.Vert;
        else
            this.couleur = Couleur.Rouge;
    }

    public Point getPosition() {
        return position;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public Direction getOrientation() {
        return orientation;
    }


    @Override
    public void envoyerMessage(Message message) {
        message.envoyer();
    }

    @Override
    public void traiterMessage(Message message) {
        if (message instanceof MessageChangementEtat) {
            MessageChangementEtat m = (MessageChangementEtat) message;
            this.couleur = m.getInformation();
            this.notifier();
        }
    }


    @Override
    public String toString() {
        return "Feu (" + this.position + ")";
    }

}