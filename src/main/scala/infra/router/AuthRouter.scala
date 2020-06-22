package infra.router

import domain.entity.auth.LoginReq
import io.finch._
import io.finch.syntax._

object AuthRouter {

  val login: Endpoint[String] = post("sessions") {

    Ok("users!")
  }

  val router = login
}
