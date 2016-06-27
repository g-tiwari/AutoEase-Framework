package org.selenium2automate.framework.websteps;

import static org.selenium2automate.framework.WebDriverGenerator.OR;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.selenium2automate.framework.WebDriverGenerator.getDriver;
import static org.selenium2automate.framework.WebDriverGenerator.getWait;
import static org.selenium2automate.framework.WebDriverGenerator.getLocator;

import cucumber.api.java.en.Then;
import org.selenium2automate.framework.utilities.Utils;

public class TypeAndClickSteps {
	
		@Then("^I click on element identifier \"(.*)\"$")
		public void I_click_on_element_identifier(String identifier) {
			try{
				//System.out.println(OR.getProperty(params1));
				getDriver().findElement(getLocator(identifier)).click();
			}
			catch(NoSuchElementException e){
				System.out.println(e.getMessage() + "Given Identifier is not found on the page, either the identifier is invalid or changed");
			}
		
		}
		
		@Then("^I click using javascript on element identifier \"(.*)\"$")
		public void I_click_using_javascript_on_element_identifier(String params1) {
			JavascriptExecutor je = (JavascriptExecutor) getDriver();
			Utils.highLight(getDriver(), getDriver().findElement(getLocator(params1)));
			je.executeScript("arguments[0].click()",getDriver().findElement(getLocator(params1)));
		
		}

		@Then("^I click using native events on element identifier \"(.*)\"$")
		public void I_click_using_actions_class_on_element_identifier(String params1) throws Throwable {
		//throw new PendingException();
		
		}

		@Then("^I type \"(.*)\" in field identifier \"(.*)\"$")
		public void I_type_in_field_identifier(String text_to_type,String identifier) {
			try{	
				WebElement e=getDriver().findElement(getLocator(identifier));
				Utils.highLight(getDriver(), e);
				e.clear();
				e.sendKeys(text_to_type);
			}
				catch(Exception e){
					System.out.println("Identifier is not found on the page, either the identifier is invalid or changed");
					Assert.fail(e.getMessage());
				
			}
		
		}

		@Then("^I clear the field identifier \"(.*)\"$")
		public void I_clear_the_field_identifier(String identifier){
			try{
				WebElement e=getDriver().findElement(getLocator(OR.getProperty(identifier)));
				Utils.highLight(getDriver(), e);
				e.clear();
			} catch (Exception e) {
				//Assert.assertFalse(true);
				Assert.fail(e.getMessage());
			} 
		
		}

		@Then("^I type \"(.*)\" without clearing the field identifier \"(.*)\"$")
		public void I_type_without_clearing_the_field_identifier(String text_to_type,String identifier) {
			try{	
				getDriver().findElement(By.id(identifier)).sendKeys(text_to_type);
			} catch (Exception e) {
				//Assert.assertFalse(true);
				Assert.fail(e.getMessage());
			} 
		
		
		}
		
		@Then("^I submit the form \"(.*)\"$")
		public void I_submit_the_form(String identifier) {
			try{	
				WebElement e=getDriver().findElement(getLocator(identifier));
				Utils.highLight(getDriver(), e);
				e.submit();
			} catch (Exception e) {
				//Assert.assertFalse(true);
				Assert.fail(e.getMessage());
			} 
		
		}
		

		@Then("^I type \"(.*)\" using javascript in field identifier \"(.*)\"$")
		public void I_type_using_javascript_in_field_identifier(String params1,String params2){
		//throw new PendingException();
		
		}
		
}