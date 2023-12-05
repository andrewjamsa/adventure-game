package AdventureModel;

import AdventureModel.NPCStates.NPCState;
import AdventureModel.NPCStates.NPCStateHasObject;
import AdventureModel.NPCStates.NPCStateNoObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class NPCFactory. Factory that generates NPCs.
 */
public class NPCFactory {
    /**
     * NPCGenerator
     * _____________________________
     * Handles the npc.txt file to generate all the NPCs.
     *
     * @param NPCbuff BufferedReader that corresponds to the file of the npc
     * @param rooms Hashmap of all the room numbers with the corresponding rooms in the adventure game
     * @return Hashmap of all the room numbers that corresponds to the location of the NPC
     * @throws IOException
     */
    public HashMap<Integer, NPC> NPCGenerator(BufferedReader NPCbuff, HashMap<Integer, Room> rooms) throws IOException {
        HashMap<Integer, NPC> npcHashMap = new HashMap<>();
        while (NPCbuff.ready()){
            String npcName = NPCbuff.readLine();

            String checkIfObject = NPCbuff.readLine();
            ArrayList<String[]> objectsListInStringList = new ArrayList<>();
            ArrayList<AdventureObject> objectsList = new ArrayList<>();


            while (checkIfObject.contains("object")){
                String[] convertStringList = checkIfObject.split("-");
                objectsListInStringList.add(convertStringList);
                checkIfObject = NPCbuff.readLine();
            }


            String intro;
            intro = checkIfObject.substring(7);
            Room room = rooms.get(Integer.parseInt(NPCbuff.readLine()));
            NPCbuff.readLine(); // remove a separator line
            NPC npc = new NPC(room, npcName, intro);
            room.NPCAvailable = npc;


            if (objectsListInStringList.isEmpty()){
                npc.changeState(new NPCStateNoObject(npc));
            } else if (!objectsListInStringList.isEmpty()) {
                for (String[] element: objectsListInStringList){
                    objectsList.add(new AdventureObject(element[0].substring(8).toUpperCase(), element[1], room));
                }
                npc.changeState(new NPCStateHasObject(npc, objectsList));
            }

            npcHashMap.put(room.getRoomNumber(), npc);

        }
        return npcHashMap;
    }
}
