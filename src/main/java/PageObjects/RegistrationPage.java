package PageObjects;

import User.User;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static API.Constants.REG_PAGE;

public class RegistrationPage {

    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public RegistrationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    //локаторы страницы Регистрация
    //инпут Имя
    private final By RegistrationPageNameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    //инпут Имейл
    private final By RegistrationPageEmailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    //инпут Пароль
    private final By RegistrationPagePasswordInput = By.xpath("//label[text()='Пароль']/following-sibling::input");
    //кнопка Зарегистрироваться
    private final By RegistrationPageRegisterButton = By.xpath("//button[text()='Зарегистрироваться']");
    //кнопка Войти
    private final By RegistrationPageAuthButton = By.xpath("//a[@class='Auth_link__1fOlj' and contains(@href, '/login')]");
    //сообщение об ошибке
    private final By RegistrationPageErrorMessage = By.xpath("//div[@class='input__container']//p[text() = 'Некорректный пароль']");


    @Step("Открытие страницы регистрации")
    public RegistrationPage openRegistrationPage() {
        webDriver.get(REG_PAGE);
        return this;
    }

    @Step("Регистрация, ввод данных и кликнуть Зарегистрироваться")
    public AuthPage registrationInput(User user) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(RegistrationPageNameInput))
                .sendKeys(user.getName());
        wait.until(ExpectedConditions.visibilityOfElementLocated(RegistrationPageEmailInput))
                .sendKeys(user.getEmail());
        wait.until(ExpectedConditions.visibilityOfElementLocated(RegistrationPagePasswordInput))
                .sendKeys(user.getPassword());
        wait.until(ExpectedConditions.elementToBeClickable(RegistrationPageRegisterButton)).click();
        return new AuthPage(webDriver);
    }

    @Step("Войти")
    public AuthPage loginRegistrationClick() {
        wait.until(ExpectedConditions.elementToBeClickable(RegistrationPageAuthButton)).click();
        return new AuthPage(webDriver);
    }

    @Step("Проверка сообщения об ошибке")
    public boolean errorMessageCheck() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(RegistrationPageErrorMessage));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}