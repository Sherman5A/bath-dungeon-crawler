import java.util.*;

public class BotHandler {
    private final GameMap gameMap;
    private final Person bot;
    private final Person player;
    private final HashMap<String, Integer> playerCoordinates;
    private final HashMap<String, Integer> goldCoordinates;
    private final Set<String> invalidDirections;
    private ArrayList<ArrayList<String>> mapMemory;
    private HashMap<String, Integer> lastLookCoordinates;
    private int numMoves;

    public BotHandler(GameMap gameMap, Person bot, Person player) {
        this.gameMap = gameMap;
        this.bot = bot;
        this.player = player;
        this.invalidDirections = new HashSet<>();
        this.goldCoordinates = new HashMap<>();
        this.playerCoordinates = new HashMap<>();
    }

    public void botTurn() {
        if (mapMemory == null) {
            lookCommand();
            return;
        }
        if (numMoves > 2) {
            lookCommand();
            numMoves = 0;
            return;
        }

        if (!playerCoordinates.isEmpty()) {
            moveBot(playerCoordinates);
            return;
        }

        if (!goldCoordinates.isEmpty()) {

            if (bot.getCoordinates().equals(goldCoordinates)) {
                goldCoordinates.clear();

            } else {
                moveBot(goldCoordinates);
            }
            return;
        }
        moveBot();
    }


    private void moveBot() {
        ArrayList<String> possibleDirections = new ArrayList<>(Arrays.asList("N", "S", "E", "W"));
        possibleDirections.removeAll(invalidDirections.stream().toList());
        Random randomIndex = new Random();
        String direction = possibleDirections.get(randomIndex.nextInt(possibleDirections.size()));
        bot.move(direction);
        numMoves++;
    }

    //    private void pickUp()
    private void moveBot(HashMap<String, Integer> desiredCoordinates) {
        int[] coordinateDifference = {desiredCoordinates.get("x") - bot.getCoordinates().get("x"),
                bot.getCoordinates().get("y") - desiredCoordinates.get("y")};

        ArrayList<String> directionToTake = coordinatesToDirections(coordinateDifference);
        for (String direction : directionToTake) {
            if (invalidDirections.contains(direction)) {
                continue;
            }
            try {
                bot.move(direction);
                numMoves++;
            } catch (IllegalArgumentException e) {
                return;
            }
            return;
        }
        moveBot();
        // use .contains with the set
    }


    private ArrayList<String> coordinatesToDirections(int[] coordinateDifference) {
        ArrayList<String> directions = new ArrayList<>();
        if (coordinateDifference[0] > 0) {
            directions.add("E");
        } else if (coordinateDifference[0] < 0) {
            directions.add("W");
        }

        if (coordinateDifference[1] > 0) {
            directions.add("N");
        } else if (coordinateDifference[1] < 0) {
            directions.add("S");
        }
        return directions;
    }

    private void lookCommand() {
        this.mapMemory = UserInterface.getLocalArray(gameMap.getMap(), bot.getCoordinates(), player.getCoordinates());
        this.lastLookCoordinates = bot.getCoordinates();
        parseLocalMap();
    }

    private void parseLocalMap() {
        invalidDirections.clear();
        int xCounter = -2;
        int yCounter = -2;
        for (int i = 0; i < mapMemory.size(); i++) {
            xCounter = -2;
            for (int j = 0; j < mapMemory.get(i).size(); j++) {
                if (mapMemory.get(i).get(j).equals("B")) {
                    playerCoordinates.put("x", lastLookCoordinates.get("x") + xCounter);
                    playerCoordinates.put("y", lastLookCoordinates.get("y") + yCounter);
                }

                if (mapMemory.get(i).get(j).equals("G")) {
                    goldCoordinates.put("x", lastLookCoordinates.get("x") + xCounter);
                    goldCoordinates.put("y", lastLookCoordinates.get("y") + yCounter);
                }

                if (mapMemory.get(i).get(j).equals("#")) {
                    int[] direction = {j, i};
                    if (direction[0] < 2 && direction[1] == 2) {
                        invalidDirections.add("W");
                    } else if (direction[0] > 2 && direction[1] == 2) {
                        invalidDirections.add("E");
                    }
                    if (direction[1] < 2 && direction[0] == 2) {
                        invalidDirections.add("N");
                    } else if (direction[1] > 2 && direction[0] == 2) {
                        invalidDirections.add("S");
                    }
                }
                xCounter++;
            }
            yCounter++;
        }
    }
}
