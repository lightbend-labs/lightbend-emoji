import com.typesafe.sbt.SbtGit

name := "emoji"

organization := "com.lightbend"

version := "1.1.0"

lazy val scala210Version = "2.10.6"
lazy val scala211Version = "2.11.8"
lazy val scala212Version = "2.12.1"

scalaVersion := scala212Version

crossScalaVersions := Seq(scala212Version, scala211Version, scala210Version)

credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")

val typesafeIvyReleases = Resolver.url("typesafe-ivy-private-releases", new URL("http://private-repo.typesafe.com/typesafe/ivy-releases/"))(Resolver.ivyStylePatterns)

publishTo := Some(typesafeIvyReleases)

publishMavenStyle := false

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings")

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

initialCommands in console := {
  """import com.lightbend.emoji._
    |import com.lightbend.emoji.Emoji.Implicits._
    |import com.lightbend.emoji.ShortCodes.Implicits._
    |""".stripMargin
}

scalariformSettings
