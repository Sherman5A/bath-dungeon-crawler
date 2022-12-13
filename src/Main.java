import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserInterface userInput = new UserInterface(scanner);
        Map gameMap = userInput.chooseMap();

        UserInterface.printMap(gameMap.getMap());
        UserInterface.displayMapInfo(gameMap);
        Person player = new Person(gameMap);
        userInput.userLoop();

    }
}