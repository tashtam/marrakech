package comp1110.ass2;

import java.util.HashMap;
import java.util.Map;

/**
 * this class defines Board string, start with character 'B' continued with 49*3 character
 * parameters: tiles include 49 instance of tiles
 * @author Xinyang Li (u7760022)
 */
public class Board {
    public Tile[] tiles;

    /**
     * initialize the board with all tiles with no rug
     */
    public Board() {
        int n = Utils.MAP_SIZE * Utils.MAP_SIZE;
        tiles = new Tile[n];
        for (int k = 0; k < n; k++) {
            int x = k / Utils.MAP_SIZE;
            int y = k % Utils.MAP_SIZE;
            tiles[k] = new Tile(x, y);
        }
    }

    /**
     * @param boardString input the string stands for Board string started with 'B' and followed by 49*3 characters
     * this constructor will construct Board string into tiles object and place rugs on the tile
     */
    public Board(String boardString) {
        int n = Utils.MAP_SIZE * Utils.MAP_SIZE;
        tiles = new Tile[n];

        // key: color + id
        // value: positions
        Map<String, String> map = new HashMap<>();

        for (int k = 0; k < n; k++) {
            // get position
            int x = k / Utils.MAP_SIZE;
            int y = k % Utils.MAP_SIZE;

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
        if (pos == null || pos.x < 0 || pos.x >= Utils.MAP_SIZE || pos.y < 0 || pos.y >= Utils.MAP_SIZE) return null;
        return this.tiles[pos.x * Utils.MAP_SIZE + pos.y];
    }

    /**
     * get player rug score
     * every tile counts one score
     *
     * @param player stand for player information
     * @return n stand for the rug score of player
     */
    public int getPlayerRugScore(Player player) {
        if (player.out) return 0;
        int n = 0;
        for (Tile tile : this.tiles)
            if (tile.rug != null && tile.rug.color == player.color)
                n += 1;
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
            if (position.x < 0 || position.x >= Utils.MAP_SIZE ||
                    position.y < 0 || position.y >= Utils.MAP_SIZE)
                return false;

        // rug color + id is duplicated
        for (Tile tile : this.tiles) {
            if (tile.rug == null) continue;
            if (tile.rug.id == rug.id && tile.rug.color == rug.color) return false;
        }
        return true;
    }


    /**
     * @return s generate a Board string start with 'B' and follow by 49*3 characters stand for tile information
     */
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
