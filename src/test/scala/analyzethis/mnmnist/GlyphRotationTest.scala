package analyzethis.mnmnist

import java.awt.font.TextAttribute
import java.awt.{Dimension, Font}

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
    val attrib = new Font(Font.SERIF, Font.PLAIN, 12).
      getAttributes.
      asInstanceOf[java.util.Map[TextAttribute, Any]] // step over Java generic wildcard
    attrib.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON)
    val font = new Font(attrib)
    val f = new GlyphRotation(2*Pi/18)
    val g = f(new SimpleFontGlyph('q', font))
    val image = g.getImage(d)

    // comment-out sleep as necessary.
    val frame = SimpleFontGlyphTest.showImage(image)
    Thread.sleep(5000)

    assertEquals(d.getWidth.toInt, image.getWidth(frame))
    assertEquals(d.getHeight.toInt, image.getHeight(frame))
    assertEquals((0, 49), SimpleFontGlyph.getXBounds(image))
    assertEquals((0, 49), SimpleFontGlyph.getYBounds(image))
  }
}
