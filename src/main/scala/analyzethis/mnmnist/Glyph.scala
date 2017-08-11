package analyzethis.mnmnist

import java.awt.Dimension
import java.awt.image.BufferedImage

/**
  * Represent a character for our corpus.
  *
  * Created by bredin@acm.org on 8/10/2017.
  */
trait Glyph {
    val c: Char
    def getImage(d: Dimension): BufferedImage
}




