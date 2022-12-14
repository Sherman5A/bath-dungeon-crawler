import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints the nested map formatted as multiline string
     *
     * @param mapArray Nested ArrayList to print
     */
    public static void printMap(ArrayList<ArrayList<String>> mapArray) {
        for (ArrayList<String> row : mapArray) {
            System.out.println(String.join("", row));
        }
        System.out.println();
    }

    // TODO: put in bot coordinates
    public static void printMap(ArrayList<ArrayList<String>> mapArray, HashMap<String, Integer> playerCoords) {
       int[] x_bound = new int[]{playerCoords.get("x") - 2, playerCoords.get("x") + 3};
       int[] y_bound = new int[]{playerCoords.get("y") - 2, playerCoords.get("y") + 3};
        System.out.println(Arrays.toString(x_bound));
        System.out.println(Arrays.toString(y_bound));

        for (int i = y_bound[0]; i < y_bound[1]; i++) {
            for (int j = x_bound[0]; j < x_bound[1]; j++) {
                if (i == playerCoords.get("y") && j == playerCoords.get("x")) {
                    System.out.print("P");
                    continue;
                }
                try {
                    // System.out.printf("%d %d %s - ", j, i, mapArray.get(i).get(j)); For debugging
                    System.out.print(mapArray.get(i).get(j));
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }

    public static void displayMapInfo(GameMap map) {
        System.out.println("GameMap name: " + map.getMapName());
        System.out.println("Gold required to win: " + map.getGoldRequired() + "\n");
    }

    /**
     * Provides interface for user to choose which map they want
     *
     * @return Object of MapFile that allows reading, editing of user's
     * selected map file.
     */
    public GameMap chooseMap() {
        String fileToRead;
        MapFile readMapFile;

        while (true) {
            System.out.println("""
                    Select a map:
                    1 - Small GameMap
                    2 - Medium GameMap
                    3 - Large GameMap""");
            String mapFile = scanner.nextLine();

            switch (mapFile) {
                case "1" -> {
                    System.out.println("Small map selected:\n");
                    fileToRead = "example-maps/small_example_map.txt";
                }
                case "2" -> {
                    System.out.println("Medium");
                    fileToRead = "example-maps/medium_example_map.txt";
                }
                case "3" -> {
                    System.out.println("Large");
                    fileToRead = "example-maps/large_example_map.txt";
                }
                default -> {
                    System.out.println("Incorrect map");
                    continue;
                }
            }
            try {
                readMapFile = new MapFile(fileToRead);
                break;
            } catch (IOException e) {
                System.out.println("File does not exist.");
            }
        }
        try {
            return readMapFile.getMapFromFile();

        } catch (IllegalArgumentException e) {
            System.out.println("File error:" + e);
            return this.chooseMap();
        }
    }

    public void userLoop() {
        scannerLoop:
        while (true) {
            System.out.println("Enter a command:");
            String userCommand = scanner.nextLine();
            switch (userCommand) {
                case "HELLO" -> {
                    System.out.println("Hello");
                }
                case "GOLD" -> {
                    System.out.println("Gold");
                }
                case "PICKUP" -> {
                    System.out.println("Pickup");
                }
                case "MOVE" -> {
                    // Must parse this
                    System.out.println("Move");
                }
                case "LOOK" -> {
                    System.out.println("Look");
                }
                case "QUIT" -> {
                    System.out.println("Quit");
                    break scannerLoop;
                }
                default -> {
                    System.out.println("Incorrect command");
                }
            }
        }
    }

    // Make bot loop??
}
