import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WebDriverFactory {

    private static WebDriver webDriver;

    public static WebDriver getWebDriver(String browser) {
        WebDriver webDriver = null;
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                webDriver = new ChromeDriver();
                break;
            case "yandex":
                System.setProperty("webdriver.chrome.driver", "C:/WebDriver/bin/yandexdriver/yandexdriver.exe");
                webDriver = new ChromeDriver();
                break;
        }
        return webDriver;
    }

    public static void driverQuit(WebDriver webDriver) {
        webDriver.quit();
    }
}
