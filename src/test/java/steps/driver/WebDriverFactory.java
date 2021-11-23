package steps.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.util.CollectionUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.github.bonigarcia.wdm.DriverManagerType.CHROME;

public class WebDriverFactory {

	private static List<WebDriver> drivers = new ArrayList<>();

	public static WebDriver getDefaultDriver() {
		WebDriver driver;
		if (CollectionUtils.isEmpty(drivers)) {
//			WebDriverManager.getInstance(CHROME).setup();
			System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
			System.setProperty("webdriver.chrome.args", "--disable-logging");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--headless");
			chromeOptions.addArguments("--disable-gpu");
			chromeOptions.addArguments("--no-sandbox");
			chromeOptions.addArguments("--silent");
			chromeOptions.addArguments("--start-maximized");
			driver = new ChromeDriver(chromeOptions);
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		} else {
			driver = drivers.get(0);
		}
		return driver;
	}

	public static void resetAll() {
		for (WebDriver driver : drivers) {
			driver.close();
			driver.quit();
		}
		drivers = new ArrayList<>();
	}
}
