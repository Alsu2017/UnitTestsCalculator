import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.Constants.BASE_URL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.openqa.selenium.PageLoadStrategy.NONE;

public class SeleniumDriverTests {
    WebDriver driver;

    @BeforeEach
    void start() {
        driver = new ChromeDriver();

    }

    @AfterEach
    void quit() {
        driver.quit();
    }

    @Test
    void chromeTest() {
        ChromeOptions chromeOptions = new ChromeOptions();
        //chromeOptions.setPlatformName("Windows 10"); //set remote OS
        chromeOptions.setBrowserVersion("123.0.6312.86");
        chromeOptions.setPageLoadStrategy(NONE);
        chromeOptions.setAcceptInsecureCerts(true);
        chromeOptions.setPageLoadTimeout(Duration.ofSeconds(60));
        chromeOptions.setScriptTimeout(Duration.ofSeconds(30));
        chromeOptions.setImplicitWaitTimeout(Duration.ofSeconds(5));

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        String html = driver.getPageSource();
        assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
        assertThat(html).contains("Hands-On Selenium WebDriver with Java");
    }





//    WebElement iframe = driver.findElement(By.cssSelector("#modal>iframe")); //ссылка на frame
//driver.switchTo().frame(iframe); //переключение контекста на iframe
//driver.switchTo().frame("buttonframe"); //переключение контекста на iframe по id
//driver.switchTo().frame("myframe"); ///переключение контекста на iframe по name
//driver.switchTo().frame(1); ///переключение контекста на iframe по индексу
//driver.switchTo().defaultContent(); //вернуться в основной контекст

    @Test
    void iframeTest() {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/iframes.html");
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("lead")));
        driver.switchTo().frame("my-iframe");
        assertThrows(NoSuchElementException.class, () -> driver.findElement(By.className("display-6")));
        assertThat(driver.findElement(By.className("lead")).getText()).contains("Lorem ipsum dolor sit amet");
        driver.switchTo().defaultContent();
        assertThat(driver.findElement(By.className("display-6")).getText()).contains("IFrame");
    }

//    driver.findElement(By.id("my-alert")).click(); //открыть алерт
//    Alert alert = wait.until(ExpectedConditions.alertIsPresent()); //получить ссылку с ожиданием
//    Alert alert = driver.switchTo().alert(); //получить ссылку переключая контекст WebDriver
//    String text = alert.getText(); //получить текст алерта
//    alert.sendKeys("keysToSend"); //отправить текст в алерт
//    alert.accept(); //принять алерт
//    alert.dismiss(); //отклонить алерт
//    https://www.selenium.dev/documentation/webdriver/interactions/alerts/
    @Test
    void dialogBoxesTest() {
        driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dialog-boxes.html");
        driver.findElement(By.id("my-alert")).click();
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertThat(alert.getText()).isEqualTo("Hello world!");
        alert.accept();


        driver.findElement(By.id("my-confirm")).click();
        driver.switchTo().alert().dismiss();
        assertThat(driver.findElement(By.id("confirm-text")).getText()).isEqualTo("You chose: false");

        driver.findElement(By.id("my-prompt")).click();
        driver.switchTo().alert().sendKeys("Alsu");
        driver.switchTo().alert().accept();
        assertThat(driver.findElement(By.id("prompt-text")).getText()).isEqualTo("You typed: Alsu");

        driver.findElement(By.id("my-modal")).click();
        WebElement save = driver.findElement(By.xpath("//button[normalize-space() = 'Save changes']"));
        wait.until(ExpectedConditions.elementToBeClickable(save));
        save.click();
    }


    @Test
    void navigateTest() {
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.navigate().to("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        driver.navigate().back();
        assertEquals("https://bonigarcia.dev/selenium-webdriver-java/", driver.getCurrentUrl());
        driver.navigate().forward();
        driver.navigate().refresh();
        assertEquals("https://bonigarcia.dev/selenium-webdriver-java/web-form.html", driver.getCurrentUrl());
    }

    @Test
    void testNewTab() {
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        String initHandle = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        assertThat(driver.getWindowHandles()).hasSize(2);

        driver.switchTo().window(initHandle);
        driver.close();
        assertThat(driver.getWindowHandles()).hasSize(1);
    }

    @Test
    void testNewWindow() {
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        String initHandle = driver.getWindowHandle();

        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        assertThat(driver.getWindowHandles()).hasSize(2);

        driver.switchTo().window(initHandle);
        driver.close();
        assertThat(driver.getWindowHandles()).hasSize(1);
    }

    @Test
    void WindowTest() {
        driver = new ChromeDriver();
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        WebDriver.Window window = driver.manage().window();

        Point initialPosition = window.getPosition();
        Dimension initialSize = window.getSize();
        System.out.printf("Initial window: position {%s} -- size {%s}\n", initialPosition, initialSize);

        window.maximize();

        Point maximizedPosition = window.getPosition();
        Dimension maximizedSize = window.getSize();
        System.out.printf("Maximized window: position {%s} -- size {%s}\n", maximizedPosition, maximizedSize);

        assertThat(initialPosition).isNotEqualTo(maximizedPosition);
        assertThat(initialSize).isNotEqualTo(maximizedSize);
    }


}
