/*
 * Copyright (C) 2015-2021 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

object ScalaVersionSpecific {
  def checkLengths(sc: StringContext, args: Seq[Any]): Unit =
    StringContext.checkLengths(args, sc.parts)
}

