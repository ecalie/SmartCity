package simulation.modele;

public class Route {
    private int x;
    private int y;
    private int longueur;
    private int largeur;

    public Route(int x, int y, int longueur, int largeur) {
        this.x = x;
        this.y = y;
        this.longueur = longueur;
        this.largeur = largeur;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getLargeur() {
        return largeur;
    }
}
