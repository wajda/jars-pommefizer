package com.github.wajda.commons

import scala.language.reflectiveCalls

object ARM {

  type Closeable = {def close(): Unit}

  def managed[T <: Closeable, U](resource: => T): ResourceWrapper[T] = new ResourceWrapper(resource)

  def managed[T <: Closeable, U](fn: T => U): T => U = using(_)(fn)

  def using[T <: Closeable, U](resource: => T)(body: T => U): U = {
    val res = resource
    try body(res)
    finally res.close()
  }

  /**
   * Implements a for-comprehension contract
   */
  class ResourceWrapper[ResourceType <: Closeable](resource: => ResourceType) {
    def foreach(f: ResourceType => Unit): Unit = using(resource)(f)

    def map[ResultType](body: ResourceType => ResultType): ResultType = using(resource)(body)

    def flatMap[ResultType](body: ResourceType => ResultType): ResultType = using(resource)(body)

    def withFilter(f: ResourceType => Boolean): ResourceWrapper[ResourceType] = this
  }

}
