import java.util.ArrayList;

/**
 * Reads and contains in memory the map of the game.
 */
public class Map {

    /* Representation of the map */
//	private char[][] map;

    private ArrayList<ArrayList<String>> map;


    /* Map name */
    private String mapName;

    /* Gold required for the human player to win */
    private int goldRequired;

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

    public ArrayList<ArrayList<String>> getMap() {
        return this.map;
    }
}
