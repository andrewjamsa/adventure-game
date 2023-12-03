package AdventureModel.SideQuests;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.NPC;

import java.util.Objects;

public interface SideQuest {
    public NPC npc = null;
    public String type = null;
    public String question = null;
    public default String getType(){ return this.type;}
    public default String getQuestion(){return this.question;}
}
