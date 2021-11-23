package steps;

import com.scrum.training.repository.ImageRepository;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import steps.driver.WebDriverFactory;

import java.io.File;
import java.io.IOException;

import static org.apache.commons.io.FileUtils.copyFile;

public class Hooks implements BeanFactoryAware {

    TransactionStatus txStatus;
    private BeanFactory beanFactory;
    private String txnManagerBeanName;
    @Autowired
    private ImageRepository imageRepository;


    @Before
    public void beforeScenario() {
        deleteAllData();
    }

    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            WebDriver defaultDriver = WebDriverFactory.getDefaultDriver();
            ChromeDriver chromeDriver = (ChromeDriver) defaultDriver;
            File source = chromeDriver.getScreenshotAs(OutputType.FILE);
            String dest = "tmp/" + scenario.getName() + ".png";
            File destination = new File(dest);
            try {
                copyFile(source, destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        WebDriverFactory.resetAll();
        deleteAllData();
    }

    @Before({ "@txn" })
    public void rollBackBeforeHook() {
        txStatus = obtainPlatformTransactionManager().getTransaction(new DefaultTransactionDefinition());
    }

    @After({ "@txn" })
    public void rollBackAfterHook() {
        obtainPlatformTransactionManager().rollback(txStatus);
    }

    PlatformTransactionManager obtainPlatformTransactionManager() {
        if (getTxnManagerBeanName() == null) {
            return beanFactory.getBean(PlatformTransactionManager.class);
        } else {
            return beanFactory.getBean(txnManagerBeanName, PlatformTransactionManager.class);
        }
    }

    public String getTxnManagerBeanName() {
        return txnManagerBeanName;
    }

    public void setTxnManagerBeanName(String txnManagerBeanName) {
        this.txnManagerBeanName = txnManagerBeanName;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    private void deleteAllData() {
        // transaction周りいじる余裕がなかったので一旦無に帰す
        imageRepository.deleteAll();
    }
}
