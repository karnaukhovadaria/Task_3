package PageObjects;

import User.User;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static API.Constants.AUTH_PAGE;

public class AuthPage {

    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public AuthPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        // Исправлено: используем Duration.ofSeconds()
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    //Локаторы авторизации
    //Локатор кнопки "Войти":
    private final By authPageAuthButton = By.xpath("//div[@class='Auth_login__3hAey']//button[text()='Войти']");
    //Локатор инпута "Email":
    private final By authPageEmailInput = By.xpath(".//label[contains(text(),'Email')]/../input");
    //Локатор инпута "Пароль":
    private final By authPagePasswordInput = By.xpath(".//label[contains(text(),'Пароль')]/../input");

    @Step("Открыть страницу авторизации")
    public AuthPage openAuthPage() {
        webDriver.get(AUTH_PAGE);
        return this;
    }

    @Step("Заполнить имейл, пароль и нажать Войти")
    public MainPage loginInput(User user) {
        // Исправлено: сначала заполняем email, потом пароль
        wait.until(ExpectedConditions.visibilityOfElementLocated(authPageEmailInput))
                .sendKeys(user.getEmail());
        wait.until(ExpectedConditions.visibilityOfElementLocated(authPagePasswordInput))
                .sendKeys(user.getPassword());
        wait.until(ExpectedConditions.elementToBeClickable(authPageAuthButton)).click();
        return new MainPage(webDriver);
    }

    @Step("Проверка отображения страницы авторизации")
    public boolean isAuthPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(authPageAuthButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}