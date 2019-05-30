name := "emoji"

organization := "com.lightbend"

version := "1.2.2-SNAPSHOT"

crossScalaVersions := Seq("2.12.8", "2.11.12", "2.13.0-RC3")
scalaVersion := crossScalaVersions.value.head

bintrayOrganization := Some("typesafe")
bintrayRepository := "ivy-releases"
bintrayPackage := "emoji"
bintrayReleaseOnPublish := false

publishMavenStyle := false

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings", "-Xlint")

scalacOptions in (Compile, console) ~= (_ filterNot Set(
  "-Xlint",
  "-Xfatal-warnings"
))

unmanagedSourceDirectories in Compile += {
  val sourceDir = (sourceDirectory in Compile).value
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
    case _                       => sourceDir / "scala-2.13-"
  }
}

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.8-RC5" % Test
)

initialCommands in console := {
  """import com.lightbend.emoji._
    |import com.lightbend.emoji.Emoji.Implicits._
    |import com.lightbend.emoji.ShortCodes.Implicits._
    |""".stripMargin
}
