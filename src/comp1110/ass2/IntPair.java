package comp1110.ass2;

// Tashia

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

    public int getX() { return x; }

    public int getY() {
        return y;
    }

    public IntPair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String toString(){
        return "(" + this.x + "," + this.y + ")";
    }
}