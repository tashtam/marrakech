## Code Review

Reviewed by: Tashia Tamara, u7754676

Reviewing code written by: Jiangbei Zhang, u7759982

Component:

```java
class Marrakech {
    void calculateColoredTiles(
            IntPair presentPosition,
            ArrayList<Tile> connectedTiles,
            ArrayList<Tile> visitedTiles,
            char tileColor
    ) {
        // four directions (up, down, left, right)
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        for (int direction = 0; direction < 4; direction++) {
            int newX = presentPosition.x + dx[direction];
            int newY = presentPosition.y + dy[direction];

            // get tile
            IntPair pos = new IntPair(newX, newY);
            Tile adjacentTile = this.board.getTile(pos);

            // out of board
            if (adjacentTile == null) continue;

            // visited
            if (visitedTiles.contains(adjacentTile)) continue;
            visitedTiles.add(adjacentTile);

            // create a new object adjacentRug
            Rug adjacentRug = adjacentTile.rug;
            if (adjacentRug != null && adjacentRug.color == tileColor && !connectedTiles.contains(adjacentTile)) {
                // add new
                connectedTiles.add(adjacentTile);
                calculateColoredTiles(pos, connectedTiles, visitedTiles, tileColor);
            }
        }
    }
}
```

### Comments

1. What are the best features of this code?

int[] dx = {0, 0, -1, 1};
int[] dy = {-1, 1, 0, 0};

        for (int direction = 0; direction < 4; direction++) {
            int newX = presentPosition.x + dx[direction];
            int newY = presentPosition.y + dy[direction];

I think if I were to write this code, I would use four separate loops for each direction. However, upon reviewing this code snippet, I think that this method is indeed better and much more efficient.

2. Is the code well-documented?

I think the code is quite well documented, but I think I would also appreciate it if the comments were written with more words for better clarity. For example, "out of board" could be "Check if the tile is on the board".
I also think the code needs annotation preceding it, also for better clarity and for the sake of uniformity.

3. Is the program decomposition (class and method structure) appropriate?

Yes, I think the program decomposition is already appropriate.

4. Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?

Yes, it does. I think the methods and variables are already properly named. The style is also consistent throughout.

5. If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.

This code in particular has passed the unit test, so I think there are no errors in it.