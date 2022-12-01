import java.io.IOException;
import java.security.cert.TrustAnchor;
import java.util.Scanner;

public class UserInput {
    private Scanner scanner;

    public UserInput(Scanner scanner) {
        this.scanner = scanner;
    }

    public MapFile userStart() {
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
                    fileToRead = "small.txt";
                }
                case "2" -> {
                    System.out.println("Medium");
                    fileToRead = "medium.txt";
                }
                case "3" -> {
                    System.out.println("Large");
                    fileToRead = "large.txt";
                }
                default -> {
                    System.out.println("Incorrect map");
                    continue;
                }
            }
            System.out.println(fileToRead);
            try {
                return new MapFile(fileToRead);
            } catch (IOException e) {
                System.out.println("File does not exist.");

            }
        }
    }

    public void userLoop() {
        while (true) {
            String userCommand = scanner.nextLine();
            if (userCommand.equals("Q")) {
                break;
            }
            System.out.println(userCommand);
        }
    }

}
