package modele.message;

import modele.agent.Agent;

public class MessageUtilite extends Message {
    private int utilite;

    public MessageUtilite(Agent emetteur, Agent destinataire, int utilite) {
        super(emetteur, destinataire);
        this.utilite = utilite;
        this.performative = Performative.Inform;
    }

    public int getInformation() {
        return this.utilite;
    }

    @Override
    public String toString() {
        return emetteur + " -> " + utilite;
    }
}
