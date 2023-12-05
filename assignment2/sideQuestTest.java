import AdventureModel.*;
import AdventureModel.SideQuests.SQ_Object;
import AdventureModel.SideQuests.SQ_Question;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class sideQuestTest {
    @Test
    void SQFactoryTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        assertNull(adventureGame.npcHashMap.get(2).getSideQuest());
        assertTrue(adventureGame.npcHashMap.get(1).getSideQuest() instanceof SQ_Question);
        assertTrue(adventureGame.npcHashMap.get(3).getSideQuest() instanceof SQ_Object);
    }
    @Test
    void SQFinishSideQuestTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        adventureGame.interpretAction("west");
        adventureGame.interpretAction("npc take torch");
        adventureGame.interpretAction("east");
        adventureGame.interpretAction("in");
        adventureGame.interpretAction("npc sidequest");
        assertEquals(adventureGame.interpretAction("npc give torch"),
                "CONGRATULATIONS! YOU FINISHED MY SIDEQUEST!");

    }

    @Test
    void SQActionInterfereTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        assertTrue(adventureGame.npcHashMap.get(3).getSideQuest().actionInterfere("IPAD"));
    }
}
