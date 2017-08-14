package analyzethis.mnmnist

/**
  * A cut-point to allow (hide) different processes of training data selection.
  *
  * Created by bredin@acm.org on 8/14/2017.
  */
trait GlyphSampler extends Iterable[Glyph] {
  val config: WriteGlyphs.Config
}
