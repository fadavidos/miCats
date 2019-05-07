val versionCats = "1.6.0"

organization := "com.miCats"
version := "0.1.0-SNAPSHOT"
scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

libraryDependencies ++= Seq(
  "org.scalatest"     %%  "scalatest"     % "3.0.5"       %   "test",
  "com.typesafe"      %   "config"        % "1.3.2",
  "org.typelevel"     %%  "cats-core"     % versionCats,
  "org.typelevel"     %%  "cats-testkit"  % versionCats
)
