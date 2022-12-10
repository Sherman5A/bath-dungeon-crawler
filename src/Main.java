import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserInterface userInterface = new UserInterface(scanner);
        MapFile mapRead = userInterface.chooseMap();
        try {
            Map gameMap = mapRead.readFile();
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
//        UserInterface.printMap(gameMap.getMap());
        userInterface.userLoop();
    }
}