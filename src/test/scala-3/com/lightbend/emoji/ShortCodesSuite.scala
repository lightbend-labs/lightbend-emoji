/*
 * Copyright (C) 2015-2022 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

import ShortCodes.{given, *}

class ShortCodesSuite extends munit.FunSuite:

  test("map emoji to shortcodes using the implicit class") {
    val pointRightEmoji = Emoji(0x1f449)
    assertEquals(pointRightEmoji.shortCodes, Some(Set("point_right")))
  }

  test("map shortcode to emoji using the implicit class") {
    val pointRightEmoji = new String(Character.toChars(0x1f449))
    assertEquals("point_right".emoji.toString, s"$pointRightEmoji")
  }

  test("default short codes sanity check") {
    val current = ShortCodes.current
    assert(current.shortCodes.nonEmpty)
    assert(current.emojis.nonEmpty)
    current.shortCodes.foreach { shortCode =>
      current.emoji(shortCode) match {
        case Some(emoji) => assert(Emoji.isEmoji(emoji.codePoint))
        case None        => fail(s"Unable to find Emoji for shortCode '${shortCode}")
      }
    }
  }

  test("pick up the current shortCode mapping") {
    val shortCodes = ShortCodes.current
    assertEquals(shortCodes, ShortCodes.given_ShortCodes)
  }

  test("find emoji given a short code") {
    val maybeEmoji = ShortCodes.current.emoji("point_right")
    assertEquals(maybeEmoji, Some(Emoji(0x1f449)))
  }

  test("find short codes given an emoji") {
    val maybeShortCodes = ShortCodes.current.shortCodes(Emoji(0x1f449))
    assertEquals(maybeShortCodes, Some(Set("point_right")))
  }

  test("define a new custom shortcode mapping") {
    implicit val newShortCodes = new ShortCodes(Some(ShortCodes.given_ShortCodes))

    val stuckOutTongue = Emoji(0x1f61b) // aka "stuck_out_tongue"
    newShortCodes.entry(stuckOutTongue, "silly")

    val silly = ShortCodes.current.emoji("silly").get
    assertEquals(stuckOutTongue, silly)
  }

  test("allow short names between colons") {
    val eye = "I"
    val heart = "heart".emoji

    assertEquals(e"$eye :heart: Scala", s"I $heart Scala")
  }

  test("ignore short names in interpolated args") {
    val eye = "I"
    val heart = ":heart:"
    assertEquals(e"$eye $heart Scala", s"I :heart: Scala")
  }

  test("accept double colon as double colon") {
    val heart = "heart".emoji
    assertEquals(e"List of fave emojis = :heart: :: Nil", s"List of fave emojis = $heart :: Nil")
  }

  test("accept colon not followed by emoji char as literal colon") {
    val smiley = "smiley".emoji
    assertEquals(e"Dear Customer: Have a nice day! :) :smiley:", s"Dear Customer: Have a nice day! :) $smiley")
  }

  test("also accept backslash-escaped colon as literal colon") {
    val eye = "I"
    val heart = "heart".emoji
    assertEquals(e"$eye :heart:\: Scala", s"I $heart: Scala")
    assertEquals(e"$eye \:heart:\: Scala", s"I :heart:: Scala")
    assertEquals(e"$eye \::heart:\: Scala", s"I :$heart: Scala")
  }

  test("not go kaput on bad short name") {
    val upper = "+1".emoji
    assertEquals(e":+1: Loved it! So much! :++1:", s"$upper Loved it! So much! :++1:")
  }

  test("gently ignore bad characters") {
    val upper = "+1".emoji
    assertEquals(e":+1: Love the idea of using :left arrow: in for comprehensions!",
      s"$upper Love the idea of using :left arrow: in for comprehensions!")
  }
