import modele.*;
import vue.FenetreMessages;
import modele.agent.Feu;
import vue.FenetreSimulation;
import vue.GraphiqueTempsAttente;

public class Main {

    public static void main(String[] args) {
        Ville ville = new Ville(440, 440);
       /* Ville ville = new Ville(400, 500);

        ville.ajouter(new Route(0, 40, 400, Constante.largeurRoute));
        ville.ajouter(new Route(100, 0, Constante.largeurRoute, 40));
        ville.ajouter(new Route(250, 80, Constante.largeurRoute, 150));
        ville.ajouter(new Route(290, 190, 70, Constante.largeurRoute));
        ville.ajouter(new Route(320, 230, Constante.largeurRoute, 270));
        ville.ajouter(new Route(0, 200, 150, Constante.largeurRoute));
        ville.ajouter(new Route(0, 340, 350, Constante.largeurRoute));
        ville.ajouter(new Route(110, 240, Constante.largeurRoute, 100));
        ville.ajouter(new Route(110, 380, Constante.largeurRoute, 120));

        int[][] positionsFeux = {{100,70},{110,40},{140,50},{250,70},{290,50},{280,80},{330,340},{320,370},{350,380},
                {150,350},{110,370},{120,340},{140,380}};
        
        Direction[] orientationsFeux = {Direction.Ouest, Direction.Nord, Direction.Est, Direction.Ouest, Direction.Est,
                Direction.Sud, Direction.Nord, Direction.Ouest, Direction.Sud, Direction.Est, Direction.Ouest,
                Direction.Nord, Direction.Sud};

        List<Feu> feux = new ArrayList<>();

        for (int i = 0 ; i < positionsFeux.length ; i++)
            feux.add(new Feu(positionsFeux[i][0], positionsFeux[i][1], orientationsFeux[i], ville));

        for(Feu f : feux)
            ville.ajouter(f);*/

        ville.ajouter(new Route(0, 200, 440, Constante.largeurRoute));
        ville.ajouter(new Route(200, 0, Constante.largeurRoute, 200));
        ville.ajouter(new Route(200, 240, Constante.largeurRoute, 200));

        ville.ajouter(new Feu(new Point(200, 230), Direction.Est));
        ville.ajouter(new Feu(new Point(210, 200), Direction.Sud));
        ville.ajouter(new Feu(new Point(240, 210), Direction.Ouest));
        ville.ajouter(new Feu(new Point(230, 240), Direction.Nord));

        new FenetreSimulation(ville);
        FenetreMessages.getInstance().setVisible(true);
        new GraphiqueTempsAttente();
    }
}
