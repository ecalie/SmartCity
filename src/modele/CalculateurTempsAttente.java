package modele;

import observer.Observable;

import java.util.ArrayList;
import java.util.List;

public class CalculateurTempsAttente extends Observable {

    private static CalculateurTempsAttente instance;
    private List<Integer> tempsAttente;

    private CalculateurTempsAttente() {
        this.tempsAttente = new ArrayList<>();
    }

    public static CalculateurTempsAttente getInstance() {
        if (instance == null)
            instance = new CalculateurTempsAttente();
        return instance;
    }

    public List<Integer> getTempsAttente() {
        return tempsAttente;
    }

    public void ajouterTempsAttente(int temps) {
        this.tempsAttente.add(temps);
        this.notifier();
    }
}
