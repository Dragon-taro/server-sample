package infra.router

import cats.effect._
import io.circe.generic.auto._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import interface.controller.MixInAuthController
import domain.entity.auth.{LoginReq, LoginResp}
import org.http4s.server.Router

object RootRouter extends MixInAuthController {
  implicit val loginReqDecoder = jsonOf[IO, LoginReq]
  implicit val loginRespDecoder = jsonOf[IO, LoginResp]

  val defaultRouter = HttpRoutes
    .of[IO] {
      case GET -> Root / "hc" =>
        Ok("ok!")
    }

  val routes =
    Router("" -> defaultRouter, "sessions" -> AuthRouter.routes).orNotFound
}
