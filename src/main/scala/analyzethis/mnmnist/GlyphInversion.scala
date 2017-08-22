package analyzethis.mnmnist
import java.awt.Dimension
import java.awt.image.BufferedImage

/**
  * Invert black and white glyph.
  *
  * Created by bredin@acm.org on 8/16/2017.
  */
class GlyphInversion
  extends GlyphTransform
{
  /** The transformation to alter glyphs, e.g. rotation, noise, etc. */
  override def transform(in: BufferedImage, outDim: Dimension): BufferedImage = {
    val result = SimpleFontGlyph.copyImage(in, outDim)
    val raster = result.getRaster
    val pixel = new Array[Int](1)
    for {
      i <- Range(0, raster.getWidth)
      j <- Range(0, raster.getHeight)
    } raster.setPixel(i, j, GlyphInversion.invert(raster.getPixel(i, j, pixel)))
    result
  }
}

object GlyphInversion {
  private [mnmnist] val instance = new GlyphInversion
  private [mnmnist] val invert = instance.transform _
  private [mnmnist] def invert(pixel: Array[Int]): Array[Int] = {
    pixel(0) = 65535 - pixel(0)
    pixel
  }
}

object InvertedGlyph {
  def apply(g: Glyph): Glyph =
    new Glyph {
      override val c: Char = g.c
      override def getImage(d: Dimension): BufferedImage =
        GlyphInversion.instance.transform(g.getImage(d), d)
      override def toString: String = s"$g-inverted"
    }
}

