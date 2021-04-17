import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;


import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class test {
    protected WebDriver driver;
    public static String loginUrl = "https://www.gittigidiyor.com";

    @Before
    //setting up the webdriver
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "/Users/.../chromedriver");
        driver = new ChromeDriver();
        driver.get(loginUrl);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(60,TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @Test
    // checking the open page
    public void correctPage() {
        Assert.assertEquals(driver.getTitle(), "GittiGidiyor - Türkiye'nin Öncü Alışveriş Sitesi"); // checking whether in the main page or not
    }
    @Test
    // checking login
    public void login(){
        Actions hover = new Actions(driver);
        hover.moveToElement(driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]"))).build().perform(); // in the main page hovering over the log in logo
        driver.findElement(By.xpath("//*[@id=\"main-header\"]/div[3]/div/div/div/div[3]/div/div[1]/div[2]/div/div/div/a")).click(); // clicking log in button
        driver.findElement(By.id("L-UserNameField")).sendKeys("yourmailaddress"); // entering the email
        driver.findElement(By.id("L-PasswordField")).sendKeys("yourpassword"); // entering the password
        driver.findElement(By.id("gg-login-enter")).click(); // clicking log in
        Assert.assertEquals(driver.getTitle(), "GittiGidiyor - Türkiye'nin Öncü Alışveriş Sitesi"); // checking whether logged in or not
    }
    @Test

    public void product() throws InterruptedException {
        driver.findElement(By.name("k")).sendKeys("bilgisayar");//search for word "bilgisayar"
        Thread.sleep(100);
        driver.findElement(By.className("qjixn8-0")).click();//search button click
        Thread.sleep(100);
        driver.findElement(By.className("current")).sendKeys("2");//navigate to second page
        Thread.sleep(100);
        List<WebElement> results = driver.findElements(By.className("gg-uw-6"));//creating list of products in the entire page
        Random rN = new Random();
        int productNo = rN.nextInt(results.size());// generating random number between 0 and the product number
        results.get(productNo).click();// selecting random product
        Thread.sleep(100);
        String priceTag = driver.findElement(By.xpath("//*[@id='sp-price-discountPrice']")).getText(); // getting the price tag of the product in the page
        WebElement addBasket = driver.findElement(By.xpath("//*[@id=\"add-to-basket\"]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addBasket);
        addBasket.click();// adding the selected product in to the basket
        Thread.sleep(3000);
        driver.findElement(By.className("basket-container")).click();// navigate to basket
        String basketPrice = driver.findElement(By.xpath("//*[@id=\"cart-price-container\"]/div[3]/p")).getText();// getting the price of the product in the basket
        Assert.assertEquals(priceTag,basketPrice); // comparing the prices
        driver.findElement(By.xpath("//*[@class='amount']")).click();// clicking the dropdown menu for amount selection
        Thread.sleep(1000);
        driver.findElement(By.xpath("//*[@class='amount']//*[@value='2']")).click();// from dropdown menu 2 selected
        Thread.sleep(1000);
        String basketAmount = driver.findElement(By.xpath("//*[@id=\"submit-cart\"]/div/div[2]/div[3]/div/div[1]/div/div[5]/div[1]/div/ul/li[1]/div[1]")).getText();// getting the product amount from the basket
        Assert.assertEquals(basketAmount,"Ürün Toplamı (2 Adet)");// comparing the amounts
        driver.findElement(By.className("btn-delete")).click();// clicking empty basket button
        Thread.sleep(1000);
        String emptyBasket = driver.findElement(By.xpath("//*[@id=\"empty-cart-container\"]/div[1]/div[1]/div/div[2]/h2")).getText();// getting the message from the screen
        Assert.assertEquals(emptyBasket,"Sepetinizde ürün bulunmamaktadır.");// comparing the message whether the basket is empty or not
    }
    @After
    public void tearDown() {
        driver.quit();
    }
}
