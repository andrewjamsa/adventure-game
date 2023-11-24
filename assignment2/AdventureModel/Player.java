package AdventureModel;

import AdventureModel.PlayerHealth.PlayerHealth;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class keeps track of the progress
 * of the player in the game.
 */
public class Player implements Serializable {
    /**
     * The current room that the player is located in.
     */
    private Room currentRoom;

    /**
     * The list of items that the player is carrying at the moment.
     */
    public ArrayList<AdventureObject> inventory;

    /**
     * The health of the player.
     */
    public PlayerHealth health;

    /**
     * Adventure Game Player Constructor
     */
    public Player(Room currentRoom, Integer defaultHealth, Integer maxHealth) {
        this.inventory = new ArrayList<AdventureObject>();
        this.currentRoom = currentRoom;
        this.health = new PlayerHealth(defaultHealth, maxHealth);
    }

    /**
     * This method adds an object into players inventory if the object is present in
     * the room and returns true. If the object is not present in the room, the method
     * returns false.
     *
     * @param object name of the object to pick up
     * @return true if picked up, false otherwise
     */
    public boolean takeObject(String object) {
        if (this.currentRoom.checkIfObjectInRoom(object)) {
            AdventureObject object1 = this.currentRoom.getObject(object);
            this.currentRoom.removeGameObject(object1);
            this.addToInventory(object1);
            return true;
        } else {
            return false;
        }
    }

    /**
     * checkIfObjectInInventory
     * __________________________
     * This method checks to see if an object is in a player's inventory.
     *
     * @param s the name of the object
     * @return true if object is in inventory, false otherwise
     */
    public boolean checkIfObjectInInventory(String s) {
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getName().equals(s)) return true;
        }
        return false;
    }

    /**
     * This method drops an object in the players inventory and adds it to the room.
     * If the object is not in the inventory, the method does nothing.
     *
     * @param s name of the object to drop
     */
    public void dropObject(String s) {
        for (int i = 0; i < this.inventory.size(); i++) {
            if (this.inventory.get(i).getName().equals(s)) {
                this.currentRoom.addGameObject(this.inventory.get(i));
                this.inventory.remove(i);
            }
        }
    }

    /**
     * Setter method for the current room attribute.
     *
     * @param currentRoom The location of the player in the game.
     */
    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    /**
     * This method adds an object to the inventory of the player.
     *
     * @param object Prop or object to be added to the inventory.
     */
    public void addToInventory(AdventureObject object) {
        this.inventory.add(object);
    }


    /**
     * Getter method for the current room attribute.
     *
     * @return current room of the player.
     */
    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    /**
     * Getter method for string representation of inventory.
     *
     * @return ArrayList of names of items that the player has.
     */
    public ArrayList<String> getInventory() {
        ArrayList<String> objects = new ArrayList<>();
        for (int i = 0; i < this.inventory.size(); i++) {
            objects.add(this.inventory.get(i).getName());
        }
        return objects;
    }

    /**
     * Does the effect of all the objects in player's inventory.
     */
    public void doAllObjectEffect() {
        // Copies to modifying inventory in an effect doesnt cause error
        for (AdventureObject object : new ArrayList<AdventureObject>(inventory)) {
            object.doEffect(this);
        }
    }

    /**
     * This method returns whether the player is alive or not.
     *
     * @return true if the player is alive, false otherwise
     */
    private boolean isAlive() {
        return this.health.getObservableHealth() > 0;
    }

    /**
     * This method sets the health value of the player.
     *
     * @param health the health value of the player
     */
    public void setHealth(Integer health) {
        this.health.setObservableHealth(health);
    }

    /**
     * This method removes health from the player.
     *
     * @param health the amount of health to remove
     */
    public void removeHealth(Integer health) {
        this.setHealth(this.health.getObservableHealth() - health);
    }

    /**
     * This method adds health to the player.
     *
     * @param health the amount of health to add
     */
    public void addHealth(Integer health) {
        this.setHealth(this.health.getObservableHealth() + health);
    }

    /**
     * This method returns the health value of the player.
     *
     * @return the health value of the player
     */
    public Integer getHealthValue() {
        return this.health.getObservableHealth();
    }

}
