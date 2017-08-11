package analyzethis.mnmnist

import java.awt._
import javax.swing.JFrame

import org.junit.Assert._
import org.junit.Test

/**
  * Demo features with some basic diagnostics to GUI head.
  *
  * Created by bredin@acm.org on 8/10/2017.
  */
class SimpleFontGlyphTest {

  @Test
  def demoGetXBounds(): Unit = {
    val width = 100
    val height = 100
    val image = SimpleFontGlyph.createCanvas(width, height)
    val g = image.getGraphics.asInstanceOf[Graphics2D]

    g.setColor(Color.GRAY)
    g.fillOval(50, 25, 15, 10)
    // showImage(image)

    val xs = SimpleFontGlyph.getXBounds(image)
    assertEquals((50, 64), xs)
  }

  @Test
  def demoGetYBounds(): Unit = {
    val width = 100
    val height = 100
    val image = SimpleFontGlyph.createCanvas(width, height)
    val g = image.getGraphics.asInstanceOf[Graphics2D]

    g.setColor(Color.GRAY)
    g.fillOval(50, 25, 15, 10)
    // showImage(image)

    val ys = SimpleFontGlyph.getYBounds(image)
    assertEquals((26, 34), ys) // rounding issues stable across hosts?
  }

  /**
    * Diagnostic to view a sample glyph.
    * mvn -Dtest=analyzethis.mnmnist.SimpleFontGlyphTest#renderCharacter \
    *  -DtrimStackTrace=false test
    */
  @Test
  def renderCharacter(): Unit = {
    val d = new Dimension(50, 50)
    val font = new Font(Font.SERIF, 0, 12) // font size ignored
    val g = new SimpleFontGlyph('g', font)
    val image = g.getImage(d)
    // comment-out sleep as necessary.
    val frame = showImage(image)
    Thread.sleep(5000)

    assertEquals(d.getWidth.toInt, image.getWidth(frame))
    assertEquals(d.getHeight.toInt, image.getHeight(frame))
    assertEquals((0, 49), SimpleFontGlyph.getXBounds(image))
    assertEquals((0, 49), SimpleFontGlyph.getYBounds(image))
  }

  /**
    * Debugging tool to display an image.
    * @return the viewing component
    */
  private def showImage(image: Image): JFrame = {
    val frame = new JFrame()
    frame.setSize(100, 150)
    frame.setVisible(true)
    frame.getGraphics.
      drawImage(image, 50, 50, frame)
    frame
  }
}
