name          := "FileIO"
organization  := "de.htwg.se"
version       := "0.0.1"
scalaVersion  := "2.13.5"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.1.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.1.1" % "test"
libraryDependencies += "org.scala-lang.modules" % "scala-swing_2.13" % "2.1.1"
libraryDependencies += "com.google.inject" % "guice" % "4.1.0"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.10"
libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.0-M1"
libraryDependencies += "com.typesafe.play" %% "play-json-joda" % "2.9.0"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.0"
libraryDependencies += "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.4"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-xml" % "10.2.4",
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion,
  "com.typesafe.akka" %% "akka-testkit" % "2.6.8" % Test,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
  "de.heikoseeberger" %% "akka-http-play-json" % "1.32.0",
  "com.typesafe.akka" %% "akka-actor-typed" % "2.6.8",
  "org.mockito" %% "mockito-scala" % "1.16.37"
)
