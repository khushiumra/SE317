package lab5;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/*
 * @author Khushveen Kaur Umra
 * Class SE 317
 * Lab 5 - Embedded Systems Testing
 */


public class Testing {
	
	private WSensor s;
	
	@Before public void create()
	{
		s = new WSensor();
	}
	
  //The following test cases are going to test the current temperature and humidity levels
	
	//Here is the first current test for both temperature and humidity
	@Test
	public void Test1() throws Exception {
		
		//Here we are going to input the value "0" for both temperature and humidity
		s.read(0, 0);
		assertTrue(s.CTemp == 0 && s.CHum == 0);
	}
	
	//Here is the second current test for both temperature and humidity
	@Test
	public void Test2() throws Exception {
		
		//Here we are going to input the maximum values for both temperature and humidity
		s.read(125, 100);
		assertTrue(s.CTemp == 125 && s.CHum == 100);
	}
	
	//Here is the third current test for both temperature and humidity
	@Test
	public void Test3() throws Exception {
		Assertions.assertThrows(Exception.class, () -> {
			
		//Here we are going to input negative values which will throw the Exception 
			s.read(-1,-1);
		});
	}
	
	//Here is the fourth current test for both temperature and humidity
	@Test
	public void Test4() throws Exception {
		Assertions.assertThrows(Exception.class, () -> {
			
			//Here we are going to input a value greater than 125F which will throw an exception
			s.read(126, 100);
		});	
	} 
	
	 //The following tests are going to test the maximum values for both temperature and humidity
	@Test
	public void Test5() throws Exception {
		
	//Here is the first test for the maximum temperature and humidity
		//We only input one value to check the maximum for both of them
		s.read(0,0);
		assertTrue(s.MxTemp == 0 && s.MxHum == 0);
	}
	
	@Test
	public void Test6() throws Exception {
		//Here is the second test for the maximum temperature and humidity
		//We input two set of values to check if we get the maximum values
		s.read(0, 0);
		s.read(125,100);
		assertTrue(s.MxTemp == 125 && s.MxHum == 100);
	}
	
	@Test
	public void Test7() throws Exception {
		//Here is the third test for the maximum temperature and humidity
		//We input 3 set of value to check if we get the maximum values
		s.read(0, 0);
		s.read(125, 100);
		s.read(50, 70);
		assertTrue(s.MxTemp == 125 && s.MxHum == 100);
	}
	
	@Test
	public void Test8() throws Exception {
		//Here is the fourth test for the maximum temperature and humidity
		//We input 3 set of values to check if we get the maximum values
		s.read(0, 0);
		s.read(125,100);
		s.read(25,80);
		assertTrue(s.MxTemp == 125 && s.MxHum == 100);
	}
	
	//The following test cases test the minimum values for the temperature and humidity
	  @Test
	public void Test9() throws Exception {
		  //Here we input the highest value of the accepted range for temperature and humidity
		s.read(125, 100);
		assertTrue(s.MnTemp == 125 && s.MnHum == 100);
	}
	
	@Test
	public void Test10() throws Exception {
		//Here we input two set of values to see if get the minimum values 
		s.read(125, 100);
		s.read(0, 0);
		assertTrue(s.MnTemp == 0 && s.MnHum == 0);
	}
	
	@Test
	public void Test11() throws Exception {
		//Here we input 3 set of values to see if we get the minimum values
		s.read(125, 100);
		s.read(0, 0);
		s.read(25, 25);
		assertTrue(s.MnTemp == 0 && s.MnHum == 0);
	}
	
	@Test
	public void Test12() throws Exception {
		//Here we input 3 set of different values to see if we get the minimum values
		s.read(125,100);
		s.read(0, 0);
		s.read(40,50);
		assertTrue(s.MnTemp == 0 && s.MnHum == 0);
	}
	
	// The following test cases test the different trends seen for temperature and humidity 
	@Test
	public void Test13() throws Exception {
		s.read(0, 0);
		assertTrue(s.getTrend(s.TTemp).equals("N/A") && s.getTrend(s.THum).equals("N/A"));
	}
	
	@Test
	public void Test14() throws Exception {
		s.read(0,1);
		s.read(1,0);
		assertTrue(s.getTrend(s.TTemp).equals("Increasing") && s.getTrend(s.THum).equals("Decreasing"));		
	}
	
	@Test
	public void Test15() throws Exception {
		s.read(1,0);
		s.read(0,1);
		assertTrue(s.getTrend(s.TTemp).equals("Decreasing") && s.getTrend(s.THum).equals("Increasing"));		
	}
	
	@Test
	public void Test16() throws Exception {
		s.read(0, 0);
		s.read(0, 0);
		assertTrue(s.getTrend(s.TTemp).equals("Stable") && s.getTrend(s.THum).equals("Stable"));		
	}
	
	@Test
	public void Test17() throws Exception {
		s.read(1, 1);
		assertTrue(s.getTrend(s.TTemp).equals("N/A") && s.getTrend(s.THum).equals("N/A"));
	}
	
	@Test
	public void Test18() throws Exception {
		s.read(1, 1);
		s.read(1, 1);
		assertTrue(s.getTrend(s.TTemp).equals("Stable") && s.getTrend(s.THum).equals("Stable"));
	}
	
	@Test
	public void Test19() throws Exception {
		s.read(0,0);
		assertTrue(s.HumStatus(s.CHum).equals("OK"));
	}
	
	@Test
	public void Test20() throws Exception {
		s.read(0,1);
		s.read(0,1);
		assertTrue(s.HumStatus(s.CHum).equals("Hi"));
	}
	
	@Test
	public void Test21() throws Exception{
		s.read(0, 2);
		assertTrue(s.HumStatus(s.CHum).equals("Low"));
	}
}