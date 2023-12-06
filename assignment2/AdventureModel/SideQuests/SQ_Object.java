package AdventureModel.SideQuests;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;

import java.util.Objects;

/**
 * Class SQ_Object. This class stores the side quest details of the NPC. This class implements the SideQuest Interface
 */
public class SQ_Object implements SideQuest{
    public NPC npc = null;
    public String type = "OBJECT";
    public String required = null;
    public String question = null;
    public String accomplished = null;
    /**
     * SQ_Object constructor.
     * _________________________________
     * Initializes Attributes
     *
     * @param npc NPC that bears the SideQuest
     * @param required String that represents the required object to be given by the player
     * @param question String that represents the task assigned to the player
     * @param accomplished String that represents the reward to be given to the player
     */
    public SQ_Object(NPC npc, String required, String question, String accomplished){
        this.npc = npc;
        this.required = required;
        this.question = question;
        this.accomplished = accomplished;
    }
    /**
     * actionInterfere
     * _________________________
     * checks if the action interferes with the side quest
     *
     * @param obj AdventureObject that corresponds to the action that is made
     * @return boolean that corresponds to whether the action interfere with the side quest
     */
    @Override
    public boolean actionInterfere(String obj) {
        return Objects.equals(obj, accomplished);
    }

    /**
     * finishSideQuest
     * _______________________
     * executes the prompt that the player enters and send the results back to the player
     *
     * @param player Player that is currently in the AdventureGame
     * @param actionName String list that corresponds to the action entered by the player
     * @return String that corresponds to the result of finishing the side quest
     */
    public String finishSideQuest(Player player, String[] actionName) {
        if (actionName.length == 3 && actionName[1].equalsIgnoreCase("GIVE")
                && actionName[2].equalsIgnoreCase(required)) {
            for (AdventureObject obj : player.inventory) {
                if (Objects.equals(obj.getName(), required)) {
                    npc.getState().giveObject(player, obj);
                    String takeAction = "NPC TAKE " + accomplished;
                    String[] takeActionStrList = takeAction.split(" ");
                    npc.getState().giveReward(player, accomplished);
                    npc.setDoSideQuest(false);
                    return "CONGRATULATIONS! YOU FINISHED MY SIDEQUEST!";
                }
            }
        }
        npc.setDoSideQuest(false);
        return "Giving up already?";
    }

    /**
     * getAccomplished
     * _______________________
     * Getter method for accomplished
     *
     * @return String representation of the reward to be given to the player
     */
    public String getAccomplished() {
        return accomplished;
    }
    /**
     * getQuestion
     * ________________________
     * Getter method for question
     *
     * @return String that represents the task for the player
     */
    public String getQuestion() {
        return question;
    }

    /**
     * getRequired
     * ________________________
     * Getter method for required
     *
     * @return String that represents required object from the player
     */
    public String getRequired() {
        return required;
    }
}
