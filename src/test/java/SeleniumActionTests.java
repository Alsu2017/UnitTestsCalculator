import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.Constants.BASE_URL;
import static org.example.Constants.WEBFORM_URL;
import static steps.WebFormSteps.openWebFormPage;
import static steps.WebFormSteps.sendInput;

public class SeleniumActionTests {
    WebDriver driver;
    @BeforeEach
    void start() {
        driver = new ChromeDriver();
        driver.get(WEBFORM_URL);
    }

    @AfterEach
    void quit() {
        driver.quit();
    }

    @Test
    void webFormOpenTest() throws InterruptedException {
        //act
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement webFormButton = driver.findElement(By.xpath("//a[@href = 'web-form.html']"));
        Thread.sleep(3000);
        webFormButton.click();

        WebElement title = driver.findElement(By.className("display-6"));

        //assert
        Assertions.assertEquals("https://bonigarcia.dev/selenium-webdriver-java/web-form.html", driver.getCurrentUrl());
        Assertions.assertEquals("Web form", title.getText());
    }

    @Test
    void textInputTest() throws InterruptedException {
//        driver.get(WEBFORM_URL);
//        driver.manage().window().fullscreen();
        WebElement InputCSssById = driver.findElement(By.cssSelector("#my-text-id"));
//        Кликать не обязательно
//        InputCSssById.click();
        InputCSssById.sendKeys("Alsu");
        Assertions.assertEquals("Alsu", InputCSssById.getAttribute("value"));
        Thread.sleep(3000);
    }


    @Test
    void textInputClearTest() throws InterruptedException {
//        Тут используем конструктор
//        openWebFormPage(driver);
        sendInput(driver, "Alsu");

        WebElement InputCSssById = driver.findElement(By.cssSelector("#my-text-id"));
        InputCSssById.clear();
        Assertions.assertEquals("", InputCSssById.getAttribute("value"));
        Thread.sleep(3000);
    }

    @Test
    void basicTests() throws InterruptedException {
        //click
    //    driver.get(WEBFORM_URL);
        driver.manage().window().fullscreen();
        WebElement checkboxInputById = driver.findElement(By.id("my-check-2"));
        checkboxInputById.click();
        Thread.sleep(2000);

        //sendKeys
        WebElement textInputByName = driver.findElement(By.cssSelector("[name='my-text']"));
        textInputByName.sendKeys("AlsuPassword");
        Thread.sleep(2000);

        //clear
        textInputByName.clear();
        Thread.sleep(2000);

        //submit by click
        WebElement submitClick = driver.findElement(By.cssSelector("button[type='submit']"));
        submitClick.click();
        Thread.sleep(2000);

        driver.get(WEBFORM_URL);
        Thread.sleep(2000);

        //submit by submit
        WebElement submitSubmit = driver.findElement(By.cssSelector("#my-text-id"));
        submitSubmit.submit();
        Assertions.assertEquals("Form submitted", driver.findElement(By.className("display-6")).getText());
    }

    @Test
    void selectFrom() throws InterruptedException {
        driver.manage().window().fullscreen();
        //select by id
        WebElement dropdownSelectMenu = driver.findElement(By.cssSelector("select[name='my-select']"));
        Select select = new Select(dropdownSelectMenu);

        //select by id
        Thread.sleep(2000);
        select.selectByIndex(1);
        select.selectByIndex(0);

        //select by value
        Thread.sleep(2000);
        select.selectByValue("3");

        //select by text
        Thread.sleep(2000);
        select.selectByVisibleText("Two");

        //first selected option
        Assertions.assertTrue(select.getFirstSelectedOption().isSelected());
        Assertions.assertEquals("Two", select.getFirstSelectedOption().getText());

        //get all selected options  тут тока один выведется, тот что выбран если несколько выбрано
        List<WebElement> allselOptions = select.getAllSelectedOptions();
        for (WebElement allSelectedOptions: allselOptions) {
            System.out.println("Возможные варианты: "+allSelectedOptions.getText());
        }
        Thread.sleep(3000);

        //get all options   тут все выведятся
        List<WebElement> options = select.getOptions();
        for (WebElement option: options) {
            System.out.println("Возможные варианты: "+option.getText());
        }
        Thread.sleep(3000);

        //deselecting
        if (select.isMultiple()) {
            select.deselectByIndex(1);
            select.deselectByValue("1");
            select.deselectByVisibleText("One");
            select.deselectAll();
        } else {
            System.out.println("You may only deselect all options of a multi-select");
        }
        Thread.sleep(3000);
    }

    @Test
    void getInfoTests() throws InterruptedException {
        //get isDisplayed
        //get text
        driver.get(BASE_URL);
        driver.manage().window().fullscreen();
        WebElement webFormPage = driver.findElement(By.xpath("//a[@href = 'web-form.html']"));
        Assertions.assertEquals("Web form", webFormPage.getText());
        Assertions.assertTrue(webFormPage.isDisplayed());
        webFormPage.click();
        Thread.sleep(3000);



        //get isEnabled
        WebElement isEnableMethod = driver.findElement(By.name("my-disabled"));
        Assertions.assertFalse(isEnableMethod.isEnabled());

        //check exception
        Assertions.assertThrows(ElementNotInteractableException.class, () -> isEnableMethod.sendKeys("test"));


        //get tag name
        Assertions.assertEquals("input", isEnableMethod.getTagName());

        //get rect
        Rectangle rec = isEnableMethod.getRect();
        System.out.println("Вывод параметров: " + rec.getHeight() + ", " + rec.getX());

        //get css values Вывод стилей
        System.out.println(isEnableMethod.getCssValue("background-clip"));


        //get attribute
        WebElement webForAttribute = driver.findElement(By.className("form-select"));
        Assertions.assertEquals("my-select", webForAttribute.getAttribute("name"));
        System.out.println(webForAttribute);
    }

    @Test
    void fileUploadTest() throws IOException, InterruptedException {
        String filePath = "src/main/resources/text.txt";

        // Чтение содержимого файла в виде строки
        String content = new String(Files.readAllBytes(Paths.get(filePath)));

        // Используйте содержимое файла в вашем коде, например, вывод на экран
        System.out.println("Содержимое файла: " + content);

        // Получаем URL ресурса
        URL url = SeleniumActionTests.class.getClassLoader().getResource("text.txt");

        String absolutePath = null;
        if (url != null) {
            // Получаем абсолютный путь к файлу
            absolutePath = new File(url.getPath()).getAbsolutePath();
            System.out.println("Абсолютный путь к файлу: " + absolutePath);
        } else {
            System.out.println("Ресурс не найден.");
        }
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/web-form.html");
        WebElement fileUpload = driver.findElement(By.name("my-file"));
        fileUpload.sendKeys(absolutePath);
        Thread.sleep(5000);
        WebElement submit = driver.findElement(By.xpath("//button[text()='Submit']"));
        submit.click();
        Thread.sleep(5000);
        assertThat(driver.getCurrentUrl()).contains("text.txt");
    }

    @Test
    void actionAPIKeyboardTests() throws InterruptedException {

        WebElement keyboard = driver.findElement(By.name("my-text"));
        keyboard.click();

        new Actions(driver)
                .keyDown(Keys.SHIFT).sendKeys("alsu_big_letter ")
                .keyUp(Keys.SHIFT).sendKeys("alsu_small_letters")
                .perform();
        Thread.sleep(2000);
        Assertions.assertEquals("ALSU_BIG_LETTER alsu_small_letters", keyboard.getAttribute("value"));

        WebElement password = driver.findElement(By.cssSelector("[name='my-password']"));
//        keyboard.click();
//        Thread.sleep(2000);

        new Actions(driver)
                .sendKeys(password, "alsu")
                .perform();
        Thread.sleep(2000);
        Assertions.assertEquals("alsu", password.getAttribute("value"));
    }

    @Test
    void actionAPIMouseClickTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/dropdown-menu.html");
        driver.manage().window().fullscreen();

        //Клик левой кнопкой
        WebElement dropdown1 = driver.findElement(By.id("my-dropdown-1"));
        new Actions(driver)
                .click(dropdown1)
                .perform();
        Thread.sleep(2000);

        //Клик правой кнопкой
        WebElement dropdown2 = driver.findElement(By.id("my-dropdown-2"));
        new Actions(driver)
                .contextClick(dropdown2)
                .perform();
        Thread.sleep(2000);

        //Двойной клик
        WebElement dropdown3 = driver.findElement(By.id("my-dropdown-3"));
        new Actions(driver)
                .doubleClick(dropdown3)
                .perform();
        Thread.sleep(2000);

    }

    @Test
    void actionAPIMouseOverTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/mouse-over.html");
        driver.manage().window().fullscreen();

        //Провести мышкой поверх текста
        List<WebElement> images = driver.findElements(By.className("img-fluid"));
        for (WebElement image : images) {
            new Actions(driver)
                    .moveToElement(image)
                    .perform();
            Thread.sleep(1000);
        }
    }

    @Test
    void actionAPIDragAndDropTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/drag-and-drop.html");
        Thread.sleep(2000);

        WebElement draggable = driver.findElement(By.id("draggable"));
        WebElement droppable = driver.findElement(By.id("target"));
        new Actions(driver)
                .dragAndDrop(draggable, droppable)
                .perform();
        Thread.sleep(2000);
    }

    @Test
    void actionAPIScrollTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/long-page.html");
        Thread.sleep(2000);

        WebElement footerLink = driver.findElement(By.className("text-muted"));
        new Actions(driver)
                .scrollToElement(footerLink)
                .perform();
        Thread.sleep(2000);
    }

    @Test
    void navigationTests() throws InterruptedException {
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/navigation1.html");
        Thread.sleep(2000);

        WebElement page1 = driver.findElement(By.xpath("//a[@href ='navigation1.html']"));
        new Actions(driver)
                .click(page1)
                .perform();
        Thread.sleep(1000);

        WebElement page2 = driver.findElement(By.xpath("//a[@href ='navigation2.html']"));
        new Actions(driver)
                .click(page2)
                .perform();
        Thread.sleep(1000);

        WebElement page3 = driver.findElement(By.xpath("//a[@href ='navigation3.html']"));
        new Actions(driver)
                .click(page3)
                .perform();
        Thread.sleep(1000);
    }


}
