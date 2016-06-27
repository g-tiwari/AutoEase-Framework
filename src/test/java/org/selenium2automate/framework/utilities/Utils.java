package org.selenium2automate.framework.utilities;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


/**
 * Created by gauravtiwari on 6/27/2016.
 */
public class Utils {
    /**
     * Method to highLight a webElement.
     * @param e WebElement
     */
    public static void highLight(WebDriver driver, WebElement e) {
        try {
            if (driver instanceof JavascriptExecutor) {
                executeJS(driver, e, "arguments[0].style.border='3px solid yellow'");
            }
        }catch (Exception ex){
            //ignore
        }
    }


    /**
     * executeJS:(execute the java script in this page).
     * @param script  --the java script we need to execute
     * @since JDK 1.6
     * @return Object
     */
    public static Object executeJS(WebDriver driver, String script) {
        //logger.info("Run the javascript from page ,the java script is:"
        //	+ script);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        return je.executeScript(script);
    }
    /**
     * Method executeJS.
     * @param script String
     * @param e WebElement
     */
    public static void executeJS(WebDriver driver,WebElement e, String script) {
        //logger.info("Run the javascript from page ,the java script is:"
        //	+ script);
        //highLight(e);
        JavascriptExecutor je = (JavascriptExecutor) driver;
        je.executeScript(script,e);

    }



}
