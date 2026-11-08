import pageobjects.AccountPage;
import pageobjects.AuthPage;
import pageobjects.MainPage;
import pageobjects.RegistrationPage;
import steps.UserSteps;
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
import user.DataGenerator;

import static api.Constants.REG_PAGE;

@ExtendWith(AllureJunit5.class)
public class RegistrationTest {

    private WebDriver webDriver;
    MainPage mainPage;
    AuthPage authPage;
    RegistrationPage registrationPage;
    AccountPage accountPage;

    private final UserSteps userSteps = new UserSteps();
    private final User user = DataGenerator.randomUser();
    private final User userWithShortPassword = DataGenerator.userWithShortPassword();
    private boolean userCreated = false;

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
        webDriver.get(REG_PAGE);

        mainPage = new MainPage(webDriver);
        authPage = new AuthPage(webDriver);
        registrationPage = new RegistrationPage(webDriver);
        accountPage = new AccountPage(webDriver);
        registrationPage.openRegistrationPage();
    }
    @Test
    @Description("Проверка регистрации - успех")
    void userRegistrationSuccess() {
        registrationPage.registrationInput(user);
        mainPage.openMainPage();
        Assertions.assertTrue(mainPage.createOrderButtonIsDisplayed());
        userCreated = true;
    }
    @Test
    @Description("Проверка регистрации - неуспех, пароль менее 6 символов")
    void userRegistrationWithShortPassword() {
        registrationPage.registrationInput(userWithShortPassword);
        Assertions.assertTrue(registrationPage.errorMessageCheck());
    }
    @AfterEach
    void teardown() {
        WebDriverFactory.driverQuit(webDriver);
        if (userCreated) {
            userSteps
                    .userDelete(userSteps.getAccessToken(user));
        }
    }

}