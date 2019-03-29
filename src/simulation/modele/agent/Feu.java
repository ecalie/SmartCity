package simulation.modele.agent;

import observer.Observable;
import simulation.modele.Couleur;
import simulation.modele.Direction;
import simulation.modele.Ville;
import simulation.modele.message.Message;
import simulation.modele.message.MessageChangementEtat;
import simulation.modele.message.MessageNombreVoitures;

public class Feu extends Observable implements Agent {

    private int x;
    private int y;
    private Direction orientation;
    private Couleur couleur;
    private Ville ville;
    private int nbVoitures;

    public Feu(int x, int y, Direction orientation, Ville ville) {
        this.x = x;
        this.y = y;
        this.orientation = orientation;
        this.couleur = Couleur.Rouge;
        this.ville = ville;
        this.nbVoitures = 0;
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

    public int getNbVoitures() {
        return nbVoitures;
    }

    @Override
    public void run() {
        int oldNb = nbVoitures;
        while (true) {
            nbVoitures = ville.nombreVoituresFeu(this);
            if (nbVoitures != oldNb) {
                this.envoyerMessage(new MessageNombreVoitures(this, TourControle.getInstance(), nbVoitures));
                oldNb = nbVoitures;
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