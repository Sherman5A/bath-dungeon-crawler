import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserInterface userInput = new UserInterface(scanner);
        GameMap gameMap = userInput.chooseMap();
        Person player = new Person(gameMap);
        Person bot = new Person(gameMap);
        BotHandler botControl = new BotHandler(gameMap, bot, player);
        userInput.userLoop(player, bot, gameMap, botControl);
    }
}