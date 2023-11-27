package AdventureModel.Effects;

import AdventureModel.Player;

import javax.lang.model.type.NullType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MultipleEffects implements EffectDecorators{
    List<EffectStrategy> effects;

    public MultipleEffects(List<EffectStrategy> effects){
        this.effects = new ArrayList<>(effects);
    }

    @Override
    public void doEffect(Player player) {
        for (EffectStrategy effect:effects) {
            effect.doEffect(player);
        }
    }

    @Override
    public String getDescription() {
        return effects.stream().map(EffectStrategy::getDescription).collect(Collectors.joining(", "));
    }

    @Override
    public void applyFunction(Function<EffectStrategy, NullType> function) {
        for (EffectStrategy effect:effects){
            function.apply(effect);
            if (effect instanceof EffectDecorators){
                ((EffectDecorators) effect).applyFunction(function);
            }
        }
    }
}
