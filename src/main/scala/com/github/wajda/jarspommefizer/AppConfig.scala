package com.github.wajda.jarspommefizer

import java.io.File

case class AppConfig(
  jarsDir: File = null,
  maybeOutputFile: Option[File] = None
)
