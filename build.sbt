//System sbt config
import NativePackagerHelper._

enablePlugins(JavaAppPackaging)
enablePlugins(UniversalPlugin)

mappings in Universal += file("src/main/config/app.sh") -> "app.sh"
mappings in Universal ++= {
  ((sourceDirectory in Compile).value / s"config/${System.getProperty("conf")}" * "*").get.map { f =>
    f -> s"config/${f.name}"
  }
}
mappings in (Compile, packageBin) ~= { (ms: Seq[(File, String)]) =>
  ms filter {
    case (file, toPath)  =>{
      !file.getAbsolutePath.endsWith("application.conf")
    }
  }
}

addCommandAlias("pkg", ";universal:packageZipTarball")

//Config - Root
lazy val root = (project in file(".")).settings(name := "sbt-template", commonSettings, librarySettings)

lazy val commonSettings = Seq(
  organization := "com.zzcm.adx",
  scalaVersion := "2.11.8",
  version      := "1.0.1"
)

lazy val librarySettings = {
  libraryDependencies ++= Seq(
    "com.typesafe.slick" %% "slick" % "3.2.0",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.typesafe.slick" %% "slick-hikaricp" % "3.2.0",
    "mysql" % "mysql-connector-java" % "5.1.18",
    "com.typesafe" % "config" % "1.2.1",
    "net.debasishg" %% "redisclient" % "3.4"
  )
}





