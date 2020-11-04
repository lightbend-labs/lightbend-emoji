name := "emoji"

organization := "com.lightbend"

version := "1.2.2-SNAPSHOT"

resolvers += Resolver.typesafeIvyRepo("releases")
mimaPreviousArtifacts := {
  if (isDotty.value)
    Set.empty
  else
    Set(organization.value %% name.value % "1.2.1")
}

bintrayOrganization := Some("typesafe")
bintrayRepository := "ivy-releases"
bintrayPackage := "emoji"
bintrayReleaseOnPublish := false

publishMavenStyle := false

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings") ++ {
  if (isDotty.value)
    Seq.empty
  else
    Seq("-Xlint")
}

scalacOptions in (Compile, console) ~= (_ filterNot Set(
  "-Xlint",
  "-Xfatal-warnings"
))

scalacOptions in (Test) ~= (_ filterNot Set(
  "-Xfatal-warnings"
))

unmanagedSourceDirectories in Compile += {
  val sourceDir = (sourceDirectory in Compile).value
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
    case Some((0 | 3, _))        => sourceDir / "scala-2.13+"
    case _                       => sourceDir / "scala-2.13-"
  }
}

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-wordspec" % "3.2.2" % Test,
  "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.2" % Test
)

initialCommands in console := {
  """import com.lightbend.emoji._
    |import com.lightbend.emoji.Emoji.Implicits._
    |import com.lightbend.emoji.ShortCodes.Implicits._
    |""".stripMargin
}
