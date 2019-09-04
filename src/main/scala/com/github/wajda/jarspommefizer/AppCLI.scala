package com.github.wajda.jarspommefizer

import java.io.{File, FileWriter, OutputStreamWriter}

import com.github.wajda.commons.ARM._
import com.github.wajda.commons.AppBuildInfo
import com.github.wajda.jarspommefizer.gidresolver.GroupIdResolver

import scala.collection.parallel.CollectionConverters._
import scala.xml.PrettyPrinter

object AppCLI extends App {
  val cliParser = new scopt.OptionParser[AppConfig]("jars-pommefizer") {
    head(
      "JARs POMmefizer - a tool that generates a pom.xml from a directory containing a bunch of JAR-files.",
      s"Version: ${AppBuildInfo.version}")

    (opt[String]('o', "output")
      valueName "<file>"
      text "Output POM-file path"
      action ((path, conf) => conf.copy(maybeOutputFile = Some(new File(path).getAbsoluteFile))))

    help("help").text("prints this usage text")

    (arg[String]("<jars-dir-path>")
      text "Path to a directory that contains a list of JAR-files"
      action ((path, conf) => conf.copy(jarsDir = new File(path).getAbsoluteFile)))
  }

  cliParser.parse(args, AppConfig()) match {
    case Some(AppConfig(jarsDir, maybeOutFile)) =>
      val writer =
        maybeOutFile
          .map { file =>
            file.getParentFile.mkdirs()
            new FileWriter(file)
          }
          .getOrElse(
            new OutputStreamWriter(Console.out))

      val jarFiles = jarsDir
        .listFiles
        .toSeq
        .filter(_.getName.endsWith(".jar"))
        .sortBy(_.getName.toLowerCase)

      val dependencyResolver = new JarToDependencyConverter(new GroupIdResolver)
      val dependencies = jarFiles.par.map(dependencyResolver.convert)
      val pomXml = POMGenerator.generate(dependencies.seq)
      val pomXmlContent = new PrettyPrinter(999, 4).format(pomXml)

      using(writer)(_.write(pomXmlContent))

    case None => sys.exit(1)
  }
}
