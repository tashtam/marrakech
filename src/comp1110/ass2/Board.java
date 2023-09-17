package comp1110.ass2;

import java.util.HashMap;
import java.util.Map;

public class Board {
    public final int WIDTH = 7;
    public final int HEIGHT = 7;

    Tile[] tiles;

    public Board(String boardString) {
        this.tiles = new Tile[this.WIDTH * this.HEIGHT];

        // key: color + id
        // value: positions
        Map<String, String> map = new HashMap<>();

        int n = boardString.length() / 3;
        for (int k = 0; k < n; k++) {
            String rugAbbrString = boardString.substring(k * 3, (k + 1) * 3);
            char color = rugAbbrString.charAt(0);

            // skip n00
            if (color == 'n') continue;

            // get position
            int col = k / this.WIDTH;
            int row = k % this.HEIGHT;

            // set map default value
            map.putIfAbsent(rugAbbrString, "");

            // get and update positions string
            String positionsString = map.get(rugAbbrString);
            map.put(rugAbbrString, positionsString + col + row);
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
                this.tiles[position.x * this.WIDTH + position.y].rug = rug;
            }
        }
    }
}
