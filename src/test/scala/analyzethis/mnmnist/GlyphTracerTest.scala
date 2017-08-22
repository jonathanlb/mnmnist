package analyzethis.mnmnist

import java.awt.Dimension

import org.junit.Test

/**
  * Created by bredin@acm.org on 8/16/2017.
  */
class GlyphTracerTest {
  /** Evergreen test for demonstration. */
  @Test
  def renderCharacter(): Unit = {
    val d = new Dimension(28, 28)
    val font = FontUtil.italic()
    val g = TracedGlyph(new SimpleFontGlyph('y', font))
    val image = g.getImage(d)

    val frame = SimpleFontGlyphTest.showImage(image)
    Thread.sleep(SimpleFontGlyphTest.displayDuration.toMillis)
  }
}
