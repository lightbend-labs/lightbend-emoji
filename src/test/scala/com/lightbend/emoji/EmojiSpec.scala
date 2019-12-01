/*
 * Copyright (C) 2015-2019 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class EmojiSpec extends AnyWordSpec {

  "hex" should {

    "map correctly" in {
      val ramen = Emoji(0x1f35c)
      ramen.hex should be("0x1f35c")
    }

  }

  "toString" should {
    "return correct value" in {
      val ramen = Emoji(0x1f35c)
      ramen.toString should be("\ud83c\udf5c")
    }
  }

  "name" should {
    "return correct value" in {
      val ramen = Emoji(0x1f35c)
      ramen.name should be("STEAMING BOWL")
    }
  }

  "equals" should {
    "return true" in {
      val codePoint = 128515
      val e1 = Emoji(codePoint)
      val e2 = Emoji(codePoint)
      e1.equals(e1) shouldBe (true)
      e1.equals(e2) shouldBe (true)
      e2.equals(e1) shouldBe (true)
    }

    "return false" in {
      val e = Emoji(codePoint = 128515)
      Emoji(e.codePoint + 1).equals(e) shouldBe (false)
      Emoji(e.codePoint - 1).equals(e) shouldBe (false)
    }
  }

  "hashCode" should {
    "hash correctly" in {
      val e = Emoji(codePoint = 128515)
      e.hashCode shouldBe (e.hashCode)
      Emoji(e.codePoint).hashCode shouldBe (e.hashCode)
      Emoji(e.codePoint + 1).hashCode should not be (e.hashCode)
      Emoji(e.codePoint - 1).hashCode should not be (e.hashCode)
    }
  }

  "smiling face with open mouth" should {
    "pass sanity check \uD83D\uDE03" in {
      val e = Emoji(codePoint = 128515)
      e.name should be("SMILING FACE WITH OPEN MOUTH")
      e.codePoint should be(128515)
      e.toString should be("\uD83D\uDE03")
      Emoji(e.toString).toString shouldBe (e.toString)
      Emoji(e.chars).toString shouldBe (e.toString)
      Emoji(e.codePoint).toString shouldBe (e.toString)
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
