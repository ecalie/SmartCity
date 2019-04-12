package modele;

import modele.agent.Feu;
import modele.agent.Voiture;

import java.util.ArrayList;
import java.util.List;

public class Ville {
    private List<Route> routes;
    private List<Feu> feux;
    private List<Voiture> voitures;
    private int longueur;
    private int largeur;
    private List<PointEntree> pointsEntrees;

    public Ville(int longueur, int largeur) {
        this.longueur = longueur;
        this.largeur = largeur;
        routes = new ArrayList<>();
        feux = new ArrayList<>();
        voitures = new ArrayList<>();
        pointsEntrees = new ArrayList<>();
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

    public PointEntree randomPointEntree() {
        int random = (int) (Math.random() * this.pointsEntrees.size());
        PointEntree pt = this.pointsEntrees.get(random);

        // décaler le points pour éviter que deux voitures ne soient au même point au même moment
        if (pt.getX() <= 0)
            this.pointsEntrees.set(random, new PointEntree(pt.getX() - 1, pt.getY(), pt.getDirection(), pt.getPatch()));
        else if (pt.getX() >= longueur)
            this.pointsEntrees.set(random, new PointEntree(pt.getX() + 1, pt.getY(), pt.getDirection(), pt.getPatch()));
        else if (pt.getY() <= 0)
            this.pointsEntrees.set(random, new PointEntree(pt.getX(), pt.getY() - 1, pt.getDirection(), pt.getPatch()));
        else if (pt.getY() >= largeur)
            this.pointsEntrees.set(random, new PointEntree(pt.getX(), pt.getY() + 1, pt.getDirection(), pt.getPatch()));

        return pt.copie();
    }

    public void ajouter(Route route) {
        this.routes.add(route);

        // ajouter un point d'entrée si besoin
        if (route.getPosition().getX() == 0)
            this.pointsEntrees.add(new PointEntree(0, route.getPosition().getY() + Constante.largeurRoute * 3 / 4, Direction.Est, routes));
        if (route.getPosition().getX() + route.getLongueur() == this.longueur)
            this.pointsEntrees.add(new PointEntree(this.longueur, route.getPosition().getY() + Constante.largeurRoute / 4, Direction.Ouest, routes));

        if (route.getPosition().getY() == 0)
            this.pointsEntrees.add(new PointEntree(route.getPosition().getX() + Constante.largeurRoute / 4, 0, Direction.Sud, routes));
        if (route.getPosition().getY() + route.getLargeur() == this.largeur)
            this.pointsEntrees.add(new PointEntree(route.getPosition().getX() + Constante.largeurRoute * 3 / 4, this.largeur, Direction.Nord, routes));
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
