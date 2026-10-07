package com.supbro.friend;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;

class FriendE2EIT {
    @Test
    void createsFriendAndFindsItAfterReload() throws Exception {
        ChromeOptions options = new ChromeOptions();
        if (Boolean.getBoolean("headless")) {
            options.addArguments("--headless=new", "--window-size=1280,900");
        }
        WebDriver driver = new ChromeDriver(options);
        try {
            var wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            String firstname = "Selenium-" + UUID.randomUUID();
            driver.get("http://localhost:4200");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstname")))
                    .sendKeys(firstname);
            driver.findElement(By.id("lastname")).sendKeys("Test");
            driver.findElement(By.id("notes")).sendKeys("Parcours E2E");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("save-friend"))).click();

            By rowSelector = By.xpath("//ul[@id='friends-list']/li[strong[contains(., '"
                    + firstname + "')]]");
            var row = wait.until(ExpectedConditions.visibilityOfElementLocated(rowSelector));
            assertThat(row.getText()).contains(firstname, "Test", "Parcours E2E");
            String id = row.getAttribute("data-friend-id");
            assertThat(id).isNotBlank();

            driver.navigate().refresh();
            var reloaded = wait.until(ExpectedConditions.visibilityOfElementLocated(rowSelector));
            assertThat(reloaded.getAttribute("data-friend-id")).isEqualTo(id);
            assertThat(reloaded.getText()).contains(firstname, "Test", "Parcours E2E");
        } catch (Exception | AssertionError failure) {
            try {
                Path directory = Path.of("target", "selenium");
                Files.createDirectories(directory);
                Files.write(directory.resolve("friend-e2e-failure.png"),
                        ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES));
            } catch (Exception screenshotFailure) {
                failure.addSuppressed(screenshotFailure);
            }
            throw failure;
        } finally {
            driver.quit();
        }
    }
}
