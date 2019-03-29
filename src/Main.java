import intelligence_artificielle.vue.FenetreLogging;
import simulation.modele.agent.Feu;
import simulation.modele.Constante;
import simulation.modele.Direction;
import simulation.modele.Route;
import simulation.modele.Ville;
import simulation.modele.agent.TourControle;
import simulation.vue.FenetreSimulation;

public class Main {

    public static void main(String[] args) {
        Ville ville = new Ville(500, 500);

     /*   ville.ajouter(new Route(0, 50, 500, Constante.largeurRoute));
        ville.ajouter(new Route(100, 0, Constante.largeurRoute, 50));
        ville.ajouter(new Route(250, 100, Constante.largeurRoute, 150));
        ville.ajouter(new Route(300, 200, 100, Constante.largeurRoute));
        ville.ajouter(new Route(350, 250, Constante.largeurRoute, 250));
        ville.ajouter(new Route(0, 200, 150, Constante.largeurRoute));
        ville.ajouter(new Route(0, 350, 350, Constante.largeurRoute));
        ville.ajouter(new Route(100, 250, Constante.largeurRoute, 100));
        ville.ajouter(new Route(100, 400, Constante.largeurRoute, 100));

        int[][] positionsFeux = {{95,87},{112,45},{155,62},{245,87},{305,62},{287,105},{362,345},{345,387},{387,405},
                {155,362},{95,387},{112,345},{137,405}};
        
        Direction[] orientationsFeux = {Direction.Ouest, Direction.Nord, Direction.Est, Direction.Ouest, Direction.Est,
                Direction.Sud, Direction.Nord, Direction.Ouest, Direction.Sud, Direction.Est, Direction.Ouest,
                Direction.Nord, Direction.Sud};

        List<Feu> feux = new ArrayList<>();

        for (int i = 0 ; i < positionsFeux.length ; i++)
            feux.add(new Feu(positionsFeux[i][0], positionsFeux[i][1], orientationsFeux[i]));

        for(Feu f : feux)
            ville.ajouter(f);*/

        ville.ajouter(new Route(0, 200, 440, Constante.largeurRoute));
        ville.ajouter(new Route(200, 0, Constante.largeurRoute, 200));
        ville.ajouter(new Route(200, 240, Constante.largeurRoute, 200));

        ville.ajouter(new Feu(200, 230, Direction.Est, ville));
        ville.ajouter(new Feu(210, 200, Direction.Sud, ville));
        ville.ajouter(new Feu(240, 210, Direction.Ouest, ville));
        ville.ajouter(new Feu(230, 240, Direction.Nord, ville));

        TourControle.initialiserInstance(ville.getFeux());
        new FenetreSimulation(ville);
        FenetreLogging.getInstance().setVisible(true);
    }
}
