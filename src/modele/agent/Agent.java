package modele.agent;

import modele.message.Message;

public interface Agent {

    void envoyerMessage(Message message);

    void traiterMessage(Message message);
}
