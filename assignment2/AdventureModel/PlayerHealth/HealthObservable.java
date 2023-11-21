package AdventureModel.PlayerHealth;

public abstract class HealthObservable {

    /**
     * The health value of the player.
     */
    private Integer observableHealth;

    /**
     * This method registers an observer.
     *
     * @param observer the observer to be registered
     */
    public abstract void register(HealthObserver observer);

    /**
     * This method unregisters an observer.
     *
     * @param observer the observer to be unregistered
     * @return true if the observer was unregistered, false otherwise
     */
    public abstract boolean unregister(HealthObserver observer);

    /**
     * This method notifies all observers of a change.
     */
    public abstract void notifyObservers();


    /**
     * This method returns the health value of the player.
     *
     * @return the health value of the player
     */
    public Integer getObservableHealth() {
        return this.observableHealth;
    }

    /**
     * This method sets the health value of the player.
     *
     * @param health the health value of the player
     */
    public void setObservableHealth(Integer health) {
        if (health < 0) {
            this.observableHealth = 0;
        } else {
            this.observableHealth = health;
        }

        this.notifyObservers();
    }

}
