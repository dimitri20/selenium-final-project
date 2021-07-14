import CustomClasses.WebElementActions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FinalTest {
    WebDriver driver;
    String main_page_url;
    Actions action;
    JavascriptExecutor js;
    WebDriverWait wait_5_sec;
    WebElementActions customActions;
    Random random;


//    public FinalTest(){
//
//    }
//
    @BeforeTest
    @Parameters("browser")
    public void setup(String browser) throws Exception{

        if(browser.equalsIgnoreCase("Chrome")){
            WebDriverManager.chromedriver().setup();
            System.out.println("chrome");
            driver = new ChromeDriver();
        }

        else if(browser.equalsIgnoreCase("Edge")){
            WebDriverManager.edgedriver().setup();
            driver = new EdgeDriver();
        }
        else{

            throw new Exception("Browser is not correct");
        }

        driver.manage().window().maximize();
        main_page_url = "http://tutorialsninja.com/demo/";
        action = new Actions(driver);
        js = (JavascriptExecutor) driver;
        customActions = new WebElementActions(driver);
        wait_5_sec = new WebDriverWait(driver, 5);
        random = new Random();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void maintest() {
        driver.get(main_page_url);

        //Go to 'My Account' and click on 'Register' button - TODO
        customActions.click(By.cssSelector("#top-links > ul > li.dropdown > a"));
        customActions.click(By.cssSelector("#top-links > ul > li.dropdown.open > ul > li:nth-child(1) > a"));


        //Fill personal information, choose 'Subscribe' Yes and click on 'Continue' button - TODO
        //OCSESSID=387c3b838129d978987bea571e


        driver.findElement(By.id("input-firstname")).sendKeys("John");
        driver.findElement(By.id("input-lastname")).sendKeys("Star");
        driver.findElement(By.id("input-email")).sendKeys("Johnj21@gmail.com");
        driver.findElement(By.id("input-telephone")).sendKeys("22222222");
        driver.findElement(By.id("input-password")).sendKeys("JohnTest123");
        driver.findElement(By.id("input-confirm")).sendKeys("JohnTest123");

        customActions.click(By.cssSelector("#content > form > fieldset:nth-child(3) > div > div > label:nth-child(1) > input[type=radio]"));

        customActions.click(By.cssSelector("#content > form > div > div > input[type=checkbox]:nth-child(2)"));

        customActions.click(By.cssSelector("#content > form > div > div > input.btn.btn-primary"));





        if(driver.getCurrentUrl().equals("http://tutorialsninja.com/demo/index.php?route=account/success")){
            System.out.println("user registered successfully");
        } else {
            customActions.click(By.cssSelector("#top-links > ul > li.dropdown > a"));
            customActions.click(By.cssSelector("#top-links > ul > li.dropdown.open > ul > li:nth-child(2) > a"));

            driver.findElement(By.cssSelector("#input-email")).sendKeys("Johnj21@gmail.com");
            driver.findElement(By.cssSelector("#input-password")).sendKeys("JohnTest123");
            customActions.click(By.cssSelector("#content > div > div:nth-child(2) > div > form > input"));
        }

        //Move to 'Desktops' and select 'Show all Desktops' - TODO
        action.moveToElement(driver.findElement(By.cssSelector("#menu > div.collapse.navbar-collapse.navbar-ex1-collapse > ul > li:nth-child(1) > a"))).perform();
        customActions.click(By.cssSelector("#menu > div.collapse.navbar-collapse.navbar-ex1-collapse > ul > li:nth-child(1) > div > a"));
        //Choose 'MP3 Players' item - TODO
        customActions.click(By.cssSelector("#column-left > div.list-group > a:nth-child(10)"));
        //Move to 'iPod Classic' image and check that 'iPod Classic' text is visible on mouse hover - TODO
        WebElement iPod_Classic_text = driver.findElement(By.cssSelector("#content > div:nth-child(8) > div:nth-child(1) > div > div:nth-child(2) > div.caption > h4 > a"));

        //move to image
        action.moveToElement(driver.findElement(By.cssSelector("#content > div:nth-child(8) > div:nth-child(1) > div > div.image > a"))).perform();

        if(iPod_Classic_text.isDisplayed()){
            System.out.println("iPod Classic text is visible on image hover");
        } else {
            System.out.println("iPod Classic text is not visible on image hover");
        }

        action.moveToElement(iPod_Classic_text).perform();
        if(iPod_Classic_text.isDisplayed()){
            System.out.println("iPod Classic text is visible on 'iPod Classic' text hover");
        } else {
            System.out.println("iPod Classic text is not visible on 'iPod Classic' text hover");
        }


        // Click on 'iPod Classic' link

        iPod_Classic_text.click();


        //Click on first image and move on another images before text '4 of 4' is present (check Pic1) - TODO
        driver.findElement(By.cssSelector("#content > div > div.col-sm-8 > ul.thumbnails > li:nth-child(1) > a")).click();
        //wait while image is not opened
        wait_5_sec.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > div.mfp-wrap.mfp-gallery.mfp-close-btn-in.mfp-auto-cursor.mfp-ready")));

        By count_text_locator = By.cssSelector("body > div.mfp-wrap.mfp-gallery.mfp-close-btn-in.mfp-auto-cursor.mfp-ready > div > div.mfp-content > div > figure > figcaption > div > div.mfp-counter");
        WebElement count_text = driver.findElement(count_text_locator);

        By next_locator = By.cssSelector("body > div.mfp-wrap.mfp-gallery.mfp-close-btn-in.mfp-auto-cursor.mfp-ready > div > button.mfp-arrow.mfp-arrow-right.mfp-prevent-close");
        WebElement next = driver.findElement(next_locator);

        wait_5_sec.until(ExpectedConditions.textToBe(count_text_locator, "1 of 4"));

        int count = Integer.parseInt(count_text.getText().split(" ")[0]);

        while(count != 4){
            wait_5_sec.until(ExpectedConditions.textToBe(count_text_locator, count + " of 4"));

            wait_5_sec.until(ExpectedConditions.presenceOfElementLocated(next_locator));
            next.click();

            count_text = driver.findElement(count_text_locator);
            count = Integer.parseInt(count_text.getText().split(" ")[0]);
            System.out.println("Image ==>  " + count_text.getText());

            wait_5_sec.until(ExpectedConditions.textToBe(count_text_locator, count + " of 4"));
        }

        //close images
        driver.findElement(By.cssSelector("body > div.mfp-wrap.mfp-gallery.mfp-close-btn-in.mfp-auto-cursor.mfp-ready > div > div.mfp-content > div > button")).click();


        //        - Click on 'Write a review' , fill information and submit - TODO
        driver.findElement(By.cssSelector("#content > div > div.col-sm-4 > div.rating > p > a:nth-child(7)")).click();

        driver.findElement(By.id("input-name")).sendKeys("Test Name");
        driver.findElement(By.id("input-review")).sendKeys("Test ReviewTest ReviewTest ReviewTest ReviewTest ReviewTest ReviewTest ReviewTest ReviewTest ReviewTest ReviewTest ReviewTest ReviewReviewTest ReviewTest ReviewReviewTest ReviewTest ReviewReviewTest  ");
        driver.findElement(By.xpath("//*[@id='form-review']/div[4]/div/input["+ (int)(random.nextInt(5)+1) +"]")).click();

        driver.findElement(By.id("button-review")).click();

        wait_5_sec.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#form-review > div.alert.alert-dismissible")));
        if(driver.findElement(By.cssSelector("#form-review > div.alert.alert-dismissible")).getAttribute("class").contains("alert-success")){
            System.out.println("Review submitted successfully");
        } else {
            System.out.println("Couldn't submit review");
        }

        //Click on 'Add to Cart' - TODO

        double item_price = Double.parseDouble(driver.findElement(By.cssSelector("#content > div > div.col-sm-4 > ul:nth-child(4) > li:nth-child(1) > h2")).getText().replace("$", "").trim());
        int item_count = Integer.parseInt(driver.findElement(By.id("input-quantity")).getAttribute("value"));
        System.out.println("Item price - " + item_price);
        System.out.println("Item quantity - " + item_count);

        String[] cart_text_before_submit = driver.findElement(By.id("cart-total")).getText().trim().split(" ");
        int cart_count_before_submit = Integer.parseInt(cart_text_before_submit[0]);
        double cart_price_before_submit = Double.parseDouble(cart_text_before_submit[3].replace("$", "").replace(",", "").trim());

        System.out.println("Before Submitting");
        System.out.println(Arrays.toString(cart_text_before_submit));
        System.out.println("count in cart - " + cart_count_before_submit);
        System.out.println("price in cart - " + cart_price_before_submit);
        System.out.println();

        driver.findElement(By.id("button-cart")).click();

        //Check by item's count and price, that product was successfully added to cart (check Pic2) - TODO
        wait_5_sec.until(ExpectedConditions.elementToBeClickable(By.id("button-cart")));
        String[] cart_text_after_submit = driver.findElement(By.id("cart-total")).getText().trim().split(" ");
        int cart_count_after_submit = Integer.parseInt(cart_text_after_submit[0]);
        double cart_price_after_submit = Double.parseDouble(cart_text_after_submit[3].replace("$", "").replace(",", "").trim());

        System.out.println("Cart Text after submitting");
        System.out.println(Arrays.toString(cart_text_after_submit));
        System.out.println("count in cart - " + cart_count_after_submit);
        System.out.println("price in cart - " + cart_price_after_submit);
        System.out.println();


        Assert.assertEquals(cart_count_after_submit, cart_count_before_submit+item_count);
        Assert.assertEquals(cart_price_after_submit, cart_price_before_submit+item_price);
        System.out.println(cart_count_before_submit+item_count);
        System.out.println(cart_price_before_submit+item_price);

        //        - Click on Pic2 element and click on 'Checkout' - TODO

        customActions.click(By.cssSelector("#cart > button"));

        customActions.click(By.cssSelector("#cart > ul > li:nth-child(2) > div > p > a:nth-child(2)"));

        //        - Fill Billing Details, choose Georgia and Tbilisi - TODO

        customActions.click(By.cssSelector("#collapse-payment-address > div > form > div:nth-child(3) > label > input[type=radio]"));

        driver.findElement(By.id("input-payment-firstname")).sendKeys("John");
        driver.findElement(By.id("input-payment-lastname")).sendKeys("Smith");
        driver.findElement(By.id("input-payment-city")).sendKeys("Tbilisi");
        driver.findElement(By.id("input-payment-postcode")).sendKeys("2314");
        driver.findElement(By.id("input-payment-address-1")).sendKeys("Tbilisi street 2");
        new Select(driver.findElement(By.id("input-payment-country"))).selectByValue("80");
        new Select(driver.findElement(By.id("input-payment-zone"))).selectByValue("1244");
        customActions.click(By.id("button-payment-address"));


        //        - Leave Delivery Details and Methods default - TODO
        wait_5_sec.until(ExpectedConditions.presenceOfElementLocated(By.id("collapse-shipping-method")));

        js.executeScript("arguments[0].click()", driver.findElement(By.id("button-shipping-address")));
        js.executeScript("arguments[0].click()", driver.findElement(By.id("button-shipping-method")));


        wait_5_sec.until(ExpectedConditions.presenceOfElementLocated(By.id("collapse-payment-method")));
        driver.findElement(By.cssSelector("#collapse-payment-method > div > div.buttons > div > input[type=checkbox]:nth-child(2)")).click();
        driver.findElement(By.id("button-payment-method")).click();


        //        - In 'Confirm Order' section check that Sub-Total, Flat Shipping Rate and Total amount is correct - TODO
        wait_5_sec.until(ExpectedConditions.presenceOfElementLocated(By.id("collapse-checkout-confirm")));

        Assert.assertEquals(
                driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tbody/tr/td[3]")).getText().trim(),
                ((Integer)cart_count_after_submit).toString()
        );

        Assert.assertEquals(
                Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tbody/tr/td[4]")).getText().replace("$", "").replace(",", "").trim()),
                item_price
        );

        Assert.assertEquals(
                Double.parseDouble(driver.findElement(By.xpath("//*[@id=\"collapse-checkout-confirm\"]/div/div[1]/table/tbody/tr/td[5]")).getText().replace("$", "").replace(",", "").trim()),
                cart_price_after_submit
        );

        driver.findElement(By.id("button-confirm")).click();
        driver.findElement(By.cssSelector("#content > div > div > a")).click();

        //        - Click on 'history' link and check that status is 'Pending' and date equal to current date - TODO
        driver.findElement(By.cssSelector("body > footer > div > div > div:nth-child(4) > ul > li:nth-child(2) > a")).click();

        Assert.assertEquals(
                driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr[1]/td[4]")).getText().trim(),
                "Pending"
        );

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        Assert.assertEquals(
                driver.findElement(By.xpath("//*[@id=\"content\"]/div[1]/table/tbody/tr[1]/td[6]")).getText().trim(),
                dtf.format(now)
        );
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }
}
