package AdventureModel.Effects;

import AdventureModel.Player;

public class DamageEffect implements EffectStrategy{
    int damage;

    /**
     * Initializes an effect that damages or heals the player
     * @param damage the damage or healing to be done. Negative number for damage and positive for healing.
     */
    public DamageEffect(int damage){
        this.damage= damage;
    }
    @Override
    public void doEffect(Player player) {
        player.addHealth(damage);
    }

    @Override
    public String getDescription() {
        if(damage < 0){
            return String.format("Damages you %s health everytime you move", (-damage));
        }
        else if(damage > 0){
            return String.format("Heals you %s health everytime you move", damage);
        }
        else {
            return "Does nothing";
        }
    }
}
