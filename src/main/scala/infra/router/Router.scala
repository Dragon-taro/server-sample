package infra.router

import io.finch._
import io.finch.syntax._

object Router {
  val hc: Endpoint[String] = get("hc") {
    Ok("ok!")
  }

  val service = (hc :+: UserRouter.router).handle {
    case fe: io.finch.Error => BadRequest(new Exception(fe))
    case e: Exception       => InternalServerError(e)
  }.toServiceAs
}
