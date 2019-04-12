package modele.message;

import modele.agent.Feu;
import modele.agent.TourControle;
import modele.agent.Voiture;

public class MessageDepart extends Message {

    private Feu information;

    public MessageDepart(Voiture emetteur, TourControle destinataire, Feu information) {
        super(emetteur, destinataire);
        this.information = information;
        this.performative = Performative.Inform;
    }

    public Feu getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return destinataire + " <- -1 voiture au " + information;
    }
}
