package comp1110.ass2;

import javafx.scene.paint.Color;

import java.util.*;

public class Marrakech {
    /**
     * The matrix of tiles representing the board
     * For tiles[x][y]:
     * x corresponds to the tile row, working top to bottom, and
     * y corresponds to the tile row, working left to right.
     */
    public final int OFFSET_X = 100;
    public final int OFFSET_Y = 50;
    public final int TILE_SIZE = 70;
    public final int TILE_GAP = 10;
    public final int BOARD_WIDTH = 7;
    public final int BOARD_HEIGHT = 7;
    Tile[][] tiles;

    /**
     * All players are here. its length is between 2 and 4.
     */
    Player[] players;

    /**
     * the index indicating the current player
     */
    int currentPlayerIndex;

    /**
     * the assam character which every player control in turn
     */
    Assam assam;

    /**
     * get player by its color
     *
     * @param color
     * @return player
     */
    Player getPlayer(char color) {
        for (Player player : this.players) {
            if (player.color == color) return player;
        }
        return null;
    }

    public Assam getAssam() {
        return assam;
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    /**
     * get correlated javafx color object via the color char
     *
     * @param color
     * @return JavaFx.Color
     */
    public Color getJavaFxColor(char color) {
        if (color == 'y') return Color.web("#CCCC00");
        if (color == 'c') return Color.web("#00CCCC");
        if (color == 'r') return Color.RED;
        if (color == 'p') return Color.MEDIUMPURPLE;
        return Color.LIGHTGRAY;
    }

    public Marrakech(String gameString) {
        System.out.println(gameString);
        // current player index
        this.currentPlayerIndex = 0;

        // tiles
        this.tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];
        for (int p = 0; p < BOARD_WIDTH; p++) {
            for (int q = 0; q < BOARD_HEIGHT; q++) {
                this.tiles[p][q] = new Tile();
                this.tiles[p][q].position = new IntPair(p, q);
            }
        }

        // split game string into 3 parts
        int i = gameString.indexOf('A');
        String playerStringPart = gameString.substring(0, i);
        String assamStringPart = gameString.substring(i, i + 4);
        String boardStringPart = gameString.substring(i + 5);

        // player string
        {
            int n = playerStringPart.length() / 8;
            this.players = new Player[n];
            for (int k = 0; k < n; k++) {
                String playerString = playerStringPart.substring(k * 8, (k + 1) * 8);
                this.players[k] = new Player(playerString);
            }
        }

        // assam string
        this.assam = new Assam(assamStringPart);

        // board string
        {
            // key: color + id
            // value: positions
            Map<String, String> map = new HashMap<>();

            int n = boardStringPart.length() / 3;
            for (int k = 0; k < n; k++) {
                String rugAbbrString = boardStringPart.substring(k * 3, (k + 1) * 3);
                char color = rugAbbrString.charAt(0);

                // skip n00
                if (color == 'n') continue;

                // get position
                int row = k / BOARD_WIDTH;
                int col = k % BOARD_WIDTH;

                // set map default value
                map.putIfAbsent(rugAbbrString, "");

                // get and update positions string
                String positionsString = map.get(rugAbbrString);
                map.put(rugAbbrString, positionsString + row + col);
            }

            for (Map.Entry<String, String> entry : map.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                // get rug string
                String rugString = key + value;
                Rug rug = new Rug(rugString);

                // place rug on tiles
                for (IntPair position : rug.positions) {
                    if (position == null) continue;
                    this.tiles[position.x][position.y].rug = rug;
                }
            }
        }
    }

    /**
     * @param rug the given rug
     * @return if the rug is valid to put on the board
     */
    boolean isRugValid(Rug rug) {
        // rug color is invalid
        if ("cyrp".indexOf(rug.color) < 0) return false;

        // rug position is invalid
        for (IntPair position : rug.positions)
            if (position.x < 0 || position.x >= BOARD_WIDTH || position.y < 0 || position.y >= BOARD_HEIGHT)
                return false;

        // rug color + id is duplicated
        for (Tile[] ts : this.tiles)
            for (Tile t : ts)
                if (t.rug != null && t.rug.id == rug.id && t.rug.color == rug.color) return false;
        return true;
    }

    /**
     * when every player finished their 3 phases and all rugs have been used
     * //checking if the game is over
     *
     * @return if the game is over
     */
    boolean isGameOver() {

        //  First condition to check
        //  Checking if anyone has more than 0 rugs
        boolean everyPlayerHas0Rug = true;
        for (Player player : this.players) {
            if (player.out == false && player.remainingRugNumber > 0) {
                everyPlayerHas0Rug = false;
                break;
            }
        }
        if (everyPlayerHas0Rug == true) return true;

        //   Second condition to check
        //   Checking if there is only one player left (the winner)
//        int playerStillPlaying = 0;
//        for (Player player : this.players) {
//            if (player.out == false) {
//                playerStillPlaying = playerStillPlaying + 1;
//            }
//        }
//
//        if(playerStillPlaying == 1) return true; //One player left (the winner)

        return false; //More than one player still playing, AKA game is not over
    }

    /**
     * check if the given rug's placement is ok to replace
     * - the two tiles aren't covered by the SAME rug
     *
     * @param rug the given rug
     * @return if the placement is valid
     */
    boolean isPlacementValid(Rug rug) {
        // invalid rug
        if (!this.isRugValid(rug)) return false;

        Player currentPlayer = this.players[this.currentPlayerIndex];

        // must be the color of current player
        if (currentPlayer.color != rug.color) return false;

        IntPair p0 = this.assam.position;
        IntPair p1 = rug.positions[0];
        IntPair p2 = rug.positions[1];
        int d1 = Math.abs(p1.x - p0.x) + Math.abs(p1.y - p0.y);
        int d2 = Math.abs(p2.x - p0.x) + Math.abs(p2.y - p0.y);

        // exclude assam position itself
        if (d1 == 0 || d2 == 0) return false;

        // one of them must near assam position
        if (d1 != 1 && d2 != 1) return false;

        Rug rug1 = this.getTile(p1).rug;
        Rug rug2 = this.getTile(p2).rug;

        // empty tile
        if (rug1 == null || rug2 == null) return true;

        // must not be the same rug
        if (rug1 == rug2) {
            // except: the owner is out
            Player pl = this.getPlayer(rug1.color);
            return pl.out;
        }

        return true;
    }

    /**
     * place a rug (before call this method please ensure that the placement is reasonable)
     *
     * @param rug the given rug
     */
    void makePlacement(Rug rug) {
    }

    /**
     * check if the tile where player stayed has other's rug
     *
     * @return if the current player need to pay
     */
    boolean isCurrentPlayerNeedToPay() {
        return true;
    }

    public void calculateColoredTiles(IntPair presentPosition, ArrayList<Tile> connectedTiles, ArrayList<Tile> visitedTiles) {
        // four directions (up, down, left, right)
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};
        char tileColor = this.getTile(presentPosition).rug.color;
        System.out.println("====");
        System.out.println(presentPosition.x + "," + presentPosition.y);
        String s1 = "";
        for (Tile connectedTile : connectedTiles) {
            s1 += connectedTile.position.x + "," + connectedTile.position.y + " ";
        }
        System.out.println(s1);
        String s2 = "";
        for (Tile connectedTile : visitedTiles) {
            s2 += connectedTile.position.x + "," + connectedTile.position.y + " ";
        }
        System.out.println(s2);

        for (int direction = 0; direction < 4; direction++) {
            int newX = presentPosition.x + dx[direction];
            int newY = presentPosition.y + dy[direction];
            if (newX < 0 || newX > 6 || newY < 0 || newY > 6) continue;
            // within board
            Tile adjacentTile = tiles[newX][newY];
            if (visitedTiles.contains(adjacentTile)) continue;
            visitedTiles.add(adjacentTile);

            Rug adjacentRug = adjacentTile.getRug();
            if (adjacentRug != null && adjacentRug.color == tileColor && !connectedTiles.contains(adjacentTile)) {
                // add new
                connectedTiles.add(adjacentTile);
                calculateColoredTiles(new IntPair(newX, newY), connectedTiles, visitedTiles);
            }
        }
    }

    /**
     * check the nearby tiles and count the number of the connected rugs with the same color.
     * that is the coins that the current player need to pay
     *
     * @return the payment amount
     */
    int getPaymentAmount() {
        System.out.println("=----?");
        IntPair presentPosition = this.assam.position;
        Tile tile = this.getTile(presentPosition);
        System.out.println(tile.position.x + "," + tile.position.y);

        Rug rug = tile.getRug();
        if (rug == null) return 0;

        char tileColor = rug.color;
        char playerColor = this.players[this.currentPlayerIndex].color;
        ArrayList<Tile> connectedTiles = new ArrayList<>();
        ArrayList<Tile> visitedTiles = new ArrayList<>();

        // On their own tile or blank tile
        // FIXME: task 11, there might be some misunderstanding of the name of the method, need tutor to declare
//        if (playerColor == tileColor) return 0;

        connectedTiles.add(tile);
        visitedTiles.add(tile);
        this.calculateColoredTiles(presentPosition, connectedTiles, visitedTiles);
        return connectedTiles.size();
    }

    int getPlayerRugTilesAmount(Player player) {
        if (player.out) return 0;
        int n = 0;
        for (Tile[] ts : this.tiles)
            for (Tile t : ts)
                if (t.rug != null && t.rug.color == player.color)
                    n += 1;
        return n;
    }

    /**
     * (before call this method please ensure that the game has ended)
     *
     * @return winner of the game
     */
    ArrayList<Player> getWinner() {
        // max score
        ArrayList<Player> players = new ArrayList<Player>();
        int max_score = 0;

        for (Player player : this.players) {
            int score = player.coins + this.getPlayerRugTilesAmount(player);
            if (score > max_score) max_score = score;
        }

        for (Player player : this.players) {
            int score = player.coins + this.getPlayerRugTilesAmount(player);
            if (score == max_score) players.add(player);
        }

        if (players.size() == 1) return players;

        // max coin
        ArrayList<Player> players1 = new ArrayList<Player>();
        int max_coin = 0;

        for (Player player : players) {
            if (player.coins > max_coin) max_coin = player.coins;
        }

        for (Player player : players) {
            if (player.coins == max_coin) players1.add(player);
        }
        return players1;
    }


    public void turnNext() {
        this.currentPlayerIndex += 1;
        this.currentPlayerIndex %= this.players.length;
    }

    /**
     * get current game state
     * if game is not over, return 'n'
     * if game is over
     * if no winner (tie) return 't'
     * else return winner's color
     *
     * @return game state
     */
    char getGameState() {
        if (!this.isGameOver()) return 'n';
        ArrayList<Player> winner = this.getWinner();
        if (winner.size() > 1) return 't';
        return winner.get(0).color;
    }

    /**
     * @param position the give position
     * @return the tile at this position
     */
    public Tile getTile(IntPair position) {
        return this.tiles[position.x][position.y];
    }

    /**
     * Determine whether a rug String is valid.
     * For this method, you need to determine whether the rug String is valid, but do not need to determine whether it
     * can be placed on the board (you will determine that in Task 10 ). A rug is valid if and only if all the following
     * conditions apply:
     * - The String is 7 characters long
     * - The first character in the String corresponds to the colour character of a player present in the game
     * - The next two characters represent a 2-digit ID number
     * - The next 4 characters represent coordinates that are on the board
     * - The combination of that ID number and colour is unique
     * To clarify this last point, if a rug has the same ID as a rug on the board, but a different colour to that rug,
     * then it may still be valid. Obviously multiple rugs are allowed to have the same colour as well so long as they
     * do not share an ID. So, if we already have the rug c013343 on the board, then we can have the following rugs
     * - c023343 (Shares the colour but not the ID)
     * - y013343 (Shares the ID but not the colour)
     * But you cannot have c014445, because this has the same colour and ID as a rug on the board already.
     *
     * @param gameString A String representing the current state of the game as per the README
     * @param rug        A String representing the rug you are checking
     * @return true if the rug is valid, and false otherwise.
     */
    public static boolean isRugValid(String gameString, String rug) {
        // convert string to object
        Marrakech marrakech = new Marrakech(gameString);
        Rug rug1 = new Rug(rug);

        // check rug valid
        return marrakech.isRugValid(rug1);
        // FIXME: Task 4 [DONE]
    }

    /**
     * Roll the special Marrakech die and return the result.
     * Note that the die in Marrakech is not a regular 6-sided die, since there
     * are no faces that show 5 or 6, and instead 2 faces that show 2 and 3. That
     * is, of the 6 faces
     * - One shows 1
     * - Two show 2
     * - Two show 3
     * - One shows 4
     * As such, in order to get full marks for this task, you will need to implement
     * a die where the distribution of results from 1 to 4 is not even, with a 2 or 3
     * being twice as likely to be returned as a 1 or 4.
     *
     * @return The result of the roll of the die meeting the criteria above
     */
    public static int rollDie() {
        int[] diceValue = new int[]{1, 2, 2, 3, 3, 4};
        Random ranDie = new Random();
        // FIXME: Task 6 [DONE]
        return diceValue[ranDie.nextInt(6)];
    }


    /**
     * Determine whether a game of Marrakech is over
     * Recall from the README that a game of Marrakech is over if a Player is about to enter the rotation phase of their
     * turn, but no longer has any rugs. Note that we do not encode in the game state String whose turn it is, so you
     * will have to think about how to use the information we do encode to determine whether a game is over or not.
     *
     * @param currentGame A String representation of the current state of the game.
     * @return true if the game is over, or false otherwise.
     */
    public static boolean isGameOver(String currentGame) {
        // FIXME: Task 8
        Marrakech game = new Marrakech(currentGame);
        return game.isGameOver();
    }

    /**
     * Implement Assam's rotation.
     * Recall that Assam may only be rotated left or right, or left alone -- he cannot be rotated a full 180 degrees.
     * For example, if he is currently facing North (towards the top of the board), then he could be rotated to face
     * East or West, but not South. Assam can also only be rotated in 90 degree increments.
     * If the requested rotation is illegal, you should return Assam's current state unchanged.
     *
     * @param currentAssam A String representing Assam's current state
     * @param rotation     The requested rotation, in degrees. This degree reading is relative to the direction Assam
     *                     is currently facing, so a value of 0 for this argument will keep Assam facing in his
     *                     current orientation, 90 would be turning him to the right, etc.
     * @return A String representing Assam's state after the rotation, or the input currentAssam if the requested
     * rotation is illegal.
     */
    public static String rotateAssam(String currentAssam, int rotation) {
        // FIXME: Task 9
        return "";
    }

    /**
     * Determine whether a potential new placement is valid (i.e that it describes a legal way to place a rug).
     * There are a number of rules which apply to potential new placements, which are detailed in the README but to
     * reiterate here:
     * 1. A new rug must have one edge adjacent to Assam (not counting diagonals)
     * 2. A new rug must not completely cover another rug. It is legal to partially cover an already placed rug, but
     * the new rug must not cover the entirety of another rug that's already on the board.
     *
     * @param gameState A game string representing the current state of the game
     * @param rug       A rug string representing the candidate rug which you must check the validity of.
     * @return true if the placement is valid, and false otherwise.
     */
    public static boolean isPlacementValid(String gameState, String rug) {
        // FIXME: Task 10
        Marrakech game = new Marrakech(gameState);
        Rug rug1 = new Rug(rug);
        for (int i = 0; i < game.players.length; i++) {
            if (game.players[i].color == rug1.color) {
                game.currentPlayerIndex = i;
                break;
            }
        }
        return game.isPlacementValid(rug1);
    }


    /**
     * Determine the amount of payment required should another player land on a square.
     * For this method, you may assume that Assam has just landed on the square he is currently placed on, and that
     * the player who last moved Assam is not the player who owns the rug landed on (if there is a rug on his current
     * square). Recall that the payment owed to the owner of the rug is equal to the number of connected squares showing
     * on the board that are of that colour. Similarly to the placement rules, two squares are only connected if they
     * share an entire edge -- diagonals do not count.
     *
     * @param gameString A String representation of the current state of the game.
     * @return The amount of payment due, as an integer.
     */
    public static int getPaymentAmount(String gameString) {
        Marrakech marrakech = new Marrakech(gameString);
        return marrakech.getPaymentAmount();
    }

    /**
     * Determine the winner of a game of Marrakech.
     * For this task, you will be provided with a game state string and have to return a char representing the colour
     * of the winner of the game. So for example if the cyan player is the winner, then you return 'c', if the red
     * player is the winner return 'r', etc...
     * If the game is not yet over, then you should return 'n'.
     * If the game is over, but is a tie, then you should return 't'.
     * Recall that a player's total score is the sum of their number of dirhams and the number of squares showing on the
     * board that are of their colour, and that a player who is out of the game cannot win. If multiple players have the
     * same total score, the player with the largest number of dirhams wins. If multiple players have the same total
     * score and number of dirhams, then the game is a tie.
     *
     * @param gameState A String representation of the current state of the game
     * @return A char representing the winner of the game as described above.
     */
    public static char getWinner(String gameState) {
        // FIXME: Task 12
        Marrakech game = new Marrakech(gameState);
        return game.getGameState();
    }

    /**
     * Implement Assam's movement.
     * Assam moves a number of squares equal to the die result, provided to you by the argument dieResult. Assam moves
     * in the direction he is currently facing. If part of Assam's movement results in him leaving the board, he moves
     * according to the tracks diagrammed in the assignment README, which should be studied carefully before attempting
     * this task. For this task, you are not required to do any checking that the die result is sensible, nor whether
     * the current Assam string is sensible either -- you may assume that both of these are valid.
     *
     * @param currentAssam A string representation of Assam's current state.
     * @param dieResult    The result of the die, which determines the number of squares Assam will move.
     * @return A String representing Assam's state after the movement.
     */
    public static String moveAssam(String currentAssam, int dieResult) {
        // FIXME: Task 13
        return "";
    }

    /**
     * Place a rug on the board
     * This method can be assumed to be called after Assam has been rotated and moved, i.e in the placement phase of
     * a turn. A rug may only be placed if it meets the conditions listed in the isPlacementValid task. If the rug
     * placement is valid, then you should return a new game string representing the board after the placement has
     * been completed. If the placement is invalid, then you should return the existing game unchanged.
     *
     * @param currentGame A String representation of the current state of the game.
     * @param rug         A String representation of the rug that is to be placed.
     * @return A new game string representing the game following the successful placement of this rug if it is valid,
     * or the input currentGame unchanged otherwise.
     */
    public static String makePlacement(String currentGame, String rug) {
        // FIXME: Task 14
        return "";
    }
}
