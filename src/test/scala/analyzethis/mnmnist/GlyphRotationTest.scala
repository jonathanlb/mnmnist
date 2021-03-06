package analyzethis.mnmnist

import java.awt.Dimension

import org.junit.Assert._
import org.junit.Test

/**
  * Created by bredin@acm.org on 8/12/2017.
  */
class GlyphRotationTest {
  /**
    * Create a struck-through q, rotate it, and check bounds.
    * Strike through to highlight the rotation.
    */
  @Test
  def renderCharacter(): Unit = {
    import math.Pi
    val d = new Dimension(50, 50)
    val font = FontUtil.strike()
    val g = RotatedGlyph(2*Pi/18, new SimpleFontGlyph('q', font))
    val image = g.getImage(d)

    // comment-out sleep as necessary.
    val frame = SimpleFontGlyphTest.showImage(image)
    Thread.sleep(SimpleFontGlyphTest.displayDuration.toMillis)

    assertEquals(d.getWidth.toInt, image.getWidth(frame))
    assertEquals(d.getHeight.toInt, image.getHeight(frame))
    assertEquals((0, 49), SimpleFontGlyph.getXBounds(image))
    assertEquals((0, 49), SimpleFontGlyph.getYBounds(image))
  }
}
