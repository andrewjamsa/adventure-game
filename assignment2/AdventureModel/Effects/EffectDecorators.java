package AdventureModel.Effects;

import javax.lang.model.type.NullType;
import java.util.concurrent.Callable;
import java.util.function.Function;

public interface EffectDecorators extends EffectStrategy{

    /**
     * Recursively applies the inputted function to all sub effects in the decorator
     * @param function The function to be applied
     */
    void applyFunction(Function<EffectStrategy, NullType> function);
}
