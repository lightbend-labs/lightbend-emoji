name := "typesafe-emoji"

version := "1.0.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

initialCommands in console := {
  """import com.typesafe.emoji._
    |import com.typesafe.emoji.ShortCodes.Implicits._
    |import com.typesafe.emoji.ShortCodes.Defaults._
    |""".stripMargin
}