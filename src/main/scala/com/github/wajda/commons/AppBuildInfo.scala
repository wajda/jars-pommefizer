package com.github.wajda.commons

import java.util.Properties
import ARM._

object AppBuildInfo {
  val buildProps: Properties = using(this.getClass getResourceAsStream "/build.properties") { stream =>
    new Properties() {
      load(stream)
    }
  }

  val version: String = buildProps getProperty "build.version"
  val timestamp: String = buildProps getProperty "build.timestamp"
}
