package comp1110.ass2;


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
    char color;
    int coins;
    int remainingRugNumber;
    boolean out;

    public int getCoins() {
        return coins;
    }

    public char getColor() {
        return color;
    }

    public int getRemainingRugNumber() {
        return remainingRugNumber;
    }

    public Player(String playerString) {
        this.color = playerString.charAt(1);
        this.coins = Integer.parseInt(playerString, 2, 5, 10);
        this.remainingRugNumber = Integer.parseInt(playerString, 5, 7, 10);
        char inout = playerString.charAt(7);
        if ('i' == inout) {
            this.out = false;
        } else if (inout == 'o') {
            this.out = true;
        } else {
            System.out.println("wrong type constructor");
        }
    }


    /**
     * This method is for paying coins/dirhams to another player when the current player steps on another player's rug.
     *
     * @param other the player receiving payment from the current player
     * @param coins the amount of coins to be paid
     */
    void payTo(Player other, int coins) {
    }
}

