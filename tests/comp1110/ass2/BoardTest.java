package comp1110.ass2;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;

/**
 * this test will read the file "testdata/is_rug_valid_valid.txt" given by lecture and random access 50 lines data
 * first using board constructor construct data from this txt file into object
 * using string operation to check whether the constructor generate the right information as the string
 */
class BoardTest {
    @Test
    void testBoard() {
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("testdata/is_rug_valid_valid.txt"))));
            String line;
            Pattern pattern = Pattern.compile("B(.{147})");  // use capturing group to exclude 'B'
            Random r = new Random();
            int startPoint = r.nextInt(1000);//random pick line start point from 0 to 1000
            int count = startPoint;
            while ((line = fr.readLine()) != null && count < startPoint + 50) {//loop for 50 times
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String extractedString = matcher.group(1);  // remove B
                        Board board = new Board(extractedString);
                        for (int i=0;i<49;i++) {//if null check the rug is null
                            char presentColor=extractedString.charAt(3*i);
                            if(presentColor=='n'){
                                assertNull(board.tiles[i].rug);
                            }
                            else {//not null check the color and id and position
                                char subColor=extractedString.charAt(i*3);
                                int subId=Integer.parseInt(extractedString.substring(i*3+1,i*3+3));
                                IntPair subPosition=new IntPair(i/7,i%7);
                                assertEquals(presentColor,subColor);
                                assertEquals(board.tiles[i].rug.id,subId);
                                assertEquals(board.tiles[i].position.x,subPosition.x);
                                assertEquals(board.tiles[i].position.y,subPosition.y);
                            }
                        }
                }
                count++;
            }
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

