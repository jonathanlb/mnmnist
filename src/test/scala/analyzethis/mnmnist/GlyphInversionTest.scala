package analyzethis.mnmnist

import java.awt.Dimension

import org.junit.Test
import org.junit.Assert._

/**
  * Created by bredin@acm.org on 8/16/2017.
  */
class GlyphInversionTest {
  @Test
  def renderCharacter(): Unit = {
    val d = new Dimension(50, 50)
    val font = FontUtil.underline()
    val g = InvertedGlyph(new SimpleFontGlyph('w', font))
    val image = g.getImage(d)

    val frame = SimpleFontGlyphTest.showImage(image)
    Thread.sleep(SimpleFontGlyphTest.displayDuration.toMillis)

    assertEquals(d.getWidth.toInt, image.getWidth(frame))
    assertEquals(d.getHeight.toInt, image.getHeight(frame))
    assertEquals((0, 49), SimpleFontGlyph.getXBounds(image))
    assertEquals((0, 46), SimpleFontGlyph.getYBounds(image))
  }

  @Test
  def invertTest(): Unit = {
    val pixel = Array(0)
    assertEquals(65535, GlyphInversion.invert(pixel)(0))
    assertEquals(0, GlyphInversion.invert(pixel)(0))
    assertSame(pixel, GlyphInversion.invert(pixel))
  }
}
