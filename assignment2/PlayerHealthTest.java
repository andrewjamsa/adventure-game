import AdventureModel.PlayerHealth.HealthObserver;
import AdventureModel.PlayerHealth.PlayerHealth;
import org.junit.jupiter.api.Test;

public class PlayerHealthTest {

    @Test
    void testObservable() {
        PlayerHealth health = new PlayerHealth(100);

        assert health.getObservableHealth() == 100;

        health.setObservableHealth(50);
        assert health.getObservableHealth() == 50;

        health.setObservableHealth(0);
        assert health.getObservableHealth() == 0;
    }

    @Test
    void testObservers() {
        PlayerHealth playerHealth = new PlayerHealth(100);

        HealthObserver observer = new HealthObserver(playerHealth);

        assert observer.getHealth() == 100;

        playerHealth.setObservableHealth(50);
        assert observer.getHealth() == 50;
        assert observer.isAlive();

        playerHealth.setObservableHealth(0);
        assert observer.getHealth() == 0;
        assert !observer.isAlive();

        // test unregister
        assert playerHealth.unregister(observer);

        playerHealth.setObservableHealth(100);

        assert observer.getHealth() == 0;

        // test invalid object ot unregister
        assert !playerHealth.unregister(observer);
    }

}
