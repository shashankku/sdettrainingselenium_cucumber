package stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.core.api.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class registrationStep {
	
	private WebDriver driver;
	public final long implicitWait = 30;
	public final long pageLoadTimeOut = 30;

	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(this.implicitWait, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(this.pageLoadTimeOut, TimeUnit.SECONDS);
	}

	@After
	public void tearDown(Scenario scenario) throws IOException {
		// Screen shot on failure
		if (scenario.isFailed()) {
			// To embed screenshot in extent report with cucumber
			String scenarioName = scenario.getName();
			byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			/*
			 * Embeds data into the report(s). Some reporters (such as the progress one)
			 * don't embed data, but others do (html and json). Refer the website and go to
			 * EMBED method section -->
			 * //https://www.javadoc.io/doc/info.cukes/cucumber-core/1.2.5/cucumber/api/
			 * Scenario.html#embed-byte:A-java.lang.String-
			 */
			scenario.embed(screenshotBytes, "image/png");
			scenario.write(scenarioName);

			// To put screenshot in a folder with scenario name and date format
			Date d = new Date();
			String date = d.toString().replace(":", "_").replace("", "_");
			File fileTemp = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			File filePerm = new File(
					"C:\\Users\\lenovo\\Desktop\\Workspace_Ibm_Manipal_Selenim_Cucumber_Nov2020\\SeleniumCucumberProj\\FailedScreenshot\\"
							+ scenarioName + "_" + date + ".png");
			FileUtils.copyFile(fileTemp, filePerm);

		}

		if (driver != null) {
			driver.quit();
		}
	}

	@Given("That we navigate to the website \"([^\"]*)\"")
	public void that_we_navigate_to_the_website(String url) {
		driver.get(url);
	}

	@And("We click on the SignUp link")
	public void we_click_on_the_SignUp_link() {
		driver.findElement(By.xpath("//*[@id=\"login-block\"]/div/ul/li[1]/a")).click();
	}

	@Then("The registration form opens where fill the \"([^\"]*)\" edit box")
	public void the_registration_form_opens_where_fill_the_edit_box(String firstName) {
		driver.findElement(By.xpath("//*[@id=\"registration_firstname\"]")).sendKeys(firstName);
	}

	@And("We fill the \"([^\"]*)\" edit box")
	public void we_fill_the_edit_box(String lastName) {
		driver.findElement(By.xpath("//*[@id=\"registration_lastname\"]")).sendKeys(lastName);
	}

	@And("We type the \"([^\"]*)\" field")
	public void we_type_the_field(String email) {
		driver.findElement(By.xpath("//*[@id=\"registration_email\"]")).sendKeys(email);
	}

	@And("We type and fill the \"([^\"]*)\" field.")
	public void we_type_and_fill_the_field(String username) {
		driver.findElement(By.xpath("//*[@id=\"username\"]")).sendKeys(username);
	}

	@And("We filled the \"([^\"]*)\" field.")
	public void we_filled_the_field(String password) {
		driver.findElement(By.xpath("//*[@id=\"pass1\"]")).sendKeys(password);
	}

	@And("We typed the \"([^\"]*)\" field")
	public void we_typed_the_field(String confirmPassword) {
		driver.findElement(By.xpath("//*[@id=\"pass2\"]")).sendKeys(confirmPassword);
	}

	@When("We click on register button")
	public void we_click_on_register_button() {
		driver.findElement(By.xpath("//*[@id=\"registration_submit\"]")).click();
	}

	@Then("the registration confirmation page and we validate the \"([^\"]*)\" and \"([^\"]*)\"")
	public void the_registration_confirmation_page_and_we_validate_the_and(String firstName, String lastName)
			throws InterruptedException {
		String actualText = driver.findElement(By.xpath("//*[@id=\"cm-content\"]/div/div[2]/div/p[1]")).getText();
		System.out.println(actualText);
		Assert.assertEquals(firstName.contains("Kaushik"), actualText.contains("Kaushik"));
		Assert.assertEquals(lastName.contains("Mukherjee"), actualText.contains("Mukherjee"));
		Thread.sleep(5000L);

	}

	@And("Validate that \"([^\"]*)\" and \"([^\"]*)\" in profile menu")
	public void validate_that_and_in_profile_menu(String firstName, String lastName) throws InterruptedException {
		driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul[2]/li[2]/a/img")).click();
		String actualText = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul[2]/li[2]/ul/li[1]/div/a/p")).getText();
		Assert.assertEquals(firstName.contains("Sam"), actualText.contains("Kaushik"));
		Assert.assertEquals(lastName.contains("Mukherjee"), actualText.contains("Mukherjee"));
		Thread.sleep(5000L);
		// Click on Homepage button
		driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul[1]/li[1]")).click();
	}

}
