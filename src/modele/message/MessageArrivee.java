package modele.message;

import modele.agent.Agent;
import modele.agent.Voiture;

public class MessageArrivee extends Message {

    private Voiture information;

    public MessageArrivee(Agent emetteur, Agent destinataire, Voiture information) {
        this(emetteur, destinataire);
        this.information = information;
    }

    public Voiture getInformation() {
        return information;
    }

    public MessageArrivee(Agent emetteur, Agent destinataire) {
        super(emetteur, destinataire);
        this.performative = Performative.Inform;
    }

    @Override
    public String toString() {
        return destinataire + " <- +1 voiture";
    }
}
