package analyzethis.mnmnist

import java.awt.Dimension

import org.junit.Assert._
import org.junit.Test

/**
  * Created by bredin@acm.org on 8/16/2017.
  */
class GlyphUnderSampleTest {
  @Test
  def renderCharacter(): Unit = {
    val d = new Dimension(50, 50)
    val font = FontUtil.plainSansSerif
    val g = UnderSampledGlyph(4.0, new SimpleFontGlyph('O', font))
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
