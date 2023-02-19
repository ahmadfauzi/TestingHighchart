import static org.testng.Assert.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestingChartStagingAndProd {
	
	private static WebDriver driver;
	protected WebDriverWait wait;
	
	@BeforeClass
	public void start() {
		driver = new ChromeDriver();
	    //driver = new FirefoxDriver();
	    driver.manage().window().maximize();
	}
	
	@AfterClass
	public void stop() {
		driver.quit();
	}
	
	@Test
	public void TestingChart() throws InterruptedException{		
		
        // Navigate to the webpage with the barchart (Staging)
		driver.get("https://www.highcharts.com/demo/bar-basic");
		Thread.sleep(1600);
		
		// Get the chart data points from Staging
        List<String> dataPointsStaging = getDataPoints(driver);

        // Navigate to the webpage with the barchart (Production)
     	driver.get("https://www.highcharts.com/demo/bar-basic/brand-light");
     	Thread.sleep(1600);
     	
     	// Get the chart data points from Production
        List<String> dataPointsProduction = getDataPoints(driver);

        // Compare the data points from the two charts, if not match will return message
        assertEquals(dataPointsStaging, dataPointsProduction, "The data points from two chart DO NOT match.");
	}
	
	private static List<String> getDataPoints(WebDriver driver){
		 // Find the elements that represent the data points on the chart
        List<WebElement> dataPointElements = driver.findElements(By.cssSelector("rect.highcharts-point"));

        // Extract the data values from the data point elements
        List<String> dataPoints = new ArrayList<>();
        for (WebElement dataPointElement : dataPointElements) {
            String dataValue = dataPointElement.getAttribute("aria-label");
            if(dataValue != null) {
            	dataPoints.add("Data point: [" + dataValue + "]");
                //System.out.println("Data point: [" + dataValue + "]");
            }
        }
		return dataPoints;	
	}
}
