package AdventureModel.Effects;

import AdventureModel.Player;

public class HideableEffect implements EffectStrategy{
    private boolean hide = true;
    private EffectStrategy effect;
    public HideableEffect(EffectStrategy effect){
        this.effect = effect;
    }
    public void setHide(boolean hide){
        this.hide=hide;
    }

    @Override
    public String getDescription(){
        if(hide){
            return "???";
        }
        else {
            return effect.getDescription();
        }
    }

    @Override
    public void doEffect(Player player){
        effect.doEffect(player);
    }
}
