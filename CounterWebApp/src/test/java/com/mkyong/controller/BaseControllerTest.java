import junit.framework.*;

public class BaseControllerTest extends TestCase {
   protected int value1, value2;
   
   // assigning the values
   protected void setUp(){
      value1=4;
      value2=5;
   }

   // test method to add two values
   public void testAdd(){
      double result= value1 + value2;
      assertTrue(result == 9);
   }

}