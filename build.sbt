lazy val commonSettings = Seq(
  organization := "com.miCats",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.6",
  libraryDependencies ++= Seq(
    "org.scalatest"             %% "scalatest"          % "3.0.5"         % "test",
    "com.typesafe"              % "config"              % "1.3.2",
    "org.typelevel"             %% "cats-core"          % "1.4.0", // TODO: Actualizar versión de cats
    "org.typelevel"             %% "cats-testkit"       % "1.4.0"
  )
)

lazy val Functor = (project in file("Functor"))
  .settings(
    commonSettings
  )

lazy val miCats = (project in file("."))
  .aggregate(Functor)
  .settings(
    aggregate in update := false
  )
