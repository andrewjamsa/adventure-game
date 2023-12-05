package AdventureModel.NPCStates;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;

import java.util.ArrayList;
/**
 * Class NPCState. This class stores the state of the NPC.
 */
public interface NPCState {
    NPC npc = null;
    ArrayList<String> actionList = null;

    /**
     * action
     * __________________________
     * executes the prompt that the player enters and send the results back to the player
     *
     * @param player Player that is currently in the AdventureGame
     * @param actionName String list that corresponds to the action entered by the player
     * @return String that corresponds to the result of finishing the side quest
     */
    public default String action(Player player, String[] actionName){
        return null;
    }

    /**
     * getActionList
     * __________________________
     * Getter method for actionList
     *
     * @return ArrayList that contains the actions available for the NPC
     */
    public default ArrayList<String> getActionList(){
        return actionList;
    }
    /**
     * getNPCState
     * __________________________
     * Getter method for the state of the NPC
     *
     * @return String that represents the state of the NPC
     */
    public default String getNPCState() {return "NPCState";}

    /**
     * takeObject
     * ________________________
     * remove object from NPC and add the object into player's inventory
     *
     * @param player Player that is currently in the AdventureGame
     * @param object AdventureObject that is being taken away from NPC
     */
    public default void takeObject(Player player, AdventureObject object){return;}

    /**
     * giveObject
     * __________________________
     * add object from NPC and remove the object into player's inventory
     *
     * @param player Player that is currently in the AdventureGame
     * @param object AdventureObject that is given to the NPC
     */
    public default void giveObject(Player player, AdventureObject object){return;}

    /**
     * giveReward
     * __________________________
     * give the reward of the side quest to the player
     *
     * @param player Player that is currently in the AdventureGame
     * @param object String representation of the reward of the side quest
     */
    public default void giveReward(Player player, String object) {
        return;
    }

}
