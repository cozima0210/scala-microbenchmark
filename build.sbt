name := """scala-microbenchmark"""

organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(JmhPlugin)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.22",
  "com.twitter"       %% "chill-akka" % "0.9.3"
)
