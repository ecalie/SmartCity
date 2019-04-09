package modele.message;

import modele.agent.Agent;
import modele.agent.Voiture;

public class MessageDepart extends Message {

    private Voiture information;

    public MessageDepart(Agent emetteur, Agent destinataire) {
        super(emetteur, destinataire);
        this.performative = Performative.Inform;
    }

    public MessageDepart(Agent emetteur, Agent destinataire, Voiture information) {
        this(emetteur, destinataire);
        this.information = information;
    }

    public Voiture getInformation() {
        return information;
    }

    @Override
    public String toString() {
        return destinataire + " <- -1 voiture";
    }
}
