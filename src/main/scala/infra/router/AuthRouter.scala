package infra.router

import domain.entity.auth.LoginReq
import io.finch._
import io.finch.syntax._

object AuthRouter {
  import io.circe.syntax._
  import io.circe.generic.auto._

  val login: Endpoint[String] = post("sessions" :: jsonBody[LoginReq]) {

    Ok("users!")
  }

  val router = login
}
