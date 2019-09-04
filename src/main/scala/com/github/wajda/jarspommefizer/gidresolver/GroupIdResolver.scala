package com.github.wajda.jarspommefizer.gidresolver

import java.io.File
import java.util.jar.JarFile
import java.util.regex.Pattern.quote

import com.github.wajda.commons.ARM._
import com.github.wajda.jarspommefizer.gidresolver.GroupIdResolver.MavenSearchApiUrl
import org.json4s.native.JsonMethods._
import org.json4s.{DefaultFormats, Formats}
import scalaj.http.Http

import scala.jdk.CollectionConverters._

object GroupIdResolver {
  val MavenSearchApiUrl = "https://search.maven.org/solrsearch/select"
}

class GroupIdResolver {
  def resolve(artifactId: String, version: String, file: File): Option[String] = {
    using(new JarFile(file))(jarFile => {
      val pomRegex = s"META-INF\\/maven\\/([^\\\\]+)\\/${quote(artifactId)}\\/pom[.]xml".r
      val maybeGroupIdFromPOM = (for {
        e <- jarFile.entries.asScala
        m <- pomRegex.findFirstMatchIn(e.getName)
      } yield m.group(1))
        .to(LazyList)
        .headOption

      maybeGroupIdFromPOM.orElse {
        val mavenSearchResponse = Http(MavenSearchApiUrl)
          .param("wt", "json")
          .param("q", s"""a:"$artifactId" AND v:"$version"""")
          .asString
          .body

        val groups = {
          implicit val formats: Formats = DefaultFormats
          (parse(mavenSearchResponse) \ "response" \ "docs" \ "g").extract[Array[String]]
        }

        if (groups.length == 1)
          Some(groups(0))
        else for {
          mf <- Option(jarFile.getManifest)
          mfAttrs = mf.getMainAttributes
          mentionedGroup <- groups.collectFirst({ case g if mfAttrs containsValue g => g })
        } yield mentionedGroup
      }
    })
  }
}
