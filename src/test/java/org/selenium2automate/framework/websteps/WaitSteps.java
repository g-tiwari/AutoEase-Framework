package org.selenium2automate.framework.websteps;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.selenium2automate.framework.Constants;
import static org.selenium2automate.framework.WebDriverGenerator.getDriver;
import static org.selenium2automate.framework.WebDriverGenerator.getWait;
import static org.selenium2automate.framework.WebDriverGenerator.getLocator;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.selenium2automate.framework.utilities.WaitUtils;


/**
 * Wait tool class.  Provides Wait methods for an elements, and AJAX elements to load.  
 * It uses WebDriverWait (explicit wait) for waiting an element or javaScript.  
 * 
 * To use implicitlyWait() and WebDriverWait() in the same test, 
 * we would have to nullify implicitlyWait() before calling WebDriverWait(), 
 * and reset after it.  This class takes care of it. 
 * 
 * 
 * Generally relying on implicitlyWait slows things down 
 * so use WaitTool�s explicit wait methods as much as possible.
 * Also, consider (DEFAULT_WAIT_4_PAGE = 0) for not using implicitlyWait 
 * for a certain test.
 * 
 * @author Gaurav Tiwari
 * 
 * @todo check FluentWait
 *
 * Copyright [2016] [Gaurav Tiwari]
 * 
 * 
 */
public class WaitSteps {

	@Given("^I wait for the page to load$")
	public void I_wait_for_the_page_to_load(){
		try {
			getWait().until(new
									ExpectedCondition<Boolean>() {
										public Boolean apply(WebDriver driver) {
											return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
										}
									});
		}catch (Exception e){
			Assert.fail(e.getMessage());
		}
	
	}

	/**
	  * Wait for the element to be present in the DOM, and displayed on the page. 
	  * And returns the first WebElement using the given method.
	  * 
	  * @param identifier	selector to find the element
	  * @return
	  */
	@Then("^I wait for visibility of element \"(.*)\"$")
	public void I_wait_for_visibility_of_element(String identifier){
		WebElement element; 
		try{	
			//To use WebDriverWait(), we would have to nullify implicitlyWait(). 
			//Because implicitlyWait time also set "getDriver().findElement()" wait time.  
			//info from: https://groups.google.com/forum/?fromgroups=#!topic/selenium-users/6VO_7IXylgY
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 

			WebDriverWait wait = new WebDriverWait(getDriver(), Constants.EXPLICIT_WAIT_4_TIMEOUT); 
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(identifier)));
			wait.until(ExpectedConditions.visibilityOfElementLocated(getLocator(identifier)));
			getDriver().manage().timeouts().implicitlyWait(Constants.DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			Assert.assertTrue(element.getSize().height!=0); //return the element	
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail(e.getMessage());
		}
	
	}

	@And("^I wait for presence of element \"(.*)\"$")
	public void I_wait_for_presence_of_element(String params1){
		getWait().until(ExpectedConditions.presenceOfElementLocated(getLocator(params1)));
	
	}
	
	@And("^I wait for (\\d+) seconds$")
	public void I_wait_for_seconds(int waitTime){
		try {
			Long wait = (long) (waitTime * 1000);
			Thread.sleep(wait);
		}catch (Exception e){
			System.out.println("Not able to wait for "+waitTime +" seconds "+e.getMessage());
		}
	}

	@And("^I wait for element \"(.*)\" to be clickable$")
	public void I_wait_for_element_to_be_clickable(String locator){
		try {
			getWait().until(ExpectedConditions.elementToBeClickable(getLocator(locator)));
		}catch(Exception e){
			Assert.fail(e.getMessage());
		}
	}

	@And("^I wait for element \"(.*)\" to appear on the refreshed web-page within (\\d+) seconds$")
	public void I_wait_for_element_to_appear_on_the_refreshed_web_page(String locator,int time){
        try {
            WaitUtils.waitForElementRefresh(getDriver(), getLocator(locator), time);
        }catch (Exception e){
            Assert.fail(e.getMessage());
        }
	
	}

	/**
	  * Wait for the Text to be present in the given element, regardless of being displayed or not.
	  *
	  * @param identifer	selector of the given element, which should contain the text
	  * @param expected_Text	The text we are looking
	  * 
	  * @return test steps would be marked as fail if expected text is not found for the given identifier with in defined explicit wait time
	  */
	@And("^I wait for the Text \"(.*)\" to be present in the element \"(.*)\"$")
	public void I_Wait_for_the_Text_to_be_present_in_the_element(final String expected_Text,final String identifer){
		boolean isPresent = false; 
		try{	
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(getDriver(), Constants.EXPLICIT_WAIT_4_TIMEOUT) {
	        }.until(new ExpectedCondition<Boolean>() {

	            public Boolean apply(WebDriver driver) {
	            	return WaitUtils.isTextPresent(getDriver(),getLocator(identifer), expected_Text); //is the Text in the DOM
	            }
	        });
	        isPresent = WaitUtils.isTextPresent(getDriver(),getLocator(identifer), expected_Text);
			getDriver().manage().timeouts().implicitlyWait(Constants.DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			Assert.assertTrue(isPresent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	/** 
	 * Waits for the Condition of JavaScript.  
	 *
	 *
	 * @param jsCodeString	The javaScript condition we are waiting. e.g. "return (xmlhttp.readyState >= 2 && xmlhttp.status == 200)"
	 * @return test steps would be marked as fail if jquery condition does not get true with in the explicit wait time
	 **/
	@And("^I wait for the JavaScript condition \"(.*)\" to be true$")
	public void I_Wait_for_the_JavaScript_condition_to_be_true(final String jsCodeString){
		boolean jscondition = false; 
		try{	
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(getDriver(), Constants.EXPLICIT_WAIT_4_TIMEOUT) {
	        }.until(new ExpectedCondition<Boolean>() {


	            public Boolean apply(WebDriver driver) {
	            	return (Boolean) ((JavascriptExecutor) driver).executeScript(jsCodeString);
	            }
	        });
	        jscondition =  (Boolean) ((JavascriptExecutor) getDriver()).executeScript(jsCodeString);
			getDriver().manage().timeouts().implicitlyWait(Constants.DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			Assert.assertTrue(jscondition);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}

	
	/** Waits for the completion of Ajax jQuery processing by checking "return jQuery.active == 0" condition.  
	 *
	 * 
	 * @return test steps would be marked as fail if jquery condition does not get true with in the explicit wait time
	 * */
	@And("^I wait for the completion of Ajax jQuery processing by checking return jQuery.active == 0 condition$")
	public void I_Wait_for_the_completion_of_Ajax_jQuery_processing_by_checking_return_jQuery_active_condition(){
		boolean jQcondition = false; 
		try{	
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
	        new WebDriverWait(getDriver(), Constants.EXPLICIT_WAIT_4_TIMEOUT) {
	        }.until(new ExpectedCondition<Boolean>() {

	            public Boolean apply(WebDriver driver) {
	            	return (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active == 0");
	            }
	        });
	        jQcondition = (Boolean) ((JavascriptExecutor) getDriver()).executeScript("return jQuery.active == 0");
			getDriver().manage().timeouts().implicitlyWait(Constants.DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			//return jQcondition;
			Assert.assertTrue(jQcondition);
		} catch (Exception e) {
			Assert.fail(e.getMessage());
		} 
	
	}

	
	/**
	 * Coming to implicit wait, If you have set it once then you would have to explicitly set it to zero to nullify it -
	 */
	@And("^I reset the implicit wait to zero$")
	public void I_set_the_implicit_wait_to_zero(){
		try {
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
		}catch (Exception e){
			Assert.fail(e.getMessage());
		}
	
	}

	/** 
	 * To reset ImplicitWait time you would have to explicitly 
	 * set it to zero to nullify it before setting it with a default time value. 
	 */
	@And("^I reset the implicit wait to default implicit wait$")
	public void I_set_the_implicit_wait_to_default_implicit_wait(){
		try {
			getDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()
			WaitUtils.resetImplicitWait(getDriver(), Constants.DEFAULT_WAIT_4_PAGE);
		}catch (Exception e){
			Assert.fail(e.getMessage());
		}
	
	}

	@And("^I reset the implicit wait to (\\d+)$")
	public void I_set_the_implicit_wait_to_someValue(int seconds){
		try {
			WaitUtils.resetImplicitWait(getDriver(), seconds);
		}catch (Exception e){
			Assert.fail(e.getMessage());
		}
	
	}


}