package model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

// Accum Meredith and Dujia Guo


/**
 * JUnit test class for SymbolRecognizer.
 */
public class SymbolRecognizerTest {
  private SymbolRecognizer recognizer;

  /**
   * Set up for tests.
   */
  @Before
  public void setUp() {
    recognizer = new SymbolRecognizerImpl();
  }

  /**
   * Test if the reportSymbols method correctly returns a list of all symbols in symbol Recognizer.
   */
  @Test
  public void testReportSymbols() {
    recognizer.addLine(-12, 13, 4, -2);
    recognizer.addLine(11, 15, 3, 0);
    recognizer.addCircle(2, 3, 7);
    assertEquals(3, recognizer.reportSymbols().size());
  }

  /**
   * Test if the addline method correctly adds a line.
   */
  @Test
  public void testAddLine() {
    recognizer.addLine(2, 3, 4, 5);
    assertEquals("Symbols:\nLine: (2.00,3.00) to (4.00,5.00)\n", recognizer.toString());
  }

  /**
   * Test if the addcircle method correctly adds a circle.
   */
  @Test
  public void testAddCircle() {
    recognizer.addCircle(2, 3, 18);
    assertEquals("Symbols:\nCircle: (2.00,3.00) r=18.00\n", recognizer.toString());
  }

  /**
   * Test if the symbolrecognizer correctly adds a snowman if three circles are being added
   * in ascending order.
   */
  @Test
  public void testAddSnowmanInAscendingOrder() {
    recognizer.addCircle(-3, 0, 5);
    recognizer.addCircle(-3, 12, 7);
    recognizer.addCircle(-3, 29, 10);
    assertEquals("Symbols:\nSnowman: [Circle: (-3.00,29.00) r=10.00] " +
            "[Circle: (-3.00,12.00) r=7.00] " +
            "[Circle: (-3.00,0.00) r=5.00]\n", recognizer.toString());
  }

  /**
   * Test if the symbolrecognizer adds a snowman if three circles
   * are being added in descending order.
   */
  @Test
  public void testAddSnowmanInDescendingOrder() {
    recognizer.addCircle(-3, 29, 10);
    recognizer.addCircle(-3, 12, 7);
    recognizer.addCircle(-3, 0, 5);
    assertEquals("Symbols:\nSnowman: [Circle: (-3.00,0.00) r=5.00] " +
            "[Circle: (-3.00,12.00) r=7.00] " +
            "[Circle: (-3.00,29.00) r=10.00]\n", recognizer.toString());
  }

  /**
   * CHANGED : Modified these triangles, to account for larger thresholds in recognizing
   * a triangle.
   * Test if the symbolrecognizer adds a triangle if three lines are being added consecutively.
   */
  @Test
  public void testAddTriangleInOrder() {
    recognizer.addLine(0, 0, -30, -40);
    recognizer.addLine(-30, -40, -100, -110);
    recognizer.addLine(-100, -110, 0, 0);
    assertEquals("Symbols:\n" +
            "Triangle: [Line: (-100.00,-110.00) to (0.00,0.00)] " +
            "[Line: (-30.00,-40.00) to (-100.00,-110.00)] " +
            "[Line: (0.00,0.00) to (-30.00,-40.00)]\n", recognizer.toString());
  }

  /**
   * Test if the symbolrecognizer adds an equilateral triangle if three lines are
   * being added consecutively.
   */
  @Test
  public void testAddEquilateralTriangleInOrder() {
    recognizer.addLine(0, 0, 1, Math.sqrt(3));
    recognizer.addLine(1, Math.sqrt(3), 2, 0);
    recognizer.addLine(2, 0, 0, 0);
    assertEquals("Symbols:\nEquilateral Triangle: [Line: (2.00,0.00) to (0.00,0.00)] " +
                    "[Line: (1.00,1.73) to (2.00,0.00)] [Line: (0.00,0.00) to (1.00,1.73)]\n",
            recognizer.toString());
  }

  /**
   * Test if the symbolrecognizer adds a snowman if three circles are not added consecutively.
   */
  @Test
  public void testAddSnowmanOutOfOrder() {
    recognizer.addCircle(-3, 0, 5);

    recognizer.addLine(1, Math.sqrt(3), 2, 0);

    recognizer.addCircle(-3, 12, 7);
    recognizer.addCircle(-3, 29, 10);

    assertEquals("Symbols:\nLine: (1.00,1.73) to (2.00,0.00)\n" +
            "Snowman: [Circle: (-3.00,29.00) r=10.00] " +
            "[Circle: (-3.00,12.00) r=7.00] " +
            "[Circle: (-3.00,0.00) r=5.00]\n", recognizer.toString());

  }

  /**
   * CHANGED : Modified these triangles, to account for larger thresholds in recognizing
   * a triangle.   *
   * Test if the symbolrecognizer adds a triangle if three lines are not added consecutively.
   */
  @Test
  public void testAddTriangleOutOfOrder() {
    recognizer.addLine(0, 0, -30, -40);
    recognizer.addLine(-30, -40, -100, -110);

    recognizer.addCircle(-30, 120, 70);

    recognizer.addLine(-100, -110, 0, 0);
    assertEquals("Symbols:\nCircle: (-30.00,120.00) r=70.00\n" +
            "Triangle: [Line: (-100.00,-110.00) to (0.00,0.00)] " +
            "[Line: (-30.00,-40.00) to (-100.00,-110.00)] " +
            "[Line: (0.00,0.00) to (-30.00,-40.00)]\n", recognizer.toString());
  }

  /**
   * CHANGE: Added a test for an imperfectly drawn equilateral triangle.
   * Test if the symbolrecognizer adds an equilateral triangle if the end of lines were close
   * to forming a vertex but slightly off.
   */
  @Test
  public void testAddImperfectEquilateralTriangleInOrder() {
    recognizer.addLine(0, 0, 10, 17);
    recognizer.addLine(10, 17, 20, 0);
    recognizer.addLine(20, 0, 0, 0);
    assertEquals("Symbols:\nEquilateral Triangle: [Line: (20.00,0.00) to (0.00,0.00)] " +
                    "[Line: (10.00,17.00) to (20.00,0.00)] [Line: (0.00,0.00) to (10.00,17.00)]\n",
            recognizer.toString());
  }


  /**
   * CHANGED : Modified these triangles, to account for larger thresholds in recognizing
   * a triangle.
   * Test if the symbol recognizer correctly adds a list of lines, circles, triangles and snowmen
   * given a set of symbols.
   */
  @Test
  public void testAddingSymbolsInVariousOrders() {
    recognizer.addCircle(0, 0, 50);
    recognizer.addCircle(0, 120, 70);
    recognizer.addCircle(0, 290, 100);

    // changed xEnd of this line object, because thresholds are larger
    recognizer.addLine(-30, -20, -390, 100);

    recognizer.addLine(0, 0, 10, Math.sqrt(3) * 10);
    recognizer.addLine(10, Math.sqrt(3) * 10, 20, 0);
    recognizer.addLine(20, 0, 0, 0);

    recognizer.addCircle(-120, 230, 600);
    recognizer.addLine(0, 0, 200, 200);
    recognizer.addLine(0, 0, -30, -40);
    recognizer.addCircle(30, 30, 100);

    recognizer.addLine(-30, -40, -100, -110);
    recognizer.addLine(-100, -110, 0, 0);

    recognizer.addCircle(280, 30, 150);
    recognizer.addCircle(680, 30, 250);

    assertEquals("Symbols:\n" +
            "Snowman: [Circle: (0.00,290.00) r=100.00] [Circle: (0.00,120.00) r=70.00] " +
            "[Circle: (0.00,0.00) r=50.00]\n" +
            "Line: (-30.00,-20.00) to (-390.00,100.00)\n" +
            "Equilateral Triangle: [Line: (20.00,0.00) to (0.00,0.00)] " +
            "[Line: (10.00,17.32) to (20.00,0.00)] [Line: (0.00,0.00) to (10.00,17.32)]\n" +
            "Circle: (-120.00,230.00) r=600.00\n" +
            "Line: (0.00,0.00) to (200.00,200.00)\n" +
            "Triangle: [Line: (-100.00,-110.00) to (0.00,0.00)] " +
            "[Line: (-30.00,-40.00) to (-100.00,-110.00)] " +
            "[Line: (0.00,0.00) to (-30.00,-40.00)]\n" +
            "Snowman: [Circle: (680.00,30.00) r=250.00] [Circle: (280.00,30.00) r=150.00] " +
            "[Circle: (30.00,30.00) r=100.00]\n", recognizer.toString());
  }

  /**
   * Test if the toString method for the SymbolRecognizerImpl class returns a
   * String in the correct format.
   */
  @Test
  public void testToString() {
    recognizer.addCircle(0, 0, 5);
    recognizer.addCircle(0, 12, 7);
    recognizer.addCircle(0, 29, 10);

    recognizer.addLine(-3, -2, -19, 10);
    assertEquals("Symbols:\n" +
            "Snowman: [Circle: (0.00,29.00) r=10.00] [Circle: (0.00,12.00) r=7.00] " +
            "[Circle: (0.00,0.00) r=5.00]\n" +
            "Line: (-3.00,-2.00) to (-19.00,10.00)\n", recognizer.toString());
  }

  /**
   * Test if a DeathlyHallows object is recognized by the SymbolRecognizer.
   */
  @Test
  public void testDeathlyHallows() {
    recognizer.addLine(-5, 0, 5, 0);
    recognizer.addLine(0, 9, 5, 0);
    recognizer.addLine(0, 9, -5, 0);
    recognizer.addCircle(0, 4.25, 4);
    recognizer.addLine(0, 9, 0, 0);

    assertEquals(
            "Symbols:\n"
                    + "DeathlyHallows:  [Line: (0.00,9.00) to (0.00,0.00)] [Equilateral Triangle: "
                    + "[Line: (0.00,9.00) to (-5.00,0.00)] [Line: (0.00,9.00) to (5.00,0.00)] "
                    + "[Line: (-5.00,0.00) to (5.00,0.00)]] [Circle: (0.00,4.25) r=4.00]\n",
            recognizer.toString());
  }

  /**
   * Rest if a Rectangle object is recognized by the SymbolRecognizer.
   */
  @Test
  public void testRectangle() {
    recognizer.addLine(0, 0, 50, 0);
    recognizer.addLine(50, 0, 50, 50);
    recognizer.addLine(50, 50, 0, 50);
    recognizer.addLine(0, 50, 0, 0);

    assertEquals("Symbols:\n"
            + "Rectangle: [Line: (0.00,50.00) to (0.00,0.00)] "
            + "[Line: (50.00,50.00) to (0.00,50.00)] "
            + "[Line: (50.00,0.00) to (50.00,50.00)] "
            + "[Line: (0.00,0.00) to (50.00,0.00)]\n", recognizer.toString());

  }

  @Test
  public void testAddRectangleInOrder(){
    recognizer.addLine(0,0,50,0);
    recognizer.addLine(50,0,50,60);
    recognizer.addLine(50,60,0,60);
    recognizer.addLine(0,60,0,0);
    assertEquals("Symbols:\n"
            + "Rectangle: [Line: (0.00,60.00) to (0.00,0.00)] " +
            "[Line: (50.00,60.00) to (0.00,60.00)] " +
            "[Line: (50.00,0.00) to (50.00,60.00)] " +
            "[Line: (0.00,0.00) to (50.00,0.00)]\n", recognizer.toString());
  }


  @Test
  public void testAddRectangleOutOfOrder(){
    recognizer.addLine(0,0,50,0);
    recognizer.addLine(50,0,50,60);
    recognizer.addLine(50,60,0,60);
    recognizer.addCircle(0,0,50);
    recognizer.addLine(0,60,0,0);

    assertEquals("Symbols:\n"
            + "Circle: (0.00,0.00) r=50.00\n"
            + "Rectangle: [Line: (0.00,60.00) to (0.00,0.00)] " +
            "[Line: (50.00,60.00) to (0.00,60.00)] " +
            "[Line: (50.00,0.00) to (50.00,60.00)] " +
            "[Line: (0.00,0.00) to (50.00,0.00)]\n", recognizer.toString());
  }


  @Test
  public void testAddTriforce() {
    recognizer.addLine(-5*Math.sqrt(3)*10, -5*10, 5*Math.sqrt(3)*10, -5*10);
    recognizer.addLine(5*Math.sqrt(3)*10, -5*10, 0, 10*10);
    recognizer.addLine(0, 10*10, -5*Math.sqrt(3)*10, -5*10);


    recognizer.addLine((5/2)*Math.sqrt(3)*10, (5/2)*10, (-5/2)*Math.sqrt(3)*10, (5/2)*10);
    recognizer.addLine((5/2)*Math.sqrt(3)*10, (5/2)*10, 0, -5*10);
    recognizer.addLine(0, -5*10, (-5/2)*Math.sqrt(3)*10, (5/2)*10);

    recognizer.addCircle(0, 0, 100);


    assertEquals("Symbols:\n"
            + "Triforce: [Equilateral Triangle: [Line: (0.00,-50.00) to (-34.64,20.00)] "
            + "[Line: (34.64,20.00) to (0.00,-50.00)] [Line: (34.64,20.00) to (-34.64,20.00)]] "
            + "[Equilateral Triangle: [Line: (0.00,100.00) to (-86.60,-50.00)] "
            + "[Line: (86.60,-50.00) to (0.00,100.00)] [Line: (-86.60,-50.00) to "
            + "(86.60,-50.00)]] [Circle: (0.00,0.00) r=100.00]\n",recognizer.toString());
  }


}