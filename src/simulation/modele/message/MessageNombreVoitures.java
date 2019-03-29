package simulation.modele.message;

import simulation.modele.agent.Agent;

public class MessageNombreVoitures extends Message {
    private int nbVoitures;

    public MessageNombreVoitures(Agent emetteur, Agent destinataire, int nbVoitures) {
        super(emetteur, destinataire);
        this.nbVoitures = nbVoitures;
        this.performative = Performative.Inform;
    }

    public int getInformation() {
        return this.nbVoitures;
    }

    @Override
    public String toString() {
        return emetteur + " -> " + nbVoitures + " voitures";
    }
}
