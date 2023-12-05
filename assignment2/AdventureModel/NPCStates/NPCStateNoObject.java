package AdventureModel.NPCStates;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;
import AdventureModel.SideQuests.SQ_Object;
import AdventureModel.SideQuests.SQ_Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class NPCStateNoObject. This class stores the state of the NPC.
 */
public class NPCStateNoObject implements NPCState {
    NPC npc = null;
    ArrayList<String> actionList = null;
    /**
     * NPCStateNoObject Constructor
     * ____________________________________
     * Initializes Attributes
     *
     * @param npc NPC that corresponds to the state
     */
    public NPCStateNoObject(NPC npc){
        this.npc = npc;
        this.actionList = new ArrayList<String>(Arrays.asList("GIVE", "TAKE", "CHAT", "SIDEQUEST", "ITEM")); // accept for side quest (?)
    }

    /**
     * action
     * __________________________
     * executes the prompt that the player enters and send the results back to the player
     *
     * @param player Player that is currently in the AdventureGame
     * @param actionName String list that corresponds to the action entered by the player
     * @return String that corresponds to the result of finishing the side quest
     */
    public String action(Player player, String[] actionName){
        if (Objects.equals(actionName[1], "CHAT")){
            return this.npc.getMessage();
        } else if (Objects.equals(actionName[1], "ITEM")) {
            return "I don't have any object";
        } else if (Objects.equals(actionName[1], "GIVE")) {
            AdventureObject givenObj = null;
            if (player.checkIfObjectInInventory(actionName[2])){
                for (AdventureObject obj: player.inventory){
                    if (obj.getName().equalsIgnoreCase(actionName[2])){
                        givenObj = obj;
                        break;
                    }
                }
            }
            if (givenObj==null){
                return "YOU DON'T HAVE THAT OBJECT";
            }
            giveObject(player, givenObj);
            return "thank you for the "+givenObj.getName();
        } else if (Objects.equals(actionName[1], "TAKE")) {
            return "I don't have any object";
        }
        return "I don't understand what are you saying";
    }

    /**
     * giveObject
     * __________________________
     * add object from NPC and remove the object into player's inventory
     *
     * @param player Player that is currently in the AdventureGame
     * @param object AdventureObject that is given to the NPC
     */
    public void giveObject(Player player, AdventureObject object){
        // add object from NPC inventory and
        // remove object to player's inventory
        ArrayList<AdventureObject> objectList = new ArrayList<>();
        objectList.add(object);
        this.npc.changeState(new NPCStateHasObject(npc, objectList));
        player.inventory.remove(object);
    }

    /**
     * getActionList
     * __________________________
     * Getter method for actionList
     *
     * @return ArrayList that contains the actions available for the NPC
     */
    public ArrayList<String> getActionList() {
        return NPCState.super.getActionList();// may cause potential issue
    }

    /**
     * getNPCState
     * __________________________
     * Getter method for the state of the NPC
     *
     * @return String that represents the state of the NPC
     */
    public String getNPCState() {return "NPCStateNoObject";}
}
