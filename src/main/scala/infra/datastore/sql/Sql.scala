package infra.datastore.sql

import com.twitter.finagle.Mysql.newRichClient
import com.twitter.finagle.mysql.Client

trait Sql {
  val client: Client = newRichClient("root:pass@tcp(localhost:13306)/adserver")
}

object Sql extends Sql

trait UsesSql {
  val sql: Sql
}

trait MixInSql {
  val sql: Sql = Sql
}
