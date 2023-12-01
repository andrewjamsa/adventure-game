package AdventureModel.Effects;
import AdventureModel.AdventureObject;

import javax.json.*;
import java.io.StringReader;
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
            JsonReader jsonReader = Json.createReader(new StringReader(effectString));
            JsonArray arr = jsonReader.readArray();
            jsonReader.close();
            return generateFromArray(arr);
        }
        catch(Exception e){
            return null;
        }
    }

    static private EffectStrategy generateFromArray(JsonArray arr){
        if(arr.getString(0).equals("damageEffect")){
            return new DamageEffect(arr.getInt(1));
        } else if (arr.getString(0).equals("giveItemEffect")) {
            return new  GiveItemEffect(objects.get(arr.getString(1)));
        } else if (arr.getString(0).equals("hideableEffect")) {
            return new HideableEffect(generateFromArray(arr.getJsonArray(1)));
        } else if (arr.getString(0).equals("multipleEffects")) {
            List<EffectStrategy> effects = new ArrayList<>();
            for (ListIterator<JsonValue> it = arr.listIterator(1); it.hasNext(); ) {
                JsonValue value = it.next();
                effects.add(generateFromArray(value.asJsonArray()));
            }
            return new MultipleEffects(effects);
        } else if (arr.getString(0).equals("randomEffect")) {
            List<EffectStrategy> effects = new ArrayList<>();
            List<Double> weights = new ArrayList<>();
            for (int i = 1; i < arr.size(); i+=2) {
                JsonArray effArr = arr.getJsonArray(i);
                Double weight = arr.getJsonNumber(i+1).doubleValue();
                effects.add(generateFromArray(effArr));
                weights.add(weight);
            }
            return new RandomEffect(effects, weights);
        } else if (arr.getString(0).equals("unhideAllEffect")) {
            return new UnhideAllEffect();
        }
        return null;
    }
}
