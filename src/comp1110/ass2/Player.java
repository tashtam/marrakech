package comp1110.ass2;

// Tashia
/**
 * The class Player defines the players currently participating in the game.
 * Each player has 4 fields: color, coins, remainingRugNumber, and out.
 * Parameter color: each player's color.
 * Parameter coins: the number of coins each player has.
 * Parameter remainingRugNumber: the remaining number of rugs each player has.
 * Parameter out: whether each player is in or out of the game.
 *
 * Each player has the method payTo.
 */
public class Player {
    char color;
    int coins;
    int remainingRugNumber;
    boolean out;


    /**
     * This method is for paying coins/dirhams to another player when the current player steps on another player's rug.
     * @param other the player receiving payment from the current player
     * @param coins the amount of coins to be paid
     */
    void payTo(Player other, int coins){};
}

