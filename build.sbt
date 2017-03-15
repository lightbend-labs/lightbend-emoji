import com.typesafe.sbt.SbtGit

name := "emoji"

organization := "com.typesafe"

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

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")

javacOptions in (Compile, doc) ++=
  (CrossVersion.partialVersion(scalaVersion.value) match {
    case Some ((2, n)) if n <= 11 =>
      Seq("-target", "1.6", "-source", "1.6")
    case _ =>
      Seq()
  })

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % Test
)

initialCommands in console := {
  """import com.typesafe.emoji._
    |import com.typesafe.emoji.Emoji.Implicits._
    |import com.typesafe.emoji.ShortCodes.Implicits._
    |""".stripMargin
}

scalariformSettings
