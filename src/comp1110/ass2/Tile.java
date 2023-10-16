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
    public boolean mark = false;

    public Tile(int x, int y) {
        this.position = new IntPair(x, y);
    }

    public char getColor() {
        if (this.rug == null) return ' ';
        return this.rug.color;
    }

    /**
     * @param player the player who is playing now
     * @return ture if this player need to pay money;
     * false if this player doesn't need to pay
     */
    public boolean isNeedToPay(Player player) {
        return true;
    }
}




