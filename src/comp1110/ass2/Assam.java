package comp1110.ass2;

import java.util.ArrayList;

/**
 * This class defines Assam, the character players need to control
 * There are two fields named: (direction, position) and two methods: (rotate, move)
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
    * The Assam constructor takes a string assamString as input
    * It uses assamString to initialize the two fields of the class: direction and position
    * @param assamString
    */
    public Assam(String assamString) {
        char d = assamString.charAt(3);
        if (d == 'N') this.degree = 0;
        else if (d == 'E') this.degree = 90;
        else if (d == 'S') this.degree = 180;
        else this.degree = 270;

        int x = assamString.charAt(1) - '0';
        int y = assamString.charAt(2) - '0';
        this.position = new IntPair(x, y);
    }

    /**
     * Converts the Assam object to a string representation
     * @return assamString
     */
    public String toString() {
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
     * Sets the degree using the new value of the degree field
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

        boolean top = posY == 0 && degree == 0;
        boolean bottom = posY == Utils.MAP_SIZE - 1 && degree == 180;
        boolean left = posX == 0 && degree == 270;
        boolean right = posX == Utils.MAP_SIZE - 1 && degree == 90;

        if (top || bottom || left || right) {
            flag = 1;
            degree += 180;

            if (top || bottom) {
                if ((top && posX == 6) || (bottom && posX == 0)) {
                    degree += 90;
                } else if (top) {
                    if (posX % 2 == 0) posX += 1;
                    else posX -= 1;
                } else {
                    if (posX % 2 == 0) posX -= 1;
                    else posX += 1;
                }
            } else {
                if ((left && posY == 6) || (right && posY == 0)) {
                    degree -= 90;
                } else if (left) {
                    if (posY % 2 == 0) posY += 1;
                    else posY -= 1;
                } else {
                    if (posY % 2 == 0) posY -= 1;
                    else posY += 1;
                }
            }

            degree %= 360;
        }

        //If Assam is not on a mosaic track, just return the original position.
        return new int[] {posX, posY, degree, flag};
    }
}



