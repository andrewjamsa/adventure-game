package AdventureModel.PlayerHealth;

public class HealthObserver implements Observer {

    /**
     * The health value of the player.
     */
    private Integer health;

    /**
     * The subject that is being observed.
     */
    private HealthObservable subject;

    public void HealthObservable(HealthObservable subject) {
        this.subject = subject;

        // register as an observer
        if (!this.subject.register(this)) {
            throw new IllegalArgumentException("Could not register as an observer.");
        }
    }


    /**
     * This method returns the health value of the player.
     *
     * @return the health value of the player
     */
    public Integer getHealth() {
        return this.health;
    }

    /**
     * This method returns whether the player is alive or not.
     *
     * @return true if the player is alive, false otherwise
     */
    public boolean isAlive() {
        return this.getHealth() > 0;
    }


    @Override
    public void update(Integer value) {
        this.health = value;
    }
}
