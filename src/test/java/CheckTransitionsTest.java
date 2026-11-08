import pageobjects.AccountPage;
import pageobjects.AuthPage;
import pageobjects.MainPage;
import pageobjects.RegistrationPage;
import steps.UserSteps;
import user.DataGenerator;
import user.User;
import io.qameta.allure.Description;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;


import static api.Constants.MAIN_PAGE;

@ExtendWith(AllureJunit5.class)
public class CheckTransitionsTest {

    private WebDriver webDriver;
    MainPage mainPage;
    AuthPage authPage;
    RegistrationPage registrationPage;
    AccountPage accountPage;
    private final UserSteps userSteps = new UserSteps();
    private final User user = DataGenerator.randomUser();

    @BeforeEach
    void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());

        String browser = System.getProperty("browser");
        if (browser == null || browser.isBlank()) {
            browser = System.getenv("BROWSER");
        }
        if (browser == null || browser.isBlank()) {
            browser = "chrome";
        }

        webDriver = WebDriverFactory.getWebDriver(browser);
        webDriver.get(MAIN_PAGE);

        mainPage = new MainPage(webDriver);
        authPage = new AuthPage(webDriver);
        registrationPage = new RegistrationPage(webDriver);
        accountPage = new AccountPage(webDriver);

        userSteps.userCreate(user);
        authPage.openAuthPage();
        authPage.loginInput(user);
    }
    @Test
    @Description("Переход по клику на кнопку Личный кабинет")
    void transitionToClickAccount() {
        mainPage.accountClick();
        Assertions.assertTrue(accountPage.profileMenuIsDisplayed());
    }
    @Test
    @Description("Переход из личного кабинета по клику на кнопку Конструктор")
    void transitionToConstructor() {
        mainPage.accountClick();
        accountPage.clickConstructorButton();;
        Assertions.assertTrue(mainPage.constructorIsDisplayed());
    }
    @Test
    @Description("Переход из личного кабинета по логотипу")
    void transitionClickStellarBurgers() {
        mainPage.accountClick();
        accountPage.clickStellarBurgersLogo();
        Assertions.assertTrue(mainPage.constructorIsDisplayed());
    }
    @Test
    @Description("Переход на авторизация после логаута в личном кабинете")
    void logoutClickExit() {
        mainPage.accountClick();
        accountPage.logoutAccount();
        mainPage.openMainPage();
        Assertions.assertTrue(mainPage.authButtonIsDisplayed());
    }
    @AfterEach
    void tearDown() {
        WebDriverFactory.driverQuit(webDriver);
        userSteps
                .userDelete(userSteps.getAccessToken(user));
    }

}