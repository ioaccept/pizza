name := """PizzaInitial1"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "org.specs2" %% "specs2-scalacheck" % "3.5" % "test",
  "org.specs2" %% "specs2-junit" % "3.5" % "test",
  "org.specs2" %% "specs2-mock" % "3.5" % "test",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4"
)


