package model;

// Accum Meredith and Dujia Guo



import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * JUnit test class for DeathlyHallows.
 */
public class DeathlyHallowsTest {
  private Line l1;
  private Circle c1;
  private Triangle t1;

  /**
   * Set up for DH tests.
   */
  @Before
  public void setUp(){
    c1 = new Circle(0,10,10);
    l1 = new Line (0,0,0,30);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalArgumentException(){
    t1 = new Triangle (new Line(Math.sqrt(-300),0,Math.sqrt(300),0),new Line(Math.sqrt(300),0,0,300),
            new Line(0,300,Math.sqrt(300),0));
    DeathlyHallows MasterOfDeath = new DeathlyHallows(l1,t1,c1);
  }


  @Test
  public void testToString(){
    t1 = new Triangle (new Line(-Math.sqrt(3)*10,0,Math.sqrt(3)*10,0),new Line(Math.sqrt(3)*10,0,0,30),
            new Line(0,30,-Math.sqrt(3)*10,0));
    DeathlyHallows MasterOfDeath = new DeathlyHallows(l1,t1,c1);
    assertEquals("DeathlyHallows:  [Line: (0.00,0.00) to (0.00,30.00)] " +
            "[Triangle: [Line: (-17.32,0.00) to (17.32,0.00)] " +
            "[Line: (17.32,0.00) to (0.00,30.00)] " +
            "[Line: (0.00,30.00) to (-17.32,0.00)]] " +
            "[Circle: (0.00,10.00) r=10.00]",MasterOfDeath.toString());
  }

}