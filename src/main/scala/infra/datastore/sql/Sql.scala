package infra.datastore.sql

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql._
import doobie._
import cats.effect._

trait Sql {
  val client: Client = Mysql.client
    .withCredentials("root", "pass")
    .withDatabase("adserver")
    .newRichClient("localhost:13306")

  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa = Transactor.fromDriverManager[IO](
    "com.mysql.jdbc.Driver",
    "jdbc:mysql://localhost:13306/adserver",
    "root",
    ""
  )

}

object Sql extends Sql

trait UsesSql {
  val sql: Sql
}

trait MixInSql {
  val sql: Sql = Sql
}
