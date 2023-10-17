package comp1110.ass2;

/**
 * this class stands for tiles,each tile has two fields: position,rug
 * and a method isNeedToPay
 * parameter position stands for the tile's place
 * parameter rug stands for which rug is on this tile
 */
public class Tile {
    public IntPair position;
    public Rug rug = null;

    public Tile(int x, int y) {
        this.position = new IntPair(x, y);
    }

    public char getColor() {
        if (this.rug == null) return ' ';
        return this.rug.color;
    }
}




