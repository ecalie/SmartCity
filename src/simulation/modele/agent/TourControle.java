package simulation.modele.agent;

import simulation.modele.Constante;
import simulation.modele.Couleur;
import simulation.modele.Direction;
import simulation.modele.message.Message;
import simulation.modele.message.MessageChangementEtat;
import simulation.modele.message.MessageNombreVoitures;
import simulation.modele.message.Performative;

import java.util.HashMap;
import java.util.List;

public class TourControle implements Agent {

    private static TourControle instance;
    /**
     * Nombre de voitures arrêtées à chaque feu.
     */
    private HashMap<Feu, Integer> nbVoituresFeux;

    private TourControle(List<Feu> feux) {
        this.nbVoituresFeux = new HashMap<>();
        for (Feu f : feux)
            nbVoituresFeux.put(f, 0);
    }

    public static TourControle getInstance() {
        assert (instance != null);
        return instance;
    }

    public static void initialiserInstance(List<Feu> feux) {
        instance = new TourControle(feux);
    }

    public Feu getFeuByPosition(int x, int y) {
        for (Feu f : nbVoituresFeux.keySet())
            if (f.getX() == x && f.getY() == y)
                return f;
        return null;
    }

    @Override
    public void envoyerMessage(Message message) {
        message.envoyer();
    }

    @Override
    public void traiterMessage(Message message) {
        new Thread(() -> {
            System.out.println(message);
            if (message instanceof MessageNombreVoitures) {
                assert (message.getPerformative() == Performative.Inform);

                MessageNombreVoitures m = (MessageNombreVoitures) message;

                // Récupérer le nombre de voitures du feu émetteur + feu d'en face
                // Et des dex feux sur les côtés
                int nbVoitures = m.getInformation();
                int nbVoituresCote = 0;

                Feu f1 = (Feu) m.getEmetteur();
                Feu f2 = null;
                Feu f3 = null;
                Feu f4 = null;
                this.nbVoituresFeux.put(f1, nbVoitures);

                if (f1.getOrientation() == Direction.Nord) {
                    f2 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute / 2, f1.getY() - Constante.largeurRoute);
                    if (f2 != null)
                        nbVoitures += nbVoituresFeux.get(f2);

                    f3 = this.getFeuByPosition(f1.getX() + Constante.largeurRoute / 4, f1.getY() - Constante.largeurRoute * 3 / 4);
                    if (f3 != null)
                        nbVoituresCote += nbVoituresFeux.get(f3);

                    f4 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute * 3 / 4, f1.getY() - Constante.largeurRoute / 4);
                    if (f4 != null)
                        nbVoituresCote += nbVoituresFeux.get(f4);

                } else if (f1.getOrientation() == Direction.Sud) {
                    f2 = this.getFeuByPosition(f1.getX() + Constante.largeurRoute / 2, f1.getY() + Constante.largeurRoute);
                    if (f2 != null)
                        nbVoitures += nbVoituresFeux.get(f2);

                    f3 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute / 4, f1.getY() + Constante.largeurRoute * 3 / 4);
                    if (f3 != null)
                        nbVoituresCote += nbVoituresFeux.get(f3);

                    f4 = this.getFeuByPosition(f1.getX() + Constante.largeurRoute * 3 / 4, f1.getY() + Constante.largeurRoute / 4);
                    if (f4 != null)
                        nbVoituresCote += nbVoituresFeux.get(f4);

                } else if (f1.getOrientation() == Direction.Ouest) {
                    f2 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute, f1.getY() + Constante.largeurRoute / 2);
                    if (f2 != null)
                        nbVoitures += nbVoituresFeux.get(f2);

                    f3 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute / 4, f1.getY() + Constante.largeurRoute * 3 / 4);
                    if (f3 != null)
                        nbVoituresCote += nbVoituresFeux.get(f3);

                    f4 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute * 3 / 4, f1.getY() - Constante.largeurRoute / 4);
                    if (f4 != null)
                        nbVoituresCote += nbVoituresFeux.get(f4);

                } else if (f1.getOrientation() == Direction.Est) {
                    f2 = this.getFeuByPosition(f1.getX() + Constante.largeurRoute, f1.getY() - Constante.largeurRoute / 2);
                    if (f2 != null)
                        nbVoitures += nbVoituresFeux.get(f2);

                    f3 = this.getFeuByPosition(f1.getX() + Constante.largeurRoute / 4, f1.getY() - Constante.largeurRoute * 3 / 4);
                    if (f3 != null)
                        nbVoituresCote += nbVoituresFeux.get(f3);

                    f4 = this.getFeuByPosition(f1.getX() + Constante.largeurRoute * 3 / 4, f1.getY() + Constante.largeurRoute / 4);
                    if (f4 != null)
                        nbVoituresCote += nbVoituresFeux.get(f4);

                }

                if (nbVoituresCote == 0 || (nbVoitures <= nbVoituresCote && nbVoitures > 0)) {
                    this.envoyerMessage(new MessageChangementEtat(this, f1, Couleur.Vert));
                    this.envoyerMessage(new MessageChangementEtat(this, f2, Couleur.Vert));

                    this.envoyerMessage(new MessageChangementEtat(this, f3, Couleur.Rouge));
                    this.envoyerMessage(new MessageChangementEtat(this, f4, Couleur.Rouge));
                } else {
                    this.envoyerMessage(new MessageChangementEtat(this, f3, Couleur.Vert));
                    this.envoyerMessage(new MessageChangementEtat(this, f4, Couleur.Vert));

                    this.envoyerMessage(new MessageChangementEtat(this, f1, Couleur.Rouge));
                    this.envoyerMessage(new MessageChangementEtat(this, f2, Couleur.Rouge));
                }
            }
        }).start();
    }

    @Override
    public void run() {

    }
}
