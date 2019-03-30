package modele.agent;

import modele.Couleur;
import modele.Direction;
import modele.Ville;
import modele.message.Message;
import observer.Observable;
import modele.message.MessageChangementEtat;
import modele.message.MessageUtilite;

public class Feu extends Observable implements Agent {

    private int x;
    private int y;
    private Direction orientation;
    private Couleur couleur;
    private Ville ville;
    private int utilite;

    public Feu(int x, int y, Direction orientation, Ville ville) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.couleur = Couleur.Rouge;
        this.ville = ville;
        this.utilite = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Couleur getCouleur() {
        return couleur;
    }

    public Direction getOrientation() {
        return orientation;
    }

    public int getUtilite() {
        return utilite;
    }

    @Override
    public void run() {
        int oldNb = utilite;
        while (true) {
            utilite = ville.distancesVoituresFeu(this);
            if (utilite != oldNb) {
                this.envoyerMessage(new MessageUtilite(this, TourControle.getInstance(), utilite));
                oldNb = utilite;
            }
        }
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
        return "Feu (" + this.x + "," + this.y + ")";
    }
}