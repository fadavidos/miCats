lazy val commonSettings = Seq(
  organization := "com.miCats",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.6",
  libraryDependencies ++= Seq(
    "org.scalatest"             %% "scalatest"          % "3.0.5"         % "test",
    "org.typelevel"             %% "cats-core"          % "1.4.0",
    "org.typelevel"             %% "cats-testkit"       % "1.4.0"
  )
)

lazy val Functor = (project in file("Functor"))
  .settings(
    commonSettings
  )

lazy val Pruebas = (project in file("Pruebas"))
  .settings(
    commonSettings
  )

lazy val miCats = (project in file("."))
  .aggregate(Functor, Pruebas)
  .settings(
    aggregate in update := false
  )
