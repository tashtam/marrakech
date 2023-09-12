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

    public int getDegree() {
        return degree;
    }

    public IntPair getPosition() {
        return position;
    }

    public Assam(String assamString) {
        char d = assamString.charAt(3);
        if (d == 'N') this.degree = 0;
        else if (d == 'E') this.degree = 90;
        else if (d == 'S') this.degree = 180;
        else this.degree = 270;
        this.position = new IntPair(
                assamString.charAt(1) - '0',
                assamString.charAt(2) - '0'
        );
    }

    /**
     * rotate method will rotate Assam clockwise of 90 degree or anticlockwise of 90 degree
     * or not rotate
     *
     * @param degree an int number that can be 90(rotate clockwise for 90 degree)
     *               -90(rotate anticlockwise for 90 degree)
     *               0 not rotate
     */
    void rotate(int degree) {
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