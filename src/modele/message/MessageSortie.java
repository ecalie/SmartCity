package modele.message;

import modele.agent.Feu;
import modele.agent.TourControle;
import modele.agent.Voiture;

public class MessageSortie extends Message {

    private Feu information;

    public MessageSortie(Voiture emetteur, TourControle destinataire, Feu information) {
        super(emetteur, destinataire);
        this.information = information;
        this.performative = Performative.Inform;
    }
    public Feu getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return destinataire + " <- dehors du" + information;
    }
}
