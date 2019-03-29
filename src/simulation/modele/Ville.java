package simulation.modele;

import simulation.modele.agent.Feu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ville {
    private List<Route> routes;
    private List<Feu> feux;
    private List<Voiture> voitures;
    private int longueur;
    private int largeur;
    private HashMap<Point, Direction> pointsEntrees;

    public Ville(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
        routes = new ArrayList<>();
        feux = new ArrayList<>();
        voitures = new ArrayList<>();
        pointsEntrees = new HashMap<>();
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public List<Feu> getFeux() {
        return feux;
    }

    public List<Voiture> getVoitures() {
        return voitures;
    }

    public HashMap<Point, Direction> getPointsEntrees() {
        return pointsEntrees;
    }

    public Point randomPointEntree() {
        int random = (int) (Math.random() * this.pointsEntrees.keySet().size());
        Point pt = (Point) this.pointsEntrees.keySet().toArray()[random];
        Direction dir = this.pointsEntrees.get(pt);

        // décaler le points pour éviter que deux voitures ne soient au même point au même moment
        this.pointsEntrees.remove(pt);
        Point nouveau = null;
        if (pt.getX() <= 0)
            nouveau = new Point(pt.getX() - 1, pt.getY());
        else if (pt.getX() >= longueur)
            nouveau = new Point(pt.getX() + 1, pt.getY());
        else if (pt.getY() <= 0)
            nouveau = new Point(pt.getX(), pt.getY() - 1);
        else if (pt.getY() >= largeur)
            nouveau = new Point(pt.getX(), pt.getY() + 1);

        this.pointsEntrees.put(nouveau, dir);
        return nouveau;
    }

    public void ajouter(Route route) {
        this.routes.add(route);

        if (route.getX() == 0)
            this.pointsEntrees.put(new Point(0, route.getY() + Constante.largeurRoute * 3 / 4), Direction.Est);
        if (route.getX() + route.getLongueur() == this.longueur)
            this.pointsEntrees.put(new Point(this.longueur, route.getY() + Constante.largeurRoute / 4), Direction.Ouest);

        if (route.getY() == 0)
            this.pointsEntrees.put(new Point(route.getX() + Constante.largeurRoute / 4, 0), Direction.Sud);
        if (route.getY() + route.getLargeur() == this.largeur)
            this.pointsEntrees.put(new Point(route.getX() + Constante.largeurRoute * 3 / 4, this.largeur), Direction.Nord);
    }

    public void ajouter(Feu feu) {
        this.feux.add(feu);
    }

    /**
     * La voiture entre dans la ville
     */
    public void ajouter(Voiture voiture) {
        this.voitures.add(voiture);
    }

    public void removeAllVoitures() {
        this.voitures.clear();
    }

    public Feu presenceFeuRouge(int x1, int y1, int x2, int y2) {
        assert (x1 <= x2);
        assert (y1 <= y2);

        for (Feu f : feux)
            if (x1 <= f.getX() && f.getX() <= x2 && y1 <= f.getY() && f.getY() <= y2 && f.getCouleur() == Couleur.Rouge)
                return f;
        return null;
    }

    public Voiture presenceVoiture(Voiture voiture) {
        for (Voiture v : voitures) {
            if (v != voiture) {
                if (voiture.getDirection() == Direction.Nord && v.getX() == voiture.getX() &&
                        v.getY() <= voiture.getY() && v.getY() >= voiture.getY() - Constante.longueurVoiture - 5)
                    return v;
                if (voiture.getDirection() == Direction.Sud && v.getX() == voiture.getX() &&
                        v.getY() >= voiture.getY() && v.getY() <= voiture.getY() + Constante.longueurVoiture + 5)
                    return v;
                if (voiture.getDirection() == Direction.Est && v.getY() == voiture.getY() &&
                        v.getX() >= voiture.getX() && v.getX() <= voiture.getX() + Constante.longueurVoiture + 5)
                    return v;
                if (voiture.getDirection() == Direction.Ouest && v.getY() == voiture.getY() &&
                        v.getX() <= voiture.getX() && v.getX() >= voiture.getX() - Constante.longueurVoiture - 5)
                    return v;
            }
        }
        return null;
    }

    public int nombreVoituresFeu(Feu feu) {
        int res = 0;
        for (Voiture v : voitures)
            if (v.getDirection() == feu.getOrientation())
                if (feu.getOrientation() == Direction.Nord && v.getX() == feu.getX() && v.getY() >= feu.getY() - Constante.largeurRoute && v.getY() <= feu.getY() + Constante.champVisionFeu)
                    res++;
                else if (feu.getOrientation() == Direction.Sud && v.getX() == feu.getX() && v.getY() <= feu.getY() + Constante.largeurRoute && v.getY() >= feu.getY() - Constante.champVisionFeu)
                    res++;
                else if (feu.getOrientation() == Direction.Ouest && v.getY() == feu.getY() && v.getX() >= feu.getX() - Constante.largeurRoute && v.getX() <= feu.getX() + Constante.champVisionFeu)
                    res++;
                else if (feu.getOrientation() == Direction.Est && v.getY() == feu.getY() && v.getX() <= feu.getX() + Constante.largeurRoute && v.getX() >= feu.getX() - Constante.champVisionFeu)
                    res++;
        return res;
    }
}
