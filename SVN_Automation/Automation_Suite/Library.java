
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Iterator;
import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import au.com.bytecode.opencsv.CSVReader;
import com.gargoylesoftware.htmlunit.ElementNotFoundException;

public class Library {
	
	WebDriver driver;
	Timestamp timestamps;
	String executionresult,screenshotpath;
	int rownumber;
	String Keyword1;
	public static WebElement Webelement = null;
	String xpathText,text;
	String WebclientURLpath=null;
	String firefoxpathexe=null;
	String chromepathexe=null;
	String chromebinarypathexe=null;
	String iepathexe=null;
	String browsertype=null;
	String enteredname;
	String variable;
	String actualimage;
	String imageUrl;
	String imexpected;
	String imactual;
/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: wait
 * 
 * Developed By: POLARIS_TEAM
 * 
 * Functionality Automated: This Function is used to wait
 *--------------------------------------------------------------------------------------------------------------------*/

public int wait(Object[] enter)
{
	int flag=1;
	try{
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		flag=1;
	 }
	 catch (NoSuchElementException  e) {
		 flag=0;
	 }
	
	return flag;
}

/*--------------------------------------------------------------------------------------------------------
*FUNCTION NAME: setUp_web() 
*
*Developed BY: POLARIS_TEAM
*
*FUNCTIONALITY INVOLVED: This function is used to setup the capabilities for the android device in
*						  in which the script is being executed
* -------------------------------------------------------------------------------------------------------*/
public int setUp_web(Object[] enter) throws IOException, InvocationTargetException{
	
	int flag=0;
	try {
      	//Runtime.getRuntime().exec("taskkill /f /im firefox.exe") ;
     	Runtime.getRuntime().exec("taskkill /f /im iexplore.exe");     	
        Runtime.getRuntime().exec("taskkill /f /im chrome.exe");
        //Runtime.getRuntime().exec("taskkill /f /im IEDriverServer.exe");
        //Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
    } catch (Exception e) {
        e.printStackTrace();  
    }

	if(browsertype.equals("firefox")){
		FirefoxProfile profile = null;
	driver = new FirefoxDriver(new FirefoxBinary(new File(firefoxpathexe)), profile);
	driver.get(WebclientURLpath);
	}
	else if(browsertype.equals("chrome"))
	{
			System.setProperty("webdriver.chrome.driver", chromepathexe);
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
    			ChromeOptions options = new ChromeOptions();
    			options.addArguments("test-type");
   			capabilities.setCapability("chrome.binary","C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
    			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

    driver = new ChromeDriver(capabilities);
			driver.get(WebclientURLpath);
	}
	else if(browsertype.equals("ie"))
	{
		System.setProperty("webdriver.ie.driver", iepathexe);
		driver = new InternetExplorerDriver();
		driver.get(WebclientURLpath);		
	}	
	 driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
	 driver.manage().window().maximize();
	 /*
	try{
		//Thread.sleep(5000);
		String expected=enter[6].toString();
		String actual=driver.getTitle();
		System.out.format ("\n%s$", expected);
		System.out.format ("\n%s$", actual);
		
		if(expected.toString().trim().equalsIgnoreCase(actual)) //Theyagu - added trim
		{				
			System.out.println("Title Matches");
			flag=1;			
		}
	 }
	 catch(NoSuchElementException e) {
		 flag=0;
		 } catch (Exception e) {
			 e.printStackTrace();
		 }*/
             flag=1;
		return flag;
}	
/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: kill_web
 * 
 * Developed By:POLARIS_TEAM
 * 
 * Functionality Automated: This Function is used to kill web-client browser
 *--------------------------------------------------------------------------------------------------------------------*/

public int kill_web(Object[] enter) throws InterruptedException
{
	int flag=0;
	try {
        Runtime.getRuntime().exec("taskkill /f /im cmd.exe") ;
        Runtime.getRuntime().exec("taskkill /f /im emulator-arm.exe") ;
        //Runtime.getRuntime().exec("taskkill /f /im firefox.exe") ;
        Runtime.getRuntime().exec("taskkill /f /im IEDriverServer.exe");
        Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
        flag=1;
    } catch (Exception e) {
        e.printStackTrace();  
    }
	return flag;
}
/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: entertext_web
 * 
 * Developed By:POLARIS_TEAM
 * 
 * Functionality Automated: This Function is used to enter the text in the text field
 *--------------------------------------------------------------------------------------------------------------------*/

public int entertext_web(Object[] enter) throws InterruptedException
{
	
	int flag=0,txtindex=0;
	 
	if(enter[0].toString().equals("name"))
	{
		try{
			Thread.sleep(2000);
			WebElement sFDEmail=driver.findElement(By.name(enter[2].toString()));
			if(sFDEmail.isDisplayed())	
			  {				
				sFDEmail.clear();
				enteredname=enter[1].toString();				
				sFDEmail.sendKeys(enteredname);
				flag=1;
			  }
			  else
			  {
				flag=0;
			  }
		
		 }
		 catch (NoSuchElementException e) {
			 System.out.println("Element could not be found !!!");
		 }
	}
	else if(enter[0].toString().equals("xpath"))
	{
		try{
			Thread.sleep(2000);
			WebElement sFDEmail=driver.findElement(By.xpath(enter[2].toString()));
			if(sFDEmail.isDisplayed())	
			  {
				sFDEmail.clear();
				enteredname=enter[1].toString();
				sFDEmail.sendKeys(enteredname);
				flag=1;
			  }
			  else
			  {
				flag=0;
			  }
		
		 }
		 catch (NoSuchElementException e) {
		 }
	}else if(enter[0].toString().equals("id"))
	{
		try{
		
			WebElement sFDEmail=driver.findElement(By.id(enter[2].toString()));
			if(sFDEmail.isDisplayed())	
			  {
				sFDEmail.clear();
				sFDEmail.sendKeys(enter[1].toString());
				flag=1;
			  }
			  else
			  {
				flag=0;
			  }
		
		 }
		 catch (NoSuchElementException e) {
			 flag=0;
		 }
		}
else if(enter[0].toString().equals("css"))
	{
		try{
		
			WebElement sFDEmail=driver.findElement(By.cssSelector(enter[2].toString()));
			if(sFDEmail.isDisplayed())	
			  {
				sFDEmail.clear();
				sFDEmail.sendKeys(enter[1].toString());
				flag=1;
			  }
			  else
			  {
				flag=0;
			  }
		
		 }
		 catch (NoSuchElementException e) {
			 flag=0;
		 }
		}
	return flag;
}
/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: Click_On
 * 
 * Developed By: POLARIS_TEAM 
 * 
 * Functionality Automated: This Function is used to Click on the Web Element Object
 *--------------------------------------------------------------------------------------------------------------------*/
public int Click_On(Object[] enter) throws InterruptedException
{
	int flag = 0;
	if(enter[0].toString().equals("xpath"))
	{
		try{
			Thread.sleep(5000);
			WebElement Obj=driver.findElement(By.xpath(enter[2].toString()));
				
				if(Obj.isDisplayed())
				{
					Obj.click();
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {
		
			
			flag=0;
			

		}
	}else if(enter[0].toString().equals("name"))
	{
		try{
			Thread.sleep(3000);
			WebElement Obj=driver.findElement(By.name(enter[2].toString()));
				text = Obj.getText();
				if(Obj.isDisplayed())
				{
					Obj.click();
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			flag=0;

		}	
	}
	else if(enter[0].toString().equals("id"))
	{
		try{
			WebElement myDynamicElement = (new WebDriverWait(driver, 1000)).until(ExpectedConditions.presenceOfElementLocated(By.id(enter

[2].toString())));
				
			if(myDynamicElement.isDisplayed())
				{
				myDynamicElement.click();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("link"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.linkText(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				Obj.click();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("classname"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.className(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				Obj.click();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
else if(enter[0].toString().equals("css"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.cssSelector(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				Obj.click();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
//validation code
	if(enter[4].toString().equals("xpath"))
	{
		flag=0;
		try{
		WebElement myDynamicElement = (new WebDriverWait(driver, 10000)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(enter

[5].toString())));
			if(myDynamicElement.isDisplayed())
			{
				flag=1;
			}
			else
			{
				flag=0;
			} 
		}catch (NoSuchElementException e) {
			flag=0;

		}return flag;

	}
	if(enter[4].toString().equals("id"))
	{
		flag=0;
		try{	
WebElement myDynamicElement = (new WebDriverWait(driver, 1000)).until(ExpectedConditions.presenceOfElementLocated(By.id(enter[5].toString())));		
			if(myDynamicElement.isDisplayed())
			{
				flag=1;
			}
			else
			{
				flag=0;
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			
		}
	}
	if(enter[4].toString().equals("name"))
	{
		flag=0;
		try{	
			WebElement ws=driver.findElement(By.name(enter[5].toString()));
			if(ws.isDisplayed())
			{
				flag=1;
			}
			else
			{
				flag=0;
			}
		}
		catch (NoSuchElementException e) {
			flag=0;
		}
	}
	if(enter[4].toString().equals("css"))
	{
		flag=0;
		try{	
			WebElement ws=driver.findElement(By.cssSelector(enter[5].toString()));
			if(ws.isDisplayed())
			{
				flag=1;
			}
			else
			{
				flag=0;
			}
		}
		catch (NoSuchElementException e) {
			flag=0;
		}
	}
	return flag;
}


/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: Getwebclientdetails
 *
 *CREATED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to get the Application URL and the driver path.
 * -------------------------------------------------------------------------------------------------------
 */

@SuppressWarnings("rawtypes")
public void Getwebclientdetails(ArrayList webclientURL,ArrayList firefoxpath,ArrayList chromepath,ArrayList chromebinarypath,ArrayList iepath,String browsername, ArrayList imagesexpected, ArrayList imagesactual)
{
	WebclientURLpath=webclientURL.get(0).toString();
	firefoxpathexe=firefoxpath.get(0).toString();
	chromepathexe=chromepath.get(0).toString();
	chromebinarypathexe=chromebinarypath.get(0).toString();
	iepathexe=iepath.get(0).toString();
	browsertype=browsername;
	imexpected=imagesexpected.get(0).toString();
	imactual=imagesactual.get(0).toString();
}


/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: Validate_Search
 * 
 * Developed By: POLARIS_TEAM
 * 
 * Functionality Automated: This Function is used to verify if the Search works as expected
 *--------------------------------------------------------------------------------------------------------------------*/
public int Validate_Search(Object[] enter) throws InterruptedException
{
int flag=0;
String destname = null;
if(enter[0].toString().equals("id"))
{
	try{
		System.out.println("Hi");
		Thread.sleep(2000);
		enteredname=enter[1].toString();
	
	WebElement check3=driver.findElement(By.id(enter[2].toString()));
	destname=check3.getText();
	System.out.println(destname);
try
{
	if(destname.contains(enteredname))
	{
		flag=1;
	}
	
}
catch(Exception e)
{
flag=0;
}
}	catch(NoSuchElementException e){
		flag=0;
		e.printStackTrace();
	}
}
return flag;
}

/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: Validate_Copy
 * 
 * Developed By: POLARIS_TEAM
 * 
 * Functionality Automated: This Function is used to verify if the copy worked as expected
 *--------------------------------------------------------------------------------------------------------------------*/
@SuppressWarnings("unused")
public int Validate_Copy(Object[] enter) throws InterruptedException
{
int flag=0;
String destname = null;
String filename = null;
if(enter[0].toString().equals("xpath"))
{
	try{
		Thread.sleep(2000);
	WebDriverWait wait = new WebDriverWait(driver, 30); 
	wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(enter[2].toString())));
	WebElement check3=driver.findElement(By.xpath(enter[2].toString()));
	destname=check3.getText();
	filename=enter[1].toString();
	if(destname.contains(enter[1].toString()))
	{
		flag=1;
	}
	}
	catch(NoSuchElementException e){
		flag=0;
	}
}
return flag;
}



/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: Contextual_click() 
 *
 *DEVELOPED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to right click on s file/folder.
 * -------------------------------------------------------------------------------------------------------
 */
public int Contextual_click(Object[] enter) throws InterruptedException{
	int flag=0;
	try{
	if(enter[0].toString().equals("xpath")){
		
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(enter[2].toString())));
		Webelement=driver.findElement(By.xpath(enter[2].toString()));
		if(Webelement.isDisplayed()){
			Actions oAction = new Actions(driver);
			oAction.moveToElement(Webelement);
			driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
			oAction.contextClick(Webelement).build().perform();  /* this will perform right click */
			variable = Webelement.getText();
			driver.manage().timeouts().implicitlyWait(70, TimeUnit.SECONDS);
			flag=1;
		}
		else
		{
			flag=0;
		}
	}
	}catch(NoSuchElementException e){
		flag=0;
	}
	return flag;
	
}
/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: validate_delete() 
 *
 *DEVELOPED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to validate delete function.
 * -------------------------------------------------------------------------------------------------------
 */	
public int Validate_Delete(Object[] enter) throws InterruptedException   
{
int flag=0;
String destname;
if(enter[0].toString().equals("xpath"))
{
	try{
		Thread.sleep(2000);
		WebElement check3=driver.findElement(By.xpath(enter[2].toString()));
		destname=check3.getText();
		if(destname.contains(enteredname))
		{
			flag=0;
		}
		else
		{
		
			flag=1;
		}
	}
	catch(NoSuchElementException e){
		flag=0;
	}
}
return flag;
}

/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: Validate_Rename() 
 *
 *DEVELOPED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to validate rename function.
 * -------------------------------------------------------------------------------------------------------
 */
public int Validate_Rename(Object[] enter) throws InterruptedException
{
int flag=0;
String destname;
if(enter[0].toString().equals("xpath"))
{	
	try{
		WebDriverWait wait = new WebDriverWait(driver, 10); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(enter[2].toString())));
		
		WebElement check3=driver.findElement(By.xpath(enter[2].toString()));
		destname=check3.getText();
		if(destname.contains(enteredname))
		{
			flag=1;
		}else{
			flag=0;
		}
		}
	catch(NoSuchElementException e){
		flag=0;
	}
}
	return flag;
}


/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: Validate_Move() 
 *
 *DEVELOPED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to validate Move  function.
 * -------------------------------------------------------------------------------------------------------
 */	
@SuppressWarnings("unused")
public int Validate_Move(Object[] enter) throws InterruptedException{
	{
		int flag=0;
		String destname = null;
		String filename = null;
		if(enter[0].toString().equals("xpath"))
		{
			try{
				Thread.sleep(2000);
			WebDriverWait wait = new WebDriverWait(driver, 30); 
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(enter[2].toString())));
			WebElement check3=driver.findElement(By.xpath(enter[2].toString()));
			destname=check3.getText();
			filename=enter[1].toString();
			if(destname.contains(enter[1].toString()))
			{
				flag=0;
			}
			else
			{
				flag=1;
			}
			}
			catch(NoSuchElementException e){
				flag=0;
			}
		}
		return flag;
	}
}

/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: minmax_app() 
 *
 *DEVELOPED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to minimize and maximize the current browser.
 * -------------------------------------------------------------------------------------------------------
 */	

public int minmax_app(Object[] enter) throws InterruptedException{
	int i=0;
	try{
	driver.manage().window().setPosition(new Point(-2000, 0));
	Thread.sleep(1000);
	driver.manage().window().maximize();
	WebElement title=driver.findElement(By.xpath(enter[2].toString()));
	if(title.isDisplayed()){
		i=1;
	}else
	{
		i=0;
	}
	}
	catch (NoSuchElementException e) {
		i=0;
	}
	return i;
	
}


/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: times
 *
 *CREATED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to return the time stamp of the execution.
 * -------------------------------------------------------------------------------------------------------
 */

public Timestamp times()
{
	Date d = new Date();
	Timestamp t=new java.sql.Timestamp(d.getTime());
	return t;
}

/*--------------------------------------------------------------------------------------------------------
 *FUNCTION NAME: TearDown
 *
 *CREATED BY: POLARIS_TEAM
 *
 *FUNCTIONALITY INVOLVED: This function is used to quit the Driver instance.
 * -------------------------------------------------------------------------------------------------------
 */

public void teardown(){  
	
    driver.quit();
}

/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: verifylabel
 * 
 * Developed By: POLARIS_TEAM 
 * 
 * Functionality Automated: This Function is used to verify the filed is displayed or not and also verify the label name
 *--------------------------------------------------------------------------------------------------------------------*/
public int verifylabel(Object[] enter) throws InterruptedException
{
	int flag = 0;
	if(enter[0].toString().equals("xpath"))
	{
		try{
			WebElement Obj=driver.findElement(By.xpath(enter[2].toString()));
			String expected=enter[1].toString();
			if(Obj.isDisplayed())
			{
				String actual=Obj.getText();
				if(expected.equals(actual))
				{				
					flag=1;			
				}
			}
			}
		catch (NoSuchElementException e) {
			flag=0;
		}
	}else if(enter[0].toString().equals("name"))
	{
		try{
			WebElement Obj=driver.findElement(By.name(enter[2].toString()));
			String expected=enter[1].toString();
			if(Obj.isDisplayed())
			{
				String actual=Obj.getText();
				if(expected.equals(actual))
				{
					
					flag=1;			
				}
			}
			}
		catch (NoSuchElementException e) {
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			flag=0;

		}	
	}
	else if(enter[0].toString().equals("id"))
	{
		try{
			WebElement Obj=driver.findElement(By.id(enter[2].toString()));
			String expected=enter[1].toString();
			if(Obj.isDisplayed())
			{
				String actual=Obj.getText();
				if(expected.equals(actual))
				{
					flag=1;			
				}
			}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("link"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.linkText(enter[2].toString()));	
			String expected=enter[1].toString();
			if(Obj.isDisplayed())
			{
				String actual=Obj.getText();
				if(expected.equals(actual))
				{
					flag=1;			
				}
			}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("classname"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.className(enter[2].toString()));	
			String expected=enter[1].toString();
			if(Obj.isDisplayed())
			{
				String actual=Obj.getText();
				if(expected.equals(actual))
				{
					flag=1;			
				}
			}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
		else if(enter[0].toString().equals("css"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.cssSelector(enter[2].toString()));	
			String expected=enter[1].toString();
			if(Obj.isDisplayed())
			{
				String actual=Obj.getText();
				if(expected.equals(actual))
				{
					flag=1;			
				}
			}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
	return flag;
}

/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: Mouse_Over
 * 
 * Developed By: POLARIS_TEAM 
 * 
 * Functionality Automated: This Function is used to MOUSE OVER ON THE ELEMENT
 *--------------------------------------------------------------------------------------------------------------------*/
public int Mouse_Over(Object[] enter) throws InterruptedException
{
	int flag = 0;
	if(enter[0].toString().equals("xpath"))
	{
		try{
		
			Actions action = new Actions(driver);
			WebElement Obj=driver.findElement(By.xpath(enter[2].toString()));
				if(Obj.isDisplayed())
				{
					action.moveToElement(Obj).build().perform();
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {
			flag=0;
		}
	}else if(enter[0].toString().equals("name"))
	{
		try{
			Thread.sleep(3000);
			Actions action = new Actions(driver);
			WebElement Obj=driver.findElement(By.name(enter[2].toString()));
				if(Obj.isDisplayed())
				{
					action.moveToElement(Obj).build().perform();
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			flag=0;

		}	
	}
	else if(enter[0].toString().equals("id"))
	{
		try{
			Actions action = new Actions(driver);
			WebElement Obj = (new WebDriverWait(driver, 1000)).until(ExpectedConditions.presenceOfElementLocated(By.id(enter[2].toString())));
			if(Obj.isDisplayed())
				{
				action.moveToElement(Obj).build().perform();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("link"))
	{
		try{
			Actions action = new Actions(driver);
			WebElement Obj=driver.findElement(By.linkText(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				action.moveToElement(Obj).build().perform();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("classname"))
	{
		try{
			Actions action = new Actions(driver);
			WebElement Obj=driver.findElement(By.className(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				action.moveToElement(Obj).build().perform();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
	else if(enter[0].toString().equals("css"))
	{
		try{
			Actions action = new Actions(driver);
			WebElement Obj=driver.findElement(By.cssSelector(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				action.moveToElement(Obj).build().perform();
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
//validation code
	if(enter[4].toString().equals("xpath"))
	{
		flag=0;
		try{
		WebElement myDynamicElement = (new WebDriverWait(driver, 10000)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(enter

[5].toString())));
			if(myDynamicElement.isDisplayed())
			{
				flag=1;
			}
			else
			{
				flag=0;
			} 
		}catch (NoSuchElementException e) {
			flag=0;

		}return flag;

	}
	if(enter[4].toString().equals("css"))
	{
		flag=0;
		try{	
WebElement myDynamicElement = (new WebDriverWait(driver, 1000)).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(enter[5].toString())));	

	
			if(myDynamicElement.isDisplayed())
			{
				flag=1;
			}
			else
			{
				flag=0;
			}
		}
		catch (NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			
		}
	}
	if(enter[4].toString().equals("name"))
	{
		flag=0;
		try{	
			WebElement ws=driver.findElement(By.name(enter[5].toString()));
			if(ws.isDisplayed())
			{
				flag=1;
			}
			else
			{
				flag=0;
			}
		}
		catch (NoSuchElementException e) {
			flag=0;
		}
	}
	return flag;
}
/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: verifyTitle
 * 
 * Developed By: POLARIS_TEAM 
 * 
 * Functionality Automated: This Function is used to verify Title of the page
 *--------------------------------------------------------------------------------------------------------------------*/
public int VerifyTitle(Object[] enter) throws InterruptedException
{
	int flag = 0;
	
	String expected=enter[1].toString();
	String actual=driver.getTitle();
	System.out.format ("EXPECTED: %s, ACTUAL: %s", expected, actual);
	
	//if(expected.contains(actual))
	if (expected.toString().trim().equalsIgnoreCase(actual)) { //Theyagu - added trim			
			flag=1;			
	}
	return flag;
}
/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: Select_Value
 * 
 * Developed By: POLARIS_TEAM 
 * 
 * Functionality Automated: This Function is used to Select Value from the dropdown
 *--------------------------------------------------------------------------------------------------------------------*/
public int Select_Value(Object[] enter) throws InterruptedException
{
	int flag = 0;
	if(enter[0].toString().equals("xpath"))
	{
		try{
			//Select dropdown=new Select(driver.findElement(By.xpath(enter[2].toString())));
			//dropdown.selectByValue(enter[1].toString());
			WebElement Obj=driver.findElement(By.xpath(enter[2].toString()));
			Select dropdown=new Select(Obj);	
				if(Obj.isDisplayed())
				{
					dropdown.selectByValue(enter[1].toString());
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {		
				flag=0;
			}
	}else if(enter[0].toString().equals("name"))
	{
		try{
			Thread.sleep(3000);
			WebElement Obj=driver.findElement(By.name(enter[2].toString()));
			Select dropdown=new Select(Obj);	
				if(Obj.isDisplayed())
				{
					dropdown.selectByValue(enter[1].toString());
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
			flag=0;

		}	
	}
	else if(enter[0].toString().equals("id"))
	{
		try{
			WebElement myDynamicElement = (new WebDriverWait(driver, 1000)).until(ExpectedConditions.presenceOfElementLocated(By.id(enter[2].toString())));
			Select dropdown=new Select(myDynamicElement);
			if(myDynamicElement.isDisplayed())
				{
				dropdown.selectByValue(enter[1].toString());
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("link"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.linkText(enter[2].toString()));
			Select dropdown=new Select(Obj);	
			if(Obj.isDisplayed())
				{
				dropdown.selectByValue(enter[1].toString());
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("classname"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.className(enter[2].toString()));
			Select dropdown=new Select(Obj);
			if(Obj.isDisplayed())
				{
				dropdown.selectByValue(enter[1].toString());
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
	else if(enter[0].toString().equals("css"))
	{
		try{
			Thread.sleep(3000);
			WebElement Obj=driver.findElement(By.cssSelector(enter[2].toString()));
			Select dropdown=new Select(Obj);
			if(Obj.isDisplayed())
				{
				dropdown.selectByVisibleText(enter[1].toString());
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
	return flag;
}
/*---------------------------------------------------------------------------------------------------------------------
 * Function Name: ScrollBar
 * 
 * Developed By: POLARIS_TEAM 
 * 
 * Functionality Automated: This Function is used to scroll the page or item
 *--------------------------------------------------------------------------------------------------------------------*/
public int ScrollBar(Object[] enter) throws InterruptedException
{
	int flag = 0;
	if(enter[0].toString().equals("xpath"))
	{
		System.out.println("Find Me1");
		try{
				WebElement Obj=driver.findElement(By.xpath(enter[2].toString()));			
				if(Obj.isDisplayed())
				{
					System.out.println("Find Me2");
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Obj);
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {
			flag=0;
		}
	}else if(enter[0].toString().equals("name"))
	{
		try{
			
			WebElement Obj=driver.findElement(By.name(enter[2].toString()));
				if(Obj.isDisplayed())
				{
					((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Obj);
					flag=1;			
				}
			}
		catch (NoSuchElementException e) {
			flag=0;
		}	
	}
	else if(enter[0].toString().equals("id"))
	{
		try{
			WebElement Obj = (new WebDriverWait(driver, 1000)).until(ExpectedConditions.presenceOfElementLocated(By.id(enter[2].toString())));
			if(Obj.isDisplayed())
				{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Obj);
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("link"))
	{
		try{
			WebElement Obj=driver.findElement(By.linkText(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Obj);
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	else if(enter[0].toString().equals("classname"))
	{
		try{
			WebElement Obj=driver.findElement(By.className(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Obj);
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			e.printStackTrace();
			flag=0;
			}
	}
	else if(enter[0].toString().equals("css"))
	{
		try{
			WebElement Obj=driver.findElement(By.cssSelector(enter[2].toString()));	
			if(Obj.isDisplayed())
				{
				((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", Obj);
				flag=1;
				}
				else
				{
					flag=0;
				}
			}
		catch(NoSuchElementException e) {
			flag=0;
			}
	}
	return flag;
}

public int processImage(Object[] enter) {
	
	 int flag=0;     
try {
	if(enter[0].toString().equals("xpath"))
	{
	imageUrl=driver.findElement(By.xpath(enter[2].toString())).getAttribute("src");
	}
	else if(enter[0].toString().equals("id"))
	{
		imageUrl=driver.findElement(By.id(enter[2].toString())).getAttribute("src");	
	}
	else if(enter[0].toString().equals("css"))
	{
		imageUrl=driver.findElement(By.cssSelector(enter[2].toString())).getAttribute("src");	
	}
	else if(enter[0].toString().equals("name"))
	{
		imageUrl=driver.findElement(By.name(enter[2].toString())).getAttribute("src");	
	}
	else if(enter[0].toString().equals("classname"))
	{
		imageUrl=driver.findElement(By.className(enter[2].toString())).getAttribute("src");	
	}
   	 URL url = new URL(imageUrl); 
	 BufferedImage image = ImageIO.read(url); 
	   actualimage= imactual + File.separator + enter[6].toString();
	    File f1=new File(actualimage);
	    ImageIO.write(image, "JPEG", f1);
	    String expectedimage = imexpected + File.separator + enter[1].toString();
	    Image image1 = Toolkit.getDefaultToolkit().getImage(expectedimage);
	    Image image2 = Toolkit.getDefaultToolkit().getImage(actualimage);
 
PixelGrabber grab1 =new PixelGrabber(image1, 0, 0, -1, -1, false);
PixelGrabber grab2 =new PixelGrabber(image2, 0, 0, -1, -1, false);
 
int[] data1 = null;
 
if (grab1.grabPixels()) {
int width = grab1.getWidth();
int height = grab1.getHeight();
data1 = new int[width * height];
data1 = (int[]) grab1.getPixels();
}
 
int[] data2 = null;
 
if (grab2.grabPixels()) {
int width = grab2.getWidth();
int height = grab2.getHeight();
data2 = new int[width * height];
data2 = (int[]) grab2.getPixels();
}

if(java.util.Arrays.equals(data1, data2))
{
	flag=1;
}
} catch (InterruptedException e1) {
	e1.printStackTrace();
	flag=0;
} catch (IOException e1) {
	e1.printStackTrace();
	flag=0;
}
return flag;
}
}