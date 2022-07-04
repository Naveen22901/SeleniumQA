package com.cts.selenium.FlipkartAutomation;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FlipkartAutomate {

	static WebDriver driver;
	static WebElement popup_close;
	static WebElement search_box;
	static String browserName;

	public void getBrowser() {
		System.out.println("Choose any one browser\n1. Chrome\n2. Firefox");
		System.out.println("Enter the browser Name");
		Scanner sc = new Scanner(System.in);
		browserName = sc.next();
		System.out.println("You have chosen "+browserName);
	}

	public void setupDriver() {
		// Setting up driver to go to baseUrl
		if (browserName.matches("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "D:\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		if (browserName.matches("Firefox")) {
			System.setProperty("webdriver.gecko.driver", "D:\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
	}
	public void getUrl() {
		driver.manage().window().maximize();
		driver.get("https://www.flipkart.com");
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
		
	}

	public void Popup() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		popup_close = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("._2doB4z")));
		popup_close.click();
	}

	public void getMobiles() throws InterruptedException {
		// enter keyword mobiles in search box
		WebElement search_box = driver.findElement(By.name("q"));
		search_box.sendKeys("mobiles");
		Thread.sleep(5000);
		// selecting "mobiles under 15000" in auto-search
		driver.findElement(By.xpath("//*[@id='container']/div/div[1]/div[1]/div[2]/div[2]/form/ul/li[3]/div/a")).click();
		Thread.sleep(5000);
		WebElement slider = driver.findElement(By.xpath("//div[@class='_31Kbhn WC_zGJ']//div[@class='_3FdLqY']"));
		Actions builder = new Actions(driver);
		builder.dragAndDropBy(slider, -150, 0).build().perform();
		Thread.sleep(3000);
		// setting Pie as OS Version
		driver.findElement(By.xpath("//div[contains(text(),'Operating System Version Name')]")).click();
		Thread.sleep(5000);
		driver.findElement(By.xpath("//div[contains(text(),'Pie')]")).click();
		Thread.sleep(2000);
		// selecting newest arrivals
		driver.findElement(By.xpath("//div[normalize-space()='Newest First']")).click();
		Thread.sleep(2000);

	}

	public String displayMobiles() {
		List<WebElement> name = driver.findElements(By.xpath("//div[@class='_4rR01T']"));
		List<WebElement> price = driver.findElements(By.xpath("//div[@class='_30jeq3 _1_WHN1']"));
		String price1=price.get(0).getText();
		// Dispalying results
		for (int i = 0; i < 5; i++) {
			System.out.println("****Name****   |  ****Price****  ");
			System.out.println(name.get(i).getText() + price.get(i).getText());
		}
		return price1;
	}
	public void validatePrice(String mobile_price)
	{   System.out.println("Validating 1st mobile price");
		mobile_price=mobile_price.replaceAll("[^\\d]","");
	    mobile_price.trim();
		int actualPrice=Integer.parseInt(mobile_price);
		if(actualPrice<30000)
		{
			System.out.println("Passed: The 1st mobile is less than 30000");
		}
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		FlipkartAutomate f = new FlipkartAutomate();
		f.getBrowser();
		f.setupDriver();
		f.getUrl();
		f.Popup();
		f.getMobiles();
		String mobile_price=f.displayMobiles();
		f.validatePrice(mobile_price);
		driver.close();
	}

}
