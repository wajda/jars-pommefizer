package com.github.wajda.jarspommefizer

import com.github.wajda.jarspommefizer.model.Dependency

import scala.xml.Elem

object POMGenerator {
  def generate(dependencies: Seq[Dependency]): Elem =

    <project xmlns="http://maven.apache.org/POM/4.0.0"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">

      <modelVersion>4.0.0</modelVersion>

      <dependencies>
        {dependencies.map(dep =>
        <dependency>
          <groupId>{dep.groupId.orNull}</groupId>
          <artifactId>{dep.artifactId}</artifactId>
          <version>{dep.version}</version>
          <scope>provided</scope>
        </dependency>)}
      </dependencies>
    </project>
}
