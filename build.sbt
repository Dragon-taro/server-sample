import Dependencies._

name := "server-sample"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies += twitterServer % Compile
libraryDependencies += finchCore % Compile
libraryDependencies += finagleHttp % Compile
libraryDependencies += twitterUtil % Compile
libraryDependencies += scalaMock % Test
libraryDependencies += scalaTest % Test
