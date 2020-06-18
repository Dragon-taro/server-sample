package infra.datastore.sql

import com.twitter.finagle.Mysql
import com.twitter.finagle.mysql._

trait Sql {
  val client: Client = Mysql.client
    .withCredentials("root", "pass")
    .withDatabase("adserver")
    .newRichClient("localhost:13306")
}

object Sql extends Sql

trait UsesSql {
  val sql: Sql
}

trait MixInSql {
  val sql: Sql = Sql
}
