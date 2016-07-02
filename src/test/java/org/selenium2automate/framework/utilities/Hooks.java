package org.selenium2automate.framework.utilities;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

/**
 * Created by Legend on 7/1/2016.
 */
public class Hooks {

    @Before
    public void beforeScenario(Scenario scenario){
        System.out.println("******************************************************************");
        System.out.println("*******************Starting scenario "+scenario.getName()+" *******");
        System.out.println("******************************************************************");

    }
    @After
    public void afterSuccessfulScenario(Scenario scenario) {
        if (!scenario.isFailed()) {
            System.out.println("*****||=======||****||=====||*******||========*********||========***************************");
            System.out.println("*****||*******||****||*****||*******||*****************||***********************************");
            System.out.println("*****||*******||****||*****||*******||*****************||***********************************");
            System.out.println("*****||========*****|=======|********========||*********========||**************************");
            System.out.println("*****||*************||*****||****************||*****************||**************************");
            System.out.println("*****||*************||*****||****************||*****************||**************************");
            System.out.println("*****||*************||*****||********=========|*********=========|**************************");

        }
    }

    @After
    public void afterFailedScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            // Some code to execute...
        }
    }
}
