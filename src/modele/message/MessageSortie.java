package modele.message;

import modele.agent.Agent;
import modele.agent.Voiture;

public class MessageSortie extends Message {

    private Voiture information;

    public MessageSortie(Agent emetteur, Agent destinataire) {
        super(emetteur, destinataire);
        this.performative = Performative.Inform;
    }

    public MessageSortie(Agent emetteur, Agent destinataire, Voiture information) {
        this(emetteur, destinataire);
        this.information = information;
    }

    public Voiture getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return destinataire + " <- dehors";
    }
}
