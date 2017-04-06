/*
 * Copyright (C) 2015-2017 Lightbend Inc. <http://www.lightbend.com>
 */
package com.typesafe.emoji

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

}
