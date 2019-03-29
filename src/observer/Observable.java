package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable {

    private List<Observer> observers;

    public Observable() {
        this.observers = new ArrayList<>();
    }

    public void ajouterObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void supprimerObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void notifier() {
        for (Observer o : observers)
            o.update();
    }
}
