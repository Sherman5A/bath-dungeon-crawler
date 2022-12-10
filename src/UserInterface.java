import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserInterface {
    private Scanner scanner;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
    }

    /**
     * Prints the nested map formatted as multiline string
     * @param mapOutput Nested ArrayList to print
     */
    public static void printMap(ArrayList<ArrayList<String>> mapOutput) {
        for (ArrayList<String> row : mapOutput) {
            System.out.println(String.join("", row));
        }
    }

    /**
     * Provides interface for user to choose which map they want
     *
     * @return Object of MapFile that allows reading, editing of user's
     * selected map file.
     */
    public MapFile chooseMap() {
        String fileToRead;

        while (true) {
            System.out.println("""
                    Select a map:
                    1 - Small Map
                    2 - Medium Map
                    3 - Large Map""");
            String mapFile = scanner.nextLine();

            switch (mapFile) {
                case "1" -> {
                    System.out.println("Small map selected");
                    fileToRead = "example-maps/small_example_map.txt";
                }
                case "2" -> {
                    System.out.println("Medium");
                    fileToRead = "example-maps/medium_example_map.txt";
                }
                case "3" -> {
                    System.out.println("Large");
                    fileToRead = "example_maps/large_example_map.txt";
                }
                default -> {
                    System.out.println("Incorrect map");
                    continue;
                }
            }
            try {
                return new MapFile(fileToRead);
            } catch (IOException e) {
                System.out.println("File does not exist.");
            }
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
}
