package analyzethis.mnmnist

import org.junit.Assert._
import org.junit.Test

/**
  * Created by bredin@acm.org on 8/10/2017.
  */
class WriteGlyphsTest {
  @Test
  def demoParseArgs(): Unit = {
    val args = "-s 0 -S 9".split("\\s+")
    val config = WriteGlyphs.parseArgs(args)
    assertEquals('0', config.start)
    assertEquals('9', config.stop)
  }
}
