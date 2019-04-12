import modele.*;
import modele.agent.Feu;
import vue.FenetreMessages;
import vue.FenetreSimulation;
import vue.GraphiqueTempsAttente;

public class Main {

    public static void main(String[] args) {
        Ville ville = new Ville(440, 440);

        ville.ajouter(new Route(new Point(0, 200), 200, Constante.largeurRoute));
        ville.ajouter(new Route(new Point(200, 200), 40, Constante.largeurRoute));
        ville.ajouter(new Route(new Point(240, 200), 200, Constante.largeurRoute));
        ville.ajouter(new Route(new Point(200, 0), Constante.largeurRoute, 200));
        ville.ajouter(new Route(new Point(200, 240), Constante.largeurRoute, 200));

        ville.ajouter(new Feu(new Point(200, 230), Direction.Est));
        ville.ajouter(new Feu(new Point(210, 200), Direction.Sud));
        ville.ajouter(new Feu(new Point(240, 210), Direction.Ouest));
        ville.ajouter(new Feu(new Point(230, 240), Direction.Nord));

        new FenetreSimulation(ville);
        FenetreMessages.getInstance().setVisible(true);
        new GraphiqueTempsAttente();
    }
}
