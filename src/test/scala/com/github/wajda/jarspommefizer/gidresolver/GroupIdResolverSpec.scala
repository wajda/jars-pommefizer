package com.github.wajda.jarspommefizer.gidresolver

import java.io.File

import com.github.wajda.jarspommefizer.gidresolver.GroupIdResolverSpec.MavenRepoDir
import org.scalatest.{FlatSpec, Matchers}

class GroupIdResolverSpec extends FlatSpec with Matchers {

  private val resolver = new GroupIdResolver

  it should "resolve commons-lang3" in {
    val groupId = resolver.resolve(
      "commons-lang3",
      "3.5",
      new File(MavenRepoDir, "org/apache/commons/commons-lang3/3.5/commons-lang3-3.5.jar"))

    groupId should be(Some("org.apache.commons"))
  }

  it should "resolve commons-digester" in {
    val groupId = resolver.resolve(
      "commons-digester",
      "1.8",
      new File(MavenRepoDir, "commons-digester/commons-digester/1.8/commons-digester-1.8.jar"))

    groupId should be(Some("commons-digester"))
  }

  it should "resolve objenesis" in {
    val groupId = resolver.resolve(
      "objenesis",
      "2.1",
      new File(MavenRepoDir, "org/objenesis/objenesis/2.1/objenesis-2.1.jar"))

    groupId should be(Some("org.objenesis"))
  }

  it should "resolve javax.inject" in {
    val groupId = resolver.resolve(
      "javax.inject",
      "1",
      new File(MavenRepoDir, "javax/inject/javax.inject/1/javax.inject-1.jar"))

    groupId should be(Some("javax.inject"))
  }

}

object GroupIdResolverSpec {
  private val MavenRepoDir = new File(System.getProperty("user.home"), ".m2/repository")
}
