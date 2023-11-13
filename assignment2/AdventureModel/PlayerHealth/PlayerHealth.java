package AdventureModel.PlayerHealth;

public class PlayerHealth extends HealthObservable {

    private Observer[] observers;

    @Override
    public void register(HealthObserver observer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void unregister(HealthObserver observer) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void notifyObservers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
