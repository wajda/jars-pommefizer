package com.github.wajda.jarspommefizer

import java.io.File

import com.github.wajda.jarspommefizer.JarToDependencyConverter.JarFileNameRegex
import com.github.wajda.jarspommefizer.gidresolver.GroupIdResolver
import com.github.wajda.jarspommefizer.model.Dependency
import org.mockito.Mockito._
import org.scalatest.{FlatSpec, Matchers}
import org.scalatestplus.mockito.MockitoSugar


class JarToDependencyConverterSpec extends FlatSpec with Matchers with MockitoSugar {

  private val mockGroupIdResolver = mock[GroupIdResolver]
  private val converter = new JarToDependencyConverter(mockGroupIdResolver)

  it should "convert" in {
    val dummyJarFile = new File("a.b-c-1.0.0-M2.jar")
    val dummyGroupId = mock[Option[String]]
    when(mockGroupIdResolver.resolve("a.b-c", "1.0.0-M2", dummyJarFile)) thenReturn dummyGroupId
    converter.convert(dummyJarFile) should be(Dependency(
      groupId = dummyGroupId,
      artifactId = "a.b-c",
      version = "1.0.0-M2"
    ))
  }

  it should "parse file names" in {
    JarFileNameRegex.unapplySeq("mesos-1.0.0-shaded-protobuf.jar") should be(Some(Seq("mesos", "1.0.0-shaded-protobuf")))
    JarFileNameRegex.unapplySeq("javax.inject-1.jar") should be(Some(Seq("javax.inject", "1")))
    JarFileNameRegex.unapplySeq("bonecp-0.8.0.RELEASE.jar") should be(Some(Seq("bonecp", "0.8.0.RELEASE")))
    JarFileNameRegex.unapplySeq("commons-lang3-2.5.jar") should be(Some(Seq("commons-lang3", "2.5")))
    JarFileNameRegex.unapplySeq("activation-1.1.1.jar") should be(Some(Seq("activation", "1.1.1")))
    JarFileNameRegex.unapplySeq("antlr-2.7.7.jar") should be(Some(Seq("antlr", "2.7.7")))
    JarFileNameRegex.unapplySeq("antlr4-runtime-4.5.3.jar") should be(Some(Seq("antlr4-runtime", "4.5.3")))
    JarFileNameRegex.unapplySeq("antlr-runtime-3.4.jar") should be(Some(Seq("antlr-runtime", "3.4")))
    JarFileNameRegex.unapplySeq("aopalliance-1.0.jar") should be(Some(Seq("aopalliance", "1.0")))
    JarFileNameRegex.unapplySeq("aopalliance-repackaged-2.4.0-b34.jar") should be(Some(Seq("aopalliance-repackaged", "2.4.0-b34")))
    JarFileNameRegex.unapplySeq("apacheds-i18n-2.0.0-M15.jar") should be(Some(Seq("apacheds-i18n", "2.0.0-M15")))
    JarFileNameRegex.unapplySeq("apacheds-kerberos-codec-2.0.0-M15.jar") should be(Some(Seq("apacheds-kerberos-codec", "2.0.0-M15")))
    JarFileNameRegex.unapplySeq("apache-log4j-extras-1.2.17.jar") should be(Some(Seq("apache-log4j-extras", "1.2.17")))
    JarFileNameRegex.unapplySeq("api-asn1-api-1.0.0-M20.jar") should be(Some(Seq("api-asn1-api", "1.0.0-M20")))
    JarFileNameRegex.unapplySeq("api-util-1.0.0-M20.jar") should be(Some(Seq("api-util", "1.0.0-M20")))
    JarFileNameRegex.unapplySeq("arpack_combined_all-0.1.jar") should be(Some(Seq("arpack_combined_all", "0.1")))
    JarFileNameRegex.unapplySeq("avro-1.7.7.jar") should be(Some(Seq("avro", "1.7.7")))
    JarFileNameRegex.unapplySeq("avro-ipc-1.7.7.jar") should be(Some(Seq("avro-ipc", "1.7.7")))
    JarFileNameRegex.unapplySeq("avro-mapred-1.7.7-hadoop2.jar") should be(Some(Seq("avro-mapred", "1.7.7-hadoop2")))
    JarFileNameRegex.unapplySeq("base64-2.3.8.jar") should be(Some(Seq("base64", "2.3.8")))
    JarFileNameRegex.unapplySeq("stax-api-1.0-2.jar") should be(Some(Seq("stax-api", "1.0-2")))
  }
}
