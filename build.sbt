/// publishing

name := "emoji"
organization := "com.lightbend"
homepage := Some(url("https://github.com/lightbend/lightbend-emoji"))
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(ScmInfo(
  url("https://github.com/lightbend/scala-logging"),
  "scm:git:git@github.com:lightbend/scala-logging.git"))
developers := List(
  Developer(
    id = "Lightbend",
    name = "Lightbend, Inc.",
    email = "",
    url = url("https://www.lightbend.com")))

ThisBuild / dynverVTagPrefix := false

/// build

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-wordspec"       % "3.2.7" % Test,
  "org.scalatest" %% "scalatest-shouldmatchers" % "3.2.7" % Test,
)

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-Xfatal-warnings") ++ (
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, _)) => Seq("-Xlint")
    case _ => Seq.empty
  })

Compile / console / scalacOptions ~= (_ filterNot Set(
  "-Xlint",
  "-Xfatal-warnings"
))

Test / scalacOptions ~= (_ filterNot Set(
  "-Xfatal-warnings"
))

Compile / unmanagedSourceDirectories += {
  val sourceDir = (Compile / sourceDirectory).value
  CrossVersion.partialVersion(scalaVersion.value) match {
    case Some((2, n)) if n >= 13 => sourceDir / "scala-2.13+"
    case Some((3, _))            => sourceDir / "scala-2.13+"
    case _                       => sourceDir / "scala-2.13-"
  }
}

console / initialCommands := {
  """import com.lightbend.emoji._
    |import com.lightbend.emoji.Emoji.Implicits._
    |import com.lightbend.emoji.ShortCodes.Implicits._
    |""".stripMargin
}

/// MiMa

// 1.2.1 was published under com.typesafe, so we need a resolver
// so MiMa can resolve it
resolvers += Resolver.typesafeIvyRepo("releases")
mimaPreviousArtifacts := (CrossVersion.partialVersion(scalaVersion.value) match {
  // we haven't published for Scala 3 yet
  case Some((2, _)) => Set(organization.value %% name.value % "1.2.1")
  case _            => Set()
})
