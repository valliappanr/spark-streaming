import Dependencies._

name := "SparkStreaming"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies +="org.apache.spark" %% "spark-core" % versions.spark

libraryDependencies +="org.apache.spark" %% "spark-sql" % versions.spark

libraryDependencies +="org.apache.spark" %% "spark-streaming" % versions.spark

libraryDependencies +="org.apache.spark" %% "spark-hive" % versions.spark

libraryDependencies += "org.typelevel" %% "cats" % "0.8.1"

libraryDependencies += "org.apache.spark" % "spark-streaming-kafka-0-10_2.11" % versions.spark

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "0.10.0.0"


libraryDependencies += "joda-time" % "joda-time" % "2.9.4"

libraryDependencies += "org.joda" % "joda-convert" % "1.8.1"

libraryDependencies += "redis.clients" % "jedis" % "2.9.0"

libraryDependencies += "org.elasticsearch" % "elasticsearch-spark-20_2.11" % "5.0.0-beta1"


libraryDependencies += "org.apache.kafka" % "kafka_2.11" % "0.10.0.0"

libraryDependencies += "net.debasishg" % "redisclient_2.11" % "3.3"

// https://mvnrepository.com/artifact/com.sksamuel.elastic4s/elastic4s-core_2.11
libraryDependencies += "com.sksamuel.elastic4s" % "elastic4s-core_2.11" % "5.1.5"

// https://mvnrepository.com/artifact/org.scalamock/scalamock-core_2.11
libraryDependencies += "org.scalamock" % "scalamock-core_2.11" % "3.5.0"

// https://mvnrepository.com/artifact/org.scalamock/scalamock-scalatest-support_2.11
libraryDependencies += "org.scalamock" % "scalamock-scalatest-support_2.11" % "3.5.0"

libraryDependencies ++= {
  Seq(
    "com.typesafe.akka" %% "akka-actor" % versions.akka,
    "com.typesafe.akka" %% "akka-stream-experimental" % versions.akkaStream,
    "com.typesafe.akka" %% "akka-http-experimental" % versions.akkaStream,
    "com.typesafe.akka" %% "akka-http-core-experimental" % versions.akkaStream ,
    "com.typesafe.akka" %% "akka-http-testkit-experimental" % versions.akkaStream,
    "org.scalatest" %% "scalatest" % "2.2.5" % "test",
    "com.typesafe.akka" %% "akka-testkit" % versions.akka % "test"
  )
}

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "streaming", "twitter", _*) => MergeStrategy.deduplicate
  case PathList("org", "apache", "spark", _*)              => MergeStrategy.discard
  case PathList("org", "spark_project", _*)                => MergeStrategy.discard
  case m if m.toLowerCase.endsWith("manifest.mf")          => MergeStrategy.discard
  case m if m.toLowerCase.matches("meta-inf.*\\.sf$")      => MergeStrategy.discard
  case "log4j.properties"                                  => MergeStrategy.deduplicate
  case m if m.toLowerCase.startsWith("meta-inf/services/") => MergeStrategy.filterDistinctLines
  case "reference.conf"                                    => MergeStrategy.concat
  case _                                                   => MergeStrategy.first
}