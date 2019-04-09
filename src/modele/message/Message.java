package modele.message;

import modele.agent.Agent;
import vue.FenetreLogging;

public abstract class Message {

    protected Agent emetteur;
    protected Agent destinataire;
    protected Performative performative;

    public Message(Agent emetteur, Agent destinataire) {
        this.emetteur = emetteur;
        this.destinataire = destinataire;
    }

    public void envoyer() {
        FenetreLogging.ajouter(this.toString());
        destinataire.traiterMessage(this);
    }

    public Agent getEmetteur() {
        return emetteur;
    }

    public Agent getDestinataire() {
        return destinataire;
    }

}
