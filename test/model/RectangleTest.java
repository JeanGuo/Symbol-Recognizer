package model;

// Accum Meredith and Dujia Guo



import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by dujiaguo on 4/23/17.
 */
public class RectangleTest {
  private Line l1;
  private Line l2;
  private Line l3;
  private Line l4;


  @Before
  public void setUp(){
    l1 = new Line(0,0,5,0);
    l2 = new Line(5,0,5,6);
    l3 = new Line(0,0,0,6);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorIllegalArgumentException(){
    l4 = new Line(0,0,80,85);
    Rectangle rec = new Rectangle(l1,l2,l3,l4);
  }


  @Test
  public void testToString(){
    l4 = new Line(0,6,5,6);
    Rectangle rec = new Rectangle(l1, l2, l3, l4);
    assertEquals("Rectangle: [Line: (0.00,0.00) to (5.00,0.00)] "
            + "[Line: (5.00,0.00) to (5.00,6.00)] "
            + "[Line: (0.00,0.00) to (0.00,6.00)] "
            + "[Line: (0.00,6.00) to (5.00,6.00)]", rec.toString());
  }


}