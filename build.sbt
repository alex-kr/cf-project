lazy val root = project.in(file(".")).enablePlugins(PlayJava)

name := """cf-project"""

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  "mysql" % "mysql-connector-java" % "5.1.28",
  cache
)
