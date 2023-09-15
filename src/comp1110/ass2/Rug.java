package comp1110.ass2;

// jiangbei

/**
 * define the class of Rug,there are three fields named: color, id, positions
 * parameter color  ：A char from( c,r,y,p),representing the color of the Rug and
 * (c for cyan,r for red,y for yellow,p for purple)
 * parameter id :Int number representing the rug id, you can access rug by its id
 * parameter positions: IntPair array type which used to store positions of two pieces of Rug
 */
public class Rug {
    char color;
    int id;
    IntPair[] positions; // 2 denotes the length of the array

    public char getColor() {
        return color;
    }

    public Rug(String rugString) { //RugString has 7 chars in total
        this.color = rugString.charAt(0); // First char (index: 0)
        this.id = Integer.parseInt(rugString.substring(1, 3));
        //The line above denotes the second (index: 1) and third (index: 2) chars

        int y0 = Integer.parseInt(rugString.substring(3, 4));
        int x0 = Integer.parseInt(rugString.substring(4, 5));
        this.positions = new IntPair[2];
        this.positions[0] = new IntPair(y0, x0); //[0] denotes index 0 in array IntPair [2]

        if (rugString.length() >= 6) {
            int y1 = Integer.parseInt(rugString.substring(5, 6));
            int x1 = Integer.parseInt(rugString.substring(6, 7)); //Seventh char (index: 6), started from index: 0
            this.positions[1] = new IntPair(y1, x1); //[1] denotes index 1 in array IntPair [2]
        }
    }

    public int getId() {
        return id;
    }
}
