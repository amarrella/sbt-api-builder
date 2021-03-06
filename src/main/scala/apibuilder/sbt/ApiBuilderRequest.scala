package apibuilder.sbt

import java.nio.file.{Path, PathMatcher}

final case class ApiBuilderRequest(path: String, matchers: Seq[PathMatcher], maybeTargetPath: Option[Path])

object ApiBuilderRequests {
  def fromCLIConfig(cliConfig: CLIConfig): Seq[ApiBuilderRequest] =
    for {
      (org, orgConfig)                                          <- cliConfig.organizationFor.toList
      (app, ApplicationConfig(version, generators))             <- orgConfig.applicationFor
      GeneratorConfig(generator, maybeTargetPath, pathMatchers) <- generators
    } yield ApiBuilderRequest(s"$org/$app/$version/$generator", pathMatchers, maybeTargetPath)
}
