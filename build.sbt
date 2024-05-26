import org.scalajs.linker.interface.ModuleSplitStyle
import jsenv.playwright.PWEnv

lazy val livechart = project
  .in(file("."))
  .enablePlugins(ScalaJSPlugin) // Enable the Scala.js plugin in this project
  .enablePlugins(ScalablyTypedConverterExternalNpmPlugin)
  .settings(
//    scalaVersion := "3.2.2",
    scalaVersion := "3.4.1",
    // Tell Scala.js that this is an application with a main method
    scalaJSUseMainModuleInitializer := true,
    /* Configure Scala.js to emit modules in the optimal way to
     * connect to Vite's incremental reload.
     * - emit ECMAScript modules
     * - emit as many small modules as possible for classes in the "livechart" package
     * - emit as few (large) modules as possible for all other classes
     *   (in particular, for the standard library)
     */
    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(
          ModuleSplitStyle.SmallModulesFor(List("livechart"))
        )
    },
    /* Depend on the scalajs-dom library.
     * It provides static types for the browser DOM APIs.
     */
    libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "2.4.0",
    // Depend on Laminar
    libraryDependencies ++= List(
      "com.raquo"     %%% "laminar"      % "15.0.1",
      // Testing framework
      "org.scalameta" %%% "munit"        % "0.7.29" % Test,
      "org.scalatest" %%% "scalatest"    % "3.2.18" % Test,
      "com.raquo"     %%% "domtestutils" % "18.0.1" % Test,
      "org.scalatest" %%% "scalatest"    % "3.2.18" % Test
    ),
    Test / jsEnv := new PWEnv(
      browserName = "chrome",
      headless = true,
      showLogs = true
    ),
    // Tell ScalablyTyped that we manage `npm install` ourselves
    externalNpm := baseDirectory.value,
//    Test / fastOptJS
    (Test / parallelExecution) := false,
    (Test / requireJsDomEnv) := true,
    (installJsdom / version) := "24.0.0"
  )
  .settings(
    Test / scalaJSUseTestModuleInitializer := true,
    scalaJSLinkerConfig ~= { _.withModuleKind(ModuleKind.ESModule) }
  )
