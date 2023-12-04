package AdventureModel.SideQuests;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;

import java.util.Objects;

public class SQ_Question implements SideQuest{
    public NPC npc = null;
    public String type = "QNA";
    public String question = null;
    public String hint = null;
    public String correct = null;
    public String incorrectPrompt = null;
    public SQ_Question(NPC npc, String question, String hint, String correct, String incorrectPrompt){
        this.npc = npc;
        this.question = question;
        this.hint = hint;
        this.correct = correct;
        this.incorrectPrompt = incorrectPrompt;
    }

    public String finishSideQuest(Player player, String[] actionName) {
        npc.setDoSideQuest(false);
        if (actionName[0].equalsIgnoreCase(this.correct)) {
            return hint;
        } else {
            return incorrectPrompt;
        }
    }


    public String getQuestion() {
        return question;
    }

    @Override
    public boolean actionInterfere(String obj) {
        return false;
    }

    public String getHint() {
        return hint;
    }
    public String getCorrect(){ return correct; }
    public String getIncorrectPrompt() {
        return incorrectPrompt;
    }
}
