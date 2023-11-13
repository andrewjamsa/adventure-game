package AdventureModel.PlayerHealth;

public abstract class HealthObservable {

    private Integer observableHealth;

    public abstract void register(HealthObserver observer);

    public abstract void unregister(HealthObserver observer);

    public abstract void notifyObservers();


    public Integer getObservableHealth() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setObservableHealth(Integer health) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
