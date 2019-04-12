package modele.agent;

import modele.Constante;
import modele.Couleur;
import modele.Direction;
import modele.Point;
import modele.message.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TourControle implements Agent {

    private static TourControle instance;
    /**
     * Nombre de voituresParFeu arrêtées à chaque feu.
     */
    private List<List<Feu>> intersections;
    private List<ThreadSlave> esclaves;

    private TourControle(List<Feu> feux) {
        initialiserIntersection(feux);

        esclaves = new ArrayList<>();
        for (List<Feu> l : intersections)
            esclaves.add(new ThreadSlave(l));
    }

    public static TourControle getInstance() {
        assert (instance != null);
        return instance;
    }

    public static void go(List<Feu> feux) {
        instance = new TourControle(feux);
    }

    private void initialiserIntersection(List<Feu> feux) {
        this.intersections = new ArrayList<>();
        for (Feu f1 : feux) {
            if (f1.getOrientation() == Direction.Nord) {
                List<Feu> liste = new ArrayList<>();
                liste.add(f1);

                Feu f2 = this.getFeuByPosition(
                        f1.getPosition().getX() - Constante.largeurRoute / 2,
                        f1.getPosition().getY() - Constante.largeurRoute,
                        feux);
                if (f2 != null)
                    liste.add(f2);

                Feu f3 = this.getFeuByPosition(
                        f1.getPosition().getX() + Constante.largeurRoute / 4,
                        f1.getPosition().getY() - Constante.largeurRoute * 3 / 4,
                        feux);
                if (f3 != null)
                    liste.add(f3);

                Feu f4 = this.getFeuByPosition(
                        f1.getPosition().getX() - Constante.largeurRoute * 3 / 4,
                        f1.getPosition().getY() - Constante.largeurRoute / 4,
                        feux);
                if (f4 != null)
                    liste.add(f4);

                intersections.add(liste);
            }
        }
    }

    private Feu getFeuByPosition(int x, int y, List<Feu> feux) {
        for (Feu f : feux)
            if (f.getPosition().equals(new Point(x, y)))
                return f;
        return null;
    }

    @Override
    public void envoyerMessage(Message message) {
        if (message instanceof MessageChangementEtat &&
                ((Feu) message.getDestinataire()).getCouleur() != ((MessageChangementEtat) message).getInformation())
            message.envoyer();
    }

    @Override
    public void traiterMessage(Message message) {
        if (message instanceof MessageArrivee)
            this.traiterArrivee((MessageArrivee) message);
        else if (message instanceof MessageDepart)
            this.traiterDepart((MessageDepart) message);
        else if (message instanceof MessageSortie)
            this.traiterSortie((MessageSortie) message);
    }

    private void traiterArrivee(MessageArrivee message) {
        for (ThreadSlave ts : esclaves)
            if (ts.getVoituresParFeux().keySet().contains(message.getInformation())) {
                ts.stop();
                ts.ajouterVoiture((Voiture) message.getEmetteur(), message.getInformation());
                ts = new ThreadSlave(ts.getVoituresParFeux());
                ts.start();
            }
    }

    private void traiterDepart(MessageDepart message) {
        for (ThreadSlave ts : esclaves)
            if (ts.getVoituresParFeux().keySet().contains(message.getInformation())) {
                ts.stop();
                ts = new ThreadSlave(ts.getVoituresParFeux());
                ts.start();
            }
    }

    private void traiterSortie(MessageSortie message) {
        for (ThreadSlave ts : esclaves)
            if (ts.getVoituresParFeux().keySet().contains(message.getInformation())) {
                ts.stop();
                ts.retirerVoiture((Voiture) message.getEmetteur(), message.getInformation());
                ts = new ThreadSlave(ts.getVoituresParFeux());
                ts.start();
            }
    }

    @Override
    public String toString() {
        return "tour de controle";
    }
}

class ThreadSlave extends Thread {

    private HashMap<Feu, List<Voiture>> voituresParFeux;

    public ThreadSlave(HashMap<Feu, List<Voiture>> voituresParFeux) {
        this.voituresParFeux = voituresParFeux;
    }

    public ThreadSlave(List<Feu> feux) {
        this.voituresParFeux = new HashMap<>();
        for (Feu f : feux)
            voituresParFeux.put(f, new ArrayList<>());
    }

    public HashMap<Feu, List<Voiture>> getVoituresParFeux() {
        return voituresParFeux;
    }

    public void ajouterVoiture(Voiture voiture, Feu feu) {
        this.voituresParFeux.get(feu).add(voiture);
    }

    public void retirerVoiture(Voiture voiture, Feu feu) {
        this.voituresParFeux.get(feu).remove(voiture);
    }

    @Override
    public void run() {
        int utiliteHorizontale = 0;

        //calculer l'utilité
        double minV = 2000;
        double minH = 2000;
        // chercher la voiture la plus proche de son feu
        for (Feu f : voituresParFeux.keySet()) {
            for (Voiture v : voituresParFeux.get(f))
                if (!v.isDansIntersection()) {
                    double dist;
                    if (f.getOrientation() == Direction.Nord)
                        dist = v.getPosition().distance(f.getPosition());
                    else if (f.getOrientation() == Direction.Sud)
                        dist = -v.getPosition().distance(f.getPosition());
                    else if (f.getOrientation() == Direction.Est)
                        dist = -v.getPosition().distance(f.getPosition());
                    else
                        dist = v.getPosition().distance(f.getPosition());

                    if ((f.getOrientation() == Direction.Nord || f.getOrientation() == Direction.Sud) && dist > 0 && dist < minV)
                        minV = dist;
                    else if ((f.getOrientation() == Direction.Est || f.getOrientation() == Direction.Ouest) && dist > 0 && dist < minH)
                        minH = dist;
                }
        }

        // ajouter l'utilité selon la distance de la voiture la plus proche
        if (minH < 2000)
            utiliteHorizontale += (Constante.champVisionFeu - minH);
        if (minV < 2000)
            utiliteHorizontale -= (Constante.champVisionFeu - minV);

        // regarder s'il y a des voitures dans l'intersection
        boolean intersectionVide = true;
        for (Feu f : voituresParFeux.keySet())
            for (Voiture v : voituresParFeux.get(f))
                if (v.isDansIntersection()) {
                    if (f.getOrientation() == Direction.Nord || f.getOrientation() == Direction.Sud)
                        utiliteHorizontale -= 1000;
                    else
                        utiliteHorizontale += 1000;
                    intersectionVide = false;
                }

        // regarder si une voiture est prioritaire en approche
        for (Feu f : voituresParFeux.keySet())
            for (Voiture v : voituresParFeux.get(f))
                if (v.isPrioritaire() && !v.isDansIntersection())
                    if (v.getDirection() == Direction.Nord || v.getDirection() == Direction.Sud)
                        utiliteHorizontale -= 5000;
                    else
                        utiliteHorizontale += 5000;


        // changer les couleurs des feux selon l'utilité
        if (utiliteHorizontale > 0) {
            for (Feu f : voituresParFeux.keySet())
                if (f.getOrientation() == Direction.Nord || f.getOrientation() == Direction.Sud)
                    TourControle.getInstance().envoyerMessage(
                            new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Rouge));
                else if (intersectionVide)
                    TourControle.getInstance().envoyerMessage(
                            new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Vert));
        } else {
            for (Feu f : voituresParFeux.keySet())
                if (f.getOrientation() == Direction.Ouest || f.getOrientation() == Direction.Est)
                    TourControle.getInstance().envoyerMessage(
                            new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Rouge));
                else if (intersectionVide)
                    TourControle.getInstance().envoyerMessage(
                            new MessageChangementEtat(TourControle.getInstance(), f, Couleur.Vert));
        }
    }
}