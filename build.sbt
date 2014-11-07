lazy val root = project.in(file(".")).enablePlugins(PlayJava)

name := """cf-project"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Final",
  "org.hibernate" % "hibernate-core" % "4.3.6.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  "mysql" % "mysql-connector-java" % "5.1.28",
  cache,
  "org.webjars" %% "webjars-play" % "2.3.0-2",
  "org.webjars" % "bootstrap" % "3.1.1-2"
)
