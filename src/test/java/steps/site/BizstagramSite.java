package steps.site;

import org.openqa.selenium.WebDriver;
import steps.driver.WebDriverFactory;

public class BizstagramSite {

	private final WebDriver driver = WebDriverFactory.getDefaultDriver();

	/**
	 * テスト時のURL。docker-compose時とURLは異なるため要注意。
	 * @return test base url
	 */
	private String baseUrl() {
		return "http://localhost:8082/";
	}

	public WebDriver getDriver() {
		return driver;
	}

	public void visit(String path) {
		driver.get(baseUrl() + path);
	}

}
