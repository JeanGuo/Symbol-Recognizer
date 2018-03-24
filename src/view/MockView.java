package view;
// Assignment 10
// Accum Meredith, Guo Dujia

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JFrame;
import controller.IGestureController;
import model.Symbol;

/**
 * This class represents a Mock View, which will be used to test the GestureController.
 * It contains all the methods of an IGestureView. It gets data that is passed to it by the
 * Controller and it outputs messages with that data to a StringBuilder object.
 */
public class MockView extends JFrame implements IGestureView {
  private StringBuilder ap;
  private IGestureController controller;
  private final int width;
  private final int height;

  /**
   * Construct a MockView by writing the given width and height to a StringBuilder.
   *
   * @param width      width of the mock view
   * @param height     height of the mock view
   * @param controller controller of the mock view
   * @param ap         StringBuilder object
   */
  public MockView(int width, int height, IGestureController controller, StringBuilder ap) {
    super();
    this.controller = controller;
    this.ap = ap;
    this.width = width;
    this.height = height;
    ap.append("Width:").append(Integer.toString(width))
            .append(" Height:").append(Integer.toString(height));
  }

  /**
   * Write the given width and height of the mock view to the mock view's StringBuilder field.
   *
   * @param width  width of view
   * @param height height of view
   */
  @Override
  public void setCanvasSize(int width, int height) {
    ap.delete(0, ap.length());
    ap.append("Width:").append(Integer.toString(width))
            .append(" Height:").append(Integer.toString(height));
  }

  /**
   * Repaint the mock canvas by resetting the StringBuilder and printing all
   * the symbol strings to the StringBuilder.
   */
  @Override
  public void rePaintCanvas() {
    setCanvasSize(width, height);
    List<Symbol> symbols = controller.getSymbols();
    ap.append("\nSymbols:\n");
    for (Symbol s : symbols) {
      ap.append(s.toString()).append("\n");
    }
  }

  /**
   * Set the mouse listener and mouse motion listener for the JPanel. In the Mock View
   * this method doesn't do anything. It needs to be here so the Controller can call
   * on it.
   *
   * @param listener given MouseAdapter to use as a mouse listener
   */
  @Override
  public void setMouseListener(MouseAdapter listener) {
    // this method doesn't do anything in the mock view
    // it needs to be here so the controller can call on it
  }


}
