package modele.message;

import modele.Couleur;
import modele.agent.Feu;
import modele.agent.TourControle;

public class MessageChangementEtat extends Message {

    private Couleur couleurFeu;

    public MessageChangementEtat(TourControle emetteur, Feu destinataire, Couleur couleurFeu) {
        super(emetteur, destinataire);
        this.couleurFeu = couleurFeu;
        this.performative = Performative.Request;
    }

    public Couleur getInformation() {
        return couleurFeu;
    }

    @Override
    public String toString() {
        return destinataire + " <- " + couleurFeu;
    }
}
