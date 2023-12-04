package AdventureModel;

import AdventureModel.NPCStates.NPCState;
import AdventureModel.SideQuests.SQ_Object;
import AdventureModel.SideQuests.SQ_Question;
import AdventureModel.SideQuests.SideQuest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class NPC {
    Room room;
    NPCState state;
    String name;
    String message;
    SideQuest sideQuest = null;
    boolean doSideQuest = false;
    ArrayList<String> mainAction = new ArrayList<>(Arrays.asList("CHAT", "SIDEQUEST"));

    public NPC(Room room, String name, String message){
        this.name = name;
        this.room = room;
        this.message = message;
    }
    public String action(Player player, String[] actionName){
        if (doSideQuest){
            return sideQuest.finishSideQuest(player, actionName);
        }
        if (actionName.length==1){
            return "Hi, what can I do for you?";
        }
        if (this.mainAction.contains(actionName[1])){
            if (Objects.equals(actionName[1], "CHAT")){
                return message;
            } else if (Objects.equals(actionName[1], "SIDEQUEST")){
                if (sideQuest==null){
                    return "I don't have any side quest for you";
                } else {
                    doSideQuest=true;
                    return sideQuest.getQuestion();
                }
            }
        }
        return state.action(player, actionName);
    }

    public void changeState(NPCState newState){
        this.state = newState;
    }
    public NPCState getState(){
        return this.state;
    }
    public String getMessage(){return this.message;}
    public void setSideQuest(SideQuest sideQuest){this.sideQuest = sideQuest;}
    public SideQuest getSideQuest(){return this.sideQuest;}
    public boolean getDoSideQuest(){return doSideQuest;}
    public void setDoSideQuest(boolean setter){doSideQuest=setter;}
}
