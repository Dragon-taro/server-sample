import sbt._

object Dependencies {
  object Versions {
    lazy val finagle = "18.7.0"
    lazy val finch = "0.23.0"
    lazy val twServer = "18.8.0"
    lazy val scalaMock = "4.4.0"
    lazy val scalaTest = "3.0.5"
  }

  // web server
  lazy val finchCore = "com.github.finagle" %% "finch-core" % Versions.finch
  lazy val finagleHttp = "com.twitter" %% "finagle-http" % Versions.finagle
  lazy val twitterServer = "com.twitter" %% "twitter-server" % Versions.twServer

  // others
  lazy val twitterUtil = "com.twitter" %% "util-core" % "6.42.0"
  lazy val finagleRedis = "com.twitter" %% "finagle-redis" % Versions.finagle

  // test
  lazy val scalaTest = "org.scalatest" %% "scalatest" % Versions.scalaTest
  lazy val scalaMock = "org.scalamock" %% "scalamock" % Versions.scalaMock
}
