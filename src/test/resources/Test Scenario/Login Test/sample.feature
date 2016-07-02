Feature: This feature file is for demo
  	 
@Login
Scenario: Sample login scenario
     Given I open the URL "http://www.testciq.com"
     And I type "rahulsharma1@spcapitaliq.com" in field identifier "loginusernameID"
     And I type "Login@11" in field identifier "loginpasswordID"
     When I click on element identifier "signInBtnID"
     And I wait for presence of element "logoutLinkXpath"



  

	    
	    
  	 
  	 
  	 		