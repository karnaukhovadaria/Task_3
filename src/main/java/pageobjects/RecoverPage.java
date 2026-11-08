package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static api.Constants.FORGOT_PASSWORD_PAGE;

public class RecoverPage {

    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public RecoverPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        // Исправлено: используем Duration.ofSeconds()
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    //локатор кнопки Войти на странице восстановления пароля
    private final By recoverPageAuthButtonLoc = By.xpath("//a[@class='Auth_link__1fOlj']");

    @Step("Открыть страницу восстановления пароля")
    public RecoverPage openRecoverPage() {
        webDriver.get(FORGOT_PASSWORD_PAGE);
        return this;
    }

    @Step("Ожидание загрузки страницы восстановления пароля")
    public RecoverPage waitForPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(recoverPageAuthButtonLoc));
        return this;
    }

    @Step("Войти через кнопку Войти на странице восстановления пароля")
    public AuthPage loginRecoverPage() {
        wait.until(ExpectedConditions.elementToBeClickable(recoverPageAuthButtonLoc)).click();
        return new AuthPage(webDriver);
    }

    @Step("Полный переход на страницу авторизации через восстановление")
    public AuthPage goToLoginFromRecover() {
        return openRecoverPage()
                .waitForPageLoaded()
                .loginRecoverPage();
    }
}