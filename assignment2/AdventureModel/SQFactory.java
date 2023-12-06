package AdventureModel;

import AdventureModel.SideQuests.SQ_Object;
import AdventureModel.SideQuests.SQ_Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Class SQFactory. Factory that generates SideQuest.
 */
public class SQFactory {
    /**
     * SQGenerator
     * ______________________________
     * Handles the side_quest.txt file to generate all the SideQuest.
     *
     * @param npcHashMap Hashmap of all the room numbers that corresponds to the location of the NPC
     * @param buff BufferedReader that corresponds to the file of the side quest
     * @throws IOException
     */
    public void SQGenerator(HashMap<Integer, NPC> npcHashMap, BufferedReader buff) throws IOException {
        while (buff.ready()){
            String type = buff.readLine().substring(6);
            String npcName = buff.readLine().substring(6);
            NPC npcInCharge = null;
            for (NPC npc: npcHashMap.values()){
                if (Objects.equals(npc.name, npcName)){
                    npcInCharge = npc;
                }
            }
            if (type.equals("QNA")){
                String question = buff.readLine().substring(9);
                String hint = buff.readLine().substring(6);
                String correct = buff.readLine().substring(9);
                String incorrect = buff.readLine().substring(11);
                SQ_Question sqQuestion = new SQ_Question(npcInCharge, question, hint, correct, incorrect);
                assert npcInCharge != null;
                npcInCharge.sideQuest = sqQuestion;
                buff.readLine();
            } else if (type.equals("OBJECT")) {
                String required = buff.readLine().substring(10);
                String question = buff.readLine().substring(10);
                String accomplished = buff.readLine().substring(14);
                SQ_Object sqObject = new SQ_Object(npcInCharge, required, question, accomplished);
                assert npcInCharge != null;
                npcInCharge.sideQuest = sqObject;
                buff.readLine();
            }
        }
    }
}
