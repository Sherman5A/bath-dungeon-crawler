import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        UserInput userInterface = new UserInput(scanner);
        MapFile mapRead = userInterface.userStart();
        mapRead.readFile();
    }
}