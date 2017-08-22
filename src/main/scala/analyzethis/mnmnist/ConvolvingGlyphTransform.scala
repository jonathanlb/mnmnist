package analyzethis.mnmnist

import java.awt.{Dimension, Image, RenderingHints}
import java.awt.image.{BufferedImage, ConvolveOp, Kernel}

/**
  * Created by bredin@acm.org on 8/17/2017.
  */
trait ConvolvingGlyphTransform
extends GlyphTransform
{
  val kernel: Kernel
  val edgeCondition = ConvolveOp.EDGE_ZERO_FILL
  val renderHints = null : RenderingHints

  override def transform(in: BufferedImage, outDim: Dimension): BufferedImage = {
    val scaleMode = if (in.getHeight > outDim.getHeight) Image.SCALE_SMOOTH else Image.SCALE_AREA_AVERAGING
    val image = new ConvolveOp(kernel, edgeCondition, renderHints).
      filter(in, null).
      getScaledInstance(outDim.width, outDim.height, scaleMode)

    val result = SimpleFontGlyph.createCanvas(outDim.width, outDim.height)
    result.getGraphics.
      drawImage(image, 0, 0, null)
    result
  }
}
