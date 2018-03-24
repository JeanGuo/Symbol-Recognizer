package view;
// Assignment 10
// Accum Meredith, Guo Dujia

import java.awt.event.MouseAdapter;

/**
 * This interface contains the methods that should by supported by the view for
 * the GestureRecognizer program.
 */
public interface IGestureView {

  /**
   * Set the size of the view.
   *
   * @param width  width of view
   * @param height hieght of view
   */
  void setCanvasSize(int width, int height);

  /**
   * Set the mouse listener for the view to the given MouseAdapter.
   *
   * @param listener given MouseAdapter to use as a mouse listener
   */
  void setMouseListener(MouseAdapter listener);

  /**
   * Repaint the canvas.
   */
  void rePaintCanvas();

}
