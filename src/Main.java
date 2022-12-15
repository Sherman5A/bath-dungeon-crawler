import java.util.Scanner;

/**
 * Starts up required classes and loops
 */
public class Main {
    public static void main(String[] args) {

        // Scanner to read user's input.
        Scanner scanner = new Scanner(System.in);
        UserInterface userInput = new UserInterface(scanner);
        GameMap gameMap = userInput.chooseMap();
        Person player = new Person(gameMap);
        Person bot = new Person(gameMap);
        // Controls the bot
        BotHandler botControl = new BotHandler(gameMap, bot, player);
        // Loop that lets the user control the player
        userInput.userLoop(player, bot, gameMap, botControl);
    }
}
