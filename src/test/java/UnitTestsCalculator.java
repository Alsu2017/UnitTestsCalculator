import static org.junit.jupiter.api.Assertions.*;


import org.example.DeliveryCostCalculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Tags;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class UnitTestsCalculator {

    @BeforeEach
    void setUp(){
        System.out.println("Запускаю тест");
    }

    @AfterEach
    void tearDown(){
        System.out.println("Закончил тест");
    }

    @Test
    public void testCalculateDeliveryCostNormal() {
        double result = DeliveryCostCalculator.calculateDeliveryCost(15, "маленькие", false, "нормальная");
        assertEquals(400, result);
    }

    @Disabled
    public void testCalculateDeliveryCostWithFragile() {
        double result = DeliveryCostCalculator.calculateDeliveryCost(25, "большие", true, "высокая");
        assertEquals(840, result); // (300 + 200) * 1.4 = 700
    }

    @Test
    @DisplayName("Хрупкий груз на расстояние больше допустимого")
    public void testCalculateDeliveryCostTooFarForFragile() {
        try {
            DeliveryCostCalculator.calculateDeliveryCost(35, "большие", true, "высокая");
        } catch (IllegalArgumentException e) {
            assertEquals("Хрупкие грузы нельзя возить на расстояние более 30 км.", e.getMessage());
        }
     }

    @Test
    @Tags({@Tag("Минимальная стоимость"), @Tag("Fragile")})
    public void testCalculateDeliveryCostMinimum() {
        double result = DeliveryCostCalculator.calculateDeliveryCost(2, "маленькие", false, "нормальная");
        assertEquals(400, result);
    }

    @Test
    @Tag("Fragile")
    public void testCalculateDeliveryCostWithFragile1() {
        double result = DeliveryCostCalculator.calculateDeliveryCost(25, "большие", true, "высокая");
        assertEquals(840, result); // (300 + 200) * 1.4 = 700
    }

    @Test
    public void testCalculateDeliveryCostTooFarForFragile1() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            DeliveryCostCalculator.calculateDeliveryCost(35, "большие", true, "высокая");
        });
        assertEquals("Хрупкие грузы нельзя возить на расстояние более 30 км.", exception.getMessage());
    }

    @Test
    public void testCalculateDeliveryCostMinimum1() {
        double result = DeliveryCostCalculator.calculateDeliveryCost(2, "маленькие", false, "нормальная");
        assertEquals(400, result);
    }

    @Test
    public void testLoadLevelImpact() {
        double normalCost = DeliveryCostCalculator.calculateDeliveryCost(10, "маленькие", false, "нормальная");
        double highLoadCost = DeliveryCostCalculator.calculateDeliveryCost(10, "маленькие", false, "высокая");
        double veryHighLoadCost = DeliveryCostCalculator.calculateDeliveryCost(10, "маленькие", false, "очень высокая");

        assertTrue(highLoadCost > normalCost);
        assertTrue(veryHighLoadCost > highLoadCost);
    }

    @Test
    public void testSizeImpact() {
        double smallSizeCost = DeliveryCostCalculator.calculateDeliveryCost(10, "маленькие", false, "нормальная");
        double largeSizeCost = DeliveryCostCalculator.calculateDeliveryCost(10, "большие", false, "нормальная");

        assertFalse(smallSizeCost > largeSizeCost);
    }

    @Test
    public void testMinimumDeliveryCost() {
        double result1 = DeliveryCostCalculator.calculateDeliveryCost(5, "маленькие", false, "нормальная");
        double result2 = DeliveryCostCalculator.calculateDeliveryCost(1, "большие", false, "нормальная");

        assertAll("Проверка минимальной стоимости доставки",
                () -> assertEquals(400, result1),
                () -> assertEquals(400, result2)
        );
    }

    @ParameterizedTest
    @CsvSource({
            "10, маленькие, false, нормальная, 400",
            "5, маленькие, false, нормальная, 400"
    })
    public void testCalculateDeliveryCostParameterized(int distance, String size, boolean fragile, String loadLevel, double expectedCost) {
        double result = DeliveryCostCalculator.calculateDeliveryCost(distance, size, fragile, loadLevel);
        assertEquals(expectedCost, result);
    }
}