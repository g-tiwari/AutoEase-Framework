package org.selenium2automate.framework.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.selenium2automate.framework.Constants;


import java.util.List;
import java.util.concurrent.TimeUnit;


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
 * @todo implement fluent wait for polling
 *
 * Copyright [2016] [Gaurav Tiwari]
 * 
 * 
 */
public class WaitUtils {
	
	/** define the Default wait time for an element in Constants.java file */ 
	public static final int DEFAULT_WAIT_4_ELEMENT = Constants.DEFAULT_WAIT_4_ELEMENT;
	/** Define default wait time for a page to be displayed in Constants.java file.  
	 * The average webpage load time is 5 seconds in 2016.
	 * Based on your tests, please set this value in Constant.java. 
	 * "0" will nullify implicitlyWait and speed up a test. */ 
	public static final int DEFAULT_WAIT_4_PAGE = Constants.DEFAULT_WAIT_4_PAGE;

	/**
	  * Wait for the element to be present in the DOM, regardless of being displayed or not.
	  * And returns the first WebElement using the given method.
	  *
	  * @param by	selector to find the element
	  * @param timeOutInSeconds	The time in seconds to wait until returning a failure
	  * 
	  * @return WebElement	the first WebElement using the given method, or null (if the timeout is reached)
	  */
	public static WebElement waitForElementPresent(WebDriver driver, final By by, int timeOutInSeconds) {
		WebElement element; 
		try{
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait()

			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.presenceOfElementLocated(by));

			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return element; //return the element
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null; 
	}


	/**
	  * Wait for the List<WebElement> to be present in the DOM, regardless of being displayed or not.
	  * Returns all elements within the current page DOM. 
	  *
	  * @param by	selector to find the element
	  * @param timeOutInSeconds	The time in seconds to wait until returning a failure
	  *
	  * @return List<WebElement> all elements within the current page DOM, or null (if the timeout is reached)
	  */
	public static List<WebElement> waitForListElementsPresent(WebDriver driver,final By by, int timeOutInSeconds) {
		List<WebElement> elements; 
		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 

			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds); 
			wait.until((new ExpectedCondition<Boolean>() {
	            public Boolean apply(WebDriver driver) {
	                return areElementsPresent(driver,by);
	            }
	        }));

			elements = driver.findElements(by); 
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return elements; //return the element	
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null; 
	}

	/**
	  * Wait for an element to appear on the refreshed web-page.
	  * And returns the first WebElement using the given method.
	  *
	  * This method is to deal with dynamic pages.
	  * 
	  * Some sites require a page refresh to add additional elements to the DOM.
	  *
	  * @param by	selector to find the element
	  * @param timeOutInSeconds	The time in seconds to wait until returning a failure
	  * 
	  * @return WebElement	the first WebElement using the given method, or null(if the timeout is reached)
	  * 
	  * @author Gaurav Tiwari
	  */
	 public static WebElement waitForElementRefresh(WebDriver driver,final By by,
			                           int timeOutInSeconds) {
		WebElement element; 
		try{	
			driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
		        new WebDriverWait(driver, timeOutInSeconds) {
		        }.until(new ExpectedCondition<Boolean>() {

		            
		            public Boolean apply(WebDriver driver) {
		                driver.navigate().refresh(); //refresh the page ****************
		                return isElementPresentAndDisplay(driver,by);
		            }
		        });
		    element = driver.findElement(by);
			driver.manage().timeouts().implicitlyWait(DEFAULT_WAIT_4_PAGE, TimeUnit.SECONDS); //reset implicitlyWait
			return element; //return the element
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return null; 
	 }

	

	/**
	 * Set driver implicitlyWait() time. 
	 */
	public static void setImplicitWait(WebDriver driver,int waitTime_InSeconds) {
		driver.manage().timeouts().implicitlyWait(waitTime_InSeconds, TimeUnit.SECONDS);  
	} 


	/**
	 * Reset ImplicitWait.  
	 * @param waitTimeSeconds - a new wait time in seconds
	 */
	public static void resetImplicitWait(WebDriver driver, int waitTimeSeconds) {
		driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); //nullify implicitlyWait() 
		driver.manage().timeouts().implicitlyWait(waitTimeSeconds, TimeUnit.SECONDS); //reset implicitlyWait
	} 


     /**
	   * Checks if the text is present in the element. 
       *
	   * @param by - selector to find the element that should contain text
	   * @param text - The Text element you are looking for
	   * @return true or false
	   */
	public static boolean isTextPresent(WebDriver driver,By by, String text)
	{
		try {
				return driver.findElement(by).getText().contains(text);
		} catch (NullPointerException e) {
				return false;
		}
	}


	/**
	 * Checks if the elment is in the DOM, regardless of being displayed or not.
	 *
	 * @param by - selector to find the element
	 * @return boolean
	 */
	private static boolean isElementPresent(WebDriver driver, By by) {
		try {
			driver.findElement(by);//if it does not find the element throw NoSuchElementException, which calls "catch(Exception)" and returns false; 
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}


	/**
	 * Checks if the List<WebElement> are in the DOM, regardless of being displayed or not.
	 *
	 * @param by - selector to find the element
	 * @return boolean
	 */
	private static boolean areElementsPresent(WebDriver driver,By by) {
		try {
			if(driver.findElements(by).size()>0)
				return true;
			else
				return  false;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	/**
	 * Checks if the elment is in the DOM and displayed. 
	 *
	 * @param by - selector to find the element
	 * @return boolean
	 */
	private static boolean isElementPresentAndDisplay(WebDriver driver,By by) {
		try {			
			return driver.findElement(by).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}


}