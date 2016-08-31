import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Hashtable;
import au.com.bytecode.opencsv.CSVReader;

public class Utility {	
	
	static CSVReader objCSVReader; // CSV result set
	public static ArrayList alTCIteration = new ArrayList(); //To store the TCTD combination in ArrayList.
	public static Hashtable hashRS = new Hashtable();	// Result set container with TC+TD+COLUMN as key and TD as Value
	
	public Utility(){		
	}
	
	public static void main (String[] args) throws IOException {	
	}

	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: getCSVResultSet
	 * Functionality: Retrieves the CSV test data record set from the Hash table
	 * Developed By: Polaris
	 * -----------------------------------------------------------------------------------------------------------*/
	
	public static void getCSVResultSet (String strFilepath) throws IOException {
	
		// Variable Assignment
		// ------------------------------------------------------------------------------	
		
		int tdColCount=0;	// today test data master column count
		int tdrowcount=0;	// today test data master row count
		int tcColIndex=0; 	// TC column index
		int tdColIndex=1;	// TD column index	
		int intTBE=2;	// TD column index
		int intStart=3; // TD starting column for data fetch in Hash table
		int intExecuteonce = 0; // OTE	
		int intRecCount = 1; //Setting the cursor to first record
		int tclistindex = 0; //Index to save TC+TD combo in ArrayList
		
		String testnameid = new String(); //To save TC NAME
		String testdataid = new String(); //To save TD ID
		String tbe = new String(); //To save TBE = Y/N
		String strElement = null; // TC arraylist elements
		
		String[] arrRecordSet = null;
		String[] arrColumnValues = null;
		
		//Hashtable hashRS = new Hashtable();
		ArrayList alistTDColumns = new ArrayList();		
		//------------------------------------------------------------------------------
		
		// Invoke CSV Reader
		
		objCSVReader = new CSVReader(new FileReader(strFilepath));		
		
		List listRecordSet = objCSVReader.readAll();
		
		//EH (Error Handling): Empty test data master
		if (listRecordSet.isEmpty()!=false){
			//System.out.println("Test Data Master is empty");
			objCSVReader.close();
			return ;
		}
		
		try{
			for (Object objArr : listRecordSet){
								
				arrColumnValues = (String[]) objArr;
				
				// OTE (One time execution)
				if (intExecuteonce == 0){
					
					tdColCount=arrColumnValues.length;
					tdrowcount=listRecordSet.size();
					
					//System.out.println("Total column count = "+tdColCount);
					//System.out.println("\nTotal row count = "+tdrowcount);
					
					// Storing the column header in ArrayList
					
					for (int i=0; i<=(tdColCount-1); i++){
						alistTDColumns.add(i,arrColumnValues[i].toString());					
					}					
					intExecuteonce = 1;
				}
				
				if (intRecCount>=2){
					testnameid = arrColumnValues[tcColIndex].toString().trim().toUpperCase();
					testdataid = arrColumnValues[tdColIndex].toString().trim().toUpperCase();
					tbe = arrColumnValues[intTBE].toString().trim().toUpperCase();
					
					// Storing all TC+TD+COLUMN test data in Hashtable for TBE=Y
					if (tbe.equalsIgnoreCase("Y")) {
						
						// Storing TC & TD in array list for iteration count;
						strElement = testnameid+testdataid;						
						alTCIteration.add(tclistindex, strElement);
						tclistindex++;
						
						for (int i=intStart; i<=(tdColCount-1);i++){
							
							Object[] arrCols = alistTDColumns.toArray();
							//String strKey = arrCols[i].toString();
							String strKey = testnameid+testdataid+arrCols[i].toString();
							String strValue = arrColumnValues[i].toString();						
							//System.out.println(strKey +","+ strValue);
							hashRS.put(strKey,strValue);							
						}	
					}
				}
				intRecCount++; // Record set counter
			}	// list for loop			
		} catch (ArrayIndexOutOfBoundsException e){
			e.printStackTrace();
		} catch (ArrayStoreException e){
			e.printStackTrace();
		} catch (IndexOutOfBoundsException e){
			e.printStackTrace();
		} catch (NegativeArraySizeException e){
			e.printStackTrace();
		} catch (NullPointerException e){
			e.printStackTrace();
		}		
		objCSVReader.close();	
		return ;
	}

	/*-----------------------------------------------------------------------------------------------------------
	 * Function Name: getIterationCount
	 * Functionality: Fetches the iteration count of a specific test case from the ArrayList
	 * Developed By: Polaris
	 * -----------------------------------------------------------------------------------------------------------*/
	
	public static int getIterationCount (ArrayList listValue, String strInput) {
		
		int count=0;
		
		String strValue=null;
		String strTemp=null;
		
		Iterator loop = listValue.iterator();
		
		while (loop.hasNext()){
			
			strValue = loop.next().toString();	
			strTemp = strValue.substring(0,strInput.length());			
			
			//System.out.println(strTemp + "," + strInput);
			
			if (strTemp.equalsIgnoreCase(strInput)){			
				count++;
			}
		}
		return count;
	}
}