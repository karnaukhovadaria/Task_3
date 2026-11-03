package pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static api.Constants.MAIN_PAGE;

public class MainPage {
    private final WebDriver webDriver;
    private final WebDriverWait wait;

    public MainPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        this.wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    //Локаторы кнопок на Главной странице
    //кнопка Личный кабинет
    private final By mainPageAccountButton = By.xpath("//p[contains(text(),'Личный Кабинет')]");
    //кнопка Войти в аккаунт
    private final By mainPageAuthButton = By.xpath("//button[text()='Войти в аккаунт']");
    //кнопка Оформить заказ
    private final By mainPageCreateOrder = By.xpath("//button[@class='button_button__33qZ0 button_button_type_primary__1O7Bx button_button_size_large__G21Vg']");
    //кнопка Булки
    private final By mainPageBunsButton = By.xpath("//span[contains(text(),'Булки')]");
    //кнопка Соусы
    private final By mainPageSaucesButton = By.xpath("//span[contains(text(),'Соусы')]");
    //кнопка Начинки
    private final By mainPageToppingsButton = By.xpath("//span[contains(text(),'Начинки')]");
    //локатор меню Булки
    private final By mainPageBunsMenu = By.xpath("//h2[contains(text(),'Булки')]");
    //локатор меню Соусы
    private final By mainPageSaucesMenu = By.xpath("//h2[contains(text(),'Соусы')]");
    //локатор меню Начинки
    private final By mainPageToppingsMenu = By.xpath("//h2[contains(text(),'Начинки')]");
    //локатор меню Соберите булки
    private final By mainPageTitle = By.xpath("//h1[text()='Соберите бургер']");
    //локатор всего конструктора бургера
    private final By mainPageConstructor = By.xpath("//main//section[contains(@class, 'ingredients')]");

    private static final String ACTIVE_TAB_CLASS = "tab_tab_type_current";

    @Step("Открыть главную страницу")
    public MainPage openMainPage() {
        webDriver.get(MAIN_PAGE);
        return this;
    }

    @Step("Загрузка главной страницы")
    public MainPage waitForMainPageLoaded() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(mainPageTitle));
        return this;
    }

    @Step("Клик на кнопку войти")
    public AuthPage loginClick() {
        wait.until(ExpectedConditions.elementToBeClickable(mainPageAuthButton)).click();
        return new AuthPage(webDriver);
    }

    @Step("Клик на кнопку Личный кабинет")
    public AccountPage accountClick() {
        wait.until(ExpectedConditions.elementToBeClickable(mainPageAccountButton)).click();
        return new AccountPage(webDriver);
    }

    @Step("Проверка наличия конструктора бургера")
    public boolean constructorIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainPageConstructor));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка наличия кнопки Войти в аккаунт")
    public boolean authButtonIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainPageAuthButton));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Проверка наличия кнопки Оформить заказ")
    public boolean createOrderButtonIsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(mainPageCreateOrder));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Step("Клик на раздел Булки")
    public MainPage bunsClick() {
        wait.until(ExpectedConditions.elementToBeClickable(mainPageBunsButton)).click();
        return this;
    }

    @Step("Проверка, что раздел Булки активен (скролл сработал)")
    public boolean bunsMenuIsDisplayed() {
        return isTabActive(mainPageBunsMenu, mainPageBunsButton);
    }

    @Step("Клик на раздел Соусы")
    public MainPage saucesClick() {
        wait.until(ExpectedConditions.elementToBeClickable(mainPageSaucesButton)).click();
        return this;
    }

    @Step("Проверка, что раздел Соусы активен (скролл сработал)")
    public boolean saucesMenuIsDisplayed() {
        return isTabActive(mainPageSaucesMenu, mainPageSaucesButton);
    }

    @Step("Клик на раздел Начинки")
    public MainPage toppingsClick() {
        wait.until(ExpectedConditions.elementToBeClickable(mainPageToppingsButton)).click();
        return this;
    }

    @Step("Проверка, что раздел Начинки активен (скролл сработал)")
    public boolean toppingsMenuIsDisplayed() {
        return isTabActive(mainPageToppingsMenu, mainPageToppingsButton);
    }

    private boolean isTabActive(By sectionTitle, By tabButton) {
        try {

            WebElement title = wait.until(
                    ExpectedConditions.presenceOfElementLocated(sectionTitle));
            ((JavascriptExecutor) webDriver)
                    .executeScript("arguments[0].scrollIntoView({block: 'center'});", title);


            wait.until(ExpectedConditions.attributeContains(
                    tabButton, "class", ACTIVE_TAB_CLASS));


            return webDriver.findElement(tabButton).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}