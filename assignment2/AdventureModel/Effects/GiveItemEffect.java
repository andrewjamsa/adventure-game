package AdventureModel.Effects;

import AdventureModel.AdventureObject;
import AdventureModel.Player;

public class GiveItemEffect implements EffectStrategy{
    AdventureObject object;
    boolean done = false;
    public GiveItemEffect(AdventureObject object){
        this.object = object;
    }
    @Override
    public void doEffect(Player player) {
        if(!player.inventory.contains(object) && !done){
            player.addToInventory(object);
            done = true;
        }
    }

    @Override
    public String getDescription() {
        return String.format("Gives you the item '%s'", object.getName());
    }
}
