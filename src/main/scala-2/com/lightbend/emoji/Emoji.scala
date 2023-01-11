/*
 * Copyright (C) Lightbend Inc. <http://www.lightbend.com>
 */
package com.lightbend.emoji

import scala.util.Try

/**
 * The value class representing a codepoint in the Emoji set.
 *
 * @param codePoint
 *   the codepoint representing the Emoji character
 */
class Emoji private (val codePoint: Int) extends AnyVal {

  /**
   * Returns the sequence of characters (usually surrogate pairs).
   */
  def chars: Array[Char] = Character.toChars(codePoint)

  /**
   * Returns the emoji's Unicode name.
   */
  def name: String = Emoji.name(this.codePoint)

  /**
   * Returns the hexadecimal code.
   *
   * @return
   */
  def toHexString: String = Emoji.toHexString(this.codePoint)

  /**
   * A convenience method that prepends "0x" before toHexString
   *
   * @return
   */
  def hex: String = "0x" + toHexString

  /**
   * Returns the emoji as a String. Use this if you want smiley faces.
   */
  override def toString = new String(chars)
}

class EmojiNotFound(msg: String) extends RuntimeException(msg)

/**
 * This is the singleton object for Emoji.
 */
object Emoji {

  object Implicits {

    implicit class RichStringEmoji(string: String) {

      // Don't want this to conflict with shortCode.emoji
      def codePointEmoji: Emoji = {
        val codePoint = Try(Integer.parseInt(string, 16)).recover {
          case e: NumberFormatException =>
            val stripped = string.replace("0x", "")
            Integer.parseInt(stripped, 16)
        }.getOrElse {
          throw new EmojiNotFound("Cannot parse emoji from hexadecimal string")
        }
        Emoji(codePoint)
      }
    }

    implicit class RichIntEmoji(codePoint: Int) {
      def emoji: Emoji = {
        Emoji(codePoint)
      }
    }

  }

  /**
   * Returns the emoji for the given array of characters, throws EmojiNotFound
   */
  def apply(chars: Array[Char]): Emoji = {
    val codePoint = Character.codePointAt(chars, 0)
    apply(codePoint)
  }

  /**
   * Returns the emoji given a string containing the codepoint.
   */
  def apply(string: String): Emoji = {
    val codePoint = string.codePointAt(0)
    apply(codePoint)
  }

  /**
   * Returns the emoji for this codepoint if found, throws EmojiNotFound otherwise.
   */
  def apply(codePoint: Int): Emoji = {
    validate(codePoint)
    new Emoji(codePoint)
  }

  /**
   * Returns Some(emoji) if found, None otherwise.
   */
  def get(codePoint: Int): Option[Emoji] = {
    if (Emoji.isEmoji(codePoint)) {
      Some(new Emoji(codePoint))
    } else {
      None
    }
  }

  /**
   * Returns true if this is a valid Unicode codepoint, false otherwise.
   */
  def isEmoji(codePoint: Int): Boolean = {
    // there is no codeblock for unicode.
    //    val block = UnicodeBlock.of(codePoint)
    //    block == Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS ||
    //      block == Character.UnicodeBlock.EMOTICONS ||
    //      block == Character.UnicodeBlock.TRANSPORT_AND_MAP_SYMBOLS ||
    //      block == Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS ||
    //      block == Character.UnicodeBlock.DINGBATS
    Character.isValidCodePoint(codePoint)
  }

  /**
   * Throws an exception if this is not a valid Unicode codepoint.
   */
  def validate(codePoint: Int): Unit = {
    if (!Emoji.isEmoji(codePoint)) {
      throw new EmojiNotFound("Code point is not emoji!")
    }
  }

  /**
   * Returns the unicode name for this codePoint. Throws EmojiNotFound if this is not a valid
   * codepoint.
   *
   * @param codePoint
   *   the codePoint.
   * @return
   *   the unicode description of the emoji.
   */
  def name(codePoint: Int): String = {
    Option(Character.getName(codePoint)).getOrElse {
      throw new EmojiNotFound("No name found for this codePoint")
    }
  }

  /**
   * Returns the hexadecimal string of the code point.
   */
  def toHexString(codePoint: Int): String = {
    codePoint.toHexString
  }

}
