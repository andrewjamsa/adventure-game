package AdventureModel.NPCStates;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;
import AdventureModel.SideQuests.SQ_Object;
import AdventureModel.SideQuests.SQ_Question;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NPCStateHasObject implements NPCState {
    NPC npc = null;
    ArrayList<String> actionList = null;
    ArrayList<AdventureObject> objects = null;
    public NPCStateHasObject(NPC npc, ArrayList<AdventureObject> objects){
        this.npc = npc;
        this.objects = objects;
        this.actionList = new ArrayList<String>(Arrays.asList("GIVE", "TAKE", "CHAT", "ASK", "SIDEQUEST", "SING", "ITEM")); // accept for side quest (?)
    }
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
        }
        return "I don't understand what are you saying";
    }
    public void giveObject(Player player, AdventureObject object){
        // add object from NPC inventory and
        // remove object to player's inventory
        this.objects.add(object);
        player.inventory.remove(object);
    }
    public void takeObject(Player player, AdventureObject object){
        // remove object from NPC inventory and
        // add object to player's inventory
        this.objects.remove(object);
        if (this.objects.isEmpty()){
            this.npc.changeState(new NPCStateNoObject(npc));
        }
        player.addToInventory(object);
    }
    public ArrayList<String> getActionList() {
        return NPCState.super.getActionList(); // may cause potential issue
    }
    public String getNPCState() {return "NPCStateHasObject";}
    public void giveReward(Player player, String object){
        for (AdventureObject obj: objects) {
            if (Objects.equals(obj.getName(), object)) {
                player.inventory.add(obj);
                objects.remove(obj);
            }
        }
    }
}
