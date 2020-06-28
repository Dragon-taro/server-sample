package infra.router

import cats.effect._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import interface.controller.MixInAuthController
import domain.entity.auth.{LoginReq, LoginResp}

object AuthRouter extends MixInAuthController {
  implicit val loginReqDecoder = jsonOf[IO, LoginReq]
  implicit val loginRespDecoder = jsonOf[IO, LoginResp]

  val routes = HttpRoutes
    .of[IO] {
      case req @ POST -> Root =>
        (for {
          auth <- req.as[LoginReq]
          result <- authController.login(auth)
          resp <- Ok(result.asJson)
        } yield resp).handleErrorWith(ErrorHandling.handler)
    }
}
