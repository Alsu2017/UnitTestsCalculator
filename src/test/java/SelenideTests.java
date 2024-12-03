import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.selenide.HomePage;
import pages.selenide.WebFormPage;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.Condition.attributeMatching;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.title;
import static com.codeborne.selenide.WebDriverRunner.url;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("selenide")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SelenideTests {

    @BeforeAll
    void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
        options.addArguments("--no-sandbox"); // Bypass OS security model
        options.addArguments("--headless"); // without browser interface
        Configuration.browserCapabilities = options;
    }

    @Test
    void successfulLoginTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/login-form.html");

        SelenideElement subTitle = $(By.className("display-6"));
        WebElement loginInput = $("#username");
        WebElement passwordInput = $("#password");
        WebElement submitButton = $(By.xpath("//button[@type='submit']"));

        loginInput.sendKeys("user");
        passwordInput.sendKeys("user");
        String textBeforeClick = subTitle.getText();
        submitButton.click();

        assertThat(textBeforeClick).isEqualTo("Login form");
        subTitle.shouldHave(text("Login form"));
        WebElement successMessage = $("#success");
        assertThat(successMessage.isDisplayed()).isTrue();
    }

    @Test
    void openSiteTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/");
        assertEquals("Hands-On Selenium WebDriver with Java", title());
    }

    @Test
    void openForm() {
        open("https://bonigarcia.dev/selenium-webdriver-java/");
        WebElement webFormButton = $(By.xpath("//div[@class = 'card-body']")).find(By.xpath(".//a[contains(@class, 'btn')]"));
        webFormButton.click();
        SelenideElement actualH1 = $(By.xpath("//h1[@class='display-6']"));
        actualH1.shouldHave(text("Web form"));
    }

    @Test
    void loadingImagesDefaultWaitTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        $("#compass").shouldHave(attributeMatching("src", ".*compass.*"));
    }

    @Test
    void loadingImagesWithUpdatedTimeoutWaitTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");
        Configuration.timeout = 10_000;
        $("#landscape").shouldHave(attributeMatching("src", ".*landscape.*"));
    }

    @Test
    void loadingImagesWithExplicitTimeoutWaitTest() {
        open("https://bonigarcia.dev/selenium-webdriver-java/loading-images.html");

        ElementsCollection images = $$("img").filter(visible);
        images.shouldHave(size(4), Duration.ofSeconds(10));
    }

    @Test
    void pageObjectTest() {
        HomePage homePage = new HomePage();
        homePage.open();
        WebFormPage webFormPage = homePage.openWebForm();
        webFormPage.submit();

        Assertions.assertThat(url()).contains("https://bonigarcia.dev/selenium-webdriver-java/submitted-form.html");
    }
}
