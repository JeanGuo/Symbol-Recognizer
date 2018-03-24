package view;
// Assignment 10
// Accum Meredith, Guo Dujia

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.geom.Point2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import controller.IGestureController;
import model.Symbol;

/**
 * This class implements the IGestureView interface, and extends JFrame, to represent
 * the View for the GestureRecognizer program. Its purpose is to draw the
 * symbols that the user adds to the model. It contains a JPanel class, which has
 * methods to draw the symbols passed by the controller to the window.
 */
public class GestureView extends JFrame implements IGestureView {
  private JPanel drawPanel;
  private IGestureController controller;

  /**
   * Construct a GestureView with the given height, width, and controller.
   *
   * @param width      width of window
   * @param height     height of window
   * @param controller controller to get data from
   */
  public GestureView(int width, int height, IGestureController controller) {
    super();
    this.controller = controller;
    setTitle("Symbols");
    setSize(width, height);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    drawPanel = new MyPanel();
    JScrollPane scrolls = new JScrollPane(drawPanel);
    this.add(scrolls);
    setVisible(true);
  }

  /**
   * Set the size of the view.
   *
   * @param width  width of view
   * @param height height of view
   */
  @Override
  public void setCanvasSize(int width, int height) {
    drawPanel.setPreferredSize(new Dimension(width, height));
    rePaintCanvas();
  }

  /**
   * Repaint the canvas.
   */
  @Override
  public void rePaintCanvas() {
    drawPanel.revalidate();
    drawPanel.repaint();
  }

  /**
   * Set the mouse listener for the view to the given MouseAdapter. This sets both
   * a Mouse Listener and a Mouse Motion Listener.
   *
   * @param listener given MouseAdapter to use as a mouse listener
   */
  @Override
  public void setMouseListener(MouseAdapter listener) {
    drawPanel.addMouseListener(listener);
    drawPanel.addMouseMotionListener(listener);
  }

  /**
   * This class extends a JPanel and is used by the View to draw the model's symbols
   * onto the window.
   */
  class MyPanel extends JPanel {

    /**
     * Construct a MyPanel object by calling on the parent constructor.
     */
    public MyPanel() {
      super();
      setBackground(Color.WHITE);
    }

    /**
     * Paint the symbols to the window each time that repaint is called.
     *
     * @param g given graphics object
     */
    @Override
    public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.setFont(new Font("Dialog", Font.BOLD, 30));
      g.setColor(Color.magenta);

      // draw dots as user drags mouse
      for (Point2D pt : controller.getMousePoints()) {
        g.drawOval((int) pt.getX(), (int) pt.getY(), 5, 5);
        g.fillOval((int) pt.getX(), (int) pt.getY(), 5, 5);
      }

      // draw symbols
      List<Symbol> symbols = controller.getSymbols();
      for (Symbol s : symbols) {
        s.drawSymbolString(g);
        s.drawSymbol(g);
      }
    }

  }


}
