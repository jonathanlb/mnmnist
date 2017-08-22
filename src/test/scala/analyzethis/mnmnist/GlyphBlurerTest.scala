package analyzethis.mnmnist

import java.awt.Dimension

import org.junit.Test

/**
  * Created by bredin@acm.org on 8/17/2017.
  */
class GlyphBlurerTest {
  /** Evergreen test for demonstration. */
  @Test
  def renderCharacter(): Unit = {
    val d = new Dimension(100, 100)
    val font = FontUtil.underline()
    val g = BlurredGlyph(new SimpleFontGlyph('Z', font))
    val image = g.getImage(d)

    val frame = SimpleFontGlyphTest.showImage(image)
    Thread.sleep(SimpleFontGlyphTest.displayDuration.toMillis)
  }
}
