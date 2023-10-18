package comp1110.ass2;

import java.util.ArrayList;

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

    ArrayList<Rug> getPossibleRugs() {
        var player = this.getCurrentPlayer();
        var rugs = new ArrayList<Rug>();
        for (int mode = 0; mode < 12; mode++) {
            var positions = Utils.createRugPositions(mode, assam.position.clone());
            var rug = new Rug(player.color, 15 - player.remainingRugNumber, positions);
            if (this.isPlacementValid(rug)) rugs.add(rug);
        }
        return rugs;
    }

    Rug easyAIPlayerPutRug() {
        var rugs = this.getPossibleRugs();
        return rugs.get(Utils.randint(rugs.size()));
    }

    Rug hardAIPlayerPutRug() {
        var rugs = this.getPossibleRugs();
        int n = rugs.size();
        var rugs2 = new Rug[n];
        for (int i = 0; i < n; i++) rugs2[i] = rugs.get(i);
        Utils.shuffle(rugs2);

        int maxScore = 0;
        int maxIndex = 0;
        for (int i = 0; i < rugs2.length; i++) {
            var rug = rugs2[i];
            var t1 = board.getTile(rug.positions[0]);
            var t2 = board.getTile(rug.positions[1]);
            var r1 = t1.rug;
            var r2 = t2.rug;
            t1.rug = rug;
            t2.rug = rug;

            // try best to connect self rugs
            var score1 = 0;
            var amount = this.getConnectedTileAmount(t1);
            if (amount > 2) score1 += 1;
            score1 += amount / 4;

            // try to cover enemy's rug
            var score2 = 0;
            if (r1 != null && r1.color != rug.color) score2 += 1;
            if (r2 != null && r2.color != rug.color) score2 += 1;

            // don't put rug behind self
            var score3 = 0;

            // don't cover self rugs
            var score4 = 0;
            if (r1 != null && r1.color == rug.color) score4 -= 1;
            if (r2 != null && r2.color == rug.color) score4 -= 1;

            var score = score1 + score2 + score3 + score4;
            if (score > maxScore) {
                maxScore = score;
                maxIndex = i;
            }

            t1.rug = r1;
            t2.rug = r2;
        }

        return rugs2[maxIndex];
    }

    public Rug aiPlayerPutRug() {
        boolean hardAI = this.getCurrentPlayer().hardAI;
        if (hardAI) return this.hardAIPlayerPutRug();
        return this.easyAIPlayerPutRug();
    }

    public void aiPlayerSetDegree() {
        boolean hardAI = this.getCurrentPlayer().hardAI;
        if (hardAI) this.hardAIPlayerSetDegree();
        else this.easyAIPlayerSetDegree();
    }

    void easyAIPlayerSetDegree() {
        var d = Utils.randint(3);

        int j = 0;
        var newDegree = 0;
        for (int i = 0; i < 4; i++) {
            newDegree = i * 90;
            if ((newDegree - assam.degree + 360) % 360 == 180) continue;
            if (j == d) break;
            j += 1;
        }

        assam.degree = newDegree;
        assam.oldDegree = newDegree;
    }

    void hardAIPlayerSetDegree() {
        var pos = assam.position;
        var degree = assam.degree;
        double minPayment = Utils.RowMax * Utils.ColumnMax;
        var minPaymentDegree = 0;
        for (int i = 0; i < 4; i++) {
            var newDegree = i * 90;
            if ((newDegree - degree + 360) % 360 == 180) continue;
            assam.degree = newDegree;
            double payment = 0;
            for (int j = 0; j < 4; j++) {
                double rate = 1.0 / 6;
                if (j == 1 || j == 2) rate = 1.0 / 3;
                assam.position = pos;
                assam.move(j);
                payment += this.getPaymentAmount() * rate;
            }
            if (payment < minPayment) {
                minPayment = payment;
                minPaymentDegree = newDegree;
            }
        }
        assam.position = pos;
        assam.degree = minPaymentDegree;
        assam.oldDegree = minPaymentDegree;
    }

    public Game(int playerAmount, int aiPlayerAmount, int hardAIPlayerAmount) {
        // current player index
        currentPlayerIndex = 0;
        int n = playerAmount + aiPlayerAmount + hardAIPlayerAmount;
        int m = playerAmount + aiPlayerAmount;
        players = new Player[n];
        var colors = "cypr";
        for (int k = 0; k < n; k++) {
            players[k] = new Player(colors.charAt(k), k >= playerAmount, k >= m);
        }
        Utils.shuffle(players);
        assam = new Assam();
        board = new Board();
    }

    public Game(String gameString) {
        // current player index
        currentPlayerIndex = 0;

        // split game string into 3 parts
        int i = gameString.indexOf('A');
        String playerStringPart = gameString.substring(0, i);
        String assamStringPart = gameString.substring(i, i + 4);
        String boardStringPart = gameString.substring(i + 5);

        // player string
        int n = playerStringPart.length() / 8;
        players = new Player[n];
        for (int k = 0; k < n; k++) {
            String playerString = playerStringPart.substring(k * 8, (k + 1) * 8);
            players[k] = new Player(playerString);
        }

        // assam string
        assam = new Assam(assamStringPart);

        // board string
        board = new Board(boardStringPart);
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

    int getConnectedTileAmount(Tile tile) {
        var rug = tile.rug;
        if (rug == null) return 0;

        ArrayList<Tile> connectedTiles = new ArrayList<>();
        ArrayList<Tile> visitedTiles = new ArrayList<>();
        connectedTiles.add(tile);
        visitedTiles.add(tile);

        this.calculateConnectedTileAmount(tile.position, connectedTiles, visitedTiles, rug.color);
        return connectedTiles.size();
    }

    void calculateConnectedTileAmount(
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
                calculateConnectedTileAmount(pos, connectedTiles, visitedTiles, tileColor);
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
        var assamPos = assam.position;
        var tile = board.getTile(assamPos);
        return this.getConnectedTileAmount(tile);
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
        ArrayList<Player> players = new ArrayList();
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
        ArrayList<Player> players1 = new ArrayList();
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
        System.out.println("player " + this.getCurrentPlayer().color + " assam pos: " + assam.position);
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
        var diceValue = new int[]{1, 2, 2, 3, 3, 4};
        return diceValue[Utils.randint(6)];
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

