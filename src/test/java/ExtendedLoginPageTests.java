import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pages.ExtendedLoginPage;

import static org.assertj.core.api.Assertions.assertThat;

//@Epic - для обозначения крупных функциональных блоков
//@Feature - указывает на конкретную функцию или возможность, которую необходимо реализовать в продукте. Функции обычно являются частью эпиков и описывают, что именно должно быть разработано.
//@Story - описывает требования к продукту
//@Step - для обозначения шагов в тестовом сценарии
//@Attachment - прикрепленные файлы или документы
//@Description - для предоставления подробного описания задачи
//@Severity - указывает на степень серьезности проблемы или дефекта
//@Link - для создания ссылок на связанные задачи
//@Issue -  указывает на проблему или дефект
//@TmsLink - Указывает на связь с системой управления тестированием
//@Flaky - для обозначения нестабильных тестов

@Feature("Site")
@Story("LoginPage")
class ExtendedLoginPageTests {
    ExtendedLoginPage login;

    @BeforeEach
    void setup() {
        login = new ExtendedLoginPage("chrome");
    }

    @AfterEach
    void teardown() {
        login.quit();
    }

    @Test
    void testLoginSuccess() {
        login.with("user", "user");
        assertThat(login.successBoxPresent()).isTrue();
        assertThat(login.invalidCredentialsBoxPresent()).isFalse();
    }

    @Test
    void testLoginFailure() {
        login.with("test", "test");
        assertThat(login.successBoxPresent()).isFalse();
        assertThat(login.invalidCredentialsBoxPresent()).isTrue();
    }
}
