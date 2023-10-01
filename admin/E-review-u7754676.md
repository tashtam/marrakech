## Code Review

Reviewed by: Tashia Tamara u7754676

Reviewing code written by: Xinyang Li u7760022

Component: RugTest, isGameOver

### Comments 

```java
class RugTest {
    @Test
    void testRug() {
        for (int i = 0; i < 200; i++) {
            //Added loop to run the test 200 times to decrease the chances of edge cases/issues happening
            var rand = new Random();

            var colors = "cypr";
            var id1 = rand.nextInt(10); // Number of bound of random (0-9)
            var id2 = rand.nextInt(10); // Number of bound of random (0-9)
            var positions0x = rand.nextInt(7); // Number of bound of random (0-6)
            var positions0y = rand.nextInt(7);
            var positions1x = rand.nextInt(7);
            var positions1y = rand.nextInt(7);

            var color = colors.charAt(rand.nextInt(4));
            // Number of bound of random (0-3)
            // Each index number points to different letters in the String colors ("cypr")

            var rugString = "" + color + id1 + id2 + positions0x + positions0y + positions1x + positions1y;

            //char (like color) + int (id1, id2, etc.) = int rugString [Implicit typecasting]
            //However, Rug() needs String as its parameter
            //Therefore, we need to add "" before color in var rugString
            //String (like "") + char + int = String rugString (because String is the strongest)

            System.out.println(rugString);

            var rug = new Rug(rugString);
            Assertions.assertEquals(rug.color, color);
            Assertions.assertEquals(rug.id, id1 * 10 + id2);
            Assertions.assertEquals(rug.positions[0].x, positions0x);
            Assertions.assertEquals(rug.positions[0].y, positions0y);
            Assertions.assertEquals(rug.positions[1].x, positions1x);
            Assertions.assertEquals(rug.positions[1].y, positions1y);
        }
    }
}
```

1. What are the best features of this code?

it uses random value to test the code

2. Is the code well-documented?

yes 

3. Is the program decomposition (class and method structure) appropriate?

obviously

4. Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?

yes, like rugString means the string that indicates the whole information of a rug

5. If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.

no error


```java
class RugTest {
    boolean isGameOver() {
        //  First condition to check
        //  Checking if anyone has more than 0 rugs
        boolean everyPlayerHas0Rug = true;
        for (Player player : this.players) {
            if (!player.out && player.remainingRugNumber > 0) {
                everyPlayerHas0Rug = false;
                break;
            }
        }
        if (everyPlayerHas0Rug) return true;
        return false; //More than one player still playing, AKA game is not over
    }

}
```

1. What are the best features of this code?

it uses everyPlayerHas0Rug instead of flag, so it's good to understand its meaning.

2. Is the code well-documented?

yes

3. Is the program decomposition (class and method structure) appropriate?

obviously

4. Does it follow Java code conventions (for example, are methods and variables properly named), and is the style consistent throughout?

yes, like everyPlayerHas0Rug

5. If you suspect an error in the code, suggest a particular situation in which the program will not function correctly.

no error

