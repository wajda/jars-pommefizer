package com.github.wajda.jarspommefizer

import java.io.File

import com.github.wajda.jarspommefizer.JarToDependencyConverter._
import com.github.wajda.jarspommefizer.gidresolver.GroupIdResolver
import com.github.wajda.jarspommefizer.model.Dependency

import scala.util.matching.Regex

object JarToDependencyConverter {
  val JarFileNameRegex: Regex = """(.*?)-(\d[.\d]*(?:[-.].*)?).jar""".r
}

class JarToDependencyConverter(groupIdResolver: GroupIdResolver) {
  def convert(jarFile: File): Dependency = {
    val JarFileNameRegex(artifactId, version) = jarFile.getName
    Dependency(
      groupId = groupIdResolver.resolve(artifactId, version, jarFile),
      artifactId,
      version
    )
  }
}
