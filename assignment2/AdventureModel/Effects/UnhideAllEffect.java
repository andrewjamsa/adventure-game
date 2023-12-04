package AdventureModel.Effects;

import AdventureModel.AdventureObject;
import AdventureModel.Player;

public class UnhideAllEffect implements EffectStrategy{
    private void unide(EffectStrategy effect){
        if(effect instanceof HideableEffect){
            ((HideableEffect) effect).setHide(false);
        }
    }

    @Override
    public void doEffect(Player player) {
        for(AdventureObject i: player.inventory){
            unide(i.getEffect());
            if(i instanceof EffectDecorators){
                ((EffectDecorators) i).applyFunction(this::unide);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Inspects all the objects you have and reveals their effects";
    }
}
