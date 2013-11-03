name := "workflow"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.hamcrest" % "hamcrest-core" % "1.3.RC2" % "test",
  "org.hamcrest" % "hamcrest-library" % "1.3.RC2" % "test"
)     

play.Project.playJavaSettings
