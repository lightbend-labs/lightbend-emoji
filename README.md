# Typesafe Emoji

Typesafe Emoji is a wrapper around Java's Unicode Character handling. 

## Examples

For an example of Typesafe Emoji in an Enterprise Mission Critical Environment, please see [Typesafe Emojr](https://github.com/typesafehub/typesafe-emojr).

## Installation

Add to build.sbt

```
resolvers += Resolver.typesafeIvyRepo("releases")

libraryDependencies += "com.typesafe" %% "emoji" % "1.0.0"
```

## Usage

```
$ sbt console
scala> import com.typesafe.emoji._
scala> import com.typesafe.emoji.Emoji.Implicits._
```

You can map Emoji directly from a Unicode character:

```
scala> Emoji(0x1f603)
res0: comp.typesafe.emoji.Emoji = 😃
```

Or you can map the implicit from Int or String:

```
scala> 0x1f603.emoji
res1: comp.typesafe.emoji.Emoji = 😃
scala> "0x1f603".codePointEmoji
res2: com.typesafe.emoji.Emoji = 😃
```

Once you have an emoji, you can ask it for codePoint (Int) or hexidecimal value:

```
scala> res2.hex
res3: String = 0x1f603
scala> res2.codePoint
res4: Int = 128515
```

Unicode codepoints are not very convenient, so there's a ShortCodes class which is designed to be used as an implicit parameter for emoji mapping.

There is a default mapping available, which allows you to map from a string directly to an emoji:

```
scala> import com.typesafe.emoji.ShortCodes.Implicits._
scala> import com.typesafe.emoji.ShortCodes.Defaults._
scala> "smiley".emoji
res5: com.typesafe.emoji.Emoji = 😃
```

You can query the current mapping for short codes:

```
scala> ShortCodes.current.shortCodes.filter(_.startsWith("heart"))
res6: scala.collection.Set[String] = Set(heart_decoration, heart_eyes_cat, hearts, heart_eyes, heartpulse, heart, heartbeat)
```

Finally, you can also use your own short codes mapping:

```
scala> implicit val mycodes = new ShortCodes()
mycodes: com.typesafe.emoji.ShortCodes = com.typesafe.emoji.ShortCodes@49fd69f5
scala> mycodes.entry(Emoji(0x1f603), "yay")
scala> "yay".emoji
res1: com.typesafe.emoji.Emoji = 😃
```

## Bugs

Sadly, there is no direct mapping to [emoji-cheat-sheet](http://www.emoji-cheat-sheet.com/) or [emoji searcher](http://emoji.muan.co/), because some emoji are mapped directly to glyphs, without [Unicode involvement](http://apps.timwhitlock.info/emoji/tables/unicode), e.g. [:neckbeard:](https://signalvnoise.com/posts/3395-neckbeard).

