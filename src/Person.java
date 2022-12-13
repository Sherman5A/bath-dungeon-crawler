import java.util.HashMap;
import java.util.Random;

public class Person {
    private Map gameMap;
    // Person coordinates
    private HashMap<String, Integer> coordinates = new HashMap<String, Integer>();
    // Person gold

    // Init

    public Person(Map gameMap) {
        this.gameMap = gameMap;
        spawnPerson();
    }

    private void spawnPerson() {
        boolean spawnSuccess = false;
        while (!spawnSuccess) {
            Random rand = new Random();
            coordinates.put("x", rand.nextInt());
            coordinates.put("y", rand.nextInt());
            spawnSuccess = gameMap.checkTile(coordinates);
        }
    }

    // Move Person

    // Pickup

    // getGoldAmount

    // getCoordinates

}
