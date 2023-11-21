package AdventureModel.PlayerHealth;

public abstract class HealthObservable {

    /**
     * The health value of the player.
     */
    private Integer observableHealth;

    /**
     * The maximum health value of the player.
     */
    private Integer maxHealth;

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
     * @param maxHealth the maximum health value of the player
     */
    public void setMaxHealth(Integer maxHealth) {
        this.maxHealth = maxHealth;
    }

    /**
     * This method returns the maximum health value of the player.
     *
     * @return the maximum health value of the player
     */
    public Integer getMaxHealth() {
        return this.maxHealth;
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

        if (this.observableHealth > this.maxHealth) {
            this.observableHealth = this.maxHealth;
        }

        this.notifyObservers();
    }

}
