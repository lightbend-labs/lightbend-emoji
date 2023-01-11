/*
 * Copyright (C) Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

object ScalaVersionSpecific {
  def checkLengths(sc: StringContext, args: Seq[Any]): Unit =
    sc.checkLengths(args)
}
