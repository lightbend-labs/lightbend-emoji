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

  "toString" should {
    "return correct value" in {
      val ramen = Emoji(0x1f35c)
      ramen.toString should be ("\ud83c\udf5c")
    }
  }

  "name" should {
    "return correct value" in {
      val ramen = Emoji(0x1f35c)
      ramen.name should be ("STEAMING BOWL")
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

/*

Note:  the US Flag emoji is composed of two codepoints

The current implementation of the Emoji.scala implies that an Emoji object
corresponds to exactly 1 codepoint.

This makes it impossible to construct a single Emoji object for the US flag.

  "Country flags" should {
    "US flag" in {
      // https://emojipedia.org/flag-for-united-states/
      val regionalIndicator_u = Emoji(0x1F1FA)
      val regionalIndicator_s = Emoji(0x1F1F8)
      val usFlag = regionalIndicator_u.toString + regionalIndicator_s.toString
      Character.codePointCount(usFlag, 0, usFlag.length - 1) shouldBe 2
      System.out.println("US flag: " + usFlag)
    }
  } */

}
