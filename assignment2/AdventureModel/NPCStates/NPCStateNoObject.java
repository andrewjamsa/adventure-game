package AdventureModel.NPCStates;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;
import AdventureModel.SideQuests.SQ_Object;
import AdventureModel.SideQuests.SQ_Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NPCStateNoObject implements NPCState {
    NPC npc = null;
    ArrayList<String> actionList = null;
    public NPCStateNoObject(NPC npc){
        this.npc = npc;
        this.actionList = new ArrayList<String>(Arrays.asList("GIVE", "TAKE", "CHAT", "SIDEQUEST", "ITEM")); // accept for side quest (?)
    }
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
        } /*else if (Objects.equals(actionName[1], "SIDEQUEST")){
            if (this.npc.getSideQuest()==null){
                return "I don't have any side quest for you";
            } else if (Objects.equals(this.npc.getSideQuest().getType(), "QNA")) {
                SQ_Question casted_SQ_Question = (SQ_Question) this.npc.getSideQuest();
                return casted_SQ_Question.getQuestion();
            } else if (Objects.equals(this.npc.getSideQuest().getType(), "OBJECT")) {
                SQ_Object casted_SQ_Object = (SQ_Object) this.npc.getSideQuest();
                return casted_SQ_Object.getQuestion();
            }
        }*/

        return "I don't understand what are you saying";
    }
    public void giveObject(Player player, AdventureObject object){
        // add object from NPC inventory and
        // remove object to player's inventory
        ArrayList<AdventureObject> objectList = new ArrayList<>();
        objectList.add(object);
        this.npc.changeState(new NPCStateHasObject(npc, objectList));
        player.inventory.remove(object);
    }
    /*
    public void takeObject(Player player, AdventureObject object){
        // remove object from NPC inventory and
        // add object to player's inventory
        this.objects.remove(object);
        player.addToInventory(object);
    }
    // note from andrew: i don't think this will be necessary since npc already has no obj.
    */
    public ArrayList<String> getActionList() {
        return NPCState.super.getActionList();// may cause potential issue
    }
    public String getNPCState() {return "NPCStateNoObject";}
}
