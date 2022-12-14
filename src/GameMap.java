import java.util.ArrayList;
import java.util.HashMap;

public class GameMap {

    private final ArrayList<ArrayList<String>> map;
    /** GameMap name */
    private final String mapName;
    /** Gold required for the human player to win */
    private final int numGoldRequired;

    public GameMap(String mapName, int numGoldRequired, ArrayList<ArrayList<String>> mapList)
            throws IllegalArgumentException {
        this.mapName = mapName;
        this.numGoldRequired = numGoldRequired;
        this.map = mapList;
    }

    public boolean checkTile(HashMap<String, Integer> coordinates) {
        if (!checkTileBounds(coordinates)) return false;
        String spawnTile = this.map.get(coordinates.get("y")).get(coordinates.get("x"));
        return !spawnTile.equals("G") && !spawnTile.equals("#");
    }

    public boolean checkTile(HashMap<String, Integer> coordinates, String whichTile) {
//        if (checkTileBounds(coordinates)) return false;
        return !(this.map.get(coordinates.get("y")).get(coordinates.get("x")).equals(whichTile));
    }

    // TODO: implement this so that you cannot get bounds outside of the array
    // Check in event that the map does not have any borders
    private boolean checkTileBounds(HashMap<String, Integer> coordinates) {
        int yLength = map.size() - 1 ;
        int xLength = map.get(0).size() - 1;
        return ((coordinates.get("y") < yLength && coordinates.get("y") > 0) ||
                (coordinates.get("x") < xLength && coordinates.get("x") > 0));
    }

    public void consumeTile(HashMap<String, Integer> coordinates) {
        this.map.get(coordinates.get("y")).set(coordinates.get("x"),".");
    }

    public ArrayList<ArrayList<String>> getMap() {
        return this.map;
    }

    public int getNumGoldRequired() {
        return numGoldRequired;
    }

    public String getMapName() {
        return mapName;
    }
}
