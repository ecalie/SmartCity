package simulation.modele.message;

import simulation.modele.agent.Agent;

public abstract class Message {

    protected Agent emetteur;
    protected Agent destinataire;
    protected Performative performative;

    public Message(Agent emetteur, Agent destinataire) {
        this.emetteur = emetteur;
        this.destinataire = destinataire;
    }

    public void envoyer() {
        destinataire.traiterMessage(this);
    }

    public Agent getEmetteur() {
        return emetteur;
    }

    public Performative getPerformative() {
        return performative;
    }
}
