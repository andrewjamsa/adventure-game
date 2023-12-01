package AdventureModel.PlayerHealth;

import AdventureModel.Player;
import javafx.geometry.Insets;
import javafx.scene.control.ProgressBar;

public class PlayerHealthBar extends ProgressBar {

    /**
     * The observer that observes the player's health.
     */
    private HealthObserver observer;

    public PlayerHealthBar() {
        super();

        // --- styling ---
        setPrefHeight(50);
        setPrefWidth(650);
        setStyle("-fx-accent: #17871b"); // default color
        setPadding(new Insets(0, 100, 0, 100));

        // --- accessibility ---
        setAccessibleText("Health Bar");
        setAccessibleHelp("This is the health bar");
        setAccessibleRoleDescription("This is the health bar");
    }

    /**
     * This method links the player to the health bar.
     *
     * @param player the player to be linked
     */
    public void linkPlayer(Player player) {
        this.observer = new HealthObserver(player.health);
        this.update();
    }

    /**
     * This method updates the health bar.
     */
    public void update() {
        setProgress(this.observer.getHealthPercentage());
    }

}
