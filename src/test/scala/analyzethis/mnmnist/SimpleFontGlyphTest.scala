package analyzethis.mnmnist

import java.awt._
import javax.swing.JFrame

import org.junit.Assert._
import org.junit.Test

import scala.concurrent.duration._

/**
  * Demo features with some basic diagnostics to GUI head.
  *
  * Created by bredin@acm.org on 8/10/2017.
  */
class SimpleFontGlyphTest {
  import SimpleFontGlyphTest._

  @Test
  def demoGetXBounds(): Unit = {
    val width = 100
    val height = 100
    val image = SimpleFontGlyph.createCanvas(width, height)
    val g = image.getGraphics

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
    val g = image.getGraphics

    g.setColor(Color.GRAY)
    g.fillOval(50, 25, 15, 10)
    // showImage(image)

    val ys = SimpleFontGlyph.getYBounds(image)
    assertEquals((26, 34), ys) // rounding issues stable across hosts?
  }

  /**
    * Diagnostic to view a sample glyph.
    * mvn -Dtest=analyzethis.mnmnist.SimpleFontGlyphTest#renderCharacter \
    * -DtrimStackTrace=false test
    */
  @Test
  def renderCharacter(): Unit = {
    val d = new Dimension(50, 50)
    val font = new Font(Font.SERIF, 0, 12) // font size ignored
    val g = new SimpleFontGlyph('g', font)
    val image = g.getImage(d)
    val frame = showImage(image)
    Thread.sleep(SimpleFontGlyphTest.displayDuration.toMillis)

    assertEquals(d.getWidth.toInt, image.getWidth(frame))
    assertEquals(d.getHeight.toInt, image.getHeight(frame))
    assertEquals((0, 49), SimpleFontGlyph.getXBounds(image))
    assertEquals((0, 49), SimpleFontGlyph.getYBounds(image))
  }
}

object SimpleFontGlyphTest {
  /**
    * Sleep duration for display during tests.
    * Use example:
    * mvn -Dtest=analyzethis.mnmnist.GlyphBlurerTest#renderCharacter -Danalyzethis.display_seconds=60 test
    */
  val displayDuration: FiniteDuration =
    Option(System.getProperty("analyzethis.display_seconds")).
      map(_.toInt).
      getOrElse(0).
      seconds

  /**
    * Debugging tool to display an image.
    *
    * @return the viewing component
    */
  private [mnmnist] def showImage(image: Image): JFrame = {
    val marginPx = 50
    val frame = new JFrame()
    frame.setSize(image.getWidth(null) + 2*marginPx, image.getHeight(null) + 2*marginPx)
    frame.setVisible(true)
    frame.getGraphics.
      drawImage(image, marginPx, marginPx, frame)
    frame
  }
}
