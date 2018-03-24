// Accum Meredith, Guo Dujia


import controller.GestureController;
import controller.IGestureController;
import model.SymbolRecognizer;
import model.SymbolRecognizerImpl;
import view.GestureView;
import view.IGestureView;

/**
 * This class contains the main method to run the GestureRecognizer program, which
 * will bring up a window in which the user can draw lines and circles that will be
 * recognized as Triangles, Equilateral Triangles, and Snowmen.
 */
public class GestureRecognizer {

  /**
   * Run the GestureRecognizer program, which will bring up a new window.
   *
   * @param args none
   */
  public static void main(String[] args) {
    SymbolRecognizer model = new SymbolRecognizerImpl();
    IGestureController controller = new GestureController(model);
    IGestureView view = new GestureView(1000, 1000, controller);
    controller.setView(view);
  }
}
