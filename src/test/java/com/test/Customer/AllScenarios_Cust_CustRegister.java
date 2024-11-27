package com.test.Customer;

import java.io.IOException;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.BasePackage.Base_Class;
import com.Page_Customer.Customer_CustomerRegister;
import com.Page_Repositary.PageRepositary_Cust_CustRegister;
import com.Page_Repositary.PageRepositary_Cust_CustSearch;
import com.Utility.Log;
import com.extentReports.ExtentManager;
import com.extentReports.ExtentTestManager;
import com.listeners.TestListener;

public class AllScenarios_Cust_CustRegister extends Base_Class{
	com.Utility.ExcelReader ExcelReader;
	Base_Class Base_Class;
	Log log;
	TestListener TestListener; 
	com.Utility.ScreenShot screenShot;
	PageRepositary_Cust_CustRegister custRgstr = new PageRepositary_Cust_CustRegister();
	com.Page_Customer.Customer_CustomerSearch custSrchMthds = new com.Page_Customer.Customer_CustomerSearch();
	Customer_CustomerRegister custRgstrMthds = new Customer_CustomerRegister();
	@BeforeSuite
	public void reference() {
		ExcelReader = new com.Utility.ExcelReader("Customer_CustRegister");
		log = new Log();
		TestListener = new TestListener();
		screenShot = new com.Utility.ScreenShot(null);
		Base_Class = new Base_Class();
	}
	
	@Test(dataProvider = "TestData")
	public void customerRegister(Map<Object, Object> testdata, ITestContext context) throws ClassNotFoundException, InterruptedException, IOException{
		try {
			if(testdata.get("Run").toString().equalsIgnoreCase("Yes")){
				ExtentTestManager.startTest(testdata.get("TestScenario").toString());
				Log.info("*** Running test method " + testdata.get("TestScenario").toString() + "...");
				context.setAttribute("fileName", "Login");

				//TC No. - 02 --> Application launch
				Base_Class.SetUp();
				Thread.sleep(2000);
				
//				String pcRegFormName = testdata.get("pcRegFormName").toString();
//				String pcRegFormPcName = testdata.get("pcRegFormPcName").toString();
//				String loginUserName = testdata.get("loginUserName").toString();
//				String loginValidPassword = testdata.get("loginValidPassword").toString();
//				String validCustId = testdata.get("validCustId").toString();

				//PC Registration
				custSrchMthds.pcRegistration(testdata, context);
				
				//User Login
				custSrchMthds.userLoginValidPaswrd(testdata, context);

				//TC No. - 01 --> Customer Register Window
				custRgstrMthds.customerRegisterWindow();
				
				//TC No. - 02 --> Customer ID Field Required, validation
				custRgstrMthds.srchWithoutCustId();
				
				//TC No. - 05 --> Customer Register - Validation for parameters 
				custRgstrMthds.testAllParametersPresent();
				
				//TC No. - 04,06 & 07 --> Customer ID Field BVA
				//MIN-1 & MAX-1
				custRgstrMthds.searchByMobileInvalid1(testdata, context);
				//MIN+1 & MAX+1 (Can't execute, text box doesn't accept >12)
//				custRgstrMthds.searchByMobileInvalid2(testdata, context);
				//MIN & MAX Value
				custRgstrMthds.viewByValidCustId(testdata, context);


				
				
				
				
				//Sign out
				Thread.sleep(5000);
				custSrchMthds.signOut();
				
				// EndTest
				ExtentTestManager.endTest();
				ExtentManager.getInstance().flush();
			}		
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	@DataProvider(name = "TestData")
	public static Object[][] gettestdate() throws IOException {

		Object[][] objectarry = null;
		java.util.List<Map<String, String>> completedata = com.Utility.ExcelReader.getdata();

		objectarry = new Object[completedata.size()][1];

		for (int i = 0; i < completedata.size(); i++) {
			objectarry[i][0] = completedata.get(i);
		}
		return objectarry;
	}
	
}