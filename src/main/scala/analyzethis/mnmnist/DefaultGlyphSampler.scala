package analyzethis.mnmnist

/**
  * Generate a simple corpus of plain, bold, and italic letters.
  *
  * Created by bredin@acm.org on 8/14/2017.
  */
class DefaultGlyphSampler(override val config: WriteGlyphs.Config)
  extends GlyphSampler
{
  override def iterator: Iterator[Glyph] =
    (for {
      bold <- Seq(true, false)
      italic <- Seq(true, false)
    } yield {
      var font = FontUtil.plainSerif
      if (bold) font = FontUtil.bold(font)
      if (italic) font = FontUtil.italic(font)

      for (i <- Range(config.start, config.stop + 1, 1))
        yield new SimpleFontGlyph(i.toChar, font)
    }).flatten.
      iterator
}
