val versionCats = "1.6.0"

lazy val commonSettings = Seq(
  organization := "com.miCats",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.8",
  libraryDependencies ++= Seq(
    "org.scalatest"             %% "scalatest"          % "3.0.5"         % "test",
    "com.typesafe"              % "config"              % "1.3.2",
    "org.typelevel"             %% "cats-core"          % versionCats,
    "org.typelevel"             %% "cats-testkit"       % versionCats
  )
)

lazy val Categorias = (project in file("Functor"))
  .settings(
    commonSettings
  )

lazy val miCats = (project in file("."))
  .aggregate(Categorias)
  .settings(
    aggregate in update := false
  )
