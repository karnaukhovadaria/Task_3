import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverFactory {

    public static WebDriver getWebDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                return new ChromeDriver();

            case "yandex":
                String yandexBinary = System.getenv("YANDEX_BROWSER_BIN");
                if (yandexBinary == null || yandexBinary.isEmpty()) {
                    yandexBinary = System.getProperty("yandex.browser.bin");
                }
                if (yandexBinary == null || yandexBinary.isEmpty()) {
                    throw new IllegalStateException(
                            "Не задан путь к Yandex Browser. " +
                                    "Установите переменную окружения YANDEX_BROWSER_BIN " +
                                    "или системное свойство -Dyandex.browser.bin=...");
                }

                WebDriverManager.chromedriver().setup();

                ChromeOptions options = new ChromeOptions();
                options.setBinary(yandexBinary);
                return new ChromeDriver(options);

            default:
                throw new IllegalArgumentException("Неизвестный браузер: " + browser);
        }
    }

    public static void driverQuit(WebDriver webDriver) {
        if (webDriver != null) {
            webDriver.quit();
        }
    }
}