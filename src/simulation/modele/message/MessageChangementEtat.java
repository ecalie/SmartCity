package simulation.modele.message;

import simulation.modele.Couleur;
import simulation.modele.agent.Agent;

public class MessageChangementEtat extends Message {

    private Couleur couleurFeu;

    public MessageChangementEtat(Agent emetteur,Agent destinataire, Couleur couleurFeu) {
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
