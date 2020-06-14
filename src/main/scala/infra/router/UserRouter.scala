package infra.router

import io.finch._
import io.finch.syntax._

object UserRouter {
  val users: Endpoint[String] = get("users") {
    Ok("users!")
  }

  val user: Endpoint[String] = get("users" :: path[Long]) { id: Long =>
    Ok(s"user: $id!")
  }

  val router = users :+: user
}
