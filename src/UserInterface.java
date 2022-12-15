import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * Class that handles user input and output.
 */
public class UserInterface {
    private final Scanner scanner;

    /**
     * Constructor for UserInterface
     * @param scanner Scanner for user / file input
     */
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
     * Called when the bot exits with enough gold, causing the player to
     * lose the game.
     */
    public static void botExit() {
        System.out.println("Lose");
        System.out.println("Bot exited successfully");
        System.exit(0);
    }

    /**
     * Returns a 5x5 array around the given person with the label "P". The other person is rendered as a bot with the tile
     * label "B".
     *
     * @param mapArray               The map to use to print the tile information
     * @param centerCoordinates      The coordinates to center the map around and to use as the player tile.
     * @param otherPersonCoordinates The coordinates to render as a bot, if present.
     * @return Nested array of the 5x5 grid.
     */
    public static ArrayList<ArrayList<String>> getLocalArray(ArrayList<ArrayList<String>> mapArray,
                                                             HashMap<String, Integer> centerCoordinates,
                                                             HashMap<String, Integer> otherPersonCoordinates) {

        /* Gets the required indexes to iterate through to print a 5x5 grid around the coordinates
         * Two values, the lower bound (for x: left side, for y: top of grid),
         * and upper bound (for x: right side, for y: bottom of grid)
         */
        int[] x_bound = new int[]{centerCoordinates.get("x") - 2, centerCoordinates.get("x") + 3};
        int[] y_bound = new int[]{centerCoordinates.get("y") - 2, centerCoordinates.get("y") + 3};
        ArrayList<ArrayList<String>> localArray = new ArrayList<>();
        // Iterate from top to bottom of 5x5 grid.
        int count = 0;
        for (int i = y_bound[0]; i < y_bound[1]; i++) {
            localArray.add(new ArrayList<>());
            // On each row of the grid, print 5 tiles from the GameMap array from the lower to upper x bound
            for (int j = x_bound[0]; j < x_bound[1]; j++) {
                // If current tile coordinates match the player's coordinates then place the player tile instead of
                // the tile on the map.

                if (i == otherPersonCoordinates.get("y") && j == otherPersonCoordinates.get("x")) {
                    localArray.get(count).add("B");
                    continue;
                } else if (i == centerCoordinates.get("y") && j == centerCoordinates.get("x")) {
                    localArray.get(count).add("P");
                    continue;
                }

                // Otherwise, print the typical tile in the array.
                try {
                    // System.out.printf("%d %d %s - ", j, i, mapArray.get(i).get(j)); For debugging
                    localArray.get(count).add(mapArray.get(i).get(j));
                    // If we go past the map's area, then print #s
                } catch (IndexOutOfBoundsException e) {
                    localArray.get(count).add("#");
                }
            }
            count++;
            // Print a linebreak to separate the rows.
        }
        return localArray;
    }

    /**
     * Provides interface for user to choose which map they want
     *
     * @return Object of MapFile that allows reading, editing of user's
     * selected map file.
     */
    public GameMap chooseMap() {
        MapFile readMapFile;

        while (true) {
            System.out.println("Enter relative path:");
            String fileToRead = scanner.nextLine();
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
            // Recursively ask for a new file if the format is incorrect
            // or if data is missing.
            return this.chooseMap();
        }
    }

    /**
     * The loop for handling user input once the game has begun.
     *
     * @param player  Person instance to perform movement, etc. on
     * @param bot Instance of bot to check its coordinates
     * @param gameMap Game map to use as the environment
     * @param botControl BotControl instance to execute bots turn.
     */
    public void userLoop(Person player, Person bot, GameMap gameMap, BotHandler botControl) {

        scannerLoop:
        while (true) {

            // Player loses if the bots coordinates match the players coordinates.
            if (player.getCoordinates().equals(bot.getCoordinates())) {
                printMap(getLocalArray(gameMap.getMap(), player.getCoordinates(), bot.getCoordinates()));
                System.out.println("Fail");
                break;
            }

            // Request the player to enter a command.
            System.out.println("Enter a command:");
            String userCommand = scanner.next();
            switch (userCommand) {
                // Prints gold required to win the game.
                case "HELLO" -> {
                    System.out.printf("Gold to win: %d\n", gameMap.getNumGoldRequired());
                }
                // Prints the player's gold.
                case "GOLD" -> {
                    System.out.printf("Gold owned: %d\n", player.getNumGold());
                }
                // Pickup gold if on correct tile.
                case "PICKUP" -> {
                    System.out.println("Pickup");
                    boolean outcome = player.pickUpGold();
                    if (outcome) {
                        System.out.printf("Success. Gold owned: %d\n", player.getNumGold());
                    } else System.out.println("Fail");
                }
                // Move in a given direction.
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
                // Generate and print a 5x5 map around the player.
                case "LOOK" -> {
                    printMap(getLocalArray(gameMap.getMap(), player.getCoordinates(), bot.getCoordinates()));
                }
                // Quit the game.
                case "QUIT" -> {
                    // If the player is on an exit tile, and enough gold, then print the winning message. Print the
                    // fail message otherwise
                    System.out.println(player.quitGame() ? "WIN\nGood job!" : "LOSE");
                    break scannerLoop;
                }
                // If command is unrecognised:
                default -> {
                    System.out.println("Incorrect command");
                }
            }
            // Execute the bots turn.
            botControl.botTurn();
        }
    }
}
