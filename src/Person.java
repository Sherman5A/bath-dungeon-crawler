import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Person {
    private final GameMap gameMap;
    // Person coordinates
    private HashMap<String, Integer> coordinates = new HashMap<String, Integer>();
    // Person gold

    // Init

    public Person(GameMap gameMap) {
        this.gameMap = gameMap;
        spawnPerson();
    }

    private void spawnPerson() {
        ArrayList<ArrayList<String>> map = this.gameMap.getMap();
        int yLength = map.size();
        int xLength = map.get(0).size();
        boolean spawnSuccess = false;
        while (!spawnSuccess) {
            Random rand = new Random();
            coordinates.put("x", rand.nextInt(0, xLength));
            coordinates.put("y", rand.nextInt(0, yLength));
            spawnSuccess = gameMap.checkTile(coordinates);
        }
    }

    public Boolean move(String direction) {
        HashMap<String, Integer> testCoordinates = new HashMap<String, Integer>(this.coordinates);
        switch (direction) {
            case "N" -> testCoordinates.put("y", testCoordinates.get("y") + 1);
            case "S" -> testCoordinates.put("y", testCoordinates.get("y") - 1);
            case "E" -> testCoordinates.put("x", testCoordinates.get("x") + 1);
            case "W" -> testCoordinates.put("x", testCoordinates.get("x") - 1);
        }
        boolean coordinatesValid = this.gameMap.checkTile(testCoordinates, "#");
        if (coordinatesValid) {
            this.coordinates = testCoordinates;
            testCoordinates.clear();
            return true;
        }
        testCoordinates.clear();
        return false;
    }

    public HashMap<String, Integer> getCoordinates() {
        return this.coordinates;
    }

    // Move Person

    // Pickup

    // getGoldAmount

    // getCoordinates

}
