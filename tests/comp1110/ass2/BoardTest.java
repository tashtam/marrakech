package comp1110.ass2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
class BoardTest {
    @Test
    void testBoard(){
        BufferedReader fr;
        fr = new BufferedReader(new InputStreamReader(Objects.requireNonNull(this.getClass().getResourceAsStream("testdata/is_rug_valid_valid.txt"))));
        Stream<String> testLines = fr.lines();
        for (String line : testLines.toList()) {
            String[] splitLine = line.split("@");
            // For this test, there's two arguments needed to the function
            Assertions.assertEquals(splitLine[2], String.valueOf(Marrakech.isRugValid(splitLine[0], splitLine[1])), splitLine[3]);

            }
        }

}