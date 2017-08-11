package analyzethis.mnmnist

/**
  * Write glyphs similar to the NotMNIST dataset for AnalyzeThis classifier testing.
  *
  * Created by bredin@acm.org on 8/10/2017.
  */
object WriteGlyphs {
  case class Config(fonts: Seq[String] = Seq(),
                    outputDir: String = ".",
                    start: Char = 'a',
                    stop: Char = 'j')

  def createGlyphs(config: Config): Seq[String] = {
    for (i <- Range(config.start, config.stop + 1))
      yield i.toString
  }

  def main(args: Array[String]): Unit = {
    val config = parseArgs(args)
    val glyphs = createGlyphs(config)
    println(glyphs.mkString(" "))
  }

  def parseArgs(args: Array[String]): Config = {
    val appName = getClass.getSimpleName
    val version = "0.0.1"

    val parser = new scopt.OptionParser[Config](appName) {
      head(appName)

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
}
