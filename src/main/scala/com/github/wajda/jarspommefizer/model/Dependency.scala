package com.github.wajda.jarspommefizer.model

case class Dependency(
  groupId: Option[String],
  artifactId: String,
  version: String
)
