import PageObjects.*;
import Steps.UserSteps;
import User.DataGenerator;
import User.User;
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

import static API.Constants.MAIN_PAGE;

@ExtendWith(AllureJunit5.class)
public class AuthenticationTest {

    private WebDriver webDriver;
    private final UserSteps userSteps = new UserSteps();
    private final User user = DataGenerator.randomUser();

    MainPage mainPage;
    AuthPage authPage;
    RegistrationPage registrationPage;
    AccountPage accountPage;
    RecoverPage recoverPage;

    @BeforeEach
    void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        webDriver = WebDriverFactory.getWebDriver("chrome");
        webDriver.get(MAIN_PAGE);

        mainPage = new MainPage(webDriver);
        authPage = new AuthPage(webDriver);
        registrationPage = new RegistrationPage(webDriver);
        accountPage = new AccountPage(webDriver);
        recoverPage = new RecoverPage(webDriver);

        userSteps.userCreate(user);

    }
    @Test
    @Description("Авторизация по кнопке Войти в аккаунт на Главной")
    void authMainPageAuthButton() {
        mainPage.loginClick();
        authPage.loginInput(user);
        mainPage.accountClick();
        Assertions.assertTrue(accountPage.profileMenuIsDisplayed());
    }
    @Test
    @Description("Авторизация через кнопку Личный кабинет")
    void authMainAccountButton() {
        mainPage.accountClick();
        authPage.loginInput(user);
        mainPage.openMainPage();
        Assertions.assertTrue(mainPage.createOrderButtonIsDisplayed());
    }
    @Test
    @Description("Авторизация через форму регистрации")
    void authRegistrationPage() {
        registrationPage.openRegistrationPage();
        registrationPage.loginRegistrationClick();
        authPage.loginInput(user);
        mainPage.accountClick();
        Assertions.assertTrue(accountPage.profileMenuIsDisplayed());
    }
    @Test
    @Description("Авторизация через форму восстановления пароля")
    void authRecoverPage() {
        recoverPage.openRecoverPage();
        recoverPage.loginRecoverPage();
        authPage.loginInput(user);
        mainPage.accountClick();
        Assertions.assertTrue(accountPage.profileMenuIsDisplayed());
    }
    @AfterEach
    void tearDown() {
        WebDriverFactory.driverQuit(webDriver);
        userSteps
                .userDelete(userSteps.getAccessToken(user));
    }

}
