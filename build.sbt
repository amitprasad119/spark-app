name := "SparkApp"

version := "0.1"

scalaVersion := "2.12.12"
val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
  "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.spark" %% "spark-mllib" % "3.0.1",
  "com.typesafe.akka" %% "akka-actor-typed" % AkkaVersion,
  "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
 // "mysql" % "mysql-connector-java" % "8.0.22",
  //"org.postgresql" % "postgresql" % "9.4-1200-jdbc41",
  "org.apache.spark" %% "spark-hive" % "3.0.1",
  "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion

  //  "com.typesafe.scala-logging" %% "scala-logging" % "3.7.2",
//  "ch.qos.logback" % "logback-classic" % "1.2.3"
)
addSbtPlugin("org.scalameta" % "sbt-scalafmt" % "2.4.3")

