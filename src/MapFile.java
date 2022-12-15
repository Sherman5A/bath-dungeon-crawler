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
     *
     * @param fileToRead The file to read that contains the map
     * @throws IOException Throws if the given file is not found.
     */
    public MapFile(String fileToRead) throws IOException {
        // Create a scanner that reads from the file given in the argument.
        this.fileScan = new Scanner(Paths.get(fileToRead));
    }

    /**
     * Returns the map from the file. Handling parsing the map and converting it to a list. Grabs the data from the
     * file, e.g. the gold required and the name.
     *
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
            // Create a scanner dedicated to scanning each row for map data.
            Scanner parseRow = new Scanner(row);

            // Handle the map data in the file.
            switch (parseRow.next()) {
                case "name" -> {
                    // Parse all of the information after "name".
                    tableName = parseRow.nextLine().strip();
                }
                case "win" -> {
                    // Parse the number after gold required.
                    goldRequired = parseRow.nextInt();
                }
                default -> {
                    gameMap.add(new ArrayList<>(Arrays.asList(row.split(""))));
                }
            }
            // Close the row to avoid memory leaks.
            parseRow.close();
        }
        // Close the scanner for reading file as no need for it.
        fileScan.close();

        // If the name / gold data was not in the file, force the user to choose another file.
        if (goldRequired == -1) {
            throw new IllegalArgumentException("No win condition in file");
        }
        if (tableName == null) {
            throw new IllegalArgumentException("No table name in file");
        }
        // Handle returning GameMap class for encapsulation purposes.
        return new GameMap(tableName, goldRequired, gameMap);
    }
}

