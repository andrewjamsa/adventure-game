package AdventureModel.Effects;

import AdventureModel.AdventureObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.*;

public class EffectFactory {
    static public Map<String, AdventureObject> objects = new HashMap<>();

    /**
     * Generates the EffectStrategy based on the given string.
     * The string has to be in the format '["effectName",effectinput1,effectinput2, ...]'
     * For example '["multipleEffect",["damageEffect", -1], ["unhideAllEffect"]]'
     * @param effectString the effect string
     * @return the generated EffectStrategy
     */
    static public EffectStrategy generateEffect(String effectString){
        try{
            JSONArray arr = (JSONArray) new JSONParser().parse(effectString);
            return generateFromArray(arr);
        }
        catch(Exception e){
            return null;
        }
    }

    static private EffectStrategy generateFromArray(JSONArray arr){
        if(arr.get(0).equals("damageEffect")){
            return new DamageEffect(((Long) arr.get(1)).intValue());
        } else if (arr.get(0).equals("giveItemEffect")) {
            return new  GiveItemEffect(objects.get(arr.get(1)));
        } else if (arr.get(0).equals("hideableEffect")) {
            return new HideableEffect(generateFromArray((JSONArray) arr.get(1)));
        } else if (arr.get(0).equals("multipleEffects")) {
            List<EffectStrategy> effects = new ArrayList<>();
            for (ListIterator it = arr.listIterator(1); it.hasNext(); ) {
                effects.add(generateFromArray((JSONArray) it.next()));
            }
            return new MultipleEffects(effects);
        } else if (arr.get(0).equals("randomEffect")) {
            List<EffectStrategy> effects = new ArrayList<>();
            List<Double> weights = new ArrayList<>();
            for (int i = 1; i < arr.size(); i+=2) {
                JSONArray effArr = (JSONArray) arr.get(i);
                Double weight = (Double) arr.get(i+1);
                effects.add(generateFromArray(effArr));
                weights.add(weight);
            }
            return new RandomEffect(effects, weights);
        } else if (arr.get(0).equals("unhideAllEffect")) {
            return new UnhideAllEffect();
        }
        return null;
    }
}
