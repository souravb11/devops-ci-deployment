
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.text.AttributeSet;
import javax.swing.text.Element;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLDocument;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
//Theyagu
import java.util.Hashtable;
//-----------------

public class Driver {
	static String ConfigFileName;
	static ArrayList FileName_Suite = new ArrayList();
	static ArrayList FileName_TestScript = new ArrayList();
	static ArrayList FileName_TestResults = new ArrayList();
	static ArrayList summaryURL = new ArrayList();
	static ArrayList URLLink = new ArrayList();
	static ArrayList summaryHTMLPath = new ArrayList();
	static ArrayList ExecutedTestCaseResults = new ArrayList();
	static ArrayList PerTestCaseResult = new ArrayList();
	static ArrayList screenshotpath = new ArrayList();
	static ArrayList TestCase_Name = new ArrayList();
	static ArrayList Function_Name = new ArrayList();
	static ArrayList SummaryTestCaseID=new ArrayList();
	static ArrayList SummaryTestCaseID1=new ArrayList();
	static ArrayList SummaryObjective=new ArrayList();
	static ArrayList SummaryStatus=new ArrayList();
	static ArrayList imagesexpected=new ArrayList();
	static ArrayList imagesactual=new ArrayList();
	static ArrayList webclientURL=new ArrayList();
	static ArrayList firefoxpath=new ArrayList();
	static ArrayList chromepath=new ArrayList();
	static ArrayList chromebinarypath=new ArrayList();
	static ArrayList iepath=new ArrayList();
	
	static ArrayList TestDataFilePath=new ArrayList();	//Theyagu
	static ArrayList alTestCases=new ArrayList();	//Theyagu	
	static ArrayList Iteration=new ArrayList();	//Theyagu
	
	static CSVReader csvReader;
	static CSVWriter csv;
	
	static String rows[]=null;
	static String rowing[]=null;
	static String env=null;
	static String runningContainer=null;
	static String runningBrowser=null;
	static String testtype=null;
	static Method method=null;
	
	static FileWriter writer;
	
	static StringBuilder htmlBuilder2[] = new StringBuilder[100];
	static StringBuilder htmlBuilder1=new StringBuilder();
	static StringBuilder htmlBuilderFull=new StringBuilder();
	static StringBuilder htmlBuilderIndividual[]=new StringBuilder[100];
	
	static Library dl=new Library();	
	
	static Utility utilityClass = new Utility(); //Theyagu
	static String strTestDataFile=null; //Theyagu
	static int g_intTotalTC=0; //Theyagu
	
	static int n=0;
	static String StepCount=null,summaryHTML=null,ExecutedTestCaseResultsURL=null,Scpath=null;
	
	static ArrayList TestCaseNameContainer=new ArrayList();
	static ArrayList TestCaseStatusContainer=new ArrayList();
	
	static int iStepPassCount=0,iStepFailCount=0,iOverallPasscount=0,iOverallFailCount=0,iOverAllExecuted=0;
	
    String workingdirectory, subdirectory;
	static String OS = System.getProperty ("os.name");
		
	//static CSVReader objCSVReader;	
	//public static String strFilepath = "D:\\QATAR_AUTOMATION\\WebClient\\Smoke_Test\\Functional_driver\\Test_Data_Master.csv";	
	//-----
	
	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: main
	 * 
	 * Functionality: This Function Drives the Execution
	 * 
	 * Developed By: Polaris_Testting_Team
	 * 
	 -----------------------------------------------------------------------------------------------------------*/
	public static void main(final String args[]) throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException,
    UnsupportedLookAndFeelException {
UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
SwingUtilities.invokeLater(new Runnable() {

    
	@Override
    public void run() {
		
		env=args[0];
		//env="webclient"; //Theyagu
		testtype=args[1];
		//testtype="smoke"; //Theyagu
		String s = null;
		int exittime=0;
		try{
			runningContainer=args[2];			
			//dl.iosdevicename=runningContainer;
		}
		catch(ArrayIndexOutOfBoundsException e){
			runningContainer=null;
		}
		
		try{
			runningBrowser=args[2];
			//runningBrowser="ie"; //Theyagu
			//dl.iosdevicever=args[3];
		}
		catch(ArrayIndexOutOfBoundsException e){
			runningBrowser=null;
		}
		String directory= System.getProperty("user.dir");
		String subdir=directory.substring(0, directory.length()-17);
		//String subdir = "D:\\QATAR_AUTOMATION\\"; //Theyagu
		
		ConfigFileName=subdir+File.separator+"Application"+File.separator+"Configuration.csv";
		//ConfigFileName="C:\\CrossBrowserAutomation\\Application\\Configuration.csv";
		
		GetFileNames();	
		
		//** Get TD records for Smoke/Functional/Regression - Theyagu **
		try{
			
			strTestDataFile = TestDataFilePath.get(0).toString();			
			utilityClass.getCSVResultSet(strTestDataFile); //Fetches CSV result set
			alTestCases = utilityClass.alTCIteration; //Fetches the TC+TD combo in ArrayList for getting Iteration count
			
			if (utilityClass.hashRS.isEmpty()==true){				
				System.out.println("Empty Records in the CSV DB");
			}
		} catch (IOException e){
			e.printStackTrace();
		}
		//----------------------------------------------------------------------------- 
		
        try {
			new Driver().testing();
			
		} catch (NullPointerException e) {
		} catch (SecurityException e) {
		} catch (IllegalArgumentException e) {
		}
    	new Driver().initUI();
    }
});
}
	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: testing
	 * 
	 * Functionality: This Function Drives the Execution
	 * 
	 * Developed By: Polaris_Testing_Team
	 * 
	 -----------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public void testing() {
		
		int filerem=0;
		String s = null;
		String TestCase = null;
		int intIteration = 0; // Theyagu
		int iterator = 0; // Theyagu
		
		
		for(int i=0;i<FileName_Suite.size();i++)
		{			
								
				GetTestCases(i);
								
					// CREATING HEADER in EXECUTION SUMMARY HTML
				
					htmlBuilderFull.append("<html><body>");
					htmlBuilderFull.append("<CENTER>");
					htmlBuilderFull.append("<H1>WEB CROSS BROWSER AUTOMATION</H1>");
					htmlBuilderFull.append("<H3>WEB CROSS BROWSER AUTOMATION-EXECUTED TEST CASES RESULTS</H3>");		
					htmlBuilderFull.append("<table BORDER=2 CELLPADDING=0 CELLSPACING=0 WIDTH=100% HEIGHT=50%>");
					htmlBuilderFull.append("<tr>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>TEST CASE ID</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>TEST CASE NAME</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>ROLE</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>OBJECTIVE</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>STEP NO</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>DESCRIPTION</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>EXPECTED RESULT</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>STEP STATUS UPDATED BY SCRIPT</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>SCREEN SHOT UPDATED BY SCRIPT</CENTER></td>");
					htmlBuilderFull.append("<td bgcolor=DarkGray><CENTER>TIME STAMP</CENTER></td>");
					htmlBuilderFull.append("</tr>");	
						
					for(int j=0;j<TestCase_Name.size();j++) 
					{
						//TO FETCH ITERATION COUNT						
						intIteration = utilityClass.getIterationCount(alTestCases, TestCase_Name.get(j).toString()); //Getting iteration count
						
						if (Iteration.get(j).toString().equalsIgnoreCase("Y")) {
							if ((intIteration >= 2)!= true){
								intIteration = 1;					
							}
						} else {
							intIteration = 1;
						}
						
						for (iterator=1; iterator<=intIteration; iterator++) // Theyagu - Test case iteration Loop begins
						{ 
							iOverAllExecuted++; //Theyagu - Total Executed Count counter							
							
							// CREATING HEADER in INDIVIDUAL TC HTML
							
							htmlBuilder2[n]=new StringBuilder(); 
							htmlBuilder2[n].append("<html><body>");
							htmlBuilder2[n].append("<CENTER>");
							htmlBuilder2[n].append("<H1>WEB CROSS BROWSER AUTOMATION</H1>");
							htmlBuilder2[n].append("<H3>WEB CROSS BROWSER AUTOMATION-SMOKE TEST RESULTS</H3>");
							htmlBuilder2[n].append("<table BORDER=2 CELLPADDING=0 CELLSPACING=0 WIDTH=100% HEIGHT=50%>");
							htmlBuilder2[n].append("<tr>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>TEST CASE ID</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>TEST CASE NAME</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>ROLE</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>OBJECTIVE</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>STEP NO</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>DESCRIPTION</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>EXPECTED RESULT</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>STEP STATUS UPDATED BY SCRIPT</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>SCREEN SHOT UPDATED BY SCRIPT</CENTER></td>");
							htmlBuilder2[n].append("<td bgcolor=DarkGray><CENTER>TIME STAMP</CENTER></td>");
							htmlBuilder2[n].append("</tr>");
						
							int k=ExecuteTestCase(TestCase_Name.get(j),j,i,iterator); //Theyagu - Added param iterator
							
							TestCase=TestCase_Name.get(j).toString()+"_"+String.valueOf(iterator); //Theyagu - Appended iterator					
							TestCaseNameContainer.add(TestCase_Name.get(j).toString()+"_"+String.valueOf(iterator)); //Theyagu - Appended iterator
							TestCaseStatusContainer.add(k);
							
							// WRITING EXECUTION DETAILS in INDIVIDUAL TC HTML
							try
							{
								writer = new FileWriter(PerTestCaseResult.get(0)+TestCase+".html");
						    	writer.write(htmlBuilder2[n].toString());
						    	writer.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							// WRITING EXECUTION DETAILS in EXECUTION SUMMARY HTML
							try
							{
								writer = new FileWriter(ExecutedTestCaseResults.get(0).toString());
						    	writer.write(htmlBuilderFull.toString());
						    	writer.close();
							} catch (IOException e) {
								e.printStackTrace();
							}
							htmlBuilder2[n].append("</table>");
							htmlBuilder2[n].append("</CENTER>");
							htmlBuilder2[n].append("</body></html>\n");
							n=n+1;
						} //Iteration Loop Ends
					}
					
					try 
					{
						int p=0;
						int k = 0;
						csvReader =new CSVReader(new FileReader((String) FileName_Suite.get(0)),',', '\'', 1);
						while((rows = csvReader.readNext()) != null) {
							SummaryTestCaseID1.add(rows[0].toString());
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					// CREATING SUMMARY DETAILS HEADER CREATION
					
					htmlBuilderFull.append("</table>");
					htmlBuilderFull.append("</CENTER>");
					htmlBuilderFull.append("</body></html>\n");					
					//iOverAllExecuted=TestCase_Name.size(); Theyagu
					iOverallPasscount=iOverAllExecuted-iOverallFailCount;
					
					htmlBuilder1.append("<html><body>");
					htmlBuilder1.append("<head>");
					htmlBuilder1.append("<script>function changetext(id) {id.innerHTML=Ooops!;}</script>");
					htmlBuilder1.append("</head>");
					htmlBuilder1.append("<body>");
					htmlBuilder1.append("<CENTER>");
					htmlBuilder1.append("<H1>WEB CROSS BROWSER AUTOMATION</H1>");
					htmlBuilder1.append("<table FONT SIZE=-50 BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=60% HEIGHT=3%>");
			    	htmlBuilder1.append("<tr>");
			    	htmlBuilder1.append("<td>Project</td>");
			    	htmlBuilder1.append("<td> WEB - Cross Browser Automation Project</td>");
			    	htmlBuilder1.append("</tr>");
			    	htmlBuilder1.append("<tr>");
			    	htmlBuilder1.append("<td>Test Type</td>");
			    	htmlBuilder1.append("<td>Smoke Test Results</td>");
			    	htmlBuilder1.append("</tr>");
			    	htmlBuilder1.append("<tr>");
					htmlBuilder1.append("<td>Browser</td>");
			    	htmlBuilder1.append("<td>"+dl.browsertype+"</td>");
			    	htmlBuilder1.append("</tr>");
			    	htmlBuilder1.append("<tr>");
			    	htmlBuilder1.append("<td bgcolor=#6AA121>Total No of TestCases</td>");
			    	//htmlBuilder1.append("<td>"+SummaryTestCaseID1.size()+"</td>");
			    	htmlBuilder1.append("<td>"+TestCase_Name.size()+"</td>"); // Theyagu
			    	htmlBuilder1.append("</tr>");
			    	htmlBuilder1.append("<tr>");
			    	htmlBuilder1.append("<td bgcolor=#6AA121>PASS</td>");
			    	htmlBuilder1.append("<td>"+iOverallPasscount+"</td>");			    	
			    	htmlBuilder1.append("</tr>");
			    	htmlBuilder1.append("<tr>");
			    	htmlBuilder1.append("<td bgcolor=Red>FAIL</td>");
			    	htmlBuilder1.append("<td>"+iOverallFailCount+"</td>");			    	
			    	htmlBuilder1.append("</tr>");
			    	htmlBuilder1.append("<tr>");
			    	htmlBuilder1.append("<td bgcolor=DarkGray>TOTAL EXECUTED</td>");
			    	htmlBuilder1.append("<td>"+iOverAllExecuted+"</td>");			    	
			    	htmlBuilder1.append("</tr>");
			    	htmlBuilder1.append("<tr>");
			    	htmlBuilder1.append("<td bgcolor=DarkGray>TOTAL EXECUTED RESULTS FILE</td>");
			    	htmlBuilder1.append("<td><a href=file:///"+ExecutedTestCaseResultsURL+" target=_blank>Executed TestCase Results</a></td>");
			    	htmlBuilder1.append("</tr>");			    	
			    	htmlBuilder1.append("</table>");
			    	
			    	// WRITING SUMMARY DETAILS HEADER in SUMMARY.HTML
			    	try {
			    		
						writer = new FileWriter(summaryHTMLPath.get(0).toString());
				    	writer.write(htmlBuilder1.toString());
				    	writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					// CREATING TC EXECUTION DETAILS HEADER in SUMMARY.HTML
					
			    	htmlBuilder1.append("<h3><u><CENTER>TEST CASE STATUS</CENTER></u></h3>");
			    	htmlBuilder1.append("<table FONT SIZE=-2 BORDER=1 CELLPADDING=0 CELLSPACING=0 WIDTH=90%>");
			    	htmlBuilder1.append("<CENTER>");
					htmlBuilder1.append("<tr>");
					htmlBuilder1.append("<td FONT SIZE=-2 bgcolor=Yellow><B>TEST CASE ID</B></td>");
					htmlBuilder1.append("<td FONT SIZE=-2 bgcolor=Yellow><B>OBJECTIVE</B></td>");
					htmlBuilder1.append("<td FONT SIZE=-2 bgcolor=Yellow><B>STATUS</B></td>");
					htmlBuilder1.append("</tr>");
					
					try {
						int p=0;
						int k = 0;
						csvReader =new CSVReader(new FileReader((String) FileName_Suite.get(0)),',', '\'', 1);
						while((rowing = csvReader.readNext()) != null) {
							if(env.equals("ios")){
							SummaryTestCaseID.add(rowing[0].toString().substring(1,rowing[0].toString().length()-1));
							}
							else
							{
								SummaryTestCaseID.add(rowing[0].toString());
							}
							SummaryObjective.add(rowing[1].toString());
							SummaryStatus.add("NOTEXECUTED");
						}
						for(p=0;p<TestCaseNameContainer.size();p++)
		    			{
							for(int y=0;y<SummaryTestCaseID.size();y++)
							{
			    				//if(TestCaseNameContainer.get(p).toString().toUpperCase().equals(SummaryTestCaseID.get(y).toString().toUpperCase()))
								if(TestCaseNameContainer.get(p).toString().toUpperCase().startsWith(SummaryTestCaseID.get(y).toString().toUpperCase())) // Theyagu - comparison logic
			    				{
			    					
									htmlBuilder1.append("<tr>");			    					
			    					htmlBuilder1.append("<td FONT SIZE=-2><a href=file:///"+URLLink.get(0).toString()+TestCaseNameContainer.get(p).toString().toUpperCase()+".html target=_blank>"+TestCaseNameContainer.get(p).toString().toUpperCase()+"</a></td>");
			    					htmlBuilder1.append("<td FONT SIZE=-2>"+SummaryObjective.get(y).toString()+"</td>");
			    					
			    					if(Integer.parseInt(TestCaseStatusContainer.get(p).toString())==1)
			    					{
			    						htmlBuilder1.append("<td FONT SIZE=-1>PASS</td>");
			    					}
			    					else if(Integer.parseInt(TestCaseStatusContainer.get(p).toString())==0||Integer.parseInt(TestCaseStatusContainer.get(p).toString())==2)
			    					{
			    						htmlBuilder1.append("<td FONT SIZE=-1>FAIL</td>");
			    					}	
			    					htmlBuilder1.append("</tr>");
			    				}
							}
		    			}
						htmlBuilder1.append("</CENTER>");
						htmlBuilder1.append("</table>");
						htmlBuilder1.append("</CENTER>");
						htmlBuilder1.append("</body></html>\n");
						
						try {
							writer = new FileWriter(summaryHTMLPath.get(0).toString());
					    	writer.write(htmlBuilder1.toString());
					    	writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					try {
						writer = new FileWriter(summaryHTMLPath.get(0).toString());
				    	writer.write(htmlBuilder1.toString());
				    	writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				
					if(OS.contains("Mac")){}
					else
					{
						killallprocess();
					}						
		}
	}
	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: ExecuteTestCase
	 * 
	 * Functionality: This Function is used to Execute Test Case
	 * 
	 * Developed By: Polaris_Testing_Team
	 * 
	 -----------------------------------------------------------------------------------------------------------*/
	public static int ExecuteTestCase(Object object,int j,int i,int iterator){ //Theyagu added iterator param

		int StepNo=2;
		int LineNo=1;
		int flag=1;
		int counter=0;
		String strTCName=null; //Theyagu
		
		try {			
			csvReader = new CSVReader(new FileReader((String) FileName_TestScript.get(i)));			
			while((rows = csvReader.readNext()) != null) {
				StepCount = rows[4];
				if(rows[0].equals(object))
				{
					try {
						
						strTCName = rows[0].toString().toUpperCase(); //Theyagu -  Saving TC name in a common variable
						method = dl.getClass().getMethod(rows[7].toString(), Object[].class);
						Object[] ex = new Object[8];
						
					    ex[0] = rows[8];
					    
					    ex[1] = rows[9];
					    
					    ex[2] = rows[10];
					    
					    ex[3] = rows[11];
					    
					    ex[4]=rows[12];
					    
					    ex[5]=rows[13];
					    
					    ex[6]=rows[14];
					    
					    ex[7]=rows[15];					    
					    					    
						if(flag==1){
							flag=Integer.parseInt(method.invoke(dl,(Object)ex).toString());
						}
						if(flag==1 || flag==0||flag==2)
						{
		
							if(flag==0){
								iOverallFailCount=iOverallFailCount+1;
								flag=2;
								
							}
							if(flag==2)
							{
								counter=counter+1;
							}
							htmlwriter(object,rows,LineNo,StepNo,flag,Integer.parseInt(StepCount),counter,iterator); //Theyagu - Added iterator param 
						}
						
					} catch (IllegalAccessException e) {
						
					} catch (IllegalArgumentException e) {
						
					} catch (InvocationTargetException e) {
					
					} catch (NoSuchMethodException e) {
						
					} catch (SecurityException e) {
					
					}	
					while((rows = csvReader.readNext()) != null)
					{
						try{
							StepCount = rows[4];
						}
						catch(NullPointerException e){
							break;
						}
						if(Integer.parseInt(StepCount)==StepNo)
						{
							StepNo=StepNo+1;
							try {
								
								method = dl.getClass().getMethod(rows[7].toString(), Object[].class);
																
								Object[] ex = new Object[8];
								
							    ex[0] = rows[8];
							    
							    ex[1] = rows[9];							    
							    
							    //Master Test Data Value - Theyagu					    
								String strKey = strTCName+"TD_"+String.valueOf(iterator)+rows[9];	
								if (utilityClass.hashRS.get(strKey)!=null){									
							    	ex[1] = utilityClass.hashRS.get(strKey).toString();
							    }
							    
							    ex[2] = rows[10];
							    
							    ex[3] = rows[11];
							    
							    ex[4]=rows[12];
							    
							    ex[5]=rows[13];	
							    
							    ex[6]=rows[14];
							    
							    ex[7]=rows[15];							    
							    
							    if(flag==1){
							    	flag=Integer.parseInt(method.invoke(dl,(Object)ex).toString());
							    }
							    if(flag==1 || flag==0 || flag==2)
								{
									
									if(flag==0){
										iOverallFailCount=iOverallFailCount+1;
										flag=2;
										
									}
									if(flag==2)
									{
										counter=counter+1;
									}
									htmlwriter(object,rows,LineNo,StepNo,flag,Integer.parseInt(StepCount),counter,iterator); //Theyagu - Added iterator param
								}
								
							} catch (IllegalAccessException e) {
								
							} catch (IllegalArgumentException e) {
								
							} catch (InvocationTargetException e) {
							e.printStackTrace();	
							}
							
							catch (NoSuchMethodException e) {
								
							} catch (SecurityException e) {
								
							}
						}
						else
						{
							break;
						}
					}
					
				}
			}
	
		} catch (IOException e) {
			
		}
		return flag;
	}
	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: htmlwriter
	 * 
	 * Functionality: This Function is used to write the detailed results
	 * 
	 * Developed By: Polaris_Testing_Team
	 * 
	 -----------------------------------------------------------------------------------------------------------*/

	public static void htmlwriter(Object ObjTC,String[] rows2,int LineNo, int StepNo,int flag,int StepCount,int counter,int iterator) { //Theyagu - added iterator param
				
		String sTCID = null,sTestCaseName=null,sRole=null,sObjective=null,sStepNo=null,sDesc=null,sExpRes=null,sStepStatus=null;
		File afile;
		try
		{
		sTCID=rows2[0]+"["+String.valueOf(iterator)+"]"; //Theyagu - Appending the iterator
		sTestCaseName=rows2[1];
		sRole=rows2[2];
		sObjective=rows2[3];
		sStepNo=rows2[4];
		sDesc=rows2[5];
		sExpRes=rows2[6];
			if(flag==1 && StepCount<StepNo){
				sStepStatus="Pass";
				iStepPassCount=iStepPassCount+1;
				Scpath="No ScreenShot";
			}
			else if((flag==0||flag==2) && StepCount<StepNo && counter<2)
			{				 
				sStepStatus="Fail";
				iStepFailCount=iStepFailCount+1;
				if(env.contains("ios")){
					WebDriver augmentedDriver = new Augmenter().augment(dl.driver);
				    afile = ((TakesScreenshot)augmentedDriver).getScreenshotAs(OutputType.FILE);
				    dl.teardown();
				}
				else
				{
					 afile = ((TakesScreenshot)dl.driver).getScreenshotAs(OutputType.FILE);
				}
				try {
					FileUtils.copyFile(afile,new File(screenshotpath.get(0).toString() + ObjTC.toString() + "\\"+ ObjTC.toString() + "step" + (StepNo-1) + ".png"));
				} catch (IOException e) {
					
					e.printStackTrace();
				}
				Scpath="file:///"+screenshotpath.get(0).toString() + ObjTC.toString() + "\\"+ ObjTC.toString() + "step" + (StepNo-1) + ".png";
			}
		}
		catch(NullPointerException e){
			
		}
		htmlBuilder2[n].append("<tr>");
    	if(sTCID!="")
    	{
    		htmlBuilder2[n].append("<td><A ID="+sTCID.toUpperCase()+"><FONT SIZE=-2>"+sTCID.toUpperCase()+"</FONT></A></td>");
    	}
    	else
    	{
    		htmlBuilder2[n].append("<td></td>");
    	}
    	htmlBuilder2[n].append("<td><FONT SIZE=-2>"+sTestCaseName+"</FONT></td>");
    	htmlBuilder2[n].append("<td><FONT SIZE=-2>"+sRole+"</FONT></td>");
    	htmlBuilder2[n].append("<td><FONT SIZE=-2>"+sObjective+"</FONT></td>");
    	htmlBuilder2[n].append("<td width=-100%><FONT SIZE=-2>"+sStepNo+"</FONT></td>");
    	htmlBuilder2[n].append("<td><FONT SIZE=-2>"+sDesc+"</FONT></td>");
    	htmlBuilder2[n].append("<td><FONT SIZE=-2>"+sExpRes+"</FONT></td>");
    	if(counter>=2){
    		sStepStatus="Not Executed";
    	}
    	htmlBuilder2[n].append("<td><FONT SIZE=-2>"+sStepStatus+"</FONT></td>");
    	if(Scpath!=null){
    	if(Scpath.contains("png"))
    	{	
    		htmlBuilder2[n].append("<td><FONT SIZE=-2><a href="+Scpath+" target=_blank>Screen Shot</FONT></td>");
    	}
    	else if(Scpath.contains("No") && Scpath!=null)
    	{
    		htmlBuilder2[n].append("<td><FONT SIZE=-2>"+Scpath+"</FONT></td>");
    	}
    	if(counter>=2)
    	{
    		htmlBuilder2[n].append("<td><FONT SIZE=-2>Not Executed</FONT></td>");
    	}
    	}
    	else if(Scpath==null){
    		htmlBuilder2[n].append("<td><FONT SIZE=-2>NULL</FONT></td>");
    	}
    	if(counter>=2){
    		htmlBuilder2[n].append("<td><FONT SIZE=-2>Not Executed</FONT></td>");
    	}
    	else
    	{
    		htmlBuilder2[n].append("<td><FONT SIZE=-2>"+dl.times()+"</FONT></td>");
    	}
    	htmlBuilder2[n].append("</tr>");			
    	
    	htmlBuilderFull.append("<tr>");
    	if(sTCID!="")
    	{
    		htmlBuilderFull.append("<td><A ID="+sTCID.toUpperCase()+"><FONT SIZE=-2>"+sTCID.toUpperCase()+"</FONT></A></td>");
    	}
    	else
    	{
    		htmlBuilderFull.append("<td></td>");
    	}
    	htmlBuilderFull.append("<td><FONT SIZE=-2>"+sTestCaseName+"</FONT></td>");
    	htmlBuilderFull.append("<td><FONT SIZE=-2>"+sRole+"</FONT></td>");
    	htmlBuilderFull.append("<td><FONT SIZE=-2>"+sObjective+"</FONT></td>");
    	htmlBuilderFull.append("<td width=-100%><FONT SIZE=-2>"+sStepNo+"</FONT></td>");
    	htmlBuilderFull.append("<td><FONT SIZE=-2>"+sDesc+"</FONT></td>");
    	htmlBuilderFull.append("<td><FONT SIZE=-2>"+sExpRes+"</FONT></td>");
    	htmlBuilderFull.append("<td><FONT SIZE=-2>"+sStepStatus+"</FONT></td>");
    	if(Scpath!=null){
    	if(Scpath.contains("png"))
    	{	
    		htmlBuilderFull.append("<td><FONT SIZE=-2><a href="+Scpath+" target=_blank>Screen Shot</FONT></td>");
    		Scpath="";
    	}
    	else if(Scpath.contains("No"))
    	{
    		htmlBuilderFull.append("<td><FONT SIZE=-2>"+Scpath+"</FONT></td>");
    	}
    	if(counter>=2)
    	{
    		htmlBuilderFull.append("<td><FONT SIZE=-2>Not Executed</FONT></td>");
    	}
    	}
    	else if(Scpath==null){
    		htmlBuilderFull.append("<td><FONT SIZE=-2>NULL</FONT></td>");
    	}
    	if(counter>=2)
    	{
    		htmlBuilderFull.append("<td><FONT SIZE=-2>Not Executed</FONT></td>");
    	}
    	else
    	{
    		htmlBuilderFull.append("<td><FONT SIZE=-2>"+dl.times()+"</FONT></td>");
    	}
    	htmlBuilderFull.append("</tr>");			

	}

	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: GetTestCases
	 * 
	 * Functionality: This Function is used to Get the TestCases that needs to be executed.
	 * 
	 * Developed By: Polaris_Testing_Team
	 * 
	 -----------------------------------------------------------------------------------------------------------*/
	
	@SuppressWarnings("unchecked")
	public static void GetTestCases(int i) {
		try {
			csvReader = new CSVReader(new FileReader((String) FileName_Suite.get(i)));
			while((rows = csvReader.readNext()) != null) {
			    if(rows[2].equals("Y"))
				{
					TestCase_Name.add(rows[0].trim());		
					Iteration.add(rows[3].trim().toUpperCase()); //Theyagu - added for iteration
				}
			}
	
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: GetFileNames
	 * 
	 * Functionality: This Function is used to Get the Filenames and paths of Test Suite and Test Script File.
	 * 
	 * Developed By: Polaris_Testing_Team
	 * 
	 -----------------------------------------------------------------------------------------------------------*/
	@SuppressWarnings("unchecked")
	public static void GetFileNames() {	
		String[] row = null;
		 Date date = new Date();
		Format formatter = new SimpleDateFormat("YYYY-MM-dd_hh-mm-ss");
		try {
			csvReader = new CSVReader(new FileReader(ConfigFileName));
			while((row = csvReader.readNext()) != null) {
			    
				if(row[0].trim().equalsIgnoreCase(env.trim()) &&row[1].trim().equalsIgnoreCase(testtype.trim())) //theyagu
				{
					FileName_Suite.add(file4(row[3].trim()));
					FileName_TestScript.add(file4(row[4].trim()));
					FileName_TestResults.add(file4(row[5].trim()));
					summaryURL.add(file4(row[6].trim()));
					ExecutedTestCaseResults.add(file4(row[9].trim())+formatter.format(date)+".html");
					PerTestCaseResult.add(file4(row[10].trim())+File.separator);
					screenshotpath.add(file4(row[11].trim())+File.separator);
              			      if(OS.contains("Mac")|OS.contains("Linux"))
					{
					summaryHTMLPath.add(file4(row[8].trim()));
					URLLink.add(file4(row[7].trim())+"/");
					ExecutedTestCaseResultsURL=file4(row[12].trim());
					}
					else
					{
						summaryHTMLPath.add(file4(row[8].trim()).replace("\\","/"));
						URLLink.add(file4(row[7].trim()).replace("\\","/")+"/");
						ExecutedTestCaseResultsURL=file4(row[12].trim()).replace("\\","/");
					}
					ExecutedTestCaseResultsURL=ExecutedTestCaseResultsURL+formatter.format(date)+".html";
					webclientURL.add(row[2].trim());
					firefoxpath.add(row[13].trim());
					chromepath.add(file4(row[14].trim()));
					chromebinarypath.add(row[15].trim());
					iepath.add(file4(row[16].trim()));
					imagesexpected.add(file4(row[17].trim()));
					imagesactual.add(file4(row[18].trim()));
					TestDataFilePath.add(file4(row[19].trim())); //Theyagu TD file
					dl.Getwebclientdetails(webclientURL, firefoxpath, chromepath,chromebinarypath, iepath, runningBrowser,imagesexpected,imagesactual);				
				}
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
/*---------------------------------------------------------------------------------------------------------------------
 * 
 * FUNCTIONALITY: HTML REPORT GENERATOR
 * 
 * 
 ---------------------------------------------------------------------------------------------------------------------*/
	private final class HyperlinkMouseListener extends MouseAdapter {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    	int HTMLRetivedStepNo=1,HTMLReferenceStepNo=1,Notificationflag = 0,t=0;
	    	if (e.getButton() == MouseEvent.BUTTON1) {
	            Element h = getHyperlinkElement(e);
	            if (h != null) {
	                Object attribute = h.getAttributes().getAttribute(HTML.Tag.A);
	                if (attribute instanceof AttributeSet) {
	                    AttributeSet set = (AttributeSet) attribute;
	                    String ID = (String) set.getAttribute(HTML.Attribute.HREF);
	                    if(ID!=null)
	                    {
	                    try {                            
	                    	Desktop.getDesktop().browse(new URI(ID));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (URISyntaxException e1) {
                            e1.printStackTrace();
                        }
	                }
	                }
	            }
	            }
	    }
	    private Element getHyperlinkElement(MouseEvent event) {
	        JEditorPane editor = (JEditorPane) event.getSource();
	        int pos = editor.getUI().viewToModel(editor, event.getPoint());
	        if (pos >= 0 && editor.getDocument() instanceof HTMLDocument) {
	            HTMLDocument hdoc = (HTMLDocument) editor.getDocument();
	            Element elem = hdoc.getCharacterElement(pos);
	            if (elem.getAttributes().getAttribute(HTML.Tag.A) != null) {
	                return elem;
	            }
	        }
	        return null;
	    }
	}

	protected void initUI() {
	    JPanel panel = new JPanel(false);
	    JEditorPane gentextp = new JTextPane();
	    JScrollPane scrollPane = new JScrollPane(gentextp);
	    panel.add(scrollPane);
	    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    gentextp.setContentType("text/html");
	    gentextp.setEditable(false);
	    gentextp.setPreferredSize(new Dimension(1200, 800));
	    gentextp.addMouseListener(new HyperlinkMouseListener());
	    try {
	    	gentextp.setPage("file:///"+summaryURL.get(0).toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	    JFrame f = new JFrame(Driver.class.getSimpleName());
	    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.add(panel, BorderLayout.CENTER);
	    f.pack();
	    f.setSize(f.getWidth() + 1000, f.getHeight() + 1000);
	    f.setVisible(true);
	}
	public static void killallprocess(){
		try {            
         //  Runtime.getRuntime().exec("taskkill /f /im firefox.exe");
           Runtime.getRuntime().exec("taskkill /f /im iexplore.exe");
           Runtime.getRuntime().exec("taskkill /f /im chrome.exe");           
           Runtime.getRuntime().exec("taskkill /f /im IEDriverServer.exe");
           Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
        } catch (Exception e) {
        }
	}
	public static String file4(String a)
	{
	String workingdirectory= System.getProperty("user.dir");

	String subdirectory=workingdirectory.substring(0, workingdirectory.length()-17);
	//String subdirectory="D:\\QATAR_AUTOMATION"; // Theyagu
	
	String[] filename = a.split("\\\\");
	
			for(String file: filename){

			//subdirectory=subdirectory+File.separator+file;
			subdirectory=subdirectory+File.separator+file; //Theyagu

			}
			return subdirectory;
			
	}			
}
