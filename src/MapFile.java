import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


/**
 * Class that handles reading the map file and creating the map class with the correctly formatted arguments.
 */
public class MapFile {
    private final Scanner fileScan;


    /**
     * Constructor for the class.
     * @param fileToRead The file to read that contains the map
     * @throws IOException Throws if the given file is not found.
     */
    public MapFile(String fileToRead) throws IOException {

        this.fileScan = new Scanner(Paths.get(fileToRead));
    }
    /**
     * Returns the map from the file. Handling parsing the map and converting it to a list. Grabs the data from the
     * file, e.g. the gold required and the name.
     * @return Returns the new constructed GameMap class created from the file that was read
     * @throws IllegalArgumentException Thrown if the name and gold data is not present in the given file.
     */
    public GameMap getMapFromFile() throws IllegalArgumentException {
        int goldRequired = -1;
        int count = 0;
        String tableName = null;
        ArrayList<ArrayList<String>> gameMap = new ArrayList<ArrayList<String>>();

        while (fileScan.hasNextLine()) {

            String row = fileScan.nextLine();
            if (row.isBlank()) {
                continue;
            }
            Scanner parseRow = new Scanner(row);

            switch (parseRow.next()) {
                case "name" -> {
                    tableName = parseRow.nextLine().strip();
                }
                case "win" -> {
                    goldRequired = parseRow.nextInt();
                }
                default -> {
                    gameMap.add(new ArrayList<>(Arrays.asList(row.split(""))));
                }
            }
            parseRow.close();
        }

        fileScan.close();

        if (goldRequired == -1) {
            throw new IllegalArgumentException("No win condition in file");
        }
        if (tableName == null) {
            throw new IllegalArgumentException("No table name in file");
        }

        return new GameMap(tableName, goldRequired, gameMap);
    }
}
