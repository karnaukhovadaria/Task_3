package PageObjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage {

    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public AccountPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    //Локаторы страницы Личный кабинет
    //кнопка Выйти
    private final By accountPageLogoutButton = By.xpath("//button[contains(text(),'Выход')]");
    //кнопка Конструктор
    private final By accountPageConstructorButton = By.xpath("//p[contains(text(),'Конструктор')]");
    //кнопка Логотип
    private final By accountPageStellarBurgersLogo = By.xpath("//div[@class='AppHeader_header__logo__2D0X2']//a//*[name()='svg']");
    //текст "В этом разделе вы можете изменить свои персональные данные"
    private final By accountPageProfileMenu = By.xpath("//p[contains(@class, 'Account_text') and contains(text(), 'В этом разделе')]");

    @Step("Проверка отображения меню пользователя")
    public boolean profileMenuIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(accountPageProfileMenu));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Клик на Конструктор")
    public void clickConstructorButton() {
        wait.until(ExpectedConditions.elementToBeClickable(accountPageConstructorButton)).click();
    }

    @Step("Клик на логотип")
    public void clickStellarBurgersLogo() {
        wait.until(ExpectedConditions.elementToBeClickable(accountPageStellarBurgersLogo)).click();
    }

    @Step("Клик на кнопку Выйти")
    public void logoutAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(accountPageLogoutButton)).click();
    }
}