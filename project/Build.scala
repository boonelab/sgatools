import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "sgatools"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq("commons-io" % "commons-io" % "2.4", "org.apache.commons" % "commons-email" % "1.3.1")

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = JAVA).settings(
      // Add your own project settings here    //"commons-io" % "commons-io" % "2.4"   
    )

}
