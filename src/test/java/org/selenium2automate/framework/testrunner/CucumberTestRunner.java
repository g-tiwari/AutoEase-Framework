package org.selenium2automate.framework.testrunner;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import static org.selenium2automate.framework.WebDriverGenerator.getDriver;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:Test Scenario/",
		tags={"@Login"},
		glue ={"org.selenium2automate.framework.customizedsteps","org.selenium2automate.framework.mobilesteps","org.selenium2automate.framework.websteps"},
		monochrome = true, 
		format={"pretty",
				"json:target/cucumber-json-report/cucumber.json",
				"html:target/cucumber-html-report",
				"rerun:target/cucumber-failed-report/failed-rerun.txt"}
		)

//,tags={"@groupTest"}
public class CucumberTestRunner {

	static{
		//here goes the code to print the initiation of this framework

	}

	@BeforeClass
	public static void setUp(){
		System.out.println("*******************************************************************");
		System.out.println("*****************************Welcome to****************************");
		System.out.println("************************Auto-Ease Framework************************");
		System.out.println("*******************************************************************");
	}

	
	@AfterClass
	public static void shutDown(){
		try {
			getDriver().close();
		}catch (Exception e){
			System.out.println("some exception occred in closing the browser");
		}
		//System.out.println("\n\n\nThanks for using the AutoEase framework, We hope you like it, please send your feedback on gauravtiwari91@yahoo.com");
		System.out.println("*******************************************************************");
		System.out.println("*****************************Thank You*****************************");
		System.out.println("************************For using the Framework********************");
		System.out.println("*************************Send your feedback on*********************");
		System.out.println("***********************GAURAVTIWARI91@YAHOO.COM********************");
		System.out.println("*******************************************************************");
	
	}
}
