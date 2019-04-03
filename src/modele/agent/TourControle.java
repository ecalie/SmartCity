package modele.agent;

import modele.Constante;
import modele.Couleur;
import modele.Direction;
import modele.message.Message;
import vue.FenetreLogging;
import modele.message.MessageChangementEtat;
import modele.message.MessageUtilite;
import modele.message.Performative;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TourControle implements Agent {

    private static TourControle instance;
    /**
     * Nombre de voitures arrêtées à chaque feu.
     */
    private HashMap<Feu, Integer> utilites;
    private List<List<Feu>> intersections;

    private TourControle(List<Feu> feux) {
        this.utilites = new HashMap<>();
        for (Feu f : feux)
            utilites.put(f, 0);
        initialiserIntersection();
    }

    public static TourControle getInstance() {
        assert (instance != null);
        return instance;
    }

    public static void go(List<Feu> feux) {
        instance = new TourControle(feux);
    }

    private void initialiserIntersection() {
        this.intersections = new ArrayList<>();
        for (Feu f1 : utilites.keySet()) {
            if (f1.getOrientation() == Direction.Nord) {
                List<Feu> liste = new ArrayList<>();
                liste.add(f1);

                Feu f2 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute / 2, f1.getY() - Constante.largeurRoute);
                if (f2 != null)
                    liste.add(f2);

                Feu f3 = this.getFeuByPosition(f1.getX() + Constante.largeurRoute / 4, f1.getY() - Constante.largeurRoute * 3 / 4);
                if (f3 != null)
                    liste.add(f3);

                Feu f4 = this.getFeuByPosition(f1.getX() - Constante.largeurRoute * 3 / 4, f1.getY() - Constante.largeurRoute / 4);
                if (f4 != null)
                    liste.add(f4);

                intersections.add(liste);
            }

        }
    }

    public Feu getFeuByPosition(int x, int y) {
        for (Feu f : utilites.keySet())
            if (f.getX() == x && f.getY() == y)
                return f;
        return null;
    }

    @Override
    public void envoyerMessage(Message message) {
        if (message instanceof MessageChangementEtat &&
                ((Feu)message.getDestinataire()).getCouleur() != ((MessageChangementEtat) message).getInformation()) {
            FenetreLogging.ajouter(message.toString());
            message.envoyer();
        }
    }

    @Override
    public void traiterMessage(Message message) {
        FenetreLogging.ajouter(message.toString());
        if (message instanceof MessageUtilite) {
            assert (message.getPerformative() == Performative.Inform);

            MessageUtilite m = (MessageUtilite) message;
            this.utilites.put((Feu) m.getEmetteur(), m.getInformation());

            for (List<Feu> liste : intersections) {
                if (liste.contains(m.getEmetteur())) {
                    int nbVoituresVertical = 0;
                    int nbVoituresHorizontal = 0;

                    for (Feu f : liste) {
                        if (f.getOrientation() == Direction.Nord || f.getOrientation() == Direction.Sud)
                            nbVoituresVertical += f.getUtilite();
                        else
                            nbVoituresHorizontal += f.getUtilite();
                    }

                    if ((nbVoituresHorizontal <= nbVoituresVertical && nbVoituresHorizontal > 0) || nbVoituresVertical == 0)
                        for (Feu f : liste)
                            if (f.getOrientation() == Direction.Nord || f.getOrientation() == Direction.Sud)
                                envoyerMessage(new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Rouge));
                            else
                                envoyerMessage(new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Vert));
                    else
                        for (Feu f : liste)
                            if (f.getOrientation() == Direction.Nord || f.getOrientation() == Direction.Sud)
                                envoyerMessage(new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Vert));
                            else
                                envoyerMessage(new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Rouge));
                }
            }
        }
    }

    @Override
    public void run() {
    }
}
