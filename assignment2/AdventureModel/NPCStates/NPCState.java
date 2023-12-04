package AdventureModel.NPCStates;

import AdventureModel.AdventureObject;
import AdventureModel.NPC;
import AdventureModel.Player;

import java.util.ArrayList;

public interface NPCState {
    NPC npc = null;
    ArrayList<String> actionList = null;
    public default String action(Player player, String[] actionName){
        return null;
    }
    public default ArrayList<String> getActionList(){
        return actionList;
    }
    public default String getNPCState() {return "NPCState";}
    public default void takeObject(Player player, AdventureObject object){return;}
    public default void giveObject(Player player, AdventureObject object){return;}

    public default void giveReward(Player player, String object) {
        return;
    }

}
