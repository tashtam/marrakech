package comp1110.ass2;

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

    public Assam(String assamString) {
        //Creating the Assam object (as in an object of the complete program) using the assamString (as an argument); constructor of a java class
        //Assam is a complete class (It is its own class)
        //But when considering the whole program, Assam is the object of the complete program
        //String assamString is the value that has been passed to the Assam class
        //In other words, assamString has become an argument of the public Assam class
        char d = assamString.charAt(3); //d is the 4th character of the string
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
        // toString() is an instance method (Returning string using the information of this instance)
        // This is an instance method. We wanted this method to happen, but in order for us to have a method,
        // the method must belong to an object.
        // In other words, we have to create an object first before we create a method.
        //This is why we need the previous code snippet to create the Assam object.
        // In reality, we just need this method, but in order to create this method, the previous part
        // (The step of creating the object) is necessary.
        // Also, this is an instance method, to account for all the different times Assam has to turn.
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