package comp1110.ass2;

/**
 * This class defines the Rug, which refers to the different rugs players can put on the tiles around Assam
 * There are three fields: (color, id, positions)
 * Parameter color: A char (c,r,y,p) representing the color of the Rug
 *                  There are four possible chars:
 *                  c for cyan
 *                  r for red
 *                  p for purple
 * Parameter id: An int number representing the rug id (You can access a specific rug using its id)
 * Parameter positions: An IntPair array type used to store the positions of two pieces of Rug (As a rug lies across two tiles)
 * @author Group
 */
public class Rug {
    public char color;
    public int id;
    public IntPair[] positions;

    public Rug(char color, int id, IntPair[] positions) {
        this.positions = positions;
        this.color = color;
        this.id = id;
    }

    /**
     * Creates a new Rug object by parsing the string representation of the rug (rugString)
     * @param rugString
     */
    public Rug(String rugString) {
        this.color = rugString.charAt(0);
        this.id = Integer.parseInt(rugString.substring(1, 3));

        int x0 = Integer.parseInt(rugString.substring(3, 4));
        int y0 = Integer.parseInt(rugString.substring(4, 5));
        this.positions = new IntPair[2];
        this.positions[0] = new IntPair(x0, y0);

        if (rugString.length() >= 6) {
            int x1 = Integer.parseInt(rugString.substring(5, 6));
            int y1 = Integer.parseInt(rugString.substring(6, 7));
            this.positions[1] = new IntPair(x1, y1);
        }
    }

    /**
     * Checks if the second element of the positions array is null.
     * @return Rug string with only the color and the first position, if the second position is null
     *         Rug string with the color and both positions, if the second position is not null
     */
    @Override
    public String toString() {
        if (this.positions[1] == null) return "" + this.color + this.positions[0].x + this.positions[0].y;
        return "" + this.color + this.positions[0].x + this.positions[1].y + this.positions[1].x + this.positions[1].y;
    }
}
