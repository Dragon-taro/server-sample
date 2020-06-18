package infra.router

import io.finch.{Endpoint, Ok, path}
import io.finch.syntax._

object AuthRouter {
  val login: Endpoint[String] = post("sessions") {

    Ok("users!")
  }

  val router = login
}
