package example;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TestTheProgress {

	public static void main(String[] args) throws InterruptedException {
		// Multiple Input names and message validation
		String[][] testData = { { "Jill", "Jill is female with 100% certainty" },
				{ "Shiwani", "Shiwani is female with 94% certainty" },
				{ "Tom", "Tom is male with 99% certainty" } };
		WebDriver driver = new ChromeDriver(); // Invoking chrome driver
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		for (String[] data : testData) {
			String name = data[0];
			String expectedMessage = data[1];
			//Navigate to the website
			driver.get("https://genderize.io/");
			//maximize the window
			driver.manage().window().maximize();
			//enter the name to the text field
			driver.findElement(By.xpath("//*[@id=\"trial-input\"]")).sendKeys(name);
			//Clicking the magnifying glass.
			WebElement Element = driver.findElement(By.cssSelector(".hero-magnifying-glass.text-white.h-6.w-6"));
			//static wait added due to dynamic webelement
			Thread.sleep(1000);
			Element.click();
			Thread.sleep(2000);
			String actualMessagea = driver
					.findElement(By.xpath("(//p[@class='max-w-[400px] text-center mt-6 text-smi font-mono'])[1]"))
					.getText();
			System.out.println(actualMessagea);
			//Validating the message
			Assert.assertEquals(actualMessagea, expectedMessage);

		}
		//close the current browser
		driver.close();
	}

}
