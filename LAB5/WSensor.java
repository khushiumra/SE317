package lab5;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
 * @author Khushveen Kaur Umra
 * Class SE 317
 * Lab 5 - Embedded Systems Testing
 */

public class WSensor {
	
	//Variable to check the status of humidity 
	
	public int CheckHum;
	
	//Variables for temperature and humidity
	
	public int CTemp; //Current Temperature
	public int CHum;  // Current Humidity
	public int PTemp; // Previous Temperature
	public int PHum;  // Previous Humidity
	public int MnTemp; //Minimum Temperature
	public int MnHum;  //Minimum Humidity
	public int MxTemp; //Maximum Temperature
	public int MxHum; //Maximum Humidity
	
	// Variables to display the trend seen in temperature and humidity changes
	
	public int TTemp; //Trend in temperature
	public int THum; //Trend in humidity
	
	public static void main(String[] args) throws NumberFormatException, Exception {
		WSensor w = new WSensor();
		
		System.out.println("Please wait for a few seconds after you enter the values");
		System.out.println("to allow the system to display the results!");
		System.out.println("\n");
		System.out.println("Type 'reset' if you wish to reset the values!");
		System.out.println("Enter the values as <Temp><Humidity>: ");
		System.out.println("Example : input 20 20");
		System.out.println("\n"); 
		
		int flag = 1;
		switch(flag) {
		case 1:
				while(true) {
					Scanner sc = new Scanner(System.in);
					
					String [] line = sc.nextLine().split(" "); 
					
					if(line[0].equals("reset")) //To see if the user has asked to reset the values
					{
						w.reset();
						System.out.println(w.toString());
					}
					
					else if (line[0].equals("input")) // To check the users input and display it
					{
						w.read(Integer.valueOf(line[1]), Integer.valueOf(line[2]));
						System.out.println(w.toString());
					}
					
					else { //To let the user know that the value given by then does not match the format
						
						System.out.println("Invalid input format");
						continue;
					}
					
					TimeUnit.SECONDS.sleep(30);
				}
				
		case 2:
			
			int [] temperature = {66, 68, 69, 67, 63, 59, 53};
			// {66, 68, 69, 67, 63, 59, 53};
			int [] humidity = {53, 51, 48, 49, 54, 56, 56};
			// {53, 51, 48, 49, 54, 56, 56};
			
			int length = temperature.length > humidity.length ? temperature.length:humidity.length;
			
					for(int i =0; i<length; i++)
					{
						try {
							w.read(temperature[i], humidity[i]); //To read the value the user input, as temperature and humidity
						}
						
						catch(Exception e)
						{
							throw new Exception("Invalid Input"); //To let the user know they have used the wrong format 
						}
					}
						
						System.out.println(w.toString()); //To display the values input by the user
						break;
					} 	 
		}
	
	//A constructor for a default meter with assigned values
	
		public WSensor()  
		{
			CTemp = -1;
			CHum = -1;
			PTemp = -1;
			PHum = -1;
			MxTemp = 0;
			MxHum = 0;
			MnTemp = 125;
			MnHum = 100;
			TTemp = -1;
			THum = -1;
			CheckHum = -1;
		}
	
	//A function to reset the values on the meter
	
		public void reset()
		{
			PTemp = -1;
			PHum = -1;
			MxTemp = CTemp;
			MxHum = CHum;
			MnTemp = CTemp;
			MnHum = CHum;
			TTemp = -1;
			THum = -1;
			CheckHum = HumidityCheck(CHum);
		}
	
	//A function to the check the humidity status based on the value shown on the meter
		// Here the values represent the following 
		// 0 = OK Level
		// 1 = High Level
		// 2 = Low Level
		
		public int HumidityCheck(int hum)
		{
			if(hum < 0 || hum > 100) // There is something wrong with the readings or the levels are too low or high
			{
				return -1;
			}
			
			else if (hum < 30) // The humidity level being reported is extremely low
			{
				return 2;
			}
			
			else if (hum >= 30 && hum <= 55) //The humidity level being reported is OK
			{
				return 0;
			}
			
			else 
			{
				return 1; 
			}
		}
		
		// A function to set the trend for the humidity and the temperature
		// where the trend will compare the last two readings and will determine
		// if the temperature or humidity is either increasing, decreasing or is stable.
		
		public int setTrend(int before, int current)
		{
			if(before < 0)
			{
				return -1; 
			}
			
		     if (before < current) // This means that the temperature/humidity has been increased from the previous reading
			{
				return 0;
			}
			
			else if (before == current) // This means that the temperature/humidity readings have not changed and are stable
			{
				return 2;
			}
			
			else {
				return 1; // This means that the readings have decreased from the previous reading
			}
		}
		
		// A function to read the values of the temperature and humidity based on the input values

		public void read(int temp, int hum) throws Exception {
			
			// These are the minimum and maximum values that are the allowed for the input range
			//If these values are being input, it will know that there is an error and will not show the accurate results
			if ((temp < 0 || temp > 125) || (hum < 0 || hum > 100))
			{
				throw new Exception("Invalid input");
			}
			
			PTemp = CTemp;
			PHum = CHum;
			CTemp = temp;
			CHum = hum;
			MxTemp = Math.max(MxTemp, CTemp);
			MxHum = Math.max(MxHum, CHum);
			MnTemp = Math.min(MnTemp, CTemp);
			MnHum = Math.min(MnHum, CHum);
			TTemp = setTrend(PTemp, CTemp);
			THum = setTrend(PHum, CHum);
			CheckHum = HumidityCheck(CHum);
		}
		
		//A function to get the trend of the humidity and the temperature
		// The trend will compare the last two readings for both of them
		//And will determine what the trend is. i.e., whether it is increasing, decreasing, or is stable
		
		public String getTrend (int s)
		{
			if (s == -1)
			{
				return "N/A"; //Error
			}
			
			else if (s == 0)
			{
				return "Increasing"; //The value reported shows that it is increasing
			}
			
			else if (s == 1)
			{
				return "Decreasing"; //The value reported shows that it is decreasing
			}
			
			else if (s == 2)
			{
				return "Stable"; //The value reported is same as the previous readings
			}
			
			else {
				return "N/A"; //Error
			}
		}
		
		// A function to get the humidity status
				// Here we will know if the relative humidity being reported is either OK, Low or High
				
				public String HumStatus(int s)
				{
					if(s == -1)
					{
						return "N/A"; //Error
					}
					
					else if (s == 0) //The relative humidity level is OK
					{
						return "OK";
					}
					
					else if (s == 1) //The relative humidity level is High
					{
						return "Hi";
					}
					
					else if (s == 2)
					{
						return "Low"; //The relative humidity level is low
					}
					
					else {
						return "N/A"; //Error
					}
				}
	
	
	
	//A function to display all the values on the meter
			// A To string function
			
			public String toString()
			{
				return "Relative Humidity: " + "\n" + 
			"Current Humidity: " +CHum+ "%"+"\n" +
			"Maximum Humidity: " +MxHum+ "%"+"\n" +
			"Minimum Humidity: " +MnHum+ "%" + "\n" +
		    "Humidity Trend: " +getTrend(THum)+"\n" +
			"\n" + 
			"Temperature :" + "\n" +
			"Current Temperature: " +CTemp+ "F"+"\n" +
			"Maximum Temperature: " +MxTemp+ "F" + "\n" +
			"Minimum Temperature: " +MnTemp+ "F" + "\n" +
			"Temperature Trend: " +getTrend(TTemp) + "\n" +
			"Humidity Status: " +HumStatus(CheckHum);
			}
}
