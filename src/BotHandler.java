import java.util.*;

/**
 * Determines what inputs the bot should take in the game
 */ 
public class BotHandler {

    private final GameMap gameMap;
    private final Person bot;
    private final Person player;
    private final HashMap<String, Integer> playerCoordinates;
    private final HashMap<String, Integer> goldCoordinates;
    private final HashMap<String, Integer> exitCoordinates;
    /** Directions that the bot remembers that have walls, generated when bot looks. 
      * Using set to prevent duplicate values. 
      */
    private final Set<String> invalidDirections;
    /** Bot's memory of the map it last looked at. */
    private ArrayList<ArrayList<String>> mapMemory;
    /** Coordiantes of the bot when it last looked at the map. */
    private HashMap<String, Integer> lastLookCoordinates;
    /** Number of moves since last looking at the map. */
    private int numMoves;

    /** 
     * Constructor class for BotHandler.
     * @param gameMap Map of the game, used in look commands.
     * @param bot Used for getting coordinates and performing actions on the bot.
     * @param player Used for getting players coordinates.
     */  
    public BotHandler(GameMap gameMap, Person bot, Person player) {
        this.gameMap = gameMap;
        this.bot = bot;
        this.player = player;
        this.invalidDirections = new HashSet<>();
        this.goldCoordinates = new HashMap<>();
        this.playerCoordinates = new HashMap<>();
        this.exitCoordinates = new HashMap<>();
    }

    /**
     * Handles the bot's turn, deciding which actions to do based on
     * the data available to it.
     */
    public void botTurn() {
        UserInterface.printMap(UserInterface.getLocalArray(gameMap.getMap(), bot.getCoordinates(), player.getCoordinates()));
        // Look around on first start.
        if (mapMemory == null) {
            lookCommand();
            return;
        }
        // When the bot has moved several times look around to refresh
        // surroundings
        if (numMoves > 2) {
            lookCommand();
            numMoves = 0;
            return;
        }
        // If the player has been seen, move towards them. Highest priority.
        if (!playerCoordinates.isEmpty()) {
            moveBot(playerCoordinates);
            return;
        }
        // Try to exit the game if the bot has enough gold.
        System.out.println(bot.getNumGold());
        if ((bot.getNumGold() == gameMap.getNumGoldRequired()) && !(exitCoordinates.isEmpty())) {

            if (bot.quitGame()) {
                UserInterface.botExit();
            }
            moveBot(exitCoordinates);
            return;
        }

        // If the gold has been seen, move towards it. Lower priority than the player.
        if (!goldCoordinates.isEmpty()) {

            // Pick up the gold if on the same tile.
            if (bot.getCoordinates().equals(goldCoordinates)) {
                bot.pickUpGold();
                goldCoordinates.clear();
            // Otherwise try to move towards the tile.
            } else {
                moveBot(goldCoordinates);
            }
            return;
        }
        // If no tiles are known, perform "dumb", semi-random movement.
        moveBot();
    }

    /**
     * Move the bot in a random direction that is not near a wall
     */
    private void moveBot() {
        // Array list of possible directions that the bot can go in.
        ArrayList<String> possibleDirections = new ArrayList<>(Arrays.asList("N", "S", "E", "W"));
        // Remove the directions that are close to walls, info sourced from last bot look call.
        possibleDirections.removeAll(invalidDirections.stream().toList());
        Random randomIndex = new Random();
        // Get random direction form the available ones.
        String direction = possibleDirections.get(randomIndex.nextInt(possibleDirections.size()));
        bot.move(direction);
        // Increment number of moves.
        numMoves++;
    }
    
    /**
     * Move bot towards given coordinates.
     * @param desiredCoordinates Coordinates that the bot needs to move towards
     */
    private void moveBot(HashMap<String, Integer> desiredCoordinates) {
        // Get the coordinates towards the goal.
        int[] coordinateDifference = {desiredCoordinates.get("x") - bot.getCoordinates().get("x"),
                bot.getCoordinates().get("y") - desiredCoordinates.get("y")};

        // Convert the coordinates into directions to take.
        ArrayList<String> directionToTake = coordinatesToDirections(coordinateDifference);
        for (String direction : directionToTake) { 
            try {
                bot.move(direction);
                numMoves++;
            } catch (IllegalArgumentException e) {
                return;
            }
            return;
        }
        // If impossible, just use random movement
        moveBot();
    }

    /**
     * Convert cartesian coordinates into a string of cardinal directions for the bot to
     * take.
     * @param coordinateDifference Coordinates to convert
     * @return String of cardinal directions, "N", "S", "E", "W".
     */
    private ArrayList<String> coordinatesToDirections(int[] coordinateDifference) {
        // ArrayList that stores the list of directions to take.
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

    /** 
     * Get the bot to look around the map and record the details of the environment
     * Records the coordinates of when the bot last looked for use in getting overall, not local coordinates.
     */
    private void lookCommand() {
        this.mapMemory = UserInterface.getLocalArray(gameMap.getMap(), bot.getCoordinates(), player.getCoordinates());
        this.lastLookCoordinates = bot.getCoordinates();
        parseLocalMap();
    }

    /**
     * Records coordinates of important tiles and stores them.
     * Moreover, records the locations of blocking walls.
     */
    private void parseLocalMap() {
        // Clear the invalid directions
        invalidDirections.clear();
        /* Set counters. Left and top tiles are -2 relative to the center of the local map so
         * this counter is set. */
        int xCounter = -2;
        int yCounter = -2;
        // Iterate through the nested local map.
        for (int i = 0; i < mapMemory.size(); i++) {
            // Reset the xCounter on every new row.
            xCounter = -2;
            for (int j = 0; j < mapMemory.get(i).size(); j++) {
                // B reperesents the player. Always record the player over gold and exits.
                if (mapMemory.get(i).get(j).equals("B")) {
                    // Record the coordinates.
                    playerCoordinates.put("x", lastLookCoordinates.get("x") + xCounter);
                    playerCoordinates.put("y", lastLookCoordinates.get("y") + yCounter);
                }

                // Check for gold
                if (mapMemory.get(i).get(j).equals("G")) {
                    goldCoordinates.put("x", lastLookCoordinates.get("x") + xCounter);
                    goldCoordinates.put("y", lastLookCoordinates.get("y") + yCounter);
                }

                // Check for exits, least important.
                if (mapMemory.get(i).get(j).equals("E")) {
                    exitCoordinates.put("x", lastLookCoordinates.get("x") + xCounter);
                    exitCoordinates.put("y", lastLookCoordinates.get("y") + yCounter);
                }

                // Check for walls.
                if (mapMemory.get(i).get(j).equals("#")) {
                    // Store direction in primitive list as easier to read than hashmap's .get()
                    int[] direction = {j, i};
                    /* If it blocks the west side and is on the same y plane, add west as an
                     * invalid direction. */
                    if (direction[0] < 2 && direction[1] == 2) {
                        invalidDirections.add("W");
                    // Vice-versa for east.
                    } else if (direction[0] > 2 && direction[1] == 2) {
                        invalidDirections.add("E");
                    }
                    /* If it blocks the north side and is on the same x plane, add north as an
                     * invalid direction. */
                    if (direction[1] < 2 && direction[0] == 2) {
                        invalidDirections.add("N");
                    // Vice-versa for south
                    } else if (direction[1] > 2 && direction[0] == 2) {
                        invalidDirections.add("S");
                    }
                }
                // Increase counter after reading column
                xCounter++;
            }
            // Increase counter after reading row.
            yCounter++;
        }
    }
}
