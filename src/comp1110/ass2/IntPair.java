package comp1110.ass2;

/**
 * The class IntPair defines a pair of integers used to determine positions on the board.
 */
public class IntPair {
    /**
     * column
     */
    int x;
    /**
     * row
     */
    int y;

    IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    public boolean equals(IntPair pos) {
        return this.x == pos.x && this.y == pos.y;
    }
}