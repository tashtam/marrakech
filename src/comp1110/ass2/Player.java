package comp1110.ass2;


import java.util.ArrayList;

/**
 * The class Player defines the players currently participating in the game.
 * Each player has 4 fields: color, coins, remainingRugNumber, and out.
 * Parameter color: each player's color.
 * Parameter coins: the number of coins each player has.
 * Parameter remainingRugNumber: the remaining number of rugs each player has.
 * Parameter out: whether each player is in or out of the game.
 * <p>
 * Each player has the method payTo.
 */
public class Player {
    public char color;
    public int coins = 30;
    public int remainingRugNumber = 15;
    public boolean out = false;
    public boolean ai = false;
    public boolean hardAI = false;
    Game game;

    public Player(char color, boolean ai, boolean hardAI, Game game) {
        this.color = color;
        this.ai = ai;
        this.hardAI = hardAI;
        this.game = game;
    }

    public Player(String playerString) {
        color = playerString.charAt(1);
        coins = Integer.parseInt(playerString, 2, 5, 10);
        remainingRugNumber = Integer.parseInt(playerString, 5, 7, 10);
        out = playerString.charAt(7) == 'o';
    }

    @Override
    public String toString() {
        String s = out ? "o" : "i";
        return String.format("P%c%03d%02d", color, coins, remainingRugNumber) + s;
    }

    /**
     * This method is for paying coins/dirhams to another player when the current player steps on another player's rug.
     *
     * @param other the player receiving payment from the current player
     * @param coins the amount of coins to be paid
     */
    void payTo(Player other, int coins) {
        if (this.coins < coins) {
            this.out = true;
            other.coins += this.coins;
            this.coins = 0;
        } else {
            this.coins -= coins;
            other.coins += coins;
        }
    }

    public ArrayList<Rug> getPossibleRugs() {
        var rugs = new ArrayList<Rug>();
        for (int mode = 0; mode < 12; mode++) {
            var positions = Utils.createRugPositions(mode, game.assam.position);
            var rug = this.createRug(positions);
            if (game.isPlacementValid(rug)) rugs.add(rug);
        }
        return rugs;
    }

    public boolean checkPositionsValid(IntPair[] positions) {
        var rug = this.createRug(positions);
        return game.isPlacementValid(rug);
    }

    Rug easyAIPlayerPutRug() {
        var rugs = this.getPossibleRugs();
        return rugs.get(Utils.randint(rugs.size()));
    }

    Rug hardAIPlayerPutRug() {
        var board = game.board;
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
            var amount = game.getConnectedTileAmount(t1);
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
        if (hardAI) return this.hardAIPlayerPutRug();
        return this.easyAIPlayerPutRug();
    }

    public void aiPlayerSetDegree() {
        if (hardAI) this.hardAIPlayerSetDegree();
        else this.easyAIPlayerSetDegree();
    }

    void easyAIPlayerSetDegree() {
        var d = Utils.randint(3);
        var assam = game.assam;
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
        var assam = game.assam;
        var pos = assam.position;
        var degree = assam.degree;
        double minPayment = Utils.MAP_SIZE * Utils.MAP_SIZE;
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
                payment += game.getPaymentAmount() * rate;
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

    public int calculateScore() {
        return coins + game.board.getPlayerRugScore(this);
    }

    public void pay() {
        var tile = game.board.getTile(game.assam.position);
        if (tile.rug == null) return;
        var owner = game.getPlayer(tile.rug.color);
        if (owner.out) return;
        if (owner != this) {
            var money = game.getPaymentAmount();
            this.payTo(owner, money);
        }
    }

    public Rug createRug(IntPair[] positions) {
        return new Rug(color, 15 - remainingRugNumber, positions);
    }
}

