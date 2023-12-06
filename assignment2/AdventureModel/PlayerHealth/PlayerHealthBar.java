package AdventureModel.PlayerHealth;

import AdventureModel.Player;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;

import java.util.concurrent.Callable;

public class PlayerHealthBar extends HealthObserver {

    /**
     * The observer that observes the player's health.
     */
    private final ProgressBar healthBar;

    /**
     * Custom on change method.
     */
    private Callable<?> customOnChange;

    public PlayerHealthBar(Player player, Callable<?> customOnChange) {
        super(player.health);

        this.customOnChange = customOnChange;

        healthBar = new ProgressBar();

        // --- styling ---
        healthBar.setPrefHeight(50);
        healthBar.setPrefWidth(650);
        healthBar.setStyle("-fx-accent: #17871b"); // default color
        healthBar.setPadding(new Insets(0, 100, 0, 100));

        // --- accessibility ---
        healthBar.setAccessibleText("Health Bar");
        healthBar.setAccessibleHelp("This is the health bar");
        healthBar.setAccessibleRoleDescription("This is the health bar");

        updateBar();
    }

    /**
     * This method returns the health bar.
     */
    public void updateBar() {
        this.getBar().setProgress(this.getHealthPercentage());
    }

    /**
     * This method returns the health bar.
     *
     * @return the health bar
     */
    public ProgressBar getBar() {
        return this.healthBar;
    }

    @Override
    public void onChange() {
        if (this.healthBar == null) return;

        updateBar();

        try {
            customOnChange.call();
        } catch (Exception e) {
            // ignore exception
        }

    }
}
