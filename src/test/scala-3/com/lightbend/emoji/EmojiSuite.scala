/*
 * Copyright (C) 2015-2022 Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

import Emoji.Implicits.given

class EmojiSuite extends munit.FunSuite {

  def assertFalse(b: Boolean) = assert(!b)

  val ramen  = Emoji(0x1f35c)
  val smiley = Emoji(codePoint = 128515)

  test("hex maps correctly") {
    assertEquals(ramen.hex, "0x1f35c")
  }

  test("toString shows Unicode value") {
    assertEquals(ramen.toString, "\ud83c\udf5c")
  }

  test("name returns the name") {
    assertEquals(ramen.name, "STEAMING BOWL")
  }

  test("equals is reflexive and symmetric") {
    val codePoint = 128515
    val e1 = Emoji(codePoint)
    val e2 = Emoji(codePoint)
    assert(e1.equals(e1))
    assert(e1.equals(e2))
    assert(e2.equals(e1))
  }

  test("equals knows when it's not equal") {
    val e = Emoji(codePoint = 128515)
    assertFalse(Emoji(e.codePoint + 1).equals(e))
    assertFalse(Emoji(e.codePoint - 1).equals(e))
  }

  test("hashCode is the codepoint hash") {
    val e = Emoji(codePoint = 128515)
    assertEquals(e.hashCode, e.hashCode)
    assertEquals(Emoji(e.codePoint).hashCode, e.hashCode)
    assertFalse(Emoji(e.codePoint + 1).hashCode == e.hashCode)
    assertFalse(Emoji(e.codePoint - 1).hashCode == e.hashCode)
  }

  test("smiling face with open mouth is \uD83D\uDE03") {
    assertEquals(smiley.name, "SMILING FACE WITH OPEN MOUTH")
    assertEquals(smiley.codePoint, 128515)
    assertEquals(smiley.toString, "\uD83D\uDE03")
    assertEquals(Emoji(smiley.toString).toString, smiley.toString)
    assertEquals(Emoji(smiley.chars).toString, smiley.toString)
    assertEquals(Emoji(smiley.codePoint).toString, smiley.toString)
  }

  test("map hexcode correctly") {
    assertEquals("1f35c".codePointEmoji, ramen)
  }

  test("map hexcode correctly using a leading 0x") {
    assertEquals("0x1f35c".codePointEmoji, ramen)
  }

  test("map raw integers correctly") {
    assertEquals(0x1f35c.emoji, ramen)
  }
}
