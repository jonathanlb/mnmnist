package analyzethis.mnmnist

import org.junit.Assert._
import org.junit.Test

/**
  * Created by bredin@acm.org on 8/14/2017.
  */
class DefaultGlyphSamplerTest {
  @Test
  def demoSampler(): Unit = {
    val config = new WriteGlyphs.Config(start = 'X', stop = 'Z')
    val gs = new DefaultGlyphSampler(config).iterator.toSeq
    assertEquals(4*3, gs.size)
    for (c <- Seq('X', 'Y', 'Z'))
      assertTrue(s"expected test glyphs to contain an $c", gs.exists(_.c == c))
  }
}
