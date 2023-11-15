package AdventureModel.Effects;

import AdventureModel.Player;

public interface EffectStrategy {
    /**
     * Effect to be done on player
     *
     * @param player the player, the effect is to be done on.
     */
    void doEffect(Player player);

    /**
     * Gets the description of the effect.
     *
     * @return A string of the description of the effect.
     */
    String getDescription();
}
