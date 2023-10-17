package comp1110.ass2;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    public int phase = 0;

    public Board board;

    /**
     * All players are here. its length is between 2 and 4.
     */
    public Player[] players;

    /**
     * the index indicating the current player
     */
    public int currentPlayerIndex;

    /**
     * the assam character which every player control in turn
     */
    public Assam assam;

    /**
     * get player by its color
     *
     * @param color
     * @return player
     */
    public Player getPlayer(char color) {
        for (Player player : this.players) {
            if (player.color == color) return player;
        }
        return null;
    }

    public Game(int playerAmount) {
        // current player index
        this.currentPlayerIndex = 0;
        int n = playerAmount;
        this.players = new Player[n];
        var colors = "cypr";
        for (int k = 0; k < n; k++) this.players[k] = new Player(colors.charAt(k));
        this.assam = new Assam();
        this.board = new Board();
    }

    public Game(String gameString) {
        // current player index
        this.currentPlayerIndex = 0;

        // split game string into 3 parts
        int i = gameString.indexOf('A');
        String playerStringPart = gameString.substring(0, i);
        String assamStringPart = gameString.substring(i, i + 4);
        String boardStringPart = gameString.substring(i + 5);

        // player string
        int n = playerStringPart.length() / 8;
        this.players = new Player[n];
        for (int k = 0; k < n; k++) {
            String playerString = playerStringPart.substring(k * 8, (k + 1) * 8);
            this.players[k] = new Player(playerString);
        }

        // assam string
        this.assam = new Assam(assamStringPart);

        // board string
        this.board = new Board(boardStringPart);
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }

    /**
     * when every player finished their 3 phases and all rugs have been used
     * //checking if the game is over
     *
     * @return if the game is over
     */
    public boolean isGameOver() {
        //  Checking if anyone has more than 0 rugs
        for (Player player : this.players) {
            if (!player.out && player.remainingRugNumber > 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * check if the given rug's placement is ok to replace
     * - the two tiles aren't covered by the SAME rug
     *
     * @param rug the given rug
     * @return if the placement is valid
     */
    public boolean isPlacementValid(Rug rug) {
        // invalid rug
        if (!this.board.isRugValid(rug)) return false;

        IntPair p0 = this.assam.position;
        IntPair p1 = rug.positions[0];
        IntPair p2 = rug.positions[1];
        int d1 = Math.abs(p1.x - p0.x) + Math.abs(p1.y - p0.y);
        int d2 = Math.abs(p2.x - p0.x) + Math.abs(p2.y - p0.y);

        // exclude assam position itself
        if (d1 == 0 || d2 == 0) return false;

        // one of them must near assam position
        if (d1 != 1 && d2 != 1) return false;

        Rug rug1 = this.board.getTile(p1).rug;
        Rug rug2 = this.board.getTile(p2).rug;

        // empty tile
        if (rug1 == null || rug2 == null) return true;

        // must not be the same rug
        return rug1 != rug2;
    }

    void calculateColoredTiles(
            IntPair presentPosition,
            ArrayList<Tile> connectedTiles,
            ArrayList<Tile> visitedTiles,
            char tileColor
    ) {
        // four directions (up, down, left, right)
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int direction = 0; direction < 4; direction++) {
            int newX = presentPosition.x + dx[direction];
            int newY = presentPosition.y + dy[direction];

            // get tile
            IntPair pos = new IntPair(newX, newY);
            Tile adjacentTile = this.board.getTile(pos);

            // out of board
            if (adjacentTile == null) continue;

            // visited
            if (visitedTiles.contains(adjacentTile)) continue;
            visitedTiles.add(adjacentTile);

            // create a new object adjacentRug
            Rug adjacentRug = adjacentTile.rug;
            if (adjacentRug != null && adjacentRug.color == tileColor && !connectedTiles.contains(adjacentTile)) {
                // add new
                connectedTiles.add(adjacentTile);
                calculateColoredTiles(pos, connectedTiles, visitedTiles, tileColor);
            }
        }
    }

    /**
     * check the nearby tiles and count the number of the connected rugs with the same color.
     * that is the coins that the current player need to pay
     *
     * @return the payment amount
     */
    public int getPaymentAmount() {
        var assamPos = this.assam.position;
        var tile = this.board.getTile(assamPos);
        var rug = tile.rug;

        if (rug == null) return 0;

        char tileColor = rug.color;

        ArrayList<Tile> connectedTiles = new ArrayList<>();
        ArrayList<Tile> visitedTiles = new ArrayList<>();
        connectedTiles.add(tile);
        visitedTiles.add(tile);

        this.calculateColoredTiles(assamPos, connectedTiles, visitedTiles, tileColor);
        return connectedTiles.size();
    }

    public int getPlayerScore(Player player) {
        return player.coins + board.getPlayerRugScore(player);
    }

    /**
     * (before call this method please ensure that the game has ended)
     *
     * @return winner of the game
     */
    public ArrayList<Player> getWinner() {
        // max score
        ArrayList<Player> players = new ArrayList<Player>();
        int max_score = 0;

        for (Player player : this.players) {
            int score = this.getPlayerScore(player);
            if (score > max_score) max_score = score;
        }

        for (Player player : this.players) {
            int score = this.getPlayerScore(player);
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
        currentPlayerIndex += 1;
        currentPlayerIndex %= players.length;
        if (players[currentPlayerIndex].out) {
            this.turnNext();
        }
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
    public char getGameState() {
        if (!this.isGameOver()) return 'n';
        ArrayList<Player> winner = this.getWinner();
        if (winner.size() > 1) return 't';
        return winner.get(0).color;
    }

    public int rollDie() {
        int[] diceValue = new int[]{1, 2, 2, 3, 3, 4};
        Random ranDie = new Random();
        return diceValue[ranDie.nextInt(6)];
        // FIXME: Task 6 [DONE]
    }

    public void makePlacement(Rug rug) {
        for (IntPair pos : rug.positions) {
            var tile = this.board.getTile(pos);
            tile.rug = rug;
        }
        var player = this.getPlayer(rug.color);
        player.remainingRugNumber -= 1;
    }

    @Override
    public String toString() {
        var s = "";
        for (Player player : players) {
            s += player.toString();
        }
        return s + assam.toString() + board.toString();
    }
}

