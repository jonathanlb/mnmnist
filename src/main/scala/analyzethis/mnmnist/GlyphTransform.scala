package analyzethis.mnmnist

import java.awt.Dimension
import java.awt.image.BufferedImage

/**
  * A factory structure to create altered glyphs.
  *
  * Created by bredin@acm.org on 8/12/2017.
  */
trait GlyphTransform
{
  /** The factory method. */
  def apply(g: Glyph): Glyph =
    new Glyph {
      override val c: Char = g.c
      override def getImage(d: Dimension): BufferedImage = {
        val overScaleF = 2 // Scale up the source to improve fidelity in output.
        val d2 = new Dimension(overScaleF*d.getWidth.toInt, overScaleF*d.getHeight.toInt)
        transform(g.getImage(d2), d)
      }
    }

  /** The transformation to alter glyphs, e.g. rotation, noise, etc. */
  def transform(in: BufferedImage, outDim: Dimension): BufferedImage
}
