package modele;

public class Route {
    private Point position;
    private int longueur;
    private int largeur;

    public Route(Point point, int longueur, int largeur) {
        this.position = point;
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public Point getPosition() {
        return position;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }
}
