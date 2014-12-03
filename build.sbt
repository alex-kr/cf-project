lazy val root = project.in(file(".")).enablePlugins(PlayJava)

name := """cf-project"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  "org.hibernate.javax.persistence" % "hibernate-jpa-2.1-api" % "1.0.0.Final",
  "org.hibernate" % "hibernate-core" % "4.3.6.Final",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final",
  //"mysql" % "mysql-connector-java" % "5.1.28",
  "org.postgresql" % "postgresql" % "9.3-1102-jdbc41",
  cache,
  "org.webjars" %% "webjars-play" % "2.2.2-1",
  "org.webjars" % "bootstrap" % "3.1.0",
  "org.webjars" % "bootswatch-yeti" % "3.1.1",
  "org.webjars" % "html5shiv" % "3.7.0",
  "org.webjars" % "respond" % "1.4.2"
)
