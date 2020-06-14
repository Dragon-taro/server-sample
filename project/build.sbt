resolvers ++= Seq(
  "JBoss" at "https://repository.jboss.org/",
  Resolver.url(
    "bintray-sbt-plugin-releases",
    url("http://dl.bintray.com/content/sbt/sbt-plugin-releases")
  )(Resolver.ivyStylePatterns),
  Resolver.sonatypeRepo("releases"),
  Classpaths.sbtPluginReleases
)
