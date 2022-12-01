import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Paths;

public class MapFile {
    private Scanner fileScan;

    public MapFile(String fileToRead) throws IOException {

        this.fileScan = new Scanner(Paths.get(fileToRead));
    }

    public void readFile() {
        ArrayList<ArrayList<String>> gameMap = new ArrayList<ArrayList<String>>();
        int i = 0;
        while (fileScan.hasNextLine()) {
            String row = fileScan.nextLine();
            if (row.isBlank()) {
                continue;
            }
            gameMap.add(new ArrayList<>(Arrays.asList(row.split(""))));

        }
        System.out.println(gameMap);

    }


}

