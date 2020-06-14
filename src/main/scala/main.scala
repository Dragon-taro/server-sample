package main

import com.twitter.finagle.Http
import com.twitter.util.Await
import infra.router.Router

object MyServer extends App {
  val server = Http.serve(":18080", Router.service)
  Await.ready(server)
}
