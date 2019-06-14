name := """scala-microbenchmark"""

organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(JmhPlugin)

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.5.22",
  "com.twitter"       %% "chill-akka" % "0.9.3",
  // https://scalapb.github.io/sbt-settings.html
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  // circe
  "io.circe" %% "circe-core" % "0.11.1",
  "io.circe" %% "circe-generic" % "0.11.1",
  "io.circe" %% "circe-parser" % "0.11.1",
  // https://github.com/msgpack4z/msgpack4z-core
  "com.github.xuwei-k" %% "msgpack4z-core" % "0.3.10",
  "com.github.xuwei-k" %% "msgpack4z-native" % "0.3.5",
)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

