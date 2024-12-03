import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Flaky;
import io.qameta.allure.Issue;
import io.qameta.allure.Issues;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.example.User;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@Epic("Epic")
@Feature("SimpleJUnit")
@Owner("Gennadii Chursov")
@Link(value = "value", name = "name", url = "https://bonigarcia.dev/selenium-webdriver-java/web-form.html")
class SimpleJUnitTests {
    @Test
    @DisplayName("Check simple Junit test")
    @Story("Simple")
    void simpleJUnitTest() {
        // Здесь размещаем код теста
        int actualSum = 2 + 2;
        int expectedSum = 4;
        // Используем assertTrue, assertFalse,  assertEquals и другие методы Assertions
        assertEquals(expectedSum, actualSum);
    }

    @Test
    @DisplayName("Check disabled Junit test")
    @Story("Disabled")
    @Issues({@Issue("Jira-123"), @Issue("Jira-234")})
    @Severity(SeverityLevel.BLOCKER)
    @TmsLink("TmsLink")
    @Flaky
    @Disabled //тест не будет запущен,  в отчет попадет как ignored
    void disabledTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        assertEquals(expectedSum, actualSum);
        System.out.println("Test");
    }

    @Test
    @DisplayName("Сложение двух чисел")
    void titleTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        assertEquals(expectedSum, actualSum);
    }

    @Test
    @Tag("smoke")
    void tagTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        assertEquals(expectedSum, actualSum, "Суммы должны быть разными");
    }

    @Test
    @Tags({@Tag("defect"), @Tag("smoke")})
    @Timeout(value = 2)
    void timeoutTest() throws InterruptedException {
        Thread.sleep(2000);
        int actualSum = 2 + 2;
        int expectedSum = 4;
        assertEquals(expectedSum, actualSum);
    }

    @RepeatedTest(value = 3, name = "{displayName} - повторение {currentRepetition} из {totalRepetitions}")
    @DisplayName("Сложение двух чисел")
    void repeatedTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        assertEquals(expectedSum, actualSum);
    }

    @Test
    void assertTrueFalseTest() {
        int actualSum = 2 + 2;
        int expectedSum = 4;
        assertTrue(expectedSum == actualSum);
        assertFalse(expectedSum != actualSum);
    }

    @Test
    void exceptionTest() {
        String test = null;
        Exception thrown = assertThrows(NullPointerException.class, () -> test.length());

        Assertions.assertEquals("Cannot invoke \"String.length()\" because \"test\" is null", thrown.getMessage());
    }

    @Test
    void assertsAllTest() {
        User user = new User("John", "Doe", 30);
        assertAll(
                () -> assertEquals("John", user.getFirstName(), "Неправильное имя"),
                () -> assertEquals("Doe", user.getLastName(), "Неправильная фамилия"),
                () -> assertEquals(30, user.getAge(), "Неправильный возраст")
        );
    }

    @Test
    void AssertAllSeparate() {
        User user = new User("John", "Doe", 30);
        assertEquals("John1", user.getFirstName(), "Неправильное имя");
        assertEquals("Doe2", user.getLastName(), "Неправильная фамилия");
        assertEquals(31, user.getAge(),  "Неправильный возраст");
    }

    @Test
    public void first() throws Exception{
        System.out.println("FirstParallelUnitTest first() start => " + Thread.currentThread().getName());
        Thread.sleep(500);
        System.out.println("FirstParallelUnitTest first() end => " + Thread.currentThread().getName());
    }

    @Test
    public void second() throws Exception{
        System.out.println("FirstParallelUnitTest second() start => " + Thread.currentThread().getName());
        Thread.sleep(500);
        System.out.println("FirstParallelUnitTest second() end => " + Thread.currentThread().getName());
    }
}
