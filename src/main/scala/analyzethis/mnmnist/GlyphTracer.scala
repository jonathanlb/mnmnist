package analyzethis.mnmnist
import java.awt.Dimension
import java.awt.image.{BufferedImage, Kernel}

/**
  * Return edge trace of glyph.
  *
  * Created by bredin@acm.org on 8/16/2017.
  */
class GlyphTracer
  extends ConvolvingGlyphTransform
{
  override val kernel = {
    val edge = Array(-1.0f, -1.0f, -1.0f, -1.0f, 8.0f, -1.0f, -1.0f, -1.0f, -1.0f)
    new Kernel(3, 3, edge)
  }
}

object TracedGlyph {
  import GlyphInversion._

  private val trace = (new GlyphTracer).transform _

  def apply(g: Glyph): Glyph =
    new Glyph {
      override val c: Char = g.c
      override def getImage(d: Dimension): BufferedImage = {
        // make edges black on white background
        invert(trace(invert(g.getImage(d), d), d), d)
      }
      override def toString: String = s"$g-edge"
    }
}
