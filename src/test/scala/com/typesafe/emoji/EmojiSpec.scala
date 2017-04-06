/*
 * Copyright (C) 2015-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

import org.scalatest._
import org.scalatest.Matchers._

class EmojiSpec extends WordSpec {

  "hex" should {

    "map correctly" in {
      val ramen = Emoji(0x1f35c)
      ramen.hex should be("0x1f35c")
    }

  }

  "implicits" should {

    "map hexcode correctly" in {
      import Emoji.Implicits._

      val ramen = Emoji(0x1f35c)
      "1f35c".codePointEmoji should be(ramen)
    }

    "map hexcode correctly using a leading 0x" in {
      import Emoji.Implicits._

      val ramen = Emoji(0x1f35c)
      "0x1f35c".codePointEmoji should be(ramen)
    }

    "map raw integers correctly" in {
      import Emoji.Implicits._

      val ramen = Emoji(0x1f35c)
      0x1f35c.emoji should be(ramen)
    }
  }

}
