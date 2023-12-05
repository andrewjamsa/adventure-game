package AdventureModel.SideQuests;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;

import java.util.Objects;

/**
 * Class SideQuest. This class stores the side quest details of the NPC.
 */
public interface SideQuest {
    public NPC npc = null;
    public String type = null;
    public String question = null;

    /**
     * getType
     * ________________________
     * Getter method for type
     *
     *
     * @return String that represents the type of the side quest
     */
    public default String getType(){ return this.type;}

    /**
     * getQuestion
     * ________________________
     * Getter method for question
     *
     * @return String that represents the task for the player
     */
    public default String getQuestion(){return this.question;}

    /**
     * actionInterfere
     * _________________________
     * checks if the action interferes with the side quest
     *
     * @param obj AdventureObject that corresponds to the action that is made
     * @return boolean that corresponds to whether the action interfere with the side quest
     */
    public boolean actionInterfere(String obj);

    /**
     * finishSideQuest
     * _______________________
     * executes the prompt that the player enters and send the results back to the player
     *
     * @param player Player that is currently in the AdventureGame
     * @param actionName String list that corresponds to the action entered by the player
     * @return String that corresponds to the result of finishing the side quest
     */
    public default String finishSideQuest(Player player, String[] actionName){ return null;}
}
