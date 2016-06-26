package org.selenium2automate.framework.websteps;

import junit.framework.Assert;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.selenium2automate.framework.WebDriverGenerator.getDriver;
import static org.selenium2automate.framework.WebDriverGenerator.getWaitObject;
import cucumber.api.java.en.When;

public class BrowserRelated {
		
		@When("^I open the URL \"(.*)\"$")
		public void I_open_the_URL(String URL){
			try{
				System.out.println(URL);
				//assert that URL has the protocol in itself or not
				Assert.assertTrue("URL does not contain http",URL.contains("http"));
				getDriver().get(URL);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
		
		}
}