package comp1110.ass2;

/**
 * The class IntPair defines a pair of integers used to determine positions on the board.
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

    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}