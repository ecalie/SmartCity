package modele;

import modele.agent.Feu;
import modele.agent.TourControle;

import java.util.ArrayList;
import java.util.List;

public class Simulateur {

    private List<Thread> threadsVoitures;
    private boolean feuxCrees;
    private Ville ville;

    public Simulateur(Ville ville) {
        this.ville = ville;
        this.threadsVoitures = new ArrayList<>();
        this.feuxCrees = false;
    }

    public boolean isFeuxCrees() {
        return feuxCrees;
    }

    public void initialiser(int nbVoitures) {
        threadsVoitures.clear();
        ville.removeAllVoitures();

        TourControle.go(ville.getFeux());

        for (int i = 0; i < nbVoitures; i++) {
            Point pt = ville.randomPointEntree();
            Voiture v = new Voiture(pt, ville.getPointsEntrees().get(pt), ville);
            ville.ajouter(v);
            Thread t = new Thread(v);
            threadsVoitures.add(t);
        }

        for (Thread t : threadsVoitures)
            t.start();

        if (!feuxCrees) {
            feuxCrees = true;
            for (Feu f : ville.getFeux())
                new Thread(f).start();
        }
    }

    public void pause() {
        for (Thread t : threadsVoitures)
            t.stop();
    }
}
