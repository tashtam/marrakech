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
    public char color;
    public int coins;
    public int remainingRugNumber;
    public boolean out;

    public Player(char color) {
        this.color = color;
        this.coins = 30;
        this.remainingRugNumber = 15;
        this.out = false;
    }

    public Player(String playerString) {
        this.color = playerString.charAt(1);
        this.coins = Integer.parseInt(playerString, 2, 5, 10);
        this.remainingRugNumber = Integer.parseInt(playerString, 5, 7, 10);
        this.out = playerString.charAt(7) == 'o';
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
    public void payTo(Player other, int coins) {
        System.out.println(this);
        System.out.println(other);
        if (this.coins < coins) {
            this.out = true;
            other.coins += this.coins;
            this.coins = 0;
        } else {
            this.coins -= coins;
            other.coins += coins;
        }
        System.out.println(this);
        System.out.println(other);
    }
}

