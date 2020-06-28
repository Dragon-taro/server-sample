import Dependencies._

name := "server-sample"

version := "0.1"

scalaVersion := "2.12.8"

scalacOptions += "-Ypartial-unification"

resolvers += Resolver.sonatypeRepo("releases")
addCompilerPlugin(
  "org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full
)

libraryDependencies += twitterServer % Compile
libraryDependencies += finchCore % Compile
libraryDependencies += finagleHttp % Compile
libraryDependencies += twitterUtil % Compile
libraryDependencies += finagleCore % Compile
libraryDependencies += finagleRedis % Compile
libraryDependencies += finagleMySQL % Compile
libraryDependencies += finchCirce % Compile
libraryDependencies += circeGeneric % Compile
libraryDependencies += doobieCore % Compile
libraryDependencies += catsCore % Compile
libraryDependencies += http4sDsl % Compile
libraryDependencies += http4sBlazeServer % Compile
libraryDependencies += http4sCirce % Compile
libraryDependencies += mysql % Compile
libraryDependencies += scalaMock % Compile
libraryDependencies += scalaTest % Compile
