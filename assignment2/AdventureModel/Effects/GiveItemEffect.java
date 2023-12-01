package AdventureModel.Effects;

import AdventureModel.AdventureObject;
import AdventureModel.Player;

public class GiveItemEffect implements EffectStrategy{
    AdventureObject object;
    public GiveItemEffect(AdventureObject object){
        this.object = object;
    }
    @Override
    public void doEffect(Player player) {
        if(!player.inventory.contains(object)){
            player.addToInventory(object);
        }
    }

    @Override
    public String getDescription() {
        return String.format("Gives you the item '%s'", object.getName());
    }
}
