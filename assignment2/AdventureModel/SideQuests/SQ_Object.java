package AdventureModel.SideQuests;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;

import java.util.Objects;

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

    @Override
    public boolean actionInterfere(String obj) {
        return Objects.equals(obj, accomplished);
    }

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
        return "Giving up already?";
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
