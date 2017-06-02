

enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.1",
      version      := "0.1.0"
    )),
    name := "sbt-template",
    libraryDependencies += "com.typesafe.slick" %% "slick" % "3.2.0",
    libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.6.4",
    libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
    libraryDependencies += "mysql" % "mysql-connector-java" % "5.1.18",
    libraryDependencies += "com.typesafe" % "config" % "1.2.1"

  )

import NativePackagerHelper._
mappings in Universal += file("src/main/config/app.sh") -> "app.sh"
mappings in Universal ++= {
    ((sourceDirectory in Compile).value / s"config/${System.getProperty("conf.path")}" * "*").get.map { f => 
      f -> s"config/${f.name}"
    }
}

addCommandAlias("install", ";universal:packageZipTarball")



