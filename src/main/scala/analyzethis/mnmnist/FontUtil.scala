package analyzethis.mnmnist

import java.awt.{Font, GraphicsEnvironment}
import java.awt.font.TextAttribute

/**
  * Common Font constants and operations.
  *
  * Created by bredin@acm.org on 8/12/2017.
  */
object FontUtil {
  lazy val plainMono = plain(Font.MONOSPACED)
  lazy val plainSansSerif = plain(Font.SANS_SERIF)
  lazy val plainSerif = plain(Font.SERIF)

  /** Create a new font from the source, adding bold. */
  def bold(f: Font = plainSerif): Font = {
    val attributes = getAttributes(f)
    attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD)
    new Font(attributes)
  }

  def getInstalledFontNames: Iterable[String] =
    GraphicsEnvironment.getLocalGraphicsEnvironment.
      getAvailableFontFamilyNames

  def isBold(font: Font): Boolean =
    TextAttribute.WEIGHT_BOLD == getAttributes(font).get(TextAttribute.WEIGHT)

  def isItalic(font: Font): Boolean =
    TextAttribute.POSTURE_OBLIQUE == getAttributes(font).get(TextAttribute.POSTURE)

  def isStrike(font: Font): Boolean =
    TextAttribute.STRIKETHROUGH_ON == getAttributes(font).get(TextAttribute.STRIKETHROUGH)

  def isUnderline(font: Font): Boolean =
    TextAttribute.UNDERLINE_ON == getAttributes(font).get(TextAttribute.UNDERLINE)

  /** Create a new font from the source, adding italic. */
  def italic(f: Font = plainSerif): Font = {
    val attributes = getAttributes(f)
    attributes.put(TextAttribute.POSTURE, TextAttribute.POSTURE_OBLIQUE)
    new Font(attributes)
  }

  def plain(fontName: String): Font =
    new Font(fontName, Font.PLAIN, 12)

  /** Create a new font from the source, adding strike-through. */
  def strike(f: Font = plainSerif): Font = {
    val attributes = getAttributes(f)
    attributes.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON)
    new Font(attributes)
  }

  /** Create a new font from the source, adding strike-through. */
  def underline(f: Font = plainSerif): Font = {
    val attributes = getAttributes(f)
    attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON)
    new Font(attributes)
  }

  /** Step over Java generic wildcard font attributes typing. */
  private def getAttributes(f: Font) =
    f.getAttributes.asInstanceOf[java.util.Map[TextAttribute, Any]]

}
