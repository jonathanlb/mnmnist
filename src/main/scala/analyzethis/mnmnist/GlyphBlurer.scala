package analyzethis.mnmnist

import java.awt.{Dimension, RenderingHints}
import java.awt.image.{BufferedImage, Kernel}
import scala.collection.JavaConverters._

/**
  * Blur a glyph using a box-blur filter.
  *
  * Created by bredin@acm.org on 8/17/2017.
  */
class GlyphBlurer
extends ConvolvingGlyphTransform
{
  override val kernel = {
    val boxSize = 3
    new Kernel(boxSize, boxSize, boxBlur(boxSize))
  }

  override val renderHints =
    new RenderingHints(Map(
        RenderingHints.KEY_ANTIALIASING -> RenderingHints.VALUE_ANTIALIAS_ON,
        RenderingHints.KEY_FRACTIONALMETRICS -> RenderingHints.VALUE_FRACTIONALMETRICS_ON,
        RenderingHints.KEY_INTERPOLATION -> RenderingHints.VALUE_INTERPOLATION_BICUBIC,
        RenderingHints.KEY_RENDERING -> RenderingHints.VALUE_COLOR_RENDER_QUALITY,
        RenderingHints.KEY_TEXT_ANTIALIASING -> RenderingHints.VALUE_TEXT_ANTIALIAS_ON
    ).asJava)

  private def boxBlur(n: Int): Array[Float] = {
    val n2 = n*n
    Array.fill(n2)(1.0f / n2)
  }
}

/**
  * A factory for blurring by repeatedly applying box blur.
  * https://prolost.com/blog/2006/3/2/a-tale-of-three-blurs.html
  */
object BlurredGlyph {
  import GlyphInversion._

  private val f = new GlyphBlurer
  private val overScale = 8
  private val numBoxBlurs = 16

  def apply(g: Glyph): Glyph =
    new Glyph {
      override val c: Char = g.c
      override def getImage(d: Dimension): BufferedImage = {
        val tmpDim = new Dimension(d.width * overScale, d.height * overScale)
        def blur(image: BufferedImage) = f.transform(image, tmpDim)
        invert(
          (0 to numBoxBlurs).foldLeft(
            invert(g.getImage(tmpDim), tmpDim)
          )((image, _) => blur(image)),
          d)
      }
      override def toString: String = s"$g-blur"
    }
}