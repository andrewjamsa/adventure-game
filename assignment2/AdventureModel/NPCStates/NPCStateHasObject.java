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
 * Class NPCStateHasObject. This class stores the state of the NPC.
 */
public class NPCStateHasObject implements NPCState {
    NPC npc = null;
    ArrayList<String> actionList = null;
    ArrayList<AdventureObject> objects = null;

    /**
     * NPCStateHasObject Constructor
     * ____________________________________
     * Initializes Attributes
     *
     * @param npc NPC that corresponds to the state
     * @param objects ArrayList of the AdventureObjects owned by the NPC
     */
    public NPCStateHasObject(NPC npc, ArrayList<AdventureObject> objects){
        this.npc = npc;
        this.objects = objects;
        this.actionList = new ArrayList<String>(Arrays.asList("GIVE", "TAKE", "CHAT", "ASK", "SIDEQUEST", "SING", "ITEM")); // accept for side quest (?)
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
        if (this.actionList.contains(actionName[1])){
            if (Objects.equals(actionName[1], "CHAT")){
                return this.npc.getMessage();
            } else if (Objects.equals(actionName[1], "ITEM")) {
                StringBuilder returner = new StringBuilder("I have");
                for (AdventureObject obj: objects){
                    returner.append(" ").append(obj.getName());
                }
                returner.append(".");
                return returner.toString();
            } else if (Objects.equals(actionName[1], "GIVE")) {
                AdventureObject givenObj = null;
                if (player.checkIfObjectInInventory(actionName[2])){
                    for (AdventureObject obj: player.inventory){
                        if (Objects.equals(obj.getName(), actionName[2])){
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
                AdventureObject takenObj = null;
                for (AdventureObject obj: this.objects){
                    if (obj.getName().equalsIgnoreCase(actionName[2])) {
                        takenObj = obj;
                    }
                }
                if (takenObj==null){
                    return "I DON'T HAVE THAT OBJECT";
                }
                if (npc.getSideQuest()!=null && npc.getSideQuest().actionInterfere(takenObj.getName())){
                    return "YOU NEED TO COMPLETE MY SIDEQUEST FIRST!";
                }
                takeObject(player, takenObj);
                return "Here it is, the "+takenObj.getName();
            }
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
        this.objects.add(object);
        player.inventory.remove(object);
    }
    /**
     * takeObject
     * ________________________
     * remove object from NPC and add the object into player's inventory
     *
     * @param player Player that is currently in the AdventureGame
     * @param object AdventureObject that is being taken away from NPC
     */
    public void takeObject(Player player, AdventureObject object){
        // remove object from NPC inventory and
        // add object to player's inventory
        this.objects.remove(object);
        if (this.objects.isEmpty()){
            this.npc.changeState(new NPCStateNoObject(npc));
        }
        player.addToInventory(object);
    }

    /**
     * getActionList
     * __________________________
     * Getter method for actionList
     *
     * @return ArrayList that contains the actions available for the NPC
     */
    public ArrayList<String> getActionList() {
        return NPCState.super.getActionList(); // may cause potential issue
    }

    /**
     * getNPCState
     * __________________________
     * Getter method for the state of the NPC
     *
     * @return String that represents the state of the NPC
     */
    public String getNPCState() {return "NPCStateHasObject";}
    /**
     * giveReward
     * __________________________
     * give the reward of the side quest to the player
     *
     * @param player Player that is currently in the AdventureGame
     * @param object String representation of the reward of the side quest
     */
    public void giveReward(Player player, String object){
        for (AdventureObject obj: objects) {
            if (Objects.equals(obj.getName(), object)) {
                player.inventory.add(obj);
                objects.remove(obj);
            }
        }
    }
}
