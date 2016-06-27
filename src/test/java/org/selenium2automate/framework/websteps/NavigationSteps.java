package org.selenium2automate.framework.websteps;

import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriverException;

import static org.selenium2automate.framework.WebDriverGenerator.getDriver;

public class NavigationSteps {
		
		@When("^I open the URL \"(.*)\"$")
		public void I_open_the_URL(String URL){
			try{
				getDriver().get(URL);
			}catch(WebDriverException we){
				getDriver().get("http://"+URL);
			}catch(Exception e){
				//mark this step as fail and skip the test
				Assert.fail(e.getMessage());
			}
		
		}


}