package org.selenium2automate.framework.websteps;

public class Reusable {
	
	public AssertionsSteps as;
	public NavigationSteps br;
	public InputSteps is;
	public TypeAndClickSteps tf;
	

	
	
	public Reusable(){
		System.out.println("reusable contructor");
		 as=new AssertionsSteps();
		 br=new NavigationSteps();
		 is=new InputSteps();
		 tf=new TypeAndClickSteps();
		
	}
}
