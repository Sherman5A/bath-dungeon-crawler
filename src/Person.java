import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class that represents the things in the game that can move and collect items.
 */
public class Person {
    private final GameMap gameMap;
    /**
     * Amount of gold the person has.
     */
    private int numGold;
    /**
     * Hashmaps used as showing the "x" and "y" as keys improves readability compared to indexes [0] and [1]
     * in an array.
     */
    private HashMap<String, Integer> coordinates = new HashMap<String, Integer>();

    /**
     * Constructor for Person class.
     *
     * @param gameMap Map is passed to allow access to its functions.
     */
    public Person(GameMap gameMap) {
        this.gameMap = gameMap;
        this.numGold = 2;
        spawnPerson();
    }

    /**
     * Spawns the person at a random location. Perform checks to prevent spawning person on wall or gold.
     */
    private void spawnPerson() {
        ArrayList<ArrayList<String>> map = this.gameMap.getMap();
        int yLength = map.size();
        int xLength = map.get(0).size();
        boolean spawnTileClear = false;
        while (!spawnTileClear) {
            Random rand = new Random();
            coordinates.put("x", rand.nextInt(0, xLength));
            coordinates.put("y", rand.nextInt(0, yLength));
            spawnTileClear = !(gameMap.checkTile(coordinates));
        }
    }


    /**
     * Moves the person in one of 4 cardinal directions. Prevents person from walking through walls and off the array
     *
     * @param direction Direction that the person moves in. "N", "S", "E", and "W" are the only accepted values
     *                  for the string
     * @throws IllegalArgumentException Thrown when a string has anything other than "N", "S", "E", or "W".
     * @throws Error                    Thrown when attempting to enter a wall.
     */
    public void move(String direction) throws IllegalArgumentException, Error {
        HashMap<String, Integer> testCoordinates = new HashMap<String, Integer>(this.coordinates);
        switch (direction) {
            case "N" -> testCoordinates.put("y", testCoordinates.get("y") - 1);
            case "S" -> testCoordinates.put("y", testCoordinates.get("y") + 1);
            case "E" -> testCoordinates.put("x", testCoordinates.get("x") + 1);
            case "W" -> testCoordinates.put("x", testCoordinates.get("x") - 1);
            default -> throw new IllegalArgumentException("Invalid direction given");
        }
        boolean coordinatesValid = !this.gameMap.checkTile(testCoordinates, "#");
        if (coordinatesValid) {
            this.coordinates = testCoordinates;
            return;
        }
        testCoordinates.clear();
        throw new Error("Can't enter walls");
    }

    /**
     * Performs checks to see if person can quit game with a win message or not
     *
     * @return Bool for game quit outcome. Depends on whether person is on exit tile and has enough gold.
     */
    public boolean quitGame() {
        return gameMap.checkTile(this.coordinates, "E") && this.numGold >= gameMap.getNumGoldRequired();
    }

    /**
     * Picks up gold providing that valid tile is at the persons coordinates. If tile is valid, then it is "consumed"
     * and becomes a standard tile. Moreover, the gold is incremented by 1.
     *
     * @return Returns true if gold was successfully picked up
     */
    public boolean pickUpGold() {
        if (!gameMap.checkTile(this.coordinates, "G")) return false;
        this.numGold++;
        gameMap.consumeTile(this.coordinates);
        return true;
    }

    /**
     * Gets the coordinates of the person.
     *
     * @return Hashmap of the coordinates. Hashmap has key values "x" and "y".
     */
    public HashMap<String, Integer> getCoordinates() {
        return this.coordinates;
    }

    /**
     * Returns the amount of gold the person has collected.
     *
     * @return Integer contain the amount of gold the person has collected.
     */
    public int getNumGold() {
        return numGold;
    }
}
