package analyzethis.mnmnist

import java.awt._
import java.awt.image.BufferedImage

/**
  * Created by bredin@acm.org on 8/10/2017.
  */
class SimpleFontGlyph(override val c: Char,
                      font: Font)
  extends Glyph
{
  import SimpleFontGlyph._

  /**
    * Provide a scaled and clipped image of the character glyph.
    * a) Draw an over-sized character on an even larger canvas.
    * b) Clip the area drawn.
    * c) Scale down.
    */
  override def getImage(d: Dimension): BufferedImage = {
    // Pad and draw over-sized character.
    val padF = 4
    val overScaleF = 2
    val canvas = createCanvas((padF*d.getWidth).toInt, (padF*d.getHeight).toInt)
    val g = canvas.getGraphics.asInstanceOf[Graphics2D]
    g.setFont(font.deriveFont(overScaleF*d.getHeight.toFloat))
    g.setColor(Color.BLACK)
    g.drawString(c.toString, 0, (overScaleF*d.getHeight).toInt)

    // Clip and scale to fit.
    val (minY, maxY) = getYBounds(canvas)
    val (minX, maxX) = getXBounds(canvas)
    val usedHeight = maxY - minY
    val usedWidth = maxX - minX
    // AffineTransform.getScaleInstance() doesn't smooth, but Image can.
    // https://stackoverflow.com/questions/4752748/swt-how-to-do-high-quality-image-resize
    val scaleMode = if (usedHeight > d.getHeight) Image.SCALE_SMOOTH else Image.SCALE_AREA_AVERAGING
    val clippedAndScaled = canvas.getSubimage(minX, minY, usedWidth, usedHeight).
      getScaledInstance(d.getWidth.toInt, d.getHeight.toInt, scaleMode)

    val result = createCanvas(d.getWidth.toInt, d.getHeight.toInt)
    result.getGraphics.asInstanceOf[Graphics2D].
      drawImage(clippedAndScaled, 0, 0, null)
    result
  }

}

/**
  * Utility classes exposed for testing only.
  */
object SimpleFontGlyph {
  private final val GLYPH_LIMIT = 65535 // white

  private [mnmnist] def createCanvas(width: Int, height: Int): BufferedImage = {
    val image = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_GRAY)
    val g = image.getGraphics.asInstanceOf[Graphics2D]
    g.setColor(Color.WHITE)
    g.fillRect(0, 0, width, height)
    image
  }

  private [mnmnist] def getXBounds(image: BufferedImage): (Int, Int) = {
    val raster = image.getData
    val hscanLine = new Array[Int](raster.getHeight)
    val hscan = for {
      x <- 0 until raster.getWidth
      if raster.getPixels(x, 0, 1, raster.getHeight, hscanLine).exists(isNotBackground)
    } yield x
    require(hscan.nonEmpty, s"empty image $image")
    (hscan.head, hscan.last)
  }

  private [mnmnist] def getYBounds(image: BufferedImage): (Int, Int) = {
    val raster = image.getData
    val vscanLine = new Array[Int](raster.getWidth)
    val vscan = for {
      y <- 0 until raster.getHeight
      if raster.getPixels(0, y, raster.getWidth, 1, vscanLine).exists(isNotBackground)
    } yield y
    require(vscan.nonEmpty, s"empty image $image")
    (vscan.head, vscan.last)
  }

  /**
    * Identify non-background content.
    * Depends upon fill and image mode in [[createCanvas()]].
    */
  private def isNotBackground(pixel: Int): Boolean =
    pixel < GLYPH_LIMIT

}