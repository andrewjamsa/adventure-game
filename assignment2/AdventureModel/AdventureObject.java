package AdventureModel;

import AdventureModel.Effects.EffectStrategy;

import java.io.Serializable; //you will need this to save the game!

/**
 * This class keeps track of the props or the objects in the game.
 * These objects have a name, description, and location in the game.
 * The player with the objects can pick or drop them as they like and
 * these objects can be used to pass certain passages in the game.
 */
public class AdventureObject implements Serializable {
    /**
     * The name of the object.
     */
    private String objectName;

    /**
     * The description of the object.
     */
    private String description;

    /**
     * The location of the object.
     */
    private Room location = null;

    /**
     * The effect that is done to the player holding the object upon entering a new room
     */
    private EffectStrategy effect;

    /**
     * Adventure Object Constructor
     * ___________________________
     * This constructor sets the name, description, and location of the object.
     *
     * @param name The name of the Object in the game.
     * @param description One line description of the Object.
     * @param location The location of the Object in the game.
     */
    public AdventureObject(String name, String description, Room location){
        this.objectName = name;
        this.description = description;
        this.location = location;
    }

    /**
     * Getter method for the name attribute.
     *
     * @return name of the object
     */
    public String getName(){
        return this.objectName;
    }

    /**
     * Getter method for the description attribute.
     *
     * @return description of the game
     */
    public String getDescription(){
        if (effect == null){
            return this.description;
        }
        else{
            return String.format("%s Effect: %s", this.description, this.effect.getDescription());
        }
    }

    /**
     * This method returns the location of the object if the object is still in
     * the room. If the object has been pickUp up by the player, it returns null.
     *
     * @return returns the location of the object if the objects is still in
     * the room otherwise, returns null.
     */
    public Room getLocation(){
        return this.location;
    }

    /**
     * This method sets the location the object is in
     */
    public void setLocation(Room location){
        this.location = location;
    }

    /**
     * Sets the effect of the object
     */
    public void setEffect(EffectStrategy effect) {
        this.effect = effect;
    }

    public EffectStrategy getEffect(){
        return effect;
    }

    /**
     * Do the effect of the object
     * Does nothing if effect is not set
     */
    public void doEffect(Player player){
        if(effect == null){
            return;
        }
        this.effect.doEffect(player);
    }

}
