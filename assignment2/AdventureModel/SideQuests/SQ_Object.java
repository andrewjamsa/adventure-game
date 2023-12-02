package AdventureModel.SideQuests;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;

public class SQ_Object implements SideQuest{
    public NPC npc = null;
    public String type = "OBJECT";
    public String required = null;
    public String question = null;
    public String accomplished = null;
    public SQ_Object(NPC npc, String required, String question, String accomplished){
        this.npc = npc;
        this.required = required;
        this.question = question;
        this.accomplished = accomplished;
    }

    public String getAccomplished() {
        return accomplished;
    }

    public String getQuestion() {
        return question;
    }

    public String getRequired() {
        return required;
    }
}
