import com.typesafe.sbt.SbtGit

name := "emoji"

organization := "com.typesafe"

version := "1.1.0"

lazy val scala210Version = "2.10.5"
lazy val scala211Version = "2.11.8"
lazy val scala212Version = "2.12.1"

scalaVersion := scala212Version

crossScalaVersions := Seq(scala212Version, scala211Version, scala210Version)

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

val typesafeIvyReleases = Resolver.url("typesafe-ivy-private-releases", new URL("http://private-repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)

publishTo := Some(typesafeIvyReleases)

publishMavenStyle := false

scalacOptions <<= (scalaVersion) map { sv =>
  Seq("-unchecked", "-deprecation") ++
    { if (sv.startsWith("2.9")) Seq.empty else Seq("-feature") }
}

javacOptions in (Compile, doc) := Seq("-target", "1.6", "-source", "1.6")

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.0" % Test
)

initialCommands in console := {
  """import com.typesafe.emoji._
    |import com.typesafe.emoji.Emoji.Implicits._
    |import com.typesafe.emoji.ShortCodes.Implicits._
    |""".stripMargin
}

scalariformSettings
