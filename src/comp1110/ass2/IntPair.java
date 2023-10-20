package comp1110.ass2;

/**
 * The class IntPair defines a pair of integers used to determine positions on the board.
 * @author Xinyang Li (u7760022)
 */
public class IntPair {
    /**
     * column
     */
    public final int x;
    /**
     * row
     */
    public final int y;

    /**
     * @param x stand for the positions x
     * @param y stand for the positions y
     */
    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}