import PageObjects.AccountPage;
import PageObjects.AuthPage;
import PageObjects.MainPage;
import PageObjects.RegistrationPage;
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
        webDriver = WebDriverFactory.getWebDriver("chrome");
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
    @Description("Переход из личного кабинета по логотипу Ыеуддфк Игкпукы")
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
