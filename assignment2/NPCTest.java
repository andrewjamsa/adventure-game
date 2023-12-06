import AdventureModel.*;
import AdventureModel.Effects.*;
import AdventureModel.NPCStates.NPCStateHasObject;
import AdventureModel.NPCStates.NPCStateNoObject;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/*
 * Class NPCTest. This class tests the properties of NPCs and their states
 */
public class NPCTest {
    /*
     * Testing the NPCFactory
     */
    @Test
    void NPCFactoryTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        assertEquals(adventureGame.npcHashMap.get(1).getName(), "NPC-no-1");
        assertEquals(adventureGame.npcHashMap.get(2).getName(), "NPC-no-2");
        assertEquals(adventureGame.npcHashMap.get(3).getName(), "NPC-no-3");
    }
    /*
     * Testing the NPCState changes
     */
    @Test
    void NPCStateTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        adventureGame.interpretAction("NPC TAKE GEMS");
        assertTrue(adventureGame.npcHashMap.get(1).getState() instanceof NPCStateNoObject);
        assertEquals(adventureGame.npcHashMap.get(1).getState().getNPCState(), "NPCStateNoObject");
    }
    /*
     * Testing the NPCState with objects
     */
    @Test
    void NPCObjectTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        assertTrue(adventureGame.npcHashMap.get(1).getState() instanceof NPCStateHasObject);
        assertEquals(adventureGame.npcHashMap.get(1).getState().getNPCState(), "NPCStateHasObject");
    }
    /*
     * Testing the giveReward method in NPC
     */
    @Test
    void NPCGiveRewardTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        adventureGame.interpretAction("NPC Sidequest");
        assertEquals(adventureGame.interpretAction("Paris"),
                "congratulations! here's a hint: If you have BIRD, it will be useful throughout your journey.");
    }
    /*
     * Test takeObject method in NPC
     */
    @Test
    void NPCTakeObjectTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        adventureGame.interpretAction("west");
        adventureGame.interpretAction("NPC take torch");
        assertEquals(adventureGame.player.inventory.get(0).getName(), "TORCH");
    }
    /*
     * Take giveObject method in NPC
     */
    @Test
    void NPCGiveObjectTest(){
        NPCFactory npcFactory = new NPCFactory();
        AdventureGame adventureGame = new AdventureGame("TinyGame");
        adventureGame.interpretAction("take bird");
        adventureGame.interpretAction("west");
        adventureGame.interpretAction("NPC take torch");
        adventureGame.interpretAction("npc give bird");
        assertTrue(adventureGame.npcHashMap.get(2).getState() instanceof NPCStateHasObject);
    }
}
