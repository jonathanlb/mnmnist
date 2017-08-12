package analyzethis.mnmnist

import java.awt.Dimension
import java.awt.geom.AffineTransform
import java.awt.image.{AffineTransformOp, BufferedImage}

/**
  * A factory to rotate glyphs.
  *
  * Created by bredin@acm.org on 8/12/2017.
  */
class GlyphRotation(theta: Double)
  extends GlyphTransform
{
  /** The transformation to rotate glyphs. */
  override def transform(in: BufferedImage, d: Dimension): BufferedImage = {
    val cx = in.getWidth / 2
    val cy = in.getHeight / 2
    val f = new AffineTransformOp(
      AffineTransform.getRotateInstance(theta, cx, cy),
      AffineTransformOp.TYPE_BILINEAR)

    val padF = 4
    val overScaleF = 2
    val canvas = SimpleFontGlyph.createCanvas(padF*in.getWidth, padF*in.getHeight)
    canvas.getGraphics.
      drawImage(f.filter(in, null), 0, 0, null)

    SimpleFontGlyph.clipAndScale(canvas, d)
  }
}
