/// publishing

name := "emoji"
organization := "com.lightbend"
homepage := Some(url("https://github.com/lightbend/lightbend-emoji"))
licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0.html"))
scmInfo := Some(ScmInfo(
  url("https://github.com/lightbend/lightbend-emoji"),
  "scm:git:git@github.com:lightbend/lightbend-emoji.git"))
developers := List(
  Developer(
    id = "Lightbend",
    name = "Lightbend, Inc.",
    email = "",
    url = url("https://www.lightbend.com")))

ThisBuild / dynverVTagPrefix := false
ThisBuild / versionScheme := Some("early-semver")

/// build

crossScalaVersions := Seq("2.13.5", "2.12.14", "3.0.0-RC2", "3.0.0-RC3")
scalaVersion := crossScalaVersions.value.head

libraryDependencies ++= {
  val scalaTestVersion =
    if (scalaVersion.value == "3.0.0-RC2") "3.2.7"
    else "3.2.8"
  Seq(
    "org.scalatest" %% "scalatest-wordspec"       % scalaTestVersion % Test,
    "org.scalatest" %% "scalatest-shouldmatchers" % scalaTestVersion % Test,
  )
}

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
