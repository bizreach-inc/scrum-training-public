package steps;

import com.scrum.training.entity.Image;
import com.scrum.training.repository.ImageRepository;
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

public class ImageUploadStep {

    private final BizstagramSite site = new BizstagramSite();
    private final WebDriver driver = site.getDriver();

    @Autowired
    ImageRepository repository;

    @Given("^画像をフォームで選択する$")
    public void 画像をフォームで選択する() {
        site.visit("upload");
        driver.findElement(By.id("file"))
                .sendKeys(Paths.get("src/test/resources/150x150.png").toAbsolutePath().toString());
    }

    @When("^アップロードボタンを押す$")
    public void アップロードボタンを押す() {
        driver.findElement(By.id("upload")).submit();
    }

    @Then("^トップに遷移すると投稿した画像が見られる$")
    public void トップに遷移すると投稿した画像が見られる() {
        site.visit("");
        List<WebElement> elements = driver.findElements(By.tagName("img"));
        List<String> names =
                elements.stream().map(element -> element.getAttribute("name")).collect(Collectors.toList());
        List<Image> viewImages = repository.findAll().stream()
                .filter(img -> names.stream().anyMatch(name -> img.getId().toString().equals(name)))
                .collect(Collectors.toList());
        Assertions.assertThat(viewImages.size()).isEqualTo(elements.size());
    }

}
