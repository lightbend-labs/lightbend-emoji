/*
 * Copyright (C) 2015-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

import org.scalatest.Matchers._
import org.scalatest._

import ShortCodes.Implicits._

class ShortCodesSpec extends WordSpec {

  "ShortCodes.Implicits" should {

    "map emoji to shortcodes using the implicit class" in {
      import ShortCodes.Defaults._

      val pointRightEmoji = Emoji(0x1f449)
      pointRightEmoji.shortCodes should be(Some(Set("point_right")))
    }

    "map shortcode to emoji using the implicit class" in {
      import ShortCodes.Defaults._

      val pointRightEmoji = new String(Character.toChars(0x1f449))
      "point_right".emoji.toString should be(s"$pointRightEmoji")
    }

  }

  "ShortCodes" should {

    "default short codes sanity check" in {
      import ShortCodes.Defaults._

      val current = ShortCodes.current
      current.shortCodes.size should be > 0
      current.emojis.size should be > 0
      current.shortCodes.foreach { shortCode =>
        current.emoji(shortCode) match {
          case Some(emoji) => Emoji.isEmoji(emoji.codePoint) shouldBe true
          case None => fail(s"Unable to find Emoji for shortCode '${shortCode}")
        }
      }
    }


    "pick up the current shortCode mapping" in {
      import ShortCodes.Defaults._

      val shortCodes = ShortCodes.current
      shortCodes should be(ShortCodes.Defaults.defaultImplicit)
    }

    "find emoji given a short code" in {
      import ShortCodes.Defaults._

      val maybeEmoji = ShortCodes.current.emoji("point_right")
      maybeEmoji should be(Some(Emoji(0x1f449)))
    }

    "find short codes given an emoji" in {
      import ShortCodes.Defaults._

      val maybeShortCodes = ShortCodes.current.shortCodes(Emoji(0x1f449))
      maybeShortCodes should be(Some(Set("point_right")))
    }

    "define a new custom shortcode mapping" in {
      implicit val newShortCodes = new ShortCodes(Some(ShortCodes.Defaults.defaultImplicit))

      val stuckOutTongue = Emoji(0x1f61b) // aka "stuck_out_tongue"
      newShortCodes.entry(stuckOutTongue, "silly")

      val silly = ShortCodes.current.emoji("silly").get
      stuckOutTongue should be(silly)
    }

  }

  "The Emojilator" should {

    "allow short names between colons" in {
      import ShortCodes.Defaults._

      val eye = "I"
      val heart = "heart".emoji

      e"$eye :heart: Scala" should be(s"I $heart Scala")
    }

    "ignore short names in interpolated args" in {
      import ShortCodes.Defaults._

      val eye = "I"
      val heart = ":heart:"

      e"$eye $heart Scala" should be(s"I :heart: Scala")
    }

    "accept double colon as double colon" in {
      import ShortCodes.Defaults._

      val heart = "heart".emoji

      e"List of fave emojis = :heart: :: Nil" should be(s"List of fave emojis = $heart :: Nil")
    }

    "accept colon not followed by emoji char as literal colon" in {
      import ShortCodes.Defaults._

      val smiley = "smiley".emoji

      e"Dear Customer: Have a nice day! :) :smiley:" should be(s"Dear Customer: Have a nice day! :) $smiley")
    }

    "also accept backslash-escaped colon as literal colon" in {
      import ShortCodes.Defaults._

      val eye = "I"
      val heart = "heart".emoji

      e"$eye :heart:\: Scala" should be(s"I $heart: Scala")
      e"$eye \:heart:\: Scala" should be(s"I :heart:: Scala")
      e"$eye \::heart:\: Scala" should be(s"I :$heart: Scala")
    }

    "not go kaput on bad short name" in {
      import ShortCodes.Defaults._

      val upper = "+1".emoji

      e":+1: Loved it! So much! :++1:" should be(s"$upper Loved it! So much! :++1:")
    }

    "gently ignore bad characters" in {
      import ShortCodes.Defaults._

      val upper = "+1".emoji

      e":+1: Love the idea of using :left arrow: in for comprehensions!" should be(
        s"$upper Love the idea of using :left arrow: in for comprehensions!")
    }
  }

}
