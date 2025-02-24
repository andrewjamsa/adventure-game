package AdventureModel;

import AdventureModel.Effects.EffectFactory;
import AdventureModel.Effects.EffectStrategy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Class AdventureLoader. Loads an adventure from files.
 */
public class AdventureLoader {

    private AdventureGame game; //the game to return
    private String adventureName; //the name of the adventure
    private Map<String, AdventureObject> objects = new HashMap<>();

    /**
     * Adventure Loader Constructor
     * __________________________
     * Initializes attributes
     * @param game the game that is loaded
     * @param directoryName the directory in which game files live
     */
    public AdventureLoader(AdventureGame game, String directoryName) {
        this.game = game;
        this.adventureName = directoryName;
    }

    public void parseNPC() throws IOException {
        String npcFileName = this.adventureName + "/npc.txt";
        BufferedReader buff = new BufferedReader(new FileReader(npcFileName));
        NPCFactory npcFactory = new NPCFactory();
        this.game.npcHashMap = npcFactory.NPCGenerator(buff, this.game.getRooms());
    }
    public void parseSQ() throws IOException {
        String sqFileName = this.adventureName + "/side_quest.txt";
        BufferedReader buff = new BufferedReader(new FileReader(sqFileName));
        SQFactory sqFactory = new SQFactory();
        sqFactory.SQGenerator(this.game.npcHashMap, buff);
    }
    public void parseHint() throws IOException {
        String hintFileName = this.adventureName + "/hints.txt";
        BufferedReader buff = new BufferedReader(new FileReader(hintFileName));
        while (buff.ready()){
            String[] temp = buff.readLine().split(": ");
            if (this.game.hints.containsKey(Integer.valueOf(temp[0]))){
                this.game.hints.get(Integer.valueOf(temp[0])).add(temp[1]);
            } else {
                this.game.hints.put(Integer.valueOf(temp[0]), new ArrayList<>(Collections.singletonList(temp[1])));
            }
        }
    }
     /**
     * Load game from directory
     */
    public void loadGame() throws IOException {
        objects.clear();
        fillObjectsMap();
        EffectFactory.setObjects(objects);
        parseRooms();
        parseObjects();
        parseSynonyms();
        parseNPC();
        parseSQ();
        parseHint();
        this.game.setHelpText(parseOtherFile("help"));
    }

     /**
     * Parse Rooms File
     */
    private void parseRooms() throws IOException {

        int roomNumber;

        String roomFileName = this.adventureName + "/rooms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(roomFileName));

        while (buff.ready()) {

            String currRoom = buff.readLine(); // first line is the number of a room

            roomNumber = Integer.parseInt(currRoom); //current room number

            // now need to get room name
            String roomName = buff.readLine();

            EffectStrategy effectStrategy = EffectFactory.generateEffect(buff.readLine());

            // now we need to get the description
            String roomDescription = "";
            String line = buff.readLine();
            while (!line.equals("-----")) {
                roomDescription += line + "\n";
                line = buff.readLine();
            }
            roomDescription += "\n";

            // now we make the room object
            Room room = new Room(roomName, roomNumber, roomDescription, adventureName);
            room.setEffect(effectStrategy);

            // now we make the motion table
            line = buff.readLine(); // reads the line after "-----"
            while (line != null && !line.equals("")) {
                String[] part = line.split(" \s+"); // have to use regex \\s+ as we don't know how many spaces are between the direction and the room number
                String direction = part[0];
                String dest = part[1];
                if (dest.contains("/")) {
                    String[] blockedPath = dest.split("/");
                    String dest_part = blockedPath[0];
                    String object = blockedPath[1];
                    Passage entry = new Passage(direction, dest_part, object);
                    room.getMotionTable().addDirection(entry);
                } else {
                    Passage entry = new Passage(direction, dest);
                    room.getMotionTable().addDirection(entry);
                }
                line = buff.readLine();
            }
            this.game.getRooms().put(room.getRoomNumber(), room);
        }

    }

    /**
     * Fill objects map by parsing objects.txt. Effects and location not filled
     */
    public void fillObjectsMap() throws IOException {
        String objectFileName = this.adventureName + "/objects.txt";
        System.out.println(new File(objectFileName).getAbsolutePath());

        BufferedReader buff = new BufferedReader(new FileReader(objectFileName));

        while (buff.ready()) {
            String objectName = buff.readLine();
            buff.readLine();
            String objectDescription = buff.readLine();
            String objectLocation = buff.readLine();
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            AdventureObject object = new AdventureObject(objectName, objectDescription, null);
            objects.put(objectName, object);
        }
        buff.close();
    }

     /**
     * Parse Objects File uses objects map. Fills in missing location and effects
     */
    public void parseObjects() throws IOException {

        String objectFileName = this.adventureName + "/objects.txt";
        BufferedReader buff = new BufferedReader(new FileReader(objectFileName));

        while (buff.ready()) {
            String objectName = buff.readLine();
            EffectStrategy effectStrategy = EffectFactory.generateEffect(buff.readLine());
            String objectDescription = buff.readLine();
            String objectLocation = buff.readLine();
            String separator = buff.readLine();
            if (separator != null && !separator.isEmpty())
                System.out.println("Formatting Error!");
            int i = Integer.parseInt(objectLocation);
            AdventureObject object = objects.get(objectName);
            object.setEffect(effectStrategy);
            if(this.game.getRooms().containsKey(i)){
                Room location = this.game.getRooms().get(i);
                object.setLocation(location);
                location.addGameObject(object);
            }
        }
        buff.close();
    }

     /**
     * Parse Synonyms File
     */
    public void parseSynonyms() throws IOException {
        String synonymsFileName = this.adventureName + "/synonyms.txt";
        BufferedReader buff = new BufferedReader(new FileReader(synonymsFileName));
        String line = buff.readLine();
        while(line != null){
            String[] commandAndSynonym = line.split("=");
            String command1 = commandAndSynonym[0];
            String command2 = commandAndSynonym[1];
            this.game.getSynonyms().put(command1,command2);
            line = buff.readLine();
        }

    }

    /**
     * Parse Files other than Rooms, Objects and Synonyms
     *
     * @param fileName the file to parse
     */
    public String parseOtherFile(String fileName) throws IOException {
        String text = "";
        fileName = this.adventureName + "/" + fileName + ".txt";
        BufferedReader buff = new BufferedReader(new FileReader(fileName));
        String line = buff.readLine();
        while (line != null) { // while not EOF
            text += line+"\n";
            line = buff.readLine();
        }
        return text;
    }

}
