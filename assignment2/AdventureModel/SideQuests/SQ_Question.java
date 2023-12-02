package AdventureModel.SideQuests;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;

public class SQ_Question implements SideQuest{
    public NPC npc = null;
    public String type = "QNA";
    public String question = null;
    public String hint = null;
    public String incorrect = null;
    public SQ_Question(NPC npc, String question, String hint, String incorrect){
        this.npc = npc;
        this.question = question;
        this.hint = hint;
        this.incorrect = incorrect;
    }

    public String getQuestion() {
        return question;
    }

    public String getHint() {
        return hint;
    }

    public String getIncorrect() {
        return incorrect;
    }
}
