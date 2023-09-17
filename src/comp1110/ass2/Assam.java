package comp1110.ass2;

// jiangbei

/**
 * this class defines Assam that players need to control,there are
 * two fields named: (direction, position) and two methods: (rotate, move)
 * parameter direction: char type stands for the present direction of Assam
 * parameter position: IntPair type stands for the position(x,y) of Assam
 */
public class Assam {

    int degree;
    IntPair position;

    public int getDegree() { return degree; }

    public IntPair getPosition() { return position; }

    public Assam(String assamString) { //Creating the Assam object using the assamString; constructor of a java class
        char d = assamString.charAt(3);
        if (d == 'N') this.degree = 0;
        else if (d == 'E') this.degree = 90;
        else if (d == 'S') this.degree = 180;
        else this.degree = 270;

        // column first
        int x = assamString.charAt(1) - '0';
        // row second
        int y = assamString.charAt(2) - '0';
        this.position = new IntPair(x, y);
    }

    public String toString() { //Creating the assamString using the Assam Object
        // [Instance method (Returning string using the information of this instance)
        String assamString = "A" + this.position.x + this.position.y;
        if (this.degree == 0) assamString += "N";
        else if (this.degree == 90) assamString += "E";
        else if (this.degree == 180) assamString += "S";
        else assamString += "W";
        return assamString;
    }

    /**
     * rotate method will rotate Assam clockwise of 90 degree or anticlockwise of 90 degree
     * or not rotate
     *
     * @param degree an int number that can be 90(rotate clockwise for 90 degree)
     *               270 (rotate anticlockwise for 90 degree)
     *               0 not rotate
     */
    void rotate(int degree) {
        if (degree == 90 || degree == 270) {
            this.degree = (this.degree + degree) % 360;
        }
    }

    /**
     * this method will move Assam forward at present direction for steps of dieResult
     *
     * @param dieResult a random int number stands for die result from a set{1,2,2,3,3,4}
     *                  each element of the set has the same probability
     */
    void move(int dieResult) {
    }
}