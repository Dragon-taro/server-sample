package infra.router

import cats.effect.IO
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.Response
import domain.entity.errorResp.ErrorResp
import domain.entity.errors.InvalidUserIdOrPasswordException

object ErrorHandling {
  def handler(e: Throwable): IO[Response[IO]] = e match {
    case e @ InvalidUserIdOrPasswordException =>
      BadRequest(ErrorResp.fromException(e).asJson)
    case e: Exception =>
      InternalServerError(ErrorResp.fromException(e).asJson)
  }
}
