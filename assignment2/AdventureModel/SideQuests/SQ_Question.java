package AdventureModel.SideQuests;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;

import java.util.Objects;

/**
 * Class SQ_Question. This class stores the side quest details of the NPC. This class implements the SideQuest Interface
 */
public class SQ_Question implements SideQuest{
    public NPC npc = null;
    public String type = "QNA";
    public String question = null;
    public String hint = null;
    public String correct = null;
    public String incorrectPrompt = null;

    /**
     * SQ_Question constructor.
     * _________________________________
     * Initializes Attributes
     *
     * @param npc NPC that bears the SideQuest
     * @param question String that represents the task assigned to the player
     * @param hint String that represents the reward to be given to the player
     * @param correct String that represents the correct answer of the question
     * @param incorrectPrompt String that represents the prompt given from the NPC to Player if the answer is wrong.
     */

    public SQ_Question(NPC npc, String question, String hint, String correct, String incorrectPrompt){
        this.npc = npc;
        this.question = question;
        this.hint = hint;
        this.correct = correct;
        this.incorrectPrompt = incorrectPrompt;
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
        npc.setDoSideQuest(false);
        if (actionName[0].equalsIgnoreCase(this.correct)) {
            return hint;
        } else {
            return incorrectPrompt;
        }
    }

    /**
     * getQuestion
     * __________________________
     * Getter method for question
     *
     * @return String that represents the question prompt of the NPC's side quest
     */
    public String getQuestion() {
        return question;
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
        return false;
    }

    /**
     * getHint
     * __________________________
     * Getter method for hint
     *
     * @return String that represents the reward to be given to the player
     */
    public String getHint() {
        return hint;
    }

    /**
     * getCorrect
     * __________________________
     * Getter method for correct
     *
     * @return String that represents the correct answer of the question
     */
    public String getCorrect(){ return correct; }

    /**
     * getIncorrectPrompt
     * __________________________
     * Getter method for incorrectPrompt
     *
     * @return String that represents the prompt given from the NPC to Player if the answer is wrong.
     */
    public String getIncorrectPrompt() {
        return incorrectPrompt;
    }
}
