import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import static org.example.Constants.WEBFORM_URL;

public class SeleniumLocatorsTests {
    WebDriver driver;
    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        driver.get(WEBFORM_URL);
    }

    @AfterEach
    void close() {
        driver.close();
    }

    @Test
    void openUrlTest() {
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", driver.getTitle());
    }

    @Test
    void openWebFormTest() {
        String xpath = "//a[@href = 'web-form.html']";
        WebElement webFormButton = driver.findElement(By.xpath(xpath));
        webFormButton.click();
        WebElement title = driver.findElement(By.xpath("//h1[@class = 'display-6']"));
        Assertions.assertEquals("Web form", title.getText());
    }

    @Test
    void baseLocatorsTestID() throws InterruptedException {
        driver.get(WEBFORM_URL);
        driver.manage().window().fullscreen();
        WebElement checkboxInputById = driver.findElement(By.id("my-check-2"));
        checkboxInputById.click();
        Thread.sleep(5000);
    }

    @Test
    void baseLocatorsTestClassName() throws InterruptedException {
        driver.get(WEBFORM_URL);
        driver.manage().window().fullscreen();
        WebElement checkboxInputByClassName = driver.findElement(By.className("form-check-input"));
        checkboxInputByClassName.click();
        Thread.sleep(5000);
    }

    @Test
    void baseLocatorsTestName() throws InterruptedException {
        driver.get(WEBFORM_URL);
        driver.manage().window().fullscreen();
        WebElement textInputInputByName = driver.findElement(By.name("my-password"));
        textInputInputByName.sendKeys("textInputByName");
        Thread.sleep(5000);
    }

    @Test
    void baseLocatorsTestTagName() throws InterruptedException {
        driver.get(WEBFORM_URL);
        driver.manage().window().fullscreen();
        WebElement textInputByTagName = driver.findElement(By.tagName("select"));
        textInputByTagName.click();
        Thread.sleep(3000);
        textInputByTagName.sendKeys("One");
        Thread.sleep(3000);
        textInputByTagName.click();
        Thread.sleep(3000);
    }

    @Test
    void baseLocatorsTestLinkText() throws InterruptedException {
        driver.get(WEBFORM_URL);
        driver.manage().window().fullscreen();
        WebElement titleByLinkText = driver.findElement(By.linkText("Boni García"));
        Actions actions = new Actions(driver);
        // Перемещаем курсор к элементу, но только переместили, не нажали
        actions.moveToElement(titleByLinkText).perform();
        Thread.sleep(3000);
        titleByLinkText.click();
        Thread.sleep(3000);
    }

    @Test
    void baseLocatorsTestPartialLinkText() throws InterruptedException {
        driver.get(WEBFORM_URL);
        // Развернуть на весь экран
        driver.manage().window().fullscreen();
        //Вставляю неполное название, с linkText так не получится
        WebElement titleByPartialLinkText = driver.findElement(By.partialLinkText("Boni"));
        // Перемещаем курсор к элементу, но только переместили, не нажали
        Actions actions = new Actions(driver);
        actions.moveToElement(titleByPartialLinkText).perform();
        Thread.sleep(3000);
        titleByPartialLinkText.click();
        Thread.sleep(3000);
        WebElement titleById = driver.findElement(By.id("divSiteTitle"));
//        Assertions.assertEquals("Boni García", titleById.getText());
        Assertions.assertEquals("BONI GARCÍA", titleById.getText());
    }

    @Test
    void cssSelectorsTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement textInputById = driver.findElement(By.cssSelector("#my-check-2"));
        textInputById.sendKeys("textInputById");
        Thread.sleep(2000);

        WebElement textInputByClass = driver.findElement(By.cssSelector(".form-check-input"));
        textInputByClass.sendKeys("textInputByClass");
        Thread.sleep(2000);

        WebElement textInputByName = driver.findElement(By.cssSelector("[name='my-check']"));
        textInputByName.sendKeys("textInputByName");
        Thread.sleep(2000);

        WebElement textInputByTagAndClass = driver.findElement(By.cssSelector("input.form-check-input"));
        textInputByTagAndClass.sendKeys("textInputByTagAndClass");
        Thread.sleep(2000);

        WebElement textInputByTagAndId = driver.findElement(By.cssSelector("input#my-check-2"));
        textInputByTagAndId.sendKeys("textInputByTagAndId");
        Thread.sleep(2000);

        WebElement textInputByTagAndAttribute = driver.findElement(By.cssSelector("input[autocomplete='off']"));
        textInputByTagAndAttribute.sendKeys("textInputByTagAndAttribute");
        Thread.sleep(2000);
    }

    @Test
    void xpathSelectorsTest() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement byTag = driver.findElement(By.xpath("//input"));
        byTag.sendKeys("byTag");
        Thread.sleep(1000);

        WebElement byAttribute = driver.findElement(By.xpath("//*[@autocomplete='off']"));
        byAttribute.sendKeys("byAttribute");
        Thread.sleep(1000);

        WebElement byText = driver.findElement(By.xpath("//h1[text()='Hands-On Selenium WebDriver with Java']"));
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", byText.getText());
        Thread.sleep(1000);

        WebElement byPartialText = driver.findElement(By.xpath("//h1[contains(text(), 'Hands-On Selenium WebDriver')]"));
        Assertions.assertEquals("Hands-On Selenium WebDriver with Java", byPartialText.getText());
        Thread.sleep(1000);

        WebElement child = driver.findElement(By.xpath("//label/input[@id='my-text-id']"));
        child.click();
        Thread.sleep(1000);

        WebElement parent = driver.findElement(By.xpath("//input[@id='my-text-id']/.."));
        Assertions.assertEquals("Text input", parent.getText());
        Thread.sleep(3000);
    }
}
