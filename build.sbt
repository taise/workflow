name := "workflow"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "org.easytesting" % "fest-assert-core" % "2.0M10" % "test"
)

play.Project.playJavaSettings
