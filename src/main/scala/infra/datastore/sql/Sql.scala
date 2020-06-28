package infra.datastore.sql

import doobie._
import cats.effect._

trait Sql {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  val xa = Transactor.fromDriverManager[IO](
    "com.mysql.jdbc.Driver",
    "jdbc:mysql://localhost:13306/adserver",
    "root",
    "pass"
  )

}

object Sql extends Sql

trait UsesSql {
  val sql: Sql
}

trait MixInSql {
  val sql: Sql = Sql
}
