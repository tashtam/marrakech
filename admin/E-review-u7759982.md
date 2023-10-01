## Code Review

Reviewed by: Jiangbei Zhang, u7759982

Reviewing code written by: Xinyang Li, u7760022

Component: 

```java
public class Marrakech {
public Marrakech (String gameString) {
        //System.out.println(gameString);
        // current player index
        this.currentPlayerIndex = 0;
        // split game string into 3 parts
        int i = gameString.indexOf('A');
        String playerStringPart = gameString.substring(0, i);
        String assamStringPart = gameString.substring(i, i + 4);
        String boardStringPart = gameString.substring(i + 5);
        // player string
        int n = playerStringPart.length() / 8;
        this.players = new Player[n];
        for (int k = 0; k < n; k++) {
            String playerString = playerStringPart.substring(k * 8, (k + 1) * 8);
            this.players[k] = new Player(playerString);
        }
        // assam string
        this.assam = new Assam(assamStringPart);
        // board string
        this.board = new Board(boardStringPart);
    }
        boolean isPlacementValid(Rug rug){
        // invalid rug
        if(!this.board.isRugValid(rug))return false;
        Player currentPlayer=this.players[this.currentPlayerIndex];
        // must be the color of current player
        if(currentPlayer.color!=rug.color)return false;
        IntPair p0=this.assam.position;
        IntPair p1=rug.positions[0];
        IntPair p2=rug.positions[1];
        int d1=Math.abs(p1.x-p0.x)+Math.abs(p1.y-p0.y);
        int d2=Math.abs(p2.x-p0.x)+Math.abs(p2.y-p0.y);
        // exclude assam position itself
        if(d1==0||d2==0)return false;
        // one of them must near assam position
        if(d1!=1&&d2!=1)return false;
        Rug rug1=this.board.getTile(p1).rug;
        Rug rug2=this.board.getTile(p2).rug;
        // empty tile
        if(rug1==null||rug2==null)return true;
        // must not be the same rug
        return rug1!=rug2;
        }}
```

### Comments 

1. Best features: use distance between Assam and one rug piece 
        to decide whether this rug piece is adjacent to Assam or not
        too far to be placed. By this way, less for loop is needed.

2. Definitely the code is well documented, if there must be something
        to be improved, "split game string into 3 parts" can be changed 
        to "split game string into 3 parts separately are playerStringPart, 
        assamStringPart, boardStringPart"

3. Sure this program is decomposition appropriate, all method exactly implement
        related function.

4. I think the code follow Java code conventions.I mean the methods and variables
        are named properly and consistent throughout.

5. There are no errors that I can find, and it work well with the test.

