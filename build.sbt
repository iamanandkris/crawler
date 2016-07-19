name := """crawler-project"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws
)

val akkaVersion = "2.4.7"

libraryDependencies += "org.mockito" % "mockito-core" % "1.9.5" % "test"

libraryDependencies +="com.typesafe.akka" %% "akka-remote" % akkaVersion

libraryDependencies +="com.typesafe.akka" %% "akka-multi-node-testkit" % akkaVersion % "test"

libraryDependencies +="com.typesafe.akka" %%  "akka-http-core"                     % akkaVersion

libraryDependencies +="com.typesafe.akka" %%  "akka-http-experimental"             % akkaVersion

libraryDependencies +="com.typesafe.akka" %%  "akka-http-spray-json-experimental"  % akkaVersion

libraryDependencies +="com.github.romix.akka" %% "akka-kryo-serialization" % "0.4.1"
