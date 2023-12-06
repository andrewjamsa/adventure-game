package AdventureModel;

import AdventureModel.NPCStates.NPCState;
import AdventureModel.SideQuests.SQ_Object;
import AdventureModel.SideQuests.SQ_Question;
import AdventureModel.SideQuests.SideQuest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
/**
 * Class NPC.  Handles every action that relates to NPC.
 */
public class NPC {
    Room room;
    NPCState state;
    String name;
    String message;
    SideQuest sideQuest = null;
    boolean doSideQuest = false;
    ArrayList<String> mainAction = new ArrayList<>(Arrays.asList("CHAT", "SIDEQUEST"));

    /**
     * NPC Constructor
     * ____________________________
     * Initializes Attributes
     *
     *
     * @param room Room that corresponds to the location of the NPC
     * @param name String that corresponds to the name of the NPC
     * @param message String that corresponds to the introduction message of the NPC
     */
    public NPC(Room room, String name, String message){
        this.name = name;
        this.room = room;
        this.message = message;
    }

    /**
     * action
     * ______________________________
     * handles all actions that relates to the NPC
     *
     * @param player the player that is currently playing the AdventureGame
     * @param actionName the actionName that the player entered to interact with the NPC
     * @return String that represents the response of the NPC to Player
     */
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

    /**
     * changeState
     * _______________________
     * Setter method for State
     *
     * @param newState the new state that corresponds to the NPC
     */
    public void changeState(NPCState newState){
        this.state = newState;
    }

    /**
     * getState
     * ______________________
     * Getter method for State
     *
     * @return NPCState that corresponds to the NPC
     */
    public NPCState getState(){
        return this.state;
    }
    /**
     * getRoom
     * ______________________
     * Getter method for Room
     *
     * @return Room that corresponds to the location of the NPC
     */
    public Room getRoom(){
        return this.room;
    }
    /**
     * getName
     * ______________________
     * Getter method for State
     *
     * @return String that corresponds to the name of the NPC
     */
    public String getName(){
        return this.name;
    }

    /**
     * getMessage
     * ____________________
     * Getter method for message
     *
     * @return String that represents the introduction message of the corresponding NPC
     */
    public String getMessage(){return this.message;}

    /**
     * setSideQuest
     * _______________________
     * Setter method for sideQuest
     *
     * @param sideQuest SideQuest that will be set into the corresponding NPC
     */
    public void setSideQuest(SideQuest sideQuest){this.sideQuest = sideQuest;}

    /**
     * getSideQuest
     * ________________________
     * Getter method for sideQuest
     *
     * @return SideQuest that corresponds to the NPC
     */
    public SideQuest getSideQuest(){return this.sideQuest;}

    /**
     * getDoSideQuest
     * _____________________
     * Getter method for doSideQuest
     *
     * @return boolean that corresponds to the doSideQuest of the NPC
     */
    public boolean getDoSideQuest(){return doSideQuest;}

    /**
     * setDoSideQuest
     * _______________________
     * Setter method for doSideQuest
     *
     * @param setter boolean to set the doSideQuest of the NPC
     */
    public void setDoSideQuest(boolean setter){doSideQuest=setter;}
}
