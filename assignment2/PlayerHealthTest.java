import AdventureModel.AdventureGame;
import AdventureModel.PlayerHealth.HealthObserver;
import AdventureModel.PlayerHealth.PlayerHealth;
import org.junit.jupiter.api.Test;

public class PlayerHealthTest {

    @Test
    void testObservable() {
        PlayerHealth health = new PlayerHealth(100, 100);

        assert health.getObservableHealth() == 100;

        health.setObservableHealth(50);
        assert health.getObservableHealth() == 50;

        health.setObservableHealth(0);
        assert health.getObservableHealth() == 0;

        health.setObservableHealth(150);
        assert health.getObservableHealth() == 100;
    }

    @Test
    void testObservers() {
        PlayerHealth playerHealth = new PlayerHealth(100, 100);

        final boolean[] onChangeEntered = {false};
        HealthObserver observer = new HealthObserver(playerHealth) {
            @Override
            public void onChange() {
                onChangeEntered[0] = true;
            }
        };

        assert onChangeEntered[0];

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

    @Test
    void gameTest() {
        AdventureGame game = new AdventureGame("TinyGame", 100, 100);

        // direct access to player health

        assert game.getPlayer().getHealthValue() == 100;

        game.getPlayer().setHealth(50);
        assert game.getPlayer().getHealthValue() == 50;

        game.getPlayer().setHealth(0);
        assert game.getPlayer().getHealthValue() == 0;

        // access through observer

        HealthObserver observer = new HealthObserver(game.getPlayer().health) {
            @Override
            public void onChange() {
                // do nothing
            }
        };

        assert observer.getHealth() == 0;
        assert !observer.isAlive();

        game.getPlayer().setHealth(100);

        assert observer.getHealth() == 100;
        assert observer.isAlive();
    }

}

