import sbt.Keys.libraryDependencies

ThisBuild / version       := "0.0.1"
ThisBuild / scalaVersion  := "2.13.5"
ThisBuild / trapExit := false

val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.4"

val commonDependencies = Seq(
  "org.scalactic" %% "scalactic" % "3.1.1",
  "org.scalatest" %% "scalatest" % "3.1.1" % "test",
  "org.scala-lang.modules" %% "scala-swing" % "2.1.1",
  "net.codingwell" %% "scala-guice" % "5.0.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.3.0",
  "com.typesafe.play" %% "play-json" % "2.8.1",
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
  "org.mockito" %% "mockito-scala" % "1.16.37",
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "mysql" % "mysql-connector-java" % "8.0.24",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3",
  "org.mongodb.scala" %% "mongo-scala-driver" % "4.0.4",
  "org.apache.httpcomponents" % "httpclient" % "4.5.13",
)

lazy val root = (project in file(".")).settings(
  name := "de.htwg.se.malefiz",
  libraryDependencies ++= commonDependencies,
  assemblyJarName in assembly := "root.jar",
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "module-info.class"                           => MergeStrategy.concat
    case "CHANGELOG.adoc"                              => MergeStrategy.concat
    case "unwanted.txt"                                => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  mainClass in assembly := Some("de.htwg.se.malefiz.Malefiz")
).aggregate(gameboard, fileio).dependsOn(gameboard, fileio)

lazy val gameboard = project.settings(
  name := "GameBoard",
  libraryDependencies ++= commonDependencies,
  assemblyJarName in assembly := "gameboard.jar",
  test in assembly := {},
  assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "module-info.class"                           => MergeStrategy.concat
    case "CHANGELOG.adoc"                              => MergeStrategy.concat
    case "unwanted.txt"                                => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  mainClass in assembly := Some("de.htwg.se.malefiz.gameBoardModule.GameBoardServer"),
  fullClasspath in assembly := (fullClasspath in Runtime).value
)

lazy val fileio = project.settings(
  name := "FileIO",
  libraryDependencies ++= commonDependencies,
  test in assembly := {},
  assemblyJarName in assembly := "fileio.jar",
  assemblyMergeStrategy in assembly := {
    case PathList("javax", "servlet", xs @ _*)         => MergeStrategy.first
    case PathList(ps @ _*) if ps.last endsWith ".html" => MergeStrategy.first
    case "application.conf"                            => MergeStrategy.concat
    case "module-info.class"                           => MergeStrategy.concat
    case "CHANGELOG.adoc"                              => MergeStrategy.concat
    case "unwanted.txt"                                => MergeStrategy.discard
    case x =>
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
  },
  mainClass in assembly := Some("de.htwg.se.malefiz.fileIoModule.FileIOServer")
).aggregate(gameboard).dependsOn(gameboard)