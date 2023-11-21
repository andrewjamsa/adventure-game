package AdventureModel.PlayerHealth;

import java.util.ArrayList;
import java.util.List;

public class PlayerHealth extends HealthObservable {

    /**
     * The list of observers.
     */
    private List<Observer> observers;

    public PlayerHealth(Integer health, Integer maxHealth) {
        this.observers = new ArrayList<>(); // initialize the list of observers
        this.setMaxHealth(maxHealth); // set the max health value
        this.setObservableHealth(health); // set the health value
    }

    @Override
    public void register(HealthObserver observer) {
        this.observers.add(observer);
        this.notifyObservers();
    }

    @Override
    public boolean unregister(HealthObserver observer) {
        return this.observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update(this.getObservableHealth());
        }
    }
}
