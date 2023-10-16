package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    public static final int RowMax = 7;
    public static final int ColumnMax = 7;

    public Tile[] tiles;

    public Board() {
        int n = ColumnMax * RowMax;
        tiles = new Tile[n];
        for (int k = 0; k < n; k++) {
            int x = k / RowMax;
            int y = k % RowMax;
            tiles[k] = new Tile(x, y);
        }
    }

    public Board(String boardString) {
        int n = ColumnMax * RowMax;
        tiles = new Tile[n];

        // key: color + id
        // value: positions
        Map<String, String> map = new HashMap<>();

        for (int k = 0; k < n; k++) {
            // get position
            int x = k / RowMax;
            int y = k % RowMax;

            // init tile
            this.tiles[k] = new Tile(x, y);

            // get color
            String rugAbbrString = boardString.substring(k * 3, (k + 1) * 3);
            char color = rugAbbrString.charAt(0);

            // skip n00
            if (color == 'n') continue;

            // set map default value
            map.putIfAbsent(rugAbbrString, "");

            // get and update positions string
            String positionsString = map.get(rugAbbrString);
            map.put(rugAbbrString, positionsString + x + y);
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // get rug string
            String rugString = key + value;
            Rug rug = new Rug(rugString);

            // place rug on tiles
            for (IntPair position : rug.positions) {
                if (position == null) continue;
                this.getTile(position).rug = rug;
            }
        }
    }

    /**
     * @param pos the give position
     * @return the tile at this position
     */
    public Tile getTile(IntPair pos) {
        if (pos == null || pos.x < 0 || pos.x >= ColumnMax || pos.y < 0 || pos.y >= RowMax) return null;
        return this.tiles[pos.x * RowMax + pos.y];
    }

    public int getPlayerRugTilesAmount(Player player) {
        if (player.out) return 0;
        int n = 0;
        for (Tile tile : this.tiles) {
            if (tile.rug != null && tile.rug.color == player.color)
                n += 1;
        }
        return n;
    }

    /**
     * @param rug the given rug
     * @return if the rug is valid to put on the board
     */
    public boolean isRugValid(Rug rug) {
        // rug color is invalid
        if ("cyrp".indexOf(rug.color) < 0) return false;
        // rug position is invalid
        for (IntPair position : rug.positions)
            if (position.x < 0 || position.x >= ColumnMax ||
                    position.y < 0 || position.y >= RowMax)
                return false;

        // rug color + id is duplicated
        for (Tile tile : this.tiles) {
            if (tile.rug == null) continue;
            if (tile.rug.id == rug.id && tile.rug.color == rug.color) return false;
        }
        return true;
    }


    @Override
    public String toString() {
        String s = "B";
        for (Tile tile : this.tiles) {
            if (tile.rug == null) s += "n00";
            else s += String.format("%c%02d", tile.rug.color, tile.rug.id);
        }
        return s;
    }
}
