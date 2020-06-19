package infra.router

import io.finch._
import io.finch.circe.Decoders
import io.finch.syntax._

object Router extends Decoders {
  val hc: Endpoint[String] = get("hc") {
    Ok("ok!")
  }

  val service = (hc :+: UserRouter.router).handle {
    case fe: io.finch.Error => BadRequest(new Exception(fe))
    case e: Exception       => InternalServerError(e)
  }.toServiceAs
}
