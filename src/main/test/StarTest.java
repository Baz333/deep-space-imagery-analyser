import com.example.assignment1.Star;
import javafx.fxml.FXML;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StarTest {

    private Star star;

    @BeforeEach
    void setup() {
        star = new Star(1, 20, 10, 15, 30, 35, 5060, 4360, 2960);
    }

    @AfterEach
    void teardown() {
        star = null;
    }

    @Nested
    class GettersAndSetters {

        @Test
        void getAndSetNumber() {
            assertEquals(star.getNumber(), 1);
            star.setNumber(2000);
            assertEquals(star.getNumber(), 2000);
        }

        @Test
        void getAndSetSize() {
            assertEquals(star.getSize(), 20);
            star.setSize(150);
            assertEquals(star.getSize(), 150);
        }

        @Test
        void getAndSetMinX() {
            assertEquals(star.getMinX(), 10);
            star.setMinX(20);
            assertEquals(star.getMinX(), 20);
        }

        @Test
        void getAndSetMaxX() {
            assertEquals(star.getMaxX(), 15);
            star.setMaxX(25);
            assertEquals(star.getMaxX(), 25);
        }

        @Test
        void getAndSetMinY() {
            assertEquals(star.getMinY(), 30);
            star.setMinY(40);
            assertEquals(star.getMinY(), 40);
        }

        @Test
        void getAndSetMaxY() {
            assertEquals(star.getMaxY(), 35);
            star.setMaxY(45);
            assertEquals(star.getMaxY(), 45);
        }

        @Test
        void getAndSetRed() {
            assertEquals(star.getRed(), 5060);
            star.setRed(4460);
            assertEquals(star.getRed(), 4460);
        }

        @Test
        void getAndSetGreen() {
            assertEquals(star.getGreen(), 4360);
            star.setGreen(1960);
            assertEquals(star.getGreen(), 1960);
        }

        @Test
        void getAndSetBlue() {
            assertEquals(star.getBlue(), 2960);
            star.setBlue(820);
            assertEquals(star.getBlue(), 820);
        }

    }

}
