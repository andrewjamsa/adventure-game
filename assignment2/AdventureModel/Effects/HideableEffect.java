package AdventureModel.Effects;

import AdventureModel.Player;

import javax.lang.model.type.NullType;
import java.util.function.Consumer;


public class HideableEffect implements EffectDecorators{
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

    @Override
    public void applyFunction(Consumer<EffectStrategy> function) {
        function.accept(effect);
        if(effect instanceof EffectDecorators){
            ((EffectDecorators) effect).applyFunction(function);
        }
    }
}
