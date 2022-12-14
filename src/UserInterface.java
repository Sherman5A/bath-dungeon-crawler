import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


/**
 * Class that handles user input and output.
 */
public class UserInterface {
    private final Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints the entire nested map formatted as multiline string
     *
     * @param mapArray Nested ArrayList to print
     */
    public static void printMap(ArrayList<ArrayList<String>> mapArray) {
        for (ArrayList<String> row : mapArray) {
            System.out.println(String.join("", row));
        }
        System.out.println();
    }

    /**
     * Prints a 5x5 grid of the map around the given person
     *
     * @param mapArray     Nested array to print
     * @param playerCoords The coordinates to print the 5x5 array around.
     */
    // TODO: put in bot coordinates
    public static void printMap(ArrayList<ArrayList<String>> mapArray, HashMap<String, Integer> playerCoords) {
        /* Gets the required indexes to iterate through to print a 5x5 grid around the coordinates
         * Two values, the lower bound (for x: left side, for y: top of grid),
         * and upper bound (for x: right side, for y: bottom of grid)
         */
        int[] x_bound = new int[]{playerCoords.get("x") - 2, playerCoords.get("x") + 3};
        int[] y_bound = new int[]{playerCoords.get("y") - 2, playerCoords.get("y") + 3};

        // Iterate from top to bottom of 5x5 grid.
        for (int i = y_bound[0]; i < y_bound[1]; i++) {
            // On each row of the grid, print 5 tiles from the GameMap array from the lower to upper x bound
            for (int j = x_bound[0]; j < x_bound[1]; j++) {
                // If current tile coordinates match the player's coordinates then place the player tile instead of
                // the tile on the map.
                if (i == playerCoords.get("y") && j == playerCoords.get("x")) {
                    System.out.print("P");
                    continue;
                }
                // Otherwise, print the typical tile in the array.
                try {
                    // System.out.printf("%d %d %s - ", j, i, mapArray.get(i).get(j)); For debugging
                    System.out.print(mapArray.get(i).get(j));
                    // If we go past the map's area, then print #s
                } catch (IndexOutOfBoundsException e) {
                    System.out.print("#");
                }
            }
            // Print a linebreak to separate the rows.
            System.out.println();
        }
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
                    System.out.println("Small map selected:");
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

    /**
     * The loop for handling user input once the game has begun.
     *
     * @param player  Person instance to perform movement, etc. on
     * @param gameMap Game map to use as the environment
     */
    public void userLoop(Person player, GameMap gameMap) {
        scannerLoop:
        while (true) {
            System.out.println("Enter a command:");
            String userCommand = scanner.next();
            switch (userCommand) {
                case "HELLO" -> {
                    System.out.printf("Gold to win: %d\n", gameMap.getNumGoldRequired());
                }
                case "GOLD" -> {
                    System.out.printf("Gold owned: %d\n", player.getNumGold());
                }
                case "PICKUP" -> {
                    System.out.println("Pickup");
                    boolean outcome = player.pickUpGold();
                    if (outcome) {
                        System.out.printf("Success. Gold owned: %d\n", player.getNumGold());
                    } else System.out.println("Fail");
                }
                case "MOVE" -> {
                    String direction = scanner.next().substring(0, 1);
                    try {
                        player.move(direction);
                        System.out.println("Success");
                    } catch (IllegalArgumentException | Error e) {
                        // System.err.println(e); Debugging purposes
                        System.out.println("Fail");
                    }
                }
                case "LOOK" -> {
                    printMap(gameMap.getMap(), player.getCoordinates());
                }
                case "QUIT" -> {
                    // If the player is on an exit tile, and enough gold, then print the winning message. Print the
                    // fail message otherwise
                    System.out.println(player.quitGame() ? "WIN\nGood job!" : "LOSE");
                    break scannerLoop;
                }
                // If command is unrecognised:
                default -> System.out.println("Incorrect command");
            }
        }
    }
}
