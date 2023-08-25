package comp1110.ass2;

/**
 * this class stands for tiles,each tile has two fields: position,rug
 * and a method isNeedToPay
 * parameter position stands for the tile's place
 * parameter rug stands for which rug is on this tile
 */
// jiangbei
public class Tile {
    IntPair position;
    Rug rug = null;

    /**
     * @param player the player who is playing now
     * @return ture if this player need to pay money;
     * false if this player doesn't need to pay
     */
    boolean isNeedToPay(Player player) {
        return true;
    }

    public Rug getRug() {
        return rug;
    }
}




