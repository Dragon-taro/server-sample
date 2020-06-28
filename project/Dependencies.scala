import sbt._

object Dependencies {
  object Versions {
    lazy val finagle = "18.7.0"
    lazy val finch = "0.23.0"
    lazy val twServer = "18.8.0"
    lazy val scalaMock = "4.4.0"
    lazy val scalaTest = "3.0.5"
    lazy val circe = "0.13.0"
    lazy val http4s = "0.21.4"
  }

  // web server
  lazy val finchCore = "com.github.finagle" %% "finch-core" % Versions.finch
  lazy val finagleHttp = "com.twitter" %% "finagle-http" % Versions.finagle
  lazy val twitterServer = "com.twitter" %% "twitter-server" % Versions.twServer

  // others
  lazy val twitterUtil = "com.twitter" %% "util-core" % "6.42.0"
  lazy val finagleCore = "com.twitter" %% "finagle-core" % Versions.finagle
  lazy val finagleRedis = "com.twitter" %% "finagle-redis" % Versions.finagle
  lazy val finagleMySQL = "com.twitter" %% "finagle-mysql" % Versions.finagle
  lazy val finchCirce = "com.github.finagle" %% "finch-circe" % Versions.finch
  lazy val doobieCore = "org.tpolecat" %% "doobie-core" % "0.8.8"
  lazy val catsCore = "org.typelevel" %% "cats-core" % "2.0.0"
  lazy val http4sCirce = "org.http4s" %% "http4s-circe" % Versions.http4s
  lazy val http4sDsl = "org.http4s" %% "http4s-dsl" % Versions.http4s
  lazy val http4sBlazeServer = "org.http4s" %% "http4s-blaze-server" % Versions.http4s
  lazy val circeGeneric = "io.circe" %% "circe-generic" % Versions.circe
  lazy val circeLiteral = "io.circe" %% "circe-literal" % Versions.circe
  lazy val mysql = "mysql" % "mysql-connector-java" % "5.1.12"

  // test
  lazy val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest
  lazy val scalaMock = "org.scalamock" %% "scalamock" % Versions.scalaMock
}
