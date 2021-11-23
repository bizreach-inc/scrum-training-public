package steps;

import com.scrum.training.entity.Image;
import com.scrum.training.repository.ImageRepository;
import cucumber.api.java.After;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import steps.site.BizstagramSite;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.io.FileUtils.copyFile;

@CucumberStepsDefinition
public class ShowImagesSteps {

	private final BizstagramSite site = new BizstagramSite();
	private final WebDriver driver = site.getDriver();
	@Autowired
	private ImageRepository repository;

	@After
	public void teardown() {
		if (driver != null) {
			driver.close();
			driver.quit();
		}
	}

	@Given("^画像を投稿している$")
	public void 画像を投稿している() {
		site.visit("upload");
		driver.findElement(By.id("file"))
				.sendKeys(Paths.get("src/test/resources/150x150.png").toAbsolutePath().toString());
		driver.findElement(By.id("upload")).submit();
	}

	@When("^トップ画面に遷移する$")
	public void トップ画面に遷移する() {
		site.visit("");
	}

	@Then("^投稿した画像が表示されている$")
	public void 投稿した画像が表示されている() {
		List<WebElement> elements = driver.findElements(By.tagName("img"));
		List<String> names =
				elements.stream().map(element -> element.getAttribute("name")).collect(Collectors.toList());
		List<Image> viewImages = repository.findAll().stream()
				.filter(img -> names.stream().anyMatch(name -> img.getId().toString().equals(name)))
				.collect(Collectors.toList());
		Assertions.assertThat(viewImages.size()).isEqualTo(elements.size());
	}
}
