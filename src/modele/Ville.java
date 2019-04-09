package modele;

import modele.agent.Feu;
import modele.agent.Voiture;

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

    public Direction getPointEntree(Point point) {
        for (Point pt : pointsEntrees.keySet())
            if (point.equals(pt))
                return pointsEntrees.get(pt);
        return null;
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
        return nouveau.copie();
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

    public void ajouter(Voiture voiture) {
        this.voitures.add(voiture);
    }

    public void removeAllVoitures() {
        this.voitures.clear();
    }

    public Feu presenceFeu(Point position, Direction direction) {
        for (Feu f : feux)
            if (f.getOrientation() == direction)
                if (((direction == Direction.Nord || direction == Direction.Ouest) &&
                        position.distance(f.getPosition()) < Constante.champVisionFeu && position.distance(f.getPosition()) > 0) ||
                        ((direction == Direction.Sud || direction == Direction.Est) &&
                                f.getPosition().distance(position) < Constante.champVisionFeu && f.getPosition().distance(position) > 0))
                    return f;
        return null;
    }

    public boolean presenceVoiture(Point position, Direction direction) {
        for (Voiture v : voitures) {
            if (direction == v.getDirection())
                if (((direction == Direction.Nord || direction == Direction.Ouest) &&
                        position.distance(v.getPosition()) <= 5 + Constante.longueurVoiture && position.distance(v.getPosition()) > 0) ||
                        ((direction == Direction.Sud || direction == Direction.Est) &&
                                v.getPosition().distance(position) <= 5 + Constante.longueurVoiture && v.getPosition().distance(position) > 0))
                    return true;
        }
        return false;
    }
}
