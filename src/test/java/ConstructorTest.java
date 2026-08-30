import PageObjects.MainPage;
import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;

import static API.Constants.MAIN_PAGE;

@ExtendWith(AllureJunit5.class)
public class ConstructorTest {

    private WebDriver webDriver;
    MainPage mainPage;
    @BeforeEach
    void setUp() {
        webDriver = WebDriverFactory.getWebDriver("chrome");
        webDriver.get(MAIN_PAGE);
        mainPage = new MainPage(webDriver);
    }
    @Test
    @Description("Переход в раздел Булки")
    void transitionToBuns() {
        mainPage.toppingsClick();
        mainPage.bunsClick();
        mainPage.bunsMenuIsDisplayed();
    }
    @Test
    @Description("Переход в раздел Соусы")
    void transitionToSauces() {
        mainPage.toppingsClick();
        mainPage.saucesClick();
        mainPage.saucesMenuIsDisplayed();
    }
    @Test
    @Description("Переход в раздел Начинки")
    void transitionToToppings() {
        mainPage.toppingsClick();
        mainPage.toppingsMenuIsDisplayed();
    }
    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

}
