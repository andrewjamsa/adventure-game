package AdventureModel.Effects;

import AdventureModel.Player;

import javax.lang.model.type.NullType;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

class WeightedRandom<E>{
    public final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total;
    public WeightedRandom(){
        random = new Random();
    }
    public void add(double weight, E value){
        if(weight <= 0){
            return;
        }
        total += weight;
        map.put(total, value);
    }

    public E next(){
        return map.ceilingEntry(random.nextDouble()*total).getValue();
    }
}

public class RandomEffect implements EffectDecorators{
    WeightedRandom<EffectStrategy> weightedRandom;
    EffectStrategy now;

    /**
     * Initializes a new RandomEffect
     *
     * @param effects The list of EffectStrategy to be done
     * @param weight The list of weights in the same order of the effects
     */
    public RandomEffect(List<EffectStrategy> effects, List<Double> weight){
        weightedRandom = new WeightedRandom<>();
        for (int i = 0; i < effects.size(); i++) {
            weightedRandom.add(weight.get(i), effects.get(i));
        }
    }

    @Override
    public void doEffect(Player player) {
        now = weightedRandom.next();
        now.doEffect(player);
    }

    @Override
    public String getDescription() {
        return String.format("Random assortment of effects. Current effect: %s", now.getDescription());
    }

    @Override
    public void applyFunction(Consumer<EffectStrategy> function) {
        for (EffectStrategy effect:weightedRandom.map.values()){
            function.accept(effect);
            if (effect instanceof EffectDecorators){
                ((EffectDecorators) effect).applyFunction(function);
            }
        }
    }
}
