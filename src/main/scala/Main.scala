package main

import cats.effect._
import infra.router.Router
import org.http4s.server.blaze._
import org.http4s.server.middleware.CORS

import scala.concurrent.ExecutionContext.global

object Main extends IOApp {

  def run(args: List[String]): IO[ExitCode] =
    BlazeServerBuilder[IO](global)
      .bindHttp(18080, "localhost")
      .withHttpApp(CORS(Router.routes))
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
}
