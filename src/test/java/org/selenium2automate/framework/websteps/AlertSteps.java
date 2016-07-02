package org.selenium2automate.framework.websteps;

import cucumber.api.java.en.Then;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.security.UserAndPassword;
import org.openqa.selenium.support.ui.ExpectedConditions;


import static org.selenium2automate.framework.WebDriverGenerator.getDriver;
import static org.selenium2automate.framework.WebDriverGenerator.getWait;
/**
 * Alert steps class.  Provides alert handling for web application steps
 *
 * @author Gaurav Tiwari
 *
 * @todo create test cases for this code
 *
 * Copyright [2016] [Gaurav Tiwari]
 *
 *
 */
public class AlertSteps {

	@Then("^I accept the Alert$")
	public void I_accept_the_Alert(){
        try {
            //wait for alert presence
            getWait().until(ExpectedConditions.alertIsPresent());
            //accept the alert
            getDriver().switchTo().alert().accept();
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
	
	}

	@Then("^I dismiss the Alert$")
	public void I_dismiss_the_Alert(){
        try{
		//wait for alert presence
		getWait().until(ExpectedConditions.alertIsPresent());
		//accept the alert
		getDriver().switchTo().alert().dismiss();
        }catch(Exception e){
            Assert.fail(e.getMessage());
            }
	}

	@Then("^I authenticate the alert using \"(.*)\" and \"(.*)\"$")
	public void I_authenticate_the_alert_using_and(String username,String password){
        try {
            //wait for alert presence
            getWait().until(ExpectedConditions.alertIsPresent());
            //accept the alert
            getDriver().switchTo().alert().authenticateUsing(new UserAndPassword(username, password));
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
	
	}

	@Then("^I authenticate the alert using \"(.*)\"$")
	public void I_authenticate_the_alert_using(String params1){
        try {
            //wait for alert presence
            getWait().until(ExpectedConditions.alertIsPresent());
            //accept the alert
            Alert alert = getDriver().switchTo().alert();
            alert.sendKeys(params1);
            alert.accept();
        }catch(Exception e){
            Assert.fail(e.getMessage());
        }
	}

}