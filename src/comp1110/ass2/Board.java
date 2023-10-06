package comp1110.ass2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Board {
    public final int WIDTH = 7;
    public final int HEIGHT = 7;

    public Tile[] tiles;
    public ArrayList<Rug> rugs;

    public Board() {
        int n = this.WIDTH * this.HEIGHT;
        this.tiles = new Tile[n];
        this.rugs = new ArrayList<Rug>();
        for (int k = 0; k < n; k++) {
            int x = k / this.HEIGHT;
            int y = k % this.HEIGHT;
            this.tiles[k] = new Tile(x, y);
        }
    }

    public Board(String boardString) {
        int n = this.WIDTH * this.HEIGHT;
        this.tiles = new Tile[n];
        this.rugs = new ArrayList<Rug>();

        // key: color + id
        // value: positions
        Map<String, String> map = new HashMap<>();

        for (int k = 0; k < n; k++) {
            // get position
            int x = k / this.HEIGHT;
            int y = k % this.HEIGHT;

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
            rugs.add(rug);

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
        if (pos == null || pos.x < 0 || pos.x >= this.WIDTH || pos.y < 0 || pos.y >= this.HEIGHT) return null;
        return this.tiles[pos.x * this.HEIGHT + pos.y];
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
        if ("cyrp".indexOf(rug.color) < 0)return false;
        // rug position is invalid
        for (IntPair position : rug.positions)
            if (position.x < 0 || position.x >= this.WIDTH ||
                    position.y < 0 || position.y >= this.HEIGHT)
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
