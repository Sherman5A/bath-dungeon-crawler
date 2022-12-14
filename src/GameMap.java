import java.util.ArrayList;
import java.util.HashMap;

/**
 * GameMap is a class that contains the game's map and several related functions:
 */
public class GameMap {

    /**
     * Map name retrieved from the map's file data.
     */
    private final String mapName;
    /**
     * Amount of gold required for the human player to win
     */
    private final int numGoldRequired;
    /**
     * Map stored as nested ArrayList to allow for easy access compared to string.
     */
    private final ArrayList<ArrayList<String>> map;

    /**
     * Constructor for GameMap class.
     *
     * @param mapName         Name of map parsed from map file.
     * @param numGoldRequired Amount of gold required to win, parsed from map file.
     * @param mapList         List that holds the map's tiles. Read from map file. Stored as nested array of strings.
     */
    public GameMap(String mapName, int numGoldRequired, ArrayList<ArrayList<String>> mapList) {
        this.mapName = mapName;
        this.numGoldRequired = numGoldRequired;
        this.map = mapList;
    }

    /**
     * Checks that the coordinates are not outside the map. This is useful for when the map may not have borders
     *
     * @param coordinates Coordinates to check.
     * @return Returns true if the coordinates are outside the map array/
     */
    private boolean coordinatesOutOfBounds(HashMap<String, Integer> coordinates) {
        int yLength = map.size() - 1;
        int xLength = map.get(0).size() - 1;
        return ((coordinates.get("y") >= yLength || coordinates.get("y") <= 0) ||
                (coordinates.get("x") >= xLength || coordinates.get("x") <= 0));
    }


    /**
     * Checks the given tile for gold and walls as the player can not spawn on them tiles
     *
     * @param coordinates Coordinates to check
     * @return Returns whether it is valid to spawn on the given coordinates. True is allowed. False is not.
     */
    public boolean checkTile(HashMap<String, Integer> coordinates) {
        if (coordinatesOutOfBounds(coordinates)) return true;
        String spawnTile = this.map.get(coordinates.get("y")).get(coordinates.get("x"));
        return (spawnTile.equals("G") || spawnTile.equals("#"));
    }

    /**
     * Checks the given tile for the given character.
     *
     * @param coordinates Coordinates to check.
     * @param whichTile   String to check for on the tile.
     * @return Returns true if the given string matches the tile at the coordinates
     */
    public boolean checkTile(HashMap<String, Integer> coordinates, String whichTile) {
        if (coordinatesOutOfBounds(coordinates)) return true;
        return whichTile.equals(this.map.get(coordinates.get("y")).get(coordinates.get("x")));
    }

    /**
     * Used to replace a tile with empty. This is done when a tile such as gold is "consumed" and has to become
     * a normal tile
     *
     * @param tileCoordinates Coordinates of the tile to consume
     */
    public void consumeTile(HashMap<String, Integer> tileCoordinates) {
        this.map.get(tileCoordinates.get("y")).set(tileCoordinates.get("x"), ".");
    }

    /**
     * Gets the map and returns it.
     *
     * @return Nested array of strings containing the map
     */
    public ArrayList<ArrayList<String>> getMap() {
        return this.map;
    }

    /**
     * Gets the amount of gold required to win and returns it.
     *
     * @return integer containing amount of gold required to win.
     */
    public int getNumGoldRequired() {
        return numGoldRequired;
    }
}
