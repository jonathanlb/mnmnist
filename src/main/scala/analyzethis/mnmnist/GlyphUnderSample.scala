package analyzethis.mnmnist
import java.awt.Dimension
import java.awt.image.BufferedImage

/**
  * Create a blocky image.
  *
  * Created by bredin@acm.org on 8/16/2017.
  */
class GlyphUnderSample(scale: Double)
  extends GlyphTransform
{
  /** The transformation to alter glyphs, e.g. rotation, noise, etc. */
  override def transform(in: BufferedImage, outDim: Dimension): BufferedImage = {
    val f = GlyphInversion.instance.transform _
    val scaleDim = new Dimension((outDim.getWidth / scale).toInt, (outDim.getHeight / scale).toInt)
    f(f(in, scaleDim), outDim)
  }
}

object UnderSampledGlyph {
  def apply(scale: Double, g: Glyph): Glyph =
    new Glyph {
      private val f = new GlyphUnderSample(scale)
      override val c: Char = g.c
      override def getImage(d: Dimension): BufferedImage =
        f.transform(g.getImage(d), d)
      override def toString: String = s"$g-scale-" + f"$scale%1.2f"
    }
}
