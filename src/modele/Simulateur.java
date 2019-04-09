package modele;

import modele.agent.Feu;
import modele.agent.TourControle;
import modele.agent.Voiture;

import java.util.ArrayList;
import java.util.List;

public class Simulateur {

    private List<Thread> threadsVoitures;
    private Ville ville;

    public Simulateur(Ville ville) {
        this.ville = ville;
        this.threadsVoitures = new ArrayList<>();
    }

    public void initialiser(int nbVoitures) {
        threadsVoitures.clear();
        ville.removeAllVoitures();

        TourControle.go(ville.getFeux());

        for (int i = 0; i < nbVoitures; i++) {
            Voiture v = new Voiture(ville);
            Thread t = new Thread(v);
            threadsVoitures.add(t);
        }

        for (Thread t : threadsVoitures)
            t.start();
    }

    public void pause() {
        for (Thread t : threadsVoitures)
            t.stop();
    }
}
