package modele.agent;

import modele.message.Message;

public interface Agent extends Runnable {

    void envoyerMessage(Message message);

    void traiterMessage(Message message);
}
