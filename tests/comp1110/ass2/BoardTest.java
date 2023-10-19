package comp1110.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.jupiter.api.Assertions.*;
class BoardTest {
    private int subId;

    @Test
    void testBoard() {
        try {
            BufferedReader fr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("testdata/is_rug_valid_valid.txt"))));
            String line;
            Pattern pattern = Pattern.compile("B(.{147})");  // Use capturing group to exclude 'B'
            Random r = new Random();
            int startPoint = r.nextInt(1000);
            int count = startPoint;
            while ((line = fr.readLine()) != null && count < startPoint + 50) {
                    Matcher matcher = pattern.matcher(line);
                    while (matcher.find()) {
                        String extractedString = matcher.group(1);  // remove B
                        
                        Board board = new Board(extractedString);
                        for (int i=0;i<49;i++) {
                            char presentColor=extractedString.charAt(3*i);
                            if(presentColor=='n'){
                                assertNull(board.tiles[i].rug);
                            }
                            else {
                                char subColor=extractedString.charAt(i*3);
                                int subId=Integer.parseInt(extractedString.substring(i*3+1,i*3+3));
                                assertEquals(presentColor,subColor);
                                assertEquals(board.tiles[i].rug.id,subId);
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

