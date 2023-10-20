package comp1110.ass2;

import java.util.ArrayList;

/**
 * This class defines Assam, the character players need to control
 * There are two fields: (direction, position) and two methods: (rotate, move)
 * Parameter direction: char type stands for the present direction of Assam
 * Parameter position: IntPair type stands for the position (x,y) of Assam
 * X refers to the number of column, Y refers to the number of row
 * @author Group
 */
public class Assam {
    public int degree;
    public int oldDegree;
    public IntPair position;

    /**
    * Initializes Assam's direction and position
    */
    public Assam() {
        this.degree = 0;
        this.oldDegree = 0;
        this.position = new IntPair(3, 3);
    }

    /**
    * Constructs a new Assam object using the string representation of the object
    * @param assamString The string representation of the Assam object
    */
    public Assam(String assamString) {
        // Creating the Assam object (as in an object of the complete program) using the assamString (as an argument); constructor of a java class
        // Assam is a complete class (It is its own class)
        // But when considering the whole program, Assam is the object of the complete program
        // String assamString is the value that has been passed to the Assam class
        // In other words, assamString has become an argument of the public Assam class
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

    /**
     * Converts the Assam object to a string representation
     * @return assamString
     */
    public String toString() {
        // Creating the assamString using the Assam Object
        // toString() is an instance method (Returning string using the information of this instance)
        // This is an instance method. We wanted this method to happen, but in order for us to have a method,
        // the method must belong to an object.
        // In other words, we have to create an object first before we create a method.
        // This is why we need the previous code snippet to create the Assam object.
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
     * Rotates (or not rotate) Assam clockwise or anti-clockwise by 90 degrees
     * @param degree an int number that can be: 90 (rotate clockwise by 90 degrees)
     *                                          270 (rotate anticlockwise for 90 degree)
     *                                          0 (no rotation)
     */
    public void rotate(int degree) {
        if (degree == 90 || degree == 270) {
            this.degree = (this.degree + degree) % 360;
        }
    }

    /**
     * Sets the degree of Assam
     * @param degree
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    /**
     * Checks if the degree has changed by more than 180 degrees
     * @return true if the difference in degrees is not equal to 180 degrees, false otherwise
     */
    public boolean checkDegree() {
        var diff = (oldDegree - degree + 360) % 360;
        return diff != 180;
    }

    /**
     * Calls the checkDegree method to confirm whether the degree has changed by more than 180 degrees
     * If the value of the flag variable is true, update the old degree to match the new degree
     * If the value of the flag variable is false, update the new degree to match the old degree
     * @return value of the flag variable
     */
    public boolean confirmDegree() {
        var flag = this.checkDegree();
        if (flag) oldDegree = degree;
        else degree = oldDegree;
        return flag;
    }

    /**
     * Moves Assam forward at present direction for a certain number of steps
     * Where the number of steps is equal to the result of the die roll
     * @param dieResult a random int number stands for die result from a set {1,2,2,3,3,4}
     *                  each element of the set has the same probability of appearing
     * @return an ArrayList of IntPair objects which represent the coordinates of Assam's path as he moves
     */
    public ArrayList<IntPair> move(int dieResult) {
        var path = new ArrayList<IntPair>();
        var x = position.x;
        var y = position.y;
        for (int i = 1; i <= dieResult; i++) {
            path.add(new IntPair(x, y));
            int[] positionAfterChecking = this.checkForMosaicTracks(x, y, degree);

            x = positionAfterChecking[0];
            y = positionAfterChecking[1];
            degree = positionAfterChecking[2];
            oldDegree = positionAfterChecking[2];

             /*Checking the flag value.
            If it is 1, it means that the current step has been used by checkForMosaicTracks.
            In other words, Assam stepped on a mosaic track.
            In this case, the code will skip the rest of the code ("continue") and proceed to the next iteration.
            If it is 0, it means that Assam did not step on a mosaic track and the code to move Assam will still run.
             */

            if (positionAfterChecking[3] == 1) continue;

            switch (degree) {
                case 0 -> y -= 1;
                case 90 -> x += 1;
                case 180 -> y += 1;
                case 270 -> x -= 1;
            }
        }

        position = new IntPair(x, y);
        path.add(position);
        return path;
    }

    /**
     * Checks if Assam is on a mosaic track
     * If he is on a mosaic track, determine Assam's next movement
     * @param posX column number of Assam's new position
     * @param posY row number of Assam's new position
     * @param degree Assam's new direction
     * @return an array of four integers containing Assam's new coordinates and degree and the flag value
     */
    public int[] checkForMosaicTracks(int posX, int posY, int degree) {
        int flag = 0;

        //Special corner mosaic tracks
        //Top right corner mosaic track, when Assam is facing North
        if (posX == 6 && posY == 0 && degree == 0) {
            degree = 270;
            flag = 1;
        }

        //Top right corner mosaic track, when Assam is facing East
        if (posX == 6 && posY == 0 && degree == 90) {
            degree = 180;
            flag = 1;
        }

        //Bottom left corner mosaic track, when Assam is facing West
        if (posX == 0 && posY == 6 && degree == 270) {
            degree = 0;
            flag = 1;
        }

        //Bottom left corner mosaic track, when Assam is facing South
        if (posX == 0 && posY == 6 && degree == 180) {
            degree = 90;
            flag = 1;
        }

        //Bottom edge mosaic tracks (start from the right)
        if ((posX == 6 || posX == 4 || posX == 2) && posY == 6 && degree == 180) {
            posX -= 1;
            degree = 0;
            flag = 1;
        }

        //Bottom edge mosaic tracks (start from the left)
        if ((posX == 1 || posX == 3 || posX == 5) && posY == 6 && degree == 180) {
            posX += 1;
            degree = 0;
            flag = 1;
        }

        //Right edge mosaic tracks (start from the top)
        if (posX == 6 && (posY == 1 || posY == 3 || posY == 5) && degree == 90) {
            posY += 1;
            degree = 270;
            flag = 1;
        }

        //Right edge mosaic tracks (start from the bottom)
        if (posX == 6 && (posY == 6 || posY == 4 || posY == 2) && degree == 90) {
            posY -= 1;
            degree = 270;
            flag = 1;
        }

        //Left edge mosaic tracks (start from the top)
        if (posX == 0 && (posY == 0 || posY == 2 || posY == 4) && degree == 270) {
            posY += 1;
            degree = 90;
            flag = 1;
        }

        //Left edge mosaic tracks (start from the bottom)
        if (posX == 0 && (posY == 5 || posY == 3 || posY == 1) && degree == 270) {
            posY -= 1;
            degree = 90;
            flag = 1;
        }

        //Top edge mosaic tracks (start from the left)
        if ((posX == 0 || posX == 2 || posX == 4) && posY == 0 && degree == 0) {
            posX += 1;
            degree = 180;
            flag = 1;
        }

        //Top edge mosaic tracks (start from the right)
        if ((posX == 5 || posX == 3 || posX == 1) && posY == 0 && degree == 0) {
            posX -= 1;
            degree = 180;
            flag = 1;
        }

        //If Assam is not on a mosaic track, just return the original position.

        return new int[]{posX, posY, degree, flag};
    }
}



