import java.util.ArrayList;
import java.util.HashMap;

/**
 * Reads and contains in memory the map of the game.
 */
public class Map {

    /* Representation of the map */
//	private char[][] map;

    private final ArrayList<ArrayList<String>> map;


    /* Map name */
    private final String mapName;

    /* Gold required for the human player to win */
    private final int goldRequired;
    private ArrayList<ArrayList<Integer>> wallLocations;
    private ArrayList<ArrayList<Integer>> goldLocations;
    private ArrayList<ArrayList<Integer>> exitLocations;

    /**
     * Default constructor, creates the default map "Very small Labyrinth of doom".
     */
//	public Map() {
//		mapName = "Very small Labyrinth of Doom";
//		goldRequired = 2;
//		map = new char[][]{
//		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'},
//		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
//		{'#','.','.','.','.','.','.','G','.','.','.','.','.','.','.','.','.','E','.','#'},
//		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
//		{'#','.','.','E','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
//		{'#','.','.','.','.','.','.','.','.','.','.','.','G','.','.','.','.','.','.','#'},
//		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
//		{'#','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','.','#'},
//		{'#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#','#'}
//		};
//	}

    /**
     * Constructor that accepts a map to read in from.
     *
     * @param : The filename of the map file.
     */
//	public Map(String fileName) {
//		readMap(fileName);
//	}
    public Map(String mapName, int goldRequired, ArrayList<ArrayList<String>> mapList) throws IllegalArgumentException {
        this.mapName = mapName;
        this.goldRequired = goldRequired;
        this.map = mapList;
    }

    public boolean checkTile(HashMap<String, Integer> coordinates) {
        String spawnTile = this.map.get(coordinates.get("y")).get(coordinates.get("x"));
        return !spawnTile.equals("G") && !spawnTile.equals("#");
    }

    public boolean checkTile(HashMap<String, Integer> coordinates, String whichTile) {
        return this.map.get(coordinates.get("y")).get(coordinates.get("x")).equals(whichTile);
    }





    /* Players should not directly interact with map, and the strings in the map.
     * Therefore, the coordinates of map items will be placed in arrays at the start
     * of the game and passed to required classes. This achieves separation of
     * implementation between the Map and Person classes. */
//    private void parseMapData() {
//        // Get walls
//        this.wallLocations = new ArrayList<ArrayList<Integer>>();
//        this.goldLocations = new ArrayList<ArrayList<Integer>>();
//        this.exitLocations = new ArrayList<ArrayList<Integer>>();
//        int j = 0;
//        for (ArrayList<String> row : this.map) {
//
//            System.out.println(row);
//            this.goldLocations.add(new ArrayList<Integer>());
//            this.wallLocations.add(new ArrayList<Integer>());
//            String rowString = String.join("", row);
//            int wallIndex = rowString.indexOf("#");
//            int goldIndex = rowString.indexOf("G");
//            int exitIndex = rowString.indexOf("E");
//            while (wallIndex >= 0 && goldIndex >= 0 && exitIndex >= 0) {
//
//                System.out.println(wallIndex);
//            }
//            System.out.println("One loop done");
//            System.out.println(i);
//            System.out.println(j);
//            while (i >= 0) {
//                i = rowString.indexOf("#", i);
//                this.wallLocations.get(j).add(i+1);
//                System.out.println(this.wallLocations);
//            }
//            j++;
//        }
//        System.out.println(this.wallLocations);
//            System.out.println(i.nextIndex() + " " + i.next());
//    }

    public ArrayList<ArrayList<String>> getMap() {
        return this.map;
    }

    public int getGoldRequired() {
        return goldRequired;
    }

    public String getMapName() {
        return mapName;
    }
}
