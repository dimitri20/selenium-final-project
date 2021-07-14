package CustomClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebElementActions {
    WebDriver driver;
    JavascriptExecutor js;

    public WebElementActions(WebDriver _driver){
        driver = _driver;
        js = (JavascriptExecutor) driver;
    }

    public void click(By locator){
        new WebDriverWait(driver, 5).until(ExpectedConditions.elementToBeClickable(locator));
        try {
            driver.findElement(locator).click();
        }
        catch (NoSuchElementException e){
            try {
                js.executeScript(
                    "arguments[0].click()",
                    driver.findElement(locator)
                );
            }
            catch (NoSuchElementException e1){
                Actions action = new Actions(driver);
                action.moveToElement(driver.findElement(locator)).click().perform();
            }

        }
    }
}
