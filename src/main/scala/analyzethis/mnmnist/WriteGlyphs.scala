package analyzethis.mnmnist

import java.awt.Dimension
import java.io.File
import javax.imageio.ImageIO

import com.typesafe.scalalogging.Logger

/**
  * Write glyphs similar to the NotMNIST dataset for AnalyzeThis classifier testing.
  *
  * mvn exec:java -Dexec.mainClass="analyzethis.mnmnist.WriteGlyphs" -Dexec.args="-o output"
  *
  * Created by bredin@acm.org on 8/10/2017.
  */
object WriteGlyphs {
  private val log = Logger(getClass.getSimpleName)
  private final val imageFormat = "png"

  case class Config(fonts: Seq[String] = Seq(),
                    glyphWidth: Int = 28,
                    glyphHeight: Int = 28,
                    limit: Int = 1000,
                    outputDir: String = ".",
                    start: Char = 'a',
                    stop: Char = 'j')

  def createGlyphs(config: Config): Iterable[Glyph] = {
    // new ExperimentalGlyphSampler(config)
    new DefaultGlyphSampler(config)
  }

  def createOutputDir(config: Config, g: Glyph): File = {
    val dir = new File(config.outputDir, g.c.toString)
    if (!dir.exists) dir.mkdirs
    require(dir.exists && dir.isDirectory, s"cannot create directory $dir")
    dir
  }

  def main(args: Array[String]): Unit = {
    val config = parseArgs(args)
    val glyphs = createGlyphs(config)
    writeGlyphs(config, glyphs)
  }

  def parseArgs(args: Array[String]): Config = {
    val appName = getClass.getSimpleName
    val versionStr = "0.0.1"

    val parser = new scopt.OptionParser[Config](appName) {
      head(appName)
      version(versionStr)

      opt[String]('o', "output").action((x, c) => c.copy(outputDir = x)).
        text("Output directory")
      opt[String]('s', "start").action((x, c) => {
        require(x.length == 1, s"illegal start character '$x'")
        c.copy(start = x.charAt(0))
      }).text("Start character")
      opt[String]('S', "stop").action((x, c) => {
        require(x.length == 1, s"illegal stop character '$x'")
        c.copy(stop = x.charAt(0))
      }).text("Stop character, inclusive")

      help("help")
    }

    parser.parse(args, Config()).get
  }

 def writeGlyphs(config: Config, glyphs: Iterable[Glyph]): Unit = {
    val d = new Dimension(config.glyphWidth, config.glyphHeight)
    for ((g,i) <- scala.util.Random.shuffle(glyphs).
      take(config.limit.min(glyphs.size)).
      zipWithIndex)
    {
      val dir = createOutputDir(config, g)
      val f = new File(dir, s"$i-$g.$imageFormat")
      val image = g.getImage(d)
      log.info(s"writing $f")
      ImageIO.write(image, imageFormat, f)
    }
  }
}
