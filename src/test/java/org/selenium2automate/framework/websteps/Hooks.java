package org.selenium2automate.framework.websteps;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import org.selenium2automate.framework.GlobalConstants;
import static org.selenium2automate.framework.WebDriverGenerator.getDriver;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Hooks class.  This hooks class implement Before and After method, which gets executed before and after of each scenario
 * 
 * 
 * 
 * @author Gaurav Tiwari
 * 
 * @todo It is not tested yet 
 *
 * Copyright [2015] [Gaurav Tiwari]
 * 
 * 
 */



public class Hooks {
	
	@Before()
	public void beforeScenario(Scenario result){
		
		System.out.println("in the hooks");
		/*
		 * Write the sample code here for doing anything before the scenario
		 */
	}
	
	
	@After()
	public void afterScenario(Scenario result) {


		//condition for restricting the screeshot capture functionality only when a test step 
		//of a scenario is failed, Global Constants has the flag value pre-defined by user
		if (result.isFailed() && GlobalConstants.CAPTURE_SCREENSHOT.equalsIgnoreCase("Y")) {
			System.out.println("embedding the screenshot for the failed scenario--" + result.getName());
			try {
				byte[] screenshot = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.BYTES);
				result.embed(screenshot, "image/png");

			} catch (WebDriverException wde) {
				System.err.println(wde.getMessage());
			} catch (ClassCastException cce) {
				cce.printStackTrace();
			}
		}
	}
	
}
