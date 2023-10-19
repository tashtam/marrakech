package comp1110.ass2;


/**
 * This class defines Player, referring to the players currently participating in the game
 * There are six fields: (color, coins, remainingRugNumber, out, ai, hardAI) and one method: (payTo)
 * Parameter color: each player's color
 * Parameter coins: the number of coins (dirhams) each player has
 * Parameter remainingRugNumber: the remaining number of rugs each player has
 * Parameter out: whether each player is in or out of the game
 * Parameter ai: whether the player is an AI player or not (normal difficulty level)
 * Parameter hardAI: whether the player is an AI player or not (increased difficulty level)
 * @author Xinyang Li (u7760022), Tashia Tamara (u7754676)
 */
public class Player {
    public char color;
    public int coins = 30;
    public int remainingRugNumber = 15;
    public boolean out = false;
    public boolean ai = false;
    public boolean hardAI = false;

    /**
     * Constructs a new Player object
     * @param color the color of the player
     * @param ai whether the player is an AI player or not
     * @param hardAI whether the player is a hardAI player or not
     */
    public Player(char color, boolean ai, boolean hardAI) {
        this.color = color;
        this.ai = ai;
        this.hardAI = hardAI;
    }

    /**
     * Constructs a new Player object using the string representation of the object
     * @param playerString The string representation of the Player object
     */
    public Player(String playerString) {
        color = playerString.charAt(1);
        coins = Integer.parseInt(playerString, 2, 5, 10);
        remainingRugNumber = Integer.parseInt(playerString, 5, 7, 10);
        out = playerString.charAt(7) == 'o';
    }

    /**
     * Checks if the player is out of or in the game
     * @return Player string with "o" if the player is out of the game
     *         Player string with "i" if the player is still in the game
     */
    @Override
    public String toString() {
        String s = out ? "o" : "i";
        return String.format("P%c%03d%02d", color, coins, remainingRugNumber) + s;
    }

    /**
     * Pays coins or dirhams to another player when the current player steps on another player's rug
     * @param other the player receiving payment from the current player
     * @param coins the amount of coins or dirhams to be paid
     */
    public void payTo(Player other, int coins) {
        if (this.coins < coins) {
            this.out = true;
            other.coins += this.coins;
            this.coins = 0;
        } else {
            this.coins -= coins;
            other.coins += coins;
        }
    }
}

