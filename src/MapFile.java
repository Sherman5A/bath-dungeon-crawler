import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class MapFile {
    private final Scanner fileScan;

    public MapFile(String fileToRead) throws IOException {

        this.fileScan = new Scanner(Paths.get(fileToRead));
    }

    // Read the file that was created
    public GameMap getMapFromFile() throws IllegalArgumentException {
        int goldRequired = -1;
        String tableName = null;
        ArrayList<ArrayList<String>> gameMap = new ArrayList<ArrayList<String>>();
        int count = 0;
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
